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

-- 导出 examdb 的数据库结构
CREATE DATABASE IF NOT EXISTS `examdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `examdb`;


-- 导出  表 examdb.tb_exam 结构
CREATE TABLE IF NOT EXISTS `tb_exam` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COL_EXAM_NAME` varchar(45) NOT NULL COMMENT '考试名称',
  `COL_EXAM_DESC` varchar(1000) DEFAULT NULL COMMENT '描述',
  `COL_SUBJECT_DB_ID` int(11) NOT NULL COMMENT '关联题库ID',
  `COL_OPEN_TIME` bigint(20) NOT NULL COMMENT '开放考试时间',
  `COL_CLOSE_TIME` bigint(20) NOT NULL COMMENT '关闭考试时间',
  `COL_TOTAL_SCORE` int(11) NOT NULL COMMENT '卷面总分',
  `COL_PASS_SCORE` int(11) NOT NULL COMMENT '通过分数',
  `COL_DURATION` int(11) NOT NULL COMMENT '考试时长',
  `COL_LIMIT_COUNT` int(11) NOT NULL DEFAULT '1' COMMENT '限制的考试次数',
  `COL_PUBLISH` int(1) NOT NULL DEFAULT '0' COMMENT '发布状态\n0:未发布\n1:发布',
  `COL_CREATE_TIME` bigint(20) NOT NULL COMMENT '创建时间',
  `COL_UPDATE_TIME` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `COL_CREATOR_ID` char(9) NOT NULL COMMENT '创建人',
  `COL_UPDATE_ID` char(9) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 数据导出被取消选择。


-- 导出  表 examdb.tb_exam_account 结构
CREATE TABLE IF NOT EXISTS `tb_exam_account` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COL_EXAM_ID` int(11) NOT NULL,
  `COL_ACCOUNT_ID` char(9) NOT NULL,
  `COL_PARER_COUNT` int(11) NOT NULL DEFAULT '0',
  `COL_LIMIT_COUNT` int(11) DEFAULT NULL,
  `COL_IS_PASS` int(11) NOT NULL DEFAULT '0' COMMENT '0:表示未通过\n1:表示通过',
  `COL_CREATOR_ID` char(9) NOT NULL,
  `COL_CREATE_TIME` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 数据导出被取消选择。


-- 导出  表 examdb.tb_exam_struct 结构
CREATE TABLE IF NOT EXISTS `tb_exam_struct` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COL_EXAM_ID` int(11) NOT NULL COMMENT '考试ID',
  `COL_SUBJECT_TYPE` int(11) NOT NULL COMMENT '试题类型\n0:判断题\n1:单选题\n2:多选题',
  `COL_SUBJECT_COUNT` int(11) NOT NULL COMMENT '同一试卷下同一题型的数量',
  `COL_SUBJECT_POINT` int(11) NOT NULL COMMENT '同一试卷下同一题型每道题的分值',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
