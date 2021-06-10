package com.gucas.visibleControl.protectedBasic;

/**
 * Created by cxq on 2019-10-28 13:35
 */
public class Man {
    public static void main(String[] args) {
        Person p = new Person();
        Man man = new Man();
        p.height = 10;
        p.speak(man);
    }

    @Override
    public String toString() {
        return "man";
    }
}
