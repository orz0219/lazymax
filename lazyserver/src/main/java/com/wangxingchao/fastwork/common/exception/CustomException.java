package com.wangxingchao.fastwork.common.exception;

/**
 * @program: lazyserver
 * @description: 自定义异常
 * @author: wangxingchao
 * @create: 2019/10/21 下午2:56
 **/
public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

}
