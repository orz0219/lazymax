    @RequestMapping("/${methodName}")
    public ApiResponse<${response}> ${methodName}(<#if paramString ??>${paramString} in</#if>){
        log.debug("---RPC层 ${description} 入参 /api/${methodName} {}" <#if paramString ??>, JSON.toJSONString(in)</#if>);
        ApiResponse<${response}> result;
        try {
            result = ${secondPath}Service.${methodName}(<#if paramString ??>in</#if>);
        } catch (Exception e) {
            log.error("############RPC层 ${description} 异常 /api${secondPath}/${methodName} {}" , e.getMessage());
            e.printStackTrace();
            return ApiResponse.failure(ApiResponseCode.EXCEPTION);
        }
        log.debug("***RPC层 ${description} 出参 /api${secondPath}/${methodName} {}" , JSON.toJSONString(result));
        return result;
    }