


DROP DATABASE If Exists `manage_db`;
CREATE DATABASE If Not Exists  `manage_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
use manage_db;


CREATE TABLE `tb_user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(255) NOT NULL COMMENT '用户名称',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `status` varchar(255) DEFAULT NULL COMMENT '状态 1 正常 0 禁用',
  `role` varchar(255) DEFAULT NULL COMMENT '角色',
  `cellphone` varchar(255) DEFAULT NULL COMMENT '电话号码',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4  COMMENT='用户表';


CREATE TABLE `tb_organization` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(255) NOT NULL COMMENT '组织名称',
  `status` varchar(255) DEFAULT NULL COMMENT '组织状态 1 正常 0 禁用',
  `address` varchar(255) DEFAULT NULL COMMENT '组织地址',
  `cellphone` varchar(255) DEFAULT NULL COMMENT '电话号码',
  `liaison` varchar(255) DEFAULT NULL COMMENT '联系人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4  COMMENT='党组织表';



CREATE TABLE `tb_party_member` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(255) NOT NULL COMMENT '党员名称',
  `status` varchar(255) DEFAULT NULL COMMENT '状态 1 正常 0 禁用',
  `address` varchar(255) DEFAULT NULL COMMENT '党员地址',
  `cellphone` varchar(255) DEFAULT NULL COMMENT '电话号码',
  `age` int DEFAULT NULL COMMENT '年龄',
  `introducer` varchar(255) DEFAULT NULL COMMENT '介绍人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4  COMMENT='党员信息表';


INSERT INTO `tb_user`(`id`, `username`, `password`, `status`, `role`, `cellphone`, `create_time`, `update_time`)
VALUES (10000, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '1', 'admin', '123456789', '2022-12-23 21:36:27', '2022-12-23 23:50:58');