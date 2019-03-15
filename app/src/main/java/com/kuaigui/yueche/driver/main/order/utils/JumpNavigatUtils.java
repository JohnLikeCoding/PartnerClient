package com.kuaigui.yueche.driver.main.order.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.amap.api.services.core.LatLonPoint;
import com.kuaigui.yueche.driver.util.AbToastUtil;

/**
 * @author: hekang
 * @description:
 * @date: 2018/10/25 13:50
 */
public class JumpNavigatUtils {

    public static void openGaodeMapToGuide(Context context, LatLonPoint start, String startAdress, LatLonPoint end, String endAddress) {

        StringBuffer stringBuffer = new StringBuffer("androidamap://route?sourceApplication=").append("amap");
        stringBuffer.append("&dlat=").append(end.getLatitude())
                .append("&dlon=").append(end.getLongitude())
                .append("&dname=").append(endAddress)
                .append("&dev=").append(0)
                .append("&t=").append(0);

        Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse(stringBuffer.toString()));
        intent.setPackage("com.autonavi.minimap");
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            AbToastUtil.showToast(context, "启动导航失败");
            e.printStackTrace();
        }
    }

    public static void openBrowserToGuide(Context context, LatLonPoint start, String startAdress, LatLonPoint end, String endAddress) {


        String url = "androidamap://route?sourceApplication=amap&slat=" + start.getLatitude() + "&slon=" + start.getLongitude()
                + "&dlat=" + end.getLatitude() + "&dlon=" + end.getLongitude() + "&dname=" + endAddress + "&dev=0&t=1";
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "text/html");
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            AbToastUtil.showToast(context, "启动导航失败");
            e.printStackTrace();
        }


    }

}
