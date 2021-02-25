package com.ijiagoushi.chillies.core.bean;

import com.ijiagoushi.chillies.core.exceptions.BeanException;
import com.ijiagoushi.chillies.core.lang.Preconditions;
import com.ijiagoushi.chillies.core.utils.ClassUtil;
import com.ijiagoushi.chillies.core.utils.ReflectUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Java Bean工具类
 *
 * @author miles.tang
 */
public class BeanUtil {

    /**
     * 判断是不是普通的JavaBean，判断方法为：
     * <pre>
     *     1. 判断属性是不是有getter/setter方法
     *     2. 判断字段是不是public修饰符
     * </pre>
     *
     * @param tClass
     * @return
     */
    public static boolean isBeanType(Class<?> tClass) {
        if (!ClassUtil.isNormalClass(tClass)) {
            return false;
        }

        // 判断你是不是有public修饰符
        boolean result = Arrays.stream(tClass.getDeclaredFields())
                .anyMatch(field -> Modifier.isPublic(field.getModifiers()) && !Modifier.isStatic(field.getModifiers()));
        if (result) {
            return true;
        }

        Method[] methods = tClass.getDeclaredMethods();
        // 是不是有setter
        result = Arrays.stream(methods).anyMatch(method -> method.getName().startsWith("set"));
        if (result) {
            // 必须同时还要有getter/is
//            return Arrays.stream(methods)
//                    .anyMatch(method -> {
//                        System.out.println("method = " + method);
//                        System.out.println("method.getName() = " + method.getName());
//                        return method.getName().startsWith("get") || method.getName().startsWith("is");
//                    });

            return Arrays.stream(methods).anyMatch(method -> method.getName().startsWith("get") || method.getName().startsWith("is"));
        }

        return false;
    }

    /**
     * 获得Bean字段描述集合,获得的结果会缓存在{@link BeanIntrospectorCache}中
     *
     * @param beanClass Bean类
     * @return 字段描述数组
     * @throws BeanException 获取属性异常
     */
    public static List<PropertyDescriptor> getPropertyDescriptorList(Class<?> beanClass) throws BeanException {
        Preconditions.requireNonNull(beanClass, "beanClass == null");
        return BeanIntrospectorCache.getInstance().getPropertyDescriptors(beanClass);
    }

    /**
     * 获得字段名和字段描述Map,获得的结果会缓存在{@link BeanIntrospectorCache}中
     *
     * @param beanClass Bean类
     * @return 字段名和字段描述Map
     * @throws BeanException 获取属性异常
     */
    public static Map<String, PropertyDescriptor> getPropertyDescriptorMap(Class<?> beanClass) throws BeanException {
        List<PropertyDescriptor> list = getPropertyDescriptorList(beanClass);
        final Map<String, PropertyDescriptor> map = new HashMap<>(list.size());
        for (PropertyDescriptor propertyDescriptor : list) {
            map.put(propertyDescriptor.getName(), propertyDescriptor);
        }
        return map;
    }

    /**
     * 根据属性名称获取对应属性对象
     *
     * @param beanClass    类型
     * @param propertyName 属性名
     * @return {@linkplain PropertyDescriptor}
     */
    public static PropertyDescriptor getPropertyDescriptor(Class<?> beanClass, String propertyName) {
        return getPropertyDescriptorMap(beanClass).get(Preconditions.requireNotEmpty(propertyName, "propertyName is null or empty"));
    }

    /**
     * 获取属性值,通过{@linkplain PropertyDescriptor}获取，必须要求要求实现{@code getter}方法
     *
     * @param bean         bean实例
     * @param propertyName 属性名
     * @param <T>          属性值类型
     * @return 属性值, 如果属性找不到或未实现{@code getter}方法返回{@code null}
     */
    @SuppressWarnings("unchecked")
    public static <T> T getPropertyValue(Object bean, String propertyName) {
        Preconditions.requireNonNull(bean, "bean == null");
        PropertyDescriptor pd = getPropertyDescriptor(bean.getClass(), propertyName);
        if (pd == null) {
            return null;
        }
        try {
            Method readMethod = pd.getReadMethod();
            if (readMethod == null) {
                return null;
            }
            return (T) readMethod.invoke(bean);
        } catch (ReflectiveOperationException e) {
            throw new BeanException(e);
        }
    }

