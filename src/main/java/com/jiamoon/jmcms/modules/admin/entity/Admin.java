package com.jiamoon.jmcms.modules.admin.entity;

import com.jiamoon.jmcms.common.entity.DataEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * 管理员账号
 */
@Data
@Table(name = "sys_admin")
public class Admin extends DataEntity<Admin> {
    /**
     * 用户编号
     */
    @Column(name = "user_code")
    private Integer userCode;
    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;
    /**
     * 用户密码
     */
    @Column(name = "password")
    private String password;
    /**
     * 密码加密盐
     */
    @Column(name = "salt")
    private String salt;
    /**
     * 头像
     */
    @Column(name = "avatar")
    private String avatar;
    /**
     * 最后登录时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;
    /**
     * 最后登录IP
     */
    @Column(name = "last_login_ip")
    private String lastLoginIp;
    /**
     * 状态(1、正常；-1、账号被禁用)
     */
    @Column(name = "status")
    private Integer status;
}
