package com.ijiagoushi.chillies.core.map;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * 自定义键的Map
 *
 * @author miles.tang at 2021-01-21
 * @since 1.0
 */
public abstract class CustomKeyMap<K, V> extends MapWrapper<K, V> {

    /**
     * use serialVersionUID from JDK 1.0.2 for interoperability
     */
    private static final long serialVersionUID = 2021L;

    /**
     * 构造
     *
     * @param raw 被包装的Map
     */
    public CustomKeyMap(Map<K, V> raw) {
        super(raw);
    }

    @Override
    public V get(Object key) {
        return super.get(customKey(key));
    }

    @Override
    public @Nullable V put(K key, V value) {
        //noinspection unchecked
        return super.put((K) customKey(key), value);
    }

    @Override
    public void putAll(@NotNull Map<? extends K, ? extends V> m) {
        m.forEach(this::put);
    }

    @Override
    public boolean containsKey(Object key) {
        return super.containsKey(customKey(key));
    }

    @Override
    public V remove(Object key) {
        return super.remove(customKey(key));
    }

    @Override
    public boolean remove(Object key, Object value) {
        return super.remove(customKey(key), value);
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        //noinspection unchecked
        return super.replace((K) customKey(key), oldValue, newValue);
    }

    @Override
    public @Nullable V replace(K key, V value) {
        //noinspection unchecked
        return super.replace((K) customKey(key), value);
    }

    /**
     * 自定义键
     *
     * @param key KEY
     * @return 自定义KEY
     */
    protected abstract Object customKey(Object key);

}
