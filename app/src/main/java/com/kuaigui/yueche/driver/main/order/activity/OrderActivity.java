package com.kuaigui.yueche.driver.main.order.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.kuaigui.yueche.driver.MyApplication;
import com.kuaigui.yueche.driver.R;
import com.kuaigui.yueche.driver.base.adapter.BaseFragmentPagerAdapter;
import com.kuaigui.yueche.driver.base.view.BaseActivity;
import com.kuaigui.yueche.driver.bean.CheckOrderInfo;
import com.kuaigui.yueche.driver.constant.Api;
import com.kuaigui.yueche.driver.enums.OrderStatus;
import com.kuaigui.yueche.driver.main.MainActivity;
import com.kuaigui.yueche.driver.main.login.LoginActivity;
import com.kuaigui.yueche.driver.main.order.fragment.CompleteOrderFragment;
import com.kuaigui.yueche.driver.main.order.fragment.TodayPerformanceDialogFragment;
import com.kuaigui.yueche.driver.main.order.fragment.WaitOrderFragment;
import com.kuaigui.yueche.driver.mvc.BaseController;
import com.kuaigui.yueche.driver.mvc.IResultView;
import com.kuaigui.yueche.driver.okhttp.OkRequestParams;
import com.kuaigui.yueche.driver.util.AbAppUtil;
import com.kuaigui.yueche.driver.util.AbJsonUtil;
import com.kuaigui.yueche.driver.util.AbToastUtil;
import com.kuaigui.yueche.driver.util.BaseUtils;
import com.kuaigui.yueche.driver.util.CustomProgress;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: zengxc
 * 描述:
 * 时间: 2018/10/10 09:58
 */

