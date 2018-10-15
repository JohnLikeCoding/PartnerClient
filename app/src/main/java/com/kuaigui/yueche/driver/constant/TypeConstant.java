package com.kuaigui.yueche.driver.constant;

/**
 * 作者: zengxc
 * 描述:
 * 时间: 2018/10/10 15:15
 */

public class TypeConstant {

    //未接单
    public static final int WAIT_ORDER = 0;
    //已接单
    public static final int COMPLETE_ORDER = 1;
    //乘客取消
    public static final int CANCEL_CUSTOMER = 1;
    //司机取消
    public static final int CANCEL_DRIVER = 2;
    //平台取消
    public static final int CANCEL_PLATFORM = 3;
    // 撤销类型（1:乘客提前撤销 2：驾驶员提前撤销 3：平台撤销 4：乘客违约撤销 5：驾驶员违约撤销）
    public static final int CANCEL_TYPE_CUSTOMER_ERALY = 1;
    public static final int CANCEL_TYPE_DRIVER_ERALY = 2;
    public static final int CANCEL_TYPE_PLATFORM_ERALY = 3;
    public static final int CANCEL_TYPE_CUSTOMER_VIOLATE = 4;
    public static final int CANCEL_TYPE_DRIVER_VIOLATE = 5;

}
