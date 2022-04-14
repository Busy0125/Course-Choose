/*
 Navicat Premium Data Transfer

 Source Server         : NavicatConnectMySQL
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : course_choose

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 13/04/2022 18:30:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for classes
-- ----------------------------
DROP TABLE IF EXISTS `classes`;
CREATE TABLE `classes`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `term` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `course_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `course_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `teacher_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `teacher_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of classes
-- ----------------------------
INSERT INTO `classes` VALUES ('2001', '19年秋', '08305138', '面向对象程序设计', '101', '吴基长', '一1-2');
INSERT INTO `classes` VALUES ('2002', '19年秋', '08305138', '面向对象程序设计', '101', '吴基长', '一7-8');
INSERT INTO `classes` VALUES ('2003', '19年秋', '08305139', '数字逻辑', '122', '哔哩哔哩弹幕网', '二3-4');
INSERT INTO `classes` VALUES ('2004', '19年秋', '08305139', '数字逻辑', '122', '哔哩哔哩弹幕网', '四3-4');
INSERT INTO `classes` VALUES ('2005', '19年秋', '08305072', '数字逻辑实验', '126', '一只小仙若', '三9-10');
INSERT INTO `classes` VALUES ('2006', '19年秋', '08305072', '数字逻辑实验', '126', '一只小仙若', '五9-10');
INSERT INTO `classes` VALUES ('2007', '19年秋', '08305072', '数字逻辑实验', '122', '哔哩哔哩弹幕网', '三9-10');
INSERT INTO `classes` VALUES ('2008', '19年秋', '08305072', '数字逻辑实验', '122', '哔哩哔哩弹幕网', '五9-10');
INSERT INTO `classes` VALUES ('2009', '19年冬', '08305140', '离散数学', '130', '视角姬', '一1-2');
INSERT INTO `classes` VALUES ('2010', '19年冬', '08305140', '离散数学', '130', '视角姬', '三1-2');
INSERT INTO `classes` VALUES ('2011', '19年冬', '08305140', '离散数学', '101', '吴基长', '一1-2');
INSERT INTO `classes` VALUES ('2012', '19年冬', '08305140', '离散数学', '101', '吴基长', '三1-2');
INSERT INTO `classes` VALUES ('2013', '19年冬', '08305009', '数据结构', '151', '黑马程序员', '二7-8');
INSERT INTO `classes` VALUES ('2014', '19年冬', '08305009', '数据结构', '151', '黑马程序员', '四7-8');
INSERT INTO `classes` VALUES ('2015', '19年春', '08305073', '计算机组成原理', '105', '毕导THU', '一5-6');
INSERT INTO `classes` VALUES ('2016', '19年春', '08305073', '计算机组成原理', '105', '毕导THU', '三5-6');
INSERT INTO `classes` VALUES ('2017', '19年春', '08305073', '计算机组成原理', '126', '一只小仙若', '一5-6');
INSERT INTO `classes` VALUES ('2018', '19年春', '08305073', '计算机组成原理', '126', '一只小仙若', '三5-6');
INSERT INTO `classes` VALUES ('2019', '19年春', '08305075', '计算机组成原理实验', '106', '宋浩老师官方', '一1-2');
INSERT INTO `classes` VALUES ('2022', '19年春', '08305075', '计算机组成原理实验', '106', '宋浩老师官方', '一3-4');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '课号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '课名',
  `credit` int NULL DEFAULT NULL COMMENT '学分',
  `department` int NULL DEFAULT NULL COMMENT '院系号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('01013010', '线性代数', 3, 3);
INSERT INTO `course` VALUES ('01014017', '概率论与数理统计', 3, 3);
INSERT INTO `course` VALUES ('02075003', '现代汉语', 6, 2);
INSERT INTO `course` VALUES ('02075032', '中国文学批评史', 6, 2);
INSERT INTO `course` VALUES ('02075052', '文学概论', 3, 2);
INSERT INTO `course` VALUES ('02075053', '语言学概论', 3, 2);
INSERT INTO `course` VALUES ('02075133', '中国古典文献学', 3, 2);
INSERT INTO `course` VALUES ('02075271', '中国当代文学', 6, 2);
INSERT INTO `course` VALUES ('02075363', '比较文学', 3, 2);
INSERT INTO `course` VALUES ('02075432', '中国古代文学A', 9, 2);
INSERT INTO `course` VALUES ('02075435', '中国古代文学B', 6, 2);
INSERT INTO `course` VALUES ('02075437', '中国现代文学', 6, 2);
INSERT INTO `course` VALUES ('02075439', '外国文学', 9, 2);
INSERT INTO `course` VALUES ('02075442', '古代汉语', 9, 2);
INSERT INTO `course` VALUES ('02076273', '西方文论', 6, 2);
INSERT INTO `course` VALUES ('02076331', '创意写作', 3, 2);
INSERT INTO `course` VALUES ('04105083', '经管文献检索', 3, 3);
INSERT INTO `course` VALUES ('04135004', '经济法', 4, 3);
INSERT INTO `course` VALUES ('04135025', '世界经济', 3, 3);
INSERT INTO `course` VALUES ('04135061', '公共经济学', 3, 3);
INSERT INTO `course` VALUES ('04136012', '产业经济学', 3, 3);
INSERT INTO `course` VALUES ('04145001', '货币银行学', 4, 3);
INSERT INTO `course` VALUES ('04145002', '国际金融', 4, 3);
INSERT INTO `course` VALUES ('04145003', '统计学', 5, 3);
INSERT INTO `course` VALUES ('04145011', '计量经济学', 3, 3);
INSERT INTO `course` VALUES ('04155001', '国际贸易原理', 4, 3);
INSERT INTO `course` VALUES ('04156003', '国际贸易实务', 4, 3);
INSERT INTO `course` VALUES ('04165002', '税收学', 4, 3);
INSERT INTO `course` VALUES ('04205018', '基础会计', 4, 3);
INSERT INTO `course` VALUES ('06265051', '民事诉讼法', 6, 5);
INSERT INTO `course` VALUES ('06265072', '行政法与行政诉讼法', 6, 5);
INSERT INTO `course` VALUES ('06265136', '法理学', 8, 5);
INSERT INTO `course` VALUES ('06265171', '中国法律史', 5, 5);
INSERT INTO `course` VALUES ('06265172', '宪法学', 5, 5);
INSERT INTO `course` VALUES ('06265173', '民法', 13, 5);
INSERT INTO `course` VALUES ('06265176', '刑法', 13, 5);
INSERT INTO `course` VALUES ('06265178', '刑事诉讼法', 6, 5);
INSERT INTO `course` VALUES ('06265179', '国际法', 5, 5);
INSERT INTO `course` VALUES ('06265194', '习近平法治思想概论', 2, 5);
INSERT INTO `course` VALUES ('06266170', '法律职业伦理', 2, 5);
INSERT INTO `course` VALUES ('08305009', '数据结构', 8, 1);
INSERT INTO `course` VALUES ('08305011', '操作系统', 8, 1);
INSERT INTO `course` VALUES ('08305014', '数据库原理', 7, 1);
INSERT INTO `course` VALUES ('08305016', '软件工程', 4, 1);
INSERT INTO `course` VALUES ('08305072', '数字逻辑实验', 1, 1);
INSERT INTO `course` VALUES ('08305073', '计算机组成原理', 6, 1);
INSERT INTO `course` VALUES ('08305075', '计算机组成原理实验', 2, 1);
INSERT INTO `course` VALUES ('08305138', '面向对象程序设计', 4, 1);
INSERT INTO `course` VALUES ('08305139', '数字逻辑', 4, 1);
INSERT INTO `course` VALUES ('08305140', '离散数学', 6, 1);
INSERT INTO `course` VALUES ('08305142', '计算机网络', 5, 1);
INSERT INTO `course` VALUES ('08305144', '编译原理', 4, 1);
INSERT INTO `course` VALUES ('14765010', '原动画设计', 8, 4);
INSERT INTO `course` VALUES ('14765015', '剪辑艺术与实践', 4, 4);
INSERT INTO `course` VALUES ('14765017', '定格动画基础', 4, 4);
INSERT INTO `course` VALUES ('14765018', '数字合成技术基础', 4, 4);
INSERT INTO `course` VALUES ('14765024', '动画表演', 4, 4);
INSERT INTO `course` VALUES ('14765038', '艺术概论', 2, 4);
INSERT INTO `course` VALUES ('14765039', '动画概论', 3, 4);
INSERT INTO `course` VALUES ('14765041', '动画导演', 4, 4);
INSERT INTO `course` VALUES ('14765042', '分镜头脚本设计', 4, 4);
INSERT INTO `course` VALUES ('14765043', '动画角色设计', 6, 4);
INSERT INTO `course` VALUES ('14765045', '动画场景设计', 6, 4);
INSERT INTO `course` VALUES ('14765064', '视听语言基础', 2, 4);
INSERT INTO `course` VALUES ('14765065', '设计构成基础', 2, 4);
INSERT INTO `course` VALUES ('14765066', '动画数字技术基础', 4, 4);
INSERT INTO `course` VALUES ('14765068', '动画原理与规律', 2, 4);
INSERT INTO `course` VALUES ('14765069', '数字三维动画基础', 8, 4);
INSERT INTO `course` VALUES ('14765071', '电影声音设计与制作', 4, 4);
INSERT INTO `course` VALUES ('14765072', '游戏设计基础', 2, 4);
INSERT INTO `course` VALUES ('14765073', '动画故事写作基础', 2, 4);

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `department` int NOT NULL COMMENT '院系号',
  `department_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '院名',
  PRIMARY KEY (`department`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES (1, '计算机学院');
INSERT INTO `department` VALUES (2, '文学院');
INSERT INTO `department` VALUES (3, '经济学院');
INSERT INTO `department` VALUES (4, '电影学院');
INSERT INTO `department` VALUES (5, '法学院');

-- ----------------------------
-- Table structure for grade
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `student_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `term` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `course_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `teacher_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `usual_grade` int NULL DEFAULT NULL,
  `final_grade` int NULL DEFAULT NULL,
  `total_grade` int NULL DEFAULT NULL,
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 82 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of grade
-- ----------------------------
INSERT INTO `grade` VALUES (106, '19122169', '19年秋', '08305139', '122', 89, 76, 80, '二3-4');
INSERT INTO `grade` VALUES (107, '19122169', '19年秋', '08305072', '126', 93, 86, 89, '五9-10');
INSERT INTO `grade` VALUES (109, '19122169', '19年冬', '08305009', '151', 0, 0, 0, '二7-8');
INSERT INTO `grade` VALUES (110, '19122169', '19年春', '08305073', '105', 0, 0, 0, '三5-6');
INSERT INTO `grade` VALUES (111, '19122169', '19年春', '08305075', '106', 0, 0, 0, '一3-4');
INSERT INTO `grade` VALUES (112, '19122169', '19年秋', '08305138', '101', 93, 82, 86, '一1-2');
INSERT INTO `grade` VALUES (113, '19122170', '19年秋', '08305138', '101', 0, 0, 0, '一1-2');
INSERT INTO `grade` VALUES (114, '19122170', '19年秋', '08305139', '122', 0, 0, 0, '二3-4');
INSERT INTO `grade` VALUES (115, '19122170', '19年秋', '08305072', '126', 90, 98, 95, '五9-10');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '学号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `department` int NULL DEFAULT NULL COMMENT '院系号',
  `gpa` double NULL DEFAULT NULL COMMENT '绩点',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('19122169', '19122169', '卜晟原', '男', 1, 0.42);
INSERT INTO `student` VALUES ('19122170', '19122170', '绵羊料理', '女', 1, 0);
INSERT INTO `student` VALUES ('19122171', '19122171', '小潮院长', '男', 4, 0);
INSERT INTO `student` VALUES ('19122172', '19122172', '宝剑嫂', '女', 3, 0);
INSERT INTO `student` VALUES ('19122173', '19122173', '力元君', '男', 3, 0);
INSERT INTO `student` VALUES ('19122174', '19122174', '雨哥到处跑', '男', 4, 0);
INSERT INTO `student` VALUES ('19122175', '19122175', '口袋迷ag', '男', 2, 0);
INSERT INTO `student` VALUES ('19122176', '19122176', '二喵的饭', '女', 1, 0);
INSERT INTO `student` VALUES ('19122177', '19122177', '狂阿弥_', '男', 4, 0);
INSERT INTO `student` VALUES ('19122178', '19122178', '酸梨大王', '女', 5, 0);
INSERT INTO `student` VALUES ('19122179', '19122179', 'Liyuu_', '女', 4, 0);
INSERT INTO `student` VALUES ('19122180', '19122180', '嘉然今天吃什么', '女', 1, 0);
INSERT INTO `student` VALUES ('19122181', '19122181', '自来卷三木', '男', 5, 0);
INSERT INTO `student` VALUES ('19122182', '19122182', '小片片说大片', '男', 4, 0);
INSERT INTO `student` VALUES ('19122183', '19122183', '假美食po主', '女', 5, 0);
INSERT INTO `student` VALUES ('19122184', '19122184', '吃花椒的喵酱', '女', 2, 0);
INSERT INTO `student` VALUES ('19122185', '19122185', '-欣小萌-', '女', 2, 0);
INSERT INTO `student` VALUES ('19122186', '19122186', '杜海皇', '男', 4, 0);
INSERT INTO `student` VALUES ('19122187', '19122187', '东尼ookii', '男', 3, 0);
INSERT INTO `student` VALUES ('19122188', '19122188', '醋醋cucu', '女', 1, 0);
INSERT INTO `student` VALUES ('19122189', '19122189', '伢伢gagako', '女', 1, 0);
INSERT INTO `student` VALUES ('19122190', '19122190', '老番茄', '男', 3, 0);
INSERT INTO `student` VALUES ('19122191', '19122191', '小文哥吃吃吃', '男', 2, 0);
INSERT INTO `student` VALUES ('19122192', '19122192', '上大王俊凯', '男', 5, 0);
INSERT INTO `student` VALUES ('19122193', '19122193', '七海Nana7mi', '女', 2, 0);
INSERT INTO `student` VALUES ('19122194', '19122194', '刘庸干净又卫生', '男', 4, 0);
INSERT INTO `student` VALUES ('19122195', '19122195', '李令羽', '女', 5, 0);
INSERT INTO `student` VALUES ('19122196', '19122196', '杨颜同学', '男', 2, 0);
INSERT INTO `student` VALUES ('19122197', '19122197', '原神', '女', 5, 0);
INSERT INTO `student` VALUES ('19122198', '19122198', '骚拳超人', '男', 3, 0);
INSERT INTO `student` VALUES ('19122199', '19122199', '动力节点', '男', 1, 0);
INSERT INTO `student` VALUES ('19122200', '19122200', '哔哩哔哩番剧', '女', 2, 0);

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `department` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('100', '100', 'SystemAdministrator', NULL, NULL);
INSERT INTO `teacher` VALUES ('101', '101', '吴基长', '女', 1);
INSERT INTO `teacher` VALUES ('102', '102', '波喵喵喵', '女', 2);
INSERT INTO `teacher` VALUES ('103', '103', '肌肉山山', '男', 3);
INSERT INTO `teacher` VALUES ('104', '104', '塑料叉FOKU', '女', 5);
INSERT INTO `teacher` VALUES ('105', '105', '毕导THU', '男', 1);
INSERT INTO `teacher` VALUES ('106', '106', '宋浩老师官方', '男', 1);
INSERT INTO `teacher` VALUES ('107', '107', '拜托了小翔哥', '男', 3);
INSERT INTO `teacher` VALUES ('108', '108', 'Yui柯基大聪明', '女', 4);
INSERT INTO `teacher` VALUES ('109', '109', '盖里老哥', '男', 3);
INSERT INTO `teacher` VALUES ('110', '110', '某幻君', '男', 5);
INSERT INTO `teacher` VALUES ('111', '111', '孙笑川258', '男', 1);
INSERT INTO `teacher` VALUES ('112', '112', '菠萝赛东°', '女', 5);
INSERT INTO `teacher` VALUES ('113', '113', '大祥哥来了', '男', 4);
INSERT INTO `teacher` VALUES ('114', '114', '徐大虾咯', '男', 2);
INSERT INTO `teacher` VALUES ('115', '115', '=咬人猫=', '女', 5);
INSERT INTO `teacher` VALUES ('116', '116', '美食作家王刚R', '男', 4);
INSERT INTO `teacher` VALUES ('117', '117', '陈睿', '男', 2);
INSERT INTO `teacher` VALUES ('118', '118', '凉风Kaze', '男', 3);
INSERT INTO `teacher` VALUES ('119', '119', 'S呐_', '女', 2);
INSERT INTO `teacher` VALUES ('120', '120', '泡芙喵-PuFF', '女', 1);
INSERT INTO `teacher` VALUES ('121', '121', '飞社长', '男', 5);
INSERT INTO `teacher` VALUES ('122', '122', '哔哩哔哩弹幕网', '女', 1);
INSERT INTO `teacher` VALUES ('123', '123', '韩小沐', '女', 3);
INSERT INTO `teacher` VALUES ('124', '124', '盗月社食遇记', '男', 3);
INSERT INTO `teacher` VALUES ('125', '125', '猛男舞团IconX', '男', 2);
INSERT INTO `teacher` VALUES ('126', '126', '一只小仙若', '女', 1);
INSERT INTO `teacher` VALUES ('127', '127', '花少北丶', '男', 5);
INSERT INTO `teacher` VALUES ('128', '128', '中国BOY超级大猩猩', '男', 4);
INSERT INTO `teacher` VALUES ('129', '129', '啊吗粽', '男', 4);
INSERT INTO `teacher` VALUES ('130', '130', '视角姬', '男', 1);
INSERT INTO `teacher` VALUES ('131', '131', 'papi酱', '女', 3);
INSERT INTO `teacher` VALUES ('132', '132', '泠鸢yousa', '女', 4);
INSERT INTO `teacher` VALUES ('133', '133', '影视飓风', '男', 1);
INSERT INTO `teacher` VALUES ('134', '134', '一马平川的妥妥', '女', 4);
INSERT INTO `teacher` VALUES ('135', '135', '木鱼水心', '男', 4);
INSERT INTO `teacher` VALUES ('136', '136', '老师好我叫何同学', '男', 5);
INSERT INTO `teacher` VALUES ('137', '137', '瓶子君152', '男', 1);
INSERT INTO `teacher` VALUES ('138', '138', '泛式', '男', 3);
INSERT INTO `teacher` VALUES ('139', '139', '月隐空夜', '女', 2);
INSERT INTO `teacher` VALUES ('140', '140', '尚硅谷', '男', 1);
INSERT INTO `teacher` VALUES ('141', '141', '空耳狂魔', '男', 2);
INSERT INTO `teacher` VALUES ('142', '142', '九三的耳朵不是特别好', '女', 3);
INSERT INTO `teacher` VALUES ('143', '143', '自由大野猪', '男', 3);
INSERT INTO `teacher` VALUES ('144', '144', '谢拉克洛瓦', '男', 3);
INSERT INTO `teacher` VALUES ('145', '145', '赫萝的苹果', '男', 2);
INSERT INTO `teacher` VALUES ('146', '146', '棉花大哥哥', '男', 5);
INSERT INTO `teacher` VALUES ('147', '147', '花园花玲', '女', 2);
INSERT INTO `teacher` VALUES ('148', '148', '乡村教师日记', '女', 2);
INSERT INTO `teacher` VALUES ('149', '149', '王道论坛', '男', 1);
INSERT INTO `teacher` VALUES ('150', '150', '十二礼', '女', 2);
INSERT INTO `teacher` VALUES ('151', '151', '黑马程序员', '男', 1);
INSERT INTO `teacher` VALUES ('152', '152', '专爱眼睛', '男', 4);
INSERT INTO `teacher` VALUES ('153', '153', 'vivi可爱多', '女', 2);
INSERT INTO `teacher` VALUES ('154', '154', '花花与三猫CatLive', '女', 3);
INSERT INTO `teacher` VALUES ('155', '155', '明日方舟终末地', '女', 4);
INSERT INTO `teacher` VALUES ('156', '156', '莴苣某人', '男', 2);
INSERT INTO `teacher` VALUES ('157', '157', '肉肉大搜索', '男', 4);
INSERT INTO `teacher` VALUES ('158', '158', '艺能人金广发', '男', 5);
INSERT INTO `teacher` VALUES ('159', '159', '晓观队长', '男', 2);
INSERT INTO `teacher` VALUES ('160', '160', '哔哩哔哩大会员', '女', 5);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '昵称',
  `age` int NULL DEFAULT NULL COMMENT '年龄',
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