public class OrderActivity extends BaseActivity implements IResultView {

    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.order_tab_layout)
    TabLayout mOrderTabLayout;
    @BindView(R.id.order_vp)
    ViewPager mOrderVp;

    private List<Fragment> mOrderFragmentList = new ArrayList<>();
    private BaseFragmentPagerAdapter mFragmentPagerAdapter;
    private String[] tabTitles = {"待接单", "已接单"};
    private final static String EXTRA_LONGITUDE = "EXTRA_LONGITUDE";
    private final static String EXTRA_LATITUDE = "EXTRA_LATITUDE";
    private WaitOrderFragment waitOrderFragment;
    private CompleteOrderFragment completeOrderFragment;
    private BaseController mController;

    public static Intent getCallIntent(Context context, String longitude, String latitude) {
        Intent intent = new Intent(context, OrderActivity.class);
        intent.putExtra(EXTRA_LONGITUDE, longitude);
        intent.putExtra(EXTRA_LATITUDE, latitude);
        return intent;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_order;
    }

    @Override
    public void initView() {
        mController = new BaseController(this);
        mTitleTv.setText(R.string.order_title);
        String longitude = getIntent().getStringExtra(EXTRA_LONGITUDE);
        String latitude = getIntent().getStringExtra(EXTRA_LATITUDE);
        waitOrderFragment = WaitOrderFragment.newInstance(longitude, latitude);
        completeOrderFragment = CompleteOrderFragment.newInstance(longitude, latitude);
        mOrderFragmentList.add(waitOrderFragment);
        mOrderFragmentList.add(completeOrderFragment);

        mFragmentPagerAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), mOrderFragmentList);
        mOrderVp.setAdapter(mFragmentPagerAdapter);

        mOrderVp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mOrderTabLayout));
        mOrderTabLayout.setupWithViewPager(mOrderVp);

        for (int i = 0; i < mFragmentPagerAdapter.getCount(); i++) {
            TabLayout.Tab tab = mOrderTabLayout.getTabAt(i);//获得每一个tab
            tab.setCustomView(R.layout.order_tab_item_layout);//给每一个tab设置view
            if (i == 0) {
                // 设置第一个tab的TextView是被选择的样式
                tab.getCustomView().findViewById(R.id.tab_item_text).setSelected(true);//第一个tab被选中
                tab.getCustomView().findViewById(R.id.tab_item_indicator).setVisibility(View.VISIBLE);
            }
            TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tab_item_text);
            textView.setText(tabTitles[i]);//设置tab上的文字
        }
        waitOrderFragment.startTask();
        mOrderTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tab_item_text).setSelected(true);
                tab.getCustomView().findViewById(R.id.tab_item_indicator).setVisibility(View.VISIBLE);
                if (tab.getPosition() == 0) {
                    waitOrderFragment.startTask();
                } else {
                    waitOrderFragment.cancelTask();
                }
                mOrderVp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tab_item_text).setSelected(false);
                tab.getCustomView().findViewById(R.id.tab_item_indicator).setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private List<String> mFilter = new ArrayList<>();

    @Override
    public void initData() {
        mFilter.add(MainActivity.class.getName());
        mFilter.add(OrderActivity.class.getName());
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(ACTION_BROADCAST_CHECK_ORDER));
    }

    /**
     * 监听是否需要取消定时获取已接订单的状态
     */
    public static final String ACTION_BROADCAST_CHECK_ORDER = "action_broadcast_check_order";
    public static final String EXTRA_BROADCAST_ORDER_STATE = "extra_broadcast_order_state";
    public static final String EXTRA_BROADCAST_ORDER_NO = "extra_broadcast_order_no";
    public static final int BROADCAST_ORDER_STATE_DONE = 0;//确认接单
    public static final int BROADCAST_ORDER_STATE_CANCEL = 1;//没有接单
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_BROADCAST_CHECK_ORDER)) {
                int state = intent.getIntExtra(EXTRA_BROADCAST_ORDER_STATE, BROADCAST_ORDER_STATE_CANCEL);
                if (state == BROADCAST_ORDER_STATE_DONE) {
                    String orderNo = intent.getStringExtra(EXTRA_BROADCAST_ORDER_NO);
                    startTask(orderNo);
                }
            }
        }
    };
    private Timer mOrderTimer;

    public void startTask(String orderNo) {
        if (mOrderTimer == null) {
            mOrderTimer = new Timer();
            CheckOrderTask task = new CheckOrderTask(orderNo);
            //2s刷新一次，刚进来不刷新
            mOrderTimer.schedule(task, 1000, 2 * 1000);
        }
    }

    public void cancelTask() {
        if (mOrderTimer != null) {
            mOrderTimer.cancel();
            mOrderTimer = null;
        }
    }

    /**
     * 实现定时更新订单状态
     */
    private class CheckOrderTask extends TimerTask {
        private String orderNo;

        public CheckOrderTask(String orderNo) {
            this.orderNo = orderNo;
        }

        @Override
        public void run() {
            checkOrder(orderNo);
        }
    }


    private void checkOrder(String orderNo) {
        OkRequestParams params = new OkRequestParams();
        params.put("orderNo", orderNo);
        mController.doPostRequest(Api.CHECK_ORDER, "check_order", params);
    }

    @OnClick(R.id.today_performance_ll)
    public void onViewClicked() {
        showTodayPerformance();
    }

    public void showTodayPerformance() {
        FragmentTransaction mFragTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("TodayPerformanceDialogFragment");
        if (fragment != null) {
            mFragTransaction.remove(fragment);
        }
        final TodayPerformanceDialogFragment performance = TodayPerformanceDialogFragment.newInstance();
        performance.setOnConfirmListener(new TodayPerformanceDialogFragment.OnConfirmListener() {
            @Override
            public void onConfirm() {
                // TODO: 2018/10/14  
                //收车后操作
                performance.dismiss();
                BaseUtils.removeLoginInfo();
                Intent intent = new Intent(OrderActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        performance.show(mFragTransaction, "TodayPerformanceDialogFragment");
    }

    @Override
    public void showLoadView(String url) {
        CustomProgress.disMiss();
    }

    @Override
    public void showResultView(String url, String type, String content) {
        if (url.equals(Api.CHECK_ORDER)) {
            CustomProgress.disMiss();
            CheckOrderInfo orderInfo = AbJsonUtil.fromJson(content, CheckOrderInfo.class);
            if (orderInfo != null) {
                if (orderInfo.getCode() == Api.CODE_SUCCESS) {
                    if (orderInfo.getData().getState() == OrderStatus.ORDER_CUSTOMER_CANCEL.mOrderStatus) {
                        AbToastUtil.showToast(this, "当前订单已被乘客取消!");
                        MyApplication.getApp().filterAndFinish(mFilter);
                        waitOrderFragment.getLastData();//刷新页面
                        cancelTask();
                    } else if (orderInfo.getData().getState() == OrderStatus.ORDER_CUSTOMER_EVALUATE.mOrderStatus
                            || orderInfo.getData().getState() == OrderStatus.ORDER_COMPLETE.mOrderStatus) {
                        cancelTask();
                    }
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

    @Override
    protected void onDestroy() {
        cancelTask();
        OkHttpUtils.getInstance().cancelTag(this);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onDestroy();
    }
}
