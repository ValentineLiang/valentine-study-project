package com.valentine.interruptdemo;

import java.util.concurrent.TimeUnit;

public class InterruptDemo {

    private static int i;

    public static void main(String[] args) throws InterruptedException {
        Thread thread=new Thread(()->{
            // 当设置了interrupt标识为true,这时候就不运行了
            while(!Thread.currentThread().isInterrupted()){
                i++;
            }
            System.out.println(i);
        },"interruptDemo");
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        //设置interrupt标识为true（提供一个钩子）
        thread.interrupt();
        System.out.println(thread.isInterrupted());
    }
}
