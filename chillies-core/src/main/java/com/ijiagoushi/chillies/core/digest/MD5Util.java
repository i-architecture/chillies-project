package com.ijiagoushi.chillies.core.digest;

import com.ijiagoushi.chillies.core.lang.Base64Util;
import com.ijiagoushi.chillies.core.lang.CharsetUtil;
import com.ijiagoushi.chillies.core.lang.HexUtil;
import com.ijiagoushi.chillies.core.lang.Preconditions;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * MD5 Message Digest Utilities
 *
 * @author miles.tang
 */
public class MD5Util {

    private MD5Util() {
        throw new AssertionError("Cannot create instance!");
    }

    /**
     * md5计算
     *
     * @param data 待计算的数据
     * @return md5计算结果
     */
    public static byte[] digest(final byte[] data) {
        return Digest.doDigest(Digest.md5(), Preconditions.requireNonNull(data));
    }

    /**
     * 将输入流的数据进行MD5计算
     *
     * @param data 待计算的数据
     * @return md5计算结果
     */
    public static byte[] digest(final InputStream data) {
        return Digest.doDigest(Digest.md5(), Preconditions.requireNonNull(data));
    }

    /**
     * 计算字符串的md5值，结果转为16进制的小写字符串返回,默认采用{@link CharsetUtil#defaultCharset()}编码
     *
     * @param data 待计算的数据
     * @return 16进制的字符串
     */
    public static String digestHex(final String data) {
        return digestHex(data, null);
    }

    /**
     * 计算字符串的md5值，结果转为16进制的大写字符串返回,默认采用{@link CharsetUtil#defaultCharset()}编码
     *
     * @param data 待计算的数据
     * @return 16进制的字符串
     */
    public static String digestUpperHex(final String data) {
        return digestUpperHex(data, null);
    }

    /**
     * 计算字符串的md5值，结果转为16进制的小写字符串返回
     *
     * @param data          待计算的数据
     * @param inputEncoding 编码
     * @return 16进制的字符串
     */
    public static String digestHex(final String data, final Charset inputEncoding) {
        byte[] input = Preconditions.requireNonNull(data).getBytes(CharsetUtil.getCharset(inputEncoding));
        return HexUtil.encodeToStr(Digest.doDigest(Digest.md5(), input));
    }

    /**
     * 计算字符串的md5值，结果转为16进制的大写字符串返回
     *
     * @param data          待计算的数据
     * @param inputEncoding 编码
     * @return 16进制的字符串
     */
    public static String digestUpperHex(final String data, final Charset inputEncoding) {
        byte[] input = Preconditions.requireNonNull(data).getBytes(CharsetUtil.getCharset(inputEncoding));
        return HexUtil.encodeToStr(Digest.doDigest(Digest.md5(), input), false);
    }

    /**
     * 计算的流的md5值，结果转为16进制的字符串返回
     *
     * @param data 待计算的数据
     * @return 16进制的字符串
     */
    public static String digestHex(final InputStream data) {
        return HexUtil.encodeToStr(digest(data));
    }

    /**
     * 计算字符串(默认UTF8)的MD5值，将结果转为BASE64输出
     *
     * @param data 待计算的数据
     * @return BASE64的字符串
     */
    public static String digestBase64(final String data) {
        return digestBase64(data, null);
    }

    /**
     * 计算字符串的MD5值，将结果转为BASE64输出
     *
     * @param data           待计算的数据
     * @param outputEncoding 字符串编码
     * @return BASE64的字符串
     */
    public static String digestBase64(final String data, final Charset outputEncoding) {
        byte[] input = Preconditions.requireNonNull(data).getBytes(CharsetUtil.getCharset(outputEncoding));
        return Base64Util.encode(Digest.doDigest(Digest.md5(), input));
    }

}
