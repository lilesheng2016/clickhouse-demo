package com.mangolost.ck.service;

import com.mangolost.ck.common.MyPage;
import com.mangolost.ck.entity.OrderData;
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
public class OrderService {

    @Autowired
    @Qualifier("testCkTemplate")
    private JdbcTemplate testCkTemplate;

    /**
     *
     * @return
     */
    public List<Map<String, Object>> getAll() {

        String sql = "select * from report_order";

        List<Map<String, Object>> list = testCkTemplate.queryForList(sql);

        return list;
    }

    /**
     *
     * @return
     */
    public MyPage<Map<String, Object>> pageAll(int pageNo, int pageSize) {

        MyPage<Map<String, Object>> myPage = new MyPage<>(pageNo, pageSize);

        String sqlCount = "select count(*) from report_order";
        int cnt = testCkTemplate.queryForObject(sqlCount, Integer.class);
        if (cnt > 0) {

            int totalCount = cnt;
            int totalPages = totalCount <= pageSize ? 1 : 1 + (totalCount - 1) / pageSize;

            String sqlData = "select * from report_order order by id desc limit ?,?";
            int offset = (pageNo - 1) * pageSize;

            List<Map<String, Object>> list = testCkTemplate.queryForList(sqlData, offset, pageSize);

            myPage.setTotalCount(totalCount);
            myPage.setTotalPages(totalPages);
            myPage.setRecords(list);

        }

        return myPage;
    }

    /**
     *
     * @param id
     * @return
     */
    public Map<String, Object> get(int id) {

        String sql = "select * from report_order where id = " + id + " limit 1";

        List<Map<String, Object>> list = testCkTemplate.queryForList(sql);
        if (list.size() > 0) {
            return list.get(0);
        }

        return null;
    }

    /**
     *
     * @param orderData
     */
    public Map<String, Object> add(OrderData orderData) {

        String sql = "insert into report_order (id, shop_id, shop_name, order_no, order_amt, create_date) values (?,?,?,?,?,?)";

        testCkTemplate.update(sql,
                orderData.getId(),
                orderData.getShop_id(),
                orderData.getShop_name(),
                orderData.getOrder_no(),
                orderData.getOrder_amt(),
                orderData.getCreate_date());

        return get(orderData.getId());
    }

    /**
     *
     * @param orderData
     */
    public Map<String, Object> update(OrderData orderData) {

        String sql = "alter table report_order update shop_id = ?, shop_name = ?, order_no = ?, order_amt = ? where id = ?";

        testCkTemplate.update(sql,
                orderData.getShop_id(),
                orderData.getShop_name(),
                orderData.getOrder_no(),
                orderData.getOrder_amt(),
                orderData.getId());
        return get(orderData.getId()); //注意，clickhouse的更新是异步的，可能这里查询的还是之前的记录状态
    }

    /**
     *
     * @param id
     */
    public void delete(int id) {

        String sql = "alter table report_order delete where id = ?";

        testCkTemplate.update(sql, id);
    }

}
