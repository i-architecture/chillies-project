package com.ijiagoushi.chillies.core.mutable;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * 可变的long
 *
 * @author miles.tang at 2021-01-20
 * @since 1.0
 */
public class MutableLong extends Number implements Comparable<MutableLong>, Mutable<Long> {

    private long value;

    /**
     * 构造，默认值0
     */
    public MutableLong() {
        super();
    }

    /**
     * 构造
     *
     * @param value 值
     */
    public MutableLong(long value) {
        super();
        this.value = value;
    }

    /**
     * 构造
     *
     * @param value 值
     */
    public MutableLong(@NotNull Number value) {
        this(value.longValue());
    }

    /**
     * 构造
     *
     * @param value String值
     * @throws NumberFormatException 数字转换错误
     */
    public MutableLong(@NotNull String value) throws NumberFormatException {
        super();
        this.value = Long.parseLong(value);
    }

    @Override
    public Long get() {
        return value;
    }

    @Override
    public void set(@NotNull Long value) {
        this.value = value;
    }

    @Override
    public int compareTo(@NotNull MutableLong o) {
        return Long.compare(this.value, o.value);
    }

    @Override
    public int intValue() {
        return (int) value;
    }

    @Override
    public long longValue() {
        return value;
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
        MutableLong that = (MutableLong) o;
        return value == that.value;
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
    public MutableLong increment() {
        value++;
        return this;
    }

    /**
     * 值减一
     *
     * @return this
     */
    public MutableLong decrement() {
        value--;
        return this;
    }

    /**
     * 增加值
     *
     * @param operand 被增加的值
     * @return this
     */
    public MutableLong add(long operand) {
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
    public MutableLong add(@NotNull Number operand) {
        this.value += operand.longValue();
        return this;
    }

    /**
     * 减去值
     *
     * @param operand 被减的值
     * @return this
     */
    public MutableLong subtract(long operand) {
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
    public MutableLong subtract(@NotNull Number operand) {
        this.value -= operand.longValue();
        return this;
    }

}
