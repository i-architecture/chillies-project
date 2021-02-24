package com.ijiagoushi.chillies.http.easy;

import com.ijiagoushi.chillies.core.lang.CollectionUtil;
import com.ijiagoushi.chillies.http.HttpHeaders;
import com.ijiagoushi.chillies.http.HttpOptions;
import com.ijiagoushi.chillies.json.TypeRef;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * HTTP的工具类，提供了一些便捷的工具方法，更丰富的工具方法可使用其他工具类
 * @author miles.tang at 2021-02-08
 * @see GetHttpUtil
 * @see BinaryHttpUtil
 * @see FormDataHttpUtil
 * @see HtmlBodyHttpUtil
 * @see JsBodyHttpUtil
 * @see JsonBodyHttpUtil
 * @see TextBodyHttpUtil
 * @see XmlBodyHttpUtil
 * @since 1.0
 */
public class HttpUtil {


    // region Http Get Method

    public static String get(@NotNull String url) {
        return get(url, (Map<String, ?>) null);
    }

    public static String get(@NotNull String url, @Nullable Map<String, ?> queryParams) {
        return get(url, queryParams, (HttpHeaders) null);
    }

    public static String get(@NotNull String url, @Nullable Map<String, ?> queryParams, @Nullable HttpHeaders headers) {
        return get(url, queryParams, headers, (HttpOptions) null);
    }

    public static String get(@NotNull String url, @Nullable Map<String, ?> queryParams, @Nullable HttpHeaders headers,
                             @Nullable HttpOptions options) {
        return GetHttpUtil.execute(url, queryParams, headers, (Charset) null, options);
    }

    public static <T> T get(String url, Class<T> clazz) {
        return get(url, null, clazz);
    }

    public static <T> T get(String url, @Nullable Map<String, ?> queryParams, @NotNull Class<T> clazz) {
        return get(url, queryParams, null, clazz);
    }

    public static <T> T get(@NotNull String url, @Nullable Map<String, ?> queryParams, @Nullable HttpHeaders headers,
                            @NotNull Class<T> clazz) {
        return get(url, queryParams, headers, null, clazz);
    }

    public static <T> T get(@NotNull String url, @Nullable Map<String, ?> queryParams, @Nullable HttpHeaders headers,
                            @Nullable HttpOptions options, @NotNull Class<T> clazz) {
        return GetHttpUtil.execute(url, queryParams, headers, null, options, clazz);
    }

    public static <T> T get(@NotNull String url, @NotNull TypeRef<T> typeRef) {
        return get(url, null, typeRef);
    }

    public static <T> T get(@NotNull String url, @Nullable Map<String, ?> queryParams, @NotNull TypeRef<T> typeRef) {
        return get(url, queryParams, null, typeRef);
    }

    public static <T> T get(@NotNull String url, @Nullable Map<String, ?> queryParams, @Nullable HttpHeaders headers,
                            @NotNull TypeRef<T> typeRef) {
        return get(url, queryParams, headers, null, typeRef);
    }

    public static <T> T get(@NotNull String url, @Nullable Map<String, ?> queryParams, @Nullable HttpHeaders headers,
                            @Nullable HttpOptions options, @NotNull TypeRef<T> typeRef) {
        return GetHttpUtil.execute(url, queryParams, headers, null, options, typeRef);
    }

    // endregion


    // region x-www-form-urlencoded/form-data

    public static String post(@NotNull String url) {
        return post(url, (Map<String, ?>) null);
    }

    public static String post(@NotNull String url, @Nullable Map<String, ?> postParams) {
        return post(url, postParams, (Map<String, ?>) null);
    }

    public static String post(@NotNull String url, @Nullable Map<String, ?> postParams, @Nullable Map<String, ?> queryParams) {
        return post(url, postParams, queryParams, (HttpHeaders) null);
    }

    public static String post(@NotNull String url, @Nullable Map<String, ?> postParams, @Nullable Map<String, ?> queryParams,
                              @Nullable HttpHeaders headers) {
        return post(url, postParams, queryParams, headers, (HttpOptions) null);
    }

    public static String post(@NotNull String url, @Nullable Map<String, ?> postParams, @Nullable Map<String, ?> queryParams,
                              @Nullable HttpHeaders headers, @Nullable HttpOptions options) {
        if (CollectionUtil.isNotEmpty(postParams) && FormDataHttpUtil.isFormData(postParams)) {
            return FormDataHttpUtil.formData(url, postParams, queryParams, headers, options);
        } else {
            Map<String, Object> postMap = new HashMap<>();
            if (CollectionUtil.isNotEmpty(postParams)) {
                postMap.putAll(postParams);
            }
            if (CollectionUtil.isNotEmpty(queryParams)) {
                postMap.putAll(queryParams);
            }
            return FormDataHttpUtil.formUrlEncoded(url, postMap, headers, options);
        }
    }

