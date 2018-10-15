package com.kuaigui.yueche.driver.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kuaigui.yueche.driver.MyApplication;
import com.kuaigui.yueche.driver.R;
import com.kuaigui.yueche.driver.base.view.BaseActivity;
import com.kuaigui.yueche.driver.bean.RootCommonBean;
import com.kuaigui.yueche.driver.constant.Api;
import com.kuaigui.yueche.driver.constant.Constant;
import com.kuaigui.yueche.driver.main.mine.AdviceActivity;
import com.kuaigui.yueche.driver.main.mine.MyPerformanceActivity;
import com.kuaigui.yueche.driver.main.order.activity.OrderActivity;
import com.kuaigui.yueche.driver.main.setting.SettingActivity;
import com.kuaigui.yueche.driver.mvc.BaseController;
import com.kuaigui.yueche.driver.mvc.IResultView;
import com.kuaigui.yueche.driver.okhttp.OkRequestParams;
import com.kuaigui.yueche.driver.util.AbJsonUtil;
import com.kuaigui.yueche.driver.util.AbToastUtil;
import com.kuaigui.yueche.driver.util.BaseUtils;
import com.kuaigui.yueche.driver.util.CustomProgress;
import com.kuaigui.yueche.driver.util.NumberUtil;
import com.kuaigui.yueche.driver.widget.CircleImageView;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, IResultView {

    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.online_tip_ll)
    LinearLayout mOnlineTipLl;
    @BindView(R.id.local_tv)
    TextView mLocalTv;
    @BindView(R.id.current_time_tv)
    TextView mCurrentTimeTv;
    @BindView(R.id.map)
    View mMap;
    @BindView(R.id.month_performance_tv)
    TextView mMonthPerformanceTv;
    @BindView(R.id.total_performance_tv)
    TextView mTotalPerformanceTv;
    @BindView(R.id.online_btn)
    Button mOnlineBtn;
    @BindView(R.id.order_btn)
    Button mOrderBtn;

    CircleImageView mHeadIv;
    TextView mDriverNumTv;

    private BaseController mController;

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mTitleTv.setText(R.string.login_title);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();

        mNavView.setNavigationItemSelectedListener(this);

        mMonthPerformanceTv.setText("¥" + NumberUtil.getDoubleNoRoundNumberFormat(BaseUtils.getMonthValue()));
        mTotalPerformanceTv.setText("¥" + NumberUtil.getDoubleNoRoundNumberFormat(BaseUtils.getTotalValue()));

        View headView = mNavView.getHeaderView(0);
        mHeadIv = headView.findViewById(R.id.head_iv);
        mDriverNumTv = headView.findViewById(R.id.driver_num_tv);
        mDriverNumTv.setText(BaseUtils.getLicenseId());

        initAction(BaseUtils.isOnline());
    }

    private void initAction(boolean isOnline) {
        mOnlineTipLl.setVisibility(isOnline ? View.GONE : View.VISIBLE);
        mOrderBtn.setEnabled(isOnline);
        mOrderBtn.setBackground(isOnline ? ContextCompat.getDrawable(this, R.drawable.conner_red_bg)
                : ContextCompat.getDrawable(this, R.drawable.conner_gray_bg));
        mOnlineBtn.setText(isOnline ? "下班" : "上班");
    }

    @Override
    public void initData() {
        mController = new BaseController(this);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

//        mDrawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId()) {
            case R.id.nav_performance:
                startActivity(new Intent(this, MyPerformanceActivity.class));
                break;
            case R.id.nav_advice:
                startActivity(new Intent(this, AdviceActivity.class));
                break;
            case R.id.nav_call:
                MainActivityPermissionsDispatcher.showCallPhoneWithPermissionCheck(this);
                break;
            case R.id.nav_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }

        return true;
    }

    @OnClick({R.id.mine_iv, R.id.online_btn, R.id.order_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_iv:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.online_btn:
                if (BaseUtils.isOnline()) {
                    offline();
                } else {
                    online();
                }
                break;
            case R.id.order_btn:
                startActivity(new Intent(this, OrderActivity.class));
                break;
        }
    }

    private void online() {
        OkRequestParams params = new OkRequestParams();
        params.put("mobile", BaseUtils.getMobile());
        params.put("longitude", "113.880714");
        params.put("latitude", "22.560353");
        mController.doPostRequest(Api.ONLINE, "online", params);

    }

    private void offline() {
        OkRequestParams params = new OkRequestParams();
        params.put("mobile", BaseUtils.getMobile());
        params.put("longitude", "113.880714");
        params.put("latitude", "22.560353");
        mController.doPostRequest(Api.OFFLINE, "online", params);
    }

    @Override
    public void showLoadView(String url) {
        CustomProgress.show(this);
    }

    @Override
    public void showResultView(String url, String type, String content) {
        CustomProgress.disMiss();
        switch (url) {
            case Api.ONLINE:
                RootCommonBean onlineBean = AbJsonUtil.fromJson(content, RootCommonBean.class);
                if (onlineBean != null) {
                    if (onlineBean.getCode() == Api.CODE_SUCCESS) {
                        BaseUtils.saveOnline();
                        initAction(true);
                    } else {
                        AbToastUtil.showToast(this, onlineBean.getMessage());
                    }
                }
                break;
            case Api.OFFLINE:
                RootCommonBean offlineBean = AbJsonUtil.fromJson(content, RootCommonBean.class);
                if (offlineBean != null) {
                    if (offlineBean.getCode() == Api.CODE_SUCCESS) {
                        BaseUtils.offline();
                        initAction(false);
                    } else {
                        AbToastUtil.showToast(this, offlineBean.getMessage());
                    }
                }
                break;
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
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
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
