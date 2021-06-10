package com.gucas.classLoader.reflex;

import com.gucas.classLoader.utils.User;
import org.apache.ivy.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by cxq on 2019-10-28 22:02
 */
public class GetClassInfo {
    private static void display(String key, Object[] values) {
        for (Object value : values) {
            System.out.println(String.format("%s: %s", key, value));
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Class c1 = Class.forName("com.gucas.classLoader.utils.User");

        User user = new User();
        Class c2 = user.getClass();

        //获取类的名字
        String name = c1.getName();
        System.out.println(name);
        System.out.println(c1.getSimpleName());
        System.out.println(c2.getName());

        //获取类的属性
        Field[] fields = c1.getFields(); //只能找到public属性
        Field[] declaredFields = c1.getDeclaredFields(); //能找到全部的属性

        display("fields", fields);
        System.out.println(StringUtils.repeat("-", 72));
        display("declaredFields", declaredFields);
        Method[] methods = c1.getMethods(); //获取本类及父类的方法
        Method[] declaredMethods = c1.getDeclaredMethods(); //获取本类
        display("methods", methods);
        System.out.println(StringUtils.repeat("-", 72));
        display("declaredMethods", declaredMethods);


    }
}
