package com.ijiagoushi.chillies.core.lang;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Map工具类
 *
 * @author miles.tang at 2021-01-21
 * @since 1.0
 */
public class MapUtil {
    /**
     * Default load factor for {@link HashMap}/{@link LinkedHashMap} variants.
     *
     * @see #newHashMap(int)
     * @see #newLinkedHashMap(int)
     */
    public static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * 创建{@linkplain HashMap}
     *
     * @param expectedSize 期望的大小
     * @param <K>          键
     * @param <V>          值
     * @return HashMap实例
     */
    public static <K, V> HashMap<K, V> newHashMap(int expectedSize) {
        return new HashMap<>((int) (expectedSize / DEFAULT_LOAD_FACTOR), DEFAULT_LOAD_FACTOR);
    }

    /**
     * 创建{@linkplain LinkedHashMap}
     *
     * @param expectedSize 期望的大小
     * @param <K>          键
     * @param <V>          值
     * @return LinkedHashMap实例
     */
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(int expectedSize) {
        return new LinkedHashMap<>((int) (expectedSize / DEFAULT_LOAD_FACTOR), DEFAULT_LOAD_FACTOR);
    }

    /**
     * 获取Map指定key的值，并转换为字符串
     *
     * @param map map
     * @param key 键
     * @return 值
     */
    public static String getStr(Map<?, ?> map, Object key) {
        return get(map, key, String.class);
    }

    /**
     * 获取Map指定key的值，并转换为字符串
     *
     * @param map          map
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static String getStr(Map<?, ?> map, Object key, String defaultValue) {
        return get(map, key, String.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Integer
     *
     * @param map map
     * @param key 键
     * @return 值
     */
    public static Integer getInt(Map<?, ?> map, Object key) {
        return get(map, key, Integer.class);
    }

    /**
     * 获取Map指定key的值，并转换为Integer
     *
     * @param map          map
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static Integer getInt(Map<?, ?> map, Object key, Integer defaultValue) {
        return get(map, key, Integer.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Double
     *
     * @param map Map
     * @param key 键
     * @return 值
     */
    public static Double getDouble(Map<?, ?> map, Object key) {
        return get(map, key, Double.class);
    }


    /**
     * 获取Map指定key的值，并转换为Double
     *
     * @param map          Map
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static Double getDouble(Map<?, ?> map, Object key, Double defaultValue) {
        return get(map, key, Double.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Float
     *
     * @param map Map
     * @param key 键
     * @return 值
     */
    public static Float getFloat(Map<?, ?> map, Object key) {
        return get(map, key, Float.class);
    }

    /**
     * 获取Map指定key的值，并转换为Float
     *
     * @param map          Map
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static Float getFloat(Map<?, ?> map, Object key, Float defaultValue) {
        return get(map, key, Float.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Short
     *
     * @param map Map
     * @param key 键
     * @return 值
     * @since 5.3.11
     */
    public static Short getShort(Map<?, ?> map, Object key) {
        return get(map, key, Short.class);
    }

    /**
     * 获取Map指定key的值，并转换为Short
     *
     * @param map          Map
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static Short getShort(Map<?, ?> map, Object key, Short defaultValue) {
        return get(map, key, Short.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Bool
     *
     * @param map Map
     * @param key 键
     * @return 值
     */
    public static Boolean getBool(Map<?, ?> map, Object key) {
        return get(map, key, Boolean.class);
    }

    /**
     * 获取Map指定key的值，并转换为Bool
     *
     * @param map          Map
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static Boolean getBool(Map<?, ?> map, Object key, Boolean defaultValue) {
        return get(map, key, Boolean.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Character
     *
     * @param map Map
     * @param key 键
     * @return 值
     */
    public static Character getChar(Map<?, ?> map, Object key) {
        return get(map, key, Character.class);
    }

    /**
     * 获取Map指定key的值，并转换为Character
     *
     * @param map          Map
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static Character getChar(Map<?, ?> map, Object key, Character defaultValue) {
        return get(map, key, Character.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Byte
     *
     * @param map          Map
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static Byte getByte(Map<?, ?> map, Object key, Byte defaultValue) {
        return get(map, key, Byte.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Byte
     *
     * @param map Map
     * @param key 键
     * @return 值
     */
    public static Byte getByte(Map<?, ?> map, Object key) {
        return get(map, key, Byte.class);
    }

    /**
     * 获取Map指定key的值，并转换为Long
     *
     * @param map          Map
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static Long getLong(Map<?, ?> map, Object key, Long defaultValue) {
        return get(map, key, Long.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Long
     *
     * @param map Map
     * @param key 键
     * @return 值
     */
    public static Long getLong(Map<?, ?> map, Object key) {
        return get(map, key, Long.class);
    }

    /**
     * 获取Map指定key的值，并转换为指定类型
     *
     * @param map  map
     * @param key  键
     * @param type 值类型
     * @param <T>  目标值类型
     * @return 值
     */
    public static <T> T get(Map<?, ?> map, Object key, Class<T> type) {
        return get(map, key, type, null);
    }

    /**
     * 获取Map指定key的值，并转换为指定类型
     *
     * @param map          map
     * @param key          键
     * @param type         值类型
     * @param <T>          目标值类型
     * @param defaultValue 默认值
     * @return 值
     */
    public static <T> T get(Map<?, ?> map, Object key, Class<T> type, T defaultValue) {
        // return (null == map) ? null : ConvertUtil.convert(type, map.get(key), defaultValue);
        return null;
    }


}
