package com.valentine.executorsdemo;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 继承ThreadPoolExecutor扩展自己的线程
 *
 * @author Valentine
 * @since 2018/12/24 22:35
 */
public class MyExecutors extends ThreadPoolExecutor {



    // beforeExecutor、afterExecutor、shutdown 可以每次执行完上报数据或者执行前上报数据，例如存到mongo里面

    private ConcurrentMap<String, Date> startTime;

    public MyExecutors(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.startTime=new ConcurrentHashMap<>();
    }

    public MyExecutors(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public MyExecutors(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public MyExecutors(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    /**
     * 思路，定义一个API每次执行之前都调用这个API去上报数据，这就是简单的监控
     */
    @Override
    public void shutdown() {
        // 在shutdown之前加个log
        System.out.println("已经执行的任务数量：" + this.getCompletedTaskCount() + "\n");
        System.out.println("当前的活动线程数：" + this.getActiveCount() + "\n");
        System.out.println("当前排队的线程数：" + this.getQueue().size() + "\n");
        super.shutdown();
    }



    /**
     * 执行之前调用
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        // 当前任务的开始时间
        startTime.put(String.valueOf(r.hashCode()), new Date());

        super.beforeExecute(t, r);


    }

    /**
     * 执行完毕
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        // 只需要执行一次
        Date startDate = startTime.remove(String.valueOf(r.hashCode()));
        Date finishDate = new Date();
        // 计算任务执行时间
        long dif = finishDate.getTime() - startDate.getTime();
        System.out.println("任务耗时：" + dif);
        System.out.println("最大允许的线程数" + this.getMaximumPoolSize());
        System.out.println("线程的空闲时间" + this.getKeepAliveTime(TimeUnit.MICROSECONDS));
        System.out.println("任务总数" + this.getTaskCount());
        super.afterExecute(r, t);
    }

    public static ExecutorService newMyExecutors() {
        return new MyExecutors(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
    }
}
