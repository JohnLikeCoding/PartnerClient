package com.kuaigui.yueche.driver.main.order.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.kuaigui.yueche.driver.R;
import com.kuaigui.yueche.driver.constant.Api;
import com.kuaigui.yueche.driver.mvc.BaseController;
import com.kuaigui.yueche.driver.mvc.IResultView;
import com.kuaigui.yueche.driver.okhttp.OkRequestParams;
import com.kuaigui.yueche.driver.util.BaseUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者: zengxc
 * 描述:
 * 时间: 2018/10/10 16:10
 */

public class TodayPerformanceDialogFragment extends DialogFragment implements IResultView {


    public TodayPerformanceDialogFragment.OnConfirmListener onConfirmListener;

    @BindView(R.id.complete_order_tv)
    TextView mCompleteOrderTv;
    @BindView(R.id.total_work_time_tv)
    TextView mTotalWorkTimeTv;
    @BindView(R.id.peak_time_tv)
    TextView mPeakTimeTv;

    private boolean mIsCallBackDismiss = true;

    private int mBackgroundShadowColor = Color.TRANSPARENT;

    private Unbinder unbinder;

    private BaseController mController;

    public static TodayPerformanceDialogFragment newInstance() {
        TodayPerformanceDialogFragment fragment = new TodayPerformanceDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        final Window window = getDialog().getWindow();
        View view = inflater.inflate(R.layout.dialog_today_performance,
                ((ViewGroup) window.findViewById(android.R.id.content)), false);
        unbinder = ButterKnife.bind(this, view);
        window.setBackgroundDrawable(new ColorDrawable(mBackgroundShadowColor));
        window.setLayout(-1, -2);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.dimAmount = 0.0f;//设置透明背景
        wl.gravity = Gravity.BOTTOM;
        // 设置显示动画
        window.setWindowAnimations(R.style.pop_bottom_anim_style);
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        getDialog().onWindowAttributesChanged(wl);
        setCancelable(mIsCallBackDismiss);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mController = new BaseController(this);
        getPerformance();
    }

    private void getPerformance() {
        OkRequestParams params = new OkRequestParams();
        params.put("token", BaseUtils.getToken());
        mController.doPostRequest(Api.TODAY_PERFORMANCE, "todayPerformance", params);
    }

    @OnClick({R.id.close_iv, R.id.off_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.close_iv:
                dismiss();
                break;
            case R.id.off_btn:
                if (onConfirmListener != null) {
                    onConfirmListener.onConfirm();
                }
                break;
        }
    }

    @Override
    public void showLoadView(String url) {

    }

    @Override
    public void showResultView(String url, String type, String content) {
        if (url.equals(Api.TODAY_PERFORMANCE)) {
            // TODO: 2018/10/14 解析  接口报错
        }
    }

    @Override
    public void showErrorView(String url, Exception e, String msg) {

    }

    @Override
    public void showProgressView(String url, int progress) {

    }

    public interface OnConfirmListener {
        void onConfirm();
    }

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }

    @Override
    public void onDestroyView() {
        OkHttpUtils.getInstance().cancelTag(this);
        unbinder.unbind();
        super.onDestroyView();
    }

}
