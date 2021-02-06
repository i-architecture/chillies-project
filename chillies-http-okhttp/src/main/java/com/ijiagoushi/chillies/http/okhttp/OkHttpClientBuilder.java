package com.ijiagoushi.chillies.http.okhttp;

import com.ijiagoushi.chillies.http.HttpClient;
import com.ijiagoushi.chillies.http.HttpClientBuilder;
import com.ijiagoushi.chillies.http.HttpOptions;

public class OkHttpClientBuilder extends HttpClientBuilder {

    @Override
    public HttpClient build(HttpOptions options) {
        return new OkHttpClient(options);
    }

}
