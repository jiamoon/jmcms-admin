#后台用户表
DROP TABLE IF EXISTS `sys_admin`;
CREATE TABLE IF NOT EXISTS `sys_admin` (
  id VARCHAR(32) PRIMARY KEY COMMENT '主键id',
  user_code INT UNIQUE COMMENT '用户编码',
  username VARCHAR(30) UNIQUE COMMENT '用户名',
  password VARCHAR(32) COMMENT '用户密码',
  salt VARCHAR(6) COMMENT '密码加密盐',
  avatar VARCHAR(100) COMMENT '头像',
  last_login_time DATETIME COMMENT '最后登录时间',
  last_login_ip VARCHAR(15) COMMENT '最后登录IP',
  status int COMMENT '账号状态',
  del_flag int DEFAULT 0 COMMENT '删除标志'
)