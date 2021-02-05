package com.ijiagoushi.chillies.core.utils;

import com.ijiagoushi.chillies.core.exceptions.NewInstanceException;
import com.ijiagoushi.chillies.core.exceptions.ReflectUtilException;
import com.ijiagoushi.chillies.core.lang.ArrayUtil;
import com.ijiagoushi.chillies.core.lang.Preconditions;
import com.ijiagoushi.chillies.core.lang.SimpleCache;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

/**
 *
 */
public class ReflectUtil {

    /**
     * 构造器缓存
     */
    private static final SimpleCache<Class<?>, Constructor<?>[]> CONSTRUCTORS = new SimpleCache<>(1024);

    /**
     * 获取一个类的所有构造器
     *
     * @param clazz 类
     * @param <T>   构造的对象类型
     * @return 构造器
     * @throws SecurityException Security Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> Constructor<T>[] getConstructors(Class<T> clazz) throws SecurityException {
        return (Constructor<T>[]) CONSTRUCTORS.putIfAbsent(clazz, clazz.getDeclaredConstructors());
    }

    /**
     * 获取一个类的所有构造器
     *
     * @param clazz 类
     * @param <T>   构造的对象类型
     * @return 构造器
     * @throws SecurityException Security Exception
     */
    public static <T> Stream<Constructor<T>> getConstructorsStream(Class<T> clazz) throws SecurityException {
        return Arrays.stream(getConstructors(clazz));
    }

    /**
     * 获取一个类指定参数的构造器，如果找到会设置为可访问为true（即私有的也可以被访问）
     *
     * @param clazz          类
     * @param parameterTypes 参数类型，可以不传（则无参构造器）
     * @param <T>            对象类型
     * @return 匹配的构造器或{@code null}
     */
    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... parameterTypes) {
        if (clazz == null) {
            return null;
        }
        return getConstructorsStream(clazz)
                .filter(tConstructor -> ClassUtil.isAllAssignable(tConstructor.getParameterTypes(), parameterTypes))
                .peek(ReflectUtil::setAccessible)
                .findFirst()
                .orElse(null);
    }

    /**
     * 实例化对象
     *
     * @param clazz      类
     * @param parameters 构造器的参数
     * @param <T>        对象类型
     * @return 实例化后的对象
     */
    public static <T> T newInstance(Class<T> clazz, Object... parameters) {
        // 无参构造
        if (ArrayUtil.isEmpty(parameters)) {
            try {
                return getConstructor(clazz).newInstance();
            } catch (ReflectiveOperationException e) {
                throw new NewInstanceException(e);
            }
        }

        final Class<?>[] parameterTypes = ClassUtil.getClasses(parameters);
        final Constructor<T> constructor = getConstructor(clazz, parameterTypes);
        if (constructor == null) {
            throw new ReflectUtilException("No Constructor matched for parameter types: [{}]", new Object[]{parameterTypes});
        }
        try {
            return constructor.newInstance(parameters);
        } catch (ReflectiveOperationException e) {
            throw new NewInstanceException(e);
        }
    }

    /**
     * 根据类型创建一个实例
     * <p>特殊接口，默认实现实例化</p>
     *
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T tryNewInstance(Class<T> clazz) {
        Preconditions.requireNonNull(clazz);
        if (clazz.isAssignableFrom(Map.class)) {
            clazz = (Class<T>) HashMap.class;
        } else if (clazz.isAssignableFrom(List.class)) {
            clazz = (Class<T>) ArrayList.class;
        } else if (clazz.isAssignableFrom(Set.class)) {
            clazz = (Class<T>) HashSet.class;
        }
        try {
            return newInstance(clazz);
        } catch (NewInstanceException ignored) {
            // ignored
        }

        return getConstructorsStream(clazz)
                .filter(tConstructor -> tConstructor.getParameterCount() != 0)
                .findFirst()
                .map(tConstructor -> {
                    setAccessible(tConstructor);
                    try {
                        return tConstructor.newInstance(ClassUtil.getDefaultValues(tConstructor.getParameterTypes()));
                    } catch (ReflectiveOperationException ignored) {
                        // ignore
                        return null;
                    }
                })
                .orElse(null);

    }

    /**
     * 设置方法为可访问（私有方法可以被外部调用）
     *
     * @param <T>              AccessibleObject的子类，比如Class、Method、Field等
     * @param accessibleObject 可设置访问权限的对象，比如Class、Method、Field等
     * @return 被设置可访问的对象
     */
    public static <T extends AccessibleObject> T setAccessible(T accessibleObject) {
        if (null != accessibleObject && !accessibleObject.isAccessible()) {
            accessibleObject.setAccessible(true);
        }
        return accessibleObject;
    }

    /**
     * 方法调用
     *
     * @param method 方法
     * @param obj    对象
     * @param args   所需的参数，可空
     * @return 调用的结果
     */
    @SuppressWarnings("unchecked")
    public static <T> T invoke(Method method, Object obj, Object... args) {
        setAccessible(method);

        final Class<?>[] parameterTypes = method.getParameterTypes();
        // 1、忽略多余的参数
        final Object[] actualArgs = new Object[parameterTypes.length];
        if (args != null) {
            final int length = actualArgs.length;
            for (int i = 0; i < length; i++) {
                if (i >= args.length || null == args[i]) {
                    // 越界或者空值
                    // 2、参数不够补齐默认值
                    actualArgs[i] = ClassUtil.getDefaultValue(parameterTypes[i]);
                } else if (!parameterTypes[i].isAssignableFrom(args[i].getClass())) {
                    // 类型转换
                    // 5、传入参数类型不对应，尝试转换类型
                    actualArgs[i] = args[i];
                } else {
                    actualArgs[i] = args[i];
                }
            }
        }

        try {
            return (T) method.invoke(ClassUtil.isStatic(method) ? null : obj, actualArgs);
        } catch (ReflectiveOperationException e) {
            throw new ReflectUtilException(e);
        }
    }

}
