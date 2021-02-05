package com.ijiagoushi.chillies.core.exceptions;

/**
 * File Not Found Exception
 *
 * @author miles.tang
 */
public class FileNotFoundRuntimeException extends GenericRuntimeException {

    private static final long serialVersionUID = 1990L;

    public FileNotFoundRuntimeException(String message) {
        super(message);
    }

}
