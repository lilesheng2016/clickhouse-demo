create database IF NOT EXISTS db_test1 on cluster report_shards_replicas engine = Ordinary;

use db_test1;

CREATE TABLE IF NOT EXISTS db_test1.report_order_local on cluster report_shards_replicas
(

    `id` UInt16,

    `shop_id` String,

    `shop_name` String,

    `order_no` String,

    `order_amt` Float32,

    `create_date` Date
)
ENGINE = ReplicatedMergeTree(
             '/clickhouse/tables/report_shards_replicas/shard',
             '{replica}')
ORDER BY create_date
SETTINGS index_granularity = 8192;

CREATE TABLE if not exists db_test1.report_order_distribute ON CLUSTER report_shards_replicas
as db_test1.report_order_local
ENGINE = Distributed(report_shards_replicas, db_test1, report_order_local, rand());