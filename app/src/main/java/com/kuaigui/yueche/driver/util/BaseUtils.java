package com.kuaigui.yueche.driver.util;


import android.text.TextUtils;

import com.kuaigui.yueche.driver.MyApplication;
import com.kuaigui.yueche.driver.bean.RootLoginBean;

public class BaseUtils {

    public static void saveLoginInfo(RootLoginBean.DataBean loginBean) {

        AbSharedUtil.putString(MyApplication.getApp(), "mobile", loginBean.getMobile());
        AbSharedUtil.putString(MyApplication.getApp(), "name", loginBean.getName());
        AbSharedUtil.putString(MyApplication.getApp(), "licenseId", loginBean.getLicenseId());
        AbSharedUtil.putInt(MyApplication.getApp(), "level", loginBean.getLevel());
        AbSharedUtil.putString(MyApplication.getApp(), "monthValue", String.valueOf(loginBean.getMonthValue()));
        AbSharedUtil.putString(MyApplication.getApp(), "totalValue", String.valueOf(loginBean.getTotalValue()));
        AbSharedUtil.putString(MyApplication.getApp(), "token", loginBean.getToken());

        AbSharedUtil.putBoolean(MyApplication.getApp(), "isLogin", true);
    }

    public static void saveOnline() {
        AbSharedUtil.putBoolean(MyApplication.getApp(), "isOnline", true);
    }

    public static void offline() {
        AbSharedUtil.remove(MyApplication.getApp(), "isOnline");
    }

    public static void removeLoginInfo() {
        AbSharedUtil.remove(MyApplication.getApp(), "mobile");
        AbSharedUtil.remove(MyApplication.getApp(), "name");
        AbSharedUtil.remove(MyApplication.getApp(), "licenseId");
        AbSharedUtil.remove(MyApplication.getApp(), "level");
        AbSharedUtil.remove(MyApplication.getApp(), "monthValue");
        AbSharedUtil.remove(MyApplication.getApp(), "totalValue");
        AbSharedUtil.remove(MyApplication.getApp(), "token");

        AbSharedUtil.remove(MyApplication.getApp(), "isLogin");
        AbSharedUtil.remove(MyApplication.getApp(), "isOnline");
    }

    public static String getMobile() {
        return TextUtils.isEmpty(AbSharedUtil.getString(MyApplication.getApp(), "mobile"))
                ? "" : AbSharedUtil.getString(MyApplication.getApp(), "mobile");
    }

    public static String getName() {
        return TextUtils.isEmpty(AbSharedUtil.getString(MyApplication.getApp(), "name"))
                ? "" : AbSharedUtil.getString(MyApplication.getApp(), "name");
    }

    public static String getLicenseId() {
        return TextUtils.isEmpty(AbSharedUtil.getString(MyApplication.getApp(), "licenseId"))
                ? "" : AbSharedUtil.getString(MyApplication.getApp(), "licenseId");
    }

    public static int getLevel() {
        return AbSharedUtil.getInt(MyApplication.getApp(), "level");
    }

    public static double getMonthValue() {
        return TextUtils.isEmpty(AbSharedUtil.getString(MyApplication.getApp(), "monthValue"))
                ? 0.00 : Double.valueOf(AbSharedUtil.getString(MyApplication.getApp(), "monthValue"));
    }

    public static double getTotalValue() {
        return TextUtils.isEmpty(AbSharedUtil.getString(MyApplication.getApp(), "totalValue"))
                ? 0.00 : Double.valueOf(AbSharedUtil.getString(MyApplication.getApp(), "totalValue"));
    }

    public static boolean isLogin() {
        return AbSharedUtil.getBoolean(MyApplication.getApp(), "isLogin", false);
    }

    public static boolean isOnline() {
        return AbSharedUtil.getBoolean(MyApplication.getApp(), "isOnline", false);
    }

    public static String getToken() {
        return TextUtils.isEmpty(AbSharedUtil.getString(MyApplication.getApp(), "token"))
                ? "" : AbSharedUtil.getString(MyApplication.getApp(), "token");
    }

}
