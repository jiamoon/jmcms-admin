package com.jiamoon.jmcms.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * ajax请求结果返回对象
 *
 * @param <T>
 */
@Data
public class AjaxResult<T> implements Serializable {
    /**
     * 错误代码。0为没有错误
     */
    private int code;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 额外返回的数据
     */
    private T data;

    /**
     * 返回成功
     *
     * @return
     */
    public static AjaxResult buildSuccess() {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setCode(0);
        return ajaxResult;
    }

    /**
     * 返回成功
     *
     * @return
     */
    public static AjaxResult buildSuccess(String msg) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setCode(0);
        ajaxResult.setMsg(msg);
        return ajaxResult;
    }

    /**
     * 返回成功
     *
     * @return
     */
    public static AjaxResult buildSuccess(String msg, Object data) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setCode(0);
        ajaxResult.setMsg(msg);
        ajaxResult.setData(data);
        return ajaxResult;
    }
}
