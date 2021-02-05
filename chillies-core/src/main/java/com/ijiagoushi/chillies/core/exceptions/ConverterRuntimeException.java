package com.ijiagoushi.chillies.core.exceptions;

/**
 * 转换器异常
 *
 * @author miles.tang at 2021-01-22
 * @since 1.0
 */
public class ConverterRuntimeException extends GenericRuntimeException {

    private final int code;

    public ConverterRuntimeException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
