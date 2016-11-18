package com.sumavison.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.xalan.xsltc.runtime.BasisLibrary;


public class CmdUnit implements Runnable {
	
    private String cmdOut = "";
    private BufferedReader br = null;
	private InputStream is;  
	
    CmdUnit(InputStream is) {
    	this.is =is; 
    }
    public void run() {  
        try {  
            BufferedReader br = new BufferedReader(new InputStreamReader(is));  
            String line = null;  
            while ((line = br.readLine()) != null) {  
                System.out.println(cmdOut = cmdOut 
                		+ line + System.getProperty("line.separator")); 
            }  
        } catch (IOException ioe) {  
            ioe.printStackTrace();
        } finally {
        	if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }  
	/**
	 * adb命令行执行并输出
	 * @param cmd
	 * @return
	 */
	public static Process runAdb(String cmd) {
		return runWin("adb shell " + cmd);
	}
	/**
	 * 命令行输入
	 * @param cmd
	 * @return 
	 */
	public static Process runWin(String cmd) {
		Process p = null;
	        try {
	            if (isWindows()) {
	                String command = "cmd /c " + cmd;
	                p = Runtime.getRuntime().exec(command);
	                new Thread(new CmdUnit(p.getInputStream())).start();
	                new Thread(new CmdUnit(p.getErrorStream())).start();
	                p.waitFor();
	                p.getOutputStream().close();
	            } else {
	            	System.err.println("无法兼容非Win系统！");
	            }
	        } catch (IOException e) {
	        	System.out.println("命令行出错！");
	            e.printStackTrace();
	        } catch (InterruptedException e) {
				e.printStackTrace();
			}
	        return p;
	}
	//判断系统是否为window
	public static boolean isWindows() {
		String os = System.getProperty("os.name");
		return (os.toLowerCase().startsWith("win")) ? true : false;
	}
	

}
