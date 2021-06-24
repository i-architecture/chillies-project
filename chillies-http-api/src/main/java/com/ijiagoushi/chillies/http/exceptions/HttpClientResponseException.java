package com.ijiagoushi.chillies.http.exceptions;

import com.ijiagoushi.chillies.core.lang.CharsetUtil;
import com.ijiagoushi.chillies.http.HttpHeaders;
import com.ijiagoushi.chillies.http.HttpRequest;

import java.nio.charset.Charset;

/**
 * Common base class for exceptions that contain actual HTTP response data.
 *
 * @author miles.tang at 2021-03-15
 * @since 1.0
 */
public class HttpClientResponseException extends HttpClientException {

    private static final long serialVersionUID = 1990L;

    private final int rawStatus;

    protected final String reason;

    private final byte[] responseBody;

    private final HttpHeaders responseHeaders;

    private final Charset responseCharset;

    private final HttpRequest request;

    public HttpClientResponseException(String message, int rawStatus, String reason, HttpHeaders responseHeaders,
                                       byte[] responseBody, Charset responseCharset, HttpRequest request) {
        super(message);
        this.rawStatus = rawStatus;
        this.reason = reason;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
        this.responseCharset = CharsetUtil.getCharset(responseCharset, CharsetUtil.UTF_8);
        this.request = request;
    }

    public int getRawStatus() {
        return rawStatus;
    }

    public String getReason() {
        return reason;
    }

    public byte[] getResponseBodyAsByteArray() {
        return responseBody;
    }

    public String getResponseBodyAsString() {
        return new String(this.responseBody, this.responseCharset);
    }

    public HttpHeaders getResponseHeaders() {
        return responseHeaders;
    }

    public HttpRequest getRequest() {
        return request;
    }

}
