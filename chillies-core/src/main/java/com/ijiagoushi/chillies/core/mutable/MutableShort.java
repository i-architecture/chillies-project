package com.ijiagoushi.chillies.core.mutable;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * 可变的short
 *
 * @author miles.tang at 2021-01-20
 * @since 1.0
 */
public class MutableShort extends Number implements Comparable<MutableShort>, Mutable<Short> {

    private short value;

    /**
     * 构造，默认值0
     */
    public MutableShort() {
        super();
    }

    /**
     * 构造
     *
     * @param value 值
     */
    public MutableShort(short value) {
        super();
        this.value = value;
    }

    /**
     * 构造
     *
     * @param value 值
     */
    public MutableShort(@NotNull Number value) {
        this(value.shortValue());
    }

    /**
     * 构造
     *
     * @param value String值
     * @throws NumberFormatException 转为Short错误
     */
    public MutableShort(@NotNull String value) throws NumberFormatException {
        super();
        this.value = Short.parseShort(value);
    }

    @Override
    public Short get() {
        return value;
    }

    @Override
    public void set(@NotNull Short value) {
        this.value = value;
    }

    @Override
    public int compareTo(@NotNull MutableShort o) {
        return Short.compare(this.value, o.value);
    }

    @Override
    public int intValue() {
        return value;
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
        MutableShort that = (MutableShort) o;
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
    public MutableShort increment() {
        value++;
        return this;
    }

    /**
     * 值减一
     *
     * @return this
     */
    public MutableShort decrement() {
        value--;
        return this;
    }

    /**
     * 增加值
     *
     * @param operand 被增加的值
     * @return this
     */
    public MutableShort add(final short operand) {
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
    public MutableShort add(final Number operand) {
        this.value += operand.shortValue();
        return this;
    }

    /**
     * 减去值
     *
     * @param operand 被减的值
     * @return this
     */
    public MutableShort subtract(final short operand) {
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
    public MutableShort subtract(final Number operand) {
        this.value -= operand.shortValue();
        return this;
    }

}
