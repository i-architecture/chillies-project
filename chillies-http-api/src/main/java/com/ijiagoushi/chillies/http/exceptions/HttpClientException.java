package com.ijiagoushi.chillies.http.exceptions;

import com.ijiagoushi.chillies.core.exceptions.GenericRuntimeException;

/**
 * Base class for exceptions thrown by {@link com.ijiagoushi.chillies.http.HttpClient} whenever it encounters client-side HTTP errors.
 *
 * @author miles.tang at 2021-02-07
 * @since 1.0
 */
public class HttpClientException extends GenericRuntimeException {

    private static final long serialVersionUID = 1990L;

    public HttpClientException(String message) {
        super(message);
    }

    public HttpClientException(String message, Throwable ex) {
        super(message, ex);
    }

}
