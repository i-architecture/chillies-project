package com.ijiagoushi.chillies.core.exceptions;

import com.ijiagoushi.chillies.core.lang.StringUtil;

/**
 * 反射工具异常
 *
 * @author miles.tang at 2021-01-17
 * @since 1.0
 */
public class ReflectUtilException extends GenericRuntimeException {

    /**
     * 构造器
     *
     * @param template 错误消息模板
     * @param params   模板参数对应的值
     */
    public ReflectUtilException(String template, Object... params) {
        super(StringUtil.format(template, params));
    }

    /**
     * 构造器
     *
     * @param message 错误信息
     * @param cause   异常信息
     */
    public ReflectUtilException(String message, Exception cause) {
        super(message, cause);
    }

    public ReflectUtilException(Exception e) {
        super(e);
    }

}
