package com.gucas.classLoader.utils;

/**
 * Created by cxq on 2019-10-28 22:03
 */
public class User {
    private String name;
    private int age;
    public String city;

    public User() {

    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private void test() {
        System.out.println("test");
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
