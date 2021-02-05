package com.ijiagoushi.chillies.core.mutable;

import com.ijiagoushi.chillies.core.thread.ThreadUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author miles.tang at 2021-01-21
 * @since 1.0
 */
class MutableIntTest {

    @Test
    void get() throws Exception {
        final MutableInt mutableInt = new MutableInt(1);
        Thread thread = new Thread(() -> {
            mutableInt.set(2);
            ThreadUtil.sleepSeconds(1);
        });
        thread.start();
        // wait thread executed
        thread.join();
        assertEquals(2, mutableInt.get());
    }
}