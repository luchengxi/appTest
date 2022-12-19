package com.ceshiren.page;

import com.ceshiren.entity.user;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class ProfilePage extends AppBasePage{
    //个人信息页面

    public ProfilePage(AndroidDriver androidDriver) {
        super(androidDriver);
    }
    //获取联系人姓名
    public com.ceshiren.entity.user getMerBerMessage(){
       String name =finds(AppiumBy.className("android.widget.TextView")).get(3).getText();
       logger.info("获取到的联系人姓名为"+name);
        //将用户名和手机号存入实体类
        user.setName(name);
       return user;
    }
    //返回到联系人搜索结果页面
    public SearchResultPage backToSearchResultPage(){
        //点击返回
        back();
        waitUtil().until(androidDriver->androidDriver.getPageSource().contains("联系人"));
        logger.info("从个人信息页面返回到联系人搜索结果页面成功");
        return new SearchResultPage(androidDriver);
    }
}
