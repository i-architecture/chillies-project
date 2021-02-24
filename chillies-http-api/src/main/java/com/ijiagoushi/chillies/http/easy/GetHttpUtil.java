package com.ijiagoushi.chillies.http.easy;

import com.ijiagoushi.chillies.core.lang.StringUtil;
import com.ijiagoushi.chillies.http.*;
import com.ijiagoushi.chillies.http.exceptions.HttpClientStatusException;
import com.ijiagoushi.chillies.json.JSONUtil;
import com.ijiagoushi.chillies.json.TypeRef;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * HTTP Get请求封装工具类
 *
 * @author miles.tang at 2021-02-07
 * @since 1.0
 */
public class GetHttpUtil {

    // region GET 请求

    /**
     * 发起GET请求，并将响应结果转为字符串
     *
     * @param url 请求地址
     * @return 响应内容
     */
    public static String execute(@NotNull String url) {
        return execute(url, (Charset) null);
    }

    /**
     * 发起GET请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @return 响应内容
     */
    public static String execute(@NotNull String url, @Nullable Charset responseEncoding) {
        return execute(url, null, responseEncoding);
    }

    /**
     * 发起GET请求，并将响应结果转为字符串
     *
     * @param url      请求地址
     * @param queryMap URL参数
     * @return 响应内容
     */
    public static String execute(@NotNull String url, @Nullable Map<String, ?> queryMap) {
        return execute(url, queryMap, (Charset) null);
    }

    /**
     * 发起GET请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应内容
     */
    public static String execute(@NotNull String url, @Nullable Map<String, ?> queryMap, @Nullable Charset responseEncoding) {
        return execute(url, queryMap, null, responseEncoding);
    }

    /**
     * 发起GET请求，并将响应结果转为字符串
     *
     * @param url      请求地址
     * @param queryMap URL参数
     * @param headers  HTTP头参数
     * @return 响应内容
     */
    public static String execute(@NotNull String url, @Nullable Map<String, ?> queryMap, @Nullable HttpHeaders headers) {
        return execute(url, queryMap, headers, (Charset) null);
    }

    /**
     * 发起GET请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param queryMap         URL参数
     * @param headers          HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应内容
     */
    public static String execute(@NotNull String url, @Nullable Map<String, ?> queryMap, @Nullable HttpHeaders headers,
                                 @Nullable Charset responseEncoding) {
        return execute(url, queryMap, headers, responseEncoding, (HttpOptions) null);
    }

    /**
     * 发起GET请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param queryMap         URL参数
     * @param headers          HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应内容
     */
    public static String execute(@NotNull String url, @Nullable Map<String, ?> queryMap, @Nullable HttpHeaders headers,
                                 @Nullable Charset responseEncoding, @Nullable HttpOptions options) {
        HttpClient httpClient = Factory.get().build(options);
        HttpRequest httpRequest = HttpRequest.builder()
                .url(url)
                .queryParams(queryMap)
                .headers(headers)
                .build();
        HttpResponse httpResponse = httpClient.execute(httpRequest, null);
        if (httpResponse.status() >= 200 && httpResponse.status() < 300) {
            HttpResponseBody httpResponseBody = httpResponse.body();
            return httpResponseBody.string(responseEncoding);
        }
        throw new HttpClientStatusException(httpResponse.status(), httpResponse.reason(), httpRequest);
    }


    /**
     * 发起GET请求，并将响应结果转为JavaBean对象
     *
     * @param url   请求地址
     * @param clazz 类型
     * @param <T>   模板实际类型
     * @return 响应内容
     */
    public static <T> T execute(@NotNull String url, @NotNull Class<T> clazz) {
        return execute(url, (Charset) null, clazz);
    }

    /**
     * 发起GET请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              模板实际类型
     * @return 响应内容
     */
    public static <T> T execute(@NotNull String url, @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return execute(url, null, null, responseEncoding, clazz);
    }

