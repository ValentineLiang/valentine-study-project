package com.valentine.samples;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableDemo implements Callable<String> {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CallableDemo callableDemo = new CallableDemo();

        Future<String> future = executorService.submit(callableDemo);

        System.out.println(future.get());
        executorService.shutdown();
    }


    @Override
    public String call() throws Exception {

        return "String" + 1;
    }
}
