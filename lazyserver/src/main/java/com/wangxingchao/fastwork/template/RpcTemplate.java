package com.wangxingchao.fastwork.template;

import com.wangxingchao.fastwork.template.ftl.FreeMarker;
import com.wangxingchao.fastwork.template.vo.BaseVo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Map;

/**
 * @program: lazyserver
 * @description:
 * @author: wangxingchao
 * @create: 2019/10/21 下午8:05
 **/
@Slf4j
public abstract class RpcTemplate {

    protected BaseVo vo;
    protected Configuration configuration;
    private static final String FTL_PATH = FreeMarker.class.getResource("").getPath();

    protected RpcTemplate(BaseVo vo){
        this.vo = vo;
        configuration = new Configuration(Configuration.VERSION_2_3_28);
        try {
            configuration.setDirectoryForTemplateLoading(new File(FTL_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Template getTemplate(){
        Template template = null;
        try {
             template = configuration.getTemplate(FreeMarker.RPC.value());
            Writer stringWriter = new StringWriter();
            template.process(getDataMap(), stringWriter);
            System.out.println(stringWriter.toString());
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return template;
    }

    protected abstract Map getDataMap();


    public static void main(String[] args) {

    }
}
