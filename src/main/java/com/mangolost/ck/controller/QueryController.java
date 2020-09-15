package com.mangolost.ck.controller;

import com.mangolost.ck.common.CommonResult;
import com.mangolost.ck.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 *
 */
@RestController
@RequestMapping("/api/query")
public class QueryController {

    @Autowired
    private QueryService queryService;

    /**
     *
     * @param sql
     * @return
     */
    @PostMapping("querydata")
    public CommonResult queryData(@RequestParam String sql) {
        CommonResult commonResult = new CommonResult();
        List<Map<String, Object>> list = queryService.query(sql);
        return commonResult.setData(list);
    }

}
