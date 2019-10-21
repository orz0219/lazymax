package com.wangxingchao.fastwork.rpc;

import com.wangxingchao.fastwork.common.Result;
import com.wangxingchao.fastwork.common.config.AutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: lazyserver
 * @description: 测试
 * @author: wangxingchao
 * @create: 2019/10/18 下午3:33
 **/
@RestController
public class TestRPC {

    @Resource
    private AutoConfiguration configuration;

    @RequestMapping("test")
    public Result test(){
        return Result.success(configuration.getTableName());
    }
}
