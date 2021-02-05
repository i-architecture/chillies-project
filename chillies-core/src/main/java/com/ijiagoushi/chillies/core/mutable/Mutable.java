package com.ijiagoushi.chillies.core.mutable;

/**
 * 可变类型接口
 *
 * @author miles.tang at 2021-01-20
 * @since 1.0
 */
public interface Mutable<T> {

    /**
     * 获取原始值
     *
     * @return 原始值
     */
    T get();

    /**
     * 设置值
     *
     * @param value 原始值
     */
    void set(T value);
}
