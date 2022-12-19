package com.ceshiren.page;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ConcatPage extends AppBasePage{
    //通讯录页面
    public ConcatPage(AndroidDriver androidDriver){
        super(androidDriver);
    }

    //跳转到添加成员页面
    public AddMemberPage toAddMerBerPage(){
        click(AppiumBy.xpath("//*[@text=\"添加成员\"]"));
        return new AddMemberPage(androidDriver);
    }

    //点击搜索,跳转到搜索页面
    public SearchPage toSearchPage(){
        click(AppiumBy.className("android.widget.TextView"),1);
        waitUtil().until(androidDriver->androidDriver.findElement(AppiumBy.xpath("//*[@class=\"android.widget.EditText\" and @text=\"搜索\"]")));
        logger.info("跳转到搜索页面");
        return new SearchPage(androidDriver);
    }
}
