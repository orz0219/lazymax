package com.wangxingchao.fastwork.rpc;

import com.wangxingchao.fastwork.common.Result;
import com.wangxingchao.fastwork.common.config.AutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: lazyserver
 * @description:
 * @author: wangxingchao
 * @create: 2019/10/18 下午7:06
 **/
@RestController
public class TableRPC {

    @Resource
    AutoConfiguration configuration;

    @GetMapping("table")
    public Result tableMessage(){
        if (null == configuration) {

        }
        return Result.success();
    }

}
