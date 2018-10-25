package com.kuaigui.yueche.driver.main.order.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.amap.api.services.core.LatLonPoint;

/**
 * @author: hekang
 * @description:
 * @date: 2018/10/25 13:50
 */
public class JumpNavigatUtils {

    public static void openGaodeMapToGuide(Context context, LatLonPoint start, String startAdress, LatLonPoint end, String endAddress) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        String url = "androidamap://route?sourceApplication=amap&slat=" + start.getLatitude() + "&slon=" + start.getLongitude()
                + "&dlat=" + end.getLatitude() + "&dlon=" + end.getLongitude() + "&dname=" + endAddress + "&dev=0&t=1";
        Uri uri = Uri.parse(url);
        //将功能Scheme以URI的方式传入data
        intent.setData(uri);
        //启动该页面即可
        context.startActivity(intent);
    }

    public static void openBrowserToGuide(Context context, LatLonPoint start, String startAdress, LatLonPoint end, String endAddress) {
        String url = "androidamap://route?sourceApplication=amap&slat=" + start.getLatitude() + "&slon=" + start.getLongitude()
                + "&dlat=" + end.getLatitude() + "&dlon=" + end.getLongitude() + "&dname=" + endAddress + "&dev=0&t=1";
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

}
