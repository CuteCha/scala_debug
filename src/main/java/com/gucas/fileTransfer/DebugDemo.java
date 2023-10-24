package com.gucas.fileTransfer;

import org.junit.Test;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DebugDemo {
    @Test
    public void callLinuxCMD01() {
        Process process;
        try {
            process = Runtime.getRuntime().exec(new String[]{"bash", "-c", "ls /Users/cxq/workflow/scala_debug"});
            System.out.println("exit: " + process.exitValue());
            process.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void callLinuxCMD02() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", "ls -al /Users/cxq/workflow/scala_debug");
        try {
            Process process = processBuilder.start();
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                System.out.println(output);
                System.exit(0);
            } else {
                System.out.println("exitVal: " + exitVal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
