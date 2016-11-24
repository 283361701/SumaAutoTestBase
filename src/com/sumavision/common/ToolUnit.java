package com.sumavision.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ToolUnit {
	
	//获取系统时间
	public static String getSysDate() {
	    return getSysDate("yyyy_MM_dd_HH_mm_ss");
	}
	//获取系统时间
	public static String getSysDate(String dateFormat) {
		SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
		Calendar cal = Calendar.getInstance();
	    Date date = cal.getTime();
	    String dateStr = sf.format(date);
	    return dateStr;
	}
	//判断文件夹是否存在；
	public static boolean initFile(String path) {
		File file = new File(path);
		if(!file.exists()) {
			file.mkdirs();
		}
		return file.exists();
	}

}
