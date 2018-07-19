package com.jiamoon.jmcms.modules.admin.entity;

import com.jiamoon.jmcms.common.entity.DataEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 管理员账号
 */
@Data
@Table(name = "sys_admin")
public class Admin extends DataEntity<Admin> {
    @Column(name = "user_code")
    private Integer userCode;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
}
