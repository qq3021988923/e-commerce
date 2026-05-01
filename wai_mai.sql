/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 80028
Source Host           : localhost:3306
Source Database       : wai_mai

Target Server Type    : MYSQL
Target Server Version : 80028
File Encoding         : 65001

Date: 2026-04-04 16:10:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `address_book`
-- ----------------------------
DROP TABLE IF EXISTS `address_book`;
CREATE TABLE `address_book` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `consignee` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '收货人',
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '性别',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `province_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '省级区划编号',
  `province_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '省级名称',
  `city_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '市级区划编号',
  `city_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '市级名称',
  `district_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '区级区划编号',
  `district_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '区级名称',
  `detail` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '详细地址',
  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '标签',
  `is_default` tinyint(1) NOT NULL DEFAULT '0' COMMENT '默认 0 否 1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='地址簿';

-- ----------------------------
-- Records of address_book
-- ----------------------------
INSERT INTO `address_book` VALUES ('1', '1', '张三', '1', '13800138001', '110000', '北京市', '110100', '北京市', '110101', '东城区', '某某小区1号楼101室', '家', '0');
INSERT INTO `address_book` VALUES ('2', '2', '李四', '2', '13800138002', '310000', '上海市', '310100', '上海市', '310101', '黄浦区', '某某路88号', '公司', '1');
INSERT INTO `address_book` VALUES ('3', '3', '王五', '1', '13800138003', '440000', '广东省', '440100', '广州市', '440103', '荔湾区', '某某花园3栋202', '家', '1');
INSERT INTO `address_book` VALUES ('4', '1', '张三', '1', '13800138000', '110000', '北京市', '110100', '北京市', '110101', '东城区', '王府井大街100号', '家', '0');
INSERT INTO `address_book` VALUES ('5', '1', '张三55', '1', '13800138000', '110000', '北京市', '110100', '北京市', '110101', '东城区', '修改后的详细地址', '公司', '1');
INSERT INTO `address_book` VALUES ('7', '1', '张三3', null, '13800138000', null, '北京市', null, '北京市', null, '东城区', '王府井大街100号', null, '0');

