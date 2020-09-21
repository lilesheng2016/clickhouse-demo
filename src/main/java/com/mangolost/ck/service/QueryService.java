package com.mangolost.ck.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class QueryService {

    @Autowired
    @Qualifier("test1CkReadTemplate")
    private JdbcTemplate test1CkReadTemplate;

    /**
     *
     * @param sql
     * @return
     */
    public List<Map<String, Object>> query(String sql) {
        List<Map<String, Object>> list = test1CkReadTemplate.queryForList(sql);
        return list;
    }

}
