package com.ijiagoushi.chillies.core.convert;

/**
 * DateTime Converter
 *
 * @author miles.tang at 2021-04-25
 * @since 1.0
 */
public class DateTimeConverter<T extends java.util.Date> extends AbstractConverter<Object, T> {

    protected Class<T> targetClass;

    /**
     * 日期格式化
     */
    private String format;

    public DateTimeConverter() {
    }

    public DateTimeConverter(String format) {
        this.format = format;
    }

    public DateTimeConverter(Class<T> targetClass, String format) {
        this.targetClass = targetClass;
        this.format = format;
    }

    public void setTargetClass(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
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
    @Override
    protected T execInternal(Object value) {

        return null;
    }

    /**
     * 返回实际目标类型
     *
     * @return 实际目标类型
     */
    @Override
    public Class<T> getTargetClass() {
        return targetClass;
    }

}
