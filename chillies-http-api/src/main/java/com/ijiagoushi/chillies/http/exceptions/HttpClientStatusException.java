package com.ijiagoushi.chillies.http.exceptions;

import com.ijiagoushi.chillies.core.lang.CharsetUtil;
import com.ijiagoushi.chillies.core.lang.StringUtil;
import com.ijiagoushi.chillies.http.HttpHeaders;
import com.ijiagoushi.chillies.http.HttpRequest;
import com.ijiagoushi.chillies.http.HttpStatus;

import java.nio.charset.Charset;

/**
 * Http Client 异常
 *
 * @author miles.tang
 */
public class HttpClientStatusException extends HttpClientResponseException {

    private static final long serialVersionUID = 1990L;

    private final HttpStatus statusCode;


    public HttpClientStatusException(HttpStatus statusCode, HttpRequest request) {
        this(statusCode, statusCode.name(), request);
    }

    public HttpClientStatusException(HttpStatus statusCode, String reason, HttpRequest request) {
        this(statusCode, reason, null, null, request);
    }

    public HttpClientStatusException(HttpStatus statusCode, String reason, byte[] responseBody, Charset responseCharset,
                                     HttpRequest request) {
        this(statusCode, reason, null, responseBody, responseCharset, request);
    }

    public HttpClientStatusException(HttpStatus statusCode, String reason, HttpHeaders responseHeaders,
                                     byte[] responseBody, Charset responseCharset, HttpRequest request) {
        super(statusCode.value() + " " + (StringUtil.hasLength(reason) ? reason : new String(responseBody, CharsetUtil.getCharset(responseCharset))),
                statusCode.value(), reason, responseHeaders, responseBody, responseCharset, request);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

}
