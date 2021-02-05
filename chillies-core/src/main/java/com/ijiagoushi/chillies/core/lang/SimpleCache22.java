package com.ijiagoushi.chillies.core.lang;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 实现了一个简单的缓存类
 *
 * @param <K> 键类型
 * @param <V> 值类型
 * @author miles.tang at 2021-01-19
 * @since 1.0
 */
 class SimpleCache22<K, V> implements Serializable {

    /**
     * 最多缓存对象的个数
     */
    private final int maxCapacity;

    /**
     * 边界缓存/临时缓存
     */
    private final Map<K, V> edenCache;

    /**
     * 长久缓存（当对象不被使用时会被GC的）
     */
    private final Map<K, V> permanentCache;

    /**
     * 乐观读写锁
     */
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    /**
     * 默认的构造器，默认最多可缓存1024个对象
     * 默认使用{@linkplain ConcurrentHashMap}实现临时缓存和{@linkplain WeakHashMap}实现永久缓存（自动清理）
     */
    public SimpleCache22() {
        this(1024);
    }

    /**
     * 指定最多缓存对象个数的构造器
     * 默认使用{@linkplain ConcurrentHashMap}实现临时缓存和{@linkplain WeakHashMap}实现永久缓存（自动清理）
     *
     * @param maxCapacity 最多缓存对象个数
     */
    public SimpleCache22(int maxCapacity) {
        this(maxCapacity, new ConcurrentHashMap<>(maxCapacity));
    }

    /**
     * 自定义最多缓存对象个数和自定义临时Map
     *
     * @param maxCapacity 最多缓存对象个数
     * @param initEdenMap 自定义临时缓存对象
     * @see #SimpleCache22(int, Map, Map)
     */
    public SimpleCache22(int maxCapacity, Map<K, V> initEdenMap) {
        this(maxCapacity, initEdenMap, new WeakHashMap<>(maxCapacity));
    }

    /**
     * 自定义最多缓存对象个数、临时Map和永久Map
     * <p>
     * 通过自定义初始化，可以实现Key的清理规则，同时也可以初始化数据
     * </p>
     *
     * @param maxCapacity      最多缓存对象个数
     * @param initEdenMap      自定义临时缓存对象
     * @param initPermanentMap 永久缓存
     */
    public SimpleCache22(int maxCapacity, Map<K, V> initEdenMap, Map<K, V> initPermanentMap) {
        this.maxCapacity = maxCapacity;
        this.edenCache = initEdenMap;
        this.permanentCache = initPermanentMap;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public Map<K, V> getEdenCache() {
        return Collections.unmodifiableMap(edenCache);
    }

    public Map<K, V> getPermanentCache() {
        return Collections.unmodifiableMap(permanentCache);
    }

    /**
     * 从缓存中查找
     * 先从临时缓存中查找，如若不存在则去永久缓存中查找，如果在永久缓存中找到则放入到临时缓存中
     *
     * @param key 键
     * @return 值
     */
    public V get(K key) {
        V value;
        readLock.lock();
        try {
            value = edenCache.get(key);
            if (value != null) {
                return value;
            }
            value = permanentCache.get(key);
            if (value != null) {
                writeLock.lock();
                try {
                    edenCache.put(key, value);
                } finally {
                    writeLock.unlock();
                }
            }
            return value;
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 将值放入到临时缓存中，如果临时缓存溢出，则将临时缓存中全部取出放到永久缓存中，同时清空临时缓存
     * 新加入的缓存会放到临时缓存中
     *
     * @param key   键
     * @param value 值
     */
    public void put(K key, V value) {
        writeLock.lock();
        try {
            if (edenCache.size() >= maxCapacity) {
                permanentCache.putAll(edenCache);
                edenCache.clear();
            }
            edenCache.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 移除缓存
     *
     * @param key 键
     * @return 值
     */
    public V remove(K key) {
        writeLock.lock();
        try {
            V value = edenCache.remove(key);
            if (value == null) {
                value = permanentCache.remove(key);
            } else {
                permanentCache.remove(key);
            }
            return value;
        } finally {
            writeLock.unlock();
        }
    }

    public void clear() {
        writeLock.lock();
        try {
            edenCache.clear();
            permanentCache.clear();
        } finally {
            writeLock.unlock();
        }
    }

}