    /**
     * 对象转Map,不忽略值为空的字段
     *
     * @param bean bean对象
     * @return Map
     */
    public static Map<String, Object> beanToMap(Object bean) {
        return beanToMap(bean, false);
    }

    /**
     * 对象转Map
     *
     * @param bean            bean对象
     * @param ignoreNullValue 是否忽略值为空的字段
     * @return Map
     */
    public static Map<String, Object> beanToMap(Object bean, boolean ignoreNullValue) {
        Map<String, Object> targetMap = new LinkedHashMap<>();
        beanToMap(bean, targetMap, ignoreNullValue);
        return targetMap;
    }

    /**
     * 对象转Map
     *
     * @param bean            bean对象
     * @param targetMap       目标的Map
     * @param ignoreNullValue 是否忽略值为空的字段
     */
    public static void beanToMap(Object bean, Map<String, Object> targetMap, boolean ignoreNullValue) {
        if (bean == null || targetMap == null) {
            return;
        }
        Method readMethod;
        Object value;
        for (PropertyDescriptor propertyDescriptor : getPropertyDescriptorList(bean.getClass())) {
            readMethod = propertyDescriptor.getReadMethod();
            if (readMethod != null) {
                try {
                    value = readMethod.invoke(bean);
                    if (ignoreNullValue && value != null) {
                        targetMap.put(propertyDescriptor.getName(), value);
                    } else {
                        targetMap.put(propertyDescriptor.getName(), value);
                    }
                } catch (ReflectiveOperationException e) {
                    throw new BeanException(e);
                }
            }
        }

    }

    /**
     * Java Bean对象转Map
     *
     * @param bean       bean对象
     * @param targetMap  目标的Map
     * @param copyOption 复制配置
     */
    public static void beanToMap(@Nullable Object bean, @NotNull Map<String, Object> targetMap, @Nullable CopyOption copyOption) {
        if (bean == null) {
            return;
        }
        if (copyOption == null) {
            copyOption = new CopyOption();
        }
        final CopyOption finalCo = copyOption;
        final Map<String, PropertyDescriptor> propertyDescriptorMap = getPropertyDescriptorMap(bean.getClass());
        propertyDescriptorMap.forEach((key, pd) -> {
            Object value = ReflectUtil.invoke(pd.getReadMethod(), bean);
            copyToMap(key, value, targetMap, finalCo);
        });
    }

    /**
     * Map Copy To Map
     *
     * @param source     源对象
     * @param target     目标对象
     * @param copyOption 拷贝的配置
     */
    public static void mapToMap(@Nullable Map<String, Object> source, @NotNull Map<String, Object> target, @Nullable CopyOption copyOption) {
        if (source == null) {
            return;
        }
        if (copyOption == null) {
            copyOption = new CopyOption();
        }
        final CopyOption finalCo = copyOption;
        source.forEach((key, value) -> {
            copyToMap(key, value, target, finalCo);
        });
    }

    /**
     * Map对象转为Java Bean对象
     *
     * @param source     源对象
     * @param target     目标对象
     * @param copyOption 拷贝配置
     */
    public static void mapToBean(@Nullable Map<String, Object> source, @NotNull Object target, @Nullable CopyOption copyOption) {
        if (source == null) {
            return;
        }
        if (copyOption == null) {
            copyOption = new CopyOption();
        }
        final CopyOption finalCo = copyOption;
        Map<String, PropertyDescriptor> propertyDescriptorMap = getPropertyDescriptorMap(target.getClass());
        source.forEach((key, value) -> {
            if (value == null) {
                if (!finalCo.isIgnoreNullValue()) {
                    if (!finalCo.getIgnoreProperties().contains(key)) {
                        PropertyDescriptor propertyDescriptor = propertyDescriptorMap.get(key);
                        Method writeMethod = propertyDescriptor.getWriteMethod();
                        if (writeMethod != null) {
                            ReflectUtil.invoke(writeMethod, target, new Object[]{null});
                        }
                    }
                }
            } else {
                if (!finalCo.getIgnoreProperties().contains(key)) {
                    PropertyDescriptor propertyDescriptor = propertyDescriptorMap.get(key);
                    Method writeMethod = propertyDescriptor.getWriteMethod();
                    if (writeMethod != null) {
                        ReflectUtil.invoke(writeMethod, target, value);
                    }
                }
            }
        });
    }

