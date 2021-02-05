package com.ijiagoushi.chillies.core.id;

import org.junit.jupiter.api.Test;

public class DateIdGeneratorTest {

    @Test
    public void get() {
        DateIdGenerator idGenerator = new DateIdGenerator();
        System.out.println("DateIdGenerator = " + idGenerator.get());
    }

}
