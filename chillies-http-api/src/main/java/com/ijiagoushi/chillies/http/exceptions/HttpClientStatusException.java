package com.ijiagoushi.chillies.http.exceptions;

import com.ijiagoushi.chillies.core.exceptions.GenericRuntimeException;
import com.ijiagoushi.chillies.http.HttpRequest;

/**
 * Http Client 异常
 *
 * @author miles.tang
 */
public class HttpClientStatusException extends GenericRuntimeException {

    private static final long serialVersionUID = 1990L;

    private int status;

    private HttpRequest request;

    /**
     * @param message the reason for the failure.
     */
    public HttpClientStatusException(int status, String message, HttpRequest request) {
        super(message);
        this.status = status;
        this.request = request;
    }

    public int getStatus() {
        return status;
    }

    public HttpRequest getRequest() {
        return request;
    }

}
