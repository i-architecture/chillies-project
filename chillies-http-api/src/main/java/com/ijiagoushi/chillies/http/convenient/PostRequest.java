package com.ijiagoushi.chillies.http.convenient;

import com.ijiagoushi.chillies.http.constants.HttpMethod;

/**
 * Post Request
 *
 * @author miles.tang at 2021-04-10
 * @since 1.0
 */
public class PostRequest extends BaseBodyRequest<PostRequest> {

    public PostRequest(String url) {
        this.url = url;
        this.method = HttpMethod.POST;
    }

}
