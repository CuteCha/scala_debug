package com.gucas.classLoader.loadOrder.utils;

/**
 * Created by cxq on 2019-10-31 09:45
 */
public class OrderSon extends OrderParent{
    static {
        System.out.println("OrderSon static code chunk ......");
    }

    {
        System.out.println("OrderSon non static code chunk ......");
    }

    public OrderSon(){
        System.out.println("OrderSon contractor ......");
    }

}
