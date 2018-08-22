package com.jiamoon.jmcms.modules.admin.service.impl;

import com.jiamoon.jmcms.common.service.impl.BaseServiceImpl;
import com.jiamoon.jmcms.modules.admin.dao.AdminMapper;
import com.jiamoon.jmcms.modules.admin.entity.Admin;
import com.jiamoon.jmcms.modules.admin.service.IAdminService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService extends BaseServiceImpl<AdminMapper, Admin> implements IAdminService {
    @Override
    public Admin findAdminByUsername(String username) {
        Admin admin = new Admin();
        admin.setUsername(username);
        List<Admin> adminList = mapper.select(admin);
        return adminList.size() > 0 ? adminList.get(0) : null;
    }
}
