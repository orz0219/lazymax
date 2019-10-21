package com.wangxingchao.fastwork.template;

import com.wangxingchao.fastwork.dto.TableDto;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.StringJoiner;

/**
 * @program: lazyserver
 * @description:  注解式mybatis开发
 * @author: wangxingchao
 * @create: 2019/10/18 下午7:13
 **/
@Slf4j
public class MybatisAnnotationTemplate extends Template {

    public MybatisAnnotationTemplate(TableDto table) {
        super(table);
    }

    private String fieldString;

    @Override
    protected String insert() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add("insert into")
                .add(tableNameWithAlias)
                .add("(");
        List<TableDto.Field> fields = table.getFields();
        StringJoiner fieldString = new StringJoiner(",");
        for (TableDto.Field field : fields) {
            fieldString.add(field.getName());
        }
        joiner.add(fieldString.toString())
                .add(") values (");

        return null;
    }

    @Override
    protected String insertMany() {
        return null;
    }

    @Override
    protected String update() {
        return null;
    }

    @Override
    protected String delete() {
        return null;
    }

    @Override
    protected String select() {
        return null;
    }

}