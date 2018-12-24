package com.valentine.volatiledemo;

public class VolatileDemo {

    private static int x = 0, y = 0;
    private static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            a = 1;
            x = b;
        });

        Thread t2 = new Thread(() -> {
            b = 1;
            y = a;
        });
        t1.start();
        t2.start();
        // t1.join() 如果t1没有执行完，就会在这里等待，等待执行完为止，基于wait-notify的
        t1.join();
        t2.join();
        System.out.println("x=" + x + "y=" + y);
    }
}
