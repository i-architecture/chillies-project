package com.ijiagoushi.chillies.core.map;

import com.ijiagoushi.chillies.core.exceptions.CloneRuntimeException;
import com.ijiagoushi.chillies.core.lang.MapUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 采用{@linkplain java.util.LinkedHashMap}实现{@linkplain MultiValueMap}
 *
 * @author miles.tang at 2021-01-21
 * @since 1.0
 */
public class MultiValueLinkedMap<K, V> extends MultiValueMapAdapter<K, V> {

    /**
     * use serialVersionUID from JDK 1.0.2 for interoperability
     */
    private static final long serialVersionUID = 2021L;

    public MultiValueLinkedMap() {
        super(new LinkedHashMap<>());
    }

    public MultiValueLinkedMap(int expectedSize) {
        super(MapUtil.newLinkedHashMap(expectedSize));
    }

    public MultiValueLinkedMap(Map<K, List<V>> otherMap) {
        super(new LinkedHashMap<>(otherMap));
    }


    /**
     * Create a deep copy of this Map.
     *
     * @return a copy of this Map, including a copy of each value-holding List entry
     * (consistently using an independent modifiable {@link ArrayList} for each entry)
     * along the lines of {@code MultiValueMap.addAll} semantics
     * @see #addAll(MultiValueMap)
     * @see #clone()
     * @since 4.2
     */
    public MultiValueLinkedMap<K, V> deepCopy() {
        MultiValueLinkedMap<K, V> copy = new MultiValueLinkedMap<>(size());
        forEach((key, values) -> copy.put(key, new ArrayList<>(values)));
        return copy;
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public MapWrapper<K, List<V>> clone() throws CloneRuntimeException {
        return new MultiValueLinkedMap<>(this);
    }

}
