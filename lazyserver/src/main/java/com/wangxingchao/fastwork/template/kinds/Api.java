package com.wangxingchao.fastwork.template.kinds;

import com.wangxingchao.fastwork.template.Templates;
import com.wangxingchao.fastwork.template.ftl.FreeMarker;
import com.wangxingchao.fastwork.template.vo.ApiVo;
import com.wangxingchao.fastwork.template.vo.BaseVo;
import com.wangxingchao.fastwork.utils.ReadUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: lazyserver
 * @description:
 * @author: wangxingchao
 * @create: 2019/10/22 下午1:51
 **/
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class Api extends Templates {

    private String url;
    private String pathIn;
    private String pathOut;

    @Override
    protected BaseVo getVo() {
        ApiVo vo = new ApiVo();
        vo.setUrl(url);
        vo.setFieldsIn(ReadUtils.readFields(pathIn));
        vo.setFieldsOut(ReadUtils.readFields(pathOut));
        return vo;
    }

    public static void main(String[] args) {
        Api api = new Api(
                "/api/getBasisConfig",
                "/home/xc/works/projects/company/Oceanus/zdd-common/src/main/java/com/zdd/vo/system/MenuVOOut.java",
                "/home/xc/works/projects/company/Oceanus/zdd-common/src/main/java/com/zdd/vo/system/WebVOOut.java"
        );
        System.out.println(api.templateByStringWriter(FreeMarker.API));
    }

}
