package com.gucas.classLoader.reflex;

import org.apache.commons.lang.StringUtils;

/**
 * Created by cxq on 2019-10-28 21:34
 * <p>
 * Bootstap ClassLoader--->用c++编写的，是jvm自带的，负责java平台核心库，用来装载核心类库，该加载器无法直接获取
 * Extension ClassLoader--->负责jre/lib/ext目录下的jar包或-D java.ext.dirs指定目录下的jar包装入工作库
 * System ClassLoader--->负责java -classpath或-D java.class.path所指目录下的类与jar包装入工作库
 * 自定义类加载器--->
 */
public class ThreeLoaders {
    public static void main(String[] args) {
        //获取系统类加载器
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemClassLoader);

        //获取系统类加载器的父类加载器--->扩展类加载器
        ClassLoader parent = systemClassLoader.getParent();
        System.out.println(parent);

        //获取扩展类加载器的父类加载器--->根加载器(c/c++)
        ClassLoader root = parent.getParent();
        System.out.println(root);

        //获取当前类的加载器
        try {
            ClassLoader classLoader1 = Class.forName("com.gucas.classLoader.reflex.ThreeLoaders").getClassLoader();
            System.out.println(classLoader1);
            ClassLoader classLoader2 = Class.forName("java.lang.String").getClassLoader();
            System.out.println(classLoader2);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //获取系统类加载器可以加载的路径
        String property = System.getProperty("java.class.path");
        System.out.println(property);
        System.out.println(StringUtils.join(property.split(":"),"\n"));

    }

}
