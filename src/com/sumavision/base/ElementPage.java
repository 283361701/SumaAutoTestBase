package com.sumavision.base;

import static com.sumavision.driver.InitDriver.appPackage;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import com.sumavison.common.HumingImageSame;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * 识别类封装
 * @author chenxunlie
 * @version 1.0
 * by.2016/11/18
 */

public class ElementPage {
	
	AndroidDriver driver;
	
	public static int WAIT_TIME = 3;
	public static String VIDEO_FRAME_PATH = System.getProperty("user.dir")+"\\Result\\Frame\\";
	public static String RESULT_IMAGE_PATH = System.getProperty("user.dir")+"\\Result\\Image\\";
	public static String COMP_IMAGE_PATH = System.getProperty("user.dir")+"\\FlagImage\\";
	
	public ElementPage(AndroidDriver androidDriver) {
		this.driver = androidDriver;
		waitAuto();
	}
	//隐性等待时间，强制执行；
	public void waitAuto() {
		waitAuto(WAIT_TIME);
	}
	public void waitAuto(int time) {
		driver.manage().timeouts()
		.implicitlyWait(time, TimeUnit.SECONDS);
	}
	//显性等待时间，等待元素出现，一出现就结束等待
	public AndroidElement waitAutoById(String id) {
        return waitAutoById(id, WAIT_TIME);
    }

    public AndroidElement waitAutoById(String id, int time) {
        return waitAuto(By.id(id), time);
    }

    public AndroidElement waitAutoByName(String name) {
        return waitAutoByName(name, WAIT_TIME);
    }

    public AndroidElement waitAutoByName(String name, int time) {
        return waitAuto(By.name(name), time);
    }
    
    public AndroidElement waitAutoByXp(String xPath, int time) {
    	return waitAuto(By.xpath(xPath), time);
    }
    
