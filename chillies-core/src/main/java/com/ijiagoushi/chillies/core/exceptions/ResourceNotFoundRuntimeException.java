package com.ijiagoushi.chillies.core.exceptions;

/**
 * Resource Not Found Exception
 *
 * @author miles.tang
 */
public class ResourceNotFoundRuntimeException extends GenericRuntimeException {

    private static final long serialVersionUID = 1990L;

    public ResourceNotFoundRuntimeException(String message) {
        super(message);
    }

}
