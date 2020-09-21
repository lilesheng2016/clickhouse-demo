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
    @Qualifier("test1CkReadTemplate")
    private JdbcTemplate test1CkReadTemplate;

    @Autowired
    @Qualifier("test1CkWriteTemplate")
    private JdbcTemplate test1CkWriteTemplate;

    /**
     *
     * @return
     */
    public List<Map<String, Object>> getAll() {

        String sql = "select * from report_order_distribute";

        List<Map<String, Object>> list = test1CkReadTemplate.queryForList(sql);

        return list;
    }

    /**
     *
     * @return
     */
    public MyPage<Map<String, Object>> pageAll(int pageNo, int pageSize) {

        MyPage<Map<String, Object>> myPage = new MyPage<>(pageNo, pageSize);

        String sqlCount = "select count(*) from report_order_distribute";
        int cnt = test1CkReadTemplate.queryForObject(sqlCount, Integer.class);
        if (cnt > 0) {

            int totalCount = cnt;
            int totalPages = totalCount <= pageSize ? 1 : 1 + (totalCount - 1) / pageSize;

            String sqlData = "select * from report_order_distribute order by id desc limit ?,?";
            int offset = (pageNo - 1) * pageSize;

            List<Map<String, Object>> list = test1CkReadTemplate.queryForList(sqlData, offset, pageSize);

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

        String sql = "select * from report_order_distribute where id = " + id + " limit 1";

        List<Map<String, Object>> list = test1CkReadTemplate.queryForList(sql);
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

        String sql = "insert into report_order_local (id, shop_id, shop_name, order_no, order_amt, create_date) values (?,?,?,?,?,?)";

        test1CkWriteTemplate.update(sql,
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

        String sql = "alter table report_order_distribute update shop_id = ?, shop_name = ?, order_no = ?, order_amt = ? where id = ?";

        test1CkWriteTemplate.update(sql,
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

        String sql = "alter table report_order_distribute delete where id = ?";

        test1CkWriteTemplate.update(sql, id);
    }

}
