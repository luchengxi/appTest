package com.ceshiren.page;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;


import java.io.IOException;

public class server {
    //命令行启动AppiumServer但是目前没装命令行的appium所以暂时无法用命令行启动
    public static void main(String[] args) {
        startServer();
    }
    public static void startServer(){
        CommandLine commandLine = new CommandLine("appium");
        commandLine.addArgument("-g");
        commandLine.addArgument("appium.log");
        commandLine.addArgument("--port");
        commandLine.addArgument("127.0.0.1");
        commandLine.addArgument("4723");

        DefaultExecuteResultHandler handler = new DefaultExecuteResultHandler();
        DefaultExecutor executor = new DefaultExecutor();
        executor.setExitValue(1);
        try {
            executor.execute(commandLine, handler);
            System.out.println("服务启动了");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
