/*
 Navicat Premium Data Transfer

 Source Server         : 阿里云
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : www.zzxblog.top:3306
 Source Schema         : student_course_system

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 08/06/2021 17:17:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for rc_admin
-- ----------------------------
DROP TABLE IF EXISTS `rc_admin`;
CREATE TABLE `rc_admin`  (
  `admin_id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '管理员Id',
  `admin_username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `admin_password` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `admin_privilege` int(0) NOT NULL COMMENT '角色\r\n二进制表示权限\r\n1-系管理\r\n2-专业管理\r\n4-班级管理\r\n8-学生管理\r\n16-教师管理\r\n32-课程管理\r\n64-选课管理\r\n128-管理员管理',
  PRIMARY KEY (`admin_id`) USING BTREE,
  UNIQUE INDEX `idx_admin_username`(`admin_username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rc_admin
-- ----------------------------
INSERT INTO `rc_admin` VALUES (29, 'zzx', 'd52799e8cb511886c0da63029f19b0d5', 511);
INSERT INTO `rc_admin` VALUES (30, 'bx', 'b1c93d23db2f4c5d0ad2d7a1cd3fc1bc', 383);
INSERT INTO `rc_admin` VALUES (31, 'cgq', '653c604490aa1697c3f326155748fdb2', 383);
INSERT INTO `rc_admin` VALUES (32, 'wk', '12e6ed564532261099d37ef2d215d548', 127);
INSERT INTO `rc_admin` VALUES (33, 'lw', 'f414c9c40b11ad50c6bb7cc702d59021', 127);
INSERT INTO `rc_admin` VALUES (34, 'z', '921e584424e21a2066b0bd247a87cab9', 1);
INSERT INTO `rc_admin` VALUES (35, 'tt', '2e86baf136607fc8f220c89e15a2f9c3', 55);
INSERT INTO `rc_admin` VALUES (36, 'yj', '0b69df6fffe6b3a5542fe8c590aaf8b7', 383);

-- ----------------------------
-- Table structure for rc_class
-- ----------------------------
DROP TABLE IF EXISTS `rc_class`;
CREATE TABLE `rc_class`  (
  `class_id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '班级Id',
  `class_major_id` int(0) UNSIGNED NOT NULL COMMENT '专业Id',
  `class_grade` int(0) UNSIGNED NOT NULL COMMENT '年级',
  `class_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '班级名称',
  PRIMARY KEY (`class_id`) USING BTREE,
  INDEX `fk_major_id`(`class_major_id`) USING BTREE,
  INDEX `idx_class_name`(`class_name`) USING BTREE,
  CONSTRAINT `fk_major_id` FOREIGN KEY (`class_major_id`) REFERENCES `rc_major` (`major_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rc_class
-- ----------------------------
INSERT INTO `rc_class` VALUES (1, 1, 2017, '计信1班');
INSERT INTO `rc_class` VALUES (2, 1, 2017, '计信2班');
INSERT INTO `rc_class` VALUES (3, 4, 2017, '数学1班');
INSERT INTO `rc_class` VALUES (24, 4, 2019, '测试1');

-- ----------------------------
-- Table structure for rc_course
-- ----------------------------
DROP TABLE IF EXISTS `rc_course`;
CREATE TABLE `rc_course`  (
  `course_id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '课程Id',
  `course_teacher_id` int(0) UNSIGNED NOT NULL COMMENT '授课教师Id',
  `course_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程名称',
  `course_grade` int(0) UNSIGNED NOT NULL COMMENT '授课年级',
  `course_time` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '上课时间 星期几-第几节-几节课',
  `course_location` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '上课地址',
  `course_credit` int(0) UNSIGNED NOT NULL COMMENT '学分',
  `course_selected_count` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '已选人数',
  `course_max_size` int(0) UNSIGNED NOT NULL COMMENT '最大容量',
  `course_exam_date` datetime(0) NULL DEFAULT NULL COMMENT '考试时间',
  `course_exam_location` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '考试地点',
  PRIMARY KEY (`course_id`) USING BTREE,
  INDEX `fk_course_teacher_id`(`course_teacher_id`) USING BTREE,
  INDEX `idx_course_name`(`course_name`) USING BTREE,
  CONSTRAINT `fk_course_teacher_id` FOREIGN KEY (`course_teacher_id`) REFERENCES `rc_teacher` (`teacher_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rc_course
-- ----------------------------
INSERT INTO `rc_course` VALUES (1, 1, 'C语言程序设计', 2017, '1-1-4', '信工楼107', 5, 18, 133, NULL, NULL);
INSERT INTO `rc_course` VALUES (2, 1, 'Java程序设计', 2017, '1-3-2', '信工楼106', 4, 3, 30, NULL, NULL);
INSERT INTO `rc_course` VALUES (3, 1, '数据库实用技术', 2017, '2-3-2', 'C区202', 2, 3, 50, NULL, NULL);
INSERT INTO `rc_course` VALUES (4, 1, 'ASP.Net开发', 2017, '5-5-3', 'E区315', 2, 1, 1, NULL, NULL);
INSERT INTO `rc_course` VALUES (5, 1, 'Spring企业级开发', 2017, '3-9-2', '信工楼101', 3, 3, 10, NULL, NULL);
INSERT INTO `rc_course` VALUES (6, 3, '数据库概论', 2017, '3-1-2', 'C区106', 5, 4, 40, NULL, NULL);
INSERT INTO `rc_course` VALUES (7, 3, 'Photoshop', 2017, '2-3-2', 'C区222', 2, 1, 20, NULL, NULL);
INSERT INTO `rc_course` VALUES (8, 4, '计算机网络', 2017, '4-1-3', '信工楼109', 5, 3, 20, NULL, NULL);
INSERT INTO `rc_course` VALUES (9, 1, 'aaa', 2017, '2-2-2', 'ssss', 2, 3, 50, '2021-02-27 00:00:00', 'ssss');
INSERT INTO `rc_course` VALUES (10, 10, '新课程', 2019, '1-2-2', 'ssss', 2, 0, 50, NULL, NULL);

-- ----------------------------
-- Table structure for rc_department
-- ----------------------------
DROP TABLE IF EXISTS `rc_department`;
CREATE TABLE `rc_department`  (
  `department_id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '系Id',
  `department_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '系名称',
  PRIMARY KEY (`department_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rc_department
-- ----------------------------
INSERT INTO `rc_department` VALUES (1, '计算机系');
INSERT INTO `rc_department` VALUES (2, '数学系');
INSERT INTO `rc_department` VALUES (3, '物理系');
INSERT INTO `rc_department` VALUES (4, '化学系');

-- ----------------------------
-- Table structure for rc_major
-- ----------------------------
DROP TABLE IF EXISTS `rc_major`;
CREATE TABLE `rc_major`  (
  `major_id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '专业Id',
  `major_department_id` int(0) UNSIGNED NOT NULL COMMENT '系Id',
  `major_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '专业名称',
  PRIMARY KEY (`major_id`) USING BTREE,
  INDEX `fk_major_department_id`(`major_department_id`) USING BTREE,
  INDEX `idx_major_name`(`major_name`) USING BTREE,
  CONSTRAINT `fk_major_department_id` FOREIGN KEY (`major_department_id`) REFERENCES `rc_department` (`department_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rc_major
-- ----------------------------
INSERT INTO `rc_major` VALUES (1, 1, '计算机科学与技术');
INSERT INTO `rc_major` VALUES (4, 2, '应用数学');
INSERT INTO `rc_major` VALUES (5, 2, '数学师范');
INSERT INTO `rc_major` VALUES (48, 4, '西药专业');

-- ----------------------------
-- Table structure for rc_student
-- ----------------------------
DROP TABLE IF EXISTS `rc_student`;
CREATE TABLE `rc_student`  (
  `student_id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '学生Id',
  `student_class_id` int(0) UNSIGNED NOT NULL COMMENT '班级Id',
  `student_number` char(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '学号',
  `student_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `student_password` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `student_email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `student_birthday` datetime(0) NULL DEFAULT NULL COMMENT '生日',
  `student_sex` tinyint(0) UNSIGNED NOT NULL COMMENT '性别',
  `student_last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最近登录时间',
  PRIMARY KEY (`student_id`) USING BTREE,
  UNIQUE INDEX `idx_student_number`(`student_number`) USING BTREE,
  INDEX `fk_student_class_id`(`student_class_id`) USING BTREE,
  INDEX `idx_student_name`(`student_name`) USING BTREE,
  CONSTRAINT `fk_student_class_id` FOREIGN KEY (`student_class_id`) REFERENCES `rc_class` (`class_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rc_student
-- ----------------------------
INSERT INTO `rc_student` VALUES (1, 1, '201711010001', '李雨轩', 'be089ccac5b3322ac6daf9b10e1ade0e', '961523404@qq.com', '1998-08-15 00:00:00', 1, '2021-04-03 14:59:21');
INSERT INTO `rc_student` VALUES (2, 1, '201711010002', '宋健', 'b67b6851ffe877a8df67de2add623d89', '495960666@qq.com', NULL, 1, '2021-03-18 17:10:15');
INSERT INTO `rc_student` VALUES (3, 1, '201711010003', '李同学1', '07bf47c692c12c91fedbfd08d90ba6ed', '', NULL, 0, '2021-03-18 17:29:10');
INSERT INTO `rc_student` VALUES (4, 1, '201711010004', '李同学2', '83d1962ed249b2d551b4a758a96b40b4', NULL, '2010-05-04 00:00:00', 0, '2021-05-30 14:02:22');
INSERT INTO `rc_student` VALUES (5, 1, '201711010005', '李同学3', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', NULL, '1998-11-01 00:00:00', 0, NULL);
INSERT INTO `rc_student` VALUES (6, 1, '201711010006', '李同学4', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', NULL, NULL, 0, NULL);
INSERT INTO `rc_student` VALUES (7, 1, '201711010007', '李同学5', '358834368611d58d4b8271e61f5abd00', NULL, '2021-03-01 00:00:00', 0, '2021-04-07 16:04:09');
INSERT INTO `rc_student` VALUES (8, 1, '201711010008', '李同学6', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', NULL, NULL, 0, NULL);
INSERT INTO `rc_student` VALUES (9, 1, '201711010009', '李同学7', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', NULL, NULL, 0, NULL);
INSERT INTO `rc_student` VALUES (10, 1, '201711010010', '李同学8', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', NULL, NULL, 0, NULL);
INSERT INTO `rc_student` VALUES (11, 1, '201711010011', '李同学9', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', NULL, NULL, 0, NULL);
INSERT INTO `rc_student` VALUES (12, 1, '201711010012', '张同学1', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', NULL, NULL, 1, NULL);
INSERT INTO `rc_student` VALUES (13, 1, '201711010013', '张同学2', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', NULL, NULL, 1, NULL);
INSERT INTO `rc_student` VALUES (14, 1, '201711010014', '张同学3', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', NULL, NULL, 1, NULL);
INSERT INTO `rc_student` VALUES (15, 1, '201711010015', '张同学4', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', NULL, NULL, 1, NULL);
INSERT INTO `rc_student` VALUES (16, 1, '201711010016', '张同学5', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', NULL, NULL, 1, NULL);
INSERT INTO `rc_student` VALUES (17, 1, '201711010017', '张同学6', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', NULL, NULL, 1, NULL);
INSERT INTO `rc_student` VALUES (18, 1, '201711010018', '张同学7', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', NULL, NULL, 1, NULL);
INSERT INTO `rc_student` VALUES (19, 3, '201711020001', '王同学1', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', NULL, NULL, 1, NULL);
INSERT INTO `rc_student` VALUES (20, 3, '201711020002', '王同学2', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', NULL, NULL, 1, NULL);
INSERT INTO `rc_student` VALUES (21, 3, '201711020003', '王同学3', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', NULL, NULL, 1, NULL);
INSERT INTO `rc_student` VALUES (22, 3, '201711020004', '王同学4', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', NULL, NULL, 1, NULL);
INSERT INTO `rc_student` VALUES (23, 3, '201711020005', '王同学5', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', NULL, NULL, 1, NULL);
INSERT INTO `rc_student` VALUES (24, 3, '201711020006', '王同学6', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', NULL, NULL, 1, NULL);
INSERT INTO `rc_student` VALUES (25, 3, '201711020007', '王同学7', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', NULL, NULL, 1, NULL);
INSERT INTO `rc_student` VALUES (26, 3, '201711020008', '王同学8', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', NULL, NULL, 1, NULL);
INSERT INTO `rc_student` VALUES (27, 3, '201711020009', '王同学9', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', NULL, NULL, 1, NULL);
INSERT INTO `rc_student` VALUES (28, 3, '201711020010', '王同学10', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', NULL, NULL, 1, NULL);
INSERT INTO `rc_student` VALUES (29, 1, '127856947111', '陈国庆', '100ca65068b0b4b40363f8b93a136904', NULL, NULL, 0, '2021-01-11 17:23:36');
INSERT INTO `rc_student` VALUES (30, 2, '123456789011', '张鑫鑫', 'f588244c75075f7793e45132ba5b50ec', '', '2021-01-03 00:00:00', 1, '2021-02-24 15:18:21');
INSERT INTO `rc_student` VALUES (31, 24, '201711020012', '张五', 'c0e8aef4dedb3627a3c8e49200b24bb3', NULL, NULL, 1, '2021-02-24 15:13:49');

-- ----------------------------
-- Table structure for rc_student_course
-- ----------------------------
DROP TABLE IF EXISTS `rc_student_course`;
CREATE TABLE `rc_student_course`  (
  `sc_id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '选课Id',
  `sc_student_id` int(0) UNSIGNED NOT NULL COMMENT '学生Id',
  `sc_course_id` int(0) UNSIGNED NOT NULL COMMENT '课程Id',
  `sc_daily_score` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '日常表现分',
  `sc_exam_score` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '期末测试分',
  `sc_score` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '总成绩',
  PRIMARY KEY (`sc_id`) USING BTREE,
  INDEX `fk_sc_course_id`(`sc_course_id`) USING BTREE,
  INDEX `fk_sc_student_id`(`sc_student_id`) USING BTREE,
  CONSTRAINT `fk_sc_course_id` FOREIGN KEY (`sc_course_id`) REFERENCES `rc_course` (`course_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_sc_student_id` FOREIGN KEY (`sc_student_id`) REFERENCES `rc_student` (`student_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rc_student_course
-- ----------------------------
INSERT INTO `rc_student_course` VALUES (2, 2, 1, 69, 69, 71);
INSERT INTO `rc_student_course` VALUES (3, 3, 1, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (5, 5, 1, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (6, 6, 1, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (8, 8, 1, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (9, 9, 1, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (10, 10, 1, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (11, 11, 1, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (12, 12, 1, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (13, 13, 1, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (14, 14, 1, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (15, 15, 1, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (16, 16, 1, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (17, 17, 1, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (18, 18, 1, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (19, 1, 3, 98, 100, 99);
INSERT INTO `rc_student_course` VALUES (20, 1, 4, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (21, 1, 5, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (24, 1, 6, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (25, 1, 1, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (26, 2, 9, 89, 80, 90);
INSERT INTO `rc_student_course` VALUES (27, 2, 8, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (28, 2, 7, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (29, 2, 6, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (30, 2, 5, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (31, 2, 2, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (33, 4, 2, 60, 60, 60);
INSERT INTO `rc_student_course` VALUES (34, 4, 3, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (35, 4, 5, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (36, 4, 6, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (37, 4, 8, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (38, 4, 9, 60, 80, 80);
INSERT INTO `rc_student_course` VALUES (40, 7, 3, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (42, 7, 6, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (43, 7, 8, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (44, 7, 9, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (45, 4, 1, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (46, 7, 1, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (47, 7, 2, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for rc_teacher
-- ----------------------------
DROP TABLE IF EXISTS `rc_teacher`;
CREATE TABLE `rc_teacher`  (
  `teacher_id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '教师Id',
  `teacher_department_id` int(0) UNSIGNED NOT NULL COMMENT '系Id',
  `teacher_number` char(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '工号',
  `teacher_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '教师姓名',
  `teacher_password` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  PRIMARY KEY (`teacher_id`) USING BTREE,
  UNIQUE INDEX `idx_teacher_number`(`teacher_number`) USING BTREE,
  INDEX `fk_teacher_department_id`(`teacher_department_id`) USING BTREE,
  CONSTRAINT `fk_teacher_department_id` FOREIGN KEY (`teacher_department_id`) REFERENCES `rc_department` (`department_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rc_teacher
-- ----------------------------
INSERT INTO `rc_teacher` VALUES (1, 1, '201711010001', '张三', 'cf8b09f68bad23f2916a0cad839ca926');
INSERT INTO `rc_teacher` VALUES (2, 3, '201711020001', '宋老师', '81a5f5a9bfde4cdcb5b9fe1f8508df2a');
INSERT INTO `rc_teacher` VALUES (3, 1, '201711010002', '宋老师', '81a5f5a9bfde4cdcb5b9fe1f8508df2a');
INSERT INTO `rc_teacher` VALUES (4, 1, '201711010003', '张老师', '81a5f5a9bfde4cdcb5b9fe1f8508df2a');
INSERT INTO `rc_teacher` VALUES (5, 1, '201711010004', '吕老师', '81a5f5a9bfde4cdcb5b9fe1f8508df2a');
INSERT INTO `rc_teacher` VALUES (6, 1, '201711010005', '王老师', '81a5f5a9bfde4cdcb5b9fe1f8508df2a');
INSERT INTO `rc_teacher` VALUES (7, 1, '201711010006', '丁老师', '81a5f5a9bfde4cdcb5b9fe1f8508df2a');
INSERT INTO `rc_teacher` VALUES (8, 1, '201711010007', '高老师', '81a5f5a9bfde4cdcb5b9fe1f8508df2a');
INSERT INTO `rc_teacher` VALUES (9, 1, '201711010008', '赵老师', '81a5f5a9bfde4cdcb5b9fe1f8508df2a');
INSERT INTO `rc_teacher` VALUES (10, 2, '201711010009', '新老师', '5b496bc01eb64b0f8fc1998419a06903');

SET FOREIGN_KEY_CHECKS = 1;