-- ----------------------------
-- Table structure for `category`
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` int DEFAULT NULL COMMENT '类型   1 菜品分类 2 套餐分类',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '分类名称',
  `sort` int NOT NULL DEFAULT '0' COMMENT '顺序',
  `status` int DEFAULT NULL COMMENT '分类状态 0:禁用，1:启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint DEFAULT NULL COMMENT '创建人',
  `update_user` bigint DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_category_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='菜品及套餐分类';

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('11', '1', '川菜2', '12', '1', '2026-03-24 11:34:40', '2026-03-24 11:34:56', '1', '1');
INSERT INTO `category` VALUES ('12', '1', '传统主食', '9', '1', '2026-03-24 11:34:40', '2026-03-24 11:34:56', '1', '1');
INSERT INTO `category` VALUES ('13', '2', '人气套餐', '12', '1', '2026-03-24 11:34:40', '2026-03-24 11:34:56', '1', '1');
INSERT INTO `category` VALUES ('15', '2', '商务套餐', '13', '1', '2026-03-24 11:34:40', '2026-03-24 11:34:56', '1', '1');
INSERT INTO `category` VALUES ('16', '1', '蜀味烤鱼', '4', '1', '2026-03-24 11:34:40', '2026-03-24 11:34:56', '1', '1');
INSERT INTO `category` VALUES ('17', '1', '蜀味牛蛙', '5', '1', '2026-03-24 11:34:40', '2026-03-24 11:34:56', '1', '1');
INSERT INTO `category` VALUES ('18', '1', '特色蒸菜', '6', '1', '2026-03-24 11:34:40', '2026-03-24 11:34:56', '1', '1');
INSERT INTO `category` VALUES ('19', '1', '新鲜时蔬', '7', '1', '2026-03-24 11:34:40', '2026-03-24 11:34:56', '1', '1');
INSERT INTO `category` VALUES ('20', '1', '水煮鱼', '8', '1', '2026-03-24 11:34:40', '2026-03-24 11:34:56', '1', '1');
INSERT INTO `category` VALUES ('21', '1', '汤类', '11', '1', '2026-03-24 11:34:40', '2026-03-24 11:34:56', '1', '1');

-- ----------------------------
-- Table structure for `dish`
-- ----------------------------
DROP TABLE IF EXISTS `dish`;
CREATE TABLE `dish` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '菜品名称',
  `category_id` bigint NOT NULL COMMENT '菜品分类id',
  `price` decimal(10,2) DEFAULT NULL COMMENT '菜品价格',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '图片',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '描述信息',
  `status` int DEFAULT '1' COMMENT '0 停售 1 起售',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint DEFAULT NULL COMMENT '创建人',
  `update_user` bigint DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_dish_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='菜品';

-- ----------------------------
-- Records of dish
-- ----------------------------
INSERT INTO `dish` VALUES ('46', '王老吉', '46', '6.00', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/41bfcacf-7ad4-4927-8b26-df366553a94c.png', '', '1', '2026-03-24 11:35:56', '2026-03-24 11:36:06', '1', '1');
INSERT INTO `dish` VALUES ('47', '北冰洋', '11', '4.00', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4451d4be-89a2-4939-9c69-3a87151cb979.png', '还是小时候的味道', '1', '2026-03-24 11:35:56', '2026-03-24 11:36:06', '1', '1');
INSERT INTO `dish` VALUES ('48', '雪花啤酒', '11', '4.00', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/bf8cbfc1-04d2-40e8-9826-061ee41ab87c.png', '', '1', '2026-03-24 11:35:56', '2026-03-24 11:36:06', '1', '1');
INSERT INTO `dish` VALUES ('49', '米饭', '12', '2.00', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/76752350-2121-44d2-b477-10791c23a8ec.png', '精选五常大米', '1', '2026-03-24 11:35:56', '2026-03-24 11:36:06', '1', '1');
INSERT INTO `dish` VALUES ('50', '馒头', '13', '1.00', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/475cc599-8661-4899-8f9e-121dd8ef7d02.png', '优质面粉', '1', '2026-03-24 11:35:56', '2026-03-24 11:36:06', '1', '1');
INSERT INTO `dish` VALUES ('51', '老坛酸菜鱼', '20', '56.00', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4a9cefba-6a74-467e-9fde-6e687ea725d7.png', '原料：汤，草鱼，酸菜', '1', '2026-03-24 11:35:56', '2026-03-24 11:36:06', '1', '1');
INSERT INTO `dish` VALUES ('52', '经典酸菜鮰鱼', '20', '66.00', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/5260ff39-986c-4a97-8850-2ec8c7583efc.png', '原料：酸菜，江团，鮰鱼', '1', '2026-03-24 11:35:56', '2026-03-24 11:36:06', '1', '1');
INSERT INTO `dish` VALUES ('53', '蜀味水煮草鱼', '20', '38.00', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a6953d5a-4c18-4b30-9319-4926ee77261f.png', '原料：草鱼，汤', '1', '2026-03-24 11:35:56', '2026-03-24 11:36:06', '1', '1');
INSERT INTO `dish` VALUES ('54', '清炒小油菜', '19', '18.00', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/3613d38e-5614-41c2-90ed-ff175bf50716.png', '原料：小油菜', '1', '2026-03-24 11:35:56', '2026-03-24 11:36:06', '1', '1');
INSERT INTO `dish` VALUES ('55', '蒜蓉娃娃菜', '19', '18.00', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4879ed66-3860-4b28-ba14-306ac025fdec.png', '原料：蒜，娃娃菜', '1', '2026-03-24 11:35:56', '2026-03-24 11:36:06', '1', '1');
INSERT INTO `dish` VALUES ('56', '清炒西兰花', '19', '18.00', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/e9ec4ba4-4b22-4fc8-9be0-4946e6aeb937.png', '原料：西兰花', '1', '2026-03-24 11:35:56', '2026-03-24 11:36:06', '1', '1');
INSERT INTO `dish` VALUES ('57', '炝炒圆白菜', '19', '18.00', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/22f59feb-0d44-430e-a6cd-6a49f27453ca.png', '原料：圆白菜', '1', '2026-03-24 11:35:56', '2026-03-24 11:36:06', '1', '1');
INSERT INTO `dish` VALUES ('58', '清蒸鲈鱼', '18', '98.00', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/c18b5c67-3b71-466c-a75a-e63c6449f21c.png', '原料：鲈鱼', '1', '2026-03-24 11:35:56', '2026-03-24 11:36:06', '1', '1');
INSERT INTO `dish` VALUES ('59', '东坡肘子', '18', '138.00', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a80a4b8c-c93e-4f43-ac8a-856b0d5cc451.png', '原料：猪肘棒', '1', '2026-03-24 11:35:56', '2026-03-24 11:36:06', '1', '1');
INSERT INTO `dish` VALUES ('60', '梅菜扣肉', '18', '58.00', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/6080b118-e30a-4577-aab4-45042e3f88be.png', '原料：猪肉，梅菜', '1', '2026-03-24 11:35:56', '2026-03-24 11:36:06', '1', '1');
INSERT INTO `dish` VALUES ('61', '剁椒鱼头', '18', '66.00', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/13da832f-ef2c-484d-8370-5934a1045a06.png', '原料：鲢鱼，剁椒', '1', '2026-03-24 11:35:56', '2026-03-24 11:36:06', '1', '1');
INSERT INTO `dish` VALUES ('62', '金汤酸菜牛蛙', '17', '88.00', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/7694a5d8-7938-4e9d-8b9e-2075983a2e38.png', '原料：鲜活牛蛙，酸菜', '1', '2026-03-24 11:35:56', '2026-03-24 11:36:06', '1', '1');
INSERT INTO `dish` VALUES ('63', '香锅牛蛙', '17', '88.00', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/f5ac8455-4793-450c-97ba-173795c34626.png', '配料：鲜活牛蛙，莲藕，青笋', '1', '2026-03-24 11:35:56', '2026-03-24 11:36:06', '1', '1');
INSERT INTO `dish` VALUES ('64', '馋嘴牛蛙', '17', '88.00', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/7a55b845-1f2b-41fa-9486-76d187ee9ee1.png', '配料：鲜活牛蛙，丝瓜，黄豆芽', '1', '2026-03-24 11:35:56', '2026-03-24 11:36:06', '1', '1');
INSERT INTO `dish` VALUES ('65', '草鱼2斤', '16', '68.00', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/b544d3ba-a1ae-4d20-a860-81cb5dec9e03.png', '原料：草鱼，黄豆芽，莲藕', '1', '2026-03-24 11:35:56', '2026-03-24 11:36:06', '1', '1');
INSERT INTO `dish` VALUES ('66', '江团鱼2斤', '16', '119.00', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a101a1e9-8f8b-47b2-afa4-1abd47ea0a87.png', '配料：江团鱼，黄豆芽，莲藕', '1', '2026-03-24 11:35:56', '2026-03-24 11:36:06', '1', '1');
INSERT INTO `dish` VALUES ('67', '鮰鱼2斤', '16', '72.00', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/8cfcc576-4b66-4a09-ac68-ad5b273c2590.png', '原料：鮰鱼，黄豆芽，莲藕', '1', '2026-03-24 11:35:56', '2026-03-24 11:36:06', '1', '1');
INSERT INTO `dish` VALUES ('68', '鸡蛋汤', '21', '4.00', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/c09a0ee8-9d19-428d-81b9-746221824113.png', '配料：鸡蛋，紫菜', '1', '2026-03-24 11:35:56', '2026-03-24 11:36:06', '1', '1');
INSERT INTO `dish` VALUES ('69', '平菇豆腐汤', '21', '6.00', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/16d0a3d6-2253-4cfc-9b49-bf7bd9eb2ad2.png', '配料：豆腐，平菇', '0', '2026-03-24 11:35:56', '2026-03-24 11:36:06', '1', '1');
INSERT INTO `dish` VALUES ('71', '江团酸菜鱼', '1', '88.90', '/images/dish/jiangtuan-sauerkraut.jpg', '配方：精选江团鱼/鮰鱼为主料，搭配老坛发酵酸菜，辅以干辣椒、花椒、姜片、蒜片等调料，骨汤慢炖而成', '1', '2026-03-24 11:35:56', '2026-03-24 11:36:06', '1', '1');
INSERT INTO `dish` VALUES ('77', '江团酸菜鱼2', '1', '88.90', '/images/dish/jiangtuan.jpg', '配方：江团鱼+老坛酸菜', '1', '2026-03-24 11:35:56', '2026-03-24 11:36:06', '1', '1');

-- ----------------------------
-- Table structure for `dish_flavor`
-- ----------------------------
DROP TABLE IF EXISTS `dish_flavor`;
CREATE TABLE `dish_flavor` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dish_id` bigint NOT NULL COMMENT '菜品',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '口味名称',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '口味数据list',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='菜品口味关系表';

