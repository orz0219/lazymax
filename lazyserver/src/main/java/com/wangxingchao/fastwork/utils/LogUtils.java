package com.wangxingchao.fastwork.utils;

/**
 * @program: lazyserver
 * @description:
 * @author: wangxingchao
 * @create: 2019/10/21 下午5:22
 **/
public class LogUtils {

    static String methodName = "FFF";
    static String apiName = "/api/getValidateCode";

    public static void main(String[] args) {
        System.out.println(new StringBuilder()
                .append("log.debug(\"---RPC层 ")
                .append(methodName)
                .append(" ")
                .append("入参 ")
                .append(apiName)
                .append(" {}\" , JSON.toJSONString(in));")
                .toString()
        );
        System.out.println(new StringBuilder()
                .append("log.debug(\"***RPC层 ")
                .append(methodName)
                .append(" ")
                .append("出参 ")
                .append(apiName)
                .append(" {}\" , JSON.toJSONString(result));")
                .toString()
        );
        System.out.println(new StringBuilder()
                .append("log.error(\"############RPC层 ")
                .append(methodName)
                .append(" ")
                .append("错误 ")
                .append(apiName)
                .append(" {}\" , JSON.toJSONString(in));")
                .toString()
        );
    }
}
