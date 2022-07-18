package com.gucas.autoDIff.math.test;

import com.gucas.autoDIff.math.DoubleReal;
import com.gucas.autoDIff.math.DoubleRealFactory;
import com.gucas.autoDIff.math.autodiff.Constant;
import com.gucas.autoDIff.math.autodiff.DifferentialFunction;
import com.gucas.autoDIff.math.autodiff.DifferentialRealFunctionFactory;
import com.gucas.autoDIff.math.autodiff.Variable;
import org.junit.Test;

import java.lang.Math;

public class ADTest {
    private final DoubleRealFactory RNFactory = DoubleRealFactory.instance();
    private final DifferentialRealFunctionFactory<DoubleReal> DFFactory = new DifferentialRealFunctionFactory<DoubleReal>(RNFactory);

    private void check(double i_expected, DifferentialFunction<DoubleReal> i_f) {
        String func_str = i_f.toString();
        double func_value = i_f.getValue().doubleValue();

        System.out.println(func_str + " = " + func_value + " is expected as " + i_expected);
    }

    @Test
    public void test01() {
        double vx = 3.0;
        double vy = 5.0;
        double vq = 8.0;

        Variable<DoubleReal> x = DFFactory.var("x", new DoubleReal(vx));
        Variable<DoubleReal> y = DFFactory.var("y", new DoubleReal(vy));
        Constant<DoubleReal> q = DFFactory.val(new DoubleReal(vq));

        //h = q*x*( cos(x*y) + y )
        DifferentialFunction<DoubleReal> h = q.mul(x).mul(DFFactory.cos(x.mul(y)).plus(y));
        //ph/px = q*( cos(x*y) + y ) + q*x*( -sin(x*y)*y )
        DifferentialFunction<DoubleReal> dhpx = h.diff(x);

        //ph/py = q*x*( -sin(x*y)*x + 1.0)
        DifferentialFunction<DoubleReal> dhpy = h.diff(y);

        //p2h/px2 = q*( -sin(x*y)*y + y ) + q*( -sin(x*y)*y ) + q*x*( -cos(x*y)*y*y )
        DifferentialFunction<DoubleReal> d2hpxpx = dhpx.diff(x);

        //p2h/pypx = q*( -sin(x*y)*x + 1.0 ) + q*x*( -sin(x*y) - cos(x*y)*y*y )
        DifferentialFunction<DoubleReal> d2hpypx = dhpx.diff(y);


        double cxy = Math.cos(vx * vy);
        double sxy = Math.sin(vx * vy);

        check(vq * vx * (cxy + vy), h);
        check(vq * (cxy + vy) + vq * vx * (-sxy * vy), dhpx);
        check(vq * vx * (-sxy * vx + 1.0), dhpy);
        check(vq * (-sxy * vy) + vq * (-sxy * vy) + vq * vx * (-cxy * vy * vy), d2hpxpx);
        check(vq * (-sxy * vx + 1.0) + vq * vx * (-sxy - cxy * vx * vy), d2hpypx);


        System.out.println("====================================================");
        vx = 4.0;
        vy = 7.0;
        x.set(new DoubleReal(vx));
        y.set(new DoubleReal(vy));

        cxy = Math.cos(vx * vy);
        sxy = Math.sin(vx * vy);

        check(vq * vx * (cxy + vy), h);
        check(vq * (cxy + vy) + vq * vx * (-sxy * vy), dhpx);
        check(vq * vx * (-sxy * vx + 1.0), dhpy);
        check(vq * (-sxy * vy) + vq * (-sxy * vy) + vq * vx * (-cxy * vy * vy), d2hpxpx);
        check(vq * (-sxy * vx + 1.0) + vq * vx * (-sxy - cxy * vx * vy), d2hpypx);

    }

    public static void main(String[] args) {
        new ADTest().test01();

    }
}
