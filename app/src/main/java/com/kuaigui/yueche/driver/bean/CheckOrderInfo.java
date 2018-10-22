package com.kuaigui.yueche.driver.bean;

/**
 * @author: hekang
 * @description:
 * @date: 2018/10/22 22: 18
 */
public class CheckOrderInfo {

    /**
     * code : 200
     * message : operate successfully
     * data : {"state":1}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * state : 1
         */

        private int state;

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
