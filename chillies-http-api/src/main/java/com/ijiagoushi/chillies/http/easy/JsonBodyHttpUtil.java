package com.ijiagoushi.chillies.http.easy;

import com.ijiagoushi.chillies.core.http.ContentType;
import com.ijiagoushi.chillies.core.lang.StringUtil;
import com.ijiagoushi.chillies.http.*;
import com.ijiagoushi.chillies.http.exceptions.HttpClientStatusException;
import com.ijiagoushi.chillies.json.JSONUtil;
import com.ijiagoushi.chillies.json.TypeRef;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author miles.tang at 2021-02-07
 * @since 1.0
 */
public class JsonBodyHttpUtil {

    // region JSON RawBody POST

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url 请求地址
     * @return 响应结果
     */
    public static String execute(@NotNull String url) {
        return execute(url, (Charset) null);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url 请求地址
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable HttpOptions options) {
        return execute(url, (Charset) null, options);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable Charset responseEncoding) {
        return execute(url, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable Charset responseEncoding, @Nullable HttpOptions options) {
        return execute(url, HttpBodyMethod.POST, responseEncoding, options);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @NotNull HttpBodyMethod bodyMethod) {
        return execute(url, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @NotNull HttpBodyMethod bodyMethod, @Nullable HttpOptions options) {
        return execute(url, bodyMethod, (Charset) null, options);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url  请求地址
     * @param body BODY内容
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable Object body) {
        return execute(url, body, HttpBodyMethod.POST);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url  请求地址
     * @param body BODY内容
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable Object body, @Nullable HttpOptions options) {
        return execute(url, body, HttpBodyMethod.POST, options);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod) {
        return execute(url, body, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                 @Nullable HttpOptions options) {
        return execute(url, body, bodyMethod, (Charset) null, options);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable Object body, @Nullable Charset responseEncoding) {
        return execute(url, body, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable Object body, @Nullable Charset responseEncoding,
                                 @Nullable HttpOptions options) {
        return execute(url, body, HttpBodyMethod.POST, responseEncoding, options);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                 @Nullable Charset responseEncoding) {
        return execute(url, body, null, bodyMethod, responseEncoding);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                 @Nullable Charset responseEncoding, @Nullable HttpOptions options) {
        return execute(url, body, null, bodyMethod, responseEncoding, options);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url      请求地址
     * @param body     BODY内容
     * @param queryMap URL参数
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap) {
        return execute(url, body, queryMap, HttpBodyMethod.POST);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url      请求地址
     * @param body     BODY内容
     * @param queryMap URL参数
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                 @Nullable HttpOptions options) {
        return execute(url, body, queryMap, HttpBodyMethod.POST, options);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                 @NotNull HttpBodyMethod bodyMethod) {
        return execute(url, body, queryMap, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                 @NotNull HttpBodyMethod bodyMethod, @Nullable HttpOptions options) {
        return execute(url, body, queryMap, bodyMethod, (Charset) null, options);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                 @Nullable Charset responseEncoding) {
        return execute(url, body, queryMap, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                 @Nullable Charset responseEncoding, @Nullable HttpOptions options) {
        return execute(url, body, queryMap, HttpBodyMethod.POST, responseEncoding, options);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                 @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding) {
        return execute(url, body, queryMap, null, bodyMethod, responseEncoding);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                 @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding,
                                 @Nullable HttpOptions options) {
        return execute(url, body, queryMap, null, bodyMethod, responseEncoding, options);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url      请求地址
     * @param body     BODY内容
     * @param queryMap URL参数
     * @param headers  HTTP头参数
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                 @Nullable HttpHeaders headers) {
        return execute(url, body, queryMap, headers, HttpBodyMethod.POST);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url      请求地址
     * @param body     BODY内容
     * @param queryMap URL参数
     * @param headers  HTTP头参数
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                 @Nullable HttpHeaders headers, @Nullable HttpOptions options) {
        return execute(url, body, queryMap, headers, HttpBodyMethod.POST, options);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param headers    HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                 @Nullable HttpHeaders headers, @NotNull HttpBodyMethod bodyMethod) {
        return execute(url, body, queryMap, headers, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param headers    HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                 @Nullable HttpHeaders headers, @NotNull HttpBodyMethod bodyMethod,
                                 @Nullable HttpOptions options) {
        return execute(url, body, queryMap, headers, bodyMethod, (Charset) null, options);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headers          HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                 @Nullable HttpHeaders headers, @Nullable Charset responseEncoding) {
        return execute(url, body, queryMap, headers, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headers          HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                 @Nullable HttpHeaders headers, @Nullable Charset responseEncoding,
                                 @Nullable HttpOptions options) {
        return execute(url, body, queryMap, headers, HttpBodyMethod.POST, responseEncoding, options);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headers          HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                 @Nullable HttpHeaders headers, @NotNull HttpBodyMethod bodyMethod,
                                 @Nullable Charset responseEncoding) {
        return execute(url, body, queryMap, headers, bodyMethod, responseEncoding, (HttpOptions) null);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headers          HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String execute(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                 @Nullable HttpHeaders headers, @NotNull HttpBodyMethod bodyMethod,
                                 @Nullable Charset responseEncoding, @Nullable HttpOptions options) {
        HttpClient httpClient = Factory.get().build(options);

        HttpRequestBody requestBody = null;
        if (body != null) {
            if (body instanceof String) {
                requestBody = HttpRequestBody.create(ContentType.APPLICATION_JSON, (String) body);
            } else if (body instanceof InputStream) {
                requestBody = HttpRequestBody.create(ContentType.APPLICATION_JSON, (InputStream) body);
            } else if (body instanceof File) {
                requestBody = HttpRequestBody.create(ContentType.APPLICATION_JSON, (File) body);
            } else {
                requestBody = HttpRequestBody.create(ContentType.APPLICATION_JSON, JSONUtil.toJson(body));
            }
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
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
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
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url   请求地址
     * @param clazz 类型
     * @param <T>   泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable HttpOptions options, @NotNull Class<T> clazz) {
        return execute(url, (Charset) null, options, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
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
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Charset responseEncoding, @Nullable HttpOptions options,
                                @NotNull Class<T> clazz) {
        return execute(url, HttpBodyMethod.POST, responseEncoding, options, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param bodyMethod HTTP请求方式
     * @param clazz      类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @NotNull HttpBodyMethod bodyMethod, @NotNull Class<T> clazz) {
        return execute(url, null, bodyMethod, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param bodyMethod HTTP请求方式
     * @param clazz      类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @NotNull HttpBodyMethod bodyMethod, @Nullable HttpOptions options, @NotNull Class<T> clazz) {
        return execute(url, null, bodyMethod, options, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url   请求地址
     * @param body  BODY内容
     * @param clazz 类型
     * @param <T>   泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, @NotNull Class<T> clazz) {
        return execute(url, body, HttpBodyMethod.POST, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url   请求地址
     * @param body  BODY内容
     * @param clazz 类型
     * @param <T>   泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, @Nullable HttpOptions options, @NotNull Class<T> clazz) {
        return execute(url, body, HttpBodyMethod.POST, options, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param bodyMethod HTTP请求方式
     * @param clazz      类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                @NotNull Class<T> clazz) {
        return execute(url, body, bodyMethod, (HttpOptions) null, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param bodyMethod HTTP请求方式
     * @param clazz      类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                @Nullable HttpOptions options, @NotNull Class<T> clazz) {
        return execute(url, body, bodyMethod, null, options, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, @Nullable Charset responseEncoding,
                                @NotNull Class<T> clazz) {
        return execute(url, body, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, @Nullable Charset responseEncoding,
                                @Nullable HttpOptions options, @NotNull Class<T> clazz) {
        return execute(url, body, HttpBodyMethod.POST, responseEncoding, options, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return execute(url, body, null, bodyMethod, responseEncoding, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                @Nullable Charset responseEncoding, @Nullable HttpOptions options, @NotNull Class<T> clazz) {
        return execute(url, body, null, bodyMethod, responseEncoding, options, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url      请求地址
     * @param body     BODY内容
     * @param queryMap URL参数
     * @param clazz    类型
     * @param <T>      泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                @NotNull Class<T> clazz) {
        return execute(url, body, queryMap, HttpBodyMethod.POST, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url      请求地址
     * @param body     BODY内容
     * @param queryMap URL参数
     * @param clazz    类型
     * @param <T>      泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                @Nullable HttpOptions options, @NotNull Class<T> clazz) {
        return execute(url, body, queryMap, HttpBodyMethod.POST, options, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param bodyMethod HTTP请求方式
     * @param clazz      类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                @NotNull HttpBodyMethod bodyMethod, @NotNull Class<T> clazz) {
        return execute(url, body, queryMap, bodyMethod, (HttpOptions) null, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param bodyMethod HTTP请求方式
     * @param clazz      类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                @NotNull HttpBodyMethod bodyMethod, @Nullable HttpOptions options,
                                @NotNull Class<T> clazz) {
        return execute(url, body, queryMap, bodyMethod, null, options, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return execute(url, body, queryMap, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                @Nullable Charset responseEncoding, @Nullable HttpOptions options, @NotNull Class<T> clazz) {
        return execute(url, body, queryMap, HttpBodyMethod.POST, responseEncoding, options, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return execute(url, body, queryMap, null, bodyMethod, responseEncoding, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding,
                                @Nullable HttpOptions options, @NotNull Class<T> clazz) {
        return execute(url, body, queryMap, null, bodyMethod, responseEncoding, options, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url      请求地址
     * @param body     BODY内容
     * @param queryMap URL参数
     * @param headers  HTTP头参数
     * @param clazz    类型
     * @param <T>      泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @NotNull Class<T> clazz) {
        return execute(url, body, queryMap, headers, HttpBodyMethod.POST, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url      请求地址
     * @param body     BODY内容
     * @param queryMap URL参数
     * @param headers  HTTP头参数
     * @param clazz    类型
     * @param <T>      泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @Nullable HttpOptions options, @NotNull Class<T> clazz) {
        return execute(url, body, queryMap, headers, HttpBodyMethod.POST, options, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param headers    HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @param clazz      类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @NotNull HttpBodyMethod bodyMethod, @NotNull Class<T> clazz) {
        return execute(url, body, queryMap, headers, bodyMethod, (HttpOptions) null, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param headers    HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @param clazz      类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @NotNull HttpBodyMethod bodyMethod,
                                @Nullable HttpOptions options, @NotNull Class<T> clazz) {
        return execute(url, body, queryMap, headers, bodyMethod, null, options, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headers          HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @Nullable Charset responseEncoding,
                                @NotNull Class<T> clazz) {
        return execute(url, body, queryMap, headers, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headers          HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @Nullable Charset responseEncoding,
                                @Nullable HttpOptions options, @NotNull Class<T> clazz) {
        return execute(url, body, queryMap, headers, HttpBodyMethod.POST, responseEncoding, options, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headers          HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @NotNull HttpBodyMethod bodyMethod,
                                @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        String text = execute(url, body, queryMap, headers, bodyMethod, responseEncoding);
        return StringUtil.isEmpty(text) ? null : JSONUtil.fromJson(text, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headers          HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @NotNull HttpBodyMethod bodyMethod,
                                @Nullable Charset responseEncoding, @Nullable HttpOptions options, @NotNull Class<T> clazz) {
        String text = execute(url, body, queryMap, headers, bodyMethod, responseEncoding, options);
        return StringUtil.isEmpty(text) ? null : JSONUtil.fromJson(text, clazz);
    }


    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
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
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return execute(url, null, responseEncoding, typeRef);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
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
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url     请求地址
     * @param body    BODY内容
     * @param typeRef 类型
     * @param <T>     泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, @NotNull TypeRef<T> typeRef) {
        return execute(url, body, HttpBodyMethod.POST, typeRef);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                @NotNull TypeRef<T> typeRef) {
        return execute(url, body, bodyMethod, null, typeRef);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, @Nullable Charset responseEncoding,
                                @NotNull TypeRef<T> typeRef) {
        return execute(url, body, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return execute(url, body, null, bodyMethod, responseEncoding, typeRef);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url      请求地址
     * @param body     BODY内容
     * @param queryMap URL参数
     * @param typeRef  类型
     * @param <T>      泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                @NotNull TypeRef<T> typeRef) {
        return execute(url, body, queryMap, HttpBodyMethod.POST, typeRef);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                @NotNull HttpBodyMethod bodyMethod, @NotNull TypeRef<T> typeRef) {
        return execute(url, body, queryMap, bodyMethod, null, typeRef);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return execute(url, body, queryMap, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding,
                                @NotNull TypeRef<T> typeRef) {
        return execute(url, body, queryMap, null, bodyMethod, responseEncoding, typeRef);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url      请求地址
     * @param body     BODY内容
     * @param queryMap URL参数
     * @param headers  HTTP头参数
     * @param typeRef  类型
     * @param <T>      泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @NotNull TypeRef<T> typeRef) {
        return execute(url, body, queryMap, headers, HttpBodyMethod.POST, typeRef);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url      请求地址
     * @param body     BODY内容
     * @param queryMap URL参数
     * @param headers  HTTP头参数
     * @param typeRef  类型
     * @param <T>      泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @Nullable HttpOptions options, @NotNull TypeRef<T> typeRef) {
        return execute(url, body, queryMap, headers, HttpBodyMethod.POST, options, typeRef);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param headers    HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @NotNull HttpBodyMethod bodyMethod,
                                @NotNull TypeRef<T> typeRef) {
        return execute(url, body, queryMap, headers, bodyMethod, null, null, typeRef);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param headers    HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @NotNull HttpBodyMethod bodyMethod,
                                @Nullable HttpOptions httpOptions, @NotNull TypeRef<T> typeRef) {
        return execute(url, body, queryMap, headers, bodyMethod, null, httpOptions, typeRef);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headers          HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @Nullable Charset responseEncoding,
                                @NotNull TypeRef<T> typeRef) {
        return execute(url, body, queryMap, headers, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headers          HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @Nullable Charset responseEncoding,
                                @Nullable HttpOptions httpOptions, @NotNull TypeRef<T> typeRef) {
        return execute(url, body, queryMap, headers, HttpBodyMethod.POST, responseEncoding, httpOptions, typeRef);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headers          HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @NotNull HttpBodyMethod bodyMethod,
                                @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return execute(url, body, queryMap, headers, bodyMethod, responseEncoding, null, typeRef);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headers          HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param httpOptions      选项
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T execute(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                @Nullable HttpHeaders headers, @NotNull HttpBodyMethod bodyMethod,
                                @Nullable Charset responseEncoding, @Nullable HttpOptions httpOptions,
                                @NotNull TypeRef<T> typeRef) {
        String text = execute(url, body, queryMap, headers, bodyMethod, responseEncoding, httpOptions);
        return StringUtil.isEmpty(text) ? null : JSONUtil.fromJson(text, typeRef);
    }

    // endregion

}
