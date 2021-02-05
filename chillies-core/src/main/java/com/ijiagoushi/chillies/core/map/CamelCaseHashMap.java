package com.ijiagoushi.chillies.core.map;

import com.ijiagoushi.chillies.core.lang.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * KEY为驼峰风格的HashMap
 * 对KEY转换为驼峰，get("key_name")和get("keyName")获得的值相同，put进入的值也会被覆盖
 *
 * @author miles.tang at 2021-01-21
 * @since 1.0
 */
public class CamelCaseHashMap<K, V> extends CustomKeyMap<K, V> {

    /**
     * 构造
     */
    public CamelCaseHashMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * 构造
     *
     * @param initialCapacity 初始大小
     */
    public CamelCaseHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * 构造
     *
     * @param m Map
     */
    public CamelCaseHashMap(Map<? extends K, ? extends V> m) {
        this(DEFAULT_LOAD_FACTOR, m);
    }

    /**
     * 构造
     *
     * @param loadFactor 加载因子
     * @param m          Map
     */
    public CamelCaseHashMap(float loadFactor, Map<? extends K, ? extends V> m) {
        this(m.size(), loadFactor);
        this.putAll(m);
    }

    /**
     * 构造
     *
     * @param initialCapacity 初始大小
     * @param loadFactor      加载因子
     */
    public CamelCaseHashMap(int initialCapacity, float loadFactor) {
        super(new HashMap<>(initialCapacity, loadFactor));
    }

    /**
     * 将Key转为驼峰风格，如果key为字符串的话
     *
     * @param key KEY
     * @return 驼峰Key
     */
    @Override
    protected Object customKey(Object key) {
        if (key instanceof CharSequence) {
            key = StringUtil.underlineToCamel(key.toString());
        }
        return key;
    }
}
