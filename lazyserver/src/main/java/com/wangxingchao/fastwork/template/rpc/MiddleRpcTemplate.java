package com.wangxingchao.fastwork.template.rpc;

import com.wangxingchao.fastwork.template.RpcTemplate;
import com.wangxingchao.fastwork.template.vo.MiddleRpcVo;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: lazyserver
 * @description:
 * @author: wangxingchao
 * @create: 2019/10/21 下午8:45
 **/
public class MiddleRpcTemplate extends RpcTemplate {

    protected MiddleRpcTemplate(MiddleRpcVo vo) {
        super(vo);
    }

    @Override
    protected Map getDataMap() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("vo", vo);
        return dataMap;
    }

    public static void main(String[] args) {
        MiddleRpcVo middleRpcVo = new MiddleRpcVo();
        middleRpcVo.setHasResult(false);
        middleRpcVo.setMethodName("validatePhone");
        middleRpcVo.setParamString("PhoneVOIn");
        new MiddleRpcTemplate(middleRpcVo).getTemplate();
    }
}
