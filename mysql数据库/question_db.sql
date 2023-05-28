/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : question_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2018-09-22 15:20:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL default '',
  `password` varchar(32) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `t_doctor`
-- ----------------------------
DROP TABLE IF EXISTS `t_doctor`;
CREATE TABLE `t_doctor` (
  `doctorNo` varchar(30) NOT NULL COMMENT 'doctorNo',
  `password` varchar(30) NOT NULL COMMENT '登录密码',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `gender` varchar(4) NOT NULL COMMENT '性别',
  `birthDate` varchar(20) default NULL COMMENT '出生日期',
  `doctorPhoto` varchar(60) NOT NULL COMMENT '医生照片',
  `zc` varchar(20) NOT NULL COMMENT '职称',
  `telephone` varchar(20) NOT NULL COMMENT '联系电话',
  `workYear` varchar(50) NOT NULL COMMENT '工作经验',
  `doctorDesc` varchar(8000) default NULL COMMENT '医生介绍',
  `regTime` varchar(20) default NULL COMMENT '入职时间',
  PRIMARY KEY  (`doctorNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_doctor
-- ----------------------------
INSERT INTO `t_doctor` VALUES ('YS001', '123', '张晓光', '男', '2018-09-12', 'upload/036f1c1c-304f-45cc-85ec-d0f82bd0b5b1.jpg', '副教授', '13989083423', '3年', '<p>一名多年经验的医生</p>', '2018-09-21 11:45:32');
INSERT INTO `t_doctor` VALUES ('YS002', '123', '李明月', '女', '2018-09-04', 'upload/12f0c528-b7e6-4ff8-ae65-622e504fb45c.jpg', '主任医师', '13598349834', '4年', '<p>测试医生</p>', '2018-09-22 14:55:46');

-- ----------------------------
-- Table structure for `t_notice`
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice` (
  `noticeId` int(11) NOT NULL auto_increment COMMENT '公告id',
  `title` varchar(80) NOT NULL COMMENT '标题',
  `content` varchar(5000) NOT NULL COMMENT '公告内容',
  `publishDate` varchar(20) default NULL COMMENT '发布时间',
  PRIMARY KEY  (`noticeId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_notice
-- ----------------------------
INSERT INTO `t_notice` VALUES ('1', '宠物答疑网站成立了', '<p>朋友们有关于宠物的任何问题都可以来这里提问，专业医生为你解答</p>', '2018-09-21 11:50:49');

-- ----------------------------
-- Table structure for `t_question`
-- ----------------------------
DROP TABLE IF EXISTS `t_question`;
CREATE TABLE `t_question` (
  `postInfoId` int(11) NOT NULL auto_increment COMMENT '帖子id',
  `title` varchar(80) NOT NULL COMMENT '问题标题',
  `questionClassObj` varchar(20) NOT NULL COMMENT '问题分类',
  `content` varchar(5000) NOT NULL COMMENT '问题内容',
  `hitNum` int(11) NOT NULL COMMENT '浏览量',
  `userObj` varchar(30) NOT NULL COMMENT '提问人',
  `addTime` varchar(20) default NULL COMMENT '提问时间',
  PRIMARY KEY  (`postInfoId`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_question_ibfk_1` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_question
-- ----------------------------
INSERT INTO `t_question` VALUES ('1', '狗狗不在吃饭的问题', '1', '<p>我家狗狗最近不爱吃饭是怎么啦？<br/><br/><img src=\"/JavaWebProject/upload/20180922/1537600563167048899.jpg\" title=\"1537600563167048899.jpg\" alt=\"1.jpg\" width=\"636\" height=\"437\"/></p>', '4', 'user1', '2018-09-21 11:48:45');
INSERT INTO `t_question` VALUES ('2', '猫猫不爱吃鱼是啥原因', '2', '<p>奇怪了我家的猫猫不爱吃鱼。。。</p>', '2', 'user1', '2018-09-22 12:26:51');

-- ----------------------------
-- Table structure for `t_questionclass`
-- ----------------------------
DROP TABLE IF EXISTS `t_questionclass`;
CREATE TABLE `t_questionclass` (
  `classId` int(11) NOT NULL auto_increment COMMENT '分类id',
  `className` varchar(20) NOT NULL COMMENT '分类名称',
  `classDesc` varchar(500) NOT NULL COMMENT '分类描述',
  PRIMARY KEY  (`classId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_questionclass
-- ----------------------------
INSERT INTO `t_questionclass` VALUES ('1', '宠物狗', '这里提问关于狗狗的问题');
INSERT INTO `t_questionclass` VALUES ('2', '宠物猫', '这里提问关于猫猫的问题');

-- ----------------------------
-- Table structure for `t_reply`
-- ----------------------------
DROP TABLE IF EXISTS `t_reply`;
CREATE TABLE `t_reply` (
  `replyId` int(11) NOT NULL auto_increment COMMENT '回复id',
  `postInfoObj` int(11) NOT NULL COMMENT '被回帖子',
  `content` varchar(2000) NOT NULL COMMENT '回复内容',
  `userObj` varchar(30) NOT NULL COMMENT '回复医生',
  `replyTime` varchar(20) default NULL COMMENT '回复时间',
  PRIMARY KEY  (`replyId`),
  KEY `postInfoObj` (`postInfoObj`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_reply_ibfk_1` FOREIGN KEY (`postInfoObj`) REFERENCES `t_question` (`postInfoId`),
  CONSTRAINT `t_reply_ibfk_2` FOREIGN KEY (`userObj`) REFERENCES `t_doctor` (`doctorNo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_reply
-- ----------------------------
INSERT INTO `t_reply` VALUES ('1', '1', '最近天气寒流冷空气重，是不是感冒了，有流鼻涕吗', 'YS001', '2018-09-21 11:48:54');
INSERT INTO `t_reply` VALUES ('2', '2', '你家猫从小吃猪肉多吧！', 'YS001', '2018-09-22 14:02:39');
INSERT INTO `t_reply` VALUES ('3', '2', '从来没吃过鱼，可能没记忆', 'YS002', '2018-09-22 14:56:11');

-- ----------------------------
-- Table structure for `t_userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_userinfo`;
CREATE TABLE `t_userinfo` (
  `user_name` varchar(30) NOT NULL COMMENT 'user_name',
  `password` varchar(30) NOT NULL COMMENT '登录密码',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `gender` varchar(4) NOT NULL COMMENT '性别',
  `birthDate` varchar(20) default NULL COMMENT '出生日期',
  `userPhoto` varchar(60) NOT NULL COMMENT '用户照片',
  `telephone` varchar(20) NOT NULL COMMENT '联系电话',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `address` varchar(80) default NULL COMMENT '家庭地址',
  `regTime` varchar(20) default NULL COMMENT '注册时间',
  PRIMARY KEY  (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_userinfo
-- ----------------------------
INSERT INTO `t_userinfo` VALUES ('user1', '123', '熊红', '女', '2018-09-05', 'upload/a902d242-728d-4f1d-b0f2-7b9eb417cab4.jpg', '13599809834', 'xiaofen@163.com', '四川成都成华区', '2018-09-21 11:44:49');
INSERT INTO `t_userinfo` VALUES ('user2', '123', '王展', '男', '2018-09-04', 'upload/7ba02f2e-8fb4-4ea3-b43f-1bf87c119e9f.jpg', '13980890834', 'wagnzhan@163.com', '四川南充滨江路', '2018-09-22 15:07:40');
