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
  `version` bigint(20) unsigned NOT NULL,
  `lastUpdate` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_userId` (`userId`)
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
  `direction` tinyint(3) unsigned NOT NULL,
  `txnId` varchar(40) NOT NULL,
  `memo` varchar(50) NOT NULL,
  `digest` varchar(40) NOT NULL,
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_userId_bizType_txnId` (`userId`,`bizType`,`txnId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user_base`;
CREATE TABLE `user_base` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `mobile` varchar(100) NOT NULL,
  `email` varchar(150) NOT NULL,
  `loginPwd` varchar(100) NOT NULL,
  `paymentPwd` varchar(100) NOT NULL,
  `createTime` datetime NOT NULL,
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
  `idCardType` tinyint(3) unsigned NOT NULL,
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_idCardNo` (`idCardNo`),
  UNIQUE KEY `uk_userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_login_history
-- ----------------------------
DROP TABLE IF EXISTS `login_history`;
DROP TABLE IF EXISTS `user_login_history`;
CREATE TABLE `user_login_history` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `loginIp` bigint(20) NOT NULL,
  `loginTime` datetime NOT NULL,
  `device` tinyint(3) NOT NULL,
  `browser` varchar(150) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for capital_operation_history
-- ----------------------------
DROP TABLE IF EXISTS `capital_operation_history`;
CREATE TABLE `capital_operation_history` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `amount` decimal(18,2) NOT NULL,
  `txnId` varchar(40) NOT NULL,
  `bizType` tinyint(3) unsigned NOT NULL,
  `memo` varchar(50) NOT NULL,
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bookkeeping
-- ----------------------------
DROP TABLE IF EXISTS `bookkeeping`;
CREATE TABLE `bookkeeping` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `recharge` decimal(18,2) NOT NULL,
  `invest` decimal(18,2) NOT NULL,
  `withdraw` decimal(18,2) NOT NULL,
  `lastUpdate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bookkeeping
-- ----------------------------
INSERT INTO `bookkeeping` VALUES (1, 0.00, 0.00, 0.00, '2016-07-01 00:00:00');

-- ----------------------------
-- Table structure for mobile_change_history
-- ----------------------------
DROP TABLE IF EXISTS `mobile_change_history`;
CREATE TABLE `mobile_change_history` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `source` varchar(100) NOT NULL,
  `target` varchar(100) NOT NULL,
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

