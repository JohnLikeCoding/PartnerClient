package com.kuaigui.yueche.driver.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 作者: zengxc
 * 描述:
 * 时间: 2018/10/11 16:28
 */

public class NumberUtil {

    public static String getDoubleNoRoundNumberFormat(double number) {
        DecimalFormat format = new DecimalFormat("0.00");
        format.setRoundingMode(RoundingMode.FLOOR);
        return format.format(number);
    }

}
