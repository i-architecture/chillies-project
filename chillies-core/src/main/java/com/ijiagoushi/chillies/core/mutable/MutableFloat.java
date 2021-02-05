package com.ijiagoushi.chillies.core.mutable;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * 可变的float
 *
 * @author miles.tang at 2021-01-20
 * @since 1.0
 */
public class MutableFloat extends Number implements Comparable<MutableFloat>, Mutable<Float> {

    private float value;

    /**
     * 构造，默认值0
     */
    public MutableFloat() {
        super();
    }

    /**
     * 构造
     *
     * @param value 值
     */
    public MutableFloat(float value) {
        super();
        this.value = value;
    }

    /**
     * 构造
     *
     * @param value 值
     */
    public MutableFloat(@NotNull Number value) {
        this(value.floatValue());
    }

    /**
     * 构造
     *
     * @param value String值
     * @throws NumberFormatException 数字转换错误
     */
    public MutableFloat(@NotNull String value) throws NumberFormatException {
        super();
        this.value = Float.parseFloat(value);
    }

    @Override
    public Float get() {
        return value;
    }

    @Override
    public void set(@NotNull Float value) {
        this.value = value;
    }

    @Override
    public int compareTo(@NotNull MutableFloat o) {
        return Float.compare(this.value, o.value);
    }

    @Override
    public int intValue() {
        return (int) value;
    }

    @Override
    public long longValue() {
        return (long) value;
    }

    @Override
    public float floatValue() {
        return value;
    }

    @Override
    public double doubleValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MutableFloat that = (MutableFloat) o;
        return Float.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    /**
     * 值+1
     *
     * @return this
     */
    public MutableFloat increment() {
        value++;
        return this;
    }

    /**
     * 值减一
     *
     * @return this
     */
    public MutableFloat decrement() {
        value--;
        return this;
    }

    /**
     * 增加值
     *
     * @param operand 被增加的值
     * @return this
     */
    public MutableFloat add(float operand) {
        this.value += operand;
        return this;
    }

    /**
     * 增加值
     *
     * @param operand 被增加的值，非空
     * @return this
     * @throws NullPointerException if the object is null
     */
    public MutableFloat add(@NotNull Number operand) {
        this.value += operand.floatValue();
        return this;
    }

    /**
     * 减去值
     *
     * @param operand 被减的值
     * @return this
     */
    public MutableFloat subtract(float operand) {
        this.value -= operand;
        return this;
    }

    /**
     * 减去值
     *
     * @param operand 被减的值，非空
     * @return this
     * @throws NullPointerException if the object is null
     */
    public MutableFloat subtract(@NotNull Number operand) {
        this.value -= operand.floatValue();
        return this;
    }

}
