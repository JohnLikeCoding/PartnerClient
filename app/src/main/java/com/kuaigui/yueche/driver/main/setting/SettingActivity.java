package com.kuaigui.yueche.driver.main.setting;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuaigui.yueche.driver.MyApplication;
import com.kuaigui.yueche.driver.R;
import com.kuaigui.yueche.driver.base.view.BaseActivity;
import com.kuaigui.yueche.driver.main.about.AboutActivity;
import com.kuaigui.yueche.driver.mvc.BaseController;
import com.kuaigui.yueche.driver.mvc.IResultView;
import com.kuaigui.yueche.driver.util.AbAppUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: zengxc
 * 描述:
 * 时间: 2018/10/11 09:35
 */

public class SettingActivity extends BaseActivity implements IResultView {

    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.app_version_tv)
    TextView mAppVersionTv;

    private BaseController mController;

    @Override
    public int setLayout() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        mBackIv.setVisibility(View.VISIBLE);
        mBackIv.setImageResource(R.drawable.back);
        mTitleTv.setText(R.string.setting);
        mAppVersionTv.setText("V" + AbAppUtil.getPackageInfo(MyApplication.getApp()).versionName);
    }

    @Override
    public void initData() {
        mController = new BaseController(this);
    }

    @OnClick({R.id.back_iv, R.id.app_update_ll, R.id.about_us_ll, R.id.logout_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.app_update_ll:
                // TODO: 2018/10/11 0011 更新
                updateApp();
                break;
            case R.id.about_us_ll:
                startActivity(new Intent(SettingActivity.this, AboutActivity.class));
                break;
            case R.id.logout_btn:
                // TODO: 2018/10/11 0011 退出登录
                logout();
                break;
        }
    }

    private void updateApp() {

    }

    private void logout() {

    }

    @Override
    public void showLoadView(String url) {

    }

    @Override
    public void showResultView(String url, String type, String content) {

    }

    @Override
    public void showErrorView(String url, Exception e, String msg) {

    }

    @Override
    public void showProgressView(String url, int progress) {

    }
}
