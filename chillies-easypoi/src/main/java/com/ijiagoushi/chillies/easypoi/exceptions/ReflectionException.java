package com.ijiagoushi.chillies.easypoi.exceptions;

/**
 * <p>
 * 类的反射出现异常
 * </p>
 *
 * @author mzlion on 2016/6/16.
 */
public class ReflectionException extends ExcelException {

    /**
     * Construct a {@code ReflectionException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public ReflectionException(String message) {
        super(message);
    }

    /**
     * Construct a {@code ReflectionException} with the  nested exception.
     *
     * @param cause the nested exception
     */
    public ReflectionException(Throwable cause) {
        super(cause);
    }
}
