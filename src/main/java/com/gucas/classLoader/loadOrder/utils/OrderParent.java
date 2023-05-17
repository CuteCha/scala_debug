package com.gucas.classLoader.loadOrder.utils;

/**
 * Created by cxq on 2019-10-31 09:45
 */
public class OrderParent {
    static int i=3;
    final static int k=5;

    static {
        System.out.println(i);
        i = 1;
        System.out.println("OrderParent static code chunk ......");
        System.out.println(i);
        i=7;
        System.out.println(i);
        System.out.println(k);
    }

    {
        System.out.println("OrderParent non static code chunk ......");
    }

    public OrderParent() {
        System.out.println("OrderParent contractor ......");
    }
}
