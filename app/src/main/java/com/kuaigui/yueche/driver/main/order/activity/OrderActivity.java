package com.kuaigui.yueche.driver.main.order.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.kuaigui.yueche.driver.R;
import com.kuaigui.yueche.driver.base.adapter.BaseFragmentPagerAdapter;
import com.kuaigui.yueche.driver.base.view.BaseActivity;
import com.kuaigui.yueche.driver.main.WelcomeActivity;
import com.kuaigui.yueche.driver.main.login.LoginActivity;
import com.kuaigui.yueche.driver.main.order.fragment.CompleteOrderFragment;
import com.kuaigui.yueche.driver.main.order.fragment.TodayPerformanceDialogFragment;
import com.kuaigui.yueche.driver.main.order.fragment.WaitOrderFragment;
import com.kuaigui.yueche.driver.util.BaseUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: zengxc
 * 描述:
 * 时间: 2018/10/10 09:58
 */

public class OrderActivity extends BaseActivity {

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

    @Override
    public int setLayout() {
        return R.layout.activity_order;
    }

    @Override
    public void initView() {
        mTitleTv.setText(R.string.order_title);
        String longitude = getIntent().getStringExtra(EXTRA_LONGITUDE);
        String latitude = getIntent().getStringExtra(EXTRA_LATITUDE);
        mOrderFragmentList.add(WaitOrderFragment.newInstance(longitude, latitude));
        mOrderFragmentList.add(CompleteOrderFragment.newInstance(longitude, latitude));

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

        mOrderTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tab_item_text).setSelected(true);
                tab.getCustomView().findViewById(R.id.tab_item_indicator).setVisibility(View.VISIBLE);
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

    @Override
    public void initData() {

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

}
