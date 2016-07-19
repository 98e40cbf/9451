SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sms_record
-- ----------------------------
DROP TABLE IF EXISTS `sms_record`;
CREATE TABLE `sms_record` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `txnId` varchar(40) NOT NULL COMMENT '业务编号',
  `smsType` tinyint(4) unsigned NOT NULL COMMENT '短信类型',
  `smsStatus` tinyint(4) NOT NULL COMMENT '短信状态',
  `receiveMobiles` varchar(600) NOT NULL COMMENT '接收手机号码，用英文逗号隔开',
  `smsParam` varchar(100) NOT NULL COMMENT '短信模板变量',
  `smsTemplateCode` varchar(20) NOT NULL COMMENT '模板编号',
  `priority` tinyint(4) unsigned NOT NULL COMMENT '优先级',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_sms_txnId` (`txnId`),
  KEY `ind_sms_templateCode` (`smsTemplateCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信记录表';


-- ----------------------------
-- Table structure for sms_template
-- ----------------------------
DROP TABLE IF EXISTS `sms_template`;
CREATE TABLE `sms_template` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `templateCode` varchar(20) NOT NULL COMMENT '模板编号',
  `templateContent` varchar(200) NOT NULL COMMENT '模板内容',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_sms_template_code` (`templateCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信模板';


DROP TABLE IF EXISTS `sms_send_record`;
CREATE TABLE `sms_send_record` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `smsId` bigint(20) unsigned NOT NULL COMMENT '短信记录Id',
  `partnerType` tinyint(4) unsigned NOT NULL COMMENT '渠道类型',
  `smsStatus` tinyint(4) NOT NULL COMMENT '短信发送状态',
	`errorCode` varchar(20) DEFAULT NULL COMMENT '错误编号',
	`errorDetail` varchar(200) DEFAULT NULL COMMENT '错误信息',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_sms_send_smsId` (`smsId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信发送记录表';