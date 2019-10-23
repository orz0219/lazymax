package com.wangxingchao.fastwork.template.vo;

import com.wangxingchao.fastwork.template.ftl.FreeMarker;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/**
 * @program: lazyserver
 * @description:
 * @author: wangxingchao
 * @create: 2019/10/21 下午8:31
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class MiddleRpcVo extends BaseVo{
    private String methodName;
    private String paramString;
    private Boolean hasResult;
    private String response;
    private String description;
}