    public static <T> T post(@NotNull String url, @NotNull Class<T> clazz) {
        return post(url, null, clazz);
    }

    public static <T> T post(@NotNull String url, @Nullable Map<String, ?> postParams, @NotNull Class<T> clazz) {
        return post(url, postParams, null, clazz);
    }

    public static <T> T post(@NotNull String url, @Nullable Map<String, ?> postParams, @Nullable Map<String, ?> queryParams,
                             @NotNull Class<T> clazz) {
        return post(url, postParams, queryParams, null, clazz);
    }

    public static <T> T post(@NotNull String url, @Nullable Map<String, ?> postParams, @Nullable Map<String, ?> queryParams,
                             @Nullable HttpHeaders headers, @NotNull Class<T> clazz) {
        return post(url, postParams, queryParams, headers, null, clazz);
    }

    public static <T> T post(@NotNull String url, @Nullable Map<String, ?> postParams, @Nullable Map<String, ?> queryParams,
                             @Nullable HttpHeaders headers, @Nullable HttpOptions options, @NotNull Class<T> clazz) {
        if (CollectionUtil.isNotEmpty(postParams) && FormDataHttpUtil.isFormData(postParams)) {
            return FormDataHttpUtil.formData(url, postParams, queryParams, headers, options, clazz);
        } else {
            Map<String, Object> postMap = new HashMap<>();
            if (CollectionUtil.isNotEmpty(postParams)) {
                postMap.putAll(postParams);
            }
            if (CollectionUtil.isNotEmpty(queryParams)) {
                postMap.putAll(queryParams);
            }
            return FormDataHttpUtil.formUrlEncoded(url, postMap, headers, clazz, options);
        }
    }

    public static <T> T post(@NotNull String url, @NotNull TypeRef<T> typeRef) {
        return post(url, null, typeRef);
    }

    public static <T> T post(@NotNull String url, @Nullable Map<String, ?> postParams, @NotNull TypeRef<T> typeRef) {
        return post(url, postParams, null, typeRef);
    }

    public static <T> T post(@NotNull String url, @Nullable Map<String, ?> postParams, @Nullable Map<String, ?> queryParams,
                             @NotNull TypeRef<T> typeRef) {
        return post(url, postParams, queryParams, null, typeRef);
    }

    public static <T> T post(@NotNull String url, @Nullable Map<String, ?> postParams, @Nullable Map<String, ?> queryParams,
                             @Nullable HttpHeaders headers, @NotNull TypeRef<T> typeRef) {
        return post(url, postParams, queryParams, headers, null, typeRef);
    }

    public static <T> T post(@NotNull String url, @Nullable Map<String, ?> postParams, @Nullable Map<String, ?> queryParams,
                             @Nullable HttpHeaders headers, @Nullable HttpOptions options, @NotNull TypeRef<T> typeRef) {
        if (CollectionUtil.isNotEmpty(postParams) && FormDataHttpUtil.isFormData(postParams)) {
            return FormDataHttpUtil.formData(url, postParams, queryParams, headers, options, typeRef);
        } else {
            Map<String, Object> postMap = new HashMap<>();
            if (CollectionUtil.isNotEmpty(postParams)) {
                postMap.putAll(postParams);
            }
            if (CollectionUtil.isNotEmpty(queryParams)) {
                postMap.putAll(queryParams);
            }
            return FormDataHttpUtil.formUrlEncoded(url, postMap, headers, typeRef, options);
        }
    }

    // endregion


    // region Http Text Raw Body Method

    public static String textBody(@NotNull String url) {
        return textBody(url, (Object) null);
    }

    public static String textBody(@NotNull String url, @Nullable Object body) {
        return textBody(url, body, (Map<String, ?>) null);
    }

