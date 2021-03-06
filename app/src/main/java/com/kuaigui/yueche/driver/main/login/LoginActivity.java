package com.kuaigui.yueche.driver.main.login;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuaigui.yueche.driver.R;
import com.kuaigui.yueche.driver.base.view.BaseActivity;
import com.kuaigui.yueche.driver.bean.RootCommonBean;
import com.kuaigui.yueche.driver.bean.RootLoginBean;
import com.kuaigui.yueche.driver.constant.Api;
import com.kuaigui.yueche.driver.main.MainActivity;
import com.kuaigui.yueche.driver.mvc.BaseController;
import com.kuaigui.yueche.driver.mvc.IResultView;
import com.kuaigui.yueche.driver.okhttp.OkRequestParams;
import com.kuaigui.yueche.driver.util.AbJsonUtil;
import com.kuaigui.yueche.driver.util.AbToastUtil;
import com.kuaigui.yueche.driver.util.BaseUtils;
import com.kuaigui.yueche.driver.util.CustomProgress;
import com.kuaigui.yueche.driver.util.RegularUtil;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

/**
 * 作者: zengxc
 * 描述:
 * 时间: 2018/10/09 16:03
 */

@RuntimePermissions
public class LoginActivity extends BaseActivity implements IResultView {

    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.title_tv)
    TextView  mTitleTv;
    @BindView(R.id.phone_et)
    EditText  mPhoneEt;
    @BindView(R.id.code_et)
    EditText  mCodeEt;
    @BindView(R.id.code_get_tv)
    TextView  mCodeGetTv;
    @BindView(R.id.vcode_ll)
    View      mVCodeLayout;
    @BindView(R.id.pwd_ll)
    View      mPwdLayout;
    @BindView(R.id.pwd_et)
    EditText  mPwdEt;
    @BindView(R.id.change_login_tv)
    TextView  mChangeLoginTv;

    private BaseController mController;

    private       MyTimer myTimer;
    private final long    TIME       = 60 * 1000L;
    private final long    INTERVAL   = 1000L;
    private       String  mobile;
    private       boolean isPwdLogin = false;

    @Override
    public int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        LoginActivityPermissionsDispatcher.needLocationWithPermissionCheck(this);
        mBackIv.setImageResource(R.drawable.back);
        mTitleTv.setText(R.string.login_title);

    }

    @Override
    public void initData() {
        mController = new BaseController(this);
    }

    @OnClick({R.id.back_iv, R.id.code_get_tv, R.id.login_btn, R.id.change_login_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.code_get_tv:
                mCodeGetTv.setEnabled(false);
                getCode();
                break;
            case R.id.change_login_tv:
                isPwdLogin = !isPwdLogin;
                mChangeLoginTv.setText(isPwdLogin ? getString(R.string.login_vcode_hint) : getString(R.string.login_pwd_hint));
                mVCodeLayout.setVisibility(isPwdLogin ? View.GONE : View.VISIBLE);
                mPwdLayout.setVisibility(isPwdLogin ? View.VISIBLE : View.GONE);
                break;
            case R.id.login_btn:
                login();
                break;
        }
    }

    private void getCode() {
        if (TextUtils.isEmpty(mPhoneEt.getText().toString())
                || !RegularUtil.isMobileExact(mPhoneEt.getText().toString())) {
            AbToastUtil.showToast(this, "请输入正确的手机号");
            return;
        }
        OkRequestParams params = new OkRequestParams();
        params.put("mobile", mPhoneEt.getText().toString());
        mController.doPostRequest(Api.GET_CODE, "getCode", params);
    }

    private void login() {
        mobile = mPhoneEt.getText().toString();
        if (TextUtils.isEmpty(mobile) || !RegularUtil.isMobileExact(mobile)) {
            AbToastUtil.showToast(this, "请输入正确的手机号码！");
            return;
        }
        if (isPwdLogin) {
            if (TextUtils.isEmpty(mPwdEt.getText().toString().trim())) {
                AbToastUtil.showToast(this, "请输入正确的密码！");
                return;
            }

        } else {
            if (TextUtils.isEmpty(mCodeEt.getText().toString())) {
                AbToastUtil.showToast(this, "请输入正确的验证码！");
                return;
            }
        }
        OkRequestParams params = new OkRequestParams();
        params.put("mobile", mobile);
        if (isPwdLogin) {
            params.put("password", mPwdEt.getText().toString().trim());
        } else {
            params.put("vCode", mCodeEt.getText().toString());
        }
        mController.doPostRequest(isPwdLogin ? Api.LOGIN_PWD : Api.LOGIN, "login", params);
    }

    @Override
    public void showLoadView(String url) {
        CustomProgress.show(this);
    }

    @Override
    public void showResultView(String url, String type, String content) {
        CustomProgress.disMiss();
        switch (url) {
            case Api.GET_CODE:
                RootCommonBean codeBean = AbJsonUtil.fromJson(content, RootCommonBean.class);
                if (codeBean != null) {
                    if (codeBean.getCode() == Api.CODE_SUCCESS) {
                        startTimer();
                    } else {
                        mCodeGetTv.setEnabled(true);
                        AbToastUtil.showToast(this, codeBean.getMessage());
                    }
                } else {
                    mCodeGetTv.setEnabled(true);
                }
                break;
            case Api.LOGIN:
                RootLoginBean loginBean = AbJsonUtil.fromJson(content, RootLoginBean.class);
                if (loginBean != null) {
                    if (loginBean.getCode() == Api.CODE_SUCCESS) {
                        BaseUtils.saveLoginInfo(loginBean.getData());
                        if (loginBean.getData().getPwdset() == 1) {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            startActivity(new Intent(LoginActivity.this, ConfigPwdActivity.class));
                        }
                        finish();
                    } else {
                        AbToastUtil.showToast(this, loginBean.getMessage());
                    }
                }
                break;
            case Api.LOGIN_PWD:
                RootLoginBean loginPwdBean = AbJsonUtil.fromJson(content, RootLoginBean.class);
                if (loginPwdBean != null) {
                    if (loginPwdBean.getCode() == Api.CODE_SUCCESS) {
                        BaseUtils.saveLoginInfo(loginPwdBean.getData());
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        AbToastUtil.showToast(this, loginPwdBean.getMessage());
                    }
                }
                break;
        }
    }

    @Override
    public void showErrorView(String url, Exception e, String msg) {
        CustomProgress.disMiss();
        if (url.equals(Api.GET_CODE)) {
            mCodeGetTv.setEnabled(true);
        }
    }

    @Override
    public void showProgressView(String url, int progress) {

    }

    @Override
    protected void onDestroy() {
        cancelTimer();
        if (mDialog != null)
            mDialog.dismiss();
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

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void needLocation() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LoginActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void remindLocation() {
        initDialog();
        mDialog.show();
    }

    private Dialog mDialog;

    private void initDialog() {
        if (mDialog == null)
            mDialog = new AlertDialog.Builder(this)
                    .setTitle("定位权限设置")//设置对话框的标题
                    .setMessage("使用地图功能，需要定位权限")//设置对话框的内容
                    //设置对话框的按钮
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })

                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            goIntentSetting();
                        }
                    })
                    .create();
    }

    private void goIntentSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyTimer extends CountDownTimer {

        public MyTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long time = millisUntilFinished / 1000;

            if (time <= 59) {
                mCodeGetTv.setText(String.format("重新获取(%02ds)", time));
            }
        }

        @Override
        public void onFinish() {
            mCodeGetTv.setEnabled(true);
            mCodeGetTv.setText(R.string.code_get);
            cancel();
        }
    }

}
