package com.valentine.multithread.synchronizeddemo;

public class SynchronizedDemoTest {
    private static Object object = new Object();

    public static void main(String[] args) throws Exception {
        synchronized (object) {

        }
    }

    public static synchronized void method() {
    }
}
