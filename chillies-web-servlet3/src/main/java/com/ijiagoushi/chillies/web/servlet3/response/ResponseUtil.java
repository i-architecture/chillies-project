package com.ijiagoushi.chillies.web.servlet3.response;

import com.ijiagoushi.chillies.core.exceptions.IoRuntimeException;
import com.ijiagoushi.chillies.core.http.ContentType;
import com.ijiagoushi.chillies.core.lang.CharsetUtil;
import com.ijiagoushi.chillies.core.lang.Preconditions;
import com.ijiagoushi.chillies.json.JSONUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * HTTP Response工具类
 *
 * @author miles.tang
 */
public class ResponseUtil {

    private ResponseUtil() {
        throw new AssertionError("No com.jishukezhan.servlet3.ResponseUtil instances for you!");
    }

    /**
     * 向{@linkplain HttpServletResponse}写入JSON,默认采用{@code UTF-8}
     *
     * @param obj      对象
     * @param response HTTP响应对象
     */
    public static void writeJson(@NotNull Object obj, @NotNull HttpServletResponse response) {
        writeJson(JSONUtil.toJson(Preconditions.requireNonNull(obj)), response);
    }

    /**
     * 向{@linkplain HttpServletResponse}写入JSON,默认采用{@code UTF-8}
     *
     * @param json     json字符串
     * @param response HTTP响应对象
     */
    public static void writeJson(@NotNull String json, @NotNull HttpServletResponse response) {
        writeJson(json, null, response);
    }

    public static void writeJson(@NotNull Object obj, @NotNull Charset encoding, @NotNull HttpServletResponse response) {
        writeJson(JSONUtil.toJson(Preconditions.requireNonNull(obj)), encoding, response);
    }

    /**
     * 向{@linkplain HttpServletResponse}写入JSON,采用指定编码
     *
     * @param json     json字符串
     * @param encoding 指定编码,如果为空则使用{@code UTF-8}
     * @param response HTTP响应对象
     */
    public static void writeJson(@NotNull String json, @Nullable Charset encoding, @NotNull HttpServletResponse response) {
        Preconditions.requireNotEmpty(json, "json is null or empty");
        Preconditions.requireNonNull(response, "response == null");
        encoding = CharsetUtil.getCharset(encoding, CharsetUtil.UTF_8);
        write(json, ContentType.APPLICATION_JSON.charset(encoding), response);
    }

    private static void write(String data, ContentType contentType, HttpServletResponse response) {
        response.setCharacterEncoding(contentType.getCharset().name());
        response.setContentType(contentType.toString());
        try {
            response.getWriter().write(data);
            response.getWriter().flush();
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

}
