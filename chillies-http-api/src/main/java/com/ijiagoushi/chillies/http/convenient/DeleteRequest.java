package com.ijiagoushi.chillies.http.convenient;

import com.ijiagoushi.chillies.http.HttpRequestBody;
import com.ijiagoushi.chillies.http.constants.HttpMethod;

/**
 * Deletre Request
 *
 * @author miles.tang at 2021-04-10
 * @since 1.0
 */
public class DeleteRequest extends AbstractBaseRequest<DeleteRequest> {

    public DeleteRequest(String url) {
        this.url = url;
        this.method = HttpMethod.DELETE;
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
