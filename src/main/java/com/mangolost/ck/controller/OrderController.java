package com.mangolost.ck.controller;

import com.mangolost.ck.common.CommonResult;
import com.mangolost.ck.common.MyPage;
import com.mangolost.ck.entity.OrderData;
import com.mangolost.ck.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     *
     * @return
     */
    @RequestMapping("getall")
    public CommonResult getAll() {
        CommonResult commonResult = new CommonResult();
        List<Map<String, Object>> list = orderService.getAll();
        return commonResult.setData(list);
    }

    /**
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping("pageall")
    public CommonResult pageAll(@RequestParam int pageNo, @RequestParam int pageSize) {
        CommonResult commonResult = new CommonResult();
        MyPage<Map<String, Object>> myPage = orderService.pageAll(pageNo, pageSize);
        return commonResult.setData(myPage);
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping("get")
    public CommonResult get(@RequestParam int id) {
        CommonResult commonResult = new CommonResult();
        Map<String, Object> map = orderService.get(id);
        return commonResult.setData(map);
    }

    /**
     *
     * @param orderData
     * @return
     */
    @PostMapping("add")
    public CommonResult add(@RequestBody OrderData orderData) {
        CommonResult commonResult = new CommonResult();
        Map<String, Object> map = orderService.add(orderData);
        return commonResult.setData(map);
    }

    /**
     *
     * @param orderData
     * @return
     */
    @PostMapping("update")
    public CommonResult update(@RequestBody OrderData orderData) {
        CommonResult commonResult = new CommonResult();
        Map<String, Object> map = orderService.update(orderData);
        return commonResult.setData(map);
    }

    /**
     *
     * @param id
     * @return
     */
    @PostMapping("delete")
    public CommonResult delete(@RequestParam int id) {
        CommonResult commonResult = new CommonResult();
        orderService.delete(id);
        return commonResult.setData(null);
    }

}
