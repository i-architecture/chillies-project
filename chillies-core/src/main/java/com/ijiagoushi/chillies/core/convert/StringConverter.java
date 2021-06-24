package com.ijiagoushi.chillies.core.convert;

/**
 * {@linkplain String}转换器
 *
 * @author miles.tang at 2021-04-21
 * @since 1.0
 */
public class StringConverter extends AbstractConverter<Object, String> {

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
    @Override
    protected String execInternal(Object value) {
        return execToStr(value);
    }

    /**
     * 获取此类实现类的反省类型
     *
     * @return 此类的泛型类型，坑你为{@code null}
     */
    @Override
    public Class<String> getTargetClass() {
        return String.class;
    }

    @Override
    public String toString() {
        return "StringConverter";
    }

}
