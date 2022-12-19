package com.ceshiren.util;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;

public class AppiumServer {
    //命令行启动AppiumServer但是目前没装命令行的appium所以暂时无法用命令行启动
    private final static Logger log = LoggerFactory.getLogger(AppiumServer.class);
    private static AppiumDriverLocalService service = null;

    // 启动Appium service并返回
    public static AppiumDriverLocalService getService(){
        if(service == null || !service.isRunning()){
            initAppiumService();
        }
        if(service == null || !service.isRunning()){
            log.info("An appium server node is not started!");
        }
        return service;
    }

    // 启用Appium service
    public static void initAppiumService(){
        service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .withIPAddress("127.0.0.1")
                .usingAnyFreePort()
                .withArgument(()-> "--base-path", "/wd/hub")
                .withArgument(GeneralServerFlag.LOG_LEVEL, "info")
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withTimeout(Duration.ofSeconds(20)));
        service.start();
        log.info("Appium server start...");
    }

    // 关闭Appium service
    public static void closeAppiumService(){
        if(service != null && service.isRunning()){
            service.stop();
            log.info("Appium server stop...");
        }
    }

}
