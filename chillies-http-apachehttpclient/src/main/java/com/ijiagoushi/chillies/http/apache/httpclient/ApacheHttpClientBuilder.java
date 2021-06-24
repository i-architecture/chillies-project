package com.ijiagoushi.chillies.http.apache.httpclient;

import com.ijiagoushi.chillies.http.HttpClient;
import com.ijiagoushi.chillies.http.HttpClientBuilder;
import com.ijiagoushi.chillies.http.HttpOptions;
import org.jetbrains.annotations.Nullable;

public class ApacheHttpClientBuilder extends HttpClientBuilder {

    @Override
    public HttpClient build(@Nullable HttpOptions options) {
        return new HttpComponentsHttpClient(options);
    }

}
