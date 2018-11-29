package com.valentine.samples;

import java.util.concurrent.TimeUnit;

public class ThreadInterruptDemo {

    public static void main1(String[] args) throws InterruptedException {

        Thread thred=new Thread(()->{
            while(true){
                boolean in=Thread.currentThread().isInterrupted();
                if(in){
                    // 如果线程被中断过的话 in 会变成true
                    System.out.println("before:"+in);
                    //设置复位，就变成 false
                    Thread.interrupted();
                    System.out.println("after:"+Thread.currentThread().isInterrupted());
                }
            }
        });
        thred.start();
        TimeUnit.SECONDS.sleep(1);
        // 中断
        thred.interrupt();
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread=new Thread(()->{
            while(true){
                // 抛异常会复位
                try {
                    Thread.sleep(1000000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        thread.interrupt();
        System.out.println("before:"+thread.isInterrupted());
        TimeUnit.SECONDS.sleep(1);
        System.out.println("after:"+thread.isInterrupted());
    }
//   volatile boolean stop=true;

}
