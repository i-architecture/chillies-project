package com.ijiagoushi.chillies.http;

import com.ijiagoushi.chillies.core.http.ContentType;
import com.ijiagoushi.chillies.core.io.FileUtil;
import com.ijiagoushi.chillies.core.io.IOUtil;
import com.ijiagoushi.chillies.core.lang.CharsetUtil;
import com.ijiagoushi.chillies.core.lang.CollectionUtil;
import com.ijiagoushi.chillies.core.lang.StringUtil;
import com.ijiagoushi.chillies.http.constants.HttpMethod;
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
import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Http的工具类
 * https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Methods/GET
 *
 * @author miles.tang at 2021-02-01
 * @since 1.0
 */
public class HttpUtil {

    // region GET 请求

    /**
     * 发起GET请求，并将响应结果转为字符串
     *
     * @param url 请求地址
     * @return 响应内容
     */
    public static String get(@NotNull String url) {
        return get(url, (Charset) null);
    }

    /**
     * 发起GET请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @return 响应内容
     */
    public static String get(@NotNull String url, @Nullable Charset responseEncoding) {
        return get(url, null, responseEncoding);
    }

    /**
     * 发起GET请求，并将响应结果转为字符串
     *
     * @param url      请求地址
     * @param queryMap URL参数
     * @return 响应内容
     */
    public static String get(@NotNull String url, @Nullable Map<String, ?> queryMap) {
        return get(url, queryMap, (Charset) null);
    }

    /**
     * 发起GET请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应内容
     */
    public static String get(@NotNull String url, @Nullable Map<String, ?> queryMap, @Nullable Charset responseEncoding) {
        return get(url, queryMap, null, responseEncoding);
    }

    /**
     * 发起GET请求，并将响应结果转为字符串
     *
     * @param url       请求地址
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @return 响应内容
     */
    public static String get(@NotNull String url, @Nullable Map<String, ?> queryMap, @Nullable Map<String, ?> headerMap) {
        return get(url, queryMap, headerMap, (Charset) null);
    }

