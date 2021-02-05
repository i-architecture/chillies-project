package com.ijiagoushi.chillies.core.mutable;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * 可变的字节
 *
 * @author miles.tang at 2021-01-20
 * @since 1.0
 */
public class MutableByte extends Number implements Comparable<MutableByte>, Mutable<Byte> {

    private byte value;

    /**
     * 构造，默认值0
     */
    public MutableByte() {
        super();
    }

    /**
     * 构造
     *
     * @param value 值
     */
    public MutableByte(byte value) {
        super();
        this.value = value;
    }

    /**
     * 构造
     *
     * @param value 值
     */
    public MutableByte(@NotNull Number value) {
        this(value.byteValue());
    }

    /**
     * 构造
     *
     * @param value String值
     * @throws NumberFormatException 转为Byte错误
     */
    public MutableByte(@NotNull String value) throws NumberFormatException {
        super();
        this.value = Byte.parseByte(value);
    }

    /**
     * 获取原始值
     *
     * @return 原始值
     */
    @Override
    public Byte get() {
        return value;
    }

    /**
     * 设置值
     *
     * @param value 原始值
     */
    @Override
    public void set(@NotNull Byte value) {
        this.value = value;
    }

    @Override
    public int compareTo(@NotNull MutableByte o) {
        return Byte.compare(this.value, o.value);
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
        MutableByte that = (MutableByte) o;
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
    public MutableByte increment() {
        value++;
        return this;
    }

    /**
     * 值减一
     *
     * @return this
     */
    public MutableByte decrement() {
        value--;
        return this;
    }

    /**
     * 增加值
     *
     * @param operand 被增加的值
     * @return this
     */
    public MutableByte add(byte operand) {
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
    public MutableByte add(@NotNull Number operand) {
        this.value += operand.byteValue();
        return this;
    }

    /**
     * 减去值
     *
     * @param operand 被减的值
     * @return this
     */
    public MutableByte subtract(byte operand) {
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
    public MutableByte subtract(@NotNull Number operand) {
        this.value -= operand.byteValue();
        return this;
    }

}
