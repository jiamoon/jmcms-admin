package com.jiamoon.jmcms.common.constants;

import lombok.Getter;
import lombok.Setter;

/**
 * 系统api错误代码
 */
public enum SystemApiCode implements IApiCode {
    LOCKED_ACCOUNT(1, "账号已被锁定"),
    DISABLED_ACCOUNT(2, "账号未启用"),;
    @Setter
    @Getter
    private int code;
    @Setter
    @Getter
    private String msg;

    SystemApiCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
