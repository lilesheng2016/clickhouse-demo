package com.mangolost.ck.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.clickhouse.BalancedClickhouseDataSource;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

/**
 *
 */
@Configuration
public class ClickHouseConfig {

    @Autowired
    private Test1CkReadDatasorceProperties test1CkReadDatasorceProperties;

    @Autowired
    private Test1CkWriteDatasorceProperties test1CkWriteDatasorceProperties;

    @Bean("test1CkReadDatasource")
    @Primary
    public DataSource test1CkReadDatasource() {

        ClickHouseProperties properties = new ClickHouseProperties();
        properties.setUser(test1CkReadDatasorceProperties.getUsername());
        properties.setPassword(test1CkReadDatasorceProperties.getPassword());
        properties.setDatabase(test1CkReadDatasorceProperties.getDatabase());
        BalancedClickhouseDataSource dataSource = new BalancedClickhouseDataSource(test1CkReadDatasorceProperties.getJdbcUrl(), properties);
        // 添加集群节点存活检查
        return dataSource.scheduleActualization(test1CkReadDatasorceProperties.getAliveTestInterval(), TimeUnit.SECONDS);
//        return dataSource;
    }
    @Bean("test1CkReadTemplate")
    @Primary
    public JdbcTemplate test1CkReadTemplate(@Qualifier("test1CkReadDatasource") DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }

    @Bean("test1CkWriteDatasource")
    public DataSource test1CkWriteDatasource(){
        ClickHouseProperties properties = new ClickHouseProperties();
        properties.setUser(test1CkWriteDatasorceProperties.getUsername());
        properties.setPassword(test1CkWriteDatasorceProperties.getPassword());
        properties.setDatabase(test1CkReadDatasorceProperties.getDatabase());
        BalancedClickhouseDataSource dataSource = new BalancedClickhouseDataSource(test1CkWriteDatasorceProperties.getJdbcUrl(), properties);
        // 添加集群节点存活检查
        return dataSource.scheduleActualization(test1CkWriteDatasorceProperties.getAliveTestInterval(), TimeUnit.SECONDS);
//        return dataSource;
    }
    @Bean("test1CkWriteTemplate")
    public JdbcTemplate test1CkWriteTemplate(@Qualifier("test1CkWriteDatasource") DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }

}
