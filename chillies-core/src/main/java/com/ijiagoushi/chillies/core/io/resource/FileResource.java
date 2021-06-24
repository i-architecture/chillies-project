package com.ijiagoushi.chillies.core.io.resource;

import com.ijiagoushi.chillies.core.exceptions.IoRuntimeException;
import com.ijiagoushi.chillies.core.http.UrlUtil;
import com.ijiagoushi.chillies.core.io.FileUtil;
import com.ijiagoushi.chillies.core.io.IOUtil;
import com.ijiagoushi.chillies.core.lang.CharsetUtil;
import com.ijiagoushi.chillies.core.lang.Preconditions;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * 文件资源
 *
 * @author miles.tang
 */
public class FileResource extends UrlResource {

    private static final long serialVersionUID = 1990L;

    /**
     * 文件
     */
    private final File file;

    public FileResource(File file) {
        this.file = Preconditions.requireNonNull(file);
        this.url = UrlUtil.getUrl(file);
    }

    public FileResource(String path) {
        this(new File(path));
    }

    /**
     * 返回文件对象，如果对象不存在则返回{@code null}
     */
    public File getFile() {
        return this.file;
    }

    /**
     * 获取输入流，该输入流支持多次读取
     *
     * @return {@link InputStream}
     * @throws IoRuntimeException 读取资源失败时抛出异常
     */
    @Override
    public InputStream getInputStream() throws IoRuntimeException {
        return FileUtil.openFileInputStream(file);
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
        return IOUtil.readToString(this.getInputStream(), charset, true);
    }

    /**
     * 返回资源的内容长度，如果资源不存在则返回{@code -1}
     *
     * @return 返回资源长度
     * @throws IoRuntimeException 读取资源失败时抛出异常
     */
    @Override
    public long contentLength() throws IoRuntimeException {
        return file.length();
    }

}
