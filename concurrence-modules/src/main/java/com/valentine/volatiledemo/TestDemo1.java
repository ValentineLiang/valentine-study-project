package com.valentine.volatiledemo;



public class TestDemo1 {

    private static volatile int a;



    public static void main(String[] args) {
        for (int i = 0; i < 10 ; i++) {
            new Thread(()->{
                a++;
            }).start();
        }
        System.out.println(a);

    }
}