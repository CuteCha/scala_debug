package com.gucas.polymorphism;

public class PolyTest01 {
    public void introduce(PolyBaseA polyBaseA) {
        polyBaseA.say();
    }

    public static void main(String[] args) {
        PolyBaseB01 polyBaseB01 = new PolyBaseB01();
        PolyBaseB02 polyBaseB02 = new PolyBaseB02();
        new PolyTest01().introduce(polyBaseB01);
        new PolyTest01().introduce(polyBaseB02);

    }
}
