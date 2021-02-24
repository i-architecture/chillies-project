package com.ijiagoushi.chillies.http;

import com.ijiagoushi.chillies.core.lang.CollectionUtil;
import com.ijiagoushi.chillies.core.map.MultiValueLinkedMap;
import com.ijiagoushi.chillies.http.constants.HeaderName;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author miles.tang at 2021-02-07
 * @since 1.0
 */
public class HttpHeaders extends MultiValueLinkedMap<String, String> {

    public HttpHeaders() {
        super();
    }

    public HttpHeaders(@Nullable Map<String, ?> headerMap) {
        super();
        if (CollectionUtil.isNotEmpty(headerMap)) {
            headerMap.forEach((BiConsumer<String, Object>) this::append);
        }
    }

    public HttpHeaders append(@Nullable String key, @Nullable Object value) {
        if (key != null && value != null) {
            add(key, value.toString());
        }

        return this;
    }

    public HttpHeaders append(@Nullable HeaderName key, Object value) {
        if (key != null && value != null) {
            add(key.toString(), value.toString());
        }

        return this;
    }

}
