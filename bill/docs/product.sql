
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '产品ID',
  `productName` varchar(200) DEFAULT NULL COMMENT '产品名称',
  `amount` decimal(18,4) DEFAULT NULL COMMENT '产品总额',
  `annualRate` decimal(18,2) DEFAULT NULL COMMENT '年利率',
  `surplusAmount` decimal(18,4) DEFAULT NULL COMMENT '剩余可投金额',
  `investTotalAmount` decimal(18,4) DEFAULT NULL COMMENT '已投总金额',
  `investSchedule` decimal(18,2) DEFAULT NULL COMMENT '投资进度',
  `deadline` int(11) DEFAULT NULL COMMENT '借款期限 单位为：天',
  `status` int(11) DEFAULT '1' COMMENT '借款状态(0初始化，1 倒计时中 2 招标中 )',
  `repaymentType` int(11) DEFAULT NULL COMMENT '还款类型',
  `productType` int(2) DEFAULT '1' COMMENT '标类型：1-散标',
  `productArea` int(2) DEFAULT '1' COMMENT '指定专区,1默认,2新客',
  `productNo` varchar(200) DEFAULT NULL COMMENT '产品编号（票据编号）',
  `valueDate` datetime DEFAULT NULL COMMENT '起息日',
  `auditDate` date DEFAULT NULL COMMENT '审核通过日期',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  `fullDate` datetime DEFAULT NULL COMMENT '满标时间，标投满时更新此时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品信息';



DROP TABLE IF EXISTS `invest`;
CREATE TABLE `invest` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `investAmount` decimal(18,4) DEFAULT '0.00' COMMENT '投资金额',
  `userId` bigint(20) DEFAULT NULL COMMENT '投资人',
  `userName` varchar(128) DEFAULT NULL COMMENT '用户名',
  `productId` bigint(20) DEFAULT NULL COMMENT '产品ID',
  `investDate` datetime DEFAULT NULL COMMENT '投资时间',
  `hasPI` decimal(18,4) DEFAULT '0.00' COMMENT '已收本息',
  `deadline` int(11) DEFAULT '0' COMMENT '期数',
  `hasDeadline` int(11) DEFAULT '0' COMMENT '已还款期数',
  `recivedPrincipal` decimal(18,4) unsigned DEFAULT '0.00' COMMENT '应收本金',
  `recievedInterest` decimal(18,4) DEFAULT '0.00' COMMENT '应收利息',
  `hasPrincipal` decimal(18,4) DEFAULT '0.00' COMMENT '已收本金',
  `hasInterest` decimal(18,4) DEFAULT '0.00' COMMENT '已收利息',
  `investStatus` int(11) DEFAULT '2' COMMENT '还款状态（2 默认投资中 3 满标 4已还款<状态值尽量保持和产品状态值一样>）',
  `orderNo` varchar(100) NOT NULL DEFAULT '' COMMENT '投资订单号',
  `platform` int(2) DEFAULT NULL COMMENT '平台：1PC,2Anroid App,3IOS App,4微信,8其他',
  `clientVersion` varchar(512) DEFAULT NULL COMMENT '客户端版本号，例如：Firefox 34.0.5，Android4.3',
  `productName` varchar(128) DEFAULT NULL COMMENT '产品名称：对应产品表productName',
  `productType` int(2) DEFAULT NULL COMMENT '产品类型：1-散标',
  `repaymentType` int(2) DEFAULT NULL COMMENT '还款方式()',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='投资信息';