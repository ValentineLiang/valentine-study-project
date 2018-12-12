package com.valentine.volatiledemo;

public class ThreadDemo {
    // 加了volatile的时候会有一个加锁的操作： 0x03060356: lock addl $0x0,(%esp)
    private volatile static ThreadDemo instance = null;
    // 没有加volatile是不会有lock的
    // private static ThreadDemo instance = null;

    public static ThreadDemo getInstance() {

        if (null == instance) {
            instance = new ThreadDemo();
        }
        return instance;
    }

    public static void main(String[] args) {
        ThreadDemo.getInstance();
    }
}
