package com.mangolost.ck.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 *
 */
@Configuration
public class ClickHouseConfig {

    @Bean("testCkDatasource")
    @Primary
    @ConfigurationProperties(prefix = "clickhouse.datasource.test")
    public DataSource testCkDatasource(){
        return DataSourceBuilder.create().build();
    }

    @Bean("testCkTemplate")
    @Primary
    public JdbcTemplate testCkTemplate(@Qualifier("testCkDatasource") DataSource dataSource) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;

    }

}
