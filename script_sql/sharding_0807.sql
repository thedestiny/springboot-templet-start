CREATE TABLE `t_goods_1` (
  `id` bigint NOT NULL COMMENT '主键',
  `goods_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品名称',
  `price` decimal(10,2) DEFAULT NULL COMMENT '价格',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `image` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '主图',
  `supplier` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '供应商',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='商品表';


CREATE TABLE `t_goods_2` (
  `id` bigint NOT NULL COMMENT '主键',
  `goods_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品名称',
  `price` decimal(10,2) DEFAULT NULL COMMENT '价格',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `image` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '主图',
  `supplier` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '供应商',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='商品表';


CREATE TABLE `t_goods_3` (
  `id` bigint NOT NULL COMMENT '主键',
  `goods_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品名称',
  `price` decimal(10,2) DEFAULT NULL COMMENT '价格',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `image` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '主图',
  `supplier` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '供应商',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='商品表';