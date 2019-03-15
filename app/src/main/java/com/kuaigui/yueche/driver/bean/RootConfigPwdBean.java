package com.kuaigui.yueche.driver.bean;

/**
 * @author: zengxc
 * @description:
 * @date: 2019/03/15 19:57
 */
public class RootConfigPwdBean {


    /**
     * code : 200
     * message : operate successfully
     * data : {"token":"c7709859c3fa4ee7b294c6f707c2d79d","pwdset":1}
     */

    private int      code;
    private String   message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * token : c7709859c3fa4ee7b294c6f707c2d79d
         * pwdset : 1
         */

        private String token;
        private int    pwdset;

        public String getToken() {
            return token == null ? "" : token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getPwdset() {
            return pwdset;
        }

        public void setPwdset(int pwdset) {
            this.pwdset = pwdset;
        }
    }
}
