package com.valentine.volatiledemo;

public class ThreadWaitNotifyDemo {

    public static void main(String[] args) {
        Object lock = new Object();
        ThreadWait threadWait = new ThreadWait(lock);
        threadWait.start();
        ThreadNotify threadNotify = new ThreadNotify(lock);
        threadNotify.start();

    }
}
