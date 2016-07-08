SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for capitalaccount
-- ----------------------------
DROP TABLE IF EXISTS `capitalaccount`;
CREATE TABLE `capitalaccount` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `balance` decimal(18,2) NOT NULL,
  `frozen` decimal(18,2) NOT NULL,
  `digest` varchar(40) NOT NULL,
  `version` bigint(20) unsigned DEFAULT '0',
  `lastUpdate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for capitaljournal
-- ----------------------------
DROP TABLE IF EXISTS `capitaljournal`;
CREATE TABLE `capitaljournal` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `amount` decimal(18,2) NOT NULL,
  `balance` decimal(18,2) NOT NULL,
  `bizType` tinyint(3) unsigned NOT NULL,
  `txnId` varchar(40) NOT NULL,
  `memo` varchar(50) NOT NULL,
  `digest` varchar(40) NOT NULL,
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_txnId_bizType` (`bizType`,`txnId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `mobile` varchar(100) NOT NULL,
  `email` varchar(150) NOT NULL,
  `loginPwd` varchar(100) NOT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_mobile` (`mobile`),
  UNIQUE KEY `uk_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_extra
-- ----------------------------
DROP TABLE IF EXISTS `user_extra`;
CREATE TABLE `user_extra` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `realName` varchar(100) NOT NULL,
  `idCardNo` varchar(100) NOT NULL,
  `idCardType` tinyint(4) NOT NULL,
  `paymentPwd` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_idCardNo` (`idCardNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;