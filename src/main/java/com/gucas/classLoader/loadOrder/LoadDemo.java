package com.gucas.classLoader.loadOrder;

import com.gucas.classLoader.loadOrder.utils.LoadParent;
import com.gucas.classLoader.loadOrder.utils.LoadSon;
import com.gucas.classLoader.loadOrder.utils.OrderParent;
import com.gucas.classLoader.loadOrder.utils.OrderSon;
import org.junit.Test;

/**
 * Created by cxq on 2019-10-30 21:19
 */
public class LoadDemo {
    static {
        System.out.println("LoadDemo loaded ......");
    }

    @Test
    public void LoadStart() throws ClassNotFoundException {
        /*类加载原则： 懒加载(用的时候再加载)
         总的顺序是：先父类后子类，先静态后动态
                  属性和代码块的初始化遵循正常的出场顺序无论是静态还是动态，
                  但是他们总是先于构造器执行。*/
        //1 JVM 标记启动类： 类与文件名相同

        System.out.println("-------");

        //2 第一次new加载类(第二次new不会)
        LoadParent loadParent = new LoadParent();
        LoadParent loadParent2 = new LoadParent();

        System.out.println("-------");

        //3 继承关系：子类被加载，父类会先被加载
        LoadSon loadSon = new LoadSon();

        System.out.println("-------");
        //4 调用类变量或类方法(静态属性或静态方法)
        System.out.println(LoadParent.age);
        LoadParent.say();

        //remark 调用类的常量不会加载类
        System.out.println(LoadParent.NUM);

        //5 反射的时候也会加载类
        Class.forName("com.gucas.classLoader.loadOrder.utils.LoadParent");
    }

    @Test
    public void referenceCheck() {
        String s1 = "abc";
        String s2 = new String("abc");
        String s3 = new String("abc");
        String s4 = new String("abc").intern(); //常量池里获取字符串

        System.out.println(s1.equals(s2));
        System.out.println(s1 == s2);
        System.out.println(s3.equals(s4));
        System.out.println(s2.equals(s4));
        System.out.println(s1.equals(s4));


    }

    @Test
    public void LoadOrder() {
        /** 类初始化：
         * 无继承
         * 1静态代码块 > 2非静态代码块 > 3构造方法
         *
         * 有继承
         * 静态：1父类静态代码块 > 2子类静态代码块
         * 父类：3父类非静态代码块 > 4父类构造方法
         * 子类：5子类非静态代码块 > 6子类构造方法
         *
         * 内部类与加载顺序：
         *   内部类中不能含有静态变量
         *     [静态变量是要占用内存的，
         *      在编译时只要是定义为静态变量了，
         *      系统就会自动分配内存给他，
         *      而内部类是在宿主类编译完编译的]
         *   运行宿主类->静态变量内存分配->内部类
         * */

        OrderParent orderParent = new OrderParent();
        OrderSon orderSon = new OrderSon();
    }

    public static void main(String[] args) {
        System.out.println(Math.E);

        Integer i1 = 127;
        Integer i2 = 127;
        System.out.println(i1 == i2);//true

        Integer i3 = 128;
        Integer i4 = 128;
        System.out.println(i3 == i4);//false
        System.out.println(i3.equals(i4));
        System.out.println(String.format("hashCode: \ni3-->%s\ni4-->%s", i3.hashCode(), i4.hashCode()));
    }
}
