package com.ijiagoushi.chillies.core.id;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UuidGeneratorTest {

    @Test
    public void get() {
        UuidGenerator uuidGenerator = new UuidGenerator();
        String id = uuidGenerator.get();
        System.out.println("UuidGenerator = " + id);
        assertEquals(id.length(), 36);
        uuidGenerator = new UuidGenerator(true);
        id=uuidGenerator.get();
        System.out.println("UuidGenerator = " + id);
        assertEquals(id.length(), 32);
    }

}
