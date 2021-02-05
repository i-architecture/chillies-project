package com.ijiagoushi.chillies.json.gson.deser;

import com.google.gson.*;
import com.ijiagoushi.chillies.core.date.DatePattern;
import com.ijiagoushi.chillies.core.date.ZoneOffsetConstant;
import com.ijiagoushi.chillies.core.lang.Preconditions;
import com.ijiagoushi.chillies.core.lang.StringUtil;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Deserializer for Java 8 temporal {@link LocalDateTime}s.
 *
 * @author Miles.Tang
 */
public class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {

    private DateTimeFormatter formatter;

    public LocalDateTimeDeserializer() {
        this(DatePattern.NORMAL_DATETIME_FORMATTER);
    }

    public LocalDateTimeDeserializer(DateTimeFormatter formatter) {
        this.formatter = Preconditions.requireNonNull(formatter, "formatter == null");
    }

    @Override
    public LocalDateTime deserialize(JsonElement element, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isString()) {
                String value = primitive.getAsString();
                if (StringUtil.isEmpty(value)) {
                    return null;
                }
                return LocalDateTime.parse(value, formatter);
            } else if (primitive.isNumber()) {
                return LocalDateTime.ofEpochSecond(primitive.getAsLong(), 0, ZoneOffsetConstant.BEIJING_ZONE_OFFSET);
            }
        }
        return null;
    }

    public static final LocalDateTimeDeserializer INSTANCE = new LocalDateTimeDeserializer();

}
