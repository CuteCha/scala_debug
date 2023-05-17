package com.gucas.visibleControl.protectedBasic;

/**
 * Created by cxq on 2019-10-28 13:37
 */
public class Woman extends Person {

    public static void main(String[] args) {
        Woman w = new Woman();
        w.speak(w);
    }

    @Override
    public String toString() {
        return "woman";
    }
}
