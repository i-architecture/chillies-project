package com.ijiagoushi.chillies.http.exceptions;

import com.ijiagoushi.chillies.http.HttpHeaders;
import com.ijiagoushi.chillies.http.HttpRequest;
import com.ijiagoushi.chillies.http.HttpStatus;

import java.nio.charset.Charset;

/**
 * Exception thrown when an HTTP 4xx is received
 *
 * @author miles.tang at 2021-03-15
 * @since 1.0
 */
public class HttpClientErrorException extends HttpClientStatusException {

    private static final long serialVersionUID = 1990L;

    public HttpClientErrorException(HttpStatus statusCode, HttpRequest request) {
        super(statusCode, request);
    }

    public HttpClientErrorException(HttpStatus statusCode, String reason, HttpRequest request) {
        super(statusCode, reason, request);
    }

    public HttpClientErrorException(HttpStatus statusCode, String reason, byte[] responseBody,
                                    Charset responseCharset, HttpRequest request) {
        super(statusCode, reason, responseBody, responseCharset, request);
    }

    public HttpClientErrorException(HttpStatus statusCode, String reason, HttpHeaders responseHeaders,
                                    byte[] responseBody, Charset responseCharset, HttpRequest request) {
        super(statusCode, reason,responseHeaders, responseBody, responseCharset, request);
    }

}
