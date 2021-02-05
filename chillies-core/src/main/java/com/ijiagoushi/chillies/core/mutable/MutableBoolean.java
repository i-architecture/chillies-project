package com.ijiagoushi.chillies.core.mutable;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * 可变的Boolean
 *
 * @author miles.tang at 2021-01-20
 * @since 1.0
 */
public class MutableBoolean implements Comparable<MutableBoolean>, Mutable<Boolean>, Serializable {

    private boolean value;

    /**
     * 构造，默认值false
     */
    public MutableBoolean() {
        super();
    }

    /**
     * 构造
     *
     * @param value 值
     */
    public MutableBoolean( boolean value) {
        super();
        this.value = value;
    }

    /**
     * 构造
     *
     * @param value String值
     * @throws NumberFormatException 转为Boolean错误
     */
    public MutableBoolean(@NotNull String value) throws NumberFormatException {
        super();
        this.value = Boolean.parseBoolean(value);
    }

    /**
     * 获取原始值
     *
     * @return 原始值
     */
    @Override
    public Boolean get() {
        return value;
    }

    /**
     * 设置值
     *
     * @param value 原始值
     */
    @Override
    public void set(Boolean value) {
        this.value = value;
    }

    @Override
    public int compareTo(@NotNull MutableBoolean o) {
        return Boolean.compare(this.value, o.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MutableBoolean that = (MutableBoolean) o;
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

}
