create database db_test1 engine = ORDINARY;

use db_test1;

CREATE TABLE db_test1.report_order
(

    `id` UInt16,

    `shop_id` String,

    `shop_name` String,

    `order_no` String,

    `order_amt` Float32,

    `create_date` Date
)
ENGINE = MergeTree(create_date,
 id,
 8192);