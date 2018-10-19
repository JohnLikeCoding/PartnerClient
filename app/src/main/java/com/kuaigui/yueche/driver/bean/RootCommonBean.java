package com.kuaigui.yueche.driver.bean;

/**
 * 作者: zengxc
 * 描述:
 * 时间: 2018/10/11 12:59
 */

public class RootCommonBean {

    /**
     * code : 200
     * message : operate successfully
     */

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
