package com.ijiagoushi.chillies.core.exceptions;

/**
 * 构建实例异常
 *
 * @author miles.tang at 2021-01-17
 * @since 1.0
 */
public class NewInstanceException extends GenericRuntimeException {

    /**
     * 反射操作异常构建
     *
     * @param e 异常
     */
    public NewInstanceException(ReflectiveOperationException e) {
        super(e);
    }

}
