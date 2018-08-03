/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 examdb 的数据库结构
CREATE DATABASE IF NOT EXISTS `paper` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `paper`;


DROP TABLE IF EXISTS TB_EXAM_PAPER ;

CREATE TABLE IF NOT EXISTS TB_EXAM_PAPER (
  `ID` INT NOT NULL AUTO_INCREMENT, 
  `COL_PAPER_NAME` VARCHAR(45) NULL COMMENT '默认与考试名相同',
  `COL_PAPER_ID` INT NOT NULL COMMENT '试卷ID',
  `COL_PAPER_OWNER_ID` CHAR(9) NOT NULL COMMENT '试卷答题人，即普通评测人员ID',
  `COL_EXAM_ID` VARCHAR(45) NULL,
  `COL_SCORE` INT NULL,
  `COL_PAPER_STATUS` INT NOT NULL DEFAULT 0 COMMENT '0:未开始\n1:答题中\n2:已提交\n3:通过\n99:未通过',
  `COL_PAPER_STATUS_DESC` VARCHAR(45) NULL COMMENT '对于状态的一些描述',
  `COL_START_TIME` BIGINT(11) NULL,
  `COL_END_TIME` BIGINT(11) NULL,
  `COL_SUBMIT_TIME` BIGINT(11) NULL,
  `COL_IPADDR` VARCHAR(15) NULL,
  PRIMARY KEY (`ID`)
  )
 
ENGINE = INNODB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS TB_EXAM_PAPER_SUBJECT ;

CREATE TABLE IF NOT EXISTS TB_EXAM_PAPER_SUBJECT (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `COL_EXAM_PAPER_ID` INT NOT NULL,
  `COL_SUBJECT_ID` INT NOT NULL,
  `COL_SUBJECT_TYPE` INT NOT NULL COMMENT '与题目表TB_SUBJECT的SUBJECT_TYPE冗余，表示题目类型\n0:判断题\n1:单选题\n2:多选题',
  `COL_POINT` INT NOT NULL COMMENT '题目分值',
  `COL_SUBJECT_ANSWER` INT NOT NULL,
  `COL_SUBMIT_ANSWER` INT NULL COMMENT '普通评测用户做题答案',
  `COL_IS_CORRECT` INT NULL COMMENT '答案是否正确',
  `COL_SUBJECT_DETAIL` VARCHAR(1000) CHARACTER SET armscii8 NULL COMMENT '{\n    \"ID\":1,\n    \"SubjectContent\":\"华东地区诞生出以下哪种菜系\",\n    \"Items\":\n    [\n        {\n            \"ID\":1,\n            \"Order\":1,\n            \"Content\":\"四川菜系\",\n            \"DisplayOrder\":\"D\"\n        },\n        {\n            \"ID' /* comment truncated */ /*":2,
            "Order":2,
            "Content":"湖南菜系",
            "DisplayOrder":"C"
        },
        {
            "ID":3,
            "Order":4,
            "Content":"山东菜系",
            "DisplayOrder":"B"
        },
        {
            "ID":4,
            "Order":8,
            "Content":"淮阳菜系",
            "DisplayOrder":"A"
        }
    ]
}*/,

    PRIMARY KEY (`ID`)
)


ENGINE = INNODB DEFAULT CHARSET=utf8;