


CREATE TABLE `tb_etf_list` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(6) NOT NULL DEFAULT '0' COMMENT 'etf代码',
  `name` varchar(100) DEFAULT '0' COMMENT 'etf名称',
  `alias` varchar(255) DEFAULT NULL COMMENT '基金别名',
  `fund_size` decimal(10,3) DEFAULT '0.000' COMMENT '基金规模',
  `fund_manager` varchar(100) DEFAULT '0' COMMENT '基金经理',
  `fund_company` varchar(100) DEFAULT '0' COMMENT '基金公司',
  `create_time` int NOT NULL DEFAULT '0' COMMENT '成立日期',
  `update_time` int NOT NULL DEFAULT '0' COMMENT '基金规模更新日期',
  `format_time` int NOT NULL DEFAULT '0' COMMENT '净值更新日期',
  `index_target` varchar(100) DEFAULT '0' COMMENT '跟踪标地',
  `index_error` decimal(10,3) DEFAULT '0.000' COMMENT '跟踪误差',
  `price` decimal(8,4) DEFAULT '0.0000' COMMENT '基金价格',
  `price_change` decimal(8,4) DEFAULT '0.0000' COMMENT '价格变动',
  `rate_change` decimal(8,4) DEFAULT '0.0000' COMMENT '变动比例',
  `href` varchar(100) DEFAULT '0' COMMENT '访问链接',
  `stage_week1` decimal(10,3) DEFAULT '0.000' COMMENT '阶段涨幅-近一周',
  `stage_month1` decimal(10,3) DEFAULT '0.000' COMMENT '阶段涨幅-近一个月',
  `stage_month3` decimal(10,3) DEFAULT '0.000' COMMENT '阶段涨幅-近三个月',
  `stage_month6` decimal(10,3) DEFAULT '0.000' COMMENT '阶段涨幅-近六个月',
  `stage_year` decimal(10,3) DEFAULT '0.000' COMMENT '阶段涨幅-今年以来',
  `stage_year1` decimal(10,3) DEFAULT '0.000' COMMENT '阶段涨幅-近1年',
  `stage_year2` decimal(10,3) DEFAULT '0.000' COMMENT '阶段涨幅-近2年',
  `stage_year3` decimal(10,3) DEFAULT '0.000' COMMENT '阶段涨幅-近3年',
  `month_a` varchar(255)  DEFAULT NULL COMMENT '当月',
  `month_b` varchar(255)  DEFAULT NULL COMMENT '前一个月',
  `month_c` varchar(255)  DEFAULT NULL COMMENT '前2个月',
  `month_d` varchar(255)  DEFAULT NULL COMMENT '前3个月',
  `month_e` varchar(255)  DEFAULT NULL COMMENT '前4个月',
  `month_f` varchar(255)  DEFAULT NULL COMMENT '前5个月',
  `month_g` varchar(255)  DEFAULT NULL COMMENT '前6个月',
  `data_type` varchar(3)  DEFAULT '1' COMMENT '数据类型 1 etf 2 stock',
  `date_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `sharp_rate` decimal(18,3) DEFAULT NULL COMMENT '夏普比率',
  `stand_error` decimal(18,3) DEFAULT NULL COMMENT '标准差',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=38091 DEFAULT CHARSET=utf8mb4 COMMENT='etf 基金列表';