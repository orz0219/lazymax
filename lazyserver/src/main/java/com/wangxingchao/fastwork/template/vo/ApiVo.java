package com.wangxingchao.fastwork.template.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @program: lazyserver
 * @description:
 * @author: wangxingchao
 * @create: 2019/10/23 下午1:31
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ApiVo extends BaseVo{
    private String url;
    private List<FieldVo> fieldsIn;
    private List<FieldVo> fieldsOut;
}
