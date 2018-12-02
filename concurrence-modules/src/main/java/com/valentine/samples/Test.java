package com.valentine.samples;

import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                /*try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    //抛出该异常，会将复位标识设置为false
                    e.printStackTrace();
                }*/
            }
        });
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        //设置复位标识为 true
        //thread.interrupt();
        System.out.println();
        TimeUnit.SECONDS.sleep(1);
        // false 抛出异常为 false
        System.out.println(thread.isInterrupted());
    }
}
