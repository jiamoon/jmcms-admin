package com.jiamoon.jmcms.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String id;
    protected int delFlag;
}
