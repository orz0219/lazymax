package com.wangxingchao.fastwork.template.ftl;

public enum FreeMarker {
    RPC("rpc"),
    API("api");

    private String value;

    FreeMarker(String value) {
        this.value = value;
    }

    public String value() {
        return value + ".ftl";
    }
}
