package com.ijiagoushi.chillies.core.lang;

import com.ijiagoushi.chillies.core.map.FixedLinkedHashMap;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.function.Function;

/**
 * 简单的缓存，基于{@linkplain SoftReference}实现了自动垃圾回收、LRU cache
 *
 * @author miles.tang at 2021-01-19
 * @see <a href="https://android.googlesource.com/platform/packages/apps/Mms/+/master/src/com/android/mms/util/SimpleCache.java">SimpleCache.java</a>
 * @since 1.0
 */
public class SimpleCache<K, V> implements Serializable {

    /**
     * The default initial capacity - MUST be a power of two.
     */
    public static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

    /**
     * The load factor used when none specified in constructor.
     */
    public static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private V unwrap(SoftReference<V> ref) {
        return ref != null ? ref.get() : null;
    }

    private final FixedLinkedHashMap<K, SoftReference<V>> fixedLinkedHashMap;

    public SimpleCache(int maxCapacity) {
        this(DEFAULT_INITIAL_CAPACITY, maxCapacity);
    }

    public SimpleCache(int initialCapacity, int maxCapacity) {
        this(initialCapacity, maxCapacity, DEFAULT_LOAD_FACTOR);
    }

    public SimpleCache(int initialCapacity, int maxCapacity, float loadFactor) {
        this.fixedLinkedHashMap = new FixedLinkedHashMap<>(initialCapacity, maxCapacity, loadFactor);
    }

    /**
     * 从缓存池中获取值
     *
     * @param key 键
     * @return 值
     */
    public V get(K key) {
        return unwrap(fixedLinkedHashMap.get(key));
    }

    /**
     * 将值放入到缓存中
     *
     * @param key   键
     * @param value 值
     * @return 值
     */
    public V put(K key, V value) {
        return unwrap(fixedLinkedHashMap.put(key, new SoftReference<>(value)));
    }

    /**
     * 如果不存在设置键值对
     *
     * @param key   键
     * @param value 值
     * @return 值
     */
    public V putIfAbsent(K key, V value) {
        V v = get(key);
        if (v == null) {
            v = put(key, value);
        }
        return v;
    }

    /**
     * 如果key对应的值不存在，则调用{@code mappingFunction}的计算结果设置到缓存中（计算结果不是{@code null}）
     *
     * @param key             键
     * @param mappingFunction 值的计算函数
     * @return 值
     */
    public V computeIfAbsent(K key, @NotNull Function<? super K, ? extends V> mappingFunction) {
        V v;
        if ((v = get(key)) == null) {
            V newValue;
            if ((newValue = mappingFunction.apply(key)) != null) {
                put(key, newValue);
                return newValue;
            }
        }
        return v;
    }

    /**
     * 清空缓存池
     */
    public void clear() {
        fixedLinkedHashMap.clear();
    }

    /**
     * 移除缓存
     *
     * @param key 键
     * @return 值
     */
    public V remove(K key) {
        return unwrap(fixedLinkedHashMap.remove(key));
    }

}