-- ----------------------------
-- Records of dish_flavor
-- ----------------------------
INSERT INTO `dish_flavor` VALUES ('40', '46', '甜味', '\"无糖\",\"少糖\",\"半糖\",\"多糖\",\"全糖2\"');
INSERT INTO `dish_flavor` VALUES ('41', '7', '忌口', '\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"');
INSERT INTO `dish_flavor` VALUES ('42', '7', '温度', '多冰，微辣');
INSERT INTO `dish_flavor` VALUES ('45', '6', '忌口', '不要辣');
INSERT INTO `dish_flavor` VALUES ('46', '6', '辣度', '重辣');
INSERT INTO `dish_flavor` VALUES ('47', '5', '辣度', '重辣');
INSERT INTO `dish_flavor` VALUES ('48', '5', '甜味', '全糖');
INSERT INTO `dish_flavor` VALUES ('49', '2', '甜味', '全糖');
INSERT INTO `dish_flavor` VALUES ('50', '4', '甜味', '全糖');
INSERT INTO `dish_flavor` VALUES ('51', '3', '甜味', '全糖');
INSERT INTO `dish_flavor` VALUES ('52', '3', '忌口', '不要辣');
INSERT INTO `dish_flavor` VALUES ('86', '52', '忌口', '不要辣');
INSERT INTO `dish_flavor` VALUES ('87', '52', '辣度', '重辣');
INSERT INTO `dish_flavor` VALUES ('88', '51', '忌口', '不要辣');
INSERT INTO `dish_flavor` VALUES ('89', '51', '辣度', '重辣');
INSERT INTO `dish_flavor` VALUES ('92', '53', '忌口', '不要辣');
INSERT INTO `dish_flavor` VALUES ('93', '53', '辣度', '重辣');
INSERT INTO `dish_flavor` VALUES ('94', '54', '忌口', '不要辣');
INSERT INTO `dish_flavor` VALUES ('95', '56', '忌口', '不要辣');
INSERT INTO `dish_flavor` VALUES ('96', '57', '忌口', '不要辣');
INSERT INTO `dish_flavor` VALUES ('97', '60', '忌口', '不要辣');
INSERT INTO `dish_flavor` VALUES ('101', '66', '辣度', '重辣');
INSERT INTO `dish_flavor` VALUES ('102', '67', '辣度', '重辣');
INSERT INTO `dish_flavor` VALUES ('103', '65', '辣度', '重辣');
INSERT INTO `dish_flavor` VALUES ('107', '77', '辣度', '中辣');
INSERT INTO `dish_flavor` VALUES ('108', '77', '鱼品种', '江团');

-- ----------------------------
-- Table structure for `employee`
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '姓名',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '性别',
  `id_number` varchar(18) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '身份证号',
  `status` int NOT NULL DEFAULT '1' COMMENT '状态 0:禁用，1:启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint DEFAULT NULL COMMENT '创建人',
  `update_user` bigint DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='员工信息';

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('1', '管理员', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '13812312312', '1', '110101199001010047', '1', '2026-03-24 11:36:48', '2026-03-24 11:36:54', '10', '3');
INSERT INTO `employee` VALUES ('2', '张三111', 'zhang_san2', 'e10adc3949ba59abbe56e057f20f883e', '13800138002', '男', '11010119900101121', '1', '2026-03-24 11:36:48', '2026-03-24 11:36:54', '1', '1');
INSERT INTO `employee` VALUES ('3', '测试张三', 'test_admin', 'e10adc3949ba59abbe56e057f20f883e', '13800138000', '男', '110101199001011234', '1', '2026-03-24 11:36:48', '2026-03-24 11:36:54', '1', '1');
INSERT INTO `employee` VALUES ('4', '测试张三2', 'test_admin02', 'e10adc3949ba59abbe56e057f20f883e', '13800138002', '女', '110101199001011232', '1', '2026-03-24 11:36:48', '2026-03-24 11:36:54', '1', '1');
INSERT INTO `employee` VALUES ('5', '张三111', 'zhang_san22', 'e10adc3949ba59abbe56e057f20f883e', '13800138002', '男', '11010119900101121', '1', '2026-03-24 11:36:48', '2026-03-24 11:36:54', '1', '1');
INSERT INTO `employee` VALUES ('6', '测试张三4', 'test_admin04', 'e10adc3949ba59abbe56e057f20f883e', '13800138004', '女', '110101199001011232', '1', '2026-03-24 11:36:48', '2026-03-24 11:36:54', '1', '1');
INSERT INTO `employee` VALUES ('7', '张三7', 'zhang_san7', 'e10adc3949ba59abbe56e057f20f883e', '13800138007', '男', '11010119900101121', '1', '2026-03-24 11:36:48', '2026-03-24 11:36:54', '1', '1');

