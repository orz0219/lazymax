package com.wangxingchao.fastwork.template.kinds;

import com.wangxingchao.fastwork.template.Templates;
import com.wangxingchao.fastwork.template.ftl.FreeMarker;
import com.wangxingchao.fastwork.template.vo.ApiVo;
import com.wangxingchao.fastwork.template.vo.BaseVo;
import com.wangxingchao.fastwork.template.vo.FieldVo;
import com.wangxingchao.fastwork.utils.ReadUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: lazyserver
 * @description:
 * @author: wangxingchao
 * @create: 2019/10/22 下午1:51
 **/
@Slf4j
public class Api extends Templates {

    @Override
    protected BaseVo getVo() {
        ApiVo vo = new ApiVo();
        vo.setUrl("/api/test");
        vo.setFieldsIn(ReadUtils.readFields("/home/xc/works/projects/company/Oceanus/zdd-common/src/main/java/com/zdd/vo/BasisRequestVOIn.java"));
        vo.setFieldsOut(ReadUtils.readFields("/home/xc/works/projects/company/Oceanus/zdd-common/src/main/java/com/zdd/vo/BasisRequestVOIn.java"));
        return vo;
    }

    public static void main(String[] args) {
        System.out.println(new Api().templateByStringWriter(FreeMarker.API));
    }

}
