    @RequestMapping("/${vo.methodName}")
    public ApiResponse<${vo.response}> ${vo.methodName}(<#if vo.paramString ??>${vo.paramString} in</#if>){
        log.debug("---RPC层 ${vo.description} 入参 /api/${vo.methodName} {}" <#if vo.paramString ??>, JSON.toJSONString(in)</#if>);
        ApiResponse<${vo.response}> result = null;
        try {
            result = ApiResponse.success(userService.${vo.methodName}(<#if vo.paramString ??>in</#if>));
        } catch (Exception e) {
            log.error("############RPC层 ${vo.description} 错误 /api/${vo.methodName} {}"<#if vo.paramString ??> , JSON.toJSONString(in)</#if>);
        return ApiResponse.failure(ApiResponseCode.CUSTOMER_SERVICE);
        }
        log.debug("***RPC层 ${vo.description} 出参 /api/${vo.methodName} {}" , JSON.toJSONString(result));
        return result;
    }