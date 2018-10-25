package com.kuaigui.yueche.driver.util;

/**
 * 创建时间： 2017/8/16.
 * Created by yusheng
 * 功能描述：
 */

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuaigui.yueche.driver.R;
import com.kuaigui.yueche.driver.mvc.IResultView;
import com.zhy.http.okhttp.OkHttpUtils;


public class CustomProgress extends Dialog {
    private static CustomProgress dialog;

    public CustomProgress(Context context) {
        super(context);
    }

    public CustomProgress(Context context, int theme) {
        super(context, theme);
    }

    /**
     * 当窗口焦点改变时调用
     */
    public void onWindowFocusChanged(boolean hasFocus) {
        ImageView imageView = (ImageView) findViewById(R.id.spinnerImageView);
        // 获取ImageView上的动画背景
        AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
        // 开始动画
        spinner.start();
    }

    /**
     * 给Dialog设置提示信息
     *
     * @param message
     */
    public void setMessage(CharSequence message) {
        if (message != null && message.length() > 0) {
            findViewById(R.id.message).setVisibility(View.VISIBLE);
            TextView txt = (TextView) findViewById(R.id.message);
            txt.setText(message);
            txt.invalidate();
        }
    }


    /**
     * 弹出自定义ProgressDialog
     *
     * @param context    上下文
     *                   提示
     * @param cancelable 是否按返回键取消
     */
    public static void show(Context context, boolean cancelable) {
        try {
            if (dialog == null) {
                dialog = new CustomProgress(context, R.style.Custom_Progress);
            }
            dialog.setTitle("");
            dialog.setContentView(R.layout.progress_custom);
            // 按返回键是否取消
            dialog.setCancelable(cancelable);
            // 监听返回键处理
            //  dialog.setOnCancelListener(cancelListener);
            // 设置居中
            dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            // 设置背景层透明度
            lp.dimAmount = 0.2f;
            dialog.getWindow().setAttributes(lp);

            dialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 弹出自定义ProgressDialog
     *
     * @param context 上下文
     *                提示
     *                是否按返回键取消
     */
    public static void show(Context context) {
        try {
            if (dialog == null) {
                dialog = new CustomProgress(context, R.style.Custom_Progress);
            }
            dialog.setTitle("");
            dialog.setContentView(R.layout.progress_custom);
            // 监听返回键处理
            //  dialog.setOnCancelListener(cancelListener);
            // 设置居中
            dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            // 设置背景层透明度
            lp.dimAmount = 0.2f;
            dialog.getWindow().setAttributes(lp);

            dialog.show();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    /**
     * 弹出自定义ProgressDialog
     *
     * @param context    上下文
     * @param message    提示
     * @param cancelable 是否按返回键取消
     */
    public static void show(Context context, CharSequence message, boolean cancelable) {
        try {
            if (dialog == null) {
                dialog = new CustomProgress(context, R.style.Custom_Progress);
            }
            dialog.setTitle("");
            dialog.setContentView(R.layout.progress_custom);
            if (message == null || message.length() == 0) {
                dialog.findViewById(R.id.message).setVisibility(View.GONE);
            } else {
                TextView txt = (TextView) dialog.findViewById(R.id.message);
                dialog.findViewById(R.id.ll_backgorund).setBackgroundResource(R.drawable.progress_custom_bg);
                txt.setText(message);
            }
            // 按返回键是否取消
            dialog.setCancelable(cancelable);
            // 监听返回键处理
            //  dialog.setOnCancelListener(cancelListener);
            // 设置居中
            dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            // 设置背景层透明度
            lp.dimAmount = 0.2f;
            dialog.getWindow().setAttributes(lp);
            // dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            dialog.show();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * 弹出自定义ProgressDialog
     *
     * @param context 上下文
     * @param message 提示
     */
    public static void show(Context context, CharSequence message) {
        try {
            if (dialog == null) {
                dialog = new CustomProgress(context, R.style.Custom_Progress);
            }
            dialog.setTitle("");
            dialog.setContentView(R.layout.progress_custom);
            if (message == null || message.length() == 0) {
                dialog.findViewById(R.id.message).setVisibility(View.GONE);
            } else {
                TextView txt = (TextView) dialog.findViewById(R.id.message);
                dialog.findViewById(R.id.ll_backgorund).setBackgroundResource(R.drawable.progress_custom_bg);
                txt.setText(message);
            }
            // 监听返回键处理
            //  dialog.setOnCancelListener(cancelListener);
            // 设置居中
            dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            // 设置背景层透明度
            lp.dimAmount = 0.2f;
            dialog.getWindow().setAttributes(lp);
            // dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

            dialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 弹出自定义ProgressDialog
     *
     * @param context 上下文
     *                提示
     */
    public static void show(Context context, int stringId) {
        try {
            if (dialog == null) {
                dialog = new CustomProgress(context, R.style.Custom_Progress);
            }
            dialog.setTitle("");
            dialog.setContentView(R.layout.progress_custom);
            if (context.getString(stringId) == null || context.getString(stringId).length() == 0) {
                dialog.findViewById(R.id.message).setVisibility(View.GONE);
            } else {
                TextView txt = (TextView) dialog.findViewById(R.id.message);
                dialog.findViewById(R.id.ll_backgorund).setBackgroundResource(R.drawable.progress_custom_bg);
                txt.setText(context.getString(stringId));
            }

            // 监听返回键处理
            //  dialog.setOnCancelListener(cancelListener);
            // 设置居中
            dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            // 设置背景层透明度
            lp.dimAmount = 0.2f;
            dialog.getWindow().setAttributes(lp);
            // dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

            dialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 弹出自定义ProgressDialog
     *
     * @param context    上下文
     * @param cancelable 是否按返回键取消
     * @param resultView 取消请求标记
     */
    public static void show(Context context, boolean cancelable, final IResultView resultView) {
        try {
            if (dialog == null) {
                dialog = new CustomProgress(context, R.style.Custom_Progress);
            }
            dialog.setTitle("");
            dialog.setContentView(R.layout.progress_custom);

            // 按返回键是否取消
            dialog.setCancelable(cancelable);
            // 监听返回键处理
            //  dialog.setOnCancelListener(cancelListener);
            // 设置居中
            dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            // 设置背景层透明度
            lp.dimAmount = 0.2f;
            dialog.getWindow().setAttributes(lp);
            // dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            dialog.setOnCancelListener(new OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    OkHttpUtils.getInstance().cancelTag(resultView);
                }
            });
            dialog.show();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void disMiss() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}