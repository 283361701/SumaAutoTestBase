package com.sumavision.base;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *断言异常监听器
 *@author chenxunlie
 *@version 1.0
 *by.2016/11/18
 */

public class AssertionListener extends TestListenerAdapter {
	
	private int index = 0;
	
	@Override
    public void onTestStart(ITestResult result) {
        Assertion.flag = true;
        Assertion.errors.clear();
    }
    @Override
    public void onTestFailure(ITestResult tr) {
        this.handleAssertion(tr);
    }
    
    @Override
    public void onTestSkipped(ITestResult tr) {
        this.handleAssertion(tr);
    }
    
    @Override
    public void onTestSuccess(ITestResult tr) {
        this.handleAssertion(tr);
    }
    
    private void handleAssertion(ITestResult tr){
        if (!Assertion.flag){  
            Throwable throwable = tr.getThrowable();
            if (throwable==null){
                throwable = new Throwable();
            }
            
            StackTraceElement[] traces = throwable.getStackTrace();
            StackTraceElement[] alltrace = new StackTraceElement[0];

            for (Error e : Assertion.errors) {
                StackTraceElement[] errorTraces = e.getStackTrace();
                StackTraceElement[] et = getKeyStackTrace(tr, errorTraces);
                StackTraceElement[] message = handleMess(e.getMessage(),tr);
                index = 0;
                alltrace = merge(alltrace, message);
                alltrace = merge(alltrace, et);
            }
            
            if (traces!=null){
                traces = getKeyStackTrace(tr, traces);
                alltrace = merge(alltrace, traces);
            }
            
            throwable.setStackTrace(alltrace);
            tr.setThrowable(throwable);
            Assertion.flag = true;
            Assertion.errors.clear();
            tr.setStatus(ITestResult.FAILURE);
        }
    }
    
    private StackTraceElement[] getKeyStackTrace(ITestResult tr, StackTraceElement[] stackTraceElements){
        List<StackTraceElement> ets = new ArrayList<>();
        
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            if (stackTraceElement.getClassName().equals(tr.getTestClass().getName())){
                ets.add(stackTraceElement);
                index = stackTraceElement.getLineNumber();
            }
        }
        return ets.toArray(new StackTraceElement[ets.size()]);
    }
    
    private StackTraceElement[] merge(StackTraceElement[] traces1, StackTraceElement[] traces2){
        StackTraceElement[] result = Arrays.copyOf(traces1, traces1.length + traces2.length);
        System.arraycopy(traces2, 0, result, traces1.length, traces2.length);
        return result;
    }
    
    private StackTraceElement[] handleMess(String mess,ITestResult tr){
        String message = "\nError Message: "+mess;
        String method = "\nError Mothod:"+tr.getMethod().getMethodName();
        String className = "\nError Class:"+tr.getTestClass().getRealClass().getSimpleName();
        return new StackTraceElement[]{
                new StackTraceElement(message, 
                        method,                        
                        className+"\nError Index",      
                        index)};
    }
    
}
