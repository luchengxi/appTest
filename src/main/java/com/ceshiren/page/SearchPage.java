package com.ceshiren.page;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class SearchPage extends AppBasePage{

    //搜索联系人页面
    public SearchPage(AndroidDriver androidDriver) {
        super(androidDriver);
    }

    //输入用户名,跳转到搜索结果页面
    public SearchResultPage toSearchResultPage(String name){
        send(AppiumBy.xpath("//*[@class=\"android.widget.EditText\" and @text=\"搜索\"]"),name);
        waitUtil().until(androidDriver->androidDriver.getPageSource().contains("联系人"));
        logger.info("成功跳转到搜索联系人结果页面");
        return new SearchResultPage(androidDriver);
    }

}
