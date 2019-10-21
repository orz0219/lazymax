package com.wangxingchao.fastwork.common.exception;

/**
 * @program: lazyserver
 * @description:
 * @author: wangxingchao
 * @create: 2019/10/18 下午8:01
 **/
public class ValidateNullParamException extends RuntimeException{

    public ValidateNullParamException(String message) {
        super(message);
    }
}
