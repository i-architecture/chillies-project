package com.ijiagoushi.chillies.core.map;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author miles.tang at 2021-01-21
 * @since 1.0
 */
class CaseInsensitiveHashMapTest {

    @Test
    public void check() {
        Map<String, String> map = new CaseInsensitiveHashMap<>(10);
        map.put("Author", "Miles");
        map.put("fullName", "Miles.Tang");
        assertEquals("Miles", map.get("author"));
        assertEquals("Miles", map.get("Author"));
        assertEquals("Miles", map.get("AUTHOR"));
        assertEquals("Miles.Tang", map.get("fullName"));
        assertEquals("Miles.Tang", map.get("FullName"));
    }


}