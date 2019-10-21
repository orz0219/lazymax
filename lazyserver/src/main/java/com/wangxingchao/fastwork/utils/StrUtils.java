package com.wangxingchao.fastwork.utils;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @program: lazyserver
 * @description:
 * @author: wangxingchao
 * @create: 2019/10/19 上午10:13
 **/
public class StrUtils extends StringUtils {

    public static String lowerCaseFirstWord(String str){
        char[] chars = str.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }
}
