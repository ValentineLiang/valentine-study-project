package com.valentine.synchronizeddemo;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class EarlyNotify {
    private List list;

    public EarlyNotify() {
        list = Collections.synchronizedList(new LinkedList());
    }

    public String removeItem() throws InterruptedException {

        synchronized (list) {
            while (list.isEmpty()) {  //问题在这
                list.wait();
            }

            //删除元素
            String item = (String) list.remove(0);
            return item;
        }
    }

    public void addItem(String item) {
        synchronized (list) {
            //添加元素
            list.add(item);
            //添加后，通知所有线程
            list.notifyAll();
        }
    }

    private static void print(String msg) {
        String name = Thread.currentThread().getName();
        System.out.println(name + ": " + msg);
    }

    public static void main(String[] args) {
        final EarlyNotify en = new EarlyNotify();

        Runnable runA = new Runnable() {
            public void run() {
                try {
                    String item = en.removeItem();
                } catch (InterruptedException ix) {
                    print("interrupted!");
                } catch (Exception x) {
                    print("threw an Exception!!!\n" + x);
                }
            }
        };

        Runnable runB = new Runnable() {
            public void run() {
                en.addItem("Hello!");
            }
        };

        try {
            //启动第一个删除元素的线程
            Thread threadA1 = new Thread(runA, "threadA1");
            threadA1.start();

            Thread.sleep(500);

            //启动第二个删除元素的线程
            Thread threadA2 = new Thread(runA, "threadA2");
            threadA2.start();

            Thread.sleep(500);
            //启动增加元素的线程
            Thread threadB = new Thread(runB, "threadB");
            threadB.start();

            Thread.sleep(1000); // wait 10 seconds

            threadA1.interrupt();
            threadA2.interrupt();
        } catch (InterruptedException x) {
        }
    }
}