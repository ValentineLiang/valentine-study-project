package com.valentine.samples;

/**
 * 原子性问题
 */
public class AtomicDemo {
    private static int count=0;

    public static void inc(){
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count++;
    }

    public static void main(String[] args) throws InterruptedException {
        // 一般来说输出结果是1000的，但是这里是小于等于1000的，这就是原子性问题，没有办法在多线程的情况下实现原子性
        for(int i=0;i<1000;i++){
            new Thread(AtomicDemo::inc).start();
        }
        Thread.sleep(4000);
        System.out.println("运行结果："+count);
    }

}
