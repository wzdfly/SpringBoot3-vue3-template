/*
Navicat MySQL Data Transfer

Source Server         : WallDB
Source Server Version : 80033
Source Host           : localhost:3306
Source Database       : template

Target Server Type    : MYSQL
Target Server Version : 80033
File Encoding         : 65001

Date: 2025-07-19 14:28:17
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of email_verification_codes
-- ----------------------------

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
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `mail` (`mail`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '$2a$10$HarvFg21.EQnjkuBpMp5ZeVt7m86AEBMoSBL38UPS7nglcnozgEXK', 'admin@example.com');
