接口URL：${url}

调用方法：
- Method：POST
- ContentType：application/json

调用限制：NA/min

请求入参：

序列 | 参数名 | 参数类型 | 是否为空 | 参数描述
:---:|:---:|:---:|:---:|:---:
<#list fieldsIn as field>
${field_index + 1}|${field.name}|${field.type}|${field.allowNull}|${field.comment}
</#list>

请求示例：
```
{
<#list fieldsIn as field>
    "${field.name}": <#if field.type == "String">"</#if>${field.defaultValue}<#if field.type == "String">"</#if>
</#list>
}
```

请求出参：

序列 | 参数名 | 参数类型 | 是否为空 | 参数描述
:---:|:---:|:---:|:---:|:---:
<#list fieldsOut as field>
${field_index + 1}|${field.name}|${field.type}|${field.allowNull}|${field.comment}
</#list>

返回示例:
```
{
    "code":1001,
    "msg":"",
    <#if fieldsOut?size = 1 && fieldsOut[0].name = "data">
    "data": ${fieldsOut[0].defaultValue},
    <#else >
    "data":{
    <#list fieldsOut as field>
        "${field.name}": <#if field.type == "String">"</#if>${field.defaultValue}<#if field.type == "String">"</#if>
    </#list>
    },
    </#if>
    "st": 1571746500635
}
```