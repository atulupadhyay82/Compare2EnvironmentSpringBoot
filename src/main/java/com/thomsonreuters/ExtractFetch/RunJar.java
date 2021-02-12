package com.thomsonreuters.ExtractFetch;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RunJar {

    public static void executeStagingJar() throws IOException {

        // creating list of commands
        List<String> commands = new ArrayList<>();
        commands.add("nohup");
        commands.add("java"); // command
        commands.add("-Xmx22g");
        commands.add("-jar"); // command
        commands.add("staging1075OnDev.jar"); //command in Mac OS
        commands.add(">log.log");
        commands.add("2>&1");
        commands.add("&");

        String currentFilePath= System.getProperty("user.dir")+ "/src/main/resources/stagingJarFile/OldJar";

        // creating the process
        ProcessBuilder pb1 = new ProcessBuilder(commands);
        pb1.directory(new File(currentFilePath));
        Process process1 = pb1.start();

        // for reading the ouput from stream
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(process1.getInputStream()));
        String s = null;
        while ((s = stdInput.readLine()) != null)
        {
            System.out.println(s);
        }


    }
}
