package com.ijiagoushi.chillies.core.map;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author miles.tang at 2021-01-21
 * @since 1.0
 */
class CamelCaseHashMapTest {

    @Test
    void customKey() {
        Map<String, String> map = new CamelCaseHashMap<>();
        map.put("userPwd", "123123");
        assertEquals("123123", map.get("userPwd"));
        assertEquals("123123", map.get("user_pwd"));
    }

}