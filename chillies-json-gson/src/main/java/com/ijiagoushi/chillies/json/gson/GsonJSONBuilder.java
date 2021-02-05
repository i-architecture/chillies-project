package com.ijiagoushi.chillies.json.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ijiagoushi.chillies.core.lang.Preconditions;
import com.ijiagoushi.chillies.json.JSON;
import com.ijiagoushi.chillies.json.JSONBuilder;
import com.ijiagoushi.chillies.json.annotation.JSONProviderName;
import com.ijiagoushi.chillies.json.gson.deser.*;
import com.ijiagoushi.chillies.json.gson.ser.*;

import java.time.*;

/**
 * 基于{@code Gson}的JSON构建器
 *
 * @author miles.tang
 */
@JSONProviderName("gson")
public class GsonJSONBuilder extends JSONBuilder {

    private Gson gson;

    public GsonJSONBuilder() {
        this(createJson());
    }

    public GsonJSONBuilder(Gson gson) {
        this.gson = Preconditions.requireNonNull(gson, "gson == null");
    }

    /**
     * 构建对象
     *
     * @return {@linkplain JSONBuilder}
     */
    @Override
    public JSON build() {

        GsonHandler gsonHandler = new GsonHandler();
        gsonHandler.setGson(gson);

        JSON json = new JSON();
        json.setHandler(gsonHandler);
        return json;
    }

    /**
     * 创建支持JSR310的gson处理
     *
     * @return
     */
    public static Gson createJson() {
        GsonBuilder builder = new GsonBuilder();

        // 序列化
        builder.registerTypeAdapter(Instant.class, InstantSerializer.INSTANCE);

        builder.registerTypeAdapter(LocalDate.class, LocalDateSerializer.INSTANCE);
        builder.registerTypeAdapter(LocalDateTime.class, LocalDateTimeSerializer.INSTANCE);
        builder.registerTypeAdapter(LocalTime.class, LocalTimeSerializer.INSTANCE);
        builder.registerTypeAdapter(Year.class, YearSerializer.INSTANCE);
        builder.registerTypeAdapter(YearMonth.class, YearMonthSerializer.INSTANCE);
        builder.registerTypeAdapter(MonthDay.class, MonthDaySerializer.INSTANCE);

        builder.registerTypeAdapter(OffsetDateTime.class, OffsetDateTimeSerializer.INSTANCE);
        builder.registerTypeAdapter(OffsetTime.class, OffsetTimeSerializer.INSTANCE);

        builder.registerTypeAdapter(ZonedDateTime.class, ZonedDateTimeSerializer.INSTANCE);

        // 反序列化
        builder.registerTypeAdapter(LocalDate.class, LocalDateDeserializer.INSTANCE);
        builder.registerTypeAdapter(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE);
        builder.registerTypeAdapter(LocalTime.class, LocalTimeDeserializer.INSTANCE);

        builder.registerTypeAdapter(Year.class, YearDeserializer.INSTANCE);
        builder.registerTypeAdapter(YearMonth.class, YearMonthDeserializer.INSTANCE);
        builder.registerTypeAdapter(MonthDay.class, MonthDayDeserializer.INSTANCE);

        builder.registerTypeAdapter(OffsetTime.class, OffsetTimeDeserializer.INSTANCE);
        builder.registerTypeAdapter(ZonedDateTime.class, ZonedDateTimeDeserializer.INSTANCE);

        return builder.create();
    }

}
