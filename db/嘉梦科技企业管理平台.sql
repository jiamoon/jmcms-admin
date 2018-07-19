#后台用户表
DROP TABLE IF EXISTS `sys_admin`;
CREATE TABLE IF NOT EXISTS `sys_admin` (
  id VARCHAR(32) PRIMARY KEY COMMENT '主键id',
  userCode INT UNIQUE COMMENT '用户编码',
  username VARCHAR(30) UNIQUE COMMENT '用户名',
  password VARCHAR(32) COMMENT '用户密码',
  status int COMMENT '账号状态',
  del_flag int DEFAULT 0 COMMENT '删除标志'
)