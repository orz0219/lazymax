package com.wangxingchao.fastwork.template;

import com.wangxingchao.fastwork.template.ftl.FreeMarker;
import com.wangxingchao.fastwork.template.vo.BaseVo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: lazyserver
 * @description:
 * @author: wangxingchao
 * @create: 2019/10/23 下午1:38
 **/
public abstract class Templates {
    private static Configuration configuration;
    private static final Map<FreeMarker, Template> templates = new HashMap<>();
    private BaseVo vo;

    static {
        configuration = new Configuration(Configuration.VERSION_2_3_28);
        try {
            configuration.setDirectoryForTemplateLoading(new File(FreeMarker.class.getResource("").getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Templates() {
        vo = getVo();
    }

    protected abstract BaseVo getVo();

    protected String template(FreeMarker freeMarker, Writer out){
        Template template = templates.get(freeMarker);
        try {
            if (null == template) {
                template = configuration.getTemplate(freeMarker.value());
                templates.put(freeMarker, template);
            }
            template.process(getDataMap(), out);
            if (out instanceof StringWriter) {
                return  out.toString();
            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<String, Object> getDataMap(){
        Field[] fields = vo.getClass().getDeclaredFields();
        Map<String, Object> map = new HashMap<>();
        for (Field f : fields) {
            try {
                String fieldName = f.getName();
                f.setAccessible(true);
                Object o = f.get(vo);
                map.put(fieldName, o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public  String templateByStringWriter(FreeMarker marker){
        return template(marker, new StringWriter());
    }

}
