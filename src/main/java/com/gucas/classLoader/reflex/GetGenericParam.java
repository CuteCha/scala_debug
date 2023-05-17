package com.gucas.classLoader.reflex;

import com.gucas.classLoader.utils.User;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by cxq on 2019-10-29 13:54
 */
public class GetGenericParam {
    public void test01(Map<String, User> map, List<User> list) {
        System.out.println("test01");

    }

    public Map<String, User> test02() {
        System.out.println("test01");
        return null;

    }

    public static void main(String[] args) throws NoSuchMethodException, InterruptedException {
        Method method = GetGenericParam.class.getDeclaredMethod("test01", Map.class, List.class);
        Type[] genericParameterTypes = method.getGenericParameterTypes();
        for (Type genericParameterType : genericParameterTypes) {
            System.err.println(genericParameterType);
            if (genericParameterType instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) genericParameterType).getActualTypeArguments();
                for (Type actualTypeArgument : actualTypeArguments) {
                    System.out.println(actualTypeArgument);
                }
            }
        }

        Thread.sleep(1000);
        System.out.println("=================================");
        //Method method2=GetGenericParam.class.getDeclaredMethod("test02",null);
        Method method2 = GetGenericParam.class.getDeclaredMethod("test02");
        Type genericReturnType = method2.getGenericReturnType();
        System.out.println(genericReturnType);
        if (genericReturnType instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
            for (Type actualTypeArgument : actualTypeArguments) {
                System.out.println(actualTypeArgument);
            }
        }
    }
}
