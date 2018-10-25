package com.kuaigui.yueche.driver.main.order;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import com.kuaigui.yueche.driver.constant.Api;
import com.kuaigui.yueche.driver.mvc.IResultModel;
import com.kuaigui.yueche.driver.okhttp.OkRequestParams;

/**
 * @author: hekang
 * @description:
 * @date: 2018/10/22 22: 20
 */
public class CheckOrderService extends Service {
    private static final String EXTRA_ORDER_NO = "EXTRA_ORDER_NO";
    private String mOrderNo;

    public static boolean startService(Context context, String orderNo) {
        Intent intent = new Intent(context, CheckOrderService.class);
        intent.putExtra(EXTRA_ORDER_NO, orderNo);
        return context.startService(intent) != null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private IResultModel resultModel = new IResultModel();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mOrderNo = intent.getStringExtra(EXTRA_ORDER_NO);

        return super.onStartCommand(intent, flags, startId);
    }

    private void checkOrder() {
        OkRequestParams params = new OkRequestParams();
        params.put("orderNo", mOrderNo);
//        resultModel.doPostRequest(Api.CHECK_ORDER, "check_order", params);
    }

}
