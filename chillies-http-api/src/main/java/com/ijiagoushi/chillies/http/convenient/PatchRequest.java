package com.ijiagoushi.chillies.http.convenient;

import com.ijiagoushi.chillies.http.constants.HttpMethod;

/**
 * Patch Request
 *
 * @author miles.tang at 2021-04-10
 * @since 1.0
 */
public class PatchRequest extends BaseBodyRequest<PatchRequest> {

    public PatchRequest(String url) {
        this.url = url;
        this.method = HttpMethod.PATCH;
    }

}
