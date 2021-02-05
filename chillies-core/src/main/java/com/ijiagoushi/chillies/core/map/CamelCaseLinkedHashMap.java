package com.ijiagoushi.chillies.core.map;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * KEY为驼峰风格的LinkedHashMap
 * 对KEY转换为驼峰，get("key_name")和get("keyName")获得的值相同，put进入的值也会被覆盖
 *
 * @author miles.tang at 2021-01-21
 * @since 1.0
 */
public class CamelCaseLinkedHashMap<K, V> extends CamelCaseHashMap<K, V> {

    /**
     * 构造
     */
    public CamelCaseLinkedHashMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * 构造
     *
     * @param initialCapacity 初始大小
     */
    public CamelCaseLinkedHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * 构造
     *
     * @param m Map
     */
    public CamelCaseLinkedHashMap(Map<? extends K, ? extends V> m) {
        this(DEFAULT_LOAD_FACTOR, m);
    }

    /**
     * 构造
     *
     * @param loadFactor 加载因子
     * @param m          Map
     */
    public CamelCaseLinkedHashMap(float loadFactor, Map<? extends K, ? extends V> m) {
        this(m.size(), loadFactor);
        this.putAll(m);
    }

    /**
     * 构造
     *
     * @param initialCapacity 初始大小
     * @param loadFactor      加载因子
     */
    public CamelCaseLinkedHashMap(int initialCapacity, float loadFactor) {
        super(new LinkedHashMap<>(initialCapacity, loadFactor));
    }

}