    /**
     * 发起GET请求，并将响应结果转为JavaBean对象
     *
     * @param url      请求地址
     * @param queryMap URL参数
     * @param clazz    类型
     * @param <T>      模板实际类型
     * @return 响应内容
     */
    public static <T> T execute(@NotNull String url, @Nullable Map<String, ?> queryMap, @NotNull Class<T> clazz) {
        return execute(url, queryMap, (Charset) null, clazz);
    }

    /**
     * 发起GET请求，并将响应结果转为JavaBean对象
     *
     * @param url      请求地址
     * @param queryMap URL参数
     * @param clazz    类型
     * @param <T>      模板实际类型
     * @return 响应内容
     */
    public static <T> T execute(@NotNull String url, @Nullable Map<String, ?> queryMap,@Nullable HttpOptions options,
                                @NotNull Class<T> clazz) {
        return execute(url, queryMap, (Charset) null, options, clazz);
    }

    /**
     * 发起GET请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              模板实际类型
     * @return 响应内容
     */
    public static <T> T execute(@NotNull String url, @Nullable Map<String, ?> queryMap, @Nullable Charset responseEncoding,
                                @NotNull Class<T> clazz) {
        return execute(url, queryMap, null, responseEncoding, clazz);
    }

    /**
     * 发起GET请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              模板实际类型
     * @return 响应内容
     */
    public static <T> T execute(@NotNull String url, @Nullable Map<String, ?> queryMap, @Nullable Charset responseEncoding,
                                @Nullable HttpOptions options, @NotNull Class<T> clazz) {
        return execute(url, queryMap, null, responseEncoding, options, clazz);
    }

    /**
     * 发起GET请求，并将响应结果转为JavaBean对象
     *
     * @param url      请求地址
     * @param queryMap URL参数
     * @param headers  HTTP头参数
     * @param clazz    类型
     * @param <T>      模板实际类型
     * @return 响应内容
     */
    public static <T> T execute(@NotNull String url, @Nullable Map<String, ?> queryMap, @Nullable HttpHeaders headers,
                                @NotNull Class<T> clazz) {
        return execute(url, queryMap, headers, (HttpOptions) null, clazz);
    }

    /**
     * 发起GET请求，并将响应结果转为JavaBean对象
     *
     * @param url      请求地址
     * @param queryMap URL参数
     * @param headers  HTTP头参数
     * @param clazz    类型
     * @param <T>      模板实际类型
     * @return 响应内容
     */
    public static <T> T execute(@NotNull String url, @Nullable Map<String, ?> queryMap, @Nullable HttpHeaders headers,
                                @Nullable HttpOptions options, @NotNull Class<T> clazz) {
        return execute(url, queryMap, headers, null, options, clazz);
    }

    /**
     * 发起GET请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param queryMap         URL参数
     * @param headers          HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              模板实际类型
     * @return 响应内容
     */
    public static <T> T execute(@NotNull String url, @Nullable Map<String, ?> queryMap, @Nullable HttpHeaders headers,
                                @Nullable Charset responseEncoding, Class<T> clazz) {
        return execute(url, queryMap, headers, responseEncoding, null, clazz);
    }

    /**
     * 发起GET请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param queryMap         URL参数
     * @param headers          HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              模板实际类型
     * @return 响应内容
     */
    public static <T> T execute(@NotNull String url, @Nullable Map<String, ?> queryMap, @Nullable HttpHeaders headers,
                                @Nullable Charset responseEncoding, @Nullable HttpOptions options, Class<T> clazz) {
        String response = execute(url, queryMap, headers, responseEncoding, options);
        return (StringUtil.isEmpty(response)) ? null : JSONUtil.fromJson(response, clazz);
    }


    /**
     * 发起GET请求，并将响应结果转为JavaBean对象
     *
     * @param url     请求地址
     * @param typeRef 泛型类型包装类
     * @param <T>     目标类型
     * @return 响应内容
     */
    public static <T> T execute(@NotNull String url, @NotNull TypeRef<T> typeRef) {
        return execute(url, (Charset) null, typeRef);
    }

    /**
     * 发起GET请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          泛型类型包装类
     * @param <T>              目标类型
     * @return 响应内容
     */
    public static <T> T execute(@NotNull String url, @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return execute(url, null, responseEncoding, typeRef);
    }

