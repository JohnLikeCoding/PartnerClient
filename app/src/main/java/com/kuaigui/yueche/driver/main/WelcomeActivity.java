package com.kuaigui.yueche.driver.main;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;

import com.kuaigui.yueche.driver.R;
import com.kuaigui.yueche.driver.base.view.BaseActivity;
import com.kuaigui.yueche.driver.main.login.LoginActivity;
import com.kuaigui.yueche.driver.util.BaseUtils;

import java.lang.ref.WeakReference;

/**
 * @author: zengxc
 * @description: 启动页
 * @date: 2018/10/9 0009 15:33
 */
public class WelcomeActivity extends BaseActivity {

    @Override
    public int setLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView() {
        new MyHandler(this).postDelayed(new Runnable() {
            @Override
            public void run() {
 /* if (!flag) {
                    //第一次使用进入新手指导页
                    Intent intent1 = new Intent(WelcomeActivity.this, GuideActivity.class);
                    startActivity(intent1);
                    finish();
                } else {*/
//                    if (webShowAndroid()){
                //非第一次直接进入主页面
                if (BaseUtils.isLogin()) {//校验token
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                }
//                    }
                finish();
//                }
            }
        }, 300);
    }

    @Override
    public void initData() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                android.os.Process.killProcess(android.os.Process.myPid());
                break;
            default:
                break;
        }
        return true;
    }


    static class MyHandler extends Handler {

        WeakReference<WelcomeActivity> mWeakReference;

        public MyHandler(WelcomeActivity activity) {
            mWeakReference = new WeakReference<WelcomeActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mWeakReference.get() == null) {
                return;
            }
        }
    }

}
