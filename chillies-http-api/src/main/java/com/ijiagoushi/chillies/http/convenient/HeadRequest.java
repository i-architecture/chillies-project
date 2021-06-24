package com.ijiagoushi.chillies.http.convenient;

import com.ijiagoushi.chillies.http.HttpRequestBody;
import com.ijiagoushi.chillies.http.constants.HttpMethod;

/**
 * Head Request
 *
 * @author miles.tang at 2021-04-10
 * @since 1.0
 */
public class HeadRequest extends AbstractBaseRequest<HeadRequest> {

    public HeadRequest(String url) {
        this.url = url;
        this.method = HttpMethod.HEAD;
    }

    /**
     * 构建{@linkplain HttpRequestBody}，根据不同的请求类型
     *
     * @return {@linkplain HttpRequestBody}
     */
    @Override
    protected HttpRequestBody generateRequestBody() {
        return null;
    }

}
