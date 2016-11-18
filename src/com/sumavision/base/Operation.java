package com.sumavision.base;

import static io.appium.java_client.android.AndroidKeyCode.BACKSPACE;
import static io.appium.java_client.android.AndroidKeyCode.KEYCODE_MOVE_END;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;

import com.sumavison.common.CmdConfig;
import com.sumavison.common.CmdUnit;
import com.sumavison.common.ToolUnit;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * 操作类封装
 * @author chenxunlie 
 * @version 1.0 
 * by.2016/11/18
 */
public class Operation {
	
	AndroidDriver driver;
	TouchAction touchAction;
	
	public static int TIME_INTERVAL = 500;
	public static String VIDEO_PATH = System.getProperty("user.dir")+"\\Result\\Video\\";
	public static String VIDEO_FRAME_PATH = System.getProperty("user.dir")+"\\Result\\Frame";
	public static String RESULT_IMAGE_PATH = System.getProperty("user.dir")+"\\Result\\Image\\";
	public static String TOOL_PATH = System.getProperty("user.dir")+"\\Tool\\";
	
	public Operation(AndroidDriver androidDriver) {
		this.driver = androidDriver;
	}
	/**
	 * 隐性等待时间
	 * @param ms 等待时间
	 */
	public void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) { 
			e.printStackTrace();
		}
	}
	/**
	 * 对输入框输入字符
	 * @param ae 元素
	 * @param str 输入内容
	 */
	public void input(AndroidElement ae, String str) {
		if(ae == null) {
			System.out.println("输入框不存在，无法输出字符串");
		} else {
			ae.sendKeys(str);
		}
	}
	/**
	 * 对输入框进行删除操作
	 * @param ae 
	 */
	public void clearText(AndroidElement ae) {
        String text = ae.getText();
        ae.click();
        driver.pressKeyCode(KEYCODE_MOVE_END);
        
        for (int i = 0; i < text.length(); i++) {
            driver.pressKeyCode(BACKSPACE);
        }
        
        System.out.println("清除完成！");
	} 
	/**
	 * 发送键值
	 * @param keyNum
	 * @param count
	 * @param ms
	 */
	public void sendKeyCode(int keyNum, int count, int ms) {
		for(int i = 0; i < count; i++){
			driver.pressKeyCode(keyNum);
			sleep(ms);
		}
	}
	public void sendKeyCode(int keyNum, int count) {
		sendKeyCode(keyNum, count, TIME_INTERVAL);
	}
	public void sendKeyCode(int keyNum) {
		sendKeyCode(keyNum,1);
	}
	/**
	 * 元素焦点点击
	 * @param ae
	 * @param x
	 * @param y
	 * @param duration
	 */
	public void clickByLongPress(AndroidDriver ae,int x,int y, int duration) {
		touchAction.longPress((WebElement) ae, x, y, duration);
	}
	public void clickByLongPress(int x,int y, int duration) {
		clickByLongPress(null, x, y, duration);
	}
	public void clickByLongPress(int x,int y) {
		clickByLongPress(null, x, y, 3);
	}
	public void clickByPress(AndroidDriver ae, int x, int y) {
		touchAction.press((WebElement) ae, x, y);
	}
	public void clickByPress(int x, int y) {
		clickByPress(null, x, y);
	}
	/**
	 * 截图并裁剪
	 * @param x 
	 * @param y
	 * @param width
	 * @param height
	 */
	public void takeScreenShot(int x, int y, int width, int height ) {
		//截图动作
		File scrFile = this.driver.getScreenshotAs(OutputType.FILE);
		//文件命名
		String imageName =ToolUnit.getSysDate()+".png";
		ToolUnit.initFile(RESULT_IMAGE_PATH);
		try {
			Iterator iterator = ImageIO.getImageReadersByFormatName("PNG");
			ImageReader reader = (ImageReader) iterator.next();
			InputStream inputStream = new FileInputStream(scrFile);
			ImageInputStream iis = ImageIO.createImageInputStream(inputStream);
			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rectangle = new Rectangle(x, y, width, height);
			param.setSourceRegion(rectangle);
			BufferedImage bi = reader.read(0, param);
			ImageIO.write(bi, "PNG", new File(RESULT_IMAGE_PATH + imageName));
			System.out.println("截图保存路径:" + RESULT_IMAGE_PATH + imageName);
		} catch (Exception e) {
			System.err.println("未截图成功");
			e.printStackTrace();
		}
	}
	public void takeScreenShot() {
		ElementPage ePage = new ElementPage(this.driver);
		int width = ePage.getScreenWidth();
		int height = ePage.getScreenHeight();
		takeScreenShot(0, 0, width, height);
	}
	/**
	 * 调用摄像头，true 打开并开始录制  false 结束录制
	 * @param flag
	 */
	public void takeScreenRecord(String state) {
		ToolUnit.initFile(TOOL_PATH);
		String cmdLaunch = TOOL_PATH + CmdConfig.RECORD_LAUNCH;
		String cmdStart = TOOL_PATH + CmdConfig.RECORD_START;
		switch (state) {
		case "launch":
			CmdUnit.runWin(cmdLaunch);
			sleep(3000);
			break;
		case "play":
			CmdUnit.runWin(cmdStart);
			sleep(3000);
			break;
		case "stop":
			CmdUnit.runWin(cmdStart);
			sleep(3000);
			break;
		default:
			System.err.println("输入非法关键字！");
			break;
		}
	}
	/**
	 * 对指定目录下的最新视频进行分帧
	 */
	public void takeRecordFrame() {
		ToolUnit.initFile(VIDEO_PATH);
		ToolUnit.initFile(VIDEO_FRAME_PATH);
		File fileName = new File(VIDEO_PATH);
		//第一个文件为最新生成文件；
		File[] listFile = fileName.listFiles();
		Arrays.sort(listFile, new Comparator<File>() {
			   @Override
			   public int compare(File file1, File file2) {
			      return (int)(file2.lastModified()-file1.lastModified());
			   }
			});
		//把最新文件重命名，否则FFMEPG不识别空格；
		listFile[0].renameTo(new File(VIDEO_PATH + "0.mp4"));
		//指定视频文件，进行分帧命令行调用
		String lastVideoName = "0.mp4";
		CmdUnit.runWin("ffmpeg -i " + VIDEO_PATH+lastVideoName 
				+ " -f image2 -vf fps=fps=30 "
				+ VIDEO_FRAME_PATH + "//%d.png");
		//临时删除视频
		new File(VIDEO_PATH+lastVideoName).delete();
	}
	
	public void takeLogcat(String usbPath) {
		String cmd = CmdConfig.ADB_LOGCAT_USB.replaceAll("#usbPath#", usbPath);
		CmdUnit.runAdb(cmd);
	}
	
	public void takeLogcat(){
		CmdUnit.runAdb(CmdConfig.ADB_LOGCAT_LOCAL);
	}
	
	public void closeLogcat() {
		CmdUnit.runAdb(CmdConfig.ADB_LOGCAT_KILL);
	}
}
