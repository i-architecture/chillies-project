package com.ijiagoushi.chillies.core.http;

import com.ijiagoushi.chillies.core.map.MultiValueLinkedMap;
import com.ijiagoushi.chillies.core.map.MultiValueMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author miles.tang at 2021-02-05
 * @since 1.0
 */
public class UrlUtilTest {

    @Test
    public void encode() {
    }

    @Test
    public void testEncode() {
    }

    @Test
    public void testEncode1() {
    }

    @Test
    public void addParam() {
    }

    @Test
    public void testAddParam() {
    }

    @Test
    public void addParams() {
    }

    @Test
    public void testAddParams() {
    }

    @Test
    public void decode() {
    }

    @Test
    public void testDecode() {
    }

    @Test
    public void openStream() {
    }

    @Test
    public void getUrl() {
    }

    @Test
    public void getJarFile() {
    }

    @Test
    public void fromUrlParams() {
        String urlParams = "";
        MultiValueMap<String, String> params = UrlUtil.parseByUrlQueryString(urlParams);
        assertEquals(params, new MultiValueLinkedMap<String, String>());

        urlParams = "a";
        params = UrlUtil.parseByUrlQueryString(urlParams);
        MultiValueMap<String, String> result = new MultiValueLinkedMap<>(4);
        result.add("a", null);
        assertEquals(params, result);
        System.out.println(urlParams + "\t->\t\t" + params);

        urlParams = "username=";
        params = UrlUtil.parseByUrlQueryString(urlParams);
        result = new MultiValueLinkedMap<>(4);
        result.add("username", null);
        assertEquals(params, result);
        System.out.println(urlParams + "\t->\t\t" + params);

        urlParams = "username=admin&password=&";
        params = UrlUtil.parseByUrlQueryString(urlParams);
        result = new MultiValueLinkedMap<>(4);
        result.add("username", "admin");
        result.add("password", null);
        assertEquals(params, result);
        System.out.println(urlParams + "\t->\t\t" + params);
    }

}