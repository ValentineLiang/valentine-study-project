package com.valentine.atomicIntegerdemo;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {

    private static AtomicInteger count = new AtomicInteger(0);

    public static synchronized void inc() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count.getAndIncrement();
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                AtomicIntegerTest.inc();
            }).start();
        }
        Thread.sleep(3000);
        System.out.println(count.get());
    }

}
