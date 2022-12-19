package com.ceshiren;

import com.ceshiren.entity.user;
import com.ceshiren.page.AddMemberPage;
import com.ceshiren.page.MainPage;
import com.ceshiren.page.ProfilePage;
import com.ceshiren.util.FakerUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AddMerBerTest {
    AddMemberPage addMemberPage;
    ProfilePage profilePage;
    @BeforeEach
    public void beforeEach(){
        MainPage mainPage = new MainPage();
        addMemberPage  = mainPage.toConcatPage()//跳转到通讯录页面
                //跳转到添加成员页面
                .toAddMerBerPage();
    }
    @Test
    public void testAddMember(){
        String userName = FakerUtil.get_name();
        String userNumb = FakerUtil.get_zh_phone();
        //编辑添加联系人
        addMemberPage.editMerBer(userName,userNumb);
        profilePage = addMemberPage.backToConcatPage() //返回通讯录页面
                .toSearchPage()//跳转到搜索页面
                .toSearchResultPage(userName)//跳转到搜索结果页面
                .toProfilePage();//跳转到个人信息页面
        user user = profilePage.getMerBerMessage();//获取个人信息
        assertThat(user.getName(),is(userName));

    }
    @AfterEach
    public void afterEach(){
        profilePage.backToSearchResultPage()
                .backToConcatPage();//返回搜索结果页面,返回通讯录页面
    }
}
