package com.jiamoon.jmcms.modules.admin.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Setter
@Getter
public class SysResource implements Serializable {
    private String id;
    private String resourceName;
}
