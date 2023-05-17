package com.gucas.classLoader.reflex;

import com.google.gson.Gson;
import com.gucas.classLoader.utils.User;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by cxq on 2019-10-29 10:28
 */
public class CreateDynamicObj {
    public static final Gson GSON = new Gson();

    public static void main(String[] args) throws Exception {
        //获得class对象
        Class userClass = Class.forName("com.gucas.classLoader.utils.User");
        User user = (User) userClass.newInstance(); //调用Class对象的newInstance方法要求:类必须有无参构造器
        System.out.println(user);

        System.err.println(StringUtils.repeat("-", 72));

        //通过构造器创建对象---类可以无参构造器
        Constructor constructor = userClass.getDeclaredConstructor(String.class, int.class);
        User james = (User) constructor.newInstance("James", 34);
        System.out.println(GSON.toJson(james));

        //通过反射调用普通方法
        Method setName = userClass.getDeclaredMethod("setName", String.class);
        setName.invoke(user, "wade");
        System.out.println(GSON.toJson(user));
        Field age = userClass.getDeclaredField("age"); //private
        age.setAccessible(true); //不能直接操作private属性，关闭安全检测才能操作
        age.set(user, 37);
        Field city = userClass.getDeclaredField("city"); //pubic
        city.set(user, "us");
        System.out.println(GSON.toJson(user));
        Method test = userClass.getDeclaredMethod("test");
        test.setAccessible(true);
        test.invoke(user);
    }
}
