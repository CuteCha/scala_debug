package com.gucas.visibleControl.protectedBasic;

/**
 * Created by cxq on 2019-10-28 13:38
 */
public class Wife extends Woman {
//    Wife wife = new Wife();
//    wife.speak(wife);//出错
//    Woman woman = new Woman();
//    woman.speak(woman);//出错
//    Person p = new Person();
//    p.height =20; //无法访问
//    p.speak(Wife); //无法访问
    public static void main(String[] args) {
        Wife wife = new Wife();
        wife.speak(wife);
        Woman woman = new Woman();
        woman.speak(woman);
        Person p = new Person();
        p.speak(wife);

    }

    @Override
    public String toString(){
        return "Wife";
    }
}
