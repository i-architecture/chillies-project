package com.ijiagoushi.chillies.core.exceptions;

/**
 * Bean Exception
 *
 * @author miles.tang
 */
public class BeanException extends GenericRuntimeException {

    public BeanException(String msg) {
        super(msg);
    }

    public BeanException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BeanException(Throwable cause) {
        super(cause);
    }

}
