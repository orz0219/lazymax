package com.wangxingchao.fastwork.template.kinds;

import com.wangxingchao.fastwork.template.Templates;
import com.wangxingchao.fastwork.template.ftl.FreeMarker;
import com.wangxingchao.fastwork.template.vo.BaseVo;
import com.wangxingchao.fastwork.template.vo.MiddleRpcVo;

/**
 * @program: lazyserver
 * @description:
 * @author: wangxingchao
 * @create: 2019/10/21 下午8:45
 **/
public class Rpc extends Templates {

    @Override
    protected BaseVo getVo() {
        MiddleRpcVo middleRpcVo = new MiddleRpcVo();
        middleRpcVo.setHasResult(false);
        middleRpcVo.setSecondPath("/home");
        middleRpcVo.setMethodName("getProductList");
        middleRpcVo.setDescription("商品列表信息");
        middleRpcVo.setResponse("ProductListVOOut");
        middleRpcVo.setParamString("PageVOIn");
        return middleRpcVo;
    }

    public static void main(String[] args) {
        System.out.println(new Rpc().templateByStringWriter(FreeMarker.RPC));
    }


}
