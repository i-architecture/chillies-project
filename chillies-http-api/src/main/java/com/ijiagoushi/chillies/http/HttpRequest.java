package com.ijiagoushi.chillies.http;

import com.ijiagoushi.chillies.core.http.UrlUtil;
import com.ijiagoushi.chillies.core.lang.CollectionUtil;
import com.ijiagoushi.chillies.core.lang.Preconditions;
import com.ijiagoushi.chillies.core.lang.StringUtil;
import com.ijiagoushi.chillies.core.map.MultiValueLinkedMap;
import com.ijiagoushi.chillies.core.map.MultiValueMap;
import com.ijiagoushi.chillies.http.constants.HttpMethod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 请求对象
 *
 * @author miles.tang
 */
public final class HttpRequest {

    /**
     * 请求方法
     */
    private final HttpMethod method;

    /**
     * 请求地址和请求参数
     */
    private final String url;

    /**
     * 请求消息头
     */
    private final Map<String, List<String>> headers;

    /**
     * URL参数
     */
    private final MultiValueMap<String, String> queryParams;

    /**
     * 请求内容
     */
    private final HttpRequestBody body;

    HttpRequest(Builder builder) {
        this.method = builder.method;
        this.headers = Collections.unmodifiableMap(builder.headers);
        this.queryParams = new MultiValueLinkedMap<>(builder.queryParams);
        int index = builder.url.indexOf('?');
        if (index > 0) {
            String paramStr = builder.url.substring(index + 1);
            this.queryParams.addAll(UrlUtil.parseUrlParams(paramStr));
            this.url = builder.url.substring(0, index);
        } else {
            this.url = builder.url;
        }
        this.body = builder.body;
    }

    /**
     * 返回请求URL
     *
     * @return URL url
     */
    public String url() {
        return url;
    }

    /**
     * HttpRequest Headers.
     *
     * @return the request headers.
     */
    public Map<String, List<String>> headers() {
        return headers;
    }

    public MultiValueMap<String, String> queryParams() {
        return queryParams;
    }

    public HttpMethod method() {
        return method;
    }

    @Nullable
    public HttpRequestBody body() {
        return body;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        /**
         * 请求方法
         */
        private HttpMethod method;

        /**
         * 请求地址和请求参数
         */
        private String url;

        /**
         * 请求消息头
         */
        private final Map<String, List<String>> headers;

        private final MultiValueMap<String, String> queryParams;

        /**
         * 请求内容
         */
        private HttpRequestBody body;

        public Builder() {
            this.method = HttpMethod.GET;
            this.headers = new HashMap<>();
            this.queryParams = new MultiValueLinkedMap<>();
        }

        public Builder(HttpRequest request) {
            this.method = request.method;
            this.url = request.url;
            this.headers = new HashMap<>(request.headers);
            this.body = request.body;
            this.queryParams = request.queryParams;
        }

        public Builder method(@NotNull HttpMethod method) {
            this.method = Preconditions.requireNonNull(method, "method == null ");
            return this;
        }

        public Builder url(@NotNull String url) {
            this.url = Preconditions.requireNotEmpty(url, "url is null or empty");
            return this;
        }

        public Builder replaceHeader(String name, String value) {
            Preconditions.requireNotEmpty(name, "name is null or empty");
            Preconditions.requireNonNull(value, "value == null");

            List<String> values = new ArrayList<>();
            values.add(value);
            headers.put(name, values);
            return this;
        }

        public Builder addHeader(String name, String value) {
            Preconditions.requireNotEmpty(name, "name is null or empty");
            Preconditions.requireNonNull(value, "value == null");

            List<String> values = headers.computeIfAbsent(name, k -> new ArrayList<>());
            values.add(value);
            return this;
        }

        public Builder removeHeader(String name) {
            headers.remove(name);
            return this;
        }

        public Builder headers(Map<String, ?> headerMap) {
            if (CollectionUtil.isNotEmpty(headerMap)) {
                headerMap.forEach((BiConsumer<String, Object>) (name, values) -> {
                    if (StringUtil.isEmpty(name) || values == null) {
                        return;
                    }
                    List<String> oValues = headers.computeIfAbsent(name, k -> new ArrayList<>());
                    if (values instanceof Collection<?>) {
                        ((Collection<?>) values).forEach((Consumer<Object>) value -> {
                            if (value != null) {
                                oValues.add(value.toString());
                            }
                        });
                    } else {
                        oValues.add(values.toString());
                    }
                });
            }
            return this;
        }

        public Builder queryParams(Map<String, ?> queryParamMap) {
            if (CollectionUtil.isNotEmpty(queryParamMap)) {
                queryParamMap.forEach((BiConsumer<String, Object>) (name, values) -> {
                    if (StringUtil.isEmpty(name) || values == null) {
                        return;
                    }
                    List<String> oValues = queryParams.computeIfAbsent(name, k -> new ArrayList<>());
                    if (values instanceof Collection<?>) {
                        ((Collection<?>) values).forEach((Consumer<Object>) value -> {
                            if (value != null) {
                                oValues.add(value.toString());
                            }
                        });
                    } else {
                        oValues.add(values.toString());
                    }
                });
            }
            return this;
        }

        public Builder body(@Nullable HttpRequestBody body) {
            return method(this.method, body);
        }

        public Builder method(HttpMethod method, @Nullable HttpRequestBody body) {
            method(method);
            if (method == HttpMethod.GET || method == HttpMethod.HEAD) {
                // 不能有body
                if (body != null) {
                    throw new IllegalArgumentException("method " + method.name() + " must not have a request body");
                }
            } else if (method == HttpMethod.POST || method == HttpMethod.PUT || method == HttpMethod.PATCH) {
                if (body == null) {
                    throw new IllegalArgumentException("method " + method.name() + " must have a request body");
                }
            }
            this.body = body;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(this);
        }
    }

}
