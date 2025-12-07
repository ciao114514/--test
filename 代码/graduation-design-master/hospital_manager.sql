/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80037
 Source Host           : localhost:3306
 Source Schema         : hospital_manager

 Target Server Type    : MySQL
 Target Server Version : 80037
 File Encoding         : 65001

 Date: 28/03/2025 13:34:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admini
-- ----------------------------
DROP TABLE IF EXISTS `admini`;
CREATE TABLE `admini`  (
  `a_id` int(0) NOT NULL,
  `a_password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `a_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `a_gender` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `a_card` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `a_phone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `a_email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`a_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admini
-- ----------------------------
INSERT INTO `admini` VALUES (202301, '123456', 'admin', '男', '440111111111111111', '13544444444', '123@qq.com');

-- ----------------------------
-- Table structure for arr
-- ----------------------------
DROP TABLE IF EXISTS `arr`;
CREATE TABLE `arr`  (
  `aid` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `a_time` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `tid` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`aid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of arr
-- ----------------------------
INSERT INTO `arr` VALUES ('7352606742025-02-13', '2025-02-13', 735260674);

-- ----------------------------
-- Table structure for arrange
-- ----------------------------
DROP TABLE IF EXISTS `arrange`;
CREATE TABLE `arrange`  (
  `ar_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `ar_time` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `d_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`ar_id`) USING BTREE,
  INDEX `arTOd`(`d_id`) USING BTREE,
  CONSTRAINT `arTOd` FOREIGN KEY (`d_id`) REFERENCES `doctor` (`d_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of arrange
-- ----------------------------
INSERT INTO `arrange` VALUES ('10000022025-03-15', '2025-03-15', 1000002);
INSERT INTO `arrange` VALUES ('10000022025-03-16', '2025-03-16', 1000002);
INSERT INTO `arrange` VALUES ('10000022025-03-26', '2025-03-26', 1000002);
INSERT INTO `arrange` VALUES ('10000022025-03-28', '2025-03-28', 1000002);
INSERT INTO `arrange` VALUES ('1000012025-03-15', '2025-03-15', 100001);
INSERT INTO `arrange` VALUES ('1000012025-03-16', '2025-03-16', 100001);
INSERT INTO `arrange` VALUES ('1000012025-03-26', '2025-03-26', 100001);
INSERT INTO `arrange` VALUES ('1000012025-03-28', '2025-03-28', 100001);
INSERT INTO `arrange` VALUES ('100002025-03-15', '2025-03-15', 10000);
INSERT INTO `arrange` VALUES ('100002025-03-16', '2025-03-16', 10000);
INSERT INTO `arrange` VALUES ('100002025-03-26', '2025-03-26', 10000);
INSERT INTO `arrange` VALUES ('100002025-03-28', '2025-03-28', 10000);
INSERT INTO `arrange` VALUES ('10002025-03-15', '2025-03-15', 1000);
INSERT INTO `arrange` VALUES ('10002025-03-16', '2025-03-16', 1000);
INSERT INTO `arrange` VALUES ('10002025-03-26', '2025-03-26', 1000);
INSERT INTO `arrange` VALUES ('10002025-03-28', '2025-03-28', 1000);
INSERT INTO `arrange` VALUES ('11112025-03-15', '2025-03-15', 1111);
INSERT INTO `arrange` VALUES ('11112025-03-16', '2025-03-16', 1111);
INSERT INTO `arrange` VALUES ('11112025-03-26', '2025-03-26', 1111);
INSERT INTO `arrange` VALUES ('11112025-03-28', '2025-03-28', 1111);
INSERT INTO `arrange` VALUES ('22222025-03-15', '2025-03-15', 2222);
INSERT INTO `arrange` VALUES ('22222025-03-16', '2025-03-16', 2222);
INSERT INTO `arrange` VALUES ('22222025-03-26', '2025-03-26', 2222);
INSERT INTO `arrange` VALUES ('22222025-03-28', '2025-03-28', 2222);
INSERT INTO `arrange` VALUES ('33332025-03-15', '2025-03-15', 3333);
INSERT INTO `arrange` VALUES ('33332025-03-16', '2025-03-16', 3333);
INSERT INTO `arrange` VALUES ('33332025-03-26', '2025-03-26', 3333);
INSERT INTO `arrange` VALUES ('33332025-03-28', '2025-03-28', 3333);
INSERT INTO `arrange` VALUES ('44442025-03-15', '2025-03-15', 4444);
INSERT INTO `arrange` VALUES ('44442025-03-16', '2025-03-16', 4444);
INSERT INTO `arrange` VALUES ('44442025-03-26', '2025-03-26', 4444);
INSERT INTO `arrange` VALUES ('44442025-03-28', '2025-03-28', 4444);
INSERT INTO `arrange` VALUES ('55552025-03-15', '2025-03-15', 5555);
INSERT INTO `arrange` VALUES ('55552025-03-16', '2025-03-16', 5555);
INSERT INTO `arrange` VALUES ('55552025-03-26', '2025-03-26', 5555);
INSERT INTO `arrange` VALUES ('55552025-03-28', '2025-03-28', 5555);
INSERT INTO `arrange` VALUES ('66662025-03-15', '2025-03-15', 6666);
INSERT INTO `arrange` VALUES ('66662025-03-16', '2025-03-16', 6666);
INSERT INTO `arrange` VALUES ('66662025-03-26', '2025-03-26', 6666);
INSERT INTO `arrange` VALUES ('66662025-03-28', '2025-03-28', 6666);
INSERT INTO `arrange` VALUES ('77772025-03-15', '2025-03-15', 7777);
INSERT INTO `arrange` VALUES ('77772025-03-16', '2025-03-16', 7777);
INSERT INTO `arrange` VALUES ('77772025-03-26', '2025-03-26', 7777);
INSERT INTO `arrange` VALUES ('77772025-03-28', '2025-03-28', 7777);
INSERT INTO `arrange` VALUES ('88882025-03-15', '2025-03-15', 8888);
INSERT INTO `arrange` VALUES ('88882025-03-16', '2025-03-16', 8888);
INSERT INTO `arrange` VALUES ('88882025-03-26', '2025-03-26', 8888);
INSERT INTO `arrange` VALUES ('88882025-03-28', '2025-03-28', 8888);
INSERT INTO `arrange` VALUES ('99992025-03-15', '2025-03-15', 9999);
INSERT INTO `arrange` VALUES ('99992025-03-16', '2025-03-16', 9999);
INSERT INTO `arrange` VALUES ('99992025-03-26', '2025-03-26', 9999);
INSERT INTO `arrange` VALUES ('99992025-03-28', '2025-03-28', 9999);

-- ----------------------------
-- Table structure for bed
-- ----------------------------
DROP TABLE IF EXISTS `bed`;
CREATE TABLE `bed`  (
  `b_id` int(0) NOT NULL,
  `p_id` int(0) NULL DEFAULT NULL,
  `b_state` int(0) NULL DEFAULT NULL,
  `b_start` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `d_id` int(0) NULL DEFAULT NULL,
  `b_reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `version` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`b_id`) USING BTREE,
  INDEX `bTOp`(`p_id`) USING BTREE,
  INDEX `bTOd`(`d_id`) USING BTREE,
  CONSTRAINT `bTOd` FOREIGN KEY (`d_id`) REFERENCES `doctor` (`d_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `bTOp` FOREIGN KEY (`p_id`) REFERENCES `patient` (`p_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bed
-- ----------------------------
INSERT INTO `bed` VALUES (1, -1, 0, NULL, -1, NULL, NULL);
INSERT INTO `bed` VALUES (10, -1, 0, NULL, -1, NULL, NULL);
INSERT INTO `bed` VALUES (11, -1, 0, NULL, -1, NULL, NULL);

-- ----------------------------
-- Table structure for checks
-- ----------------------------
DROP TABLE IF EXISTS `checks`;
CREATE TABLE `checks`  (
  `ch_id` int(0) NOT NULL AUTO_INCREMENT,
  `ch_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `ch_price` decimal(10, 2) NULL DEFAULT NULL,
  `kid` int(0) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `kname` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `tid` int(0) NULL DEFAULT NULL,
  `checks` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `is_active` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1000002 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of checks
-- ----------------------------
INSERT INTO `checks` VALUES (1111, '身高、体重、血压、心率', 20.00, -1840119807, '外科类', '普通外科', NULL, NULL, NULL);
INSERT INTO `checks` VALUES (2222, '耳、喉、鼻、咽、扁桃体、喉等检查', 20.00, -296615934, '五官科类', '耳鼻咽喉科', NULL, NULL, NULL);
INSERT INTO `checks` VALUES (2223, '眼科', 20.00, -204341246, '五官科类', '眼科', NULL, NULL, NULL);
INSERT INTO `checks` VALUES (2224, '甲状腺检查、彩超', 50.00, -1483603967, '其他类', '功能科', NULL, NULL, NULL);
INSERT INTO `checks` VALUES (2225, '子宫彩超', 50.00, 1448214530, '妇产科类', '妇科', NULL, NULL, NULL);
INSERT INTO `checks` VALUES (3333, '血常规、肝功能', 80.00, -1366163455, '内科类', '内分泌科', NULL, NULL, NULL);
INSERT INTO `checks` VALUES (4444, '尿常规、尿道感染、', 30.00, -367919103, '外科类', '泌尿外科', NULL, NULL, NULL);
INSERT INTO `checks` VALUES (4445, '碳14 U型螺旋杆菌检测', 20.00, 525467649, '外科类', '肛肠外科', NULL, NULL, NULL);
INSERT INTO `checks` VALUES (5555, '胃镜、肠镜', 30.00, 1708261378, '内科类', '消化内科', NULL, NULL, NULL);
INSERT INTO `checks` VALUES (6666, '肾功能、B超', 30.00, 215089154, '内科类', '肾内科', NULL, NULL, NULL);
INSERT INTO `checks` VALUES (7777, '心电图、心律', 40.00, -1642987518, '内科类', '心血管内科', NULL, NULL, NULL);
INSERT INTO `checks` VALUES (1000001, '神经内科', 50.00, -1634512894, '内科类', '神经内科', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for doctor
-- ----------------------------
DROP TABLE IF EXISTS `doctor`;
CREATE TABLE `doctor`  (
  `d_id` int(0) NOT NULL,
  `d_password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `d_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `d_gender` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `d_phone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `d_card` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `d_email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `d_post` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `d_introduction` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `d_section` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `d_state` int(0) NOT NULL,
  `d_price` decimal(10, 2) NULL DEFAULT NULL,
  `d_people` int(0) NULL DEFAULT NULL,
  `d_star` decimal(10, 2) NULL DEFAULT NULL,
  `d_avg_star` decimal(10, 2) NULL DEFAULT NULL,
  `d_address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`d_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of doctor
-- ----------------------------
INSERT INTO `doctor` VALUES (-1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `doctor` VALUES (1000, '3d7dd7b26500bd0595573b651d0080fd', '华佗', '男', '13899999999', '444111199901011111', '456@qq.com', '主任医师', '专职神经内科医生', '神经内科', 1, 80.00, 1, 5.00, 5.00, '本部门诊三楼功能检查室(中午及晚夜间急诊: 门诊一楼急诊科内);北京路门诊在三楼');
INSERT INTO `doctor` VALUES (1111, '3d7dd7b26500bd0595573b651d0080fd', '张仲景', '男', '18244556789', '342526188003250346', '18244556789@123.com', '主任医师', 'qweqweqwe', '内分泌科', 1, 80.00, 0, 0.00, NULL, '本部门诊三楼功能检查室(中午及晚夜间急诊: 门诊一楼急诊科内);北京路门诊在三楼');
INSERT INTO `doctor` VALUES (2222, '3d7dd7b26500bd0595573b651d0080fd', '孙思邈', '男', '15733333333', '554333333333333', '2222@123.com', '主任医师', '消化内科', '消化内科', 1, 30.00, 1, 5.00, 5.00, '本部门诊三楼功能检查室(中午及晚夜间急诊: 门诊一楼急诊科内);北京路门诊在三楼');
INSERT INTO `doctor` VALUES (3333, '3d7dd7b26500bd0595573b651d0080fd', '李时珍', '男', '15733333322', '554333333333333', '3333@123.com', '主任医师', '心血管', '心血管内科', 1, 80.00, 0, 0.00, NULL, '本部门诊三楼功能检查室(中午及晚夜间急诊: 门诊一楼急诊科内);北京路门诊在三楼');
INSERT INTO `doctor` VALUES (4444, '3d7dd7b26500bd0595573b651d0080fd', '皇甫谧', '男', '15733333112', '554333333333333', '4444@123.com', '主任医师', '普通外科', '普通外科', 1, 20.00, 0, 0.00, NULL, '本部门诊三楼功能检查室(中午及晚夜间急诊: 门诊一楼急诊科内);北京路门诊在三楼');
INSERT INTO `doctor` VALUES (5555, '3d7dd7b26500bd0595573b651d0080fd', '扁鹊', '男', '15733443112', '554333333322333', '55555@123.com', '主任医师', '耳鼻咽喉科', '耳鼻咽喉科', 1, 20.00, 0, 0.00, NULL, '本部门诊三楼功能检查室(中午及晚夜间急诊: 门诊一楼急诊科内);北京路门诊在三楼');
INSERT INTO `doctor` VALUES (6666, '3d7dd7b26500bd0595573b651d0080fd', '钱乙', '男', '15736443112', '554333333322333', '6666@123.com', '主任医师', '眼科', '眼科', 1, 20.00, 0, 0.00, NULL, '本部门诊三楼功能检查室(中午及晚夜间急诊: 门诊一楼急诊科内);北京路门诊在三楼');
INSERT INTO `doctor` VALUES (7777, '3d7dd7b26500bd0595573b651d0080fd', '葛洪', '男', '13833333345', '325554442222234', '7777@123.com', '主任医师', '泌尿外科', '泌尿外科', 1, 30.00, 0, 0.00, NULL, '本部门诊三楼功能检查室(中午及晚夜间急诊: 门诊一楼急诊科内);北京路门诊在三楼');
INSERT INTO `doctor` VALUES (8888, '3d7dd7b26500bd0595573b651d0080fd', '宋慈', '男', '13833333345', '325554442222234', '8888@123.com', '主任医师', '妇科', '妇科', 1, 50.00, 0, 0.00, NULL, '本部门诊三楼功能检查室(中午及晚夜间急诊: 门诊一楼急诊科内);北京路门诊在三楼');
INSERT INTO `doctor` VALUES (9999, '3d7dd7b26500bd0595573b651d0080fd', '叶桂', '男', '14744444444', '353444444444444', '9999@123.com', '主任医师', '肛肠外科', '肛肠外科', 1, 20.00, 3, 15.00, 5.00, '本部门诊三楼功能检查室(中午及晚夜间急诊: 门诊一楼急诊科内);北京路门诊在三楼');
INSERT INTO `doctor` VALUES (10000, '3d7dd7b26500bd0595573b651d0080fd', '薛生白', '男', '18244444433', '362223442223343', '10000@123.com', '主任医师', '功能科', '功能科', 1, 50.00, 0, 0.00, NULL, '本部门诊三楼功能检查室(中午及晚夜间急诊: 门诊一楼急诊科内);北京路门诊在三楼');
INSERT INTO `doctor` VALUES (100001, '3d7dd7b26500bd0595573b651d0080fd', '中药老师傅', '男', '13526646754', '452322223342675', '13526646754@163.com', '主治医生', '主治医师老师傅', '中医科', 0, 10.00, 0, 0.00, NULL, '本部门诊三楼功能检查室(中午及晚夜间急诊: 门诊一楼急诊科内);北京路门诊在三楼');
INSERT INTO `doctor` VALUES (1000002, '3d7dd7b26500bd0595573b651d0080fd', '妙手回春', '女', '15734344356', '334556666644433', '15734344356@qq.com', '副主任医师', 'asdasdasdasdasd', '急诊科', 0, NULL, 0, 0.00, NULL, '本部门诊三楼功能检查室(中午及晚夜间急诊: 门诊一楼急诊科内);北京路门诊在三楼');

-- ----------------------------
-- Table structure for drug
-- ----------------------------
DROP TABLE IF EXISTS `drug`;
CREATE TABLE `drug`  (
  `dr_id` int(0) NOT NULL AUTO_INCREMENT,
  `dr_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `dr_price` decimal(10, 2) NULL DEFAULT NULL,
  `dr_number` int(0) NULL DEFAULT NULL,
  `dr_publisher` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `dr_unit` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`dr_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of drug
-- ----------------------------
INSERT INTO `drug` VALUES (1, '青霉素', 23.00, 52, '国家医药局', '袋');
INSERT INTO `drug` VALUES (2, '苯唑西林', 11.00, 9, '国家医药局', '盒');
INSERT INTO `drug` VALUES (3, '氨苄西林', 13.00, 38, '国家医药局', '盒');
INSERT INTO `drug` VALUES (4, '哌拉西林', 2.00, 57, '国家医药局', '盒');
INSERT INTO `drug` VALUES (5, '阿莫西林', 13.00, 20, '国家医药局', '盒');
INSERT INTO `drug` VALUES (6, '头孢唑林', 3.00, 32, '国家医药局', '盒');
INSERT INTO `drug` VALUES (7, '头孢氨苄', 4.00, 43, '国家医药局', '盒');
INSERT INTO `drug` VALUES (8, '头孢呋辛', 8.00, 54, '国家医药局', '盒');
INSERT INTO `drug` VALUES (9, '阿米卡星', 5.00, 54, '国家医药局', '袋');
INSERT INTO `drug` VALUES (10, '庆大霉素', 7.00, 64, '国家医药局', '袋');
INSERT INTO `drug` VALUES (11, '红霉素', 6.00, 76, '国家医药局', '袋');
INSERT INTO `drug` VALUES (12, '阿奇霉素', 54.00, 52, '国家医药局', '袋');
INSERT INTO `drug` VALUES (13, '克林霉素', 65.00, 21, '国家医药局', '袋');
INSERT INTO `drug` VALUES (14, '复方磺胺甲噁唑', 76.00, 54, '国家医药局', '袋');
INSERT INTO `drug` VALUES (15, '诺氟沙星', 65.00, 33, '国家医药局', '袋');
INSERT INTO `drug` VALUES (16, '左氧氟沙星', 76.00, 43, '国家医药局', '袋');
INSERT INTO `drug` VALUES (17, '感康', 15.00, 10, '感康集团', '盒');

-- ----------------------------
-- Table structure for orderdetail
-- ----------------------------
DROP TABLE IF EXISTS `orderdetail`;
CREATE TABLE `orderdetail`  (
  `id` int(0) NOT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sore` int(0) NULL DEFAULT NULL,
  `chid` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orderdetail
-- ----------------------------

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `o_id` int(0) NOT NULL AUTO_INCREMENT,
  `p_id` int(0) NULL DEFAULT NULL,
  `d_id` int(0) NULL DEFAULT NULL,
  `o_record` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `o_start` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `o_end` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `o_state` int(0) NULL DEFAULT NULL,
  `o_drug` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `o_check` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `o_total_price` decimal(10, 2) NULL DEFAULT NULL,
  `o_price_state` int(0) NULL DEFAULT NULL,
  `o_advice` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `tid` int(0) NULL DEFAULT NULL,
  `tname` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `p_oid` int(0) NULL DEFAULT NULL,
  `o_tprice` decimal(10, 2) NULL DEFAULT NULL,
  `o_pstate` int(0) NULL DEFAULT NULL,
  `otype` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `state` int(0) NULL DEFAULT 0,
  `chid` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `sg` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `tz` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `xy` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `xl` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `tl` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `zr` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `yr` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `xj` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `kc` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `zy` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `yy` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `jzy` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `jyy` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `sj` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `jzx` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `lb` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `ggn` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `ygn` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `xdt` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `xn` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`o_id`) USING BTREE,
  INDEX `oTOp`(`p_id`) USING BTREE,
  INDEX `0TOd`(`d_id`) USING BTREE,
  CONSTRAINT `0TOd` FOREIGN KEY (`d_id`) REFERENCES `doctor` (`d_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `oTOp` FOREIGN KEY (`p_id`) REFERENCES `patient` (`p_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 211209 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (3033, 2000, 1111, '2324', '2025-03-28 14:30-17:30', '2025-03-28 11:9:23', 1, '哌拉西林*2(元)*1  药物总价2元 ', '血常规、肝功能*80(元)  项目总价80元 ', 80.00, 1, '23', 424931330, '血常规、肝功能', 3674, 2.00, 0, 'TT', 1, '3333', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '正常', '阴性', '', '');
INSERT INTO `orders` VALUES (3176, 2000, 6666, '555', '2025-03-28 14:30-17:30', '2025-03-28 11:13:38', 1, '哌拉西林*2(元)*1  药物总价2元 ', '眼科*20(元)  项目总价20元 ', 20.00, 1, '333', 424931330, '眼科', 3674, 2.00, 0, 'TT', 1, '2223', '', '', '', '', '', '', '', '', '', '5', '5', '5', '5', '正常', '', '', '', '', '', '');
INSERT INTO `orders` VALUES (3528, 2000, 5555, '问问', '2025-03-28 14:30-17:30', '2025-03-28 11:10:10', 1, '青霉素*23(元)*1  药物总价23元 ', '耳、喉、鼻、咽、扁桃体、喉等检查*20(元)  项目总价20元 ', 20.00, 1, '二维', 424931330, '耳、喉、鼻、咽、扁桃体、喉等检查', 3674, 23.00, 0, 'TT', 1, '2222', '', '', '', '', '正常', '5', '5', '正常', '正常', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `orders` VALUES (4258, 2000, 3333, NULL, '2025-03-26 14:30-17:30', NULL, 1, NULL, NULL, 40.00, 1, NULL, 735260674, '心电图、心律', 9106, NULL, 0, 'TT', 0, '7777', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `orders` VALUES (4900, 2000, 4444, '温柔为', '2025-03-28 14:30-17:30', '2025-03-28 11:11:6', 1, '氨苄西林*13(元)*1  药物总价13元 ', '身高、体重、血压、心率*20(元)  项目总价20元 ', 20.00, 1, '玩儿玩儿', 424931330, '身高、体重、血压、心率', 3674, 13.00, 0, 'TT', 1, '1111', '170', '120', '90', '56', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `orders` VALUES (5801, 2000, 10000, NULL, '2025-03-26 14:30-17:30', NULL, 1, NULL, NULL, 50.00, 1, NULL, 735260674, '甲状腺检查、彩超', 9106, NULL, 0, 'TT', 0, '2224', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL);
INSERT INTO `orders` VALUES (5921, 2000, 10000, '驱蚊器额', '2025-03-28 14:30-17:30', '2025-03-28 11:11:36', 1, '哌拉西林*2(元)*1  药物总价2元 ', '甲状腺检查、彩超*50(元)  项目总价50元 ', 50.00, 1, '轻轻巧', 424931330, '甲状腺检查、彩超', 3674, 2.00, 0, 'TT', 1, '2224', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '正常', '正常', '', '', '', '');
INSERT INTO `orders` VALUES (7335, 2000, 8888, '12312', '2025-03-28 14:30-17:30', '2025-03-28 11:6:3', 1, '青霉素*23(元)*1  药物总价23元 ', '子宫彩超*50(元)  项目总价50元 ', 50.00, 1, '123123', 424931330, '子宫彩超', 3674, 23.00, 0, 'TT', 1, '2225', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `orders` VALUES (7586, 2000, 1111, NULL, '2025-03-26 14:30-17:30', NULL, 1, NULL, NULL, 80.00, 1, NULL, 735260674, '血常规、肝功能', 9106, NULL, 0, 'TT', 0, '3333', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '22', '33', '11', '22', NULL, NULL);
INSERT INTO `orders` VALUES (8454, 2000, 6666, '333', '2025-03-26 14:30-17:30', '2025-03-26 16:50:39', 1, ' 药物总价0元 ', ' 项目总价0元 ', 20.00, 1, '333', 735260674, '眼科', 9106, 0.00, 0, 'TT', 1, '2223', '', '', '', '', '', '', '', '', '', '0.5', '0.5', '0.5', '0.5', '正常', '', '', '', '', '', '');
INSERT INTO `orders` VALUES (10194, 2000, 4444, NULL, '2025-03-26 14:30-17:30', NULL, 1, NULL, NULL, 20.00, 1, NULL, 735260674, '身高、体重、血压、心率', 9106, NULL, 0, 'TT', 0, '1111', '12', '12', '12', '12', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `orders` VALUES (10283, 2000, 5555, '222', '2025-03-26 14:30-17:30', '2025-03-26 16:45:35', 1, ' 药物总价0元 ', ' 项目总价0元 ', 20.00, 1, '222', 735260674, '耳、喉、鼻、咽、扁桃体、喉等检查', 9106, 0.00, 0, 'TT', 1, '2222', '', '', '', '', '正常', '2', '2', '正常', '正常', '', '', '', '', '', '', '', '', '', '', '');

-- ----------------------------
-- Table structure for patient
-- ----------------------------
DROP TABLE IF EXISTS `patient`;
CREATE TABLE `patient`  (
  `p_id` int(0) NOT NULL,
  `p_password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `p_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `p_gender` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `p_phone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `p_card` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `p_email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `p_state` int(0) NULL DEFAULT NULL,
  `p_birthday` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `p_age` int(0) NULL DEFAULT NULL,
  `p_type` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`p_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of patient
-- ----------------------------
INSERT INTO `patient` VALUES (-1, NULL, NULL, NULL, NULL, NULL, NULL, 1, '1997-01-20', 1, NULL);
INSERT INTO `patient` VALUES (2000, '3d7dd7b26500bd0595573b651d0080fd', '张三', '男', '15977777777', '551222200201013333', '789@qq.com', 1, '2023-07-27', 21, 'P');
INSERT INTO `patient` VALUES (2001, '3d7dd7b26500bd0595573b651d0080fd', '刘备', '男', '15977777777', '551222200201013333', '789@qq.com', 1, NULL, 18, '安徽工业大学');
INSERT INTO `patient` VALUES (2002, '3d7dd7b26500bd0595573b651d0080fd', '关羽', '男', '15977777777', '551222200201013333', '790@qq.com', 1, NULL, 19, '安徽工业大学');
INSERT INTO `patient` VALUES (2003, '3d7dd7b26500bd0595573b651d0080fd', '张飞', '男', '15977777777', '551222200201013333', '791@qq.com', 1, NULL, 20, '安徽工业大学');
INSERT INTO `patient` VALUES (2004, '3d7dd7b26500bd0595573b651d0080fd', '诸葛亮', '男', '15977777777', '551222200201013333', '792@qq.com', 1, NULL, 21, '安徽工业大学');
INSERT INTO `patient` VALUES (2005, '3d7dd7b26500bd0595573b651d0080fd', '曹操', '男', '15977777777', '551222200201013333', '793@qq.com', 1, NULL, 22, '安徽工业大学');
INSERT INTO `patient` VALUES (2006, '3d7dd7b26500bd0595573b651d0080fd', '董卓', '男', '15977777777', '551222200201013333', '794@qq.com', 1, NULL, 23, '安徽工业大学');
INSERT INTO `patient` VALUES (2007, '3d7dd7b26500bd0595573b651d0080fd', '吕布', '男', '15977777777', '551222200201013333', '795@qq.com', 1, NULL, 24, '安徽工业大学');
INSERT INTO `patient` VALUES (2008, '3d7dd7b26500bd0595573b651d0080fd', '袁绍', '男', '15977777777', '551222200201013333', '796@qq.com', 1, NULL, 25, '安徽工业大学');

-- ----------------------------
-- Table structure for setmeal
-- ----------------------------
DROP TABLE IF EXISTS `setmeal`;
CREATE TABLE `setmeal`  (
  `tid` int(0) NOT NULL AUTO_INCREMENT,
  `tname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sert` int(0) NULL DEFAULT NULL,
  `price` decimal(10, 2) NULL DEFAULT NULL,
  `checks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`tid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2056466434 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of setmeal
-- ----------------------------
INSERT INTO `setmeal` VALUES (424931330, '入职体检（女）', 2, 240.00, NULL);
INSERT INTO `setmeal` VALUES (735260674, '入职体检（男）', 1, 250.00, NULL);

-- ----------------------------
-- Table structure for setmeal_checks
-- ----------------------------
DROP TABLE IF EXISTS `setmeal_checks`;
CREATE TABLE `setmeal_checks`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `tid` int(0) NULL DEFAULT NULL,
  `ch_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1926463493 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of setmeal_checks
-- ----------------------------
INSERT INTO `setmeal_checks` VALUES (-1626095615, 735260674, 7777);
INSERT INTO `setmeal_checks` VALUES (-942424063, 735260674, 1111);
INSERT INTO `setmeal_checks` VALUES (-942424062, 735260674, 2222);
INSERT INTO `setmeal_checks` VALUES (-942424061, 735260674, 2223);
INSERT INTO `setmeal_checks` VALUES (-883703807, 735260674, 2224);
INSERT INTO `setmeal_checks` VALUES (-883703806, 735260674, 3333);
INSERT INTO `setmeal_checks` VALUES (1293139970, 424931330, 1111);
INSERT INTO `setmeal_checks` VALUES (1293139971, 424931330, 2222);
INSERT INTO `setmeal_checks` VALUES (1293139972, 424931330, 2223);
INSERT INTO `setmeal_checks` VALUES (1364443138, 424931330, 2225);
INSERT INTO `setmeal_checks` VALUES (1364443139, 424931330, 3333);
INSERT INTO `setmeal_checks` VALUES (1364443141, 424931330, 2224);

-- ----------------------------
-- Table structure for setmeal_orders
-- ----------------------------
DROP TABLE IF EXISTS `setmeal_orders`;
CREATE TABLE `setmeal_orders`  (
  `o_id` int(0) NOT NULL AUTO_INCREMENT,
  `p_id` int(0) NULL DEFAULT NULL,
  `d_id` int(0) NULL DEFAULT NULL,
  `o_record` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `o_start` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `o_end` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `o_state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `o_drug` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `o_check` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `o_total_price` decimal(10, 2) NULL DEFAULT NULL,
  `o_price_state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `o_advice` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `tid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `tname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `tstate` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `o_tprice` decimal(10, 2) NULL DEFAULT NULL,
  `o_pstate` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `otype` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `p_oid` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`o_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 132708 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of setmeal_orders
-- ----------------------------
INSERT INTO `setmeal_orders` VALUES (3674, 2000, NULL, NULL, '2025-03-28 14:30-17:30  下午', NULL, '1', '青霉素*23(元)*1  药物总价23元 ,哌拉西林*2(元)*1  药物总价2元 ,青霉素*23(元)*1  药物总价23元 ,氨苄西林*13(元)*1  药物总价13元 ,哌拉西林*2(元)*1  药物总价2元 ,哌拉西林*2(元)*1  药物总价2元 ', NULL, 240.00, '1', NULL, '424931330', '入职体检（女）', NULL, 65.00, '0', 'T', NULL);
INSERT INTO `setmeal_orders` VALUES (9106, 2000, NULL, NULL, '2025-03-26 14:30-17:30  下午', NULL, '1', ' 药物总价0元 , 药物总价0元 ', NULL, 250.00, '1', NULL, '735260674', '入职体检（男）', NULL, 0.00, '0', 'T', NULL);

-- ----------------------------
-- Table structure for subject
-- ----------------------------
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject`  (
  `kid` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `kname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sert` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`kid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2035417090 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of subject
-- ----------------------------
INSERT INTO `subject` VALUES (-1840119807, '外科类', '普通外科', 9);
INSERT INTO `subject` VALUES (-1642987518, '内科类', '心血管内科', 5);
INSERT INTO `subject` VALUES (-1634512894, '内科类', '神经内科', 1);
INSERT INTO `subject` VALUES (-1483603967, '其他类', '功能科', 26);
INSERT INTO `subject` VALUES (-1366163455, '内科类', '内分泌科', 3);
INSERT INTO `subject` VALUES (-1340997630, '外科类', '骨科', 13);
INSERT INTO `subject` VALUES (-367919103, '外科类', '泌尿外科', 11);
INSERT INTO `subject` VALUES (-296615934, '五官科类', '耳鼻咽喉科', 19);
INSERT INTO `subject` VALUES (-267255807, '外科类', '神经外科', 12);
INSERT INTO `subject` VALUES (-204341246, '五官科类', '眼科', 20);
INSERT INTO `subject` VALUES (110231553, '内科类', '呼吸与危重症医学科', 2);
INSERT INTO `subject` VALUES (215089154, '内科类', '肾内科', 6);
INSERT INTO `subject` VALUES (525467649, '外科类', '肛肠外科', 10);
INSERT INTO `subject` VALUES (1024589825, '内科类', '发热门诊', 7);
INSERT INTO `subject` VALUES (1448214530, '妇产科类', '妇科', 15);
INSERT INTO `subject` VALUES (1708261378, '内科类', '消化内科', 4);
INSERT INTO `subject` VALUES (1737621505, '外科类', '烧伤整形外科', 14);
INSERT INTO `subject` VALUES (2035417089, '外科类', '手足外科', 8);

SET FOREIGN_KEY_CHECKS = 1;
