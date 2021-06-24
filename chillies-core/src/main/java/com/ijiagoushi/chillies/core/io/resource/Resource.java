package com.ijiagoushi.chillies.core.io.resource;

import com.ijiagoushi.chillies.core.exceptions.IoRuntimeException;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * 各种资源的接口定义
 *
 * @author miles.tang
 */
public interface Resource {

//    /**
//     * 判断资源是否存在
//     *
//     * @return 返回{@code true}则存在，否则不存在
//     */
//    boolean exists();

    /**
     * 返回解析后的{@code URL}，如果不适用则返回{@code null}
     *
     * @return URL
     * @throws IoRuntimeException IO异常包装类
     */
    URL getUrl() throws IoRuntimeException;

    /**
     * 获取输入流，该输入流支持多次读取
     *
     * @return {@link InputStream}
     * @throws IoRuntimeException 读取资源失败时抛出异常
     */
    InputStream getInputStream() throws IoRuntimeException;

    /**
     * 读取并转为字符串
     *
     * @param encoding 指定编码，默认采用UTF-8
     * @return 资源的内容
     * @throws IoRuntimeException 读取资源失败时抛出异常
     */
    String readToStr(@Nullable Charset encoding) throws IoRuntimeException;

    /**
     * 返回资源的内容长度，如果资源不存在则返回{@code -1}
     *
     * @return 返回资源长度
     * @throws IoRuntimeException 读取资源失败时抛出异常
     */
    long contentLength() throws IoRuntimeException;

}
