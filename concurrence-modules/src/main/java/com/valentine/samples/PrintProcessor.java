package com.valentine.samples;

import com.valentine.example.Request;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 线程怎么很好的应用在我们的项目里面
 *
 * @author Valentine
 * @since 2018/11/28 0:18
 */
public class PrintProcessor extends Thread implements RequestProcessor {

    LinkedBlockingQueue<Request> linkedBlockingQueue = new LinkedBlockingQueue<>();

    private RequestProcessor nextProcessor;

    public PrintProcessor(RequestProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Request request = linkedBlockingQueue.take();
                System.out.println("print data: " + request);
                nextProcessor.processorRequest(request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void processorRequest(Request request) {
        linkedBlockingQueue.add(request);
    }


}
