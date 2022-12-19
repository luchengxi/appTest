package com.ceshiren.page;


import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;


public class SearchResultPage extends AppBasePage{
    //联系人搜索结果页面
    public SearchResultPage(AndroidDriver androidDriver) {
        super(androidDriver);
    }

    //跳转到个人信息页面
    public ProfilePage toProfilePage(){
        click(AppiumBy.className("android.widget.ImageView"),1);
        logger.info("点击联系人");
        waitUtil().until(androidDriver->androidDriver.getPageSource().contains("设置备注和描述"));
        logger.info("跳转到个人信息页面");
        return new ProfilePage(androidDriver);
    }
    //跳转到通讯录页面
    public ConcatPage backToConcatPage(){
        //点击返回
        back();
        waitUtil().until(androidDriver->androidDriver.getPageSource().contains("添加客户"));
        logger.info("从联系人搜索结果页面返回到通讯录页面成功");
        return new ConcatPage(androidDriver);
    }
}
