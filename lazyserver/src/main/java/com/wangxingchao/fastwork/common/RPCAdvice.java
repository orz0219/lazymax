package com.wangxingchao.fastwork.common;

import com.wangxingchao.fastwork.common.exception.CustomException;
import com.wangxingchao.fastwork.common.exception.ValidateNullParamException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: lazyserver
 * @description: 捕获异常
 * @author: wangxingchao
 * @create: 2019/10/18 下午8:05
 **/
@ControllerAdvice
public class RPCAdvice {

    @ExceptionHandler(ValidateNullParamException.class)
    @ResponseBody
    public Result validateNull(Exception e){
        return Result.failed(e.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result customException(Exception e){
        return Result.failed(e.getMessage());
    }
}
