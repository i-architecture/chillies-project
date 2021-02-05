package com.ijiagoushi.chillies.core.map;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 忽略大小写的LinkedHashMap
 * 对KEY忽略大小写，get("key")和get("Key")获得的值相同，put进入的值也会被覆盖
 *
 * @author miles.tang at 2021-01-21
 * @since 1.0
 */
public class CaseInsensitiveLinkedHashMap<K, V> extends CaseInsensitiveHashMap<K, V> {

    /**
     * 构造
     */
    public CaseInsensitiveLinkedHashMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * 构造
     *
     * @param initialCapacity 初始大小
     */
    public CaseInsensitiveLinkedHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * 构造
     *
     * @param m Map
     */
    public CaseInsensitiveLinkedHashMap(Map<? extends K, ? extends V> m) {
        this(DEFAULT_LOAD_FACTOR, m);
    }

    /**
     * 构造
     *
     * @param loadFactor 加载因子
     * @param m          Map
     * @since 3.1.2
     */
    public CaseInsensitiveLinkedHashMap(float loadFactor, Map<? extends K, ? extends V> m) {
        this(m.size(), loadFactor);
        this.putAll(m);
    }

    /**
     * 构造
     *
     * @param initialCapacity 初始大小
     * @param loadFactor      加载因子
     */
    public CaseInsensitiveLinkedHashMap(int initialCapacity, float loadFactor) {
        super(new LinkedHashMap<>(initialCapacity, loadFactor));
    }

}
