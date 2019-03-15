package com.kuaigui.yueche.driver.main.about;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuaigui.yueche.driver.MyApplication;
import com.kuaigui.yueche.driver.R;
import com.kuaigui.yueche.driver.base.view.BaseActivity;
import com.kuaigui.yueche.driver.constant.Constant;
import com.kuaigui.yueche.driver.main.protocol.ProtocolActivity;
import com.kuaigui.yueche.driver.util.AbAppUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: zengxc
 * 描述:
 * 时间: 2018/10/09 18:16
 */

public class AboutActivity extends BaseActivity {

    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.app_version_tv)
    TextView mAppVersionTv;
    @BindView(R.id.agreement_version_tv)
    TextView mAgreementVersionTv;

    @Override
    public int setLayout() {
        return R.layout.activity_about;
    }

    @Override
    public void initView() {
        mBackIv.setVisibility(View.VISIBLE);
        mBackIv.setImageResource(R.drawable.back);
        mTitleTv.setText(R.string.about_us);
        mAppVersionTv.setText("版本号V" + AbAppUtil.getPackageInfo(MyApplication.getApp()).versionName);
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.back_iv, R.id.agreement_ll, R.id.contact_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.agreement_ll:
                startActivity(ProtocolActivity.getCallIntent(this));
                break;
            case R.id.contact_ll:
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ Constant.TEL));//跳转到拨号界面，同时传递电话号码
                startActivity(dialIntent);
                break;
        }
    }

}
