package com.valentine.synchronizeddemo;

public class Demo {
    private static int count = 0;
    private static int count1 = 0;
    private static int count2 = 0;

    private static void inc() {
        synchronized (Demo.class) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
        }
    }

    private synchronized void inc1() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count1++;
    }

    private void inc2() {
        synchronized (this) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count2++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Demo demo = new Demo();
        for (int i = 0; i < 1000; i++) {

            new Thread(() -> Demo.inc()).start();

            /*new Thread(() -> {
                demo.inc1();
            }).start();

            new Thread(() -> {
                demo.inc2();
            }).start();*/

            new Thread(() -> {
                Demo demo1 = new Demo();
                demo1.inc1();
            }).start();

            new Thread(() -> {
                Demo demo2 = new Demo();
                demo2.inc2();
            }).start();
        }
        Thread.sleep(3000);
        System.out.println("运行结果：" + count);
        System.out.println("运行结果1：" + count1);
        System.out.println("运行结果2：" + count2);
    }
}