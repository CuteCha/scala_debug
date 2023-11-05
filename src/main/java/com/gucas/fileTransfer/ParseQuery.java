package com.gucas.fileTransfer;

import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ParseQuery {
    // (a|b)&(~c)
    // (a|b)&(~(c|d))

    @Test
    public void parseBoolString() {
        String str = "(a and b) or (c and d)";
        str = str.replaceAll("or", "||");
        str = str.replaceAll("and", "&&");
        System.out.println(str);
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        engine.put("a", true);
        engine.put("b", false);
        engine.put("c", false);
        engine.put("d", true);
        try {
            Object result = null;
            result = engine.eval(str);
            System.out.println("结果类型:" + result.getClass().getName() + ",计算结果:" + result);
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("done");
        }

    }

    public static void main(String[] args) {

    }
}
