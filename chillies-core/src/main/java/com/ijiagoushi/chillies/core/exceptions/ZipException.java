package com.ijiagoushi.chillies.core.exceptions;

import java.io.IOException;

/**
 * ZIP Exception
 *
 * @author miles.tang
 */
public class ZipException extends GenericRuntimeException {

    public ZipException(String message) {
        super(message);
    }

    public ZipException(IOException e) {
        super(e);
    }

}
