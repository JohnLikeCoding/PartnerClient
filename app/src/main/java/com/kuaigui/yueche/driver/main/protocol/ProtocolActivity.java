package com.kuaigui.yueche.driver.main.protocol;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuaigui.yueche.driver.R;
import com.kuaigui.yueche.driver.base.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: hekang
 * @description:
 * @date: 2018/10/22 21: 48
 */
public class ProtocolActivity extends BaseActivity {

    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.info_tv)
    TextView infoTv;

    @Override
    public int setLayout() {
        return R.layout.activity_protocol;
    }

    public static Intent getCallIntent(Context ctx) {
        Intent intent = new Intent(ctx, ProtocolActivity.class);
        return intent;
    }

    @Override
    public void initView() {
        backIv.setVisibility(View.VISIBLE);
        backIv.setImageResource(R.drawable.back);
        titleTv.setText("用户协议");
        infoTv.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
