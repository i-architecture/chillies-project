package com.ijiagoushi.chillies.core.mutable;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * 可变的double
 *
 * @author miles.tang at 2021-01-20
 * @since 1.0
 */
public class MutableDouble extends Number implements Comparable<MutableDouble>, Mutable<Double> {

    private double value;

    /**
     * 构造，默认值0
     */
    public MutableDouble() {
        super();
    }

    /**
     * 构造
     *
     * @param value 值
     */
    public MutableDouble(double value) {
        super();
        this.value = value;
    }

    /**
     * 构造
     *
     * @param value 值
     */
    public MutableDouble(@NotNull Number value) {
        this(value.doubleValue());
    }

    /**
     * 构造
     *
     * @param value String值
     * @throws NumberFormatException 数字转换错误
     */
    public MutableDouble(@NotNull String value) throws NumberFormatException {
        super();
        this.value = Double.parseDouble(value);
    }

    @Override
    public Double get() {
        return value;
    }

    @Override
    public void set(@NotNull Double value) {
        this.value = value;
    }

    @Override
    public int compareTo(@NotNull MutableDouble o) {
        return Double.compare(this.value, o.value);
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
        return (float) value;
    }

    @Override
    public double doubleValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MutableDouble that = (MutableDouble) o;
        return Double.compare(that.value, value) == 0;
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
    public MutableDouble increment() {
        value++;
        return this;
    }

    /**
     * 值减一
     *
     * @return this
     */
    public MutableDouble decrement() {
        value--;
        return this;
    }

    /**
     * 增加值
     *
     * @param operand 被增加的值
     * @return this
     */
    public MutableDouble add(double operand) {
        this.value += operand;
        return this;
    }

    /**
     * 增加值
     *
     * @param operand 被增加的值，非空
     * @return this
     */
    public MutableDouble add(@NotNull Number operand) {
        this.value += operand.doubleValue();
        return this;
    }

    /**
     * 减去值
     *
     * @param operand 被减的值
     * @return this
     */
    public MutableDouble subtract(double operand) {
        this.value -= operand;
        return this;
    }

    /**
     * 减去值
     *
     * @param operand 被减的值，非空
     * @return this
     */
    public MutableDouble subtract(@NotNull Number operand) {
        this.value -= operand.doubleValue();
        return this;
    }

}
