package com.ijiagoushi.chillies.json;

import com.ijiagoushi.chillies.core.lang.StringUtil;
import com.ijiagoushi.chillies.core.utils.ClassUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * JSON
 */
@Slf4j
public class JSON {

    private Handler handler;

    public String toJson(Object src) {
        return toJson(src, ClassUtil.getClass(src));
    }

    public String toJson(Object src, Type typeOfSrc) {
        if (src == null) {
            return StringUtil.EMPTY_STRING;
        }
        return handler.serialize(src, typeOfSrc);
    }

    @SuppressWarnings("unchecked")
    public <T> T fromJson(String json, Class<T> clazz) {
        Object obj = fromJson(json, (Type) clazz);
        return (T) obj;
    }

    public <T> T fromJson(String json, Type typeOfT) {
        if (json == null) {
            return null;
        }
        return handler.deserialize(json, typeOfT);
    }

    public <T> T fromJson(Reader reader, Type typeOfT) {
        if (reader == null) {
            return null;
        }
        return handler.deserialize(reader, typeOfT);
    }

    public JSON setHandler(Handler handler) {
        this.handler = handler;
        return this;
    }

}
