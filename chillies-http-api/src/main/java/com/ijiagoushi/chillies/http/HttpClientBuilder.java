package com.ijiagoushi.chillies.http;

import com.ijiagoushi.chillies.core.lang.CloneSupport;
import org.jetbrains.annotations.Nullable;

/**
 * HttpClient构造器
 *
 * @author miles.tang
 */
public abstract class HttpClientBuilder extends CloneSupport<HttpClientBuilder> {

    public abstract HttpClient build(@Nullable HttpOptions httpOptions);

    static class Default extends HttpClientBuilder {

        @Override
        public HttpClient build(HttpOptions httpOptions) {
            return new HttpClient.DefaultHttpClient();
        }

    }

}
