package com.gucas.thread.create;

/**
 * Created by cxq on 2019-10-28 17:11
 */
public class SampleRunnable implements Runnable {
    private String name;

    public SampleRunnable(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("name: " + this.name + "\ttime: " + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new SampleRunnable("A"));
        Thread t2 = new Thread(new SampleRunnable("B"));

        t1.start();
        t2.start();
    }
}
