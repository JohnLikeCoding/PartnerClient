package com.kuaigui.yueche.driver.main.login;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuaigui.yueche.driver.R;
import com.kuaigui.yueche.driver.base.view.BaseActivity;
import com.kuaigui.yueche.driver.bean.RootConfigPwdBean;
import com.kuaigui.yueche.driver.constant.Api;
import com.kuaigui.yueche.driver.main.MainActivity;
import com.kuaigui.yueche.driver.mvc.BaseController;
import com.kuaigui.yueche.driver.mvc.IResultView;
import com.kuaigui.yueche.driver.okhttp.OkRequestParams;
import com.kuaigui.yueche.driver.util.AbJsonUtil;
import com.kuaigui.yueche.driver.util.AbToastUtil;
import com.kuaigui.yueche.driver.util.BaseUtils;
import com.kuaigui.yueche.driver.util.CustomProgress;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: zengxc
 * @description:
 * @date: 2019/03/15 19:44
 */
public class ConfigPwdActivity extends BaseActivity implements IResultView {

    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.title_tv)
    TextView  mTitleTv;
    @BindView(R.id.pwd_et)
    EditText  mPwdEt;
    @BindView(R.id.pwd_again_et)
    EditText  mPwdAgainEt;

    private BaseController mController;

    @Override
    public int setLayout() {
        return R.layout.activity_cofig_pwd;
    }

    @Override
    public void initView() {
        mBackIv.setImageResource(R.drawable.back);
        mTitleTv.setText(R.string.config_pwd_title);
    }

    @Override
    public void initData() {
        mController = new BaseController(this);
    }

    @OnClick({R.id.back_iv, R.id.login_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.login_btn:
                configPwd();
                break;
        }
    }

    private void configPwd() {
        String pwd = mPwdEt.getText().toString().trim();
        String pwdAgain = mPwdAgainEt.getText().toString().trim();
        if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(pwdAgain)) {
            AbToastUtil.showToast(this, "请输入密码！");
            return;
        }
        if (!pwd.equals(mPwdAgainEt)) {
            AbToastUtil.showToast(this, "两次密码输入不一致，请重新输入！");
            return;
        }
        OkRequestParams params = new OkRequestParams();
        params.put("mobile", BaseUtils.getMobile());
        params.put("password", pwd);
        mController.doPostRequest(Api.CONFIG_PWD, "configPwd", params);
    }

    @Override
    public void showLoadView(String url) {
        CustomProgress.show(this);
    }

    @Override
    public void showResultView(String url, String type, String content) {
        CustomProgress.disMiss();
        if (url.equals(Api.CONFIG_PWD)) {
            RootConfigPwdBean configPwdBean = AbJsonUtil.fromJson(content, RootConfigPwdBean.class);
            if (configPwdBean != null) {
                if (configPwdBean.getCode() == Api.CODE_SUCCESS) {
                    startActivity(new Intent(ConfigPwdActivity.this, MainActivity.class));
                    finish();
                } else {
                    AbToastUtil.showToast(this, configPwdBean.getMessage());
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
