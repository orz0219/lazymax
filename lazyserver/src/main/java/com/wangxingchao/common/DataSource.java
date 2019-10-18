package com.wangxingchao.common;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.stereotype.Component;

@Data
@Slf4j
@Component
public class DataSource {
    private static final ConfigurationPropertyName url = ConfigurationPropertyName.of("lazy.datasource.url");
    private static final ConfigurationPropertyName tableName = ConfigurationPropertyName.of("lazy.datasource.table-name");

    public DataSource() {
        log.debug(url.toString());
        log.debug(tableName.toString());
    }
}
