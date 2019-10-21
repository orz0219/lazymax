package com.wangxingchao.fastwork.common;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class Result implements Serializable {
    private boolean success = true;
    private String message = "操作成功";
    private String errorMessage;
    private Object object = null;
    private Map<String, Object> attributes;
    private Long total;

    public static Result success(){
        return new Result();
    }

    public static Result success(Object o){
        Result result = new Result();
        result.setObject(o);
        return result;
    }

    public static Result failed(String message){
        Result result = new Result();
        result.setMessage(message);
        result.setSuccess(false);
        return result;
    }

    public static Result failed(String message, Exception e){
        e.printStackTrace();
        return failed(message);
    }
}
