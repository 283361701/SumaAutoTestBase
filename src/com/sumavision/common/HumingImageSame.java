package com.sumavision.common;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * 感知哈希算法
 * @author chenxunlie
 * @version 1.0 
 * by.2016/11/18
 *
 */
public class HumingImageSame {
	
	 public static int[] hummingResult(String imgPath) throws IOException {
	    	File imageFile = new File(imgPath);
	    	Image image = ImageIO.read(imageFile);
	    	image = toGrayscale(image);
	    	image = scale(image);
	    	int[] pixels = getPixels(image);
	    	int averageColor = getAverageOfPixelArray(pixels);
	    	return pixels = getPixelDeviateWeightsArray(pixels, averageColor);
	    }
	    
	    //将图片转化为BufferedImage类型
	    public static BufferedImage convertToBufferedFrom(Image srcImage) {
	    	BufferedImage bufferedImage = new BufferedImage(srcImage.getWidth(null),
	    			srcImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	    	Graphics2D g = bufferedImage.createGraphics();
	    	g.drawImage(srcImage, null, null);
	    	g.dispose();
	    	return bufferedImage;
	    }
	    
	    //转化为灰度图
	    public static BufferedImage toGrayscale(Image image) {
	    	BufferedImage sourceBuffered = convertToBufferedFrom(image);
	    	ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
	    	ColorConvertOp op = new ColorConvertOp(cs, null);
	    	BufferedImage grayBuffered = op.filter(sourceBuffered, null);
	    	return grayBuffered;
	    }
	    
	    //压缩图片为32*32
	    public static Image scale(Image image) {
	    	image = image.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
	    	return image;
	    }
	    
	    //将像素点存放至数组中  
	    public static int[] getPixels(Image image) {
	    	int width = image.getWidth(null);
	    	int height = image.getHeight(null);
	    	int[] pixels = convertToBufferedFrom(image).getRGB(0, 0, width, height,
	    			null, 0, width);
	    	return pixels;
	    }
	    
	    //去灰度图的平均像素值
	    public static int getAverageOfPixelArray(int[] pixels) {
	    	 Color color;
	    	 long sumRed = 0;
	    	 for (int i = 0; i < pixels.length; i++) {
	    		 color = new Color(pixels[i], true);
	    		 sumRed += color.getRed();    		 
	    	 }
	    	 int averageRed = (int) (sumRed / pixels.length);
	    	 return averageRed;
	    }
	    
	    //获取灰度图的像素点对比数组
	    public static int[] getPixelDeviateWeightsArray(int[] pixels,final int averageColor) {
	    	 Color color;
	    	 int[] dest = new int[pixels.length];
	    	 
	    	 for (int i = 0; i < pixels.length; i++) {
	    		 color = new Color(pixels[i], true);
	    		 dest[i] = color.getRed() - averageColor > 0 ? 1 : 0;
	    	 }
	    	 return dest;
	    	 
	    }
	    
	    //通过汉明距离计算相似度
	    public static int getHammingDistance(int[] a, int[] b) {
	    	int sum = 0;
	    	for (int i = 0; i < a.length; i++) {
	    		sum += a[i] == b[i] ? 0 : 1;
	    	}
	    	return sum;
	    }
	    
	    //通过汉明距离算法计算相似度
	    public static double calSimilarity(int hammingDistance){
	    	int length = 32*32;
	    	double similarity = (length - hammingDistance) / (double) length;
	    	similarity = java.lang.Math.pow(similarity, 2); //使用指数曲线调整结果
	    	return similarity;
	    }

}
