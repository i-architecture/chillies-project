package com.ijiagoushi.chillies.http;

import com.ijiagoushi.chillies.http.convenient.HttpClients;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author miles.tang at 2021-02-05
 * @since 1.0
 */
public class HttpUtilTest {

    @Test
    public void get() {
//        String html = HttpUtil.get("https://api.apiopen.top/getJoke?page=1&count=2&type=video");
//        System.out.println("html = " + html);

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("doctype", "json");
        queryParams.put("type", "AUTO");
        queryParams.put("i", "计算");
        String html = HttpClients.get("http://fanyi.youdao.com/translate").queryParam(queryParams).string();
        System.out.println("html = " + html);
    }


}
