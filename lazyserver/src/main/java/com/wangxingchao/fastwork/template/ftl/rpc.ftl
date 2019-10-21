    /**
    *
    */
    @RequestMapping("/${vo.methodName}")
    @ValidateBasisParam
    @ValidateNull("phone")
    public CommonResponse<String> ${vo.methodName}(${vo.paramString} in){
        log.debug("---RPC层 获取手机验证码 入参 /api/${vo.methodName} {}" , JSON.toJSONString(in));
        CommonResponse<String> result = null;
            try {
                result = CommonResponse.success(userService.${vo.methodName}(in));
            } catch (Exception e) {
                log.error("############RPC层 获取手机验证码 错误 /api/${vo.methodName} {}" , JSON.toJSONString(in));
            return CommonResponse.failure(ResponseCode.CUSTOMER_SERVICE);
            }
            log.debug("***RPC层 获取手机验证码 出参 /api/${vo.methodName} {}" , JSON.toJSONString(result));
            return result;
        }