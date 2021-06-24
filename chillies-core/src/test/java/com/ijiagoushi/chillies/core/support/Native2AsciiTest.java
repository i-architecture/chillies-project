package com.ijiagoushi.chillies.core.support;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author miles.tang at 2021-04-02
 * @since 1.0
 */
public class Native2AsciiTest {

    @Test
    public void ascii2Native() {
        String asciiStr = "\\u8fd9\\u662f\\u4e00\\u4e2a\\u4f8b\\u5b50,this is a example.";
        String nativeStr = "这是一个例子,this is a example.";
        assertEquals(nativeStr, new Native2Ascii(true).exec(asciiStr));
    }

    @Test
    public void native2Ascii() {
        String asciiStr = "\\u8fd9\\u662f\\u4e00\\u4e2a\\u4f8b\\u5b50,this is a example.";
        String nativeStr = "这是一个例子,this is a example.";
        assertEquals(asciiStr, new Native2Ascii().exec(nativeStr));
    }
}