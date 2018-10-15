package com.kuaigui.yueche.driver.main.order.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kuaigui.yueche.driver.R;
import com.kuaigui.yueche.driver.base.view.BaseActivity;
import com.kuaigui.yueche.driver.bean.RootCommonBean;
import com.kuaigui.yueche.driver.bean.RootOrderListBean;
import com.kuaigui.yueche.driver.constant.Api;
import com.kuaigui.yueche.driver.constant.TypeConstant;
import com.kuaigui.yueche.driver.mvc.BaseController;
import com.kuaigui.yueche.driver.mvc.IResultView;
import com.kuaigui.yueche.driver.okhttp.OkRequestParams;
import com.kuaigui.yueche.driver.util.AbJsonUtil;
import com.kuaigui.yueche.driver.util.BaseUtils;
import com.kuaigui.yueche.driver.util.CustomProgress;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: zengxc
 * 描述:
 * 时间: 2018/10/10 17:42
 */

public class ConfirmOrderActivity extends BaseActivity implements IResultView {

    @BindView(R.id.order_type_tv)
    TextView mOrderTypeTv;
    @BindView(R.id.time_tv)
    TextView mTimeTv;//预约订单才有显示，
    @BindView(R.id.distance_tv)
    TextView mDistanceTv;
    @BindView(R.id.start_tv)
    TextView mStartTv;
    @BindView(R.id.end_tv)
    TextView mEndTv;
    @BindView(R.id.confirm_order_btn)
    Button mConfirmOrderBtn;

    private MyTimer myTimer;
    private final long TIME = 60 * 1000L;
    private final long INTERVAL = 1000L;

    private RootOrderListBean.DataBean mOrderData;

    private BaseController mController;

    @Override
    public int setLayout() {
        return R.layout.activity_confirm_order;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        mOrderData = getIntent().getParcelableExtra("orderData");
        mOrderTypeTv.setText("专车");
//                mTimeTv.setText(AbDateUtil.getStringByFormat(item.getOrderTime(), dateFormatYMDHMS2));
//        mTimeTv.setText(mOrderData.getOrderTimeStr());//看UI应该是预约订单才有显示
        mDistanceTv.setText(mOrderData.getDistance());//显示规则是什么
        mStartTv.setText(mOrderData.getDeparture());
        mEndTv.setText(mOrderData.getDestination());

        mController = new BaseController(this);

    }

    @OnClick({R.id.confirm_order_btn, R.id.cancel_order_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.confirm_order_btn:
                pickupOrder();
                break;
            case R.id.cancel_order_btn:
                Intent intent = new Intent(this, ReasonActivity.class);
                intent.putExtra("orderNo", mOrderData.getOrderNo());
                intent.putExtra("cancelType", TypeConstant.CANCEL_TYPE_DRIVER_ERALY);
                startActivity(intent);
                break;
        }
    }

    private void pickupOrder() {
        OkRequestParams params = new OkRequestParams();
        params.put("orderNo", mOrderData.getOrderNo());
        params.put("mobile", BaseUtils.getMobile());
        mController.doPostRequest(Api.PICKUP_ORDER, "pickupOrder", params);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }

    @Override
    protected void onDestroy() {
        cancelTimer();
        super.onDestroy();
    }

    /**
     * 开始倒计时
     */
    private void startTimer() {
        if (myTimer == null) {
            myTimer = new MyTimer(TIME, INTERVAL);
        }
        myTimer.start();
    }

    /**
     * 取消倒计时
     */
    private void cancelTimer() {
        if (myTimer != null) {
            myTimer.cancel();
            myTimer = null;
        }
    }

    @Override
    public void showLoadView(String url) {
        CustomProgress.show(this);
    }

    @Override
    public void showResultView(String url, String type, String content) {
        if (url.equals(Api.PICKUP_ORDER)) {
            CustomProgress.disMiss();
            RootCommonBean pickupBean = AbJsonUtil.fromJson(content, RootCommonBean.class);
            if (pickupBean != null) {
                if (pickupBean.getCode() == Api.CODE_SUCCESS) {
                    Intent intent = new Intent(this, PickupCustomerActivity.class);
                    intent.putExtra("orderData", mOrderData);
                    startActivity(intent);
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

    public class MyTimer extends CountDownTimer {

        public MyTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long time = millisUntilFinished / 1000;
            if (time <= 59) {
                mConfirmOrderBtn.setText(String.format("立即接单(%02ds)", time));
            }
        }

        @Override
        public void onFinish() {
//            mConfirmOrderBtn.setText(R.string.code_get);//超时应该是怎么样的
            cancel();
        }
    }


}
