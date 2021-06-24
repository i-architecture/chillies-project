package com.ijiagoushi.chillies.core.thread;

import com.ijiagoushi.chillies.core.exceptions.GenericRuntimeException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 并发测试工具器
 *
 * @author miles.tang at 2021-05-24
 * @since 1.0
 */
public class ConcurrentTester {

    private final int threadSize;

    private CountDownLatch mainLatch = new CountDownLatch(1);

    private CountDownLatch workerLatch;

    private ExecutorService executorService;

    public ConcurrentTester(final int threadSize) {
        this.threadSize = threadSize;
        this.workerLatch = new CountDownLatch(threadSize);
        this.executorService = Executors.newFixedThreadPool(threadSize);

    }

    public void test(Runnable runnable) {
        for (int i = 0; i < threadSize; i++) {
            executorService.submit(runnable);
        }


    }

    public abstract class Worker implements Runnable {

        @Override
        public void run() {
            try {
                mainLatch.await();
            } catch (InterruptedException e) {
                throw new GenericRuntimeException(e);
            }
            try {
                doWork();
            } finally {
                workerLatch.countDown();
            }
        }

        public abstract void doWork();
    }
}
