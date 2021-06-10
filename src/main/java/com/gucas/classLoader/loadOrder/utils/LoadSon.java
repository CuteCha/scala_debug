package com.gucas.classLoader.loadOrder.utils;

/**
 * Created by cxq on 2019-10-30 21:21
 */
public class LoadSon extends LoadParent {
    static {
        System.out.println("LoadSon loaded ......");
    }
}
