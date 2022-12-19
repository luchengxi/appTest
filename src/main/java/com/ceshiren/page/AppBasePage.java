package com.ceshiren.page;

import com.ceshiren.entity.user;
import com.ceshiren.util.AppiumServer;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.app.SupportsAutoGrantPermissionsOption;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class AppBasePage {
    //测试基类
    public static final Logger logger = LoggerFactory.getLogger(AppBasePage.class);

    public AndroidDriver androidDriver;
    public user user = new user();

    public AppBasePage(AndroidDriver androidDriver) {
        this.androidDriver = androidDriver;
    }

    public AppBasePage() {
        deleteDir();
        mkdir();
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        //平台名称 安卓系统就是Android 苹果手机就是iOS platformName
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        //使用的driver uiautomator2 automationName
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        //设备的系统版本 安卓手机的系统版本，非小米、华为系统版本号  adb shell getprop ro.build.version.release
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "6.0.1");
        //启动的app的包名 第三方app：adb shell pm list packages -3    mm wework
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.tencent.wework");
        //启动的app的页面  adb shell monkey -p com.tencent.wework -vvv 1
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".launch.LaunchSplashActivity");
        //设备名称
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "127.0.0.1:7555");
        //设备的UDID；adb devices -l 获取，多设备的时候要指定，若不指定默认选择列表的第一个设备
        desiredCapabilities.setCapability(MobileCapabilityType.UDID, "127.0.0.1:7555");
        //app不重置
        desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        //运行失败的时候打印page source到appium-log   printPageSourceOnFindFailure
        desiredCapabilities.setCapability(MobileCapabilityType.PRINT_PAGE_SOURCE_ON_FIND_FAILURE, true);
        //在假设客户端退出并结束会话之前，Appium 将等待来自客户端的新命令多长时间（以秒为单位） http请求等待响应最长5分钟  newCommandTimeout
        desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300000);
        //默认权限通过  autoGrantPermissions
        desiredCapabilities.setCapability(SupportsAutoGrantPermissionsOption.AUTO_GRANT_PERMISSIONS_OPTION, true);
        //1、打开app操作
        try {
            androidDriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),desiredCapabilities);
            //设置隐式等待,最长等待时间为20s
            androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    //返回 安卓手机的滑动返回
    public void back(){
        System.out.println("滑动返回");
        Dimension dimension = androidDriver.manage().window().getSize();
        //起始点 x 0 y 0.5
        Point startPoint = new Point((int) (dimension.width * 0), (int) (dimension.height * 0.5));
        //结束点 x 0.9  y 0.5
        Point endPoint = new Point((int) (dimension.width * 0.9), (int) (dimension.height * 0.5));
        swipe(startPoint, endPoint);

    }

    public void swipe(Point startPoint, Point endPoint){

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence dragNDrop = new Sequence(finger, 1)
                //手指移动到起始坐标点
                .addAction(finger.createPointerMove(Duration.ofMillis(0),
                        PointerInput.Origin.viewport(), startPoint.x, startPoint.y))
                //手指按下
                .addAction(finger.createPointerDown(0))
                //滑动到第二个坐标点 滑动时间是1秒
                .addAction(finger.createPointerMove(Duration.ofMillis(1000),
                        PointerInput.Origin.viewport(),endPoint.x, endPoint.y))
                //手指释放
                .addAction(finger.createPointerUp(0));
        androidDriver.perform(Arrays.asList(dragNDrop));

    }

    @Step("元素定位：{by}")
    public WebElement find(By by){
        return find(by,true);
    }
    @Step("元素定位：{by}")
    public WebElement find(By by,Boolean flag){
        WebElement element = androidDriver.findElement(by);
        logger.info("元素定位：{}",by);
        if(flag){
            try {
                //调用截图方法
                ElementScreenBase(element, by.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return element;
    }
    @Step("元素定位：{by}")
    public List<WebElement> finds(By by){
        return androidDriver.findElements(by);

    }
    //截图并且把获取的元素标红
    public void ElementScreenBase(WebElement element, String message) throws IOException {
        File screenshot = ((TakesScreenshot) androidDriver).getScreenshotAs(OutputType.FILE);
        Point elementLocation = element.getLocation();
        Dimension elementSize = element.getSize();
        int eleX = elementLocation.x;
        int eleY = elementLocation.y;
        int eleH = elementSize.height;
        int eleW = elementSize.width;
        logger.info("location=${} size=${} x=${} y=${} width=${} height=${}",
                elementLocation,elementSize,eleX,eleY,eleW,
                eleH);
        //读取截图文件
        BufferedImage img = ImageIO.read(screenshot);
        //创建一个 Graphics2D，可用于绘制到此 BufferedImage 中
        Graphics2D graph = img.createGraphics();
        //BasicStroke 指定线宽的实心  描边 Shape 的 Stroke 对象
        graph.setStroke(new BasicStroke(5));
        graph.setColor(Color.RED);//绘制形状的颜色
        graph.drawRect(eleX, eleY, eleW, eleH);//绘制指定矩形的轮廓
        graph.dispose();//处理此图形上下文并释放它正在使用的任何系统资源
        Path pngPath = getPngPath();
        ImageIO.write(img, "png", pngPath.toFile());
        //将截图添加到allure报告中
        Allure.addAttachment(message,"image/png",new FileInputStream(pngPath.toFile()),".png");
    }
    public Path getPngPath() {
        long l = System.currentTimeMillis();
        return Paths.get("png",l+".png");

    }

    private void deleteDir() {
        try {
            File file = Paths.get("png").toFile();
            if(file.exists() && file.isDirectory()){
                FileUtils.deleteDirectory(file);
                logger.info(" png 文件夹删除");
            }else {
                logger.info(" png 文件夹不存在");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void mkdir() {
        File file = Paths.get("png").toFile();
        if(!file.exists() || !file.isDirectory()){
            file.mkdirs();
            logger.info("创建 png 文件夹");
        }else {
            logger.info(" png 文件夹已经存在");

        }
    }

    //输入操作
    public void send(By by, String str){
        WebElement webElement = find(by);
        webElement.clear();
        webElement.sendKeys(str);
    }
    public void send(By by, int index, String str){
        WebElement webElement = finds(by).get(index);
        webElement.clear();
        webElement.sendKeys(str);
    }
    //getText
    public String text(By by, boolean flag){
        return find(by,flag).getText();
    }
    public String text(By by){
        return find(by).getText();
    }
    //点击操作
    public void click(By by){
        find(by).click();
    }
    public void click(By by, int index){
        finds(by).get(index).click();
    }
    public WebDriverWait waitUtil(){
        WebDriverWait wait = new WebDriverWait(androidDriver,
                Duration.ofSeconds(20), //总共查找等待条件的时间
                Duration.ofSeconds(1));//每隔多少秒去查找一次显示等待的条件
        return wait;
    }

}
