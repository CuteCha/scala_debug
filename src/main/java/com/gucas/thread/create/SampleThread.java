package com.gucas.thread.create;

/**
 * Created by cxq on 2019-10-28 15:34
 */
public class SampleThread extends Thread {
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("name: " + this.getName() + "\ntime: " + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Thread t1 = new SampleThread();
        Thread t2 = new SampleThread();
        t1.setName("A");
        t2.setName("B");

        t1.start();
        t2.start();

    }
}