    /**
     * 将Javabean对象转为Map,其中值的类型为{@code String}
     *
     * @param bean       对象
     * @param ignoreNull 是否忽略空值
     * @return Map对象
     */
    public static Map<String, String> toMapAsValueString(Object bean, boolean ignoreNull) {
        Map<String, Object> propertiesMap = beanToMap(bean);
        Map<String, String> resultMap = new HashMap<>(propertiesMap.size());

        Object _value;
        for (String propertyName : propertiesMap.keySet()) {
            _value = propertiesMap.get(propertyName);
            if (_value == null) {
                if (!ignoreNull) {
                    resultMap.put(propertyName, null);
                }
            } else {
                if (_value instanceof Number) {
                    Number number = (Number) _value;
                    resultMap.put(propertyName, number.toString());
                } else if (_value instanceof String) {
                    resultMap.put(propertyName, (String) _value);
                } else if (_value instanceof Date) {
                    resultMap.put(propertyName, String.valueOf(((Date) _value).getTime()));
                } else {
                    resultMap.put(propertyName, _value.toString());
                }
            }
        }
        return resultMap;
    }

    /**
     * Javabean的属性值拷贝，即对象的拷贝
     *
     * @param source 原始对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target) {
        copyProperties(source, target, (String) null);
    }

    /**
     * Javabean的属性值拷贝，即对象的拷贝
     *
     * @param source           原始对象
     * @param target           目标对象
     * @param ignoreProperties 过滤的属性名
     */
    public static void copyProperties(@Nullable Object source, @NotNull Object target, @Nullable String... ignoreProperties) {
        copyProperties(source, target, new CopyOption(ignoreProperties));
    }

    /**
     * 对象复制，如果值为{@code null}时如果设置{@code ignoreNullValue}为{@code true}则不复制
     *
     * @param source          源对象
     * @param target          目标对象
     * @param ignoreNullValue 是否忽略null值
     */
    public static void copyProperties(Object source, Object target, boolean ignoreNullValue) {
        copyProperties(source, target, new CopyOption(ignoreNullValue));
    }

    /**
     * 对象复制
     *
     * @param source     源对象
     * @param target     目标对象
     * @param copyOption 复制配置
     */
    @SuppressWarnings("unchecked")
    public static void copyProperties(@Nullable Object source, @NotNull Object target, @Nullable CopyOption copyOption) {
        if (source == null) {
            return;
        }
        if (copyOption == null) {
            copyOption = new CopyOption();
        }
        if (source instanceof Map) {
            if (target instanceof Map) {
                mapToMap((Map<String, Object>) source, (Map<String, Object>) target, copyOption);
            } else {
                mapToBean((Map<String, Object>) source, target, copyOption);
            }
        } else {
            if (target instanceof Map) {
                beanToMap(source, (Map<String, Object>) target, copyOption);
            } else {
                final CopyOption finalCo = copyOption;
                final Map<String, PropertyDescriptor> targetPdMap = getPropertyDescriptorMap(target.getClass());
                getPropertyDescriptorMap(source.getClass()).forEach((key, sourcePd) -> {
                    Object value = ReflectUtil.invoke(sourcePd.getReadMethod(), source);
                    if (value == null) {
                        if (!finalCo.isIgnoreNullValue()) {
                            PropertyDescriptor targetPd = targetPdMap.get(key);
                            if (targetPd != null) {
                                ReflectUtil.invoke(targetPd.getWriteMethod(), target, new Object[]{null});
                            }
                        }
                    } else if (!finalCo.getIgnoreProperties().contains(key)) {
                        PropertyDescriptor targetPd = targetPdMap.get(key);
                        if (targetPd != null) {
                            ReflectUtil.invoke(targetPd.getWriteMethod(), target, value);
                        }
                    }
                });

            }
        }
    }


    private static void copyToMap(String key, Object value, Map<String, Object> targetMap, CopyOption copyOption) {
        if (value == null) {
            if (!copyOption.isIgnoreNullValue()) {
                if (!copyOption.getIgnoreProperties().contains(key)) {
                    targetMap.put(key, null);
                }
            }
        } else {
            if (!copyOption.getIgnoreProperties().contains(key)) {
                targetMap.put(key, value);
            }
        }
    }

}
