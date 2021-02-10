package com.thomsonreuters.ExtractFetch;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RunJar {

    public static void main(String arg[]) throws IOException, InterruptedException {

        // creating list of commands
        List<String> commands = new ArrayList<>();
        commands.add("java"); // command
        commands.add("-Xmx22g");
        commands.add("-jar"); // command
        commands.add("staging1075OnDev.jar"); //command in Mac OS
        commands.add("--port=9102");

        String currentFilePath= System.getProperty("user.dir")+ "\\src\\main\\resources\\stagingJarFile\\";

        // creating the process
        ProcessBuilder pb1 = new ProcessBuilder(commands);
//        commands.add(3,"staging1075N1071OnDev.jar");
//        commands.add(4,"-port=9104");
//        ProcessBuilder pb2= new ProcessBuilder(commands);
        pb1.directory(new File(currentFilePath));
//        pb2.directory(new File(currentFilePath));

        // startinf the process
        Process process1 = pb1.start();
//        Process process2 = pb2.start();



        // for reading the ouput from stream
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(process1.getInputStream()));
        String s = null;
        while ((s = stdInput.readLine()) != null)
        {
            System.out.println(s);
        }

        // for reading the ouput from stream
//       stdInput = new BufferedReader(new
//                InputStreamReader(process2.getInputStream()));
//        while ((s = stdInput.readLine()) != null)
//        {
//            System.out.println(s);
//        }
    }
}
