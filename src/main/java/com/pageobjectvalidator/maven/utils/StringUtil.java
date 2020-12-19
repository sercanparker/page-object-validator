package com.pageobjectvalidator.maven.utils;

/**
 * @author sercansensulun on 19.12.2020.
 */
public class StringUtil {

    /**
     * Returns true when string is empty or
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str){
        if (str == null){
            return true;
        }
        return str.isEmpty();
    }
}
