package com.ijiagoushi.chillies.core.io.resource;

import com.ijiagoushi.chillies.core.exceptions.IoRuntimeException;
import com.ijiagoushi.chillies.core.http.UrlUtil;
import com.ijiagoushi.chillies.core.io.FastByteArrayOutputStream;
import com.ijiagoushi.chillies.core.io.IOUtil;
import com.ijiagoushi.chillies.core.lang.Preconditions;

import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;

/**
 * URL的资源
 *
 * @author miles.tang
 */
public class UrlResource implements Resource, Serializable {

    private static final long serialVersionUID = 1990L;

    protected URL url;

    public UrlResource() {

    }

    public UrlResource(URL url) {
        this.url = Preconditions.requireNonNull(url);
    }

    public void setUrl(URL url) {
        this.url = Preconditions.requireNonNull(url);
    }

    /**
     * 返回解析后的{@code URL}，如果不适用则返回{@code null}
     *
     * @return URL
     * @throws IoRuntimeException IO异常包装类
     */
    @Override
    public URL getUrl() throws IoRuntimeException {
        return url;
    }

    /**
     * 获取输入流，该输入流支持多次读取
     *
     * @return {@link InputStream}
     * @throws IoRuntimeException 读取资源失败时抛出异常
     */
    @Override
    public InputStream getInputStream() throws IoRuntimeException {
        return UrlUtil.openStream(url);
    }

    /**
     * 返回资源的内容长度，如果资源不存在则返回{@code -1}
     *
     * @return 返回资源长度
     * @throws IoRuntimeException 读取资源失败时抛出异常
     */
    @Override
    public long contentLength() throws IoRuntimeException {
        InputStream in = getInputStream();
        if (in == null) {
            return -1;
        }
        FastByteArrayOutputStream out = new FastByteArrayOutputStream();
        return IOUtil.copy(in, out, true);
    }

    /**
     * 返回路径
     *
     * @return 返回URL路径
     */
    @Override
    public String toString() {
        return (null == this.url) ? "null" : this.url.toString();
    }

}
