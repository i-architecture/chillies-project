package com.ijiagoushi.chillies.webexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ChilliesWebExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChilliesWebExampleApplication.class, args);
    }

    @RequestMapping("/check")
    public String check() {
        return "/chillies-web-example";
    }

}