    /**
     * 发起GET请求，并将响应结果转为JavaBean对象
     *
     * @param url      请求地址
     * @param queryMap URL参数
     * @param typeRef  泛型类型包装类
     * @param <T>      目标类型
     * @return 响应内容
     */
    public static <T> T execute(@NotNull String url, @Nullable Map<String, ?> queryMap, @NotNull TypeRef<T> typeRef) {
        return execute(url, queryMap, (HttpHeaders) null, typeRef);
    }

    /**
     * 发起GET请求，并将响应结果转为JavaBean对象
     *
     * @param url      请求地址
     * @param queryMap URL参数
     * @param typeRef  泛型类型包装类
     * @param <T>      目标类型
     * @return 响应内容
     */
    public static <T> T execute(@NotNull String url, @Nullable Map<String, ?> queryMap, @Nullable HttpOptions options,
                                @NotNull TypeRef<T> typeRef) {
        return execute(url, queryMap, (HttpHeaders) null, options, typeRef);
    }

    /**
     * 发起GET请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          泛型类型包装类
     * @param <T>              目标类型
     * @return 响应内容
     */
    public static <T> T execute(@NotNull String url, @Nullable Map<String, ?> queryMap, @Nullable Charset responseEncoding,
                                @NotNull TypeRef<T> typeRef) {
        return execute(url, queryMap, null, responseEncoding, typeRef);
    }

    /**
     * 发起GET请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          泛型类型包装类
     * @param <T>              目标类型
     * @return 响应内容
     */
    public static <T> T execute(@NotNull String url, @Nullable Map<String, ?> queryMap, @Nullable Charset responseEncoding,
                                @Nullable HttpOptions options, @NotNull TypeRef<T> typeRef) {
        return execute(url, queryMap, null, responseEncoding, options, typeRef);
    }

    /**
     * 发起GET请求，并将响应结果转为JavaBean对象
     *
     * @param url      请求地址
     * @param queryMap URL参数
     * @param headers  HTTP头参数
     * @param typeRef  泛型类型包装类
     * @param <T>      目标类型
     * @return 响应内容
     */
    public static <T> T execute(@NotNull String url, @Nullable Map<String, ?> queryMap, @Nullable HttpHeaders headers,
                                @NotNull TypeRef<T> typeRef) {
        return execute(url, queryMap, headers, (HttpOptions) null, typeRef);
    }

    /**
     * 发起GET请求，并将响应结果转为JavaBean对象
     *
     * @param url      请求地址
     * @param queryMap URL参数
     * @param headers  HTTP头参数
     * @param typeRef  泛型类型包装类
     * @param <T>      目标类型
     * @return 响应内容
     */
    public static <T> T execute(@NotNull String url, @Nullable Map<String, ?> queryMap, @Nullable HttpHeaders headers,
                                @Nullable HttpOptions options, @NotNull TypeRef<T> typeRef) {
        return execute(url, queryMap, headers, null, options, typeRef);
    }

    /**
     * 发起GET请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param queryMap         URL参数
     * @param headers          HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          泛型类型包装类
     * @param <T>              目标类型
     * @return 响应内容
     */
    public static <T> T execute(@NotNull String url, @Nullable Map<String, ?> queryMap, @Nullable HttpHeaders headers,
                                @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        String response = execute(url, queryMap, headers, responseEncoding);
        return (StringUtil.isEmpty(response)) ? null : JSONUtil.fromJson(response, typeRef);
    }

    /**
     * 发起GET请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param queryMap         URL参数
     * @param headers          HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          泛型类型包装类
     * @param <T>              目标类型
     * @return 响应内容
     */
    public static <T> T execute(@NotNull String url, @Nullable Map<String, ?> queryMap, @Nullable HttpHeaders headers,
                                @Nullable Charset responseEncoding, @Nullable HttpOptions options, @NotNull TypeRef<T> typeRef) {
        String response = execute(url, queryMap, headers, responseEncoding, options);
        return (StringUtil.isEmpty(response)) ? null : JSONUtil.fromJson(response, typeRef);
    }

    // endregion

}
