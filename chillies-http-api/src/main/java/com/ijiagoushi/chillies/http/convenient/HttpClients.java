package com.ijiagoushi.chillies.http.convenient;

import com.ijiagoushi.chillies.core.lang.StringUtil;
import com.ijiagoushi.chillies.http.Factory;
import com.ijiagoushi.chillies.http.HttpClient;
import org.jetbrains.annotations.NotNull;

/**
 * HttpClient更高级的工具类入口
 *
 * @author miles.tang at 2021-04-10
 * @since 1.0
 */
public class HttpClients {

    protected static HttpClient httpClient;

    static {
        httpClient = Factory.get().build(null);
    }

    /**
     * Get请求
     *
     * @param url 请求地址
     * @return {@link GetRequest}
     */
    public static GetRequest get(@NotNull String url) {
        return new GetRequest(url);
    }

    /**
     * Get请求
     *
     * @param urlPattern 请求地址
     * @return {@link GetRequest}
     */
    public static GetRequest get(@NotNull String urlPattern, Object... args) {
        return new GetRequest(StringUtil.format(urlPattern, args));
    }

    /**
     * FORM/POST表单提交
     *
     * @param url 提交地址
     * @return {@link PostRequest}
     */
    public static PostRequest post(String url) {
        return new PostRequest(url);
    }

    /**
     * FORM/POST表单提交
     *
     * @param urlPattern 提交地址
     * @return {@link PostRequest}
     */
    public static PostRequest post(String urlPattern, Object... args) {
        return new PostRequest(StringUtil.format(urlPattern, args));
    }

    /**
     * DELETE 请求
     *
     * @param url 请求地址
     * @return {@linkplain DeleteRequest}
     */
    public static DeleteRequest delete(@NotNull String url) {
        return new DeleteRequest(url);
    }

    /**
     * DELETE 请求
     *
     * @param urlPattern 请求地址
     * @return {@linkplain DeleteRequest}
     */
    public static DeleteRequest delete(@NotNull String urlPattern, Object... args) {
        return new DeleteRequest(StringUtil.format(urlPattern, args));
    }

    /**
     * HEAD 请求
     *
     * @param url 请求地址
     * @return {@linkplain HeadRequest}
     */
    public static HeadRequest head(@NotNull String url) {
        return new HeadRequest(url);
    }

    /**
     * HEAD 请求
     *
     * @param urlPattern 请求地址
     * @return {@linkplain HeadRequest}
     */
    public static HeadRequest head(@NotNull String urlPattern, Object... args) {
        return new HeadRequest(StringUtil.format(urlPattern, args));
    }

    /**
     * PATCH 请求
     *
     * @param url 请求地址
     * @return {@linkplain PatchRequest}
     */
    public static PatchRequest patch(@NotNull String url) {
        return new PatchRequest(url);
    }

    /**
     * PATCH 请求
     *
     * @param urlPattern 请求地址
     * @return {@linkplain PatchRequest}
     */
    public static PatchRequest patch(@NotNull String urlPattern, Object... args) {
        return new PatchRequest(StringUtil.format(urlPattern, args));
    }

    /**
     * PUT 请求
     *
     * @param url 请求地址
     * @return {@linkplain PutRequest}
     */
    public static PutRequest put(@NotNull String url) {
        return new PutRequest(url);
    }

    /**
     * PUT 请求
     *
     * @param urlPattern 请求地址
     * @return {@linkplain PutRequest}
     */
    public static PutRequest put(@NotNull String urlPattern, Object... args) {
        return new PutRequest(StringUtil.format(urlPattern, args));
    }

}
