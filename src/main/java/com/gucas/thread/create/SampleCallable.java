package com.gucas.thread.create;

import org.apache.commons.lang.StringUtils;

import java.util.concurrent.*;

/**
 * Created by cxq on 2019-10-28 17:17
 */
public class SampleCallable implements Callable {
    private String name;

    public SampleCallable(String name) {
        this.name = name;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(1000);
        int sum = 0;
        for (int i = 1; i < 101; i++) {
            sum += i;
        }

        return String.format("sum=%d name=%s time: %d", sum, this.name, System.currentTimeMillis());
    }

    public static void createMethod() throws ExecutionException, InterruptedException {
        new Thread() {
            @Override
            public void run() {
                System.out.println("thread ......");
            }
        }.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("runnable ......");
            }
        }).start();

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Callable ......";
            }
        };
        ExecutorService pool = Executors.newFixedThreadPool(1);
        Future<String> future = pool.submit(callable);
        System.out.println(future.get());
        pool.shutdown();
    }

    public static void main(String[] args) throws Exception {
        SampleCallable callableA = new SampleCallable("A");
        FutureTask futureTaskA = new FutureTask(callableA);
        Thread threadA = new Thread(futureTaskA);

        SampleCallable callableB = new SampleCallable("B");
        FutureTask futureTaskB = new FutureTask(callableB);
        Thread threadB = new Thread(futureTaskB);

        threadA.start();
        threadB.start();
        System.out.println(futureTaskA.get());
        System.out.println(futureTaskB.get());

        System.out.println(StringUtils.repeat("-", 72));
        createMethod();

    }
}
