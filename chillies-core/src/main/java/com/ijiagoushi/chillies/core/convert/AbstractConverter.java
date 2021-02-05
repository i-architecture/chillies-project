package com.ijiagoushi.chillies.core.convert;

import com.ijiagoushi.chillies.core.exceptions.ConverterRuntimeException;
import com.ijiagoushi.chillies.core.lang.ArrayUtil;
import com.ijiagoushi.chillies.core.lang.CharUtil;
import com.ijiagoushi.chillies.core.lang.StringUtil;
import com.ijiagoushi.chillies.core.utils.ClassUtil;

import java.io.Serializable;
import java.util.Map;

/**
 * 抽象转换器，提供通用的转换逻辑
 *
 * @author miles.tang at 2021-01-22
 * @since 1.0
 */
public abstract class AbstractConverter<S, T> implements Converter<S, T>, Serializable {

    /**
     * use serialVersionUID from JDK 1.0.2 for interoperability
     */
    private static final long serialVersionUID = 2021L;

    @SuppressWarnings("unchecked")
    @Override
    public T exec(S src, T defaultValue) throws ConverterRuntimeException {
        Class<T> targetClass = getTargetClass();
        if (targetClass == null && defaultValue == null) {
            throw new ConverterRuntimeException(1, "[type] and [defaultValue] are both null for Converter");
        }
        if (targetClass == null) {
            // 目标类型不确定时使用默认值的类型
            targetClass = (Class<T>) defaultValue.getClass();
        }
        if (src == null) {
            return defaultValue;
        }
        if (null == defaultValue || targetClass.isInstance(defaultValue)) {
            if (targetClass.isInstance(src) && !Map.class.isAssignableFrom(targetClass)) {
                return targetClass.cast(src);
            }
            T result = execInternal(src);
            return (result == null) ? defaultValue : result;
        } else {
            throw new ConverterRuntimeException(2,
                    StringUtil.format("Default value [{}]({}) is not the instance of [{}]", defaultValue, defaultValue.getClass(), targetClass));
        }
    }

    /**
     * 内部转换器，被 {@link AbstractConverter#exec(Object, Object)} 调用，实现基本转换逻辑<br>
     * 内部转换器转换后如果转换失败可以做如下操作，处理结果都为返回默认值：
     *
     * <pre>
     * 1、返回{@code null}
     * 2、抛出一个{@link RuntimeException}异常
     * </pre>
     *
     * @param value 值
     * @return 转换后的类型
     */
    protected abstract T execInternal(Object value);

    /**
     * 获取此类实现类的反省类型
     *
     * @return 此类的泛型类型，坑你为{@code null}
     */
    @SuppressWarnings("unchecked")
    public Class<T> getTargetClass() {
        return (Class<T>) ClassUtil.getGenericType(getClass());
    }

    /**
     * 值转为String，用于内部转换中需要使用String中转的情况<br>
     * 转换规则为：
     *
     * <pre>
     * 1、字符串类型将被强转
     * 2、数组将被转换为逗号分隔的字符串
     * 3、其它类型将调用默认的toString()方法
     * </pre>
     *
     * @param value 值
     * @return String
     */
    protected String execToStr(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof CharSequence) {
            return value.toString();
        } else if (ArrayUtil.isArray(value)) {
            return ArrayUtil.toString(value);
        } else if (CharUtil.isChar(value)) {
            //对于ASCII字符使用缓存加速转换，减少空间创建
            return CharUtil.toString((char) value);
        }
        return value.toString();
    }

}
