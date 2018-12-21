package com.valentine.countdownlatchdemo;

import java.util.concurrent.CountDownLatch;

/**
 * @author Valentine
 * @since 2018/12/19 22:42
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        // 定义一个倒计时，每个减一,0的时候才会让主线程释放
        CountDownLatch countDownLatch = new CountDownLatch(3);

        new Thread(()->{
            countDownLatch.countDown();
        },"1").start();

        new Thread(()->{
            countDownLatch.countDown();
        },"2").start();

        new Thread(()->{
            countDownLatch.countDown();
        },"3").start();

        // 阻塞主线程
        countDownLatch.await();
        System.out.println("执行完毕");
    }

}
