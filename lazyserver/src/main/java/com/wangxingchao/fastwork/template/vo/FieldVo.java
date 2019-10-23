package com.wangxingchao.fastwork.template.vo;

import lombok.Data;

/**
 * @program: lazyserver
 * @description:
 * @author: wangxingchao
 * @create: 2019/10/23 下午4:28
 **/
@Data
public class FieldVo {
    private String name;
    private String type;
    private String allowNull;
    private String comment;
    private String defaultValue;
}
