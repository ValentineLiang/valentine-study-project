package com.valentine.multithread.volatiledemo;

public class Demo {

    private final Object lock = new Object();
    private static int count = 0;
    public void demo() {
        // 对象锁，这个对象的作用域是大多，这个锁的范围就是多大
        synchronized (this) {

        }
        // 全局锁
        synchronized (Demo.class) {

        }

        synchronized (lock) {

        }
    }

    // 全局锁
    public synchronized static void incr() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count ++;
        System.out.println(count);
    }
    // 对象锁
    public synchronized void incr1() {

    }

    public static void main(String[] args) {
        Demo demo1 = new Demo();
        Demo demo2 = new Demo();
        demo1.demo();
        demo2.demo();
        for (int i=0;i<100;i++) {
            new Thread(()->{
               Demo.incr();
            }).start();
        }
    }
}
