# 表结构
-- --------------------------------
-- Table structure for `user`
-- --------------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` varchar(64) NOT NULL COMMENT '唯一标识',
  `token` varchar(64) NOT NULL COMMENT 'token',
  `account` varchar(64) DEFAULT NULL COMMENT '账户',
  `password` varchar(128) DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(64) DEFAULT NULL COMMENT '昵称',
  `gender` varchar(64) DEFAULT NULL COMMENT '性别',
  `language` varchar(64) DEFAULT NULL COMMENT '语言',
  `city` varchar(64) DEFAULT NULL COMMENT '城市',
  `province` varchar(64) DEFAULT NULL COMMENT '省份',
  `country` varchar(64) DEFAULT NULL COMMENT '国家',
  `avatar_url` varchar(256) DEFAULT NULL COMMENT '头像地址',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `open_id` varchar(64) DEFAULT NULL COMMENT '微信:openId',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_token_code` (`token`,`code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci