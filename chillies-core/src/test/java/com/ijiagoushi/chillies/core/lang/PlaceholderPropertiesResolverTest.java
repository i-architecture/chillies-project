package com.ijiagoushi.chillies.core.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlaceholderPropertiesResolverTest {

    @Test
    public void resolve() {
        PlaceholderPropertiesResolver resolver = new PlaceholderPropertiesResolver("classpath:app-conf.properties", "classpath:conf.properties");
        assertEquals(resolver.getProperty("appUrl"), "http://localhost:8040/dev/app");
        assertEquals(resolver.getProperty("loginUrl"), "http://localhost:8040/dev/app/admin/login");
        assertEquals(resolver.getProperty("profile"), "dev");
        assertEquals(resolver.getProperty("author"), "Tony");
        assertEquals(resolver.getProperty("description"), "Tony Description");
    }

}
