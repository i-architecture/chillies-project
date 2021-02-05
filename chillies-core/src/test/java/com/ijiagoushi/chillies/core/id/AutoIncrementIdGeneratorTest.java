package com.ijiagoushi.chillies.core.id;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AutoIncrementIdGeneratorTest {

    @Test
    public void get() {
        AutoIncrementIdGenerator idGenerator = new AutoIncrementIdGenerator();
        String id = idGenerator.get(10);
        System.out.println("id = " + id);
        assertEquals(id.length(), 10);
        assertEquals(id, "0000000000");
    }

}
