package com.jiamoon.jmcms.modules.admin.service;

import com.jiamoon.jmcms.common.service.BaseService;
import com.jiamoon.jmcms.modules.admin.entity.Admin;

public interface IAdminService extends BaseService<Admin> {
    Admin findAdminByUsername(String username);
}
