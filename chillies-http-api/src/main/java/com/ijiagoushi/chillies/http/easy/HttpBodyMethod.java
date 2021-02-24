package com.ijiagoushi.chillies.http.easy;

import com.ijiagoushi.chillies.http.constants.HttpMethod;

/**
 * 带有Body体内容的请求方式
 *
 * @author miles.tang at 2021-02-07
 * @since 1.0
 */
public enum HttpBodyMethod {

    DELETE,
    POST,
    PATCH,
    PUT,

    ;

    public HttpMethod toHttpMethod() {
        switch (this) {
            case PUT:
                return HttpMethod.PUT;
            case POST:
                return HttpMethod.POST;
            case PATCH:
                return HttpMethod.PATCH;
            case DELETE:
                return HttpMethod.DELETE;
            default:
                throw new IllegalArgumentException();
        }
    }

}
