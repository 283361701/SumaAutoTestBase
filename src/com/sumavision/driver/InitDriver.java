package com.sumavision.driver;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.sumavision.driver.Builder;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/** 
 * 设备初始化模块
 * @author chenxunlie
 * @version 1.0
 * by.2016/11/18
 */

@Listeners({com.sumavision.base.AssertionListener.class}) 
public class InitDriver {
	public static String deviceName = "10.10.30.202:5555";
	public static String platformVersion = "4.4.2";
	public static String platformName = "Android";
	public static String browserName = "chrome";
	//apk安装路径
	public static String appPath = System.getProperty("user.dir")+"/apps/xxx.apk";
	public static String appPackage = "com.sumavision.iptv";
	public static String appActivity = ".MainActivity";
	public static String autuLaunch = "Flase";
	
	public static String noReset = "True";
	public static String noSign = "True";
	public static String unicodeKeyboard = "True";
	public static String resetKeyboard = "True";
	
	public static AndroidDriver <AndroidElement> driver = null;
	
	public InitDriver() {
		this (new Builder());
	}
	
	public InitDriver(Builder builder) {
		deviceName = builder.deviceName;
		platformName = builder.platformName;
		platformVersion = builder.platformVersion;
		browserName = builder.browserName;
		appActivity = builder.appActivity;
		appPackage = builder.appPackage;
		appPath = builder.appPath;
		autuLaunch = builder.antuLaunch;
		noReset = builder.noReset;
		noSign = builder.noSign;
		unicodeKeyboard = builder.unicodeKeyboard;
		resetKeyboard = builder.resetKeyboard;	
	}
	
	@BeforeSuite
	public void launchDriver() throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		
		capabilities.setCapability("deviceName", deviceName);
		capabilities.setCapability("platformVersion", platformVersion);
		capabilities.setCapability("platformName", platformName);
		//指定浏览器类型，不能和app冲突，不能同时指定
		//capabilities.setCapability("browserName", browserName);
		//指定app安装路径
		//capabilities.setCapability("app", new File(appPath).getAbsolutePath());
		//应用包名和主Activity名称
		capabilities.setCapability("appPackage", appPackage);
		capabilities.setCapability("appActivity", appActivity);		
		//指定是否启动应用
		//capabilities.setCapability("autoLaunch", autuLaunch);
		//是否重新签名和重安装
		capabilities.setCapability("noReset", noReset);
		capabilities.setCapability("noSign", noSign);
		//指定中文键盘和输入法
		capabilities.setCapability("unicodeKeyboard", unicodeKeyboard);
		capabilities.setCapability("resetKeyboard", resetKeyboard);
		
		driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
	}
	
	@AfterClass
	public void quitDriver() {
		driver.quit();
	}
}
