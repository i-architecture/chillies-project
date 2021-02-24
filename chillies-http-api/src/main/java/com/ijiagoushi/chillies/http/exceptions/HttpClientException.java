package com.ijiagoushi.chillies.http.exceptions;

import com.ijiagoushi.chillies.core.exceptions.GenericRuntimeException;

/**
 * @author miles.tang at 2021-02-07
 * @since 1.0
 */
public class HttpClientException extends GenericRuntimeException {

    public HttpClientException(String message) {
        super(message);
    }

}
