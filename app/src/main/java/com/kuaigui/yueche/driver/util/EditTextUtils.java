package com.kuaigui.yueche.driver.util;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

/**
 * 作者: zengxc
 * 描述:
 * 时间: 2018/10/12 15:51
 */

public class EditTextUtils {

    public static void setEditTextInhibitInputSpace(EditText editText, final int maxlength) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" ") || source.equals("\n") || dend > (maxlength - 1)) {
                    return "";
                } else {
                    return null;
                }
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    public static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) || ((codePoint >= 0x20) && codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

}
