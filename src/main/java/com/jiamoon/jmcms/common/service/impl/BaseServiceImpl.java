package com.jiamoon.jmcms.common.service.impl;

import com.jiamoon.jmcms.common.dao.BaseMapper;
import com.jiamoon.jmcms.common.entity.DataEntity;
import com.jiamoon.jmcms.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseServiceImpl<M extends BaseMapper<T>, T extends DataEntity<T>> implements BaseService<T> {
    @Autowired
    protected M mapper;
}
