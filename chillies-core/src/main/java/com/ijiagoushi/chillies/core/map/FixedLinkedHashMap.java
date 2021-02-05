package com.ijiagoushi.chillies.core.map;

import com.ijiagoushi.chillies.core.lang.Preconditions;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 固定大小的{@linkplain LinkedHashMap}实现
 *
 * @author miles.tang at 2021-01-21
 * @since 1.0
 */
public class FixedLinkedHashMap<K, V> extends LinkedHashMap<K, V> {

    /**
     * 最大容量
     */
    private final int maxCapacity;

    public FixedLinkedHashMap(int maxCapacity) {
        this(MapWrapper.DEFAULT_INITIAL_CAPACITY, maxCapacity);
    }

    public FixedLinkedHashMap(int initialCapacity, int maxCapacity) {
        this(initialCapacity, maxCapacity, MapWrapper.DEFAULT_LOAD_FACTOR);
    }

    public FixedLinkedHashMap(int initialCapacity, int maxCapacity, float loadFactor) {
        super(initialCapacity, loadFactor, true);
        Preconditions.checkArgument(maxCapacity > 0, "maxCapacity <= 0");
        this.maxCapacity = maxCapacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        // 当链表元素大于容量时，移除最老（最久未被使用）的元素
        return size() > this.maxCapacity;
    }
}
