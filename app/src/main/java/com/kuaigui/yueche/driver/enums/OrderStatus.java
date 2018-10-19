package com.kuaigui.yueche.driver.enums;

import android.graphics.Color;

/**
 * 作者: zengxc
 * 描述:
 * 时间: 2018/10/11 17:14
 */

public enum OrderStatus {

    ORDER_UN_KNOW(-1, Color.parseColor("#808080"), "未知"),
    ORDER_CREATE(1, Color.parseColor("#808080"), "发起"),
    ORDER_DISTRIBUTE(2, Color.parseColor("#808080"), "系统派单成功"),
    ORDER_DRIVER_TAKE(3, Color.parseColor("#808080"), "司机接单"),
    ORDER_CUSTOMER_CANCEL(4, Color.parseColor("#808080"), "乘客撤销"),
    ORDER_CUSTOMER_BOARDING(5, Color.parseColor("#808080"), "乘客上车"),
    ORDER_CUSTOMER_WAIT_PAY(6, Color.parseColor("#808080"), "乘客下车待扣款"),
    ORDER_CUSTOMER_EVALUATE(7, Color.parseColor("#808080"), "扣款完成待评价"),
    ORDER_COMPLETE(8, Color.parseColor("#808080"), "完成");

    public int mOrderStatus;
    public int mTextColor;
    public String mOrderDes;

    OrderStatus(int orderStatus, int textColor, String orderDes) {
        mOrderStatus = orderStatus;
        mTextColor = textColor;
        mOrderDes = orderDes;
    }

    public static OrderStatus getOrderStatus(int orderStatus) {
        for (OrderStatus status : values()) {
            if (orderStatus == status.mOrderStatus) {
                return status;
            }
        }
        return ORDER_UN_KNOW;
    }


}
