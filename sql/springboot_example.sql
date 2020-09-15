## build database
DROP DATABASE If Exists `springboot_example`;
CREATE DATABASE If Not Exists  `springboot_example` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
use springboot_example;

CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL COMMENT '主键',
  `username` varchar(255) NOT NULL COMMENT '用户名称',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `salt` varchar(255) DEFAULT NULL COMMENT '盐值',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `cellphone` varchar(255) DEFAULT NULL COMMENT '电话号码',
  `id_card` varchar(18) NOT NULL COMMENT '身份证号码',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `age` int(11) NOT NULL COMMENT '年龄',
  `weight` decimal(10,0) DEFAULT NULL COMMENT '体重',
  `create_time` datetime NOT NULL default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tb_user_2` (`username`,`id_card`) USING BTREE,
  UNIQUE KEY `uk_tb_user_1` (`username`,`cellphone`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
