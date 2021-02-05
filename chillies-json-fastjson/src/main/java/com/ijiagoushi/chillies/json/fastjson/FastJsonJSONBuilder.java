package com.ijiagoushi.chillies.json.fastjson;

import com.ijiagoushi.chillies.json.JSON;
import com.ijiagoushi.chillies.json.JSONBuilder;
import com.ijiagoushi.chillies.json.annotation.JSONProviderName;

/**
 * 基于{@code Fastjson}的JSON构建
 *
 * @author miles.tang
 */
@JSONProviderName("fastjson")
public class FastJsonJSONBuilder extends JSONBuilder {

    public FastJsonJSONBuilder() {
        super();
    }

    /**
     * 构建对象
     *
     * @return {@linkplain JSONBuilder}
     */
    @Override
    public JSON build() {
        JSON json = new JSON();
        json.setHandler(new FastJsonHandler());
        return json;
    }

}
