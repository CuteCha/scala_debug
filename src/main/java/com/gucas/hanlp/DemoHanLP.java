package com.gucas.hanlp;

import com.hankcs.hanlp.HanLP;

public class DemoHanLP {
    public static void main(String[] args) {
        System.out.println(HanLP.segment("你好，欢迎使用HanLP汉语处理包！"));
        System.out.println("done");
    }
}

