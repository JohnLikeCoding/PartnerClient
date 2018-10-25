/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kuaigui.yueche.driver.util;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kuaigui.yueche.driver.R;


// TODO: Auto-generated Javadoc

/**
 * © 2012 amsoft.cn
 * 名称：AbToastUtil.java
 * 描述：Toast工具类.
 *
 * @author cgd
 * @date 2016-01-11
 */

public class AbToastUtil {

    /**
     * 上下文.
     */
    private static Context mContext = null;

    /**
     * 显示Toast.
     */
    public static final int SHOW_TOAST = 0;


    /**
     * 显示Toast(单例).
     */
    private static Toast toast = null;

    /**
     * 主要Handler类，在线程中可用
     * what：0.提示文本信息
     */
    private static Handler baseHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_TOAST:
                    showToast(mContext, msg.getData().getString("TEXT"));
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 描述：Toast提示文本 ,开发一般用这个
     *
     * @param text 文本
     */
    public static void showToast(Context context, String text) {
        TextView textView = null;
        if (toast == null) {
            toast = new Toast(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_toast_layout_default, null);
            textView = (TextView) view.findViewById(R.id.message);
            textView.setText(text);
            toast.setView(view);
//            toast.setGravity(gravity, xOffset, yOffset);
            toast.setDuration(Toast.LENGTH_SHORT);
        } else {
            textView = (TextView) toast.getView().findViewById(R.id.message);
            textView.setText(text);
        }
        toast.show();
    }

    /**
     * 描述：Toast提示文本.
     *
     * @param resId 文本的资源ID
     */
    public static void showToast(Context context, int resId) {
        showToast(context, context.getString(resId));
    }

    /**
     * 自定义弹出view的toast
     */
    public static void showToast(Context context, String text, int layoutId, int gravity, int xOffset, int yOffset) {
        TextView textView = null;
        if (toast == null) {
            toast = new Toast(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(layoutId, null);
            textView = (TextView) view.findViewById(R.id.message);
            textView.setText(text);
            toast.setView(view);
            toast.setGravity(gravity, xOffset, yOffset);
            toast.setDuration(Toast.LENGTH_SHORT);
        } else {
            textView = (TextView) toast.getView().findViewById(R.id.message);
            textView.setText(text);
        }
        toast.show();
    }

    /**
     * 描述：在线程中提示文本信息.
     *
     * @param resId 要提示的字符串资源ID，消息what值为0,
     */
    public static void showToastInThread(Context context, int resId) {
        mContext = context;
        Message msg = baseHandler.obtainMessage(SHOW_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString("TEXT", context.getResources().getString(resId));
        msg.setData(bundle);
        baseHandler.sendMessage(msg);
    }

    /**
     * 描述：在线程中提示文本信息.
     *
     * @param text 消息
     */
    public static void showToastInThread(Context context, String text) {
        mContext = context;
        Message msg = baseHandler.obtainMessage(SHOW_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString("TEXT", text);
        msg.setData(bundle);
        baseHandler.sendMessage(msg);
    }

}
