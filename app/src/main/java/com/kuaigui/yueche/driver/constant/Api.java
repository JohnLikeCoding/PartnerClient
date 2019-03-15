package com.kuaigui.yueche.driver.constant;

/**
 * 作者: zengxc
 * 描述:
 * 时间: 2018/10/09 17:39
 */

public class Api {

    public static final int CODE_SUCCESS = 200;

    //    public static final String BASE_URL = "http://39.108.114.152/ntcbus/";
//    public static final String BASE_URL = "http://app.quicgo.cn/ntcbus/";
    public static final String BASE_URL = "https://app.quicgo.cn/ntcbus/";

    public static final String GET_CODE = BASE_URL + "common/smscode/send";

    public static final String LOGIN = BASE_URL + "driver/login";

    public static final String ORDER = BASE_URL + "driver/list/unorder";

    public static final String TODAY_PERFORMANCE = BASE_URL + "driver/today/performance";

    public static final String MY_PERFORMANCE = BASE_URL + "driver/info/performance";

    public static final String PROGRESSINFO = BASE_URL + "order/progressinfo/driver";

    public static final String LOCATE = BASE_URL + "driver/locate";

    public static final String ONLINE = BASE_URL + "driver/clockin";

    public static final String OFFLINE = BASE_URL + "driver/offline";

    public static final String CHECK_ORDER = BASE_URL + "order/state/check";

    public static final String PICKUP_ORDER = BASE_URL + "order/pickup";

    public static final String CANCEL_ORDER = BASE_URL + "order/cancel";

    public static final String PICKUP_CUSTOMER = BASE_URL + "driver/receive";

    public static final String CONFIRM_SEND = BASE_URL + "driver/arrive";

    public static final String LOCATION = BASE_URL + "driver/locate";

}
