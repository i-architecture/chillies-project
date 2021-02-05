package com.ijiagoushi.chillies.json.gson;

import com.google.gson.Gson;
import com.ijiagoushi.chillies.json.Handler;
import com.ijiagoushi.chillies.json.JSONRuntimeException;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * 基于{@code Gson}的JSON处理器
 *
 * @author miles.tang
 */
public class GsonHandler implements Handler {

    private Gson gson;

    /**
     * 将Java对象序列化为JSON字符串
     *
     * @param src     Java对象
     * @param typeOfT 类型
     * @return JSON字符串
     * @throws JSONRuntimeException 序列化出现异常
     */
    @Override
    public String serialize(Object src, Type typeOfT) throws JSONRuntimeException {
        return gson.toJson(src, typeOfT);
    }

    /**
     * 将JSON字符串放序列化为Java对象
     *
     * @param json    JSON字符串
     * @param typeOfT Java类型
     * @return Java对象
     * @throws JSONRuntimeException 反序列化出现异常
     */
    @Override
    public <T> T deserialize(String json, Type typeOfT) throws JSONRuntimeException {
        return gson.fromJson(json, typeOfT);
    }

    @Override
    public <T> T deserialize(Reader reader, Type typeOfT) throws JSONRuntimeException {
        return null;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

}