-- ----------------------------
-- Table structure for `orders`
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `number` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '订单号',
  `status` int NOT NULL DEFAULT '1' COMMENT '订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消 7退款',
  `user_id` bigint NOT NULL COMMENT '下单用户',
  `address_book_id` bigint NOT NULL COMMENT '地址id',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `checkout_time` datetime DEFAULT NULL COMMENT '结账时间',
  `pay_method` int NOT NULL DEFAULT '1' COMMENT '支付方式 1微信,2支付宝',
  `pay_status` tinyint NOT NULL DEFAULT '0' COMMENT '支付状态 0未支付 1已支付 2退款',
  `amount` decimal(10,2) NOT NULL COMMENT '实收金额',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手机号',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '地址',
  `user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户名称',
  `consignee` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '收货人',
  `cancel_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '订单取消原因',
  `rejection_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '订单拒绝原因',
  `cancel_time` datetime DEFAULT NULL COMMENT '订单取消时间',
  `estimated_delivery_time` datetime DEFAULT NULL COMMENT '预计送达时间',
  `delivery_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '配送状态  1立即送出  0选择具体时间',
  `delivery_time` datetime DEFAULT NULL COMMENT '送达时间',
  `pack_amount` int DEFAULT NULL COMMENT '打包费',
  `tableware_number` int DEFAULT NULL COMMENT '餐具数量',
  `tableware_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '餐具数量状态  1按餐量提供  0选择具体数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='订单表';

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('1', '202401010001', '6', '1', '1', '2026-03-01 17:00:41', '2026-03-01 17:01:04', '1', '2', '68.00', '少放辣', '13800138001', '北京市东城区某某小区1号楼101室', '张三', '张三', '太好吃了，所以退款2', '有蟑螂', '2026-03-30 10:27:52', '2026-03-25 17:02:31', '1', '2026-03-26 11:25:04', '2', '2', '1');
INSERT INTO `orders` VALUES ('2', '202401010002', '3', '2', '2', '2026-03-02 17:00:41', '2026-03-02 17:01:04', '1', '1', '128.00', '多加葱', '13800138002', '上海市黄浦区某某路88号', '李四', '李四', null, null, null, '2026-03-25 17:02:31', '1', null, '3', '2', '1');
INSERT INTO `orders` VALUES ('3', '202401010003', '2', '3', '3', '2026-03-03 17:00:41', '2026-03-03 17:01:04', '1', '1', '198.00', '不要香菜', '13800138003', '广州市荔湾区某某花园3栋202', '王五', '王五', null, null, null, '2026-03-25 17:02:31', '1', null, '4', '3', '1');
INSERT INTO `orders` VALUES ('4', '202401020001', '6', '1', '1', '2026-03-04 17:00:41', '2026-03-04 17:01:04', '1', '1', '98.00', '', '13800138001', '北京市东城区某某小区1号楼101室', '张三', '张三', '订单超时，自动取消', null, '2026-03-27 08:47:00', '2026-03-25 17:02:31', '2', '2026-01-02 11:35:00', '2', '2', '1');
INSERT INTO `orders` VALUES ('5', '202401020002', '3', '2', '2', '2026-03-05 17:00:41', '2026-03-05 17:01:04', '1', '1', '168.00', '快点送', '13800138002', '上海市黄浦区某某路88号', '李四', '李四', null, null, null, '2026-03-25 17:02:31', '3', '2026-01-02 12:40:00', '3', '2', '1');
INSERT INTO `orders` VALUES ('6', '202401030001', '5', '3', '3', '2026-03-06 17:00:41', '2026-03-06 17:01:04', '1', '1', '56.00', '备注测试', '13800138003', '广州市荔湾区某某花园3栋202', '王五', '王五', '订单超时，自动取消', null, '2026-03-25 14:36:00', '2026-03-25 17:02:31', '1', null, '1', '1', '1');
INSERT INTO `orders` VALUES ('7', '202401030002', '3', '1', '1', '2026-03-07 17:00:41', '2026-03-07 17:01:04', '2', '1', '66.00', '', '13800138001', '北京市东城区某某小区1号楼101室', '张三', '张三', null, null, null, '2026-03-25 17:02:31', '1', null, '2', '2', '1');
INSERT INTO `orders` VALUES ('8', '202401040001', '5', '2', '2', '2026-03-11 17:00:41', '2026-03-08 17:01:04', '1', '0', '38.00', '取消测试', '13800138002', '上海市黄浦区某某路88号', '李四', '李四', '不想吃了', null, '2026-02-01 10:05:00', '2026-03-25 17:02:31', '1', null, '1', '1', '1');
INSERT INTO `orders` VALUES ('9', '202401040002', '2', '3', '3', '2026-03-06 17:00:41', '2026-03-10 17:01:04', '1', '1', '68.00', '素食套餐', '13800138003', '广州市荔湾区某某花园3栋202', '王五', '王五', '订单超时，自动取消', null, '2026-03-25 14:36:00', '2026-03-25 17:02:31', '1', null, '2', '2', '1');
INSERT INTO `orders` VALUES ('10', '202401050001', '5', '1', '1', '2026-03-11 17:00:41', '2026-03-11 17:01:04', '1', '1', '128.00', '双人餐', '13800138001', '北京市东城区某某小区1号楼101室', '张三', '张三', null, null, null, '2026-03-25 17:02:31', '1', null, '3', '3', '1');
INSERT INTO `orders` VALUES ('11', '202401060001', '6', '1', '1', '2026-03-27 16:39:43', '2024-01-06 12:05:00', '1', '1', '56.00', '微辣', '13800138001', '北京市东城区某某小区1号楼101室', '张三', '张三', null, null, null, null, '1', null, null, null, '1');
INSERT INTO `orders` VALUES ('12', '202401060002', '2', '2', '2', '2026-03-27 16:39:43', '2024-01-06 12:35:00', '1', '1', '128.00', '双人餐', '13800138002', '上海市黄浦区某某路88号', '李四', '李四', null, null, null, null, '1', null, null, null, '1');
INSERT INTO `orders` VALUES ('13', '202401070001', '3', '3', '3', '2026-03-27 16:39:43', '2024-01-07 11:05:00', '1', '1', '66.00', '中辣', '13800138003', '广州市荔湾区某某花园3栋202', '王五', '王五', null, null, null, null, '1', null, null, null, '1');
INSERT INTO `orders` VALUES ('14', '202401070002', '5', '1', '1', '2026-03-27 16:39:43', '2024-01-07 18:05:00', '1', '1', '198.00', '聚餐', '13800138001', '北京市东城区某某小区1号楼101室', '张三', '张三', null, null, null, null, '1', null, null, null, '1');
INSERT INTO `orders` VALUES ('15', '202401080001', '5', '2', '2', '2026-03-27 16:39:43', '2024-01-08 12:05:00', '1', '1', '38.00', '特辣', '13800138002', '上海市黄浦区某某路88号', '李四', '李四', null, null, null, null, '1', null, null, null, '1');
INSERT INTO `orders` VALUES ('16', '202401080002', '3', '3', '3', '2026-03-27 16:39:43', '2024-01-08 19:05:00', '1', '1', '168.00', '商务餐', '13800138003', '广州市荔湾区某某花园3栋202', '王五', '王五', null, null, null, null, '1', null, null, null, '1');
INSERT INTO `orders` VALUES ('17', '202401090001', '2', '1', '1', '2026-03-26 16:39:43', '2024-01-09 12:05:00', '1', '1', '98.00', '单人餐', '13800138001', '北京市东城区某某小区1号楼101室', '张三', '张三', null, null, null, null, '1', null, null, null, '1');
INSERT INTO `orders` VALUES ('18', '202401090002', '3', '2', '2', '2026-03-27 16:39:43', '2024-01-09 18:05:00', '1', '1', '68.00', '素食', '13800138002', '上海市黄浦区某某路88号', '李四', '李四', null, null, null, null, '1', null, null, null, '1');
INSERT INTO `orders` VALUES ('19', '202401100001', '5', '3', '3', '2026-03-27 16:39:43', '2024-01-10 11:05:00', '1', '1', '56.00', '微辣', '13800138003', '广州市荔湾区某某花园3栋202', '王五', '王五', null, null, null, null, '1', null, null, null, '1');
INSERT INTO `orders` VALUES ('20', '202401100002', '5', '1', '1', '2026-03-27 16:39:43', '2024-01-10 19:05:00', '1', '1', '128.00', '双人餐', '13800138001', '北京市东城区某某小区1号楼101室', '张三', '张三', null, null, null, null, '1', null, null, null, '1');
INSERT INTO `orders` VALUES ('21', '1774753050662', '6', '1', '1', '2026-03-29 10:57:31', null, '1', '0', '66.60', '备注信息', '13800138001', '某某小区1号楼101室', null, '张三', '订单操作，自动取消', null, '2026-04-01 08:34:00', '2026-03-29 20:00:00', '1', null, '2', '2', '0');
INSERT INTO `orders` VALUES ('22', '1774856396289', '6', '1', '1', '2026-03-30 15:39:56', null, '1', '0', '66.60', '麻烦快点谢谢', '13800138001', '某某小区1号楼101室', null, '张三', '订单操作，自动取消', null, '2026-04-01 08:34:00', '2026-03-30 20:00:00', '1', null, '2', '1', '1');
INSERT INTO `orders` VALUES ('23', '1774856943820', '6', '1', '2', '2026-03-30 15:48:54', null, '2', '0', '199.90', '我要双份餐具', '13800138002', '某某路88号', null, '李四', '订单操作，自动取消', null, '2026-04-01 08:34:00', '2026-03-30 20:00:00', '1', null, '2', '1', '1');
INSERT INTO `orders` VALUES ('24', '1775003654614', '5', '1', '2', '2026-04-01 09:12:15', null, '2', '0', '199.90', '我要双份餐具', '13800138002', '某某路88号', null, '李四', '订单操作，自动取消', null, '2026-04-01 08:37:00', '2026-03-30 20:00:00', '1', null, '2', '1', '1');
INSERT INTO `orders` VALUES ('25', '1775004190210', '5', '1', '2', '2026-04-01 09:12:48', null, '2', '0', '199.90', '我要双份餐具2', '13800138002', '某某路88号', null, '李四', '订单操作，自动取消', null, '2026-04-01 08:46:00', '2026-03-30 20:00:00', '1', null, '2', '1', '1');

-- ----------------------------
-- Table structure for `order_detail`
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '名字',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '图片',
  `order_id` bigint NOT NULL COMMENT '订单id',
  `dish_id` bigint DEFAULT NULL COMMENT '菜品id',
  `setmeal_id` bigint DEFAULT NULL COMMENT '套餐id',
  `dish_flavor` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '口味',
  `number` int NOT NULL DEFAULT '1' COMMENT '数量',
  `amount` decimal(10,2) NOT NULL COMMENT '金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='订单明细表';

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES ('1', '素食套餐', 'https://example.com/veg.jpg', '1', null, '5', null, '1', '68.00');
INSERT INTO `order_detail` VALUES ('2', '酸菜鱼双人餐', 'https://example.com/fish.jpg', '2', null, '1', null, '1', '128.00');
INSERT INTO `order_detail` VALUES ('3', '经典川菜套餐', 'https://example.com/sichuan.jpg', '3', null, '4', null, '1', '198.00');
INSERT INTO `order_detail` VALUES ('4', '牛蛙单人餐', 'https://example.com/frog.jpg', '4', null, '2', null, '1', '98.00');
INSERT INTO `order_detail` VALUES ('5', '烤鱼商务餐', 'https://example.com/grill.jpg', '5', null, '3', null, '1', '168.00');
INSERT INTO `order_detail` VALUES ('6', '老坛酸菜鱼', 'https://example.com/dish1.jpg', '6', '51', null, '微辣', '1', '56.00');
INSERT INTO `order_detail` VALUES ('7', '经典酸菜鮰鱼', 'https://example.com/dish2.jpg', '7', '52', null, '中辣', '1', '66.00');
INSERT INTO `order_detail` VALUES ('8', '蜀味水煮草鱼', 'https://example.com/dish3.jpg', '8', '53', null, '特辣', '1', '38.00');
INSERT INTO `order_detail` VALUES ('9', '素食套餐', 'https://example.com/veg.jpg', '9', null, '5', null, '1', '68.00');
INSERT INTO `order_detail` VALUES ('10', '酸菜鱼双人餐', 'https://example.com/fish.jpg', '10', null, '1', null, '1', '128.00');
INSERT INTO `order_detail` VALUES ('11', '米饭', 'https://example.com/rice.jpg', '6', '49', null, null, '2', '4.00');
INSERT INTO `order_detail` VALUES ('12', '王老吉', 'https://example.com/drink.jpg', '7', '46', null, null, '1', '6.00');
INSERT INTO `order_detail` VALUES ('13', '北冰洋', 'https://example.com/drink2.jpg', '10', '47', null, null, '2', '8.00');
INSERT INTO `order_detail` VALUES ('14', '老坛酸菜鱼', 'https://example.com/dish1.jpg', '11', '51', null, '微辣', '1', '56.00');
INSERT INTO `order_detail` VALUES ('15', '米饭', 'https://example.com/rice.jpg', '11', '49', null, null, '2', '4.00');
INSERT INTO `order_detail` VALUES ('16', '酸菜鱼双人餐', 'https://example.com/setmeal1.jpg', '12', null, '1', null, '1', '128.00');
INSERT INTO `order_detail` VALUES ('17', '经典酸菜鮰鱼', 'https://example.com/dish2.jpg', '13', '52', null, '中辣', '1', '66.00');
INSERT INTO `order_detail` VALUES ('18', '经典川菜套餐', 'https://example.com/setmeal4.jpg', '14', null, '4', null, '1', '198.00');
INSERT INTO `order_detail` VALUES ('19', '蜀味水煮草鱼', 'https://example.com/dish3.jpg', '15', '53', null, '特辣', '1', '38.00');
INSERT INTO `order_detail` VALUES ('20', '烤鱼商务餐', 'https://example.com/setmeal3.jpg', '16', null, '3', null, '1', '168.00');
INSERT INTO `order_detail` VALUES ('21', '牛蛙单人餐', 'https://example.com/setmeal2.jpg', '17', null, '2', null, '99', '98.00');
INSERT INTO `order_detail` VALUES ('22', '素食套餐', 'https://example.com/setmeal5.jpg', '18', null, '5', null, '1', '68.00');
INSERT INTO `order_detail` VALUES ('23', '老坛酸菜鱼', 'https://example.com/dish1.jpg', '19', '51', null, '微辣', '1', '56.00');
INSERT INTO `order_detail` VALUES ('24', '王老吉', 'https://example.com/drink.jpg', '19', '46', null, null, '2', '12.00');
INSERT INTO `order_detail` VALUES ('25', '酸菜鱼双人餐', 'https://example.com/setmeal1.jpg', '20', null, '1', null, '1', '128.00');
INSERT INTO `order_detail` VALUES ('26', '老坛酸菜鱼', 'https://example.com/dish1.jpg', '6', '51', null, '微辣', '2', '112.00');
INSERT INTO `order_detail` VALUES ('27', '酸菜鱼双人餐', 'https://example.com/setmeal1.jpg', '10', null, '1', null, '1', '128.00');
INSERT INTO `order_detail` VALUES ('28', '经典酸菜鮰鱼', 'https://example.com/dish2.jpg', '7', '52', null, '中辣', '2', '132.00');
INSERT INTO `order_detail` VALUES ('29', '米饭', 'https://example.com/rice.jpg', '6', '49', null, null, '3', '6.00');
INSERT INTO `order_detail` VALUES ('30', '王老吉', 'https://example.com/drink.jpg', '7', '46', null, null, '3', '18.00');
INSERT INTO `order_detail` VALUES ('31', '北冰洋', 'https://example.com/drink2.jpg', '8', '47', null, null, '2', '8.00');
INSERT INTO `order_detail` VALUES ('32', '清炒小油菜', 'https://example.com/veg.jpg', '6', '54', null, null, '1', '18.00');
INSERT INTO `order_detail` VALUES ('33', '清炒小油菜', 'https://example.com/veg.jpg', '21', '54', null, null, '1', '18.00');
INSERT INTO `order_detail` VALUES ('34', '经典酸菜鮰鱼', 'https://example.com/dish2.jpg', '21', '52', null, '中辣', '1', '66.00');
INSERT INTO `order_detail` VALUES ('35', '蜀味水煮草鱼', 'https://example.com/dish3.jpg', '21', '53', null, '特辣', '1', '38.00');
INSERT INTO `order_detail` VALUES ('36', '清炒小油菜', 'https://example.com/veg.jpg', '22', '54', null, null, '1', '18.00');
INSERT INTO `order_detail` VALUES ('37', '经典酸菜鮰鱼', 'https://example.com/dish2.jpg', '22', '52', null, '中辣', '1', '66.00');
INSERT INTO `order_detail` VALUES ('38', '蜀味水煮草鱼', 'https://example.com/dish3.jpg', '22', '53', null, '特辣', '1', '38.00');
INSERT INTO `order_detail` VALUES ('39', '素食套餐', 'https://example.com/veg.jpg', '22', null, '5', null, '1', '68.00');
INSERT INTO `order_detail` VALUES ('40', '素食套餐', 'https://example.com/veg.jpg', '22', null, '5', null, '1', '68.00');
INSERT INTO `order_detail` VALUES ('41', '北冰洋', 'https://example.com/drink2.jpg', '23', '47', null, null, '2', '8.00');
INSERT INTO `order_detail` VALUES ('42', '馒头', 'https://example.com/bread.jpg', '24', '50', null, null, '2', '2.00');
INSERT INTO `order_detail` VALUES ('43', '蒜蓉娃娃菜', 'https://example.com/veg2.jpg', '25', '55', null, null, '1', '18.00');

-- ----------------------------
-- Table structure for `setmeal`
-- ----------------------------
DROP TABLE IF EXISTS `setmeal`;
CREATE TABLE `setmeal` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `category_id` bigint NOT NULL COMMENT '菜品分类id',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '套餐名称',
  `price` decimal(10,2) NOT NULL COMMENT '套餐价格',
  `status` int DEFAULT '1' COMMENT '售卖状态 0:停售 1:起售',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '描述信息',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '图片',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint DEFAULT NULL COMMENT '创建人',
  `update_user` bigint DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_setmeal_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='套餐';

-- ----------------------------
-- Records of setmeal
-- ----------------------------
INSERT INTO `setmeal` VALUES ('1', '13', '酸菜鱼双人餐', '128.00', '1', '老坛酸菜鱼+清炒小油菜+米饭2份+酸梅汤2杯，经典搭配', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/setmeal/酸菜鱼双人餐.png', '2026-03-23 09:40:39', '2026-03-23 16:08:44', '1', '1');
INSERT INTO `setmeal` VALUES ('2', '13', '牛蛙单人餐', '98.00', '1', '金汤酸菜牛蛙+米饭1份+可乐1杯，一人食首选', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/setmeal/牛蛙单人餐.png', '2026-03-23 09:40:39', '2026-03-23 09:40:39', '1', '1');
INSERT INTO `setmeal` VALUES ('3', '15', '烤鱼商务餐', '168.00', '1', '江团鱼2斤+清炒西兰花+鸡蛋汤+米饭3份，商务聚餐优选', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/setmeal/烤鱼商务餐.png', '2026-03-23 09:40:39', '2026-03-23 09:40:39', '1', '1');
INSERT INTO `setmeal` VALUES ('4', '15', '经典川菜套餐', '198.00', '1', '东坡肘子+梅菜扣肉+水煮鱼+汤类+米饭4份，宴请必备', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/setmeal/经典川菜套餐.png', '2026-03-23 09:40:39', '2026-03-23 09:40:39', '1', '1');
INSERT INTO `setmeal` VALUES ('6', '13', '素食套餐', '68.00', '1', '清炒小油菜+蒜蓉娃娃菜+鸡蛋汤+馒头2个，健康素食', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/setmeal/素食套餐.png', '2026-03-23 09:40:39', '2026-03-23 09:40:39', '1', '1');

-- ----------------------------
-- Table structure for `setmeal_dish`
-- ----------------------------
DROP TABLE IF EXISTS `setmeal_dish`;
CREATE TABLE `setmeal_dish` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `setmeal_id` bigint DEFAULT NULL COMMENT '套餐id',
  `dish_id` bigint DEFAULT NULL COMMENT '菜品id',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '菜品名称 （冗余字段）',
  `price` decimal(10,2) DEFAULT NULL COMMENT '菜品单价（冗余字段）',
  `copies` int DEFAULT NULL COMMENT '菜品份数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='套餐菜品关系';

-- ----------------------------
-- Records of setmeal_dish
-- ----------------------------
INSERT INTO `setmeal_dish` VALUES ('1', '1', '51', '老坛酸菜鱼', '56.00', '1');
INSERT INTO `setmeal_dish` VALUES ('2', '1', '54', '清炒小油菜', '18.00', '1');
INSERT INTO `setmeal_dish` VALUES ('3', '1', '49', '米饭', '2.00', '2');
INSERT INTO `setmeal_dish` VALUES ('4', '1', '46', '王老吉', '6.00', '2');
INSERT INTO `setmeal_dish` VALUES ('5', '2', '62', '金汤酸菜牛蛙', '88.00', '1');
INSERT INTO `setmeal_dish` VALUES ('6', '2', '49', '米饭', '2.00', '1');
INSERT INTO `setmeal_dish` VALUES ('7', '2', '47', '北冰洋', '4.00', '1');
INSERT INTO `setmeal_dish` VALUES ('8', '3', '66', '江团鱼2斤', '119.00', '1');
INSERT INTO `setmeal_dish` VALUES ('9', '3', '56', '清炒西兰花', '18.00', '1');
INSERT INTO `setmeal_dish` VALUES ('10', '3', '68', '鸡蛋汤', '4.00', '1');
INSERT INTO `setmeal_dish` VALUES ('11', '3', '49', '米饭', '2.00', '3');
INSERT INTO `setmeal_dish` VALUES ('12', '4', '59', '东坡肘子', '138.00', '1');
INSERT INTO `setmeal_dish` VALUES ('13', '4', '60', '梅菜扣肉', '58.00', '1');
INSERT INTO `setmeal_dish` VALUES ('14', '4', '53', '蜀味水煮草鱼', '38.00', '1');
INSERT INTO `setmeal_dish` VALUES ('15', '4', '68', '鸡蛋汤', '4.00', '1');
INSERT INTO `setmeal_dish` VALUES ('16', '4', '49', '米饭', '2.00', '4');
INSERT INTO `setmeal_dish` VALUES ('17', '5', '54', '清炒小油菜', '18.00', '1');
INSERT INTO `setmeal_dish` VALUES ('18', '5', '55', '蒜蓉娃娃菜', '18.00', '1');
INSERT INTO `setmeal_dish` VALUES ('19', '5', '68', '鸡蛋汤', '4.00', '1');
INSERT INTO `setmeal_dish` VALUES ('20', '5', '50', '馒头', '1.00', '2');
INSERT INTO `setmeal_dish` VALUES ('53', '1', '1', null, null, null);

-- ----------------------------
-- Table structure for `shopping_cart`
-- ----------------------------
DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '图片',
  `user_id` bigint NOT NULL COMMENT '主键',
  `dish_id` bigint DEFAULT NULL COMMENT '菜品id',
  `setmeal_id` bigint DEFAULT NULL COMMENT '套餐id',
  `dish_flavor` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '口味',
  `number` int NOT NULL DEFAULT '1' COMMENT '数量',
  `amount` decimal(10,2) NOT NULL COMMENT '金额',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='购物车';

-- ----------------------------
-- Records of shopping_cart
-- ----------------------------
INSERT INTO `shopping_cart` VALUES ('11', '牛蛙单人餐', 'https://example.com/setmeal2.jpg', '1', null, '2', null, '1', '98.00', '2026-03-01 00:00:00');
INSERT INTO `shopping_cart` VALUES ('12', '烤鱼商务餐', 'https://example.com/setmeal3.jpg', '2', null, '3', null, '1', '168.00', '2026-03-01 00:00:00');
INSERT INTO `shopping_cart` VALUES ('13', '老坛酸菜鱼', 'https://example.com/dish1.jpg', '3', '51', null, '中辣', '2', '112.00', '2026-03-01 00:00:00');
INSERT INTO `shopping_cart` VALUES ('14', '米饭', 'https://example.com/rice.jpg', '3', '49', null, null, '3', '6.00', '2026-03-01 00:00:00');
INSERT INTO `shopping_cart` VALUES ('15', '雪花啤酒', 'https://example.com/beer.jpg', '3', '48', null, null, '2', '8.00', '2026-03-01 00:00:00');
INSERT INTO `shopping_cart` VALUES ('16', '清炒小油菜', 'https://example.com/veg.jpg', '3', '54', null, null, '2', '36.00', '2026-03-01 00:00:00');
INSERT INTO `shopping_cart` VALUES ('17', '经典川菜套餐', 'https://example.com/setmeal4.jpg', '3', null, '4', null, '1', '198.00', '2026-03-01 00:00:00');
INSERT INTO `shopping_cart` VALUES ('18', '素食套餐', 'https://example.com/setmeal5.jpg', '3', null, '5', null, '3', '68.00', '2026-03-01 00:00:00');
INSERT INTO `shopping_cart` VALUES ('19', '王老吉', 'https://example.com/drink.jpg', '2', '46', null, null, '3', '18.00', '2026-03-01 00:00:00');
INSERT INTO `shopping_cart` VALUES ('20', '酸菜鱼双人餐', 'https://example.com/setmeal1.jpg', '3', null, '1', null, '2', '128.00', '2026-03-01 00:00:00');
INSERT INTO `shopping_cart` VALUES ('21', '王老吉', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/41bfcacf-7ad4-4927-8b26-df366553a94c.png', '2', '46', null, '少辣,不香菜', '1', '6.00', '2026-03-28 12:04:36');
INSERT INTO `shopping_cart` VALUES ('22', '王老吉', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/41bfcacf-7ad4-4927-8b26-df366553a94c.png', '3', '46', null, '少辣,不香菜1', '2', '6.00', '2026-03-28 12:11:22');
INSERT INTO `shopping_cart` VALUES ('23', '素食套餐', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/setmeal/素食套餐.png', '3', null, '6', null, '4', '68.00', '2026-03-28 14:29:37');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `openid` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '微信用户唯一标识',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手机号',
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '性别',
  `id_number` varchar(18) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '身份证号',
  `avatar` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='用户信息';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'oU1Yx5TqBqJxqYzKzYqJxqYzKzYq', '张三', '13800138001', '1', '110101199001011234', 'https://example.com/avatar1.jpg', '2026-03-22 11:56:35');
INSERT INTO `user` VALUES ('2', 'oU1Yx5TqBqJxqYzKzYqJxqYzKzYw', '李四', '13800138002', '2', '110101199002021234', 'https://example.com/avatar2.jpg', '2026-03-25 11:56:35');
INSERT INTO `user` VALUES ('3', 'oU1Yx5TqBqJxqYzKzYqJxqYzKzYx', '王五', '13800138003', '1', '110101199003031234', 'https://example.com/avatar3.jpg', '2026-03-27 11:56:35');
INSERT INTO `user` VALUES ('39', null, '张三1', null, null, '110101199001011234', null, null);
