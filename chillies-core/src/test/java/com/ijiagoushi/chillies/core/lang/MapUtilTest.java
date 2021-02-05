package com.ijiagoushi.chillies.core.lang;

import com.ijiagoushi.chillies.core.map.MultiValueLinkedMap;
import com.ijiagoushi.chillies.core.map.MultiValueMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author miles.tang at 2021-01-25
 * @since 1.0
 */
class MapUtilTest {

    @Test
    void newHashMap() {
    }

    @Test
    void newLinkedHashMap() {
    }

    @Test
    void fromUrlParams() {
        String urlParams = "";
        MultiValueMap<String, String> params = MapUtil.fromUrlParams(urlParams);
        assertEquals(params, new MultiValueLinkedMap<String, String>());

        urlParams = "a";
        params = MapUtil.fromUrlParams(urlParams);
        MultiValueMap<String, String> result = new MultiValueLinkedMap<>(4);
        result.add("a", null);
        assertEquals(params, result);
        System.out.println(urlParams + "\t->\t\t" + params);

        urlParams = "username=";
        params = MapUtil.fromUrlParams(urlParams);
        result = new MultiValueLinkedMap<>(4);
        result.add("username", null);
        assertEquals(params, result);
        System.out.println(urlParams + "\t->\t\t" + params);

        urlParams = "username=admin&password=&";
        params = MapUtil.fromUrlParams(urlParams);
        result = new MultiValueLinkedMap<>(4);
        result.add("username", "admin");
        result.add("password", null);
        assertEquals(params, result);
        System.out.println(urlParams + "\t->\t\t" + params);
    }

    @Test
    void getStr() {
    }

    @Test
    void testGetStr() {
    }

    @Test
    void getInt() {
    }

    @Test
    void testGetInt() {
    }

    @Test
    void getDouble() {
    }

    @Test
    void testGetDouble() {
    }

    @Test
    void getFloat() {
    }

    @Test
    void testGetFloat() {
    }

    @Test
    void getShort() {
    }

    @Test
    void testGetShort() {
    }

    @Test
    void getBool() {
    }

    @Test
    void testGetBool() {
    }

    @Test
    void getChar() {
    }

    @Test
    void testGetChar() {
    }

    @Test
    void getByte() {
    }

    @Test
    void testGetByte() {
    }

    @Test
    void getLong() {
    }

    @Test
    void testGetLong() {
    }

    @Test
    void get() {
    }

    @Test
    void testGet() {
    }
}