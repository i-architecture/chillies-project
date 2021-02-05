package com.ijiagoushi.chillies.core.lang;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author miles.tang at 2021-01-20
 * @since 1.0
 */
class ClassScannerTest {

    @Test
    void scan() {
        Set<Class<?>> classes = ClassScanner.scanPackage("com.ijiagoushi");
        assertNotNull(classes);
        assertTrue(classes.size() > 0);
        classes.forEach(System.out::println);

    }

}