package com.ijiagoushi.chillies.core.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ObjectUtilTest {

    @Test
    public void testTestEquals() {
        byte[] src = new byte[]{35, 40, 42, 44};
        byte[] dst = new byte[]{35, 40, 42, 44};
        assertFalse(ObjectUtil.equals(src, dst));
        assertTrue(ObjectUtil.nullSafeEquals(src, dst));
    }

}
