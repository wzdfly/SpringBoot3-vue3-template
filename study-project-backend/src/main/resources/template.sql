/*
Navicat MySQL Data Transfer

Source Server         : WallDB
Source Server Version : 80033
Source Host           : localhost:3306
Source Database       : template

Target Server Type    : MYSQL
Target Server Version : 80033
File Encoding         : 65001

Date: 2025-07-20 14:40:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `email_verification_codes`
-- ----------------------------
DROP TABLE IF EXISTS `email_verification_codes`;
CREATE TABLE `email_verification_codes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `code` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `session_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `has_account` tinyint(1) NOT NULL,
  `used` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `expire_time` datetime NOT NULL,
  `used_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_email_session` (`email`,`session_id`,`has_account`),
  KEY `idx_expire_time` (`expire_time`),
  KEY `idx_used` (`used`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of email_verification_codes
-- ----------------------------
INSERT INTO `email_verification_codes` VALUES ('5', '1660448615@qq.com', '325599', '035A2418CFA68266DB005E332D324FC2', '0', '1', '2025-07-19 15:46:54', '2025-07-19 15:49:54', '2025-07-19 15:47:09');
INSERT INTO `email_verification_codes` VALUES ('6', '1660448615@qq.com', '899920', '552057563F933FB9DC83F141D9902909', '1', '1', '2025-07-19 15:52:24', '2025-07-19 15:55:24', '2025-07-19 15:52:42');
INSERT INTO `email_verification_codes` VALUES ('7', '1660448615@qq.com', '780829', '7C6CFCD9AE77567BF24631150C7EC512', '1', '1', '2025-07-19 15:54:18', '2025-07-19 15:57:18', '2025-07-19 15:54:32');
INSERT INTO `email_verification_codes` VALUES ('8', '1660448615@qq.com', '223033', '19CE079A31229410929A624C751D1E4E', '0', '1', '2025-07-19 18:20:25', '2025-07-19 18:23:25', '2025-07-19 18:20:55');

-- ----------------------------
-- Table structure for `persistent_logins`
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins` (
  `username` varchar(64) COLLATE utf8mb4_general_ci NOT NULL,
  `series` varchar(64) COLLATE utf8mb4_general_ci NOT NULL,
  `token` varchar(64) COLLATE utf8mb4_general_ci NOT NULL,
  `last_used` timestamp NOT NULL,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `mail` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `role` varchar(20) COLLATE utf8mb4_general_ci DEFAULT 'USER' COMMENT '用户角色：USER-普通用户，ADMIN-管理员',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `mail` (`mail`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '李四', '$2a$10$Z8vxUQwbjtmIoveeI7ensOorpgaZCLCjfqYaiADYUKmHFn23CPGIW', 'lisi@example.com', 'USER');
INSERT INTO `user` VALUES ('4', 'admin', '$2a$10$DaqPzNIeyGY2fSgKPT49eO7J3Ahf/0IJvmLd2U1p.IWi0aafu2GVq', 'admin@example.com', 'ADMIN');
INSERT INTO `user` VALUES ('5', '王五', '$2a$10$GhsPcpLEUBhazam6TkzUou9asXW9MaGlwxRRCB.XuwE85Z.qDtQSy', '1660448615@qq.com', 'USER');
