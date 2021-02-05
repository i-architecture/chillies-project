package com.ijiagoushi.chillies.json.fastjson;

import com.ijiagoushi.chillies.json.Factory;
import com.ijiagoushi.chillies.json.JSON;
import com.ijiagoushi.chillies.json.JSONUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author miles.tang at 2021-02-05
 * @since 1.0
 */
public class JSONUtilTest {

    @Test
    void fromJson() {

    }

    @Test
    public void toJsonForFastjson() {
        JSON fastjson = Factory.create("fastjson").build();
        Map<String, Object> beanMap = new HashMap<>();
        String jsonStr = JSONUtil.toJson(beanMap, fastjson);
        Assertions.assertEquals("{}", jsonStr);
    }

    @Test
    public void toJsonForGson() {
        JSON fastjson = Factory.create("gson").build();
        Map<String, Object> beanMap = new HashMap<>();
        String jsonStr = JSONUtil.toJson(beanMap, fastjson);
        Assertions.assertEquals("{}", jsonStr);
    }

    @Test
    public void toJsonForJackson() {
        JSON fastjson = Factory.create("jackson").build();
        Map<String, Object> beanMap = new HashMap<>();
        String jsonStr = JSONUtil.toJson(beanMap, fastjson);
        Assertions.assertEquals("{}", jsonStr);
    }

}