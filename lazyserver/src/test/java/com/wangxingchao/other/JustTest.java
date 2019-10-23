package com.wangxingchao.other;

import com.wangxingchao.fastwork.utils.Md5Utils;

import java.security.NoSuchAlgorithmException;

/**
 * @program: lazyserver
 * @description:
 * @author: wangxingchao
 * @create: 2019/10/22 下午8:16
 **/
public class JustTest {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String s = Md5Utils.md5("T" + 1 + "ST" + 1571748645540L);
        System.out.println(s);
    }
}
