package com.kuaigui.yueche.driver.main.order.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuaigui.yueche.driver.R;
import com.kuaigui.yueche.driver.base.view.BaseActivity;
import com.kuaigui.yueche.driver.bean.RootCommonBean;
import com.kuaigui.yueche.driver.constant.Api;
import com.kuaigui.yueche.driver.constant.TypeConstant;
import com.kuaigui.yueche.driver.mvc.BaseController;
import com.kuaigui.yueche.driver.mvc.IResultView;
import com.kuaigui.yueche.driver.okhttp.OkRequestParams;
import com.kuaigui.yueche.driver.util.AbJsonUtil;
import com.kuaigui.yueche.driver.util.AbToastUtil;
import com.kuaigui.yueche.driver.util.CustomProgress;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: zengxc
 * 描述:
 * 时间: 2018/10/11 10:27
 */

public class ReasonActivity extends BaseActivity implements IResultView {

    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.reason_detail_et)
    EditText mReasonDetailEt;

    private int cancelType;
    private String orderNo;
    private BaseController mController;

    @Override
    public int setLayout() {
        return R.layout.activity_reason;
    }

    @Override
    public void initView() {
        mBackIv.setVisibility(View.VISIBLE);
        mBackIv.setImageResource(R.drawable.back);
        mTitleTv.setText(R.string.reason);
    }

    @Override
    public void initData() {
        mController = new BaseController(this);
        orderNo = getIntent().getStringExtra("orderNo");
        cancelType = getIntent().getIntExtra("cancelType", -1);
    }

    @OnClick({R.id.back_iv, R.id.post_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.post_btn:
                cancelOrder();
                break;
        }
    }

    private void cancelOrder() {
        String reason = mReasonDetailEt.getText().toString().trim();
        if (TextUtils.isEmpty(reason)) {
            AbToastUtil.showToast(this, "请输入撤销原因");
            return;
        }
        OkRequestParams params = new OkRequestParams();
        params.put("orderNo", orderNo);
        params.put("reason", reason);
        params.put("operator", TypeConstant.CANCEL_DRIVER);
        params.put("type", cancelType);
        mController.doPostRequest(Api.CANCEL_ORDER, "cancelOrder", params);
    }

    @Override
    public void showLoadView(String url) {
        CustomProgress.show(this);
    }

    @Override
    public void showResultView(String url, String type, String content) {
        if (url.equals(Api.CANCEL_ORDER)) {
            CustomProgress.disMiss();
            RootCommonBean cancelBean = AbJsonUtil.fromJson(content, RootCommonBean.class);
            if (cancelBean != null) {
                if (cancelBean.getCode() == Api.CODE_SUCCESS) {
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

}
