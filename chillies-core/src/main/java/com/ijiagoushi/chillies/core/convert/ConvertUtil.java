package com.ijiagoushi.chillies.core.convert;

import com.ijiagoushi.chillies.core.lang.CharsetUtil;
import com.ijiagoushi.chillies.core.lang.HexUtil;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * Convert Util
 *
 * @author miles.tang at 2021-04-24
 * @since 1.0
 */
public class ConvertUtil {

    /**
     * 将源对象{@code value}转为字符串，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        将被转换的值
     * @param defaultValue 默认值
     * @return 字符串
     */
    public static String toStr(Object value, @Nullable String defaultValue) {
        return convertQuietly(String.class, value, defaultValue);
    }

    /**
     * 将源对象{@code value}转为字符串，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 将被转换的值
     * @return 字符串
     */
    public static String toStr(Object value) {
        return toStr(value, null);
    }

    /**
     * 转换为字符，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static Character toChar(Object value, Character defaultValue) {
        return convertQuietly(Character.class, value, defaultValue);
    }

    /**
     * 转换为字符，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Character toChar(Object value) {
        return toChar(value, null);
    }

    /**
     * 转换为byte，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static Byte toByte(Object value, Byte defaultValue) {
        return convertQuietly(Byte.class, value, defaultValue);
    }

    /**
     * 转换为byte，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Byte toByte(Object value) {
        return toByte(value, null);
    }

    /**
     * 转换为Short，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static Short toShort(Object value, Short defaultValue) {
        return convertQuietly(Short.class, value, defaultValue);
    }

    /**
     * 转换为Short，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Short toShort(Object value) {
        return toShort(value, null);
    }

    /**
     * 转换为int，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static Integer toInt(Object value, Integer defaultValue) {
        return convertQuietly(Integer.class, value, defaultValue);
    }

    /**
     * 转换为int，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Integer toInt(Object value) {
        return toInt(value, null);
    }

    /**
     * 转换为long，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static Long toLong(Object value, Long defaultValue) {
        return convertQuietly(Long.class, value, defaultValue);
    }

    /**
     * 转换为long，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Long toLong(Object value) {
        return toLong(value, null);
    }

    /**
     * 转换为double，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static Double toDouble(Object value, Double defaultValue) {
        return convertQuietly(Double.class, value, defaultValue);
    }

    /**
     * 转换为double，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Double toDouble(Object value) {
        return toDouble(value, null);
    }

    /**
     * 转换为float，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static Float toFloat(Object value, Float defaultValue) {
        return convertQuietly(Float.class, value, defaultValue);
    }

    /**
     * 转换为float，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Float toFloat(Object value) {
        return toFloat(value, null);
    }

    /**
     * 转换为boolean，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * String支持的值为：true、false、yes、ok、no，1,0<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static Boolean toBool(Object value, Boolean defaultValue) {
        return convertQuietly(Boolean.class, value, defaultValue);
    }

    /**
     * 转换为boolean，如果转换失败返回{@code null}<br>
     * String支持的值为：true、false、yes、ok、no，1,0<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Boolean toBool(Object value) {
        return toBool(value, null);
    }

    /**
     * 转换为BigInteger，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static BigInteger toBigInteger(Object value, BigInteger defaultValue) {
        return convertQuietly(BigInteger.class, value, defaultValue);
    }

    /**
     * 转换为BigInteger，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static BigInteger toBigInteger(Object value) {
        return toBigInteger(value, null);
    }

    /**
     * 转换为BigDecimal，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static BigDecimal toBigDecimal(Object value, BigDecimal defaultValue) {
        return convertQuietly(BigDecimal.class, value, defaultValue);
    }

    /**
     * 转换为BigDecimal，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static BigDecimal toBigDecimal(Object value) {
        return toBigDecimal(value, null);
    }

    /**
     * 转换为Date，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static Date toDate(Object value, Date defaultValue) {
        return convertQuietly(Date.class, value, defaultValue);
    }

    /**
     * 转换为Date，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Date toDate(Object value) {
        return toDate(value, null);
    }

    /**
     * LocalDateTime，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static LocalDateTime toLocalDateTime(Object value, LocalDateTime defaultValue) {
        return convertQuietly(LocalDateTime.class, value, defaultValue);
    }

    /**
     * LocalDateTime，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static LocalDateTime toLocalDateTime(Object value) {
        return toLocalDateTime(value, null);
    }

    /**
     * LocalDate，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static LocalDate toLocalDate(Object value, LocalDate defaultValue) {
        return convertQuietly(LocalDate.class, value, defaultValue);
    }

    /**
     * LocalDate，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static LocalDate toLocalDate(Object value) {
        return toLocalDate(value, null);
    }

    /**
     * LocalTime，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static LocalTime toLocalTime(Object value, LocalTime defaultValue) {
        return convertQuietly(LocalTime.class, value, defaultValue);
    }

    /**
     * LocalTime，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static LocalTime toLocalTime(Object value) {
        return toLocalTime(value, null);
    }

    /**
     * Instant，如果转换失败或给定的值为{@code null}时返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 默认值
     * @return 结果
     */
    public static Instant toInstant(Object value, Instant defaultValue) {
        return convertQuietly(Instant.class, value, defaultValue);
    }

