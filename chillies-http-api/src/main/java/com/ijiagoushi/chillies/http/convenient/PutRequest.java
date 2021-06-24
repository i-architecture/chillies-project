package com.ijiagoushi.chillies.http.convenient;

import com.ijiagoushi.chillies.http.constants.HttpMethod;

/**
 * Put Request
 *
 * @author miles.tang at 2021-04-10
 * @since 1.0
 */
public class PutRequest extends BaseBodyRequest<PutRequest> {

    public PutRequest(String url) {
        this.url = url;
        this.method = HttpMethod.PUT;
    }

}
