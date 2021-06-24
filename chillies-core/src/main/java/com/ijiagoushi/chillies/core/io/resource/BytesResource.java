package com.ijiagoushi.chillies.core.io.resource;

import com.ijiagoushi.chillies.core.exceptions.IoRuntimeException;
import com.ijiagoushi.chillies.core.lang.CharsetUtil;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * 字节资源
 *
 * @author miles.tang
 */
public class BytesResource implements Resource, Serializable {

    private static final long serialVersionUID = 1990L;

    private final byte[] bytes;

    public BytesResource(final byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * 返回底层字节数组
     *
     * @return 字节数组
     */
    public final byte[] getBytes() {
        return bytes;
    }

    /**
     * 返回解析后的{@code URL}，如果不适用则返回{@code null}
     *
     * @return URL
     * @throws IoRuntimeException IO异常包装类
     */
    @Override
    public URL getUrl() throws IoRuntimeException {
        return null;
    }

    /**
     * 获取输入流，该输入流支持多次读取
     *
     * @return {@link InputStream}
     * @throws IoRuntimeException 读取资源失败时抛出异常
     */
    @Override
    public InputStream getInputStream() throws IoRuntimeException {
        return new ByteArrayInputStream(bytes);
    }

    /**
     * 读取并转为字符串
     *
     * @param encoding 指定编码，默认采用UTF-8
     * @return 资源的内容
     * @throws IoRuntimeException 读取资源失败时抛出异常
     */
    @Override
    public String readToStr(@Nullable Charset encoding) throws IoRuntimeException {
        Charset charset = CharsetUtil.getCharset(encoding, CharsetUtil.UTF_8);
        return new String(bytes, charset);
    }

    /**
     * 返回资源的内容长度，如果资源不存在则返回{@code -1}
     *
     * @return 返回资源长度
     * @throws IoRuntimeException 读取资源失败时抛出异常
     */
    @Override
    public long contentLength() throws IoRuntimeException {
        return bytes.length;
    }

}
