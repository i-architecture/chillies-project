package com.ijiagoushi.chillies.core.map;

import java.util.HashMap;
import java.util.Map;

/**
 * 忽略大小写的HashMap
 * 对KEY忽略大小写，get("key")和get("Key")获得的值相同，put进入的值也会被覆盖
 *
 * @author miles.tang at 2021-01-21
 * @since 1.0
 */
public class CaseInsensitiveHashMap<K, V> extends CustomKeyMap<K, V> {

    /**
     * 构造
     */
    public CaseInsensitiveHashMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * 构造
     *
     * @param initialCapacity 初始大小
     */
    public CaseInsensitiveHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * 构造
     *
     * @param m Map
     */
    public CaseInsensitiveHashMap(Map<? extends K, ? extends V> m) {
        this(DEFAULT_LOAD_FACTOR, m);
    }

    /**
     * 构造
     *
     * @param loadFactor 加载因子
     * @param m          Map
     * @since 3.1.2
     */
    public CaseInsensitiveHashMap(float loadFactor, Map<? extends K, ? extends V> m) {
        this(m.size(), loadFactor);
        this.putAll(m);
    }

    /**
     * 构造
     *
     * @param initialCapacity 初始大小
     * @param loadFactor      加载因子
     */
    public CaseInsensitiveHashMap(int initialCapacity, float loadFactor) {
        super(new HashMap<>(initialCapacity, loadFactor));
    }

    /**
     * 将Key转为小写
     *
     * @param key KEY
     * @return 小写KEY
     */
    @Override
    protected Object customKey(Object key) {
        if (key instanceof CharSequence) {
            key = key.toString().toLowerCase();
        }
        return key;
    }

}
