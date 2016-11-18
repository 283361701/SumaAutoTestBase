package com.sumavision.base;

import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 错误结果收集和断言
 * @author chenxunlie
 * @version 1.0
 * by.2016/11/18
 */

public class Assertion {

    public static boolean flag = true;                      

    public static List<Error> errors = new ArrayList<>();    

    public static void verifyEquals(Object actual, Object expected){
        try {
            Assert.assertEquals(actual, expected);
        } catch(Error e) {
            errors.add(e);
            flag = false;
        }
    }

    public static void verifyEquals(Object actual, Object expected, String message){
        try {
            Assert.assertEquals(actual, expected, message);
        } catch(Error e) {
            errors.add(e);
            flag = false;
        }
    }
    
}