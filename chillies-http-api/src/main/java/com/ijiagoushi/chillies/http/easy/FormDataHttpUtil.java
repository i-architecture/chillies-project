package com.ijiagoushi.chillies.http.easy;

import com.ijiagoushi.chillies.core.lang.CollectionUtil;
import com.ijiagoushi.chillies.core.lang.StringUtil;
import com.ijiagoushi.chillies.http.*;
import com.ijiagoushi.chillies.http.constants.HttpMethod;
import com.ijiagoushi.chillies.http.exceptions.HttpClientException;
import com.ijiagoushi.chillies.http.exceptions.HttpClientStatusException;
import com.ijiagoushi.chillies.json.JSONUtil;
import com.ijiagoushi.chillies.json.TypeRef;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Map;

/**
 * @author miles.tang at 2021-02-07
 * @since 1.0
 */
public class FormDataHttpUtil {

    // region x-www-form-urlencoded POST

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为字符串
     *
     * @param url 请求地址
     * @return 响应结果
     */
    public static String formUrlEncoded(@NotNull String url) {
        return formUrlEncoded(url, (Charset) null);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String formUrlEncoded(@NotNull String url, @Nullable Charset responseEncoding) {
        return formUrlEncoded(url, (Map<String, ?>) null, responseEncoding);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为字符串
     *
     * @param url     请求地址
     * @param postMap POST参数
     * @return 响应结果
     */
    public static String formUrlEncoded(@NotNull String url, @Nullable Map<String, ?> postMap) {
        return formUrlEncoded(url, postMap, (HttpOptions) null);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为字符串
     *
     * @param url     请求地址
     * @param postMap POST参数
     * @return 响应结果
     */
    public static String formUrlEncoded(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable HttpOptions options) {
        return formUrlEncoded(url, postMap, (Charset) null, options);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String formUrlEncoded(@NotNull String url, @Nullable Map<String, ?> postMap,
                                        @Nullable Charset responseEncoding, @Nullable HttpOptions options) {
        return formUrlEncoded(url, postMap, null, responseEncoding, options);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String formUrlEncoded(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable Charset responseEncoding) {
        return formUrlEncoded(url, postMap, null, responseEncoding);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为字符串
     *
     * @param url     请求地址
     * @param postMap POST参数
     * @param headers HTTP头参数
     * @return 响应结果
     */
    public static String formUrlEncoded(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable HttpHeaders headers) {
        return formUrlEncoded(url, postMap, headers, (Charset) null);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为字符串
     *
     * @param url     请求地址
     * @param postMap POST参数
     * @param headers HTTP头参数
     * @return 响应结果
     */
    public static String formUrlEncoded(@NotNull String url, @Nullable Map<String, ?> postMap,
                                        @Nullable HttpHeaders headers, @Nullable HttpOptions options) {
        return formUrlEncoded(url, postMap, headers, (Charset) null, options);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param headers          HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String formUrlEncoded(@NotNull String url, @Nullable Map<String, ?> postMap,
                                        @Nullable HttpHeaders headers, @Nullable Charset responseEncoding) {
        return formUrlEncoded(url, postMap, headers, responseEncoding, (HttpOptions) null);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param headers          HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String formUrlEncoded(@NotNull String url, @Nullable Map<String, ?> postMap,
                                        @Nullable HttpHeaders headers, @Nullable Charset responseEncoding,
                                        @Nullable HttpOptions options) {
        HttpClient httpClient = Factory.get().build(options);
        HttpRequest httpRequest = HttpRequest.builder()
                .url(url)
                .queryParams(postMap)
                .headers(headers)
                .method(HttpMethod.POST)
                .build();
        HttpResponse httpResponse = httpClient.execute(httpRequest, null);
        if (httpResponse.status() >= 200 && httpResponse.status() < 300) {
            HttpResponseBody httpResponseBody = httpResponse.body();
            return httpResponseBody.string(responseEncoding);
        }
        throw new HttpClientStatusException(httpResponse.status(), httpResponse.reason(), httpRequest);
    }


    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为JavaBean对象
     *
     * @param url   请求地址
     * @param clazz 类型
     * @param <T>   元素类型
     * @return 响应结果
     */
    public static <T> T formUrlEncoded(@NotNull String url, @NotNull Class<T> clazz) {
        return formUrlEncoded(url, (Charset) null, clazz);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              元素类型
     * @return 响应结果
     */
    public static <T> T formUrlEncoded(@NotNull String url, @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return formUrlEncoded(url, null, responseEncoding, clazz);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为JavaBean对象
     *
     * @param url     请求地址
     * @param postMap POST参数
     * @param clazz   类型
     * @param <T>     元素类型
     * @return 响应结果
     */
    public static <T> T formUrlEncoded(@NotNull String url, @Nullable Map<String, ?> postMap, @NotNull Class<T> clazz) {
        return formUrlEncoded(url, postMap, (HttpHeaders) null, clazz);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为JavaBean对象
     *
     * @param url     请求地址
     * @param postMap POST参数
     * @param clazz   类型
     * @param <T>     元素类型
     * @return 响应结果
     */
    public static <T> T formUrlEncoded(@NotNull String url, @Nullable Map<String, ?> postMap, @NotNull Class<T> clazz,
                                       @Nullable HttpOptions options) {
        return formUrlEncoded(url, postMap, (HttpHeaders) null, clazz, options);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为JavaBean对象
     *
     * @param url     请求地址
     * @param postMap POST参数
     * @param clazz   类型
     * @param <T>     元素类型
     * @return 响应结果
     */
    public static <T> T formUrlEncoded(@NotNull String url, Map<String, ?> postMap, @Nullable Charset responseEncoding,
                                       @NotNull Class<T> clazz) {
        return formUrlEncoded(url, postMap, null, responseEncoding, clazz);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为JavaBean对象
     *
     * @param url     请求地址
     * @param postMap POST参数
     * @param clazz   类型
     * @param <T>     元素类型
     * @return 响应结果
     */
    public static <T> T formUrlEncoded(@NotNull String url, Map<String, ?> postMap, @Nullable Charset responseEncoding,
                                       @NotNull Class<T> clazz, @Nullable HttpOptions options) {
        return formUrlEncoded(url, postMap, null, responseEncoding, clazz, options);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为JavaBean对象
     *
     * @param url     请求地址
     * @param postMap POST参数
     * @param headers HTTP头参数
     * @param clazz   类型
     * @param <T>     元素类型
     * @return 响应结果
     */
    public static <T> T formUrlEncoded(@NotNull String url, @Nullable Map<String, ?> postMap,
                                       @Nullable HttpHeaders headers, @NotNull Class<T> clazz) {
        String response = formUrlEncoded(url, postMap, headers, (Charset) null);
        return (StringUtil.isEmpty(response)) ? null : JSONUtil.fromJson(response, clazz);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为JavaBean对象
     *
     * @param url     请求地址
     * @param postMap POST参数
     * @param headers HTTP头参数
     * @param clazz   类型
     * @param options 选项
     * @param <T>     元素类型
     * @return 响应结果
     */
    public static <T> T formUrlEncoded(@NotNull String url, @Nullable Map<String, ?> postMap,
                                       @Nullable HttpHeaders headers, @NotNull Class<T> clazz,
                                       @Nullable HttpOptions options) {
        String response = formUrlEncoded(url, postMap, headers, (Charset) null, options);
        return (StringUtil.isEmpty(response)) ? null : JSONUtil.fromJson(response, clazz);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param responseEncoding 指定响应内容的编码
     * @param headers          HTTP头参数
     * @param clazz            类型
     * @param <T>              元素类型
     * @return 响应结果
     */
    public static <T> T formUrlEncoded(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable HttpHeaders headers,
                                       @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        String response = formUrlEncoded(url, postMap, headers, responseEncoding);
        return (StringUtil.isEmpty(response)) ? null : JSONUtil.fromJson(response, clazz);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param responseEncoding 指定响应内容的编码
     * @param headers          HTTP头参数
     * @param clazz            类型
     * @param <T>              元素类型
     * @return 响应结果
     */
    public static <T> T formUrlEncoded(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable HttpHeaders headers,
                                       @Nullable Charset responseEncoding, @NotNull Class<T> clazz, @Nullable HttpOptions options) {
        String response = formUrlEncoded(url, postMap, headers, responseEncoding, options);
        return (StringUtil.isEmpty(response)) ? null : JSONUtil.fromJson(response, clazz);
    }


    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为JavaBean对象
     *
     * @param url     请求地址
     * @param typeRef 类型
     * @param <T>     元素类型
     * @return 响应结果
     */
    public static <T> T formUrlEncoded(@NotNull String url, @NotNull TypeRef<T> typeRef) {
        return formUrlEncoded(url, (Charset) null, typeRef);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              元素类型
     * @return 响应结果
     */
    public static <T> T formUrlEncoded(@NotNull String url, @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return formUrlEncoded(url, null, responseEncoding, typeRef);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为JavaBean对象
     *
     * @param url     请求地址
     * @param postMap POST参数
     * @param typeRef 类型
     * @param <T>     元素类型
     * @return 响应结果
     */
    public static <T> T formUrlEncoded(@NotNull String url, @Nullable Map<String, ?> postMap, @NotNull TypeRef<T> typeRef) {
        return formUrlEncoded(url, postMap, (HttpHeaders) null, typeRef);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为JavaBean对象
     *
     * @param url     请求地址
     * @param postMap POST参数
     * @param typeRef 类型
     * @param <T>     元素类型
     * @return 响应结果
     */
    public static <T> T formUrlEncoded(@NotNull String url, @Nullable Map<String, ?> postMap, @NotNull TypeRef<T> typeRef,
                                       @Nullable HttpOptions options) {
        return formUrlEncoded(url, postMap, (HttpHeaders) null, typeRef, options);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              元素类型
     * @return 响应结果
     */
    public static <T> T formUrlEncoded(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable Charset responseEncoding,
                                       @NotNull TypeRef<T> typeRef) {
        return formUrlEncoded(url, postMap, null, responseEncoding, typeRef);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              元素类型
     * @return 响应结果
     */
    public static <T> T formUrlEncoded(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable Charset responseEncoding,
                                       @NotNull TypeRef<T> typeRef, @Nullable HttpOptions options) {
        return formUrlEncoded(url, postMap, null, responseEncoding, typeRef, options);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为JavaBean对象
     *
     * @param url     请求地址
     * @param postMap POST参数
     * @param headers HTTP头参数
     * @param typeRef 类型
     * @param <T>     元素类型
     * @return 响应结果
     */
    public static <T> T formUrlEncoded(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable HttpHeaders headers,
                                       @NotNull TypeRef<T> typeRef) {
        return formUrlEncoded(url, postMap, headers, null, typeRef);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为JavaBean对象
     *
     * @param url     请求地址
     * @param postMap POST参数
     * @param headers HTTP头参数
     * @param typeRef 类型
     * @param <T>     元素类型
     * @return 响应结果
     */
    public static <T> T formUrlEncoded(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable HttpHeaders headers,
                                       @NotNull TypeRef<T> typeRef, @Nullable HttpOptions options) {
        return formUrlEncoded(url, postMap, headers, null, typeRef, options);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param headers          HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              元素类型
     * @return 响应结果
     */
    public static <T> T formUrlEncoded(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable HttpHeaders headers,
                                       @Nullable Charset responseEncoding, TypeRef<T> typeRef) {
        String response = formUrlEncoded(url, postMap, headers, responseEncoding);
        return (StringUtil.isEmpty(response)) ? null : JSONUtil.fromJson(response, typeRef);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param headers          HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              元素类型
     * @return 响应结果
     */
    public static <T> T formUrlEncoded(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable HttpHeaders headers,
                                       @Nullable Charset responseEncoding, TypeRef<T> typeRef, @Nullable HttpOptions options) {
        String response = formUrlEncoded(url, postMap, headers, responseEncoding, options);
        return (StringUtil.isEmpty(response)) ? null : JSONUtil.fromJson(response, typeRef);
    }

    // endregion

    // region form-data POST

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url 请求地址
     * @return 响应结果
     */
    public static String formData(@NotNull String url) {
        return formData(url, (Charset) null);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String formData(@NotNull String url, @Nullable Charset responseEncoding) {
        return formData(url, null, responseEncoding);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url     请求地址
     * @param postMap POST参数
     * @return 响应结果
     */
    public static String formData(@NotNull String url, @Nullable Map<String, ?> postMap) {
        return formData(url, postMap, (Map<String, ?>) null);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url     请求地址
     * @param postMap POST参数
     * @return 响应结果
     */
    public static String formData(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable HttpOptions options) {
        return formData(url, postMap, (Map<String, ?>) null, options);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String formData(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable Charset responseEncoding) {
        return formData(url, postMap, (Map<String, ?>) null, responseEncoding);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String formData(@NotNull String url, @Nullable Map<String, ?> postMap,
                                  @Nullable Charset responseEncoding, @Nullable HttpOptions options) {
        return formData(url, postMap, (Map<String, ?>) null, responseEncoding, options);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url      请求地址
     * @param postMap  POST参数
     * @param queryMap URL参数
     * @return 响应结果
     */
    public static String formData(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable Map<String, ?> queryMap) {
        return formData(url, postMap, queryMap, (HttpOptions) null);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url      请求地址
     * @param postMap  POST参数
     * @param queryMap URL参数
     * @return 响应结果
     */
    public static String formData(@NotNull String url, @Nullable Map<String, ?> postMap,
                                  @Nullable Map<String, ?> queryMap, @Nullable HttpOptions options) {
        return formData(url, postMap, queryMap, (HttpHeaders) null, options);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String formData(@NotNull String url, @Nullable Map<String, ?> postMap,
                                  @Nullable Map<String, ?> queryMap, @Nullable Charset responseEncoding) {
        return formData(url, postMap, queryMap, null, responseEncoding);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String formData(@NotNull String url, @Nullable Map<String, ?> postMap,
                                  @Nullable Map<String, ?> queryMap, @Nullable Charset responseEncoding,
                                  @Nullable HttpOptions options) {
        return formData(url, postMap, queryMap, null, responseEncoding, options);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url      请求地址
     * @param postMap  POST参数
     * @param queryMap URL参数
     * @param headers  HTTP头参数
     * @return 响应结果
     */
    public static String formData(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable Map<String, ?> queryMap,
                                  @Nullable HttpHeaders headers) {
        return formData(url, postMap, queryMap, headers, (Charset) null);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url      请求地址
     * @param postMap  POST参数
     * @param queryMap URL参数
     * @param headers  HTTP头参数
     * @return 响应结果
     */
    public static String formData(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable Map<String, ?> queryMap,
                                  @Nullable HttpHeaders headers, @Nullable HttpOptions options) {
        return formData(url, postMap, queryMap, headers, (Charset) null, options);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param queryMap         URL参数
     * @param headers          HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String formData(@NotNull String url, @Nullable Map<String, ?> postMap,
                                  @Nullable Map<String, ?> queryMap, @Nullable HttpHeaders headers,
                                  @Nullable Charset responseEncoding) {
        return formData(url, postMap, queryMap, headers, responseEncoding, (HttpOptions) null);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param queryMap         URL参数
     * @param headers          HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String formData(@NotNull String url, @Nullable Map<String, ?> postMap,
                                  @Nullable Map<String, ?> queryMap, @Nullable HttpHeaders headers,
                                  @Nullable Charset responseEncoding, @Nullable HttpOptions options) {
        HttpClient httpClient = Factory.get().build(options);

        HttpRequestBody httpRequestBody = null;
        if (CollectionUtil.isNotEmpty(postMap)) {
            if (isFormData(postMap)) {
                httpRequestBody = buildHttpMultipartBody(postMap);
            } else {
                httpRequestBody = buildHttpFormBody(postMap);
            }
        }

        HttpRequest httpRequest = HttpRequest.builder()
                .url(url)
                .queryParams(queryMap)
                .headers(headers)
                .method(HttpMethod.POST)
                .body(httpRequestBody)
                .build();
        HttpResponse httpResponse = httpClient.execute(httpRequest, null);
        if (httpResponse.status() >= 200 && httpResponse.status() < 300) {
            HttpResponseBody httpResponseBody = httpResponse.body();
            return httpResponseBody.string(responseEncoding);
        }
        throw new HttpClientStatusException(httpResponse.status(), httpResponse.reason(), httpRequest);
    }

    /**
     * 判断是不是FormData
     *
     * @param postMap POST参数
     * @return {@code true}/{@code false}
     */
    public static boolean isFormData(Map<String, ?> postMap) {
        Object value;
        for (Map.Entry<String, ?> entry : postMap.entrySet()) {
            value = entry.getValue();
            if (entry.getKey() != null && value != null) {
                if (value instanceof File) {
                    return true;
                } else if (value instanceof InputStream) {
                    throw new HttpClientException("请使用Part包装输入流");
                } else if (value instanceof HttpMultipartBody.Part) {
                    return true;
                } else if (value instanceof Collection<?>) {
                    for (Object obj : (Collection<?>) value) {
                        if (obj != null) {
                            if (obj instanceof File) {
                                return true;
                            } else if (obj instanceof InputStream) {
                                throw new HttpClientException("请使用Part包装输入流");
                            } else if (obj instanceof HttpMultipartBody.Part) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static HttpFormBody buildHttpFormBody(Map<String, ?> postMap) {
        HttpFormBody.Builder builder = HttpFormBody.builder();
        Object value;
        for (Map.Entry<String, ?> entry : postMap.entrySet()) {
            value = entry.getValue();
            if (entry.getKey() != null && value != null) {
                if (value instanceof Collection<?>) {
                    for (Object obj : (Collection<?>) value) {
                        if (obj != null) {
                            builder.add(entry.getKey(), obj.toString());
                        }
                    }
                } else {
                    builder.add(entry.getKey(), value.toString());
                }
            }
        }
        return builder.build();
    }

    private static HttpMultipartBody buildHttpMultipartBody(Map<String, ?> postMap) {
        HttpMultipartBody.Builder builder = HttpMultipartBody.builder();
        String name;
        Object value;
        for (Map.Entry<String, ?> entry : postMap.entrySet()) {
            name = entry.getKey();
            value = entry.getValue();
            if (name != null && value != null) {
                if (value instanceof Collection<?>) {
                    for (Object obj : (Collection<?>) value) {
                        if (obj != null) {
                            if (obj instanceof File) {
                                builder.add(name, (File) obj);
                            } else if (obj instanceof HttpMultipartBody.Part) {
                                builder.add((HttpMultipartBody.Part) obj);
                            } else {
                                builder.add(name, obj.toString());
                            }
                        }
                    }
                } else if (value instanceof File) {
                    builder.add(name, (File) value);
                } else if (value instanceof HttpMultipartBody.Part) {
                    builder.add((HttpMultipartBody.Part) value);
                } else {
                    builder.add(name, value.toString());
                }
            }
        }
        return builder.build();
    }


    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url   请求地址
     * @param clazz 类型
     * @param <T>   泛型类型
     * @return 响应结果
     */
    public static <T> T formData(@NotNull String url, @NotNull Class<T> clazz) {
        return formData(url, (Charset) null, clazz);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T formData(@NotNull String url, @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return formData(url, null, responseEncoding, clazz);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url     请求地址
     * @param postMap POST参数
     * @param clazz   类型
     * @param <T>     泛型类型
     * @return 响应结果
     */
    public static <T> T formData(@NotNull String url, @Nullable Map<String, ?> postMap, @NotNull Class<T> clazz) {
        return formData(url, postMap, (Charset) null, clazz);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url     请求地址
     * @param postMap POST参数
     * @param clazz   类型
     * @param <T>     泛型类型
     * @return 响应结果
     */
    public static <T> T formData(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable HttpOptions options,
                                 @NotNull Class<T> clazz) {
        return formData(url, postMap, (Charset) null, options, clazz);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T formData(@NotNull String url, @Nullable Map<String, ?> postMap,
                                 @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return formData(url, postMap, null, responseEncoding, clazz);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T formData(@NotNull String url, @Nullable Map<String, ?> postMap,
                                 @Nullable Charset responseEncoding, @Nullable HttpOptions options, @NotNull Class<T> clazz) {
        return formData(url, postMap, null, responseEncoding, options, clazz);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url      请求地址
     * @param postMap  POST参数
     * @param queryMap URL参数
     * @param clazz    类型
     * @param <T>      泛型类型
     * @return 响应结果
     */
    public static <T> T formData(@NotNull String url, @Nullable Map<String, ?> postMap,
                                 @Nullable Map<String, ?> queryMap, @NotNull Class<T> clazz) {
        return formData(url, postMap, queryMap, (Charset) null, clazz);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url      请求地址
     * @param postMap  POST参数
     * @param queryMap URL参数
     * @param clazz    类型
     * @param <T>      泛型类型
     * @return 响应结果
     */
    public static <T> T formData(@NotNull String url, @Nullable Map<String, ?> postMap,
                                 @Nullable Map<String, ?> queryMap, @Nullable HttpOptions options, @NotNull Class<T> clazz) {
        return formData(url, postMap, queryMap, (Charset) null, options, clazz);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T formData(@NotNull String url, @Nullable Map<String, ?> postMap,
                                 @Nullable Map<String, ?> queryMap, @Nullable Charset responseEncoding,
                                 @NotNull Class<T> clazz) {
        return formData(url, postMap, queryMap, null, responseEncoding, clazz);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T formData(@NotNull String url, @Nullable Map<String, ?> postMap,
                                 @Nullable Map<String, ?> queryMap, @Nullable Charset responseEncoding,
                                 @Nullable HttpOptions options, @NotNull Class<T> clazz) {
        return formData(url, postMap, queryMap, null, responseEncoding, options, clazz);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url      请求地址
     * @param postMap  POST参数
     * @param queryMap URL参数
     * @param headers  HTTP头参数
     * @param clazz    类型
     * @param <T>      泛型类型
     * @return 响应结果
     */
    public static <T> T formData(@NotNull String url, Map<String, ?> postMap, @Nullable Map<String, ?> queryMap,
                                 @Nullable HttpHeaders headers, @NotNull Class<T> clazz) {
        return formData(url, postMap, queryMap, headers, (Charset) null, clazz);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url      请求地址
     * @param postMap  POST参数
     * @param queryMap URL参数
     * @param headers  HTTP头参数
     * @param clazz    类型
     * @param <T>      泛型类型
     * @return 响应结果
     */
    public static <T> T formData(@NotNull String url, Map<String, ?> postMap, @Nullable Map<String, ?> queryMap,
                                 @Nullable HttpHeaders headers, @Nullable HttpOptions options, @NotNull Class<T> clazz) {
        return formData(url, postMap, queryMap, headers, null, options, clazz);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param queryMap         URL参数
     * @param headers          HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T formData(@NotNull String url, @Nullable Map<String, ?> postMap,
                                 @Nullable Map<String, ?> queryMap, @Nullable HttpHeaders headers,
                                 @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        String response = formData(url, postMap, queryMap, headers, responseEncoding, (HttpOptions) null);
        return (StringUtil.isEmpty(response)) ? null : JSONUtil.fromJson(response, clazz);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param queryMap         URL参数
     * @param headers          HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T formData(@NotNull String url, @Nullable Map<String, ?> postMap,
                                 @Nullable Map<String, ?> queryMap, @Nullable HttpHeaders headers,
                                 @Nullable Charset responseEncoding, @Nullable HttpOptions options, @NotNull Class<T> clazz) {
        String response = formData(url, postMap, queryMap, headers, responseEncoding, options);
        return (StringUtil.isEmpty(response)) ? null : JSONUtil.fromJson(response, clazz);
    }


    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url     请求地址
     * @param typeRef 类型
     * @param <T>     泛型类型
     * @return 响应结果
     */
    public static <T> T formData(@NotNull String url, @NotNull TypeRef<T> typeRef) {
        return formData(url, (Charset) null, typeRef);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T formData(@NotNull String url, @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return formData(url, null, responseEncoding, typeRef);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url     请求地址
     * @param postMap POST参数
     * @param typeRef 类型
     * @param <T>     泛型类型
     * @return 响应结果
     */
    public static <T> T formData(@NotNull String url, @Nullable Map<String, ?> postMap, @NotNull TypeRef<T> typeRef) {
        return formData(url, postMap, (Map<String, ?>) null, typeRef);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url     请求地址
     * @param postMap POST参数
     * @param typeRef 类型
     * @param <T>     泛型类型
     * @return 响应结果
     */
    public static <T> T formData(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable HttpOptions options,
                                 @NotNull TypeRef<T> typeRef) {
        return formData(url, postMap, (Map<String, ?>) null, options, typeRef);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T formData(@NotNull String url, @Nullable Map<String, ?> postMap,
                                 @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return formData(url, postMap, null, responseEncoding, typeRef);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T formData(@NotNull String url, @Nullable Map<String, ?> postMap,
                                 @Nullable Charset responseEncoding, @Nullable HttpOptions options,
                                 @NotNull TypeRef<T> typeRef) {
        return formData(url, postMap, null, responseEncoding, options, typeRef);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url      请求地址
     * @param postMap  POST参数
     * @param queryMap URL参数
     * @param typeRef  类型
     * @param <T>      泛型类型
     * @return 响应结果
     */
    public static <T> T formData(@NotNull String url, @Nullable Map<String, ?> postMap,
                                 @Nullable Map<String, ?> queryMap, @NotNull TypeRef<T> typeRef) {
        return formData(url, postMap, queryMap, (HttpHeaders) null, typeRef);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url      请求地址
     * @param postMap  POST参数
     * @param queryMap URL参数
     * @param typeRef  类型
     * @param <T>      泛型类型
     * @return 响应结果
     */
    public static <T> T formData(@NotNull String url, @Nullable Map<String, ?> postMap,
                                 @Nullable Map<String, ?> queryMap, @Nullable HttpOptions options, @NotNull TypeRef<T> typeRef) {
        return formData(url, postMap, queryMap, (HttpHeaders) null, options, typeRef);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T formData(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable Map<String, ?> queryMap,
                                 @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return formData(url, postMap, queryMap, null, responseEncoding, typeRef);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @param options          HTTP配置
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T formData(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable Map<String, ?> queryMap,
                                 @Nullable Charset responseEncoding, @Nullable HttpOptions options, @NotNull TypeRef<T> typeRef) {
        return formData(url, postMap, queryMap, null, responseEncoding, options, typeRef);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url      请求地址
     * @param postMap  POST参数
     * @param queryMap URL参数
     * @param headers  HTTP头参数
     * @param typeRef  类型
     * @param <T>      泛型类型
     * @return 响应结果
     */
    public static <T> T formData(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable Map<String, ?> queryMap,
                                 @Nullable HttpHeaders headers, @NotNull TypeRef<T> typeRef) {
        return formData(url, postMap, queryMap, headers, (HttpOptions) null, typeRef);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url      请求地址
     * @param postMap  POST参数
     * @param queryMap URL参数
     * @param headers  HTTP头参数
     * @param options  HTTP配置
     * @param typeRef  类型
     * @param <T>      泛型类型
     * @return 响应结果
     */
    public static <T> T formData(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable Map<String, ?> queryMap,
                                 @Nullable HttpHeaders headers, @Nullable HttpOptions options, @NotNull TypeRef<T> typeRef) {
        return formData(url, postMap, queryMap, headers, null, options, typeRef);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param queryMap         URL参数
     * @param headers          HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T formData(@NotNull String url, @Nullable Map<String, ?> postMap,
                                 @Nullable Map<String, ?> queryMap, @Nullable HttpHeaders headers,
                                 @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return formData(url, postMap, queryMap, headers, responseEncoding, null, typeRef);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param queryMap         URL参数
     * @param headers          HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param options          HTTP配置
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T formData(@NotNull String url, @Nullable Map<String, ?> postMap,
                                 @Nullable Map<String, ?> queryMap, @Nullable HttpHeaders headers,
                                 @Nullable Charset responseEncoding, @Nullable HttpOptions options, @NotNull TypeRef<T> typeRef) {
        String response = formData(url, postMap, queryMap, headers, responseEncoding, options);
        return (StringUtil.isEmpty(response)) ? null : JSONUtil.fromJson(response, typeRef);
    }

    // endregion

}
