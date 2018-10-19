package com.kuaigui.yueche.driver.main.order.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.kuaigui.yueche.driver.MyApplication;
import com.kuaigui.yueche.driver.R;
import com.kuaigui.yueche.driver.base.view.BaseActivity;
import com.kuaigui.yueche.driver.bean.RootCommonBean;
import com.kuaigui.yueche.driver.bean.RootOrderListBean;
import com.kuaigui.yueche.driver.constant.Api;
import com.kuaigui.yueche.driver.constant.Constant;
import com.kuaigui.yueche.driver.mvc.BaseController;
import com.kuaigui.yueche.driver.mvc.IResultView;
import com.kuaigui.yueche.driver.okhttp.OkRequestParams;
import com.kuaigui.yueche.driver.util.AbJsonUtil;
import com.kuaigui.yueche.driver.util.AbToastUtil;
import com.kuaigui.yueche.driver.util.CustomProgress;
import com.kuaigui.yueche.driver.widget.CircleImageView;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 作者: zengxc
 * 描述:
 * 时间: 2018/10/12 10:53
 */
@RuntimePermissions
public class SendCustomerActivity extends BaseActivity implements IResultView {


    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.head_iv)
    CircleImageView mHeadIv;
    @BindView(R.id.name_tv)
    TextView mNameTv;
    @BindView(R.id.start_tv)
    TextView mStartTv;
    @BindView(R.id.end_tv)
    TextView mEndTv;
    @BindView(R.id.local_tv)
    TextView mLocalTv;
    @BindView(R.id.time_tv)
    TextView mTimeTv;
    @BindView(R.id.map)
    View mMap;

    private RootOrderListBean.DataBean mOrderData;

    private BaseController mController;

    @Override
    public int setLayout() {
        return R.layout.activity_send_customer;
    }

    @Override
    public void initView() {
        mTitleTv.setText("接乘客");
    }

    @Override
    public void initData() {
        mController = new BaseController(this);

        mOrderData = getIntent().getParcelableExtra("orderData");

        mLocalTv.setText("当前位置");
        mTimeTv.setText("6分钟");

        mStartTv.setText(mOrderData.getDeparture());
        mEndTv.setText(mOrderData.getDestination());
    }

    @OnClick({R.id.call_iv, R.id.confirm_order_tv, R.id.nav_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.call_iv:
                SendCustomerActivityPermissionsDispatcher.showCallPhoneWithPermissionCheck(this);
                break;
            case R.id.confirm_order_tv:
                confirmSendCustomer();
                break;
            case R.id.nav_ll:
                // TODO: 2018/10/12 0012 导航
                break;
        }
    }

    private void confirmSendCustomer() {
        OkRequestParams params = new OkRequestParams();
        params.put("orderNo", mOrderData.getOrderNo());
        params.put("longitude", "113.880714");
        params.put("latitude", "22.560353");
        mController.doPostRequest(Api.CONFIRM_SEND, "confirmSendCustomer", params);
    }

    @Override
    public void showLoadView(String url) {
        CustomProgress.show(this);
    }

    @Override
    public void showResultView(String url, String type, String content) {
        if (url.equals(Api.CONFIRM_SEND)) {
            CustomProgress.disMiss();
            RootCommonBean pickupCustomerBean = AbJsonUtil.fromJson(content, RootCommonBean.class);
            if (pickupCustomerBean != null) {
                if (pickupCustomerBean.getCode() == Api.CODE_SUCCESS) {
                    finish();
                }
            }
        }
    }

    @Override
    public void showErrorView(String url, Exception e, String msg) {
        CustomProgress.disMiss();
    }

    @Override
    public void showProgressView(String url, int progress) {

    }

    @NeedsPermission(Manifest.permission.CALL_PHONE)
    void showCallPhone() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Constant.TEL));
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SendCustomerActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale(Manifest.permission.CALL_PHONE)
    void showRationaleForCallPhone(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(R.string.permission_call_phone_des)
                .show();
    }

    @OnPermissionDenied(Manifest.permission.CALL_PHONE)
    void onCallPhoneDenied() {
        AbToastUtil.showToast(MyApplication.getApp(), R.string.open_authority_in_call);
    }

    @OnNeverAskAgain(Manifest.permission.CALL_PHONE)
    void onCallPhoneNeverAskAgain() {
        AbToastUtil.showToast(MyApplication.getApp(), R.string.open_authority_in_call);
    }

}
