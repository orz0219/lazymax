package com.wangxingchao.fastwork.dto;

import com.wangxingchao.fastwork.type.FieldType;
import com.wangxingchao.fastwork.type.SqlType;
import lombok.Data;

import java.util.List;

/**
 * @program: lazyserver
 * @description:
 * @author: wangxingchao
 * @create: 2019/10/18 下午7:15
 **/
@Data
public class TableDto {

    private String name;
    private List<Field> fields;
    private List<Join> joins;
    private SqlType sqlType;

    @Data
    public static class Field{
        private String name;
        private FieldType type;
        private String value;
    }

    @Data
    public static class Join{
        private Condition left;
        private Condition right;

        public boolean isWhere(){
            return null == this.left || null == this.right;
        }
    }

    @Data
    public static class Condition{
        private String tableName;
        private Field field;
    }

}
