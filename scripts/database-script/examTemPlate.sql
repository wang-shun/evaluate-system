/*
SQLyog ��ҵ�� - MySQL GUI v8.14 
MySQL - 5.5.60-0ubuntu0.14.04.1 : Database - examdb
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`examdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `examdb`;

/*Table structure for table `TB_EXAM_PAPER_TEMPLATE` */

DROP TABLE IF EXISTS `TB_EXAM_PAPER_TEMPLATE`;

CREATE TABLE `TB_EXAM_PAPER_TEMPLATE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COL_TEMPLATE_NAME` varchar(20) DEFAULT NULL COMMENT 'ģ������',
  `COL_CREATE_TIME` int(11) NOT NULL COMMENT '����ʱ��',
  `COL_CREATOR` varchar(10) NOT NULL COMMENT '������',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UDX_TEMPLATE_NAME` (`COL_TEMPLATE_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `TB_EXAM_PAPER_TEMPLATE` */

insert  into `TB_EXAM_PAPER_TEMPLATE`(`ID`,`COL_TEMPLATE_NAME`,`COL_CREATE_TIME`,`COL_CREATOR`) values (1,'��Ϣ��ȫģ��',2147483647,'test');

/*Table structure for table `TB_EXAM_PAPER_TEMPLATE_DETAIL` */

DROP TABLE IF EXISTS `TB_EXAM_PAPER_TEMPLATE_DETAIL`;

CREATE TABLE `TB_EXAM_PAPER_TEMPLATE_DETAIL` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COL_TEMPLATE_ID` int(11) DEFAULT NULL COMMENT 'ģ��ID',
  `COL_TEMPLATE_NAME` varchar(20) DEFAULT NULL COMMENT 'ģ������',
  `COL_SUBJECT_TYPE` int(11) NOT NULL COMMENT '��Ŀ����',
  `COL_SUBJECT_COUNT` int(11) NOT NULL COMMENT '��Ŀ����',
  `COL_DIFFICUTE_LEVEL` int(11) NOT NULL COMMENT '��Ŀ�Ѷ�',
  `COL_SUBJECT_POINT` int(11) NOT NULL COMMENT '��Ŀ����',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `TB_EXAM_PAPER_TEMPLATE_DETAIL` */

insert  into `TB_EXAM_PAPER_TEMPLATE_DETAIL`(`ID`,`COL_TEMPLATE_ID`,`COL_TEMPLATE_NAME`,`COL_SUBJECT_TYPE`,`COL_SUBJECT_COUNT`,`COL_DIFFICUTE_LEVEL`,`COL_SUBJECT_POINT`) values (1,1,'��Ϣ��ȫģ��',1,2,2,3),(2,1,'��Ϣ��ȫģ��',1,2,1,2),(3,1,'��Ϣ��ȫģ��',2,2,2,2),(4,1,'��Ϣ��ȫģ��',2,2,1,3);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;