package com.ceshiren.page;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage extends AppBasePage{
    //微信首页

    public MainPage() {
    }

    //跳转到通讯录页面
    public ConcatPage toConcatPage(){
        click(AppiumBy.xpath("//*[@text=\"通讯录\"]"));
        //添加显示等待并且判断是否跳转到通讯录页面
        waitUtil().until(androidDriver->androidDriver.getPageSource().contains("添加客户"));
        logger.info("跳转通讯录页面成功");
        return new ConcatPage(androidDriver);
    }
}
