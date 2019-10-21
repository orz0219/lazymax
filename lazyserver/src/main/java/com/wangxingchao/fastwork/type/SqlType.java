package com.wangxingchao.fastwork.type;

/**
 * @program: lazyserver
 * @description: sql类型
 * @author: wangxingchao
 * @create: 2019/10/18 下午8:13
 **/
public enum  SqlType {
    INSERT("insert"),
    UPDATE("update"),
    DELETE("delete"),
    SELECT("select");

    private String value;

    SqlType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
