package com.gucas.classLoader.check;

import org.junit.Test;

/**
 * Created by cxq on 2019-10-29 15:45
 */
public class CheckDemo {
    @Test
    public void  classLoaderTest(){
        //ClassLoader
        Object obj = new Object();
        CheckDemo checkDemo = new CheckDemo();

        System.out.println(obj.getClass().getClassLoader());
        System.err.println("\n-------------------------");
        System.out.println(checkDemo.getClass().getClassLoader());
        System.out.println(checkDemo.getClass().getClassLoader().getParent());
        System.out.println(checkDemo.getClass().getClassLoader().getParent().getParent());

    }

    @Test
    public void nativeInterfaceTest(){
        new Thread(()->{
            System.out.println("A...");
        },"A").start(); //native调用c++

    }

    public static void main(String[] args) {


    }
}
