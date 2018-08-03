-- --------------------------------------------------------
-- 主机:                           49.4.65.137
-- 服务器版本:                        5.5.60-0ubuntu0.14.04.1 - (Ubuntu)
-- 服务器操作系统:                      debian-linux-gnu
-- HeidiSQL 版本:                  8.3.0.4833
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 accountdb 的数据库结构
CREATE DATABASE IF NOT EXISTS `accountdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `accountdb`;


-- 导出  表 accountdb.tb_account 结构
CREATE TABLE IF NOT EXISTS `tb_account` (
  `ID` char(9) NOT NULL COMMENT '员工号',
  `COL_ACCOUNT_NAME` varchar(20) NOT NULL COMMENT '账号名',
  `COL_PASSOWRD` char(60) NOT NULL DEFAULT 'password(''123456'')' COMMENT '密码',
  `COL_ROLE_ID` int(2) DEFAULT NULL,
  `COL_FULL_NAME` varchar(60) CHARACTER SET utf8 NOT NULL COMMENT '姓名',
  `COL_MOBILE` varchar(11) DEFAULT NULL COMMENT '手机号',
  `COL_EMAIL` varchar(45) DEFAULT NULL COMMENT '邮箱',
  `COL_DEPARTMENT_ID` int(4) NOT NULL COMMENT '部门ID',
  `COL_LEVEL` varchar(10) NOT NULL COMMENT '员工职称',
  `COL_CREATE_TIME` bigint(20) NOT NULL COMMENT '创建时间',
  `COL_UPDATE_TIME` bigint(20) NOT NULL COMMENT '更新时间',
  `COL_CREATOR_ID` char(9) NOT NULL COMMENT '创建人ID',
  `COL_UPDATOR_ID` char(9) NOT NULL COMMENT '更新人ID',
  `COL_LOGIN_TIME` bigint(20) NOT NULL COMMENT '上次登录时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 数据导出被取消选择。


-- 导出  表 accountdb.tb_department 结构
CREATE TABLE IF NOT EXISTS `tb_department` (
  `ID` int(4) unsigned NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `COL_NAME` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '部门名称',
  `COL_DESC` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '部门描述',
  `COL_PARENT_ID` int(4) NOT NULL COMMENT '父部门ID',
  `COL_CREATE_TIME` bigint(20) NOT NULL COMMENT '创建时间',
  `COL_CREATEOR_ID` char(9) NOT NULL COMMENT '创建者ID',
  `COL_UPDATER_ID` char(9) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UDX_PARENT_ID_AND_NAME` (`COL_PARENT_ID`,`COL_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 数据导出被取消选择。


-- 导出  表 accountdb.tb_role 结构
CREATE TABLE IF NOT EXISTS `tb_role` (
  `ID` int(2) unsigned NOT NULL COMMENT '角色ID',
  `ROLE_DESC` varchar(15) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `tb_account` (`ID`,`COL_ACCOUNT_NAME`,`COL_PASSOWRD`,`COL_ROLE_ID`,`COL_FULL_NAME`,`COL_MOBILE`,`COL_EMAIL`,`COL_DEPARTMENT_ID`,`COL_LEVEL`,`COL_CREATE_TIME`,`COL_UPDATE_TIME`,`COL_CREATOR_ID`,`COL_UPDATOR_ID`,`COL_LOGIN_TIME`) VALUES ('admin','admin','*4ACFE3202A5FF5CF467898FC58AAB1D615029441',1,'admin user','18205083524','admin@hoperun.com',130,'0',1531986135817,1531986135817,'root','',1531986135817);
-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
