package com.gucas.classLoader.reflex;

import com.gucas.classLoader.utils.User;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by cxq on 2019-10-29 13:15
 */
public class PerformanceTesting {
    //普通方式调用
    public void natureCreate() {
        User user = new User();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            user.getName();
        }
        long endTime = System.currentTimeMillis();

        System.out.println(String.format("natureCreate: %d ms", endTime - startTime));

    }

    //反射方式调用
    public void reflexCreate1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        User user = new User();
        Class userClass = user.getClass();
        Method getName = userClass.getDeclaredMethod("getName");

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            getName.invoke(user);
        }
        long endTime = System.currentTimeMillis();

        System.out.println(String.format("reflexCreate1: %d ms", endTime - startTime));

    }

    //反射方式调用 关闭检测
    public void reflexCreate2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        User user = new User();
        Class userClass = user.getClass();
        Method getName = userClass.getDeclaredMethod("getName");
        getName.setAccessible(true);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            getName.invoke(user);
        }
        long endTime = System.currentTimeMillis();

        System.out.println(String.format("reflexCreate2: %d ms", endTime - startTime));

    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        new PerformanceTesting().natureCreate();
        new PerformanceTesting().reflexCreate1();
        new PerformanceTesting().reflexCreate2();
    }
}
