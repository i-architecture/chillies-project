package com.ijiagoushi.chillies.core.mutable;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * 可变的int
 *
 * @author miles.tang at 2021-01-20
 * @since 1.0
 */
public class MutableInt extends Number implements Comparable<MutableInt>, Mutable<Integer> {

    private int value;

    public MutableInt() {
        super();
    }

    public MutableInt(int value) {
        this();
        this.value = value;
    }

    public MutableInt(@NotNull Number value) {
        this(value.intValue());
    }

    public MutableInt(@NotNull String value) {
        this(Integer.parseInt(value));
    }

    /**
     * 获取原始值
     *
     * @return 原始值
     */
    @Override
    public Integer get() {
        return value;
    }

    /**
     * 设置值
     *
     * @param value 原始值
     */
    @Override
    public void set(Integer value) {
        this.value = value;
    }

    @Override
    public int compareTo(@NotNull MutableInt o) {
        return Integer.compare(this.value, o.value);
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
        MutableInt that = (MutableInt) o;
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
    public MutableInt increment() {
        value++;
        return this;
    }

    /**
     * 值减一
     *
     * @return this
     */
    public MutableInt decrement() {
        value--;
        return this;
    }

    /**
     * 增加值
     *
     * @param operand 被增加的值
     * @return this
     */
    public MutableInt add(int operand) {
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
    public MutableInt add(@NotNull Number operand) {
        this.value += operand.intValue();
        return this;
    }

    /**
     * 减去值
     *
     * @param operand 被减的值
     * @return this
     */
    public MutableInt subtract(int operand) {
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
    public MutableInt subtract(@NotNull Number operand) {
        this.value -= operand.intValue();
        return this;
    }

}
