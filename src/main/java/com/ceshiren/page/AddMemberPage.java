package com.ceshiren.page;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class AddMemberPage extends AppBasePage{
    //添加成员页面
    public AddMemberPage(AndroidDriver androidDriver){
        super(androidDriver);
    }

    //点击手动输入添加
    public AddMemberPage editMerBer(String name,String numb){
        click(AppiumBy.xpath("//*[@text=\"手动输入添加\"]"));
        //输入姓名和手机号码
        send(AppiumBy.className("android.widget.EditText"),name);
        send(AppiumBy.className("android.widget.EditText"),1,numb);
        //点击保存,这里使用Android原生定位
        androidDriver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"保存\")")).click();
        //显示等待Toast消息
        waitUtil().until(androidDriver->androidDriver.getPageSource().contains("Toast"));
        //xpath定位//*[@class='android.widget.Toast']获取Toast文本消息
        String toastMessage = androidDriver.findElement(AppiumBy.xpath("//*[@class=\"android.widget.Toast\"]")).getText();
        logger.info("Toast消息"+toastMessage);
        //点击返回
        back();
        return this;
    }
    //从添加成员页面返回到通讯录页面
    public ConcatPage backToConcatPage(){
        //点击返回
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        back();
        waitUtil().until(androidDriver->androidDriver.getPageSource().contains("添加客户"));
        logger.info("从添加成员页面返回到通讯录页面");
        return new ConcatPage(androidDriver);
    }

}