    public static String textBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryParams) {
        return textBody(url, body, queryParams, (HttpHeaders) null);
    }

    public static String textBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryParams,
                                  @Nullable HttpHeaders headers) {
        return textBody(url, body, queryParams, headers, (HttpOptions) null);
    }

    public static String textBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryParams,
                                  @Nullable HttpHeaders headers, HttpOptions options) {
        return TextBodyHttpUtil.execute(url, body, queryParams, headers, options);
    }


    public static <T> T textBody(@NotNull String url, @NotNull Class<T> clazz) {
        return textBody(url, null, clazz);
    }

    public static <T> T textBody(@NotNull String url, @Nullable Object body, @NotNull Class<T> clazz) {
        return textBody(url, body, null, clazz);
    }

    public static <T> T textBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryParams,
                                 @NotNull Class<T> clazz) {
        return textBody(url, body, queryParams, null, clazz);
    }

    public static <T> T textBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryParams,
                                 @Nullable HttpHeaders headers, @NotNull Class<T> clazz) {
        return textBody(url, body, queryParams, headers, null, clazz);
    }

    public static <T> T textBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryParams,
                                 @Nullable HttpHeaders headers, @Nullable HttpOptions options, @NotNull Class<T> clazz) {
        return TextBodyHttpUtil.execute(url, body, queryParams, headers, options, clazz);
    }


    public static <T> T textBody(@NotNull String url, @NotNull TypeRef<T> typeRef) {
        return textBody(url, null, typeRef);
    }

    public static <T> T textBody(@NotNull String url, @Nullable Object body, @NotNull TypeRef<T> typeRef) {
        return textBody(url, body, null, typeRef);
    }

    public static <T> T textBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryParams,
                                 @NotNull TypeRef<T> typeRef) {
        return textBody(url, body, queryParams, null, typeRef);
    }

    public static <T> T textBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryParams,
                                 @Nullable HttpHeaders headers, @NotNull TypeRef<T> typeRef) {
        return textBody(url, body, queryParams, headers, null, typeRef);
    }

    public static <T> T textBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryParams,
                                 @Nullable HttpHeaders headers, @Nullable HttpOptions options, @NotNull TypeRef<T> typeRef) {
        return TextBodyHttpUtil.execute(url, body, queryParams, headers, options, typeRef);
    }

    // endregion


    // region Http JSON Raw Body Method

    public static String jsonBody(@NotNull String url) {
        return jsonBody(url, (Object) null);
    }

    public static String jsonBody(@NotNull String url, @Nullable Object body) {
        return jsonBody(url, body, (Map<String, ?>) null);
    }

    public static String jsonBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryParams) {
        return jsonBody(url, body, queryParams, (HttpHeaders) null);
    }

    public static String jsonBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryParams,
                                  @Nullable HttpHeaders headers) {
        return jsonBody(url, body, queryParams, headers, (HttpOptions) null);
    }

    public static String jsonBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryParams,
                                  @Nullable HttpHeaders headers, HttpOptions options) {
        return JsonBodyHttpUtil.execute(url, body, queryParams, headers, options);
    }


    public static <T> T jsonBody(@NotNull String url, @NotNull Class<T> clazz) {
        return jsonBody(url, null, clazz);
    }

    public static <T> T jsonBody(@NotNull String url, @Nullable Object body, @NotNull Class<T> clazz) {
        return jsonBody(url, body, null, clazz);
    }

    public static <T> T jsonBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryParams,
                                 @NotNull Class<T> clazz) {
        return jsonBody(url, body, queryParams, null, clazz);
    }

    public static <T> T jsonBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryParams,
                                 @Nullable HttpHeaders headers, @NotNull Class<T> clazz) {
        return jsonBody(url, body, queryParams, headers, null, clazz);
    }

    public static <T> T jsonBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryParams,
                                 @Nullable HttpHeaders headers, @Nullable HttpOptions options, @NotNull Class<T> clazz) {
        return JsonBodyHttpUtil.execute(url, body, queryParams, headers, options, clazz);
    }


    public static <T> T jsonBody(@NotNull String url, @NotNull TypeRef<T> typeRef) {
        return jsonBody(url, null, typeRef);
    }

    public static <T> T jsonBody(@NotNull String url, @Nullable Object body, @NotNull TypeRef<T> typeRef) {
        return jsonBody(url, body, null, typeRef);
    }

    public static <T> T jsonBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryParams,
                                 @NotNull TypeRef<T> typeRef) {
        return jsonBody(url, body, queryParams, null, typeRef);
    }

    public static <T> T jsonBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryParams,
                                 @Nullable HttpHeaders headers, @NotNull TypeRef<T> typeRef) {
        return jsonBody(url, body, queryParams, headers, null, typeRef);
    }

    public static <T> T jsonBody(@NotNull String url, @Nullable Object body, @Nullable Map<String, ?> queryParams,
                                 @Nullable HttpHeaders headers, @Nullable HttpOptions options, @NotNull TypeRef<T> typeRef) {
        return JsonBodyHttpUtil.execute(url, body, queryParams, headers, options, typeRef);
    }

    // endregion


}
