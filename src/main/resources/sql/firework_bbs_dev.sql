/*
 Navicat Premium Data Transfer

 Source Server         : my_db
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : localhost:3306
 Source Schema         : firework_bbs_dev

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 16/05/2023 17:33:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for leaf_alloc
-- ----------------------------
DROP TABLE IF EXISTS `leaf_alloc`;
CREATE TABLE `leaf_alloc`  (
  `biz_tag` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `max_id` bigint NOT NULL DEFAULT 1,
  `step` int NOT NULL,
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT 'auto generate by enum',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`biz_tag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of leaf_alloc
-- ----------------------------
INSERT INTO `leaf_alloc` VALUES ('leaf-segment-test', 4501, 500, 'Test leaf Segment Mode Get Id', '2023-05-15 17:40:49');
INSERT INTO `leaf_alloc` VALUES ('product_code', 1, 100, 'product code', '2023-05-06 09:48:54');
INSERT INTO `leaf_alloc` VALUES ('random_num', 1, 100, 'auto generate by enum', '2023-05-06 09:49:02');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键id，字典值id',
  `dict_type_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '所属字典类型id',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '字典值编码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '字典值名称',
  `ext_1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '扩展字段1',
  `ext_2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '扩展字段2',
  `ext_3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '扩展字段3',
  `ext_4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '扩展字段4',
  `sort` int NULL DEFAULT NULL COMMENT '权重',
  `status` int NULL DEFAULT NULL COMMENT '状态 0-启用 1-禁用',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注描述',
  `create_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_del` int NOT NULL DEFAULT 0 COMMENT '逻辑删除 0-否 1-是',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `dict_type_id`(`dict_type_id`) USING BTREE,
  CONSTRAINT `dict_type_id` FOREIGN KEY (`dict_type_id`) REFERENCES `sys_dict_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '字典值信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('FB000003001', 'FB000002501', 'DICT_TEST_1', '类型1字典值1', NULL, NULL, NULL, NULL, 1, 0, '类型1字典值1', 'FB000001001', '2023-05-12 17:19:29', 'FB000001001', '2023-05-12 17:19:29', 0);
INSERT INTO `sys_dict` VALUES ('FB000003002', 'FB000002501', 'DICT_TEST_2', '类型1字典值2', NULL, NULL, NULL, NULL, 2, 0, '类型1字典值2', 'FB000001001', '2023-05-12 17:19:49', 'FB000001001', '2023-05-12 17:19:49', 0);
INSERT INTO `sys_dict` VALUES ('FB000003003', 'FB000002501', 'DICT_TEST_3', '类型1字典值3', NULL, NULL, NULL, NULL, 3, 0, '类型1字典值3', 'FB000001001', '2023-05-12 17:19:57', 'FB000001001', '2023-05-12 17:19:57', 0);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键id，字典类型id',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '字典类型编码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '字典类型名称',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '字典类型标签 SYSTEM-系统字典 BIZ-业务字典',
  `status` int NULL DEFAULT NULL COMMENT '状态 0-启用 1-禁用',
  `sort` int NULL DEFAULT NULL COMMENT '权重',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注描述',
  `create_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_del` int NOT NULL DEFAULT 0 COMMENT '逻辑删除 0-否 1-是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '字典类型信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES ('FB000002501', 'DICT_TYPE_TEST_1', '字典类型测试1', 'SYSTEM', 0, 1, '字典类型测试1', 'FB000001001', '2023-05-12 17:15:10', 'FB000001001', '2023-05-12 17:15:10', 0);
INSERT INTO `sys_dict_type` VALUES ('FB000002502', 'DICT_TYPE_TEST_2', '字典类型测试2', 'SYSTEM', 0, 2, '字典类型测试2', 'FB000001001', '2023-05-12 17:15:20', 'FB000001001', '2023-05-12 17:15:20', 0);
INSERT INTO `sys_dict_type` VALUES ('FB000002503', 'DICT_TYPE_TEST_3', '字典类型测试3', 'SYSTEM', 0, 3, '字典类型测试3', 'FB000001001', '2023-05-12 17:15:27', 'FB000001001', '2023-05-12 17:15:27', 0);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键id，菜单id',
  `pid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '父级id',
  `pids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '父级id集合',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '菜单编码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '菜单url',
  `menu_flag` int NULL DEFAULT NULL COMMENT '是否是菜单 0-否 1-是',
  `system_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '系统分类（字典）',
  `status` int NULL DEFAULT NULL COMMENT '状态(字典) 0-启用 1-禁用',
  `sort` int NULL DEFAULT NULL COMMENT '权重',
  `levels` int NULL DEFAULT NULL COMMENT '菜单层级',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注信息',
  `create_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_del` int NOT NULL DEFAULT 0 COMMENT '逻辑删除 0-否 1-是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '菜单信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('FB000001501', '0', NULL, 'TEST_1', '', '', NULL, NULL, NULL, 0, NULL, 'aa', NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES ('FB000002001', '0', '[0]', 'TEST_1', '菜单测试1', '/test1', 1, NULL, 0, 1, 1, '菜单测试1', 'FB000001001', '2023-05-11 17:34:33', 'FB000001001', '2023-05-11 17:41:16', 0);
INSERT INTO `sys_menu` VALUES ('FB000002002', 'FB000002001', '[0],[FB000002001]', 'TEST_SUB_1', '菜单二级测试1', '/test1/sub_test1', 1, NULL, 0, 1, 2, '', 'FB000001001', '2023-05-11 17:35:45', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES ('FB000002003', '0', '[0]', 'TEST_2', '菜单测试2', '/test2', 1, NULL, 0, 2, 1, '', 'FB000001001', '2023-05-11 17:38:30', NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键id，角色id',
  `pid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '父级id',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '角色编码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '角色名称',
  `sort` int NULL DEFAULT NULL COMMENT '权重',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注信息',
  `version` int NULL DEFAULT NULL COMMENT '乐观锁',
  `create_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_del` int NOT NULL DEFAULT 0 COMMENT '逻辑删除 0-否 1-是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('FB000000001', '0', 'SYSTEM_ADMIN', '系统管理员', 0, '系统管理员', NULL, NULL, '2023-05-06 11:04:06', NULL, '2023-05-06 11:04:06', 0);
INSERT INTO `sys_role` VALUES ('FB000000002', 'FB000000001', 'BACKSTAGE_ADMIN', '后台管理员', 1, '后台管理员', NULL, NULL, '2023-05-06 11:06:46', NULL, '2023-05-06 11:06:46', 0);
INSERT INTO `sys_role` VALUES ('FB000000003', 'FB000000001', 'NORLMAL_USER', '游客', 2, '普通用户', NULL, NULL, '2023-05-06 11:23:44', NULL, '2023-05-06 11:23:44', 0);
INSERT INTO `sys_role` VALUES ('FB000000004', 'FB000000001', 'VIP_MEMBER', '会员', 3, '会员用户', NULL, NULL, '2023-05-06 11:24:06', NULL, '2023-05-06 11:24:06', 0);
INSERT INTO `sys_role` VALUES ('FB000000005', 'FB000000001', 'MODERATOR', '版主', 4, '版主', NULL, NULL, '2023-05-06 11:24:29', NULL, '2023-05-06 11:24:29', 0);

-- ----------------------------
-- Table structure for sys_role_menu_rel
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu_rel`;
CREATE TABLE `sys_role_menu_rel`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键id',
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '角色id',
  `menu_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '菜单id',
  `create_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_del` int NOT NULL DEFAULT 0 COMMENT '逻辑删除 0-否 1-是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '角色菜单关联关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu_rel
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键，用户id',
  `account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '登录账号',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '密码盐',
  `role_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '角色id',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '头像',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '用户名',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '手机号',
  `gender` int NULL DEFAULT NULL COMMENT '性别 0-女 1-男',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '电子邮箱',
  `status` int NULL DEFAULT NULL COMMENT '状态 0-正常 1-限制 2-冻结 3-删除',
  `version` int NULL DEFAULT NULL COMMENT '乐观锁',
  `create_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '系统用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('FB000000501', '18888888888', '20a3da3c9f0859b3cdc626672d4e9404', 'v0c0', NULL, NULL, '188****8888用户', '18888888888', NULL, NULL, 0, NULL, NULL, '2023-05-09 18:22:56', NULL, NULL);
INSERT INTO `sys_user` VALUES ('FB000001001', '18967668031', 'b26f4da1b19823563403a0a7f34f37c6', '0eh7', NULL, '', 'Yaphets`666', '18967668031', 1, NULL, 0, NULL, NULL, '2023-05-10 11:05:54', 'FB000001001', '2023-05-10 11:59:05');

-- ----------------------------
-- Table structure for tbl_demo
-- ----------------------------
DROP TABLE IF EXISTS `tbl_demo`;
CREATE TABLE `tbl_demo`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '名称',
  `create_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_del` int NOT NULL DEFAULT 0 COMMENT '逻辑删除 0-否 1-是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_demo
-- ----------------------------

-- ----------------------------
-- Table structure for tbl_member
-- ----------------------------
DROP TABLE IF EXISTS `tbl_member`;
CREATE TABLE `tbl_member`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键id，用户id',
  `account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '登录账号',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '头像',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '用户昵称',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '手机号',
  `register_type` int NULL DEFAULT NULL COMMENT '注册类型 0-游客 1-会员',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '真实姓名',
  `gender` int NULL DEFAULT NULL COMMENT '性别 0-女 1-男',
  `birthday` datetime NULL DEFAULT NULL COMMENT '生日',
  `status` int NULL DEFAULT NULL COMMENT '状态 0-正常 1-限制 2-冻结 3-删除',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '上一次登录时间',
  `open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '微信openId',
  `create_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_del` int NOT NULL DEFAULT 0 COMMENT '逻辑删除 0-否 1-是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_member
-- ----------------------------
INSERT INTO `tbl_member` VALUES ('FB000000501', '18888888888', NULL, '188****8888用户', '18888888888', 0, NULL, NULL, NULL, 0, NULL, NULL, 'FB000000501', '2023-05-09 18:22:56', NULL, '2023-05-09 18:22:56', 0);
INSERT INTO `tbl_member` VALUES ('FB000001001', '18967668031', '', 'Yaphets`666', '18967668031', 0, 'wlj', 1, '1997-11-06 08:00:00', 0, '2023-05-10 11:10:50', NULL, 'FB000001001', '2023-05-10 11:05:54', 'FB000001001', '2023-05-10 11:59:05', 0);

-- ----------------------------
-- Table structure for tbl_note
-- ----------------------------
DROP TABLE IF EXISTS `tbl_note`;
CREATE TABLE `tbl_note`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键id，社区贴id',
  `plate_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '板块id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '内容',
  `tags` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '标签',
  `browse_num` int NULL DEFAULT NULL COMMENT '浏览数',
  `like_num` int NULL DEFAULT NULL COMMENT '点赞数',
  `dislike_num` int NULL DEFAULT NULL COMMENT '点踩数',
  `comment_num` int NULL DEFAULT NULL COMMENT '评论数',
  `collect_num` int NULL DEFAULT NULL COMMENT '收藏数',
  `create_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_del` int NOT NULL DEFAULT 0 COMMENT '逻辑删除 0-否 1-是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '社区帖信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_note
-- ----------------------------

-- ----------------------------
-- Table structure for tbl_note_replay
-- ----------------------------
DROP TABLE IF EXISTS `tbl_note_replay`;
CREATE TABLE `tbl_note_replay`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键id，回复帖id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '回复帖信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_note_replay
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
