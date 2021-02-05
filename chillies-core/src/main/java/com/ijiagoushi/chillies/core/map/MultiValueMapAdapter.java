package com.ijiagoushi.chillies.core.map;

import com.ijiagoushi.chillies.core.lang.CollectionUtil;
import com.ijiagoushi.chillies.core.lang.MapUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * MultiValueMap Adapter
 *
 * @author miles.tang at 2021-01-21
 * @since 1.0
 */
public class MultiValueMapAdapter<K, V> extends MapWrapper<K, List<V>> implements MultiValueMap<K, V>, Serializable {

    /**
     * use serialVersionUID from JDK 1.0.2 for interoperability
     */
    private static final long serialVersionUID = 2021L;

    public MultiValueMapAdapter(@NotNull Map<K, List<V>> targetMap) {
        super(targetMap);
    }

    // region MultiValueMap implementation

    @Override
    public @Nullable V getFirst(K key) {
        List<V> values = raw.get(key);
        return (CollectionUtil.isNotEmpty(values)) ? values.get(0) : null;
    }


    @Override
    public @Nullable V getLast(K key) {
        List<V> values = raw.get(key);
        return (CollectionUtil.isNotEmpty(values)) ? values.get(values.size() - 1) : null;
    }

    @Override
    public void add(K key, @Nullable V value) {
        List<V> values = raw.computeIfAbsent(key, k -> new ArrayList<>(1));
        values.add(value);
    }

    @Override
    public void addAll(K key, List<? extends V> values) {
        List<V> currentValues = raw.computeIfAbsent(key, k -> new ArrayList<>(1));
        currentValues.addAll(values);
    }

    @Override
    public void addAll(MultiValueMap<K, V> values) {
        for (Entry<K, List<V>> entry : values.entrySet()) {
            addAll(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void set(K key, @Nullable V value) {
        List<V> values = new ArrayList<>(1);
        values.add(value);
        raw.put(key, values);
    }

    @Override
    public void setAll(Map<K, V> values) {
        values.forEach(this::set);
    }

    @Override
    public Map<K, V> toSingleValueMap() {
        Map<K, V> singleValueMap = MapUtil.newLinkedHashMap(raw.size());
        raw.forEach((key, values) -> {
            if (values != null && !values.isEmpty()) {
                singleValueMap.put(key, values.get(0));
            }
        });
        return singleValueMap;
    }

    // endregion

}
