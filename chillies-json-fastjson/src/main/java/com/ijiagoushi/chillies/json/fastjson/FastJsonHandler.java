package com.ijiagoushi.chillies.json.fastjson;

import com.alibaba.fastjson.JSON;
import com.ijiagoushi.chillies.json.Handler;
import com.ijiagoushi.chillies.json.JSONRuntimeException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * 基于{@code Fastjson}的JSON处理器
 */
public class FastJsonHandler implements Handler {

    /**
     * 将Java对象序列化为JSON字符串
     *
     * @param src     Java对象
     * @param typeOfT 类型
     * @return JSON字符串
     * @throws JSONRuntimeException 序列化出现异常
     */
    @Override

    public String serialize(@NotNull Object src, @Nullable Type typeOfT) throws JSONRuntimeException {
        return JSON.toJSONString(src);
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
        return JSON.parseObject(json, typeOfT);
    }

    @Override
    public <T> T deserialize(Reader reader, Type typeOfT) throws JSONRuntimeException {
        return null;
    }

}
