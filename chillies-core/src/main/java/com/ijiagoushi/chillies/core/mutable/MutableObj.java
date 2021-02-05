package com.ijiagoushi.chillies.core.mutable;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Objects;

/**
 * 可变的Object
 *
 * @author miles.tang at 2021-01-20
 * @since 1.0
 */
public class MutableObj<T> implements Mutable<T>, Serializable {

    private T value;

    /**
     * 构造
     */
    public MutableObj() {
        super();
    }

    /**
     * 构造
     *
     * @param value 值
     */
    public MutableObj(@Nullable T value) {
        super();
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public void set(@Nullable T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MutableObj<?> that = (MutableObj<?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return (value == null) ? "null" : value.toString();
    }

}
