package com.ijiagoushi.chillies.json.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ijiagoushi.chillies.json.Handler;
import com.ijiagoushi.chillies.json.JSONRuntimeException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Jackson Handler
 *
 * @author miles.tang
 */
public class JacksonHandler implements Handler {

    private ObjectMapper objectMapper;

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
        try {
            return objectMapper.writeValueAsString(src);
        } catch (JsonProcessingException e) {
            throw new JSONRuntimeException(e);
        }
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
        try {
            if (JacksonUtil.isJacksonJavaType(typeOfT)) {
                return objectMapper.readValue(json, JacksonUtil.toJavaType(typeOfT));
            }
            // is primitive ?

            if (JacksonUtil.isClass(typeOfT)) {
                return objectMapper.readValue(json, objectMapper.getTypeFactory().constructType(JacksonUtil.toClass(typeOfT)));
            }

            if (JacksonUtil.isParameterizedType(typeOfT)) {
                ParameterizedType pType = (ParameterizedType) typeOfT;
                Class<?> parametrized = JacksonUtil.toClass(pType.getRawType());
                Type[] parameterTypes = pType.getActualTypeArguments();
                Class<?>[] parameterClasses = new Class[parameterTypes.length];
                for (int i = 0; i < parameterTypes.length; i++) {
                    parameterClasses[i] = JacksonUtil.toClass(parameterTypes[i]);
                }
                return objectMapper.readValue(json, objectMapper.getTypeFactory().constructParametricType(parametrized, parameterClasses));
            }
            return null;
        } catch (IOException e) {
            throw new JSONRuntimeException(e);
        }
    }

    @Override
    public <T> T deserialize(Reader reader, Type typeOfT) throws JSONRuntimeException {
        return null;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

}
