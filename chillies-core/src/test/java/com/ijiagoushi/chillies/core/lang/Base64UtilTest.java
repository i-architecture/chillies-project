package com.ijiagoushi.chillies.core.lang;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class Base64UtilTest {

    @Test
    public void testEncode() {
        assertNull(Base64Util.encode((String) null));
        String str = "";
        assertEquals(Base64Util.encode(str), str);

        str = "这就是一串字符串而已^_^";
        // https://base64.guru/converter/encode
        // http://www.bejson.com/enc/base64/
        assertEquals(Base64Util.encode(str), "6L+Z5bCx5piv5LiA5Liy5a2X56ym5Liy6ICM5beyXl9e");
    }

    @Test
    public void testEncodeCharset() {
        String str = "这就是一串字符串而已^_^";
        // https://base64.guru/converter/encode
        // http://www.bejson.com/enc/base64/
        assertEquals(Base64Util.encode(str, CharsetUtil.GBK), "1eK+zcrH0ru0rtfWt/u0rrb40tFeX14=");
    }

    @Test
    public void testEncodeInputStream() {
        ByteArrayInputStream bais = new ByteArrayInputStream("com.ijiagoushi.chillies.core.lang.Base64Util".getBytes());
        assertEquals(Base64Util.encode(bais), "Y29tLmlqaWFnb3VzaGkuY2hpbGxpZXMuY29yZS5sYW5nLkJhc2U2NFV0aWw=");
    }

    @Test
    public void testEncodeUrlSafe() {
        String str = "这就是一串字符串而已^_^";
        // https://base64.guru/converter/encode
        assertEquals(Base64Util.encode(str, true), "6L-Z5bCx5piv5LiA5Liy5a2X56ym5Liy6ICM5beyXl9e");
    }

    @Test
    public void testDecode() {
        assertNull(Base64Util.decode(null));
        assertEquals(Base64Util.decodeToStr("5pe26Ze05b6I5b+r77yM5LiU6KGM5LiU54+N5oOc77yB"), "时间很快，且行且珍惜！");
    }

}
