package com.wangxingchao.fastwork.utils;

import com.wangxingchao.fastwork.template.vo.FieldVo;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: lazyserver
 * @description: 读文件操作
 * @author: wangxingchao
 * @create: 2019/10/23 下午3:16
 **/
@Slf4j
public class ReadUtils {

    public static List<FieldVo> readFields(String javaPath){
        try {
            BufferedReader buffReader = new BufferedReader(new FileReader(javaPath));
            String str;
            List<FieldVo> fields = new ArrayList<>();
            boolean allowNull = true;
            String comment = null;
            while ((str = buffReader.readLine()) != null) {
                if (str.contains("@ValidateNull")) {
                    allowNull = false;
                }
                if (str.contains("//")) {
                    comment = str.replaceAll("\\s+//\\s*", " ");
                }
                if (str.contains(" private ") && str.contains(";")) {
                    String replaceAll = str.replaceAll(";", "").replaceAll("\\s+private ", "");
                    String[] typeAndName = replaceAll.split(" ");
                    FieldVo vo = new FieldVo();
                    String type = typeAndName[0].trim();
                    vo.setType(typeAndName[0].trim());
                    vo.setName(typeAndName[1].trim());
                    vo.setDefaultValue("String".equals(type) ? "some string" : new Date().getTime() + "");
                    vo.setAllowNull(allowNull ? "是" : "否");
                    allowNull = true;
                    vo.setComment(comment);
                    comment = null;
                    fields.add(vo);
                }
            }
            return fields;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