    public AndroidElement waitAutoByXp(String xPath) {
        return waitAutoByXp(xPath, WAIT_TIME);
    }
    public AndroidElement waitAuto(By by, int time) {
    	try {
    		return new AndroidDriverWait(driver, time)
    				.until(new ExpectedCondition<AndroidElement>() {
    					@Override
    					public AndroidElement apply(AndroidDriver androidDriver) {
    						return (AndroidElement) androidDriver.findElement(by);
    					}
    				});
    	} catch (TimeoutException e) {
    		return null;
    	}
    }
    //判断元素是否存在
    public boolean isIdElementExist(String id) {
    	return isIdElementExist(id,0);
    }
	public boolean isIdElementExist(String id,int timeOut) {
		return isIdElementExist(id, timeOut,false);
	}
	public boolean isIdElementExist(String id,int timeOut,boolean isShow) {
		return isElementExist(By.id(appPackage + ":id/" +id),timeOut,isShow);
	}
	public boolean isNameElementExist(String name) {
		return isNameElementExist(name, 0);
	}
	public boolean isNameElementExist(String name, int timeOut) {
		return isNameElementExist(name, timeOut,false);
	}
	public boolean isNameElementExist(String name, int timeOut, boolean isShow) {
	    return isElementExist(By.name(name),timeOut, isShow);
	}
	public boolean isClassNameElementExist(String className) {
		return isClassNameElementExist(className,0);		
	}
	public boolean isClassNameElementExist(String className,int timeOut) {
		return isClassNameElementExist(className, timeOut,false);
	}
	public boolean isClassNameElementExist(String className,int timeOut,boolean isShow) {
		return isElementExist(By.className(className), timeOut, isShow);
	}
	public boolean isXpathElementExist(String text) {
        return isXpathElementExist(text,0);
    }
    public boolean isXpathElementExist(String text,int timeOut) {
        return isXpathElementExist(text,timeOut, false);
    }
    public boolean isXpathElementExist(String text,int timeOut,boolean isShow) {
        ////android.widget.TextView[@text='"+text+"']
        return isElementExist(By.xpath(text), timeOut,isShow);
    }
	private boolean isElementExist(By by,int timeOut,boolean isShow) {
		try {
			AndroidElement element = waitAuto(by,timeOut);
			if (element == null) {
				return false;
			} else {
				if (isShow) {
                    return element.isDisplayed();
                }
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	//判断输入框内是否为指定文字；
	public boolean isTextExist(String text) {
        String str = driver.getPageSource();
        System.out.println(str);
        return str.contains(text);
    }
	/**
	 * 图片相识度对比
	 * @param imgPath1
	 * @param imgPath2
	 * @param threshold
	 * @return
	 * @throws IOException
	 */
	public boolean isImageSame(String imgPath1, String imgPath2, double threshold) throws IOException {
    	int[] pixels1 = HumingImageSame.hummingResult(imgPath1);
    	int[] pixels2 = HumingImageSame.hummingResult(imgPath2);
    	int hammingDistance = HumingImageSame.getHammingDistance(pixels1,pixels2);
    	double similarity = HumingImageSame.calSimilarity(hammingDistance);
    	if (similarity>=threshold){
    		System.out.println("图片相似度："+similarity);
    		return true;    		
    	} else {
    		return false;
    	}
    }
	
	public boolean isIamgeSame(String imgPath1, String imgPath2) throws IOException {
		return isImageSame(imgPath1, imgPath2, 0.9);
	}
	
	//查找元素
	public AndroidElement findById(String id,String desc) {
		return findElementBy(By.id(id),desc);
	}
	public AndroidElement findById(String id) {
		return findById(id,"");
	}
	public AndroidElement findByName(String name,String desc) {
		return findElementBy(By.name(name), desc);
	}
	public AndroidElement findByName(String name){
		return findByName(name,"");
	}
	public AndroidElement findByClassName(String className,String desc) {
		return findElementBy(By.className(className), desc);
	}
	public AndroidElement findByClassName(String className) {
		return findByClassName(className,"");
	}
	public AndroidElement findByXpath(String xpathName) {
        return findByXpath(xpathName,"");
    }
    public AndroidElement findByXpath(String xpathName,String desc) {
        return findElementBy(By.xpath(xpathName),desc);
    }
	private AndroidElement findElementBy(By by,String str) {
		try {
			if (driver != null) {
				return (AndroidElement) driver.findElement(by);
			} else {
				System.err.println("未识别到设备！");
			}
		} catch (NoSuchElementException e) {
			e.getMessage();
		}
		return null;
	}
	//获取屏幕高宽
	public int[] getScreen() {
		int width = driver.manage().window().getSize().getWidth();
		int height = driver.manage().window().getSize().getHeight();
		return new int[] {width,height};
	}
	//获取屏宽
	public int getScreenWidth() {
		return driver.manage().window().getSize().getWidth();
	}
	//获取屏高
	public int getScreenHeight() {
		return driver.manage().window().getSize().getHeight();
	}
	/**
	 * 遍历Frame目录，找对标志帧，返回葱
	 * @param compImage 对比帧地址 
	 * @return faremTime 标志帧的名称
	 * @throws IOException
	 */
	public int getFrameTime(String compImagePath) throws IOException{
		int frameName = 0;
		String CompImagePath = COMP_IMAGE_PATH + compImagePath;
		//数字大小排序
		File frameDir = new File (ElementPage.VIDEO_FRAME_PATH);
		File[] frameFile = frameDir.listFiles();
		List< File> lists = Arrays.asList(frameFile);
		Collections.sort(lists, new Comparator< File>() {
			@Override
			public int compare(File o1, File o2) {
				Integer int1 = Integer.parseInt(o1.getName().substring(0,o1.getName().lastIndexOf(".")));
				Integer int2 = Integer.parseInt(o2.getName().substring(0,o2.getName().lastIndexOf(".")));
				if(int1 == int2){
					return 0;
				}else if (int1 > int2) {
					return 1;
				}else {
					return -1;
				}
			}
		});
		//遍历文件夹中所有的文件
		for (File f : lists) {
			String frameFilePath = VIDEO_FRAME_PATH + f.getName();
			boolean flag = isIamgeSame(CompImagePath, frameFilePath);
			if(flag) {
				String frameTimeString = f.getName();
				frameName = Integer.parseInt(frameTimeString.replaceAll(".png", ""));
				break;
			}else {
				f.delete(); //若不是关键帧，则删除
			}			
		}
		return frameName;
	}
	/**
	 * 计算二个关键字的时间间隔
	 * @param frameStart 起始帧
	 * @param frameEnd 终止帧
	 * @return getFrameIndex 间隔时间
	 * @throws IOException
	 */
	public int getFrameIndex(String frameStart, String frameEnd) throws IOException{
		int startTime = getFrameTime(frameStart);
		int endTime = getFrameTime(frameEnd);
		int resultTime = (endTime - startTime)*33;
		System.out.println("FrameIndex-------->" +resultTime + "ms");
		return resultTime;
	}

}
