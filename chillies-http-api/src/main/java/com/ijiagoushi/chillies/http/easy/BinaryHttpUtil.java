package com.ijiagoushi.chillies.http.easy;

import com.ijiagoushi.chillies.core.http.ContentType;
import com.ijiagoushi.chillies.core.io.FileUtil;
import com.ijiagoushi.chillies.core.io.IOUtil;
import com.ijiagoushi.chillies.core.lang.CharsetUtil;
import com.ijiagoushi.chillies.core.lang.StringUtil;
import com.ijiagoushi.chillies.http.*;
import com.ijiagoushi.chillies.http.exceptions.HttpClientStatusException;
import com.ijiagoushi.chillies.json.JSONUtil;
import com.ijiagoushi.chillies.json.TypeRef;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author miles.tang at 2021-02-07
 * @since 1.0
 */
public class BinaryHttpUtil {

    // region BinaryBody POST

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url 请求地址
     * @return 响应结果
     */
    public static String execute(@NotNull String url) {
        return execute(url, (Charset) null);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param responseEncoding 响应内容编码
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable Charset responseEncoding) {
        return execute(url, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @NotNull HttpBodyMethod bodyMethod) {
        return execute(url, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param responseEncoding 响应内容编码
     * @param bodyMethod       HTTP请求方式
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @NotNull HttpBodyMethod bodyMethod,
                                 @Nullable Charset responseEncoding) {
        return execute(url, (String) null, bodyMethod, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url     请求地址
     * @param content body内容
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable String content) {
        return execute(url, content, (Charset) null);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param content          body内容
     * @param responseEncoding 响应内容编码
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable String content, @Nullable Charset responseEncoding) {
        return execute(url, content, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param content    body内容
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable String content, @NotNull HttpBodyMethod bodyMethod) {
        return execute(url, content, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param content          body内容
     * @param responseEncoding 响应内容编码
     * @param bodyMethod       HTTP请求方式
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable String content, @NotNull HttpBodyMethod bodyMethod,
                                 @Nullable Charset responseEncoding) {
        return execute(url, content, null, bodyMethod, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url      请求地址
     * @param content  body内容
     * @param queryMap URL参数
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable String content, @Nullable Map<String, ?> queryMap) {
        return execute(url, content, queryMap, (Charset) null);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param content          body内容
     * @param queryMap         URL参数
     * @param responseEncoding 响应内容编码
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable String content, @Nullable Map<String, ?> queryMap,
                                 @Nullable Charset responseEncoding) {
        return execute(url, content, queryMap, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param content    body内容
     * @param queryMap   URL参数
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable String content, Map<String, ?> queryMap,
                                 @NotNull HttpBodyMethod bodyMethod) {
        return execute(url, content, queryMap, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param content          body内容
     * @param queryMap         URL参数
     * @param responseEncoding 响应内容编码
     * @param bodyMethod       HTTP请求方式
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable String content, @Nullable Map<String, ?> queryMap,
                                 @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding) {
        return execute(url, content, queryMap, null, bodyMethod, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url       请求地址
     * @param content   body内容
     * @param queryMap  URL参数
     * @param headers HTTP头参数
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable String content, @Nullable Map<String, ?> queryMap,
                                 @Nullable HttpHeaders headers) {
        return execute(url, content, queryMap, headers, HttpBodyMethod.POST);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param content          body内容
     * @param queryMap         URL参数
     * @param headers          HTTP头参数
     * @param responseEncoding 响应内容编码
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable String content, Map<String, ?> queryMap,
                                 @Nullable HttpHeaders headers, @Nullable Charset responseEncoding) {
        return execute(url, content, queryMap, headers, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param content    body内容
     * @param queryMap   URL参数
     * @param headers  HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable String content, @Nullable Map<String, ?> queryMap,
                                 @Nullable HttpHeaders headers, @NotNull HttpBodyMethod bodyMethod) {
        return execute(url, content, queryMap, headers, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param content          body内容
     * @param queryMap         URL参数
     * @param headers        HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 响应内容编码
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable String content, @Nullable Map<String, ?> queryMap,
                                 @Nullable HttpHeaders headers, @NotNull HttpBodyMethod bodyMethod,
                                 @Nullable Charset responseEncoding) {
        InputStream in = null;
        if (content != null) {
            in = new ByteArrayInputStream(content.getBytes(CharsetUtil.UTF_8));
        }
        return execute(url, ContentType.DEFAULT_BINARY, in, queryMap, headers, bodyMethod, responseEncoding);
    }


    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url     请求地址
     * @param content 文件
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable File content) {
        return execute(url, content, HttpBodyMethod.POST);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param content          文件
     * @param responseEncoding 响应内容编码
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable File content, @Nullable Charset responseEncoding) {
        return execute(url, content, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param content    文件
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable File content, @NotNull HttpBodyMethod bodyMethod) {
        return execute(url, content, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param content          文件
     * @param responseEncoding 指定响应内容的编码
     * @param bodyMethod       HTTP请求方式
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable File content, @NotNull HttpBodyMethod bodyMethod,
                                 @Nullable Charset responseEncoding) {
        return execute(url, content, null, bodyMethod, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url      请求地址
     * @param content  文件
     * @param queryMap URL参数
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable File content, Map<String, ?> queryMap) {
        return execute(url, content, queryMap, HttpBodyMethod.POST);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param content          文件
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable File content, @Nullable Map<String, ?> queryMap,
                                 @Nullable Charset responseEncoding) {
        return execute(url, content, queryMap, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param content    文件
     * @param queryMap   URL参数
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable File content, Map<String, ?> queryMap,
                                 @NotNull HttpBodyMethod bodyMethod) {
        return execute(url, content, queryMap, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param content          文件
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @param bodyMethod       HTTP请求方式
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable File content, @Nullable Map<String, ?> queryMap,
                                 @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding) {
        return execute(url, content, queryMap, null, bodyMethod, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url       请求地址
     * @param content   文件
     * @param queryMap  URL参数
     * @param headers HTTP头参数
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable File content, Map<String, ?> queryMap,
                                 @Nullable HttpHeaders headers) {
        return execute(url, content, queryMap, headers, HttpBodyMethod.POST);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param content          文件
     * @param queryMap         URL参数
     * @param headers        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable File content, Map<String, ?> queryMap,
                                 @Nullable HttpHeaders headers, @Nullable Charset responseEncoding) {
        return execute(url, content, queryMap, headers, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param content    文件
     * @param queryMap   URL参数
     * @param headers  HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable File content, @Nullable Map<String, ?> queryMap,
                                 @Nullable HttpHeaders headers, @NotNull HttpBodyMethod bodyMethod) {
        return execute(url, content, queryMap, headers, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param content          文件
     * @param queryMap         URL参数
     * @param headers        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param bodyMethod       HTTP请求方式
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable File content, @Nullable Map<String, ?> queryMap,
                                 @Nullable HttpHeaders headers, @NotNull HttpBodyMethod bodyMethod,
                                 @Nullable Charset responseEncoding) {
        FileInputStream in = null;
        try {
            ContentType contentType = ContentType.DEFAULT_BINARY;
            if (content != null) {
                in = FileUtil.openFileInputStream(content);
                contentType = ContentType.parseByFileExt(FileUtil.getFileExt(content));
            }
            return execute(url, contentType, in, queryMap, headers, bodyMethod, responseEncoding);
        } finally {
            IOUtil.closeQuietly(in);
        }
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url     请求地址
     * @param content 输入流，不会自动关闭
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content) {
        return execute(url, contentType, content, (Charset) null);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param content          输入流，不会自动关闭
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                 @Nullable Charset responseEncoding) {
        return execute(url, contentType, content, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param content    输入流，不会自动关闭
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                 @NotNull HttpBodyMethod bodyMethod) {
        return execute(url, contentType, content, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param content          输入流，不会自动关闭
     * @param responseEncoding 指定响应内容的编码
     * @param bodyMethod       HTTP请求方式
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                 @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding) {
        return execute(url, contentType, content, null, bodyMethod, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url      请求地址
     * @param content  输入流，不会自动关闭
     * @param queryMap URL参数
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                 Map<String, ?> queryMap) {
        return execute(url, contentType, content, queryMap, (Charset) null);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param content          输入流，不会自动关闭
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                 @Nullable Map<String, ?> queryMap, @Nullable Charset responseEncoding) {
        return execute(url, contentType, content, queryMap, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param content    输入流，不会自动关闭
     * @param queryMap   URL参数
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                 Map<String, ?> queryMap, @NotNull HttpBodyMethod bodyMethod) {
        return execute(url, contentType, content, queryMap, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param content          输入流，不会自动关闭
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @param bodyMethod       HTTP请求方式
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                 @Nullable Map<String, ?> queryMap, @NotNull HttpBodyMethod bodyMethod,
                                 @Nullable Charset responseEncoding) {
        return execute(url, contentType, content, queryMap, null, bodyMethod, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url       请求地址
     * @param content   输入流，不会自动关闭
     * @param queryMap  URL参数
     * @param headers HTTP头参数
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                 Map<String, ?> queryMap, @Nullable HttpHeaders headers) {
        return execute(url, contentType, content, queryMap, headers, (Charset) null);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param content          输入流，不会自动关闭
     * @param queryMap         URL参数
     * @param headers        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                 Map<String, ?> queryMap, @Nullable HttpHeaders headers,
                                 @Nullable Charset responseEncoding) {
        return execute(url, contentType, content, queryMap, headers, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param content    输入流，不会自动关闭
     * @param queryMap   URL参数
     * @param headers  HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                 @Nullable Map<String, ?> queryMap, @Nullable HttpHeaders headers,
                                 @NotNull HttpBodyMethod bodyMethod) {
        return execute(url, contentType, content, queryMap, headers, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param content          输入流，不会自动关闭
     * @param queryMap         URL参数
     * @param headers        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param bodyMethod       HTTP请求方式
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                 @Nullable Map<String, ?> queryMap, @Nullable HttpHeaders headers,
                                 @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding) {
        HttpClient httpClient = Factory.get().build(null);

        HttpRequestBody requestBody = null;
        if (content != null) {
            requestBody = HttpRequestBody.create(contentType, content);
        }

        HttpRequest httpRequest = HttpRequest.builder()
                .url(url)
                .queryParams(queryMap)
                .headers(headers)
                .method(bodyMethod.toHttpMethod())
                .body(requestBody)
                .build();
        HttpResponse httpResponse = httpClient.execute(httpRequest, null);
        if (httpResponse.status() >= 200 && httpResponse.status() < 300) {
            HttpResponseBody httpResponseBody = httpResponse.body();
            return httpResponseBody.string(responseEncoding);
        }
        throw new HttpClientStatusException(httpResponse.status(), httpResponse.reason(), httpRequest);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url   请求地址
     * @param clazz 类型
     * @param <T>   泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @NotNull Class<T> clazz) {
        return execute(url, (Charset) null, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return execute(url, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param clazz      类型
     * @param bodyMethod HTTP请求方式
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @NotNull Class<T> clazz, @NotNull HttpBodyMethod bodyMethod) {
        return execute(url, bodyMethod, (Charset) null, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param bodyMethod       HTTP请求方式
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @NotNull HttpBodyMethod bodyMethod,
                                @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return execute(url, (String) null, bodyMethod, responseEncoding, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url     请求地址
     * @param content Body内容
     * @param clazz   类型
     * @param <T>     泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable String content, @NotNull Class<T> clazz) {
        return execute(url, content, (Charset) null, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          Body内容
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable String content, @Nullable Charset responseEncoding,
                                @NotNull Class<T> clazz) {
        return execute(url, content, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param content    Body内容
     * @param clazz      类型
     * @param bodyMethod HTTP请求方式
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable String content, @NotNull Class<T> clazz,
                                @NotNull HttpBodyMethod bodyMethod) {
        return execute(url, content, bodyMethod, (Charset) null, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          Body内容
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param bodyMethod       HTTP请求方式
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable String content, @NotNull HttpBodyMethod bodyMethod,
                                @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return execute(url, content, null, bodyMethod, responseEncoding, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url      请求地址
     * @param content  Body内容
     * @param queryMap URL参数
     * @param clazz    类型
     * @param <T>      泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable String content, Map<String, ?> queryMap,
                                @NotNull Class<T> clazz) {
        return execute(url, content, queryMap, (Charset) null, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          Body内容
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable String content, Map<String, ?> queryMap,
                                @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return execute(url, content, queryMap, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param content    Body内容
     * @param queryMap   URL参数
     * @param clazz      类型
     * @param bodyMethod HTTP请求方式
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable String content, Map<String, ?> queryMap,
                                @NotNull HttpBodyMethod bodyMethod, @NotNull Class<T> clazz) {
        return execute(url, content, queryMap, bodyMethod, (Charset) null, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          Body内容
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param bodyMethod       HTTP请求方式
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable String content, @Nullable Map<String, ?> queryMap,
                                @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return execute(url, content, queryMap, null, bodyMethod, responseEncoding, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url       请求地址
     * @param content   Body内容
     * @param queryMap  URL参数
     * @param headers HTTP头参数
     * @param clazz     类型
     * @param <T>       泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable String content, Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @NotNull Class<T> clazz) {
        return execute(url, content, queryMap, headers, (Charset) null, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          Body内容
     * @param queryMap         URL参数
     * @param headers        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable String content, Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @Nullable Charset responseEncoding,
                                @NotNull Class<T> clazz) {
        return execute(url, content, queryMap, headers, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param content    Body内容
     * @param queryMap   URL参数
     * @param headers  HTTP头参数
     * @param clazz      类型
     * @param bodyMethod HTTP请求方式
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable String content, @Nullable Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @NotNull HttpBodyMethod bodyMethod, @NotNull Class<T> clazz) {
        return execute(url, content, queryMap, headers, bodyMethod, null, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          Body内容
     * @param queryMap         URL参数
     * @param headers        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param bodyMethod       HTTP请求方式
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable String content, @Nullable Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @NotNull HttpBodyMethod bodyMethod,
                                @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        InputStream in = null;
        ContentType contentType = ContentType.DEFAULT_BINARY;
        try {
            if (content != null) {
                in = new ByteArrayInputStream(content.getBytes(CharsetUtil.UTF_8));
            }
            return execute(url, contentType, in, queryMap, headers, bodyMethod, responseEncoding, clazz);
        } finally {
            IOUtil.closeQuietly(in);
        }
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url     请求地址
     * @param content Body内容
     * @param clazz   类型
     * @param <T>     泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable File content, @NotNull Class<T> clazz) {
        return execute(url, content, (Charset) null, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          Body内容
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable File content, @Nullable Charset responseEncoding,
                                @NotNull Class<T> clazz) {
        return execute(url, content, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param content    Body内容
     * @param clazz      类型
     * @param bodyMethod HTTP请求方式
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable File content, @NotNull HttpBodyMethod bodyMethod,
                                @NotNull Class<T> clazz) {
        return execute(url, content, bodyMethod, (Charset) null, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          Body内容
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable File content, @NotNull HttpBodyMethod bodyMethod,
                                @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return execute(url, content, null, bodyMethod, responseEncoding, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url      请求地址
     * @param content  Body内容
     * @param queryMap URL参数
     * @param clazz    类型
     * @param <T>      泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable File content, Map<String, ?> queryMap, @NotNull Class<T> clazz) {
        return execute(url, content, queryMap, (Charset) null, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          Body内容
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable File content, @Nullable Map<String, ?> queryMap,
                                @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return execute(url, content, queryMap, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param content    Body内容
     * @param queryMap   URL参数
     * @param clazz      类型
     * @param bodyMethod HTTP请求方式
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable File content, Map<String, ?> queryMap,
                                @NotNull HttpBodyMethod bodyMethod, @NotNull Class<T> clazz) {
        return execute(url, content, queryMap, bodyMethod, (Charset) null, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          Body内容
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param bodyMethod       HTTP请求方式
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable File content, @Nullable Map<String, ?> queryMap,
                                @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return execute(url, content, queryMap, null, bodyMethod, responseEncoding, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url       请求地址
     * @param content   Body内容
     * @param queryMap  URL参数
     * @param headers HTTP头参数
     * @param clazz     类型
     * @param <T>       泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable File content, Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @NotNull Class<T> clazz) {
        return execute(url, content, queryMap, headers, HttpBodyMethod.POST, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param content    Body内容
     * @param queryMap   URL参数
     * @param headers  HTTP头参数
     * @param clazz      类型
     * @param bodyMethod HTTP请求方式
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable File content, @Nullable Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @NotNull HttpBodyMethod bodyMethod,
                                @NotNull Class<T> clazz) {
        return execute(url, content, queryMap, headers, bodyMethod, null, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          Body内容
     * @param queryMap         URL参数
     * @param headers        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable File content, Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @Nullable Charset responseEncoding,
                                @NotNull Class<T> clazz) {
        return execute(url, content, queryMap, headers, HttpBodyMethod.POST, responseEncoding, clazz);
    }


    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          Body内容
     * @param queryMap         URL参数
     * @param headers        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param bodyMethod       HTTP请求方式
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable File content, @Nullable Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @NotNull HttpBodyMethod bodyMethod,
                                @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        FileInputStream in = null;
        ContentType contentType = ContentType.DEFAULT_BINARY;
        try {
            if (content != null) {
                in = FileUtil.openFileInputStream(content);
                contentType = ContentType.parseByFileExt(FileUtil.getFileExt(content));
            }
            return execute(url, contentType, in, queryMap, headers, bodyMethod, responseEncoding, clazz);
        } finally {
            IOUtil.closeQuietly(in);
        }
    }


    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url     请求地址
     * @param content BODY输入流，不会自动关闭流
     * @param clazz   类型
     * @param <T>     泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                @NotNull Class<T> clazz) {
        return execute(url, contentType, content, (Charset) null, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return execute(url, contentType, content, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param content    BODY输入流，不会自动关闭流
     * @param clazz      类型
     * @param bodyMethod HTTP请求方式
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                @NotNull HttpBodyMethod bodyMethod, @NotNull Class<T> clazz) {
        return execute(url, contentType, content, bodyMethod, (Charset) null, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param bodyMethod       HTTP请求方式
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return execute(url, contentType, content, null, bodyMethod, responseEncoding, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url      请求地址
     * @param content  BODY输入流，不会自动关闭流
     * @param queryMap URL参数
     * @param clazz    类型
     * @param <T>      泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                Map<String, ?> queryMap, @NotNull Class<T> clazz) {
        return execute(url, contentType, content, queryMap, (Charset) null, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                @Nullable Map<String, ?> queryMap, @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return execute(url, contentType, content, queryMap, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param content    BODY输入流，不会自动关闭流
     * @param queryMap   URL参数
     * @param clazz      类型
     * @param bodyMethod HTTP请求方式
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                Map<String, ?> queryMap, @NotNull HttpBodyMethod bodyMethod, @NotNull Class<T> clazz) {
        return execute(url, contentType, content, queryMap, bodyMethod, (Charset) null, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param queryMap         URL参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                @Nullable Map<String, ?> queryMap, @NotNull HttpBodyMethod bodyMethod,
                                @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return execute(url, contentType, content, queryMap, null, bodyMethod, responseEncoding, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url       请求地址
     * @param content   BODY输入流，不会自动关闭流
     * @param queryMap  URL参数
     * @param headers HTTP头参数
     * @param clazz     类型
     * @param <T>       泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                Map<String, ?> queryMap, @Nullable HttpHeaders headers, @NotNull Class<T> clazz) {
        return execute(url, contentType, content, queryMap, headers, (Charset) null, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param queryMap         URL参数
     * @param headers        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                Map<String, ?> queryMap, @Nullable HttpHeaders headers,
                                @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return execute(url, contentType, content, queryMap, headers, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param content    BODY输入流，不会自动关闭流
     * @param queryMap   URL参数
     * @param headers  HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @param clazz      类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                @Nullable Map<String, ?> queryMap, @Nullable HttpHeaders headers,
                                @NotNull HttpBodyMethod bodyMethod, @NotNull Class<T> clazz) {
        return execute(url, contentType, content, queryMap, headers, bodyMethod, null, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param queryMap         URL参数
     * @param headers        HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param clazz            类型
     * @param responseEncoding 指定响应内容的编码
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                @Nullable Map<String, ?> queryMap, @Nullable HttpHeaders headers,
                                @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        String text = execute(url, contentType, content, queryMap, headers, bodyMethod, responseEncoding);
        return StringUtil.isEmpty(text) ? null : JSONUtil.fromJson(text, clazz);
    }


    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url     请求地址
     * @param typeRef 类型
     * @param <T>     泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @NotNull TypeRef<T> typeRef) {
        return execute(url, (Charset) null, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return execute(url, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @NotNull HttpBodyMethod bodyMethod, @NotNull TypeRef<T> typeRef) {
        return execute(url, bodyMethod, (Charset) null, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @NotNull HttpBodyMethod bodyMethod,
                                @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return execute(url, (String) null, bodyMethod, responseEncoding, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url     请求地址
     * @param content BODY输入流，不会自动关闭流
     * @param typeRef 类型
     * @param <T>     泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable String content, @NotNull TypeRef<T> typeRef) {
        return execute(url, content, (Charset) null, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable String content, @Nullable Charset responseEncoding,
                                @NotNull TypeRef<T> typeRef) {
        return execute(url, content, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param content    BODY输入流，不会自动关闭流
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable String content, @NotNull HttpBodyMethod bodyMethod,
                                @NotNull TypeRef<T> typeRef) {
        return execute(url, content, bodyMethod, (Charset) null, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable String content, @NotNull HttpBodyMethod bodyMethod,
                                @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return execute(url, content, null, bodyMethod, responseEncoding, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url      请求地址
     * @param content  BODY输入流，不会自动关闭流
     * @param queryMap URL参数
     * @param typeRef  类型
     * @param <T>      泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable String content, Map<String, ?> queryMap,
                                @NotNull TypeRef<T> typeRef) {
        return execute(url, content, queryMap, (Charset) null, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable String content, @Nullable Map<String, ?> queryMap,
                                @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return execute(url, content, queryMap, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param content    BODY输入流，不会自动关闭流
     * @param queryMap   URL参数
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable String content, Map<String, ?> queryMap,
                                @NotNull HttpBodyMethod bodyMethod, @NotNull TypeRef<T> typeRef) {
        return execute(url, content, queryMap, bodyMethod, null, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param queryMap         URL参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable String content, @Nullable Map<String, ?> queryMap,
                                @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding,
                                @NotNull TypeRef<T> typeRef) {
        return execute(url, content, queryMap, null, bodyMethod, responseEncoding, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url       请求地址
     * @param content   BODY输入流，不会自动关闭流
     * @param queryMap  URL参数
     * @param headers HTTP头参数
     * @param typeRef   类型
     * @param <T>       泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable String content, Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @NotNull TypeRef<T> typeRef) {
        return execute(url, content, queryMap, headers, HttpBodyMethod.POST, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param content    BODY输入流，不会自动关闭流
     * @param queryMap   URL参数
     * @param headers  HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable String content, @Nullable Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @NotNull HttpBodyMethod bodyMethod,
                                @NotNull TypeRef<T> typeRef) {
        return execute(url, content, queryMap, headers, bodyMethod, null, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param queryMap         URL参数
     * @param headers        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable String content, Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @Nullable Charset responseEncoding,
                                @NotNull TypeRef<T> typeRef) {
        return execute(url, content, queryMap, headers, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param queryMap         URL参数
     * @param headers        HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable String content, @Nullable Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @NotNull HttpBodyMethod bodyMethod,
                                @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        InputStream in = null;
        try {
            if (content != null) {
                in = new ByteArrayInputStream(content.getBytes(CharsetUtil.UTF_8));
            }
            return execute(url, ContentType.DEFAULT_BINARY, in, queryMap, headers, bodyMethod, responseEncoding, typeRef);
        } finally {
            IOUtil.closeQuietly(in);
        }
    }


    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url     请求地址
     * @param content BODY输入流，不会自动关闭流
     * @param typeRef 类型
     * @param <T>     泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable File content, @NotNull TypeRef<T> typeRef) {
        return execute(url, content, (Charset) null, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable File content, @Nullable Charset responseEncoding,
                                @NotNull TypeRef<T> typeRef) {
        return execute(url, content, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param content    BODY输入流，不会自动关闭流
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable File content, @NotNull HttpBodyMethod bodyMethod,
                                @NotNull TypeRef<T> typeRef) {
        return execute(url, content, bodyMethod, (Charset) null, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable File content, @NotNull HttpBodyMethod bodyMethod,
                                @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return execute(url, content, (Map<String, ?>) null, bodyMethod, responseEncoding, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url      请求地址
     * @param content  BODY输入流，不会自动关闭流
     * @param queryMap URL参数
     * @param typeRef  类型
     * @param <T>      泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable File content, Map<String, ?> queryMap,
                                @NotNull TypeRef<T> typeRef) {
        return execute(url, content, queryMap, HttpBodyMethod.POST, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param content    BODY输入流，不会自动关闭流
     * @param queryMap   URL参数
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable File content, Map<String, ?> queryMap,
                                @NotNull HttpBodyMethod bodyMethod, @NotNull TypeRef<T> typeRef) {
        return execute(url, content, queryMap, bodyMethod, (Charset) null, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable File content, @Nullable Map<String, ?> queryMap,
                                @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return execute(url, content, queryMap, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param queryMap         URL参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable File content, @Nullable Map<String, ?> queryMap,
                                @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding,
                                @NotNull TypeRef<T> typeRef) {
        return execute(url, content, queryMap, null, bodyMethod, responseEncoding, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url       请求地址
     * @param content   BODY输入流，不会自动关闭流
     * @param queryMap  URL参数
     * @param headers HTTP头参数
     * @param typeRef   类型
     * @param <T>       泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable File content, Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @NotNull TypeRef<T> typeRef) {
        return execute(url, content, queryMap, headers, HttpBodyMethod.POST, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param content    BODY输入流，不会自动关闭流
     * @param queryMap   URL参数
     * @param headers  HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable File content, @Nullable Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @NotNull HttpBodyMethod bodyMethod,
                                @NotNull TypeRef<T> typeRef) {
        return execute(url, content, queryMap, headers, bodyMethod, null, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param queryMap         URL参数
     * @param headers        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable File content, Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @Nullable Charset responseEncoding,
                                @NotNull TypeRef<T> typeRef) {
        return execute(url, content, queryMap, headers, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param queryMap         URL参数
     * @param headers        HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable File content, @Nullable Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @NotNull HttpBodyMethod bodyMethod,
                                @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        FileInputStream in = null;
        ContentType contentType = null;
        try {
            if (content != null) {
                in = FileUtil.openFileInputStream(content);
                //TODO 获取文件的ContentType
                contentType = ContentType.parseByFileExt(FileUtil.getFileExt(content));
            }
            return execute(url, contentType, in, queryMap, headers, bodyMethod, responseEncoding, typeRef);
        } finally {
            IOUtil.closeQuietly(in);
        }
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url     请求地址
     * @param content BODY输入流，不会自动关闭流
     * @param typeRef 类型
     * @param <T>     泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                @NotNull TypeRef<T> typeRef) {
        return execute(url, contentType, content, (Charset) null, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return execute(url, contentType, content, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param content    BODY输入流，不会自动关闭流
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                @NotNull HttpBodyMethod bodyMethod, @NotNull TypeRef<T> typeRef) {
        return execute(url, contentType, content, bodyMethod, null, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding,
                                @NotNull TypeRef<T> typeRef) {
        return execute(url, contentType, content, null, bodyMethod, responseEncoding, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url      请求地址
     * @param content  BODY输入流，不会自动关闭流
     * @param queryMap URL参数
     * @param typeRef  类型
     * @param <T>      泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                Map<String, ?> queryMap, @NotNull TypeRef<T> typeRef) {
        return execute(url, contentType, content, queryMap, HttpBodyMethod.POST, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param content    BODY输入流，不会自动关闭流
     * @param queryMap   URL参数
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                Map<String, ?> queryMap, @NotNull HttpBodyMethod bodyMethod, @NotNull TypeRef<T> typeRef) {
        return execute(url, contentType, content, queryMap, bodyMethod, null, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                @Nullable Map<String, ?> queryMap, @Nullable Charset responseEncoding,
                                @NotNull TypeRef<T> typeRef) {
        return execute(url, contentType, content, queryMap, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param queryMap         URL参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                @Nullable Map<String, ?> queryMap, @NotNull HttpBodyMethod bodyMethod,
                                @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return execute(url, contentType, content, queryMap, null, bodyMethod, responseEncoding, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url       请求地址
     * @param content   BODY输入流，不会自动关闭流
     * @param queryMap  URL参数
     * @param headers HTTP头参数
     * @param typeRef   类型
     * @param <T>       泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                @Nullable Map<String, ?> queryMap, @Nullable HttpHeaders headers,
                                @NotNull TypeRef<T> typeRef) {
        return execute(url, contentType, content, queryMap, headers, HttpBodyMethod.POST, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param content    BODY输入流，不会自动关闭流
     * @param queryMap   URL参数
     * @param headers  HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                @Nullable Map<String, ?> queryMap, @Nullable HttpHeaders headers,
                                @NotNull HttpBodyMethod bodyMethod, @NotNull TypeRef<T> typeRef) {
        return execute(url, contentType, content, queryMap, headers, bodyMethod, null, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param queryMap         URL参数
     * @param headers        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                @Nullable Map<String, ?> queryMap, @Nullable HttpHeaders headers,
                                @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return execute(url, contentType, content, queryMap, headers, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param queryMap         URL参数
     * @param headers        HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                @Nullable Map<String, ?> queryMap, @Nullable HttpHeaders headers,
                                @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding,
                                @NotNull TypeRef<T> typeRef) {
        String text = execute(url, contentType, content, queryMap, headers, bodyMethod, responseEncoding);
        return StringUtil.isEmpty(text) ? null : JSONUtil.fromJson(text, typeRef);
    }


    // endregion

}