    /**
     * 发起GET请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应内容
     */
    public static String get(@NotNull String url, @Nullable Map<String, ?> queryMap, @Nullable Map<String, ?> headerMap,
                             @Nullable Charset responseEncoding) {
        HttpClient httpClient = Factory.get().build(null);
        HttpRequest httpRequest = HttpRequest.builder()
                .url(url)
                .queryParams(queryMap)
                .headers(headerMap)
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
    public static <T> T get(@NotNull String url, @NotNull Class<T> clazz) {
        return get(url, (Charset) null, clazz);
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
    public static <T> T get(@NotNull String url, @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return get(url, null, null, responseEncoding, clazz);
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
    public static <T> T get(@NotNull String url, @Nullable Map<String, ?> queryMap, @NotNull Class<T> clazz) {
        return get(url, queryMap, (Charset) null, clazz);
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
    public static <T> T get(@NotNull String url, @Nullable Map<String, ?> queryMap, @Nullable Charset responseEncoding,
                            @NotNull Class<T> clazz) {
        return get(url, queryMap, null, responseEncoding, clazz);
    }

    /**
     * 发起GET请求，并将响应结果转为JavaBean对象
     *
     * @param url       请求地址
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @param clazz     类型
     * @param <T>       模板实际类型
     * @return 响应内容
     */
    public static <T> T get(@NotNull String url, @Nullable Map<String, ?> queryMap, @Nullable Map<String, ?> headerMap,
                            @NotNull Class<T> clazz) {
        return get(url, queryMap, headerMap, null, clazz);
    }

    /**
     * 发起GET请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              模板实际类型
     * @return 响应内容
     */
    public static <T> T get(@NotNull String url, @Nullable Map<String, ?> queryMap, @Nullable Map<String, ?> headerMap,
                            @Nullable Charset responseEncoding, Class<T> clazz) {
        String response = get(url, queryMap, headerMap, responseEncoding);
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
    public static <T> T get(@NotNull String url, @NotNull TypeRef<T> typeRef) {
        return get(url, (Charset) null, typeRef);
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
    public static <T> T get(@NotNull String url, @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return get(url, null, responseEncoding, typeRef);
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
    public static <T> T get(@NotNull String url, @Nullable Map<String, ?> queryMap, @NotNull TypeRef<T> typeRef) {
        return get(url, queryMap, (Map<String, ?>) null, typeRef);
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
    public static <T> T get(@NotNull String url, @Nullable Map<String, ?> queryMap, @Nullable Charset responseEncoding,
                            @NotNull TypeRef<T> typeRef) {
        return get(url, queryMap, null, responseEncoding, typeRef);
    }

    /**
     * 发起GET请求，并将响应结果转为JavaBean对象
     *
     * @param url       请求地址
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @param typeRef   泛型类型包装类
     * @param <T>       目标类型
     * @return 响应内容
     */
    public static <T> T get(@NotNull String url, @Nullable Map<String, ?> queryMap, @Nullable Map<String, ?> headerMap,
                            @NotNull TypeRef<T> typeRef) {
        return get(url, queryMap, headerMap, null, typeRef);
    }

    /**
     * 发起GET请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          泛型类型包装类
     * @param <T>              目标类型
     * @return 响应内容
     */
    public static <T> T get(@NotNull String url, @Nullable Map<String, ?> queryMap, @Nullable Map<String, ?> headerMap,
                            @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        String response = get(url, queryMap, headerMap, responseEncoding);
        return (StringUtil.isEmpty(response)) ? null : JSONUtil.fromJson(response, typeRef);
    }

    // endregion


    // region x-www-form-urlencoded POST

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为字符串
     *
     * @param url 请求地址
     * @return 响应结果
     */
    public static String urlPost(@NotNull String url) {
        return urlPost(url, (Charset) null);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String urlPost(@NotNull String url, @Nullable Charset responseEncoding) {
        return urlPost(url, (Map<String, ?>) null, responseEncoding);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为字符串
     *
     * @param url     请求地址
     * @param postMap POST参数
     * @return 响应结果
     */
    public static String urlPost(@NotNull String url, @Nullable Map<String, ?> postMap) {
        return urlPost(url, postMap, (Map<String, ?>) null);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String urlPost(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable Charset responseEncoding) {
        return urlPost(url, postMap, null, responseEncoding);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为字符串
     *
     * @param url       请求地址
     * @param postMap   POST参数
     * @param headerMap HTTP头参数
     * @return 响应结果
     */
    public static String urlPost(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable Map<String, ?> headerMap) {
        return urlPost(url, postMap, headerMap, (Charset) null);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String urlPost(@NotNull String url, @Nullable Map<String, ?> postMap,
                                 @Nullable Map<String, ?> headerMap, @Nullable Charset responseEncoding) {
        HttpClient httpClient = Factory.get().build(null);
        HttpRequest httpRequest = HttpRequest.builder()
                .url(url)
                .queryParams(postMap)
                .headers(headerMap)
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
    public static <T> T urlPost(@NotNull String url, @NotNull Class<T> clazz) {
        return urlPost(url, (Charset) null, clazz);
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
    public static <T> T urlPost(@NotNull String url, @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return urlPost(url, null, responseEncoding, clazz);
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
    public static <T> T urlPost(@NotNull String url, @Nullable Map<String, ?> postMap, @NotNull Class<T> clazz) {
        return urlPost(url, postMap, postMap, null, clazz);
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
    public static <T> T urlPost(@NotNull String url, Map<String, ?> postMap, @Nullable Charset responseEncoding,
                                @NotNull Class<T> clazz) {
        return urlPost(url, postMap, null, responseEncoding, clazz);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param responseEncoding 指定响应内容的编码
     * @param headerMap        HTTP头参数
     * @param clazz            类型
     * @param <T>              元素类型
     * @return 响应结果
     */
    public static <T> T urlPost(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable Map<String, ?> headerMap,
                                @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        String response = urlPost(url, postMap, headerMap, responseEncoding);
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
    public static <T> T urlPost(@NotNull String url, @NotNull TypeRef<T> typeRef) {
        return urlPost(url, (Charset) null, typeRef);
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
    public static <T> T urlPost(@NotNull String url, @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return urlPost(url, null, responseEncoding, typeRef);
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
    public static <T> T urlPost(@NotNull String url, @Nullable Map<String, ?> postMap, @NotNull TypeRef<T> typeRef) {
        return urlPost(url, postMap, (Map<String, ?>) null, typeRef);
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
    public static <T> T urlPost(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable Charset responseEncoding,
                                @NotNull TypeRef<T> typeRef) {
        return urlPost(url, postMap, null, responseEncoding, typeRef);
    }


    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为JavaBean对象
     *
     * @param url       请求地址
     * @param postMap   POST参数
     * @param headerMap HTTP头参数
     * @param typeRef   类型
     * @param <T>       元素类型
     * @return 响应结果
     */
    public static <T> T urlPost(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable Map<String, ?> headerMap,
                                @NotNull TypeRef<T> typeRef) {
        return urlPost(url, postMap, headerMap, null, typeRef);
    }

    /**
     * 发起POST(x-www-form-urlencoded)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              元素类型
     * @return 响应结果
     */
    public static <T> T urlPost(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable Map<String, ?> headerMap,
                                @Nullable Charset responseEncoding, TypeRef<T> typeRef) {
        String response = urlPost(url, postMap, headerMap, responseEncoding);
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
    public static String formPost(@NotNull String url) {
        return formPost(url, (Charset) null);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String formPost(@NotNull String url, @Nullable Charset responseEncoding) {
        return formPost(url, null, responseEncoding);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url     请求地址
     * @param postMap POST参数
     * @return 响应结果
     */
    public static String formPost(@NotNull String url, @Nullable Map<String, ?> postMap) {
        return formPost(url, postMap, (Map<String, ?>) null);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String formPost(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable Charset responseEncoding) {
        return formPost(url, postMap, (Map<String, ?>) null, responseEncoding);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url      请求地址
     * @param postMap  POST参数
     * @param queryMap URL参数
     * @return 响应结果
     */
    public static String formPost(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable Map<String, ?> queryMap) {
        return formPost(url, postMap, queryMap, (Map<String, ?>) null);
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
    public static String formPost(@NotNull String url, @Nullable Map<String, ?> postMap,
                                  @Nullable Map<String, ?> queryMap, @Nullable Charset responseEncoding) {
        return formPost(url, postMap, queryMap, null, responseEncoding);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url       请求地址
     * @param postMap   POST参数
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @return 响应结果
     */
    public static String formPost(@NotNull String url, Map<String, ?> postMap, Map<String, ?> queryMap,
                                  Map<String, ?> headerMap) {
        return formPost(url, postMap, queryMap, headerMap, (Charset) null);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String formPost(@NotNull String url, @Nullable Map<String, ?> postMap,
                                  @Nullable Map<String, ?> queryMap, @Nullable Map<String, ?> headerMap,
                                  @Nullable Charset responseEncoding) {
        HttpClient httpClient = Factory.get().build(null);

        HttpFormBody httpFormBody = null;
        if (CollectionUtil.isNotEmpty(postMap)) {
            HttpFormBody.Builder builder = HttpFormBody.builder();
            postMap.forEach((BiConsumer<String, Object>) (name, values) -> {
                if (StringUtil.hasLength(name) && values != null) {
                    if (values instanceof Collection<?>) {
                        ((Collection<?>) values).forEach((Consumer<Object>) value -> builder.add(name, value.toString()));
                    } else {
                        builder.add(name, values.toString());
                    }
                }
            });
            httpFormBody = builder.build();
        }

        HttpRequest httpRequest = HttpRequest.builder()
                .url(url)
                .queryParams(queryMap)
                .headers(headerMap)
                .method(HttpMethod.POST)
                .body(httpFormBody)
                .build();
        HttpResponse httpResponse = httpClient.execute(httpRequest, null);
        if (httpResponse.status() >= 200 && httpResponse.status() < 300) {
            HttpResponseBody httpResponseBody = httpResponse.body();
            return httpResponseBody.string(responseEncoding);
        }
        throw new HttpClientStatusException(httpResponse.status(), httpResponse.reason(), httpRequest);
    }


    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url   请求地址
     * @param clazz 类型
     * @param <T>   泛型类型
     * @return 响应结果
     */
    public static <T> T formPost(@NotNull String url, @NotNull Class<T> clazz) {
        return formPost(url, (Charset) null, clazz);
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
    public static <T> T formPost(@NotNull String url, @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return formPost(url, null, responseEncoding, clazz);
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
    public static <T> T formPost(@NotNull String url, @Nullable Map<String, ?> postMap, @NotNull Class<T> clazz) {
        return formPost(url, postMap, (Charset) null, clazz);
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
    public static <T> T formPost(@NotNull String url, @Nullable Map<String, ?> postMap,
                                 @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return formPost(url, postMap, null, responseEncoding, clazz);
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
    public static <T> T formPost(@NotNull String url, @Nullable Map<String, ?> postMap,
                                 @Nullable Map<String, ?> queryMap, @NotNull Class<T> clazz) {
        return formPost(url, postMap, queryMap, (Charset) null, clazz);
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
    public static <T> T formPost(@NotNull String url, @Nullable Map<String, ?> postMap,
                                 @Nullable Map<String, ?> queryMap, @Nullable Charset responseEncoding,
                                 @NotNull Class<T> clazz) {
        return formPost(url, postMap, queryMap, null, responseEncoding, clazz);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url       请求地址
     * @param postMap   POST参数
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @param clazz     类型
     * @param <T>       泛型类型
     * @return 响应结果
     */
    public static <T> T formPost(@NotNull String url, Map<String, ?> postMap, Map<String, ?> queryMap,
                                 Map<String, ?> headerMap, Class<T> clazz) {
        return formPost(url, postMap, queryMap, headerMap, null, clazz);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T formPost(@NotNull String url, @Nullable Map<String, ?> postMap,
                                 @Nullable Map<String, ?> queryMap, @Nullable Map<String, ?> headerMap,
                                 @Nullable Charset responseEncoding, Class<T> clazz) {
        String response = formPost(url, postMap, queryMap, headerMap, responseEncoding);
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
    public static <T> T formPost(@NotNull String url, @NotNull TypeRef<T> typeRef) {
        return formPost(url, (Charset) null, typeRef);
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
    public static <T> T formPost(@NotNull String url, @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return formPost(url, null, responseEncoding, typeRef);
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
    public static <T> T formPost(@NotNull String url, @Nullable Map<String, ?> postMap, @NotNull TypeRef<T> typeRef) {
        return formPost(url, postMap, (Map<String, ?>) null, typeRef);
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
    public static <T> T formPost(@NotNull String url, @Nullable Map<String, ?> postMap,
                                 @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return formPost(url, postMap, null, responseEncoding, typeRef);
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
    public static <T> T formPost(@NotNull String url, @Nullable Map<String, ?> postMap,
                                 @Nullable Map<String, ?> queryMap, @NotNull TypeRef<T> typeRef) {
        return formPost(url, postMap, queryMap, (Map<String, ?>) null, typeRef);
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
    public static <T> T formPost(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable Map<String, ?> queryMap,
                                 @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return formPost(url, postMap, queryMap, null, responseEncoding, typeRef);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url       请求地址
     * @param postMap   POST参数
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @param typeRef   类型
     * @param <T>       泛型类型
     * @return 响应结果
     */
    public static <T> T formPost(@NotNull String url, @Nullable Map<String, ?> postMap, @Nullable Map<String, ?> queryMap,
                                 @Nullable Map<String, ?> headerMap, @NotNull TypeRef<T> typeRef) {
        return formPost(url, postMap, queryMap, headerMap, null, typeRef);
    }

    /**
     * 发起POST(form-data)请求，即表单请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param postMap          POST参数
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T formPost(@NotNull String url, @Nullable Map<String, ?> postMap,
                                 @Nullable Map<String, ?> queryMap, @Nullable Map<String, ?> headerMap,
                                 @Nullable Charset responseEncoding, TypeRef<T> typeRef) {
        String response = formPost(url, postMap, queryMap, headerMap, responseEncoding);
        return (StringUtil.isEmpty(response)) ? null : JSONUtil.fromJson(response, typeRef);
    }

    // endregion


    // region Text RawBody POST

    /**
     * 发起POST(text raw body)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url 请求地址
     * @return 响应结果
     */
    public static String textRawBody(@NotNull String url) {
        return textRawBody(url, (Charset) null);
    }

    /**
     * 发起POST(text raw body)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String textRawBody(@NotNull String url, @Nullable Charset responseEncoding) {
        return textRawBody(url, responseEncoding, HttpBodyMethod.POST);
    }

    /**
     * 发起POST(text raw body)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String textRawBody(@NotNull String url, @NotNull HttpBodyMethod bodyMethod) {
        return textRawBody(url, (Charset) null, bodyMethod);
    }

    /**
     * 发起POST(text raw body)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @param bodyMethod       HTTP请求方式
     * @return 响应结果
     */
    public static String textRawBody(@NotNull String url, @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding) {
        return textRawBody(url, null, bodyMethod, responseEncoding);
    }

    /**
     * 发起POST(text raw body)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url  请求地址
     * @param body BODY内容
     * @return 响应结果
     */
    public static String textRawBody(@NotNull String url, @Nullable Object body) {
        return textRawBody(url, body, (Charset) null);
    }

    /**
     * 发起POST(text raw body)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String textRawBody(@NotNull String url, @Nullable Object body, @Nullable Charset responseEncoding) {
        return textRawBody(url, body, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(text raw body)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String textRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod) {
        return textRawBody(url, body, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(text raw body)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String textRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                     @Nullable Charset responseEncoding) {
        return textRawBody(url, body, null, bodyMethod, responseEncoding);
    }

    /**
     * 发起POST(text raw body)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url      请求地址
     * @param body     BODY内容
     * @param queryMap URL参数
     * @return 响应结果
     */
    public static String textRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap) {
        return textRawBody(url, body, queryMap, HttpBodyMethod.POST);
    }

    /**
     * 发起POST(text raw body)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String textRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                     @Nullable Charset responseEncoding) {
        return textRawBody(url, body, queryMap, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(text raw body)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String textRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                     @NotNull HttpBodyMethod bodyMethod) {
        return textRawBody(url, body, queryMap, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(text raw body)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String textRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                     @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding) {
        return textRawBody(url, body, queryMap, null, bodyMethod, responseEncoding);
    }

    /**
     * 发起POST(text raw body)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url       请求地址
     * @param body      BODY内容
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @return 响应结果
     */
    public static String textRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                     @Nullable Map<String, ?> headerMap) {
        return textRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST);
    }

    /**
     * 发起POST(text raw body)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param headerMap  HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String textRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                     @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod) {
        return textRawBody(url, body, queryMap, headerMap, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(text raw body)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String textRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                     @Nullable Map<String, ?> headerMap, @Nullable Charset responseEncoding) {
        return textRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(text raw body)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String textRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                     @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod,
                                     @Nullable Charset responseEncoding) {
        HttpClient httpClient = Factory.get().build(null);

        HttpRequestBody requestBody = null;
        if (body != null) {
            if (body instanceof String) {
                requestBody = HttpRequestBody.create(ContentType.DEFAULT_TEXT, (String) body);
            } else if (body instanceof InputStream) {
                requestBody = HttpRequestBody.create(ContentType.DEFAULT_TEXT, (InputStream) body);
            } else if (body instanceof File) {
                requestBody = HttpRequestBody.create(ContentType.DEFAULT_TEXT, (File) body);
            } else {
                requestBody = HttpRequestBody.create(ContentType.DEFAULT_TEXT, JSONUtil.toJson(body));
            }
        }

        HttpRequest httpRequest = HttpRequest.builder()
                .url(url)
                .queryParams(queryMap)
                .headers(headerMap)
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
     * 发起POST(text raw body)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url   请求地址
     * @param clazz 类型
     * @param <T>   泛型类型
     * @return 响应结果
     */
    public static <T> T textRawBody(@NotNull String url, @NotNull Class<T> clazz) {
        return textRawBody(url, (Charset) null, clazz);
    }

    /**
     * 发起POST(text raw body)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T textRawBody(@NotNull String url, @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return textRawBody(url, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(text raw body)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param bodyMethod HTTP请求方式
     * @param clazz      类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T textRawBody(@NotNull String url, @NotNull HttpBodyMethod bodyMethod, @NotNull Class<T> clazz) {
        return textRawBody(url, bodyMethod, (Charset) null, clazz);
    }

    /**
     * 发起POST(text raw body)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T textRawBody(@NotNull String url, @NotNull HttpBodyMethod bodyMethod,
                                    @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return textRawBody(url, null, bodyMethod, responseEncoding, clazz);
    }

    /**
     * 发起POST(text raw body)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url   请求地址
     * @param body  BODY内容
     * @param clazz 类型
     * @param <T>   泛型类型
     * @return 响应结果
     */
    public static <T> T textRawBody(@NotNull String url, @Nullable Object body, @NotNull Class<T> clazz) {
        return textRawBody(url, body, HttpBodyMethod.POST, clazz);
    }

    /**
     * 发起POST(text raw body)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T textRawBody(@NotNull String url, @Nullable Object body, @Nullable Charset responseEncoding,
                                    @NotNull Class<T> clazz) {
        return textRawBody(url, body, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(text raw body)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param bodyMethod HTTP请求方式
     * @param clazz      类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T textRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                    @NotNull Class<T> clazz) {
        return textRawBody(url, body, bodyMethod, null, clazz);
    }

    /**
     * 发起POST(text raw body)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T textRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                    @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return textRawBody(url, body, null, bodyMethod, responseEncoding, clazz);
    }

    /**
     * 发起POST(text raw body)请求，即表单请求，并将响应结果转为字符串
     *
     * @param url      请求地址
     * @param body     BODY内容
     * @param queryMap URL参数
     * @param clazz    类型
     * @param <T>      泛型类型
     * @return 响应结果
     */
    public static <T> T textRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                    @NotNull Class<T> clazz) {
        return textRawBody(url, body, queryMap, HttpBodyMethod.POST, clazz);
    }

    /**
     * 发起POST(Text Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param bodyMethod HTTP请求方式
     * @param clazz      类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T textRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                    @NotNull HttpBodyMethod bodyMethod, @NotNull Class<T> clazz) {
        return textRawBody(url, body, queryMap, bodyMethod, null, clazz);
    }

    /**
     * 发起POST(Text Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T textRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                    @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return textRawBody(url, body, queryMap, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(Text Raw Body)请求，并将响应结果转为JavaBean对象
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
    public static <T> T textRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                    @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return textRawBody(url, body, queryMap, null, bodyMethod, responseEncoding, clazz);
    }

    /**
     * 发起POST(Text Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url       请求地址
     * @param body      BODY内容
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @param clazz     类型
     * @param <T>       泛型类型
     * @return 响应结果
     */
    public static <T> T textRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @NotNull Class<T> clazz) {
        return textRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST, clazz);
    }

    /**
     * 发起POST(Text Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param headerMap  HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @param clazz      类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T textRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod, @NotNull Class<T> clazz) {
        return textRawBody(url, body, queryMap, headerMap, bodyMethod, null, clazz);
    }

    /**
     * 发起POST(Text Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T textRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return textRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(Text Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T textRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod,
                                    @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        String text = textRawBody(url, body, queryMap, headerMap, bodyMethod, responseEncoding);
        return StringUtil.isEmpty(text) ? null : JSONUtil.fromJson(text, clazz);
    }


    /**
     * 发起POST(Text Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url     请求地址
     * @param typeRef 类型
     * @param <T>     泛型类型
     * @return 响应结果
     */
    public static <T> T textRawBody(@NotNull String url, @NotNull TypeRef<T> typeRef) {
        return textRawBody(url, (Charset) null, typeRef);
    }

    /**
     * 发起POST(Text Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T textRawBody(@NotNull String url, @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return textRawBody(url, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(Text Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T textRawBody(@NotNull String url, @NotNull HttpBodyMethod bodyMethod, @NotNull TypeRef<T> typeRef) {
        return textRawBody(url, bodyMethod, (Charset) null, typeRef);
    }

    /**
     * 发起POST(Text Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url     请求地址
     * @param body    BODY内容
     * @param typeRef 类型
     * @param <T>     泛型类型
     * @return 响应结果
     */
    public static <T> T textRawBody(@NotNull String url, @Nullable Object body, @NotNull TypeRef<T> typeRef) {
        return textRawBody(url, body, HttpBodyMethod.POST, typeRef);
    }

    /**
     * 发起POST(Text Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T textRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                    @NotNull TypeRef<T> typeRef) {
        return textRawBody(url, body, bodyMethod, (Charset) null, typeRef);
    }

    /**
     * 发起POST(Text Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T textRawBody(@NotNull String url, @Nullable Object body, @Nullable Charset responseEncoding,
                                    @NotNull TypeRef<T> typeRef) {
        return textRawBody(url, body, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(Text Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T textRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                    @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return textRawBody(url, body, null, bodyMethod, responseEncoding, typeRef);
    }

    /**
     * 发起POST(Text Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url      请求地址
     * @param body     BODY内容
     * @param queryMap URL参数
     * @param typeRef  类型
     * @param <T>      泛型类型
     * @return 响应结果
     */
    public static <T> T textRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                    @NotNull TypeRef<T> typeRef) {
        return textRawBody(url, body, queryMap, HttpBodyMethod.POST, typeRef);
    }

    /**
     * 发起POST(Text Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T textRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                    @NotNull HttpBodyMethod bodyMethod, @NotNull TypeRef<T> typeRef) {
        return textRawBody(url, body, queryMap, bodyMethod, null, typeRef);
    }

    /**
     * 发起POST(Text Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T textRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                    @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return textRawBody(url, body, queryMap, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(Text Raw Body)请求，并将响应结果转为JavaBean对象
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
    public static <T> T textRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                    @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return textRawBody(url, body, queryMap, null, bodyMethod, responseEncoding, typeRef);
    }

    /**
     * 发起POST(Text Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url       请求地址
     * @param body      BODY内容
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @param typeRef   类型
     * @param <T>       泛型类型
     * @return 响应结果
     */
    public static <T> T textRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @NotNull TypeRef<T> typeRef) {
        return textRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST, typeRef);
    }

    /**
     * 发起POST(Text Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param headerMap  HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T textRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod, @NotNull TypeRef<T> typeRef) {
        return textRawBody(url, body, queryMap, headerMap, bodyMethod, null, typeRef);
    }

    /**
     * 发起POST(Text Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T textRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @Nullable Charset responseEncoding,
                                    @NotNull TypeRef<T> typeRef) {
        return textRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(Text Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T textRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod,
                                    @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        String text = textRawBody(url, body, queryMap, headerMap, bodyMethod, responseEncoding);
        return StringUtil.isEmpty(text) ? null : JSONUtil.fromJson(text, typeRef);
    }

    // endregion


    // region JSON RawBody POST

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url 请求地址
     * @return 响应结果
     */
    public static String jsonRawBody(@NotNull String url) {
        return jsonRawBody(url, (Charset) null);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String jsonRawBody(@NotNull String url, @Nullable Charset responseEncoding) {
        return jsonRawBody(url, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String jsonRawBody(@NotNull String url, @NotNull HttpBodyMethod bodyMethod) {
        return jsonRawBody(url, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url  请求地址
     * @param body BODY内容
     * @return 响应结果
     */
    public static String jsonRawBody(@NotNull String url, @Nullable Object body) {
        return jsonRawBody(url, body, HttpBodyMethod.POST);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String jsonRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod) {
        return jsonRawBody(url, body, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String jsonRawBody(@NotNull String url, @Nullable Object body, @Nullable Charset responseEncoding) {
        return jsonRawBody(url, body, HttpBodyMethod.POST, responseEncoding);
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
    public static String jsonRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                     @Nullable Charset responseEncoding) {
        return jsonRawBody(url, body, null, bodyMethod, responseEncoding);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url      请求地址
     * @param body     BODY内容
     * @param queryMap URL参数
     * @return 响应结果
     */
    public static String jsonRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap) {
        return jsonRawBody(url, body, queryMap, HttpBodyMethod.POST);
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
    public static String jsonRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                     @NotNull HttpBodyMethod bodyMethod) {
        return jsonRawBody(url, body, queryMap, bodyMethod, (Charset) null);
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
    public static String jsonRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                     @Nullable Charset responseEncoding) {
        return jsonRawBody(url, body, queryMap, HttpBodyMethod.POST, responseEncoding);
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
    public static String jsonRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                     @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding) {
        return jsonRawBody(url, body, queryMap, null, bodyMethod, responseEncoding);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url       请求地址
     * @param body      BODY内容
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @return 响应结果
     */
    public static String jsonRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                     @Nullable Map<String, ?> headerMap) {
        return jsonRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param headerMap  HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String jsonRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                     @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod) {
        return jsonRawBody(url, body, queryMap, headerMap, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String jsonRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                     @Nullable Map<String, ?> headerMap, @Nullable Charset responseEncoding) {
        return jsonRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String jsonRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                     @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod,
                                     @Nullable Charset responseEncoding) {
        HttpClient httpClient = Factory.get().build(null);

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
                .headers(headerMap)
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
    public static <T> T jsonRawBody(@NotNull String url, @NotNull Class<T> clazz) {
        return jsonRawBody(url, (Charset) null, clazz);
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
    public static <T> T jsonRawBody(@NotNull String url, @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return jsonRawBody(url, HttpBodyMethod.POST, responseEncoding, clazz);
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
    public static <T> T jsonRawBody(@NotNull String url, @NotNull HttpBodyMethod bodyMethod, @NotNull Class<T> clazz) {
        return jsonRawBody(url, null, bodyMethod, clazz);
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
    public static <T> T jsonRawBody(@NotNull String url, @Nullable Object body, @NotNull Class<T> clazz) {
        return jsonRawBody(url, body, HttpBodyMethod.POST, clazz);
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
    public static <T> T jsonRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                    @NotNull Class<T> clazz) {
        return jsonRawBody(url, body, bodyMethod, null, clazz);
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
    public static <T> T jsonRawBody(@NotNull String url, @Nullable Object body, @Nullable Charset responseEncoding,
                                    @NotNull Class<T> clazz) {
        return jsonRawBody(url, body, HttpBodyMethod.POST, responseEncoding, clazz);
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
    public static <T> T jsonRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                    @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return jsonRawBody(url, body, null, bodyMethod, responseEncoding, clazz);
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
    public static <T> T jsonRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                    @NotNull Class<T> clazz) {
        return jsonRawBody(url, body, queryMap, HttpBodyMethod.POST, clazz);
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
    public static <T> T jsonRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                    @NotNull HttpBodyMethod bodyMethod, @NotNull Class<T> clazz) {
        return jsonRawBody(url, body, queryMap, bodyMethod, null, clazz);
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
    public static <T> T jsonRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                    @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return jsonRawBody(url, body, queryMap, HttpBodyMethod.POST, responseEncoding, clazz);
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
    public static <T> T jsonRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                    @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return jsonRawBody(url, body, queryMap, null, bodyMethod, responseEncoding, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url       请求地址
     * @param body      BODY内容
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @param clazz     类型
     * @param <T>       泛型类型
     * @return 响应结果
     */
    public static <T> T jsonRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @NotNull Class<T> clazz) {
        return jsonRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param headerMap  HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @param clazz      类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T jsonRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod, @NotNull Class<T> clazz) {
        return jsonRawBody(url, body, queryMap, headerMap, bodyMethod, null, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T jsonRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @Nullable Charset responseEncoding,
                                    @NotNull Class<T> clazz) {
        return jsonRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T jsonRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod,
                                    @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        String text = jsonRawBody(url, body, queryMap, headerMap, bodyMethod, responseEncoding);
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
    public static <T> T jsonRawBody(@NotNull String url, @NotNull TypeRef<T> typeRef) {
        return jsonRawBody(url, (Charset) null, typeRef);
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
    public static <T> T jsonRawBody(@NotNull String url, @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return jsonRawBody(url, null, responseEncoding, typeRef);
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
    public static <T> T jsonRawBody(@NotNull String url, @NotNull HttpBodyMethod bodyMethod, @NotNull TypeRef<T> typeRef) {
        return jsonRawBody(url, bodyMethod, (Charset) null, typeRef);
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
    public static <T> T jsonRawBody(@NotNull String url, @Nullable Object body, @NotNull TypeRef<T> typeRef) {
        return jsonRawBody(url, body, HttpBodyMethod.POST, typeRef);
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
    public static <T> T jsonRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                    @NotNull TypeRef<T> typeRef) {
        return jsonRawBody(url, body, bodyMethod, null, typeRef);
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
    public static <T> T jsonRawBody(@NotNull String url, @Nullable Object body, @Nullable Charset responseEncoding,
                                    @NotNull TypeRef<T> typeRef) {
        return jsonRawBody(url, body, HttpBodyMethod.POST, responseEncoding, typeRef);
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
    public static <T> T jsonRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                    @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return jsonRawBody(url, body, null, bodyMethod, responseEncoding, typeRef);
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
    public static <T> T jsonRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                    @NotNull TypeRef<T> typeRef) {
        return jsonRawBody(url, body, queryMap, HttpBodyMethod.POST, typeRef);
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
    public static <T> T jsonRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                    @NotNull HttpBodyMethod bodyMethod, @NotNull TypeRef<T> typeRef) {
        return jsonRawBody(url, body, queryMap, bodyMethod, null, typeRef);
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
    public static <T> T jsonRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                    @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return jsonRawBody(url, body, queryMap, HttpBodyMethod.POST, responseEncoding, typeRef);
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
    public static <T> T jsonRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                    @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding,
                                    @NotNull TypeRef<T> typeRef) {
        return jsonRawBody(url, body, queryMap, null, bodyMethod, responseEncoding, typeRef);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url       请求地址
     * @param body      BODY内容
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @param typeRef   类型
     * @param <T>       泛型类型
     * @return 响应结果
     */
    public static <T> T jsonRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @NotNull TypeRef<T> typeRef) {
        return jsonRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST, typeRef);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param headerMap  HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T jsonRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod,
                                    @NotNull TypeRef<T> typeRef) {
        return jsonRawBody(url, body, queryMap, headerMap, bodyMethod, null, typeRef);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T jsonRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @Nullable Charset responseEncoding,
                                    @NotNull TypeRef<T> typeRef) {
        return jsonRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(JSON Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T jsonRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod,
                                    @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        String text = jsonRawBody(url, body, queryMap, headerMap, bodyMethod, responseEncoding);
        return StringUtil.isEmpty(text) ? null : JSONUtil.fromJson(text, typeRef);
    }

    // endregion


    // region Javascript RawBody POST

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为字符串
     *
     * @param url 请求地址
     * @return 响应结果
     */
    public static String javascriptRawBody(@NotNull String url) {
        return javascriptRawBody(url, (Charset) null);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String javascriptRawBody(@NotNull String url, @Nullable Charset responseEncoding) {
        return javascriptRawBody(url, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String javascriptRawBody(@NotNull String url, @NotNull HttpBodyMethod bodyMethod) {
        return javascriptRawBody(url, bodyMethod, (Map<String, ?>) null);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为字符串
     *
     * @param url  请求地址
     * @param body BODY内容
     * @return 响应结果
     */
    public static String javascriptRawBody(@NotNull String url, @Nullable Object body) {
        return javascriptRawBody(url, body, HttpBodyMethod.POST);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String javascriptRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod) {
        return javascriptRawBody(url, body, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String javascriptRawBody(@NotNull String url, @Nullable Object body, @Nullable Charset responseEncoding) {
        return javascriptRawBody(url, body, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String javascriptRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                           @Nullable Charset responseEncoding) {
        return javascriptRawBody(url, body, null, bodyMethod, responseEncoding);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为字符串
     *
     * @param url      请求地址
     * @param body     BODY内容
     * @param queryMap URL参数
     * @return 响应结果
     */
    public static String javascriptRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap) {
        return javascriptRawBody(url, body, queryMap, HttpBodyMethod.POST);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String javascriptRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                           @NotNull HttpBodyMethod bodyMethod) {
        return javascriptRawBody(url, body, queryMap, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String javascriptRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                           @Nullable Charset responseEncoding) {
        return javascriptRawBody(url, body, queryMap, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String javascriptRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                           @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding) {
        return javascriptRawBody(url, body, queryMap, null, bodyMethod, responseEncoding);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为字符串
     *
     * @param url       请求地址
     * @param body      BODY内容
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @return 响应结果
     */
    public static String javascriptRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                           @Nullable Map<String, ?> headerMap) {
        return javascriptRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param headerMap  HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String javascriptRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                           @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod) {
        return javascriptRawBody(url, body, queryMap, headerMap, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String javascriptRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                           @Nullable Map<String, ?> headerMap, @Nullable Charset responseEncoding) {
        return javascriptRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String javascriptRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                           @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod,
                                           @Nullable Charset responseEncoding) {
        HttpClient httpClient = Factory.get().build(null);

        HttpRequestBody requestBody = null;
        if (body != null) {
            if (body instanceof String) {
                requestBody = HttpRequestBody.create(ContentType.APPLICATION_JAVASCRIPT, (String) body);
            } else if (body instanceof InputStream) {
                requestBody = HttpRequestBody.create(ContentType.APPLICATION_JAVASCRIPT, (InputStream) body);
            } else if (body instanceof File) {
                requestBody = HttpRequestBody.create(ContentType.APPLICATION_JAVASCRIPT, (File) body);
            } else {
                requestBody = HttpRequestBody.create(ContentType.APPLICATION_JAVASCRIPT, JSONUtil.toJson(body));
            }
        }

        HttpRequest httpRequest = HttpRequest.builder()
                .url(url)
                .queryParams(queryMap)
                .headers(headerMap)
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
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
     *
     * @param url   请求地址
     * @param clazz 类型
     * @param <T>   泛型类型
     * @return 响应结果
     */
    public static <T> T javascriptRawBody(@NotNull String url, @NotNull Class<T> clazz) {
        return javascriptRawBody(url, (Charset) null, clazz);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T javascriptRawBody(@NotNull String url, @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return javascriptRawBody(url, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param bodyMethod HTTP请求方式
     * @param clazz      类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T javascriptRawBody(@NotNull String url, @NotNull HttpBodyMethod bodyMethod, @NotNull Class<T> clazz) {
        return javascriptRawBody(url, null, bodyMethod, clazz);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
     *
     * @param url   请求地址
     * @param body  BODY内容
     * @param clazz 类型
     * @param <T>   泛型类型
     * @return 响应结果
     */
    public static <T> T javascriptRawBody(@NotNull String url, @Nullable Object body, @NotNull Class<T> clazz) {
        return javascriptRawBody(url, body, HttpBodyMethod.POST, clazz);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param bodyMethod HTTP请求方式
     * @param clazz      类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T javascriptRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                          @NotNull Class<T> clazz) {
        return javascriptRawBody(url, body, bodyMethod, null, clazz);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T javascriptRawBody(@NotNull String url, @Nullable Object body, @Nullable Charset responseEncoding,
                                          @NotNull Class<T> clazz) {
        return javascriptRawBody(url, body, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T javascriptRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                          @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return javascriptRawBody(url, body, null, bodyMethod, responseEncoding, clazz);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
     *
     * @param url      请求地址
     * @param body     BODY内容
     * @param queryMap URL参数
     * @param clazz    类型
     * @param <T>      泛型类型
     * @return 响应结果
     */
    public static <T> T javascriptRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                          @NotNull Class<T> clazz) {
        return javascriptRawBody(url, body, queryMap, HttpBodyMethod.POST, clazz);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param bodyMethod HTTP请求方式
     * @param clazz      类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T javascriptRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                          @NotNull HttpBodyMethod bodyMethod, @NotNull Class<T> clazz) {
        return javascriptRawBody(url, body, queryMap, bodyMethod, null, clazz);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T javascriptRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                          @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return javascriptRawBody(url, body, queryMap, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
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
    public static <T> T javascriptRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                          @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding,
                                          @NotNull Class<T> clazz) {
        return javascriptRawBody(url, body, queryMap, null, bodyMethod, responseEncoding, clazz);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
     *
     * @param url       请求地址
     * @param body      BODY内容
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @param clazz     类型
     * @param <T>       泛型类型
     * @return 响应结果
     */
    public static <T> T javascriptRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                          @Nullable Map<String, ?> headerMap, @NotNull Class<T> clazz) {
        return javascriptRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST, clazz);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param headerMap  HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @param clazz      类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T javascriptRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                          @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod,
                                          @NotNull Class<T> clazz) {
        return javascriptRawBody(url, body, queryMap, headerMap, bodyMethod, null, clazz);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T javascriptRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                          @Nullable Map<String, ?> headerMap, @Nullable Charset responseEncoding,
                                          @NotNull Class<T> clazz) {
        return javascriptRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T javascriptRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                          @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod,
                                          @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        String text = javascriptRawBody(url, body, queryMap, headerMap, bodyMethod, responseEncoding);
        return StringUtil.isEmpty(text) ? null : JSONUtil.fromJson(text, clazz);
    }


    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
     *
     * @param url     请求地址
     * @param typeRef 类型
     * @param <T>     泛型类型
     * @return 响应结果
     */
    public static <T> T javascriptRawBody(@NotNull String url, @NotNull TypeRef<T> typeRef) {
        return javascriptRawBody(url, (Charset) null, typeRef);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T javascriptRawBody(@NotNull String url, @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return javascriptRawBody(url, null, responseEncoding, typeRef);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T javascriptRawBody(@NotNull String url, @NotNull HttpBodyMethod bodyMethod, @NotNull TypeRef<T> typeRef) {
        return javascriptRawBody(url, bodyMethod, (Charset) null, typeRef);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
     *
     * @param url     请求地址
     * @param body    BODY内容
     * @param typeRef 类型
     * @param <T>     泛型类型
     * @return 响应结果
     */
    public static <T> T javascriptRawBody(@NotNull String url, @Nullable Object body, @NotNull TypeRef<T> typeRef) {
        return javascriptRawBody(url, body, HttpBodyMethod.POST, typeRef);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T javascriptRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                          @NotNull TypeRef<T> typeRef) {
        return javascriptRawBody(url, body, bodyMethod, null, typeRef);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T javascriptRawBody(@NotNull String url, @Nullable Object body, @Nullable Charset responseEncoding,
                                          @NotNull TypeRef<T> typeRef) {
        return javascriptRawBody(url, body, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T javascriptRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                          @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return javascriptRawBody(url, body, null, bodyMethod, responseEncoding, typeRef);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
     *
     * @param url      请求地址
     * @param body     BODY内容
     * @param queryMap URL参数
     * @param typeRef  类型
     * @param <T>      泛型类型
     * @return 响应结果
     */
    public static <T> T javascriptRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                          @NotNull TypeRef<T> typeRef) {
        return javascriptRawBody(url, body, queryMap, HttpBodyMethod.POST, typeRef);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T javascriptRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                          @NotNull HttpBodyMethod bodyMethod, @NotNull TypeRef<T> typeRef) {
        return javascriptRawBody(url, body, queryMap, bodyMethod, null, typeRef);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T javascriptRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                          @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return javascriptRawBody(url, body, queryMap, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
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
    public static <T> T javascriptRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                          @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding,
                                          @NotNull TypeRef<T> typeRef) {
        return javascriptRawBody(url, body, queryMap, null, bodyMethod, responseEncoding, typeRef);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
     *
     * @param url       请求地址
     * @param body      BODY内容
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @param typeRef   类型
     * @param <T>       泛型类型
     * @return 响应结果
     */
    public static <T> T javascriptRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                          @Nullable Map<String, ?> headerMap, @NotNull TypeRef<T> typeRef) {
        return javascriptRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST, typeRef);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param headerMap  HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T javascriptRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                          @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod,
                                          @NotNull TypeRef<T> typeRef) {
        return javascriptRawBody(url, body, queryMap, headerMap, bodyMethod, null, typeRef);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T javascriptRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                          @Nullable Map<String, ?> headerMap, @Nullable Charset responseEncoding,
                                          @NotNull TypeRef<T> typeRef) {
        return javascriptRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(JavaScript Rawbody)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T javascriptRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                          @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod,
                                          @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        String text = javascriptRawBody(url, body, queryMap, headerMap, bodyMethod, responseEncoding);
        return StringUtil.isEmpty(text) ? null : JSONUtil.fromJson(text, typeRef);
    }

    // endregion


    // region HTML RawBody POST

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为字符串
     *
     * @param url 请求地址
     * @return 响应结果
     */
    public static String htmlRawBody(@NotNull String url) {
        return htmlRawBody(url, (Charset) null);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String htmlRawBody(@NotNull String url, @Nullable Charset responseEncoding) {
        return htmlRawBody(url, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String htmlRawBody(@NotNull String url, @NotNull HttpBodyMethod bodyMethod) {
        return htmlRawBody(url, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为字符串
     *
     * @param url  请求地址
     * @param body BODY内容
     * @return 响应结果
     */
    public static String htmlRawBody(@NotNull String url, @Nullable Object body) {
        return htmlRawBody(url, body, HttpBodyMethod.POST);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String htmlRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod) {
        return htmlRawBody(url, bodyMethod, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String htmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Charset responseEncoding) {
        return htmlRawBody(url, body, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String htmlRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                     @Nullable Charset responseEncoding) {
        return htmlRawBody(url, body, null, bodyMethod, responseEncoding);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为字符串
     *
     * @param url      请求地址
     * @param body     BODY内容
     * @param queryMap URL参数
     * @return 响应结果
     */
    public static String htmlRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap) {
        return htmlRawBody(url, body, queryMap, HttpBodyMethod.POST);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String htmlRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                     @NotNull HttpBodyMethod bodyMethod) {
        return htmlRawBody(url, body, queryMap, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String htmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                     @Nullable Charset responseEncoding) {
        return htmlRawBody(url, body, queryMap, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String htmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                     @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding) {
        return htmlRawBody(url, body, queryMap, null, bodyMethod, responseEncoding);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为字符串
     *
     * @param url       请求地址
     * @param body      BODY内容
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @return 响应结果
     */
    public static String htmlRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                     @Nullable Map<String, ?> headerMap) {
        return htmlRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param headerMap  HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String htmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                     @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod) {
        return htmlRawBody(url, body, queryMap, headerMap, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String htmlRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                     @Nullable Map<String, ?> headerMap, @Nullable Charset responseEncoding) {
        return htmlRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String htmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                     @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod,
                                     @Nullable Charset responseEncoding) {
        HttpClient httpClient = Factory.get().build(null);

        HttpRequestBody requestBody = null;
        if (body != null) {
            if (body instanceof String) {
                requestBody = HttpRequestBody.create(ContentType.APPLICATION_HTML, (String) body);
            } else if (body instanceof InputStream) {
                requestBody = HttpRequestBody.create(ContentType.APPLICATION_HTML, (InputStream) body);
            } else if (body instanceof File) {
                requestBody = HttpRequestBody.create(ContentType.APPLICATION_HTML, (File) body);
            } else {
                requestBody = HttpRequestBody.create(ContentType.APPLICATION_HTML, JSONUtil.toJson(body));
            }
        }

        HttpRequest httpRequest = HttpRequest.builder()
                .url(url)
                .queryParams(queryMap)
                .headers(headerMap)
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
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url   请求地址
     * @param clazz 类型
     * @param <T>   泛型类型
     * @return 响应结果
     */
    public static <T> T htmlRawBody(@NotNull String url, @NotNull Class<T> clazz) {
        return htmlRawBody(url, (Charset) null, clazz);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T htmlRawBody(@NotNull String url, @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return htmlRawBody(url, null, responseEncoding, clazz);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param bodyMethod HTTP请求方式
     * @param clazz      类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T htmlRawBody(@NotNull String url, @NotNull HttpBodyMethod bodyMethod, @NotNull Class<T> clazz) {
        return htmlRawBody(url, bodyMethod, (Charset) null, clazz);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url   请求地址
     * @param body  BODY内容
     * @param clazz 类型
     * @param <T>   泛型类型
     * @return 响应结果
     */
    public static <T> T htmlRawBody(@NotNull String url, @Nullable Object body, @NotNull Class<T> clazz) {
        return htmlRawBody(url, body, HttpBodyMethod.POST, clazz);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param bodyMethod HTTP请求方式
     * @param clazz      类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T htmlRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                    @NotNull Class<T> clazz) {
        return htmlRawBody(url, body, bodyMethod, null, clazz);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T htmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Charset responseEncoding,
                                    @NotNull Class<T> clazz) {
        return htmlRawBody(url, body, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T htmlRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                    @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return xmlRawBody(url, body, null, bodyMethod, responseEncoding, clazz);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url      请求地址
     * @param body     BODY内容
     * @param queryMap URL参数
     * @param clazz    类型
     * @param <T>      泛型类型
     * @return 响应结果
     */
    public static <T> T htmlRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                    @NotNull Class<T> clazz) {
        return htmlRawBody(url, body, queryMap, HttpBodyMethod.POST, clazz);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param bodyMethod HTTP请求方式
     * @param clazz      类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T htmlRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                    @NotNull HttpBodyMethod bodyMethod, @NotNull Class<T> clazz) {
        return htmlRawBody(url, body, queryMap, bodyMethod, null, clazz);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T htmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                    @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return htmlRawBody(url, body, queryMap, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
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
    public static <T> T htmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                    @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding,
                                    @NotNull Class<T> clazz) {
        return htmlRawBody(url, body, queryMap, null, bodyMethod, responseEncoding, clazz);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url       请求地址
     * @param body      BODY内容
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @param clazz     类型
     * @param <T>       泛型类型
     * @return 响应结果
     */
    public static <T> T htmlRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @NotNull Class<T> clazz) {
        return htmlRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST, clazz);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param headerMap  HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @param clazz      类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T htmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod, @NotNull Class<T> clazz) {
        return htmlRawBody(url, body, queryMap, headerMap, bodyMethod, null, clazz);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T htmlRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @Nullable Charset responseEncoding,
                                    @NotNull Class<T> clazz) {
        return htmlRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T htmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod,
                                    @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        String text = htmlRawBody(url, body, queryMap, headerMap, bodyMethod, responseEncoding);
        return StringUtil.isEmpty(text) ? null : JSONUtil.fromJson(text, clazz);
    }


    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url     请求地址
     * @param typeRef 类型
     * @param <T>     泛型类型
     * @return 响应结果
     */
    public static <T> T htmlRawBody(@NotNull String url, @NotNull TypeRef<T> typeRef) {
        return htmlRawBody(url, HttpBodyMethod.POST, typeRef);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T htmlRawBody(@NotNull String url, @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return htmlRawBody(url, HttpBodyMethod.POST, typeRef);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T htmlRawBody(@NotNull String url, @NotNull HttpBodyMethod bodyMethod, @NotNull TypeRef<T> typeRef) {
        return xmlRawBody(url, null, bodyMethod, typeRef);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url     请求地址
     * @param body    BODY内容
     * @param typeRef 类型
     * @param <T>     泛型类型
     * @return 响应结果
     */
    public static <T> T htmlRawBody(@NotNull String url, @Nullable Object body, @NotNull TypeRef<T> typeRef) {
        return htmlRawBody(url, body, HttpBodyMethod.POST, typeRef);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T htmlRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                    @NotNull TypeRef<T> typeRef) {
        return htmlRawBody(url, body, null, bodyMethod, typeRef);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T htmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Charset responseEncoding,
                                    @NotNull TypeRef<T> typeRef) {
        return htmlRawBody(url, body, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T htmlRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                    @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return htmlRawBody(url, body, null, bodyMethod, responseEncoding, typeRef);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url      请求地址
     * @param body     BODY内容
     * @param queryMap URL参数
     * @param typeRef  类型
     * @param <T>      泛型类型
     * @return 响应结果
     */
    public static <T> T htmlRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                    @NotNull TypeRef<T> typeRef) {
        return htmlRawBody(url, body, queryMap, HttpBodyMethod.POST, typeRef);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T htmlRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                    @NotNull HttpBodyMethod bodyMethod, @NotNull TypeRef<T> typeRef) {
        return htmlRawBody(url, body, queryMap, bodyMethod, null, typeRef);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T htmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                    @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return htmlRawBody(url, body, queryMap, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
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
    public static <T> T htmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                    @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding,
                                    @NotNull TypeRef<T> typeRef) {
        return htmlRawBody(url, body, queryMap, null, bodyMethod, responseEncoding, typeRef);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url       请求地址
     * @param body      BODY内容
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @param typeRef   类型
     * @param <T>       泛型类型
     * @return 响应结果
     */
    public static <T> T htmlRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @NotNull TypeRef<T> typeRef) {
        return htmlRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST, typeRef);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param headerMap  HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T htmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod,
                                    @NotNull TypeRef<T> typeRef) {
        return htmlRawBody(url, body, queryMap, headerMap, bodyMethod, null, typeRef);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T htmlRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @Nullable Charset responseEncoding,
                                    @NotNull TypeRef<T> typeRef) {
        return htmlRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(HTML Raw Body)请求，并将响应结果转为JavaBean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T htmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod,
                                    @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        String text = htmlRawBody(url, body, queryMap, headerMap, bodyMethod, responseEncoding);
        return StringUtil.isEmpty(text) ? null : JSONUtil.fromJson(text, typeRef);
    }

    // endregion


    // region XML RawBody POST

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为字符串
     *
     * @param url 请求地址
     * @return 响应结果
     */
    public static String xmlRawBody(@NotNull String url) {
        return xmlRawBody(url, (Charset) null);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String xmlRawBody(@NotNull String url, @Nullable Charset responseEncoding) {
        return xmlRawBody(url, null, responseEncoding);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String xmlRawBody(@NotNull String url, @NotNull HttpBodyMethod bodyMethod) {
        return xmlRawBody(url, null, bodyMethod);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为字符串
     *
     * @param url  请求地址
     * @param body BODY内容
     * @return 响应结果
     */
    public static String xmlRawBody(@NotNull String url, @Nullable Object body) {
        return xmlRawBody(url, body, (Map<String, ?>) null);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String xmlRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod) {
        return xmlRawBody(url, body, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String xmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Charset responseEncoding) {
        return xmlRawBody(url, body, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String xmlRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                    @Nullable Charset responseEncoding) {
        return xmlRawBody(url, body, null, bodyMethod, responseEncoding);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为字符串
     *
     * @param url      请求地址
     * @param body     BODY内容
     * @param queryMap URL参数
     * @return 响应结果
     */
    public static String xmlRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap) {
        return xmlRawBody(url, body, queryMap, HttpBodyMethod.POST);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String xmlRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                    @NotNull HttpBodyMethod bodyMethod) {
        return xmlRawBody(url, body, queryMap, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String xmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                    @Nullable Charset responseEncoding) {
        return xmlRawBody(url, body, queryMap, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String xmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                    @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding) {
        return xmlRawBody(url, body, queryMap, null, bodyMethod, responseEncoding);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为字符串
     *
     * @param url       请求地址
     * @param body      BODY内容
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @return 响应结果
     */
    public static String xmlRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap) {
        return xmlRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param headerMap  HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String xmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod) {
        return xmlRawBody(url, body, queryMap, headerMap, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String xmlRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @Nullable Charset responseEncoding) {
        return xmlRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String xmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod,
                                    @Nullable Charset responseEncoding) {
        HttpClient httpClient = Factory.get().build(null);

        HttpRequestBody requestBody = null;
        if (body != null) {
            if (body instanceof String) {
                requestBody = HttpRequestBody.create(ContentType.APPLICATION_XML, (String) body);
            } else if (body instanceof InputStream) {
                requestBody = HttpRequestBody.create(ContentType.APPLICATION_XML, (InputStream) body);
            } else if (body instanceof File) {
                requestBody = HttpRequestBody.create(ContentType.APPLICATION_XML, (File) body);
            } else {
                requestBody = HttpRequestBody.create(ContentType.APPLICATION_XML, JSONUtil.toJson(body));
            }
        }

        HttpRequest httpRequest = HttpRequest.builder()
                .url(url)
                .queryParams(queryMap)
                .headers(headerMap)
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
     * 发起POST(xml raw body)请求，并将响应结果转为Bean对象
     *
     * @param url   请求地址
     * @param clazz 类型
     * @param <T>   泛型类型
     * @return 响应结果
     */
    public static <T> T xmlRawBody(@NotNull String url, @NotNull Class<T> clazz) {
        return xmlRawBody(url, (Charset) null, clazz);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param bodyMethod HTTP请求方式
     * @param clazz      类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T xmlRawBody(@NotNull String url, @NotNull HttpBodyMethod bodyMethod, @NotNull Class<T> clazz) {
        return xmlRawBody(url, bodyMethod, (Charset) null, clazz);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T xmlRawBody(@NotNull String url, @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return xmlRawBody(url, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为Bean对象
     *
     * @param url   请求地址
     * @param body  BODY内容
     * @param clazz 类型
     * @param <T>   泛型类型
     * @return 响应结果
     */
    public static <T> T xmlRawBody(@NotNull String url, @Nullable Object body, @NotNull Class<T> clazz) {
        return xmlRawBody(url, body, HttpBodyMethod.POST, clazz);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param bodyMethod HTTP请求方式
     * @param clazz      类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T xmlRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                   @NotNull Class<T> clazz) {
        return xmlRawBody(url, body, bodyMethod, null, clazz);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T xmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Charset responseEncoding,
                                   @NotNull Class<T> clazz) {
        return xmlRawBody(url, body, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T xmlRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                   @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return xmlRawBody(url, body, null, bodyMethod, responseEncoding, clazz);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为Bean对象
     *
     * @param url      请求地址
     * @param body     BODY内容
     * @param queryMap URL参数
     * @param clazz    类型
     * @param <T>      泛型类型
     * @return 响应结果
     */
    public static <T> T xmlRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                   @NotNull Class<T> clazz) {
        return xmlRawBody(url, body, queryMap, HttpBodyMethod.POST, clazz);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param bodyMethod HTTP请求方式
     * @param clazz      类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T xmlRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                   @NotNull HttpBodyMethod bodyMethod, @NotNull Class<T> clazz) {
        return xmlRawBody(url, body, queryMap, bodyMethod, null, clazz);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T xmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                   @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return xmlRawBody(url, body, queryMap, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为Bean对象
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
    public static <T> T xmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                   @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding,
                                   @NotNull Class<T> clazz) {
        return xmlRawBody(url, body, queryMap, null, bodyMethod, responseEncoding, clazz);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为Bean对象
     *
     * @param url       请求地址
     * @param body      BODY内容
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @param clazz     类型
     * @param <T>       泛型类型
     * @return 响应结果
     */
    public static <T> T xmlRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                   @Nullable Map<String, ?> headerMap, @NotNull Class<T> clazz) {
        return xmlRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST, clazz);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param headerMap  HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @param clazz      类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T xmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                   @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod, @NotNull Class<T> clazz) {
        return xmlRawBody(url, body, queryMap, headerMap, bodyMethod, null, clazz);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T xmlRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                   @Nullable Map<String, ?> headerMap, @Nullable Charset responseEncoding,
                                   @NotNull Class<T> clazz) {
        return xmlRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T xmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                   @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod,
                                   @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        String text = xmlRawBody(url, body, queryMap, headerMap, bodyMethod, responseEncoding);
        return StringUtil.isEmpty(text) ? null : JSONUtil.fromJson(text, clazz);
    }


    /**
     * 发起POST(xml raw body)请求，并将响应结果转为Bean对象
     *
     * @param url     请求地址
     * @param typeRef 类型
     * @param <T>     泛型类型
     * @return 响应结果
     */
    public static <T> T xmlRawBody(@NotNull String url, @NotNull TypeRef<T> typeRef) {
        return xmlRawBody(url, HttpBodyMethod.POST, typeRef);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T xmlRawBody(@NotNull String url, @NotNull HttpBodyMethod bodyMethod, @NotNull TypeRef<T> typeRef) {
        return xmlRawBody(url, null, bodyMethod, typeRef);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为Bean对象
     *
     * @param url     请求地址
     * @param body    BODY内容
     * @param typeRef 类型
     * @param <T>     泛型类型
     * @return 响应结果
     */
    public static <T> T xmlRawBody(@NotNull String url, @Nullable Object body, @NotNull TypeRef<T> typeRef) {
        return xmlRawBody(url, body, HttpBodyMethod.POST, typeRef);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T xmlRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                   @NotNull TypeRef<T> typeRef) {
        return xmlRawBody(url, body, bodyMethod, (Charset) null, typeRef);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T xmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Charset responseEncoding,
                                   @NotNull TypeRef<T> typeRef) {
        return xmlRawBody(url, body, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T xmlRawBody(@NotNull String url, @Nullable Object body, @NotNull HttpBodyMethod bodyMethod,
                                   @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return xmlRawBody(url, body, null, bodyMethod, responseEncoding, typeRef);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为Bean对象
     *
     * @param url      请求地址
     * @param body     BODY内容
     * @param queryMap URL参数
     * @param typeRef  类型
     * @param <T>      泛型类型
     * @return 响应结果
     */
    public static <T> T xmlRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap, @NotNull TypeRef<T> typeRef) {
        return xmlRawBody(url, body, queryMap, HttpBodyMethod.POST, typeRef);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T xmlRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                   @NotNull HttpBodyMethod bodyMethod, @NotNull TypeRef<T> typeRef) {
        return xmlRawBody(url, body, queryMap, bodyMethod, null, typeRef);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T xmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                   @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return xmlRawBody(url, body, queryMap, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为Bean对象
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
    public static <T> T xmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                   @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding,
                                   @NotNull TypeRef<T> typeRef) {
        return xmlRawBody(url, body, queryMap, null, bodyMethod, responseEncoding, typeRef);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为Bean对象
     *
     * @param url       请求地址
     * @param body      BODY内容
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @param typeRef   类型
     * @param <T>       泛型类型
     * @return 响应结果
     */
    public static <T> T xmlRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                   @Nullable Map<String, ?> headerMap, @NotNull TypeRef<T> typeRef) {
        return xmlRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST, typeRef);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param body       BODY内容
     * @param queryMap   URL参数
     * @param headerMap  HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T xmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                   @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod,
                                   @NotNull TypeRef<T> typeRef) {
        return xmlRawBody(url, body, queryMap, headerMap, bodyMethod, null, typeRef);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T xmlRawBody(@NotNull String url, @Nullable Object body, Map<String, ?> queryMap,
                                   @Nullable Map<String, ?> headerMap, @Nullable Charset responseEncoding,
                                   @NotNull TypeRef<T> typeRef) {
        return xmlRawBody(url, body, queryMap, headerMap, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(xml raw body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param body             BODY内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T xmlRawBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryMap,
                                   @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod,
                                   @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        String text = xmlRawBody(url, body, queryMap, headerMap, bodyMethod, responseEncoding);
        return StringUtil.isEmpty(text) ? null : JSONUtil.fromJson(text, typeRef);
    }

    // endregion


    // region BinaryBody POST

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url 请求地址
     * @return 响应结果
     */
    public static String binaryBody(@NotNull String url) {
        return binaryBody(url, (Charset) null);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param responseEncoding 响应内容编码
     * @return 响应结果
     */
    public static String binaryBody(@NotNull String url, @Nullable Charset responseEncoding) {
        return binaryBody(url, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String binaryBody(@NotNull String url, @NotNull HttpBodyMethod bodyMethod) {
        return binaryBody(url, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param responseEncoding 响应内容编码
     * @param bodyMethod       HTTP请求方式
     * @return 响应结果
     */
    public static String binaryBody(@NotNull String url, @NotNull HttpBodyMethod bodyMethod,
                                    @Nullable Charset responseEncoding) {
        return binaryBody(url, (String) null, bodyMethod, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url     请求地址
     * @param content body内容
     * @return 响应结果
     */
    public static String binaryBody(@NotNull String url, @Nullable String content) {
        return binaryBody(url, content, (Charset) null);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param content          body内容
     * @param responseEncoding 响应内容编码
     * @return 响应结果
     */
    public static String binaryBody(@NotNull String url, @Nullable String content, @Nullable Charset responseEncoding) {
        return binaryBody(url, content, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param content    body内容
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String binaryBody(@NotNull String url, @Nullable String content, @NotNull HttpBodyMethod bodyMethod) {
        return binaryBody(url, content, bodyMethod, (Charset) null);
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
    public static String binaryBody(@NotNull String url, @Nullable String content, @NotNull HttpBodyMethod bodyMethod,
                                    @Nullable Charset responseEncoding) {
        return binaryBody(url, content, null, bodyMethod, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url      请求地址
     * @param content  body内容
     * @param queryMap URL参数
     * @return 响应结果
     */
    public static String binaryBody(@NotNull String url, @Nullable String content, @Nullable Map<String, ?> queryMap) {
        return binaryBody(url, content, queryMap, (Charset) null);
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
    public static String binaryBody(@NotNull String url, @Nullable String content, @Nullable Map<String, ?> queryMap,
                                    @Nullable Charset responseEncoding) {
        return binaryBody(url, content, queryMap, HttpBodyMethod.POST, responseEncoding);
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
    public static String binaryBody(@NotNull String url, @Nullable String content, Map<String, ?> queryMap,
                                    @NotNull HttpBodyMethod bodyMethod) {
        return binaryBody(url, content, queryMap, bodyMethod, (Charset) null);
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
    public static String binaryBody(@NotNull String url, @Nullable String content, @Nullable Map<String, ?> queryMap,
                                    @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding) {
        return binaryBody(url, content, queryMap, null, bodyMethod, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url       请求地址
     * @param content   body内容
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @return 响应结果
     */
    public static String binaryBody(@NotNull String url, @Nullable String content, @Nullable Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap) {
        return binaryBody(url, content, queryMap, headerMap, HttpBodyMethod.POST);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param content          body内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 响应内容编码
     * @return 响应结果
     */
    public static String binaryBody(@NotNull String url, @Nullable String content, Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @Nullable Charset responseEncoding) {
        return binaryBody(url, content, queryMap, headerMap, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param content    body内容
     * @param queryMap   URL参数
     * @param headerMap  HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String binaryBody(@NotNull String url, @Nullable String content, @Nullable Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod) {
        return binaryBody(url, content, queryMap, headerMap, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param content          body内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 响应内容编码
     * @return 响应结果
     */
    public static String binaryBody(@NotNull String url, @Nullable String content, @Nullable Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod,
                                    @Nullable Charset responseEncoding) {
        InputStream in = null;
        if (content != null) {
            in = new ByteArrayInputStream(content.getBytes(CharsetUtil.UTF_8));
        }
        return binaryBody(url, ContentType.DEFAULT_BINARY, in, queryMap, headerMap, bodyMethod, responseEncoding);
    }


    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url     请求地址
     * @param content 文件
     * @return 响应结果
     */
    public static String binaryBody(@NotNull String url, @Nullable File content) {
        return binaryBody(url, content, HttpBodyMethod.POST);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param content          文件
     * @param responseEncoding 响应内容编码
     * @return 响应结果
     */
    public static String binaryBody(@NotNull String url, @Nullable File content, @Nullable Charset responseEncoding) {
        return binaryBody(url, content, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param content    文件
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String binaryBody(@NotNull String url, @Nullable File content, @NotNull HttpBodyMethod bodyMethod) {
        return binaryBody(url, content, bodyMethod, (Charset) null);
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
    public static String binaryBody(@NotNull String url, @Nullable File content, @NotNull HttpBodyMethod bodyMethod,
                                    @Nullable Charset responseEncoding) {
        return binaryBody(url, content, null, bodyMethod, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url      请求地址
     * @param content  文件
     * @param queryMap URL参数
     * @return 响应结果
     */
    public static String binaryBody(@NotNull String url, @Nullable File content, Map<String, ?> queryMap) {
        return binaryBody(url, content, queryMap, HttpBodyMethod.POST);
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
    public static String binaryBody(@NotNull String url, @Nullable File content, @Nullable Map<String, ?> queryMap,
                                    @Nullable Charset responseEncoding) {
        return binaryBody(url, content, queryMap, HttpBodyMethod.POST, responseEncoding);
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
    public static String binaryBody(@NotNull String url, @Nullable File content, Map<String, ?> queryMap,
                                    @NotNull HttpBodyMethod bodyMethod) {
        return binaryBody(url, content, queryMap, bodyMethod, (Charset) null);
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
    public static String binaryBody(@NotNull String url, @Nullable File content, @Nullable Map<String, ?> queryMap,
                                    @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding) {
        return binaryBody(url, content, queryMap, null, bodyMethod, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url       请求地址
     * @param content   文件
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @return 响应结果
     */
    public static String binaryBody(@NotNull String url, @Nullable File content, Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap) {
        return binaryBody(url, content, queryMap, headerMap, HttpBodyMethod.POST);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param content          文件
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String binaryBody(@NotNull String url, @Nullable File content, Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @Nullable Charset responseEncoding) {
        return binaryBody(url, content, queryMap, headerMap, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param content    文件
     * @param queryMap   URL参数
     * @param headerMap  HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String binaryBody(@NotNull String url, @Nullable File content, @Nullable Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod) {
        return binaryBody(url, content, queryMap, headerMap, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param content          文件
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param bodyMethod       HTTP请求方式
     * @return 响应结果
     */
    public static String binaryBody(@NotNull String url, @Nullable File content, @Nullable Map<String, ?> queryMap,
                                    @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod,
                                    @Nullable Charset responseEncoding) {
        FileInputStream in = null;
        try {
            ContentType contentType = ContentType.DEFAULT_BINARY;
            if (content != null) {
                in = FileUtil.openFileInputStream(content);
                contentType = ContentType.parseByFileExt(FileUtil.getFileExt(content));
            }
            return binaryBody(url, contentType, in, queryMap, headerMap, bodyMethod, responseEncoding);
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
    public static String binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content) {
        return binaryBody(url, contentType, content, (Charset) null);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param content          输入流，不会自动关闭
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                    @Nullable Charset responseEncoding) {
        return binaryBody(url, contentType, content, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param content    输入流，不会自动关闭
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                    @NotNull HttpBodyMethod bodyMethod) {
        return binaryBody(url, contentType, content, bodyMethod, (Charset) null);
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
    public static String binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                    @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding) {
        return binaryBody(url, contentType, content, null, bodyMethod, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url      请求地址
     * @param content  输入流，不会自动关闭
     * @param queryMap URL参数
     * @return 响应结果
     */
    public static String binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                    Map<String, ?> queryMap) {
        return binaryBody(url, contentType, content, queryMap, (Charset) null);
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
    public static String binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                    @Nullable Map<String, ?> queryMap, @Nullable Charset responseEncoding) {
        return binaryBody(url, contentType, content, queryMap, HttpBodyMethod.POST, responseEncoding);
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
    public static String binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                    Map<String, ?> queryMap, @NotNull HttpBodyMethod bodyMethod) {
        return binaryBody(url, contentType, content, queryMap, bodyMethod, (Charset) null);
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
    public static String binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                    @Nullable Map<String, ?> queryMap, @NotNull HttpBodyMethod bodyMethod,
                                    @Nullable Charset responseEncoding) {
        return binaryBody(url, contentType, content, queryMap, null, bodyMethod, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url       请求地址
     * @param content   输入流，不会自动关闭
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @return 响应结果
     */
    public static String binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                    Map<String, ?> queryMap, @Nullable Map<String, ?> headerMap) {
        return binaryBody(url, contentType, content, queryMap, headerMap, (Charset) null);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param content          输入流，不会自动关闭
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @return 响应结果
     */
    public static String binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                    Map<String, ?> queryMap, @Nullable Map<String, ?> headerMap,
                                    @Nullable Charset responseEncoding) {
        return binaryBody(url, contentType, content, queryMap, headerMap, HttpBodyMethod.POST, responseEncoding);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url        请求地址
     * @param content    输入流，不会自动关闭
     * @param queryMap   URL参数
     * @param headerMap  HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @return 响应结果
     */
    public static String binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                    @Nullable Map<String, ?> queryMap, @Nullable Map<String, ?> headerMap,
                                    @NotNull HttpBodyMethod bodyMethod) {
        return binaryBody(url, contentType, content, queryMap, headerMap, bodyMethod, (Charset) null);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为字符串
     *
     * @param url              请求地址
     * @param content          输入流，不会自动关闭
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param bodyMethod       HTTP请求方式
     * @return 响应结果
     */
    public static String binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                    @Nullable Map<String, ?> queryMap, @Nullable Map<String, ?> headerMap,
                                    @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding) {
        HttpClient httpClient = Factory.get().build(null);

        HttpRequestBody requestBody = null;
        if (content != null) {
            requestBody = HttpRequestBody.create(contentType, content);
        }

        HttpRequest httpRequest = HttpRequest.builder()
                .url(url)
                .queryParams(queryMap)
                .headers(headerMap)
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
    public static <T> T binaryBody(@NotNull String url, @NotNull Class<T> clazz) {
        return binaryBody(url, (Charset) null, clazz);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return binaryBody(url, HttpBodyMethod.POST, responseEncoding, clazz);
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
    public static <T> T binaryBody(@NotNull String url, @NotNull Class<T> clazz, @NotNull HttpBodyMethod bodyMethod) {
        return binaryBody(url, bodyMethod, (Charset) null, clazz);
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
    public static <T> T binaryBody(@NotNull String url, @NotNull HttpBodyMethod bodyMethod,
                                   @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return binaryBody(url, (String) null, bodyMethod, responseEncoding, clazz);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable String content, @NotNull Class<T> clazz) {
        return binaryBody(url, content, (Charset) null, clazz);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable String content, @Nullable Charset responseEncoding,
                                   @NotNull Class<T> clazz) {
        return binaryBody(url, content, HttpBodyMethod.POST, responseEncoding, clazz);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable String content, @NotNull Class<T> clazz,
                                   @NotNull HttpBodyMethod bodyMethod) {
        return binaryBody(url, content, bodyMethod, (Charset) null, clazz);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable String content, @NotNull HttpBodyMethod bodyMethod,
                                   @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return binaryBody(url, content, null, bodyMethod, responseEncoding, clazz);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable String content, Map<String, ?> queryMap,
                                   @NotNull Class<T> clazz) {
        return binaryBody(url, content, queryMap, (Charset) null, clazz);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable String content, Map<String, ?> queryMap,
                                   @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return binaryBody(url, content, queryMap, HttpBodyMethod.POST, responseEncoding, clazz);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable String content, Map<String, ?> queryMap,
                                   @NotNull HttpBodyMethod bodyMethod, @NotNull Class<T> clazz) {
        return binaryBody(url, content, queryMap, bodyMethod, (Charset) null, clazz);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable String content, @Nullable Map<String, ?> queryMap,
                                   @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return binaryBody(url, content, queryMap, null, bodyMethod, responseEncoding, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url       请求地址
     * @param content   Body内容
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @param clazz     类型
     * @param <T>       泛型类型
     * @return 响应结果
     */
    public static <T> T binaryBody(@NotNull String url, @Nullable String content, Map<String, ?> queryMap,
                                   @Nullable Map<String, ?> headerMap, @NotNull Class<T> clazz) {
        return binaryBody(url, content, queryMap, headerMap, (Charset) null, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          Body内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T binaryBody(@NotNull String url, @Nullable String content, Map<String, ?> queryMap,
                                   @Nullable Map<String, ?> headerMap, @Nullable Charset responseEncoding,
                                   @NotNull Class<T> clazz) {
        return binaryBody(url, content, queryMap, headerMap, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param content    Body内容
     * @param queryMap   URL参数
     * @param headerMap  HTTP头参数
     * @param clazz      类型
     * @param bodyMethod HTTP请求方式
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T binaryBody(@NotNull String url, @Nullable String content, @Nullable Map<String, ?> queryMap,
                                   @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod, @NotNull Class<T> clazz) {
        return binaryBody(url, content, queryMap, headerMap, bodyMethod, null, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          Body内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param bodyMethod       HTTP请求方式
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T binaryBody(@NotNull String url, @Nullable String content, @Nullable Map<String, ?> queryMap,
                                   @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod,
                                   @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        InputStream in = null;
        ContentType contentType = ContentType.DEFAULT_BINARY;
        try {
            if (content != null) {
                in = new ByteArrayInputStream(content.getBytes(CharsetUtil.UTF_8));
            }
            return binaryBody(url, contentType, in, queryMap, headerMap, bodyMethod, responseEncoding, clazz);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable File content, @NotNull Class<T> clazz) {
        return binaryBody(url, content, (Charset) null, clazz);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable File content, @Nullable Charset responseEncoding,
                                   @NotNull Class<T> clazz) {
        return binaryBody(url, content, HttpBodyMethod.POST, responseEncoding, clazz);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable File content, @NotNull HttpBodyMethod bodyMethod,
                                   @NotNull Class<T> clazz) {
        return binaryBody(url, content, bodyMethod, (Charset) null, clazz);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable File content, @NotNull HttpBodyMethod bodyMethod,
                                   @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return binaryBody(url, content, null, bodyMethod, responseEncoding, clazz);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable File content, Map<String, ?> queryMap, @NotNull Class<T> clazz) {
        return binaryBody(url, content, queryMap, (Charset) null, clazz);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable File content, @Nullable Map<String, ?> queryMap,
                                   @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return binaryBody(url, content, queryMap, HttpBodyMethod.POST, responseEncoding, clazz);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable File content, Map<String, ?> queryMap,
                                   @NotNull HttpBodyMethod bodyMethod, @NotNull Class<T> clazz) {
        return binaryBody(url, content, queryMap, bodyMethod, (Charset) null, clazz);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable File content, @Nullable Map<String, ?> queryMap,
                                   @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return binaryBody(url, content, queryMap, null, bodyMethod, responseEncoding, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url       请求地址
     * @param content   Body内容
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @param clazz     类型
     * @param <T>       泛型类型
     * @return 响应结果
     */
    public static <T> T binaryBody(@NotNull String url, @Nullable File content, Map<String, ?> queryMap,
                                   @Nullable Map<String, ?> headerMap, @NotNull Class<T> clazz) {
        return binaryBody(url, content, queryMap, headerMap, HttpBodyMethod.POST, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param content    Body内容
     * @param queryMap   URL参数
     * @param headerMap  HTTP头参数
     * @param clazz      类型
     * @param bodyMethod HTTP请求方式
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T binaryBody(@NotNull String url, @Nullable File content, @Nullable Map<String, ?> queryMap,
                                   @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod,
                                   @NotNull Class<T> clazz) {
        return binaryBody(url, content, queryMap, headerMap, bodyMethod, null, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          Body内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T binaryBody(@NotNull String url, @Nullable File content, Map<String, ?> queryMap,
                                   @Nullable Map<String, ?> headerMap, @Nullable Charset responseEncoding,
                                   @NotNull Class<T> clazz) {
        return binaryBody(url, content, queryMap, headerMap, HttpBodyMethod.POST, responseEncoding, clazz);
    }


    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          Body内容
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param bodyMethod       HTTP请求方式
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T binaryBody(@NotNull String url, @Nullable File content, @Nullable Map<String, ?> queryMap,
                                   @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod,
                                   @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        FileInputStream in = null;
        ContentType contentType = ContentType.DEFAULT_BINARY;
        try {
            if (content != null) {
                in = FileUtil.openFileInputStream(content);
                contentType = ContentType.parseByFileExt(FileUtil.getFileExt(content));
            }
            return binaryBody(url, contentType, in, queryMap, headerMap, bodyMethod, responseEncoding, clazz);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                   @NotNull Class<T> clazz) {
        return binaryBody(url, contentType, content, (Charset) null, clazz);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                   @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return binaryBody(url, contentType, content, HttpBodyMethod.POST, responseEncoding, clazz);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                   @NotNull HttpBodyMethod bodyMethod, @NotNull Class<T> clazz) {
        return binaryBody(url, contentType, content, bodyMethod, (Charset) null, clazz);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                   @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return binaryBody(url, contentType, content, null, bodyMethod, responseEncoding, clazz);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                   Map<String, ?> queryMap, @NotNull Class<T> clazz) {
        return binaryBody(url, contentType, content, queryMap, (Charset) null, clazz);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                   @Nullable Map<String, ?> queryMap, @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return binaryBody(url, contentType, content, queryMap, HttpBodyMethod.POST, responseEncoding, clazz);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                   Map<String, ?> queryMap, @NotNull HttpBodyMethod bodyMethod, @NotNull Class<T> clazz) {
        return binaryBody(url, contentType, content, queryMap, bodyMethod, (Charset) null, clazz);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                   @Nullable Map<String, ?> queryMap, @NotNull HttpBodyMethod bodyMethod,
                                   @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return binaryBody(url, contentType, content, queryMap, null, bodyMethod, responseEncoding, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url       请求地址
     * @param content   BODY输入流，不会自动关闭流
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @param clazz     类型
     * @param <T>       泛型类型
     * @return 响应结果
     */
    public static <T> T binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                   Map<String, ?> queryMap, @Nullable Map<String, ?> headerMap, @NotNull Class<T> clazz) {
        return binaryBody(url, contentType, content, queryMap, headerMap, (Charset) null, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param clazz            类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                   Map<String, ?> queryMap, @Nullable Map<String, ?> headerMap,
                                   @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        return binaryBody(url, contentType, content, queryMap, headerMap, HttpBodyMethod.POST, responseEncoding, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param content    BODY输入流，不会自动关闭流
     * @param queryMap   URL参数
     * @param headerMap  HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @param clazz      类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                   @Nullable Map<String, ?> queryMap, @Nullable Map<String, ?> headerMap,
                                   @NotNull HttpBodyMethod bodyMethod, @NotNull Class<T> clazz) {
        return binaryBody(url, contentType, content, queryMap, headerMap, bodyMethod, null, clazz);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param clazz            类型
     * @param responseEncoding 指定响应内容的编码
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                   @Nullable Map<String, ?> queryMap, @Nullable Map<String, ?> headerMap,
                                   @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding, @NotNull Class<T> clazz) {
        String text = binaryBody(url, contentType, content, queryMap, headerMap, bodyMethod, responseEncoding);
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
    public static <T> T binaryBody(@NotNull String url, @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, (Charset) null, typeRef);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, HttpBodyMethod.POST, responseEncoding, typeRef);
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
    public static <T> T binaryBody(@NotNull String url, @NotNull HttpBodyMethod bodyMethod, @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, bodyMethod, (Charset) null, typeRef);
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
    public static <T> T binaryBody(@NotNull String url, @NotNull HttpBodyMethod bodyMethod,
                                   @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, (String) null, bodyMethod, responseEncoding, typeRef);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable String content, @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, content, (Charset) null, typeRef);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable String content, @Nullable Charset responseEncoding,
                                   @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, content, HttpBodyMethod.POST, responseEncoding, typeRef);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable String content, @NotNull HttpBodyMethod bodyMethod,
                                   @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, content, bodyMethod, (Charset) null, typeRef);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable String content, @NotNull HttpBodyMethod bodyMethod,
                                   @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, content, null, bodyMethod, responseEncoding, typeRef);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable String content, Map<String, ?> queryMap,
                                   @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, content, queryMap, (Charset) null, typeRef);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable String content, @Nullable Map<String, ?> queryMap,
                                   @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, content, queryMap, HttpBodyMethod.POST, responseEncoding, typeRef);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable String content, Map<String, ?> queryMap,
                                   @NotNull HttpBodyMethod bodyMethod, @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, content, queryMap, bodyMethod, null, typeRef);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable String content, @Nullable Map<String, ?> queryMap,
                                   @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding,
                                   @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, content, queryMap, null, bodyMethod, responseEncoding, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url       请求地址
     * @param content   BODY输入流，不会自动关闭流
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @param typeRef   类型
     * @param <T>       泛型类型
     * @return 响应结果
     */
    public static <T> T binaryBody(@NotNull String url, @Nullable String content, Map<String, ?> queryMap,
                                   @Nullable Map<String, ?> headerMap, @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, content, queryMap, headerMap, HttpBodyMethod.POST, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param content    BODY输入流，不会自动关闭流
     * @param queryMap   URL参数
     * @param headerMap  HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T binaryBody(@NotNull String url, @Nullable String content, @Nullable Map<String, ?> queryMap,
                                   @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod,
                                   @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, content, queryMap, headerMap, bodyMethod, null, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T binaryBody(@NotNull String url, @Nullable String content, Map<String, ?> queryMap,
                                   @Nullable Map<String, ?> headerMap, @Nullable Charset responseEncoding,
                                   @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, content, queryMap, headerMap, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T binaryBody(@NotNull String url, @Nullable String content, @Nullable Map<String, ?> queryMap,
                                   @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod,
                                   @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        InputStream in = null;
        try {
            if (content != null) {
                in = new ByteArrayInputStream(content.getBytes(CharsetUtil.UTF_8));
            }
            return binaryBody(url, ContentType.DEFAULT_BINARY, in, queryMap, headerMap, bodyMethod, responseEncoding, typeRef);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable File content, @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, content, (Charset) null, typeRef);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable File content, @Nullable Charset responseEncoding,
                                   @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, content, HttpBodyMethod.POST, responseEncoding, typeRef);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable File content, @NotNull HttpBodyMethod bodyMethod,
                                   @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, content, bodyMethod, (Charset) null, typeRef);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable File content, @NotNull HttpBodyMethod bodyMethod,
                                   @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, content, (Map<String, ?>) null, bodyMethod, responseEncoding, typeRef);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable File content, Map<String, ?> queryMap,
                                   @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, content, queryMap, HttpBodyMethod.POST, typeRef);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable File content, Map<String, ?> queryMap,
                                   @NotNull HttpBodyMethod bodyMethod, @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, content, queryMap, bodyMethod, (Charset) null, typeRef);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable File content, @Nullable Map<String, ?> queryMap,
                                   @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, content, queryMap, HttpBodyMethod.POST, responseEncoding, typeRef);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable File content, @Nullable Map<String, ?> queryMap,
                                   @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding,
                                   @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, content, queryMap, null, bodyMethod, responseEncoding, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url       请求地址
     * @param content   BODY输入流，不会自动关闭流
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @param typeRef   类型
     * @param <T>       泛型类型
     * @return 响应结果
     */
    public static <T> T binaryBody(@NotNull String url, @Nullable File content, Map<String, ?> queryMap,
                                   @Nullable Map<String, ?> headerMap, @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, content, queryMap, headerMap, HttpBodyMethod.POST, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param content    BODY输入流，不会自动关闭流
     * @param queryMap   URL参数
     * @param headerMap  HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T binaryBody(@NotNull String url, @Nullable File content, @Nullable Map<String, ?> queryMap,
                                   @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod,
                                   @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, content, queryMap, headerMap, bodyMethod, null, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T binaryBody(@NotNull String url, @Nullable File content, Map<String, ?> queryMap,
                                   @Nullable Map<String, ?> headerMap, @Nullable Charset responseEncoding,
                                   @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, content, queryMap, headerMap, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T binaryBody(@NotNull String url, @Nullable File content, @Nullable Map<String, ?> queryMap,
                                   @Nullable Map<String, ?> headerMap, @NotNull HttpBodyMethod bodyMethod,
                                   @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        FileInputStream in = null;
        ContentType contentType = null;
        try {
            if (content != null) {
                in = FileUtil.openFileInputStream(content);
                //TODO 获取文件的ContentType
                contentType = ContentType.parseByFileExt(FileUtil.getFileExt(content));
            }
            return binaryBody(url, contentType, in, queryMap, headerMap, bodyMethod, responseEncoding, typeRef);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                   @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, contentType, content, (Charset) null, typeRef);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                   @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, contentType, content, HttpBodyMethod.POST, responseEncoding, typeRef);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                   @NotNull HttpBodyMethod bodyMethod, @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, contentType, content, bodyMethod, null, typeRef);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                   @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding,
                                   @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, contentType, content, null, bodyMethod, responseEncoding, typeRef);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                   Map<String, ?> queryMap, @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, contentType, content, queryMap, HttpBodyMethod.POST, typeRef);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                   Map<String, ?> queryMap, @NotNull HttpBodyMethod bodyMethod, @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, contentType, content, queryMap, bodyMethod, null, typeRef);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                   @Nullable Map<String, ?> queryMap, @Nullable Charset responseEncoding,
                                   @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, contentType, content, queryMap, HttpBodyMethod.POST, responseEncoding, typeRef);
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
    public static <T> T binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                   @Nullable Map<String, ?> queryMap, @NotNull HttpBodyMethod bodyMethod,
                                   @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, contentType, content, queryMap, null, bodyMethod, responseEncoding, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url       请求地址
     * @param content   BODY输入流，不会自动关闭流
     * @param queryMap  URL参数
     * @param headerMap HTTP头参数
     * @param typeRef   类型
     * @param <T>       泛型类型
     * @return 响应结果
     */
    public static <T> T binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                   @Nullable Map<String, ?> queryMap, @Nullable Map<String, ?> headerMap,
                                   @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, contentType, content, queryMap, headerMap, HttpBodyMethod.POST, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url        请求地址
     * @param content    BODY输入流，不会自动关闭流
     * @param queryMap   URL参数
     * @param headerMap  HTTP头参数
     * @param bodyMethod HTTP请求方式
     * @param typeRef    类型
     * @param <T>        泛型类型
     * @return 响应结果
     */
    public static <T> T binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                   @Nullable Map<String, ?> queryMap, @Nullable Map<String, ?> headerMap,
                                   @NotNull HttpBodyMethod bodyMethod, @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, contentType, content, queryMap, headerMap, bodyMethod, null, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                   @Nullable Map<String, ?> queryMap, @Nullable Map<String, ?> headerMap,
                                   @Nullable Charset responseEncoding, @NotNull TypeRef<T> typeRef) {
        return binaryBody(url, contentType, content, queryMap, headerMap, HttpBodyMethod.POST, responseEncoding, typeRef);
    }

    /**
     * 发起POST(binary body)请求，并将响应结果转为Bean对象
     *
     * @param url              请求地址
     * @param content          BODY输入流，不会自动关闭流
     * @param queryMap         URL参数
     * @param headerMap        HTTP头参数
     * @param bodyMethod       HTTP请求方式
     * @param responseEncoding 指定响应内容的编码
     * @param typeRef          类型
     * @param <T>              泛型类型
     * @return 响应结果
     */
    public static <T> T binaryBody(@NotNull String url, @Nullable ContentType contentType, @Nullable InputStream content,
                                   @Nullable Map<String, ?> queryMap, @Nullable Map<String, ?> headerMap,
                                   @NotNull HttpBodyMethod bodyMethod, @Nullable Charset responseEncoding,
                                   @NotNull TypeRef<T> typeRef) {
        String text = binaryBody(url, contentType, content, queryMap, headerMap, bodyMethod, responseEncoding);
        return StringUtil.isEmpty(text) ? null : JSONUtil.fromJson(text, typeRef);
    }


    // endregion


    /**
     * @author miles.tang at 2021-02-01
     * @since 1.0
     */
    public enum HttpBodyMethod {

        DELETE,
        POST,
        PATCH,
        PUT,

        ;

        public HttpMethod toHttpMethod() {
            switch (this) {
                case PUT:
                    return HttpMethod.PUT;
                case POST:
                    return HttpMethod.POST;
                case PATCH:
                    return HttpMethod.PATCH;
                case DELETE:
                    return HttpMethod.DELETE;
                default:
                    throw new IllegalArgumentException();
            }
        }

    }

}
