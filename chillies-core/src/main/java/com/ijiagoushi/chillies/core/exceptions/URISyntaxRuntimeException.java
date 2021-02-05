package com.ijiagoushi.chillies.core.exceptions;

import java.net.URISyntaxException;

/**
 * URI Syntax Exception
 *
 * @author miles.tang
 */
public class URISyntaxRuntimeException extends GenericRuntimeException {

    private static final long serialVersionUID = 1990L;

    public URISyntaxRuntimeException(URISyntaxException cause) {
        super(cause);
    }

}
