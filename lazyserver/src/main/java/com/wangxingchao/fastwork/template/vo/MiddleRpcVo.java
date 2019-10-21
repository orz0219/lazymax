package com.wangxingchao.fastwork.template.vo;

import com.wangxingchao.fastwork.template.ftl.FreeMarker;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: lazyserver
 * @description:
 * @author: wangxingchao
 * @create: 2019/10/21 下午8:31
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class MiddleRpcVo extends BaseVo{
    protected String methodName;
    protected String paramString;
    protected Boolean hasResult;
}
