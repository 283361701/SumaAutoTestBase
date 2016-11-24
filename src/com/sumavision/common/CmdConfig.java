package com.sumavision.common;

public interface CmdConfig {

	//启动摄像头
	String RECORD_LAUNCH = "CameraController.exe start";
	//开始录制或停止
	String RECORD_START = "CameraController.exe record";
	//开始Logcat，抓取到本地；
	String ADB_LOGCAT_LOCAL = "logcat -c;logcat -vtiem >/data/"+ToolUnit.getSysDate()+".log &";
	//开始Logcat,抓取到本地；
	String ADB_LOGCAT_USB = "logcat -c;logcat -vtime >#usbPath#"+ToolUnit.getSysDate()+".log &";
	//结束logcat进程；
	String ADB_LOGCAT_KILL ="sync;killall logcat";
	
}
