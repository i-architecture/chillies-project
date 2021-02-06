package com.ijiagoushi.chillies.core.http;

import com.ijiagoushi.chillies.core.exceptions.IoRuntimeException;
import com.ijiagoushi.chillies.core.lang.CharsetUtil;
import com.ijiagoushi.chillies.core.lang.CollectionUtil;
import com.ijiagoushi.chillies.core.lang.Preconditions;
import com.ijiagoushi.chillies.core.lang.StringUtil;
import com.ijiagoushi.chillies.core.map.MultiValueLinkedMap;
import com.ijiagoushi.chillies.core.map.MultiValueMap;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.jar.JarFile;

/**
 * URL Utilities
 *
 * @author miles.tang
 */
public class UrlUtil {

    private UrlUtil() {
        throw new AssertionError("Cannot create instance!");
    }

    /**
     * url编码
     *
     * @param text     待编码的内容
     * @param encoding 编码
     * @return 编码后的url
     */
    public static String encode(final String text, String encoding) {
        return encode(text, CharsetUtil.forName(encoding));
    }

    /**
     * url编码，字符集采用{@code UTF-8}
     *
     * @param text 待编码的内容
     * @return 编码后的url
     */
    public static String encode(final String text) {
        return encode(text, CharsetUtil.UTF_8);
    }

    /**
     * url编码，指定字符集
     *
     * @param text    待编码的内容
     * @param charset 指定字符集
     * @return 编码后的url
     */
    public static String encode(final String text, Charset charset) {
        if (StringUtil.isEmpty(text)) {
            return StringUtil.EMPTY_STRING;
        }
        if (charset == null) {
            charset = CharsetUtil.UTF_8;
        }
        try {
            return URLEncoder.encode(text, charset.name());
        } catch (UnsupportedEncodingException e) {
            //never happen
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 为url增加请求参数
     *
     * @param url   原始的请求地址
     * @param name  参数名
     * @param value 参数值
     * @return 返回追加了参数的地址
     */
    public static String addParam(String url, String name, String value) {
        return addParam(url, name, value, null);
    }

    /**
     * 为url增加请求参数
     *
     * @param url      原始的请求地址
     * @param name     参数名
     * @param value    参数值
     * @param encoding 编码
     * @return 返回追加了参数的地址
     */
    public static String addParam(String url, String name, String value, String encoding) {
        if (StringUtil.isEmpty(url)) {
            return StringUtil.EMPTY_STRING;
        }
        if (StringUtil.isEmpty(name) || StringUtil.isEmpty(value)) {
            return url;
        }
        if (encoding == null) {
            encoding = CharsetUtil.UTF_8_VALUE;
        }
        String queryString = name + "=" + encode(value, encoding);
        if (url.contains("?")) {
            return url + "&" + queryString;
        } else {
            return url + "?" + queryString;
        }
    }

    /**
     * 为url增加请求参数
     *
     * @param url    原始的请求地址
     * @param params 参数
     * @return 返回追加了参数的地址
     */
    public static String addParams(String url, Map<CharSequence, ?> params) {
        return addParams(url, params, null);
    }

    /**
     * 为url增加请求参数
     *
     * @param url      原始的请求地址
     * @param params   参数
     * @param encoding 编码
     * @return 返回追加了参数的地址
     */
    public static String addParams(String url, Map<? extends CharSequence, ?> params, String encoding) {
        if (StringUtil.isEmpty(url)) {
            return StringUtil.EMPTY_STRING;
        }
        if (CollectionUtil.isEmpty(params)) {
            return url;
        }
        if (encoding == null) {
            encoding = CharsetUtil.UTF_8_VALUE;
        }
        final String charset = encoding;

        StringBuilder queryParamBuilder = new StringBuilder();

        params.forEach((BiConsumer<CharSequence, Object>) (name, value) -> {
            if (StringUtil.isEmpty(name) || value == null) {
                return;
            }
            if (value instanceof Iterable) {
                ((Iterable<?>) value).forEach((Consumer<Object>) subValue -> queryParamBuilder
                        .append(name.toString())
                        .append("=")
                        .append(encode(subValue.toString(), charset))
                        .append("&"));
            } else {
                // 处理的不够细致
                queryParamBuilder
                        .append(name.toString())
                        .append("=")
                        .append(encode(value.toString(), charset))
                        .append("&");
            }
        });
        queryParamBuilder.deleteCharAt(queryParamBuilder.length() - 1);
        if (url.contains("?")) {
            return url + "&" + queryParamBuilder.toString();
        } else {
            return url + "?" + queryParamBuilder.toString();
        }
    }

    /**
     * URL解码，字符集采用{@code UTF-8}
     *
     * @param text 待解码的内容
     * @return 解码后的url
     */
    public static String decode(final String text) {
        return decode(text, CharsetUtil.UTF_8);
    }

    /**
     * URL解码，指定字符集
     *
     * @param text    待解码的内容
     * @param charset 指定字符集
     * @return 解码后的url
     */
    public static String decode(final String text, Charset charset) {
        if (StringUtil.isEmpty(text)) {
            return StringUtil.EMPTY_STRING;
        }
        if (charset == null) {
            charset = CharsetUtil.UTF_8;
        }
        try {
            return URLDecoder.decode(text, charset.name());
        } catch (UnsupportedEncodingException e) {
            //never happen
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 从URL中获取流
     *
     * @param url {@link URL}
     * @return InputStream流
     */
    public static InputStream openStream(URL url) {
        try {
            return Preconditions.requireNonNull(url).openStream();
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 获得URL，常用于使用绝对路径时的情况
     *
     * @param file URL对应的文件对象
     * @return URL
     * @throws IoRuntimeException MalformedURLException
     */
    public static URL getUrl(File file) {
        try {
            return Preconditions.requireNonNull(file).toURI().toURL();
        } catch (MalformedURLException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 从URL中获取JarFile
     *
     * @param url url
     * @return JarFile
     */
    public static JarFile getJarFile(URL url) {
        try {
            return ((JarURLConnection) url.openConnection()).getJarFile();
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * <p>将url中的请求参数转为Map对象</p>
     *
     * @param urlParamStr url请求参数
     * @return 返回Map对象
     */
    @NotNull
    public static MultiValueMap<String, String> parseUrlParams(@NotNull String urlParamStr) {
        MultiValueMap<String, String> multiValueMap = new MultiValueLinkedMap<>();
        if (urlParamStr.length() == 0) {
            return multiValueMap;
        }
        List<String> urlParamList = StringUtil.splitToList(urlParamStr, StringUtil.AMP);
        if (CollectionUtil.isEmpty(urlParamList)) {
            return multiValueMap;
        }

        String[] array;
        for (String urlParam : urlParamList) {
            if (StringUtil.hasText(urlParam)) {
                array = StringUtil.split(urlParam, StringUtil.EQUALS, true, true);
                if (array.length == 2) {
                    multiValueMap.add(array[0], array[1]);
                } else {
                    multiValueMap.add(array[0], null);
                }
            }
        }
        return multiValueMap;
    }

}
