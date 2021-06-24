package com.ijiagoushi.chillies.core.exceptions;

import java.text.ParseException;
import java.time.temporal.UnsupportedTemporalTypeException;

/**
 * 日期异常类
 *
 * @author miles.tang
 */
public class DateRuntimeException extends GenericRuntimeException {

    private static final long serialVersionUID = 1990L;

    public DateRuntimeException(String message) {
        super(message);
    }

    public DateRuntimeException(UnsupportedTemporalTypeException cause) {
        super(cause);
    }

    public DateRuntimeException(ParseException cause) {
        super(cause);
    }

}
