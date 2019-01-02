package com.valentine.semaphore;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

    public static void main(String[] args) {
        // 默认非公平
        Semaphore semaphore = new Semaphore(5);

        for (int i = 0 ; i < 10; i++) {
            new DoAnything(i,semaphore).start();
        }
    }

    static class DoAnything extends Thread {
        private int num;
        private Semaphore semaphore;

        DoAnything(int num, Semaphore semaphore){
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {

            try {
                // (还有一种是guava的RateLimiter)
                // 获取一个令牌，如果拿到令牌就会运行，如果拿不到就会阻塞，可以达到一个限流的效果
                // 限流主要体现在，你的接口本来的吞吐量有限，为了保护你的接口稳定性，就会限制访问的请求量（这里只适合单机）
                semaphore.acquire();
                System.out.println("第" + num + "个线程进入");
                Thread.sleep(2000);
                // 释放令牌
                semaphore.release();
                //System.out.println("第" + num + "个线程释放令牌");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
