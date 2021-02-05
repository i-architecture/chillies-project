package com.ijiagoushi.chillies.core.map;

import com.ijiagoushi.chillies.core.lang.CollectionUtil;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * 值为多个的Map
 *
 * @author miles.tang at 2021-01-21
 * @since 1.0
 */
public interface MultiValueMap<K, V> extends Map<K, List<V>> {

    /**
     * 返回指定key值列表中的第一个
     *
     * @param key 键
     * @return 指定key的第一个值或{@code null}
     */
    @Nullable
    V getFirst(K key);

    /**
     * 返回指定key的值列表中的最后一个
     *
     * @param key 键
     * @return 值或{@code null}
     */
    @Nullable
    V getLast(K key);

    /**
     * 添加一个键值对
     *
     * @param key   键
     * @param value 值
     */
    void add(K key, @Nullable V value);

    /**
     * 添加指定键的值列表
     *
     * @param key    键
     * @param values 值列表
     */
    void addAll(K key, List<? extends V> values);

    /**
     * 添加
     *
     * @param key    键
     * @param values 值
     */
    default void addAll(K key, V... values) {
        addAll(key, CollectionUtil.newArrayList(values));
    }

    /**
     * 添加
     *
     * @param m map
     */
    void addAll(MultiValueMap<K, V> m);

    /**
     * 如果不存在则添加
     *
     * @param key   键
     * @param value 值
     */
    default void addIfAbsent(K key, @Nullable V value) {
        if (!containsKey(key)) {
            add(key, value);
        }
    }

    /**
     * 重新设置key对应的值
     *
     * @param key   键
     * @param value 值
     */
    void set(K key, @Nullable V value);

    /**
     * 设置
     *
     * @param map map
     */
    void setAll(Map<K, V> map);

    /**
     * 转为单个值的Map
     *
     * @return map
     */
    Map<K, V> toSingleValueMap();

}
