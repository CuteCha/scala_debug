package com.gucas.classLoader.loadOrder.utils;

/**
 * Created by cxq on 2019-10-30 21:20
 */
public class LoadParent {
    public static final int NUM=10;
    public static int age=18;

    static {
        System.out.println("LoadParent loaded ......");
    }

    public LoadParent(){
        System.out.println("LoadParent contractor used ......");
    }

    public static void say(){
        System.out.println("LoadParent say");
    }
}
