package com.ijiagoushi.chillies.http.exceptions;

import com.ijiagoushi.chillies.http.HttpHeaders;
import com.ijiagoushi.chillies.http.HttpRequest;

import java.nio.charset.Charset;

/**
 * Exception thrown when an unknown (or custom) HTTP status code is received.
 *
 * @author miles.tang at 2021-03-15
 * @since 1.0
 */
public class UnknownHttpStatusCodeException extends HttpClientResponseException {

    private static final long serialVersionUID = 1990L;

    public UnknownHttpStatusCodeException(int rawStatus, String reason, HttpHeaders responseHeaders,
                                          byte[] responseBody, Charset responseCharset, HttpRequest request) {
        super("Unknown status code [" + rawStatus + "]" + " " + reason, rawStatus, reason, responseHeaders,
                responseBody, responseCharset, request);
    }

}
