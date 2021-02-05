package com.ijiagoushi.chillies.core.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PreconditionsTest {

    @Test
    public void testCheckArgument() {
    }

    @Test
    public void testTestCheckArgument() {
    }

    @Test
    public void testRequireNonNull() {
    }

    @Test
    public void testTestRequireNonNull() {
    }

    @Test
    public void testTestRequireNonNull1() {
    }

    @Test
    public void testRequireNotEmpty() {
        String str = "";
        assertThrows(IllegalArgumentException.class, () -> Preconditions.requireNotEmpty(str));
    }

    @Test
    public void testRequireNotEmpty2() {
        String str = "124";
        assertEquals(Preconditions.requireNotEmpty(str), str);
    }

}