package com.ijiagoushi.chillies.http;

import com.ijiagoushi.chillies.core.http.ContentType;
import com.ijiagoushi.chillies.core.io.FileUtil;
import com.ijiagoushi.chillies.core.io.IOUtil;
import com.ijiagoushi.chillies.core.lang.CharsetUtil;
import com.ijiagoushi.chillies.core.lang.Preconditions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * HttpRequest Body
 *
 * @author miles.tang
 */
public class HttpRequestBody {

    protected ContentType contentType;

    private byte[] data;

    protected HttpRequestBody() {
        this.contentType = ContentType.ALL;
    }

    protected HttpRequestBody(ContentType contentType, byte[] data) {
        this.contentType = contentType;
        this.data = data;
    }

    public int contentLength() {
        return data != null ? data.length : 0;
    }

    public ContentType contentType() {
        return contentType;
    }

    public byte[] getData() {
        return data;
    }

    /**
     * 返回编码
     *
     * @return 编码，可能为{@code null}
     */
    public Charset getCharset() {
        return contentType != null ? contentType.getCharset() : CharsetUtil.UTF_8;
    }

    public static HttpRequestBody create(@Nullable ContentType contentType, @NotNull String content) {
        Charset encoding = CharsetUtil.UTF_8;
        if (contentType != null) {
            encoding = contentType.getCharset();
        }
        return new HttpRequestBody(contentType, content.getBytes(encoding));
    }

    public static HttpRequestBody create(@Nullable ContentType contentType, @NotNull byte[] data) {
        return new HttpRequestBody(contentType, data);
    }

    public static HttpRequestBody create(@Nullable ContentType contentType, @NotNull File file) {
        Preconditions.requireNonNull(file, "file == null");
        return new HttpRequestBody(contentType, FileUtil.readBytes(file));
    }

    public static HttpRequestBody create(@Nullable ContentType contentType, InputStream in) {
        Preconditions.requireNonNull(in, "in == null");
        return new HttpRequestBody(contentType, IOUtil.readBytes(in));
    }

}