    /**
     * Instant，如果转换失败返回{@code null}<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Instant toInstant(Object value) {
        return toInstant(value, null);
    }

    /**
     * 转换指定类型的值，不抛异常转换<br>
     * 当转换失败时返回默认值
     *
     * @param <T>          目标泛型
     * @param clazz        目标类型
     * @param value        值
     * @param defaultValue 默认值
     * @return 转换后的值
     */
    public static <T> T convertQuietly(Class<T> clazz, Object value, T defaultValue) {
        return doConvert(clazz, value, defaultValue, true);
    }

    /**
     * 将源值转为指定类型的值<br>
     * 当{@code quietly}为{@code true}时，如果转换时发生异常返回{@code defaultValue}<br>
     * 当转换结果为{@code null}时，返回默认值{@code defaultValue}
     *
     * @param <T>          目标泛型
     * @param clazz        目标类型
     * @param value        值
     * @param defaultValue 默认值
     * @param quietly      是否静默转换，{@code true}不抛异常
     * @return 转换后的值
     */
    public static <T> T doConvert(Class<T> clazz, Object value, T defaultValue, boolean quietly) {
        final ConverterRegistry registry = ConverterRegistry.getInstance();
        try {
            return registry.convert(value, clazz, defaultValue);
        } catch (Exception e) {
            if (quietly) {
                return defaultValue;
            }
            throw e;
        }
    }


    /**
     * 字符串转换成十六进制字符串，结果为小写
     *
     * @param str 待转换的ASCII字符串
     * @return 十六进制字符串
     * @see #toHexStr(String)
     */
    public static String toHexStr(String str) {
        return toHexStr(str, null);
    }

    /**
     * 字符串转换成十六进制字符串，结果为小写
     *
     * @param str     待转换的ASCII字符串
     * @param charset 编码
     * @return 十六进制字符串
     * @see #toHexStr(byte[])
     */
    public static String toHexStr(String str, Charset charset) {
        return toHexStr(str.getBytes(CharsetUtil.getCharset(charset, CharsetUtil.UTF_8)));
    }

    /**
     * byte数组转十六进制串
     *
     * @param bytes 被转换的byte数组
     * @return 转换后的值
     * @see HexUtil#encodeToStr(byte[])
     */
    public static String toHexStr(byte[] bytes) {
        return HexUtil.encodeToStr(bytes);
    }

    /**
     * Hex字符串转换为Byte值
     *
     * @param src 字节数组
     * @return byte[]
     * @see HexUtil#decode(String)
     */
    public static byte[] hexToBytes(String src) {
        return HexUtil.decode(src);
    }

    /**
     * 十六进制转换字符串
     *
     * @param hexStr  Byte字符串(Byte之间无分隔符 如:[616C6B])
     * @param charset 编码 {@link Charset}
     * @return 对应的字符串
     * @see HexUtil#decodeToStr(String, Charset)
     */
    public static String hexToStr(String hexStr, Charset charset) {
        return HexUtil.decodeToStr(hexStr, charset);
    }


}
