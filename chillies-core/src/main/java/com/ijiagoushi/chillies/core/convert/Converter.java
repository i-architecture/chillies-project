package com.ijiagoushi.chillies.core.convert;

import com.ijiagoushi.chillies.core.exceptions.ConverterRuntimeException;

/**
 * 转换器接口
 *
 * @param <S> 输入参数类型
 * @param <T> 输出参数类型
 * @author miles.tang
 */
public interface Converter<S, T> {

    /**
     * 执行转为目标类型的对象
     *
     * @param src          源对象
     * @param defaultValue 默认值
     * @return 转换后的值
     * @throws ConverterRuntimeException 转换异常
     */
    T exec(S src, T defaultValue) throws ConverterRuntimeException;

}
