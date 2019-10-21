package com.wangxingchao.fastwork.template;

import com.wangxingchao.fastwork.common.exception.CustomException;
import com.wangxingchao.fastwork.dto.TableDto;
import com.wangxingchao.fastwork.utils.StrUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.StringJoiner;

/**
 *  拟定功能
 */
@Slf4j
public abstract class Template {

    protected String tableName;

    protected String tableNameWithAlias;

    protected String tableAlias;

    protected TableDto table;

    Template(TableDto table) {
        List<TableDto.Field> fields = table.getFields();
        if (null == fields || fields.isEmpty()) {
            String errMessage = "表参数为空，请检查";
            log.error(errMessage);
            throw new CustomException(errMessage);
        }
        this.table = table;
        this.tableName = table.getName();
        getTableNameWithAlias();
    }

    public String serializeToSql(){
        switch (table.getSqlType()){
            case SELECT:
                return select();
            case INSERT:
                return insert();
            case UPDATE:
                return update();
            case DELETE:
                return delete();
        }
        throw new CustomException("未知模式");
    }

    protected abstract String insert();
    protected abstract String insertMany();
    protected abstract String update();
    protected abstract String delete();
    protected abstract String select();

    private void getTableNameWithAlias(){
        tableAlias = StrUtils.lowerCaseFirstWord(tableName);
        tableNameWithAlias = new StringJoiner(" ")
                .add(tableName)
                .add("as")
                .add(tableAlias)
                .toString();
    }

}
