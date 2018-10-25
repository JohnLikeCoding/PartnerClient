package com.kuaigui.yueche.driver.main.order.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.kuaigui.yueche.driver.MyApplication;
import com.kuaigui.yueche.driver.util.BaseUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: hekang
 * @description:获取安装在手机上的软件包名，判断是否有高德或百度地图
 * @date: 2018/10/25 10:42
 */
public class PackageManagerUtil {
    private static PackageManager mPackageManager = MyApplication.getApp().getPackageManager();
    private static List<String> mPackageNames = new ArrayList<>();
    private static final String GAODE_PACKAGE_NAME = "com.autonavi.minimap";
    private static final String BAIDU_PACKAGE_NAME = "com.baidu.BaiduMap";


    private static void initPackageManager() {

        List<PackageInfo> packageInfos = mPackageManager.getInstalledPackages(0);

        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                mPackageNames.add(packageInfos.get(i).packageName);
            }
        }
    }

    public static boolean haveGaodeMap() {
        initPackageManager();
        return mPackageNames.contains(GAODE_PACKAGE_NAME);
    }

    public static boolean haveBaiduMap() {
        initPackageManager();
        return mPackageNames.contains(BAIDU_PACKAGE_NAME);
    }
}
