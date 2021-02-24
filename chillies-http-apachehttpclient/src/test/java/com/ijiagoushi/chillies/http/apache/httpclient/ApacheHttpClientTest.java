package com.ijiagoushi.chillies.http.apache.httpclient;

import com.ijiagoushi.chillies.core.http.ContentType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author miles.tang at 2021-02-24
 * @since 1.0
 */
public class ApacheHttpClientTest {

    @Test
    public void build() {
        ContentType contentType = ContentType.MULTIPART_FORM_DATA;
        org.apache.http.entity.ContentType apacheContentType = org.apache.http.entity.ContentType.parse(contentType.toString());
        assertNotNull(apacheContentType);
        System.out.println("apacheContentType = " + apacheContentType);
    }
}