package com.wangxingchao.fastwork.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: lazyserver
 * @description:
 * @author: wangxingchao
 * @create: 2019/10/18 下午3:22
 **/
@Data
@Component
@ConfigurationProperties(prefix = "lazy.datasource")
public class AutoConfiguration {
   private String url;
   private String tableName;
}
