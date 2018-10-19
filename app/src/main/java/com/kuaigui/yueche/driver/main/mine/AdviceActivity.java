package com.kuaigui.yueche.driver.main.mine;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuaigui.yueche.driver.R;
import com.kuaigui.yueche.driver.base.view.BaseActivity;
import com.kuaigui.yueche.driver.util.AbToastUtil;
import com.kuaigui.yueche.driver.util.EditTextUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: zengxc
 * 描述:
 * 时间: 2018/10/11 12:07
 */

public class AdviceActivity extends BaseActivity {

    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.advice_et)
    EditText mAdviceEt;
    @BindView(R.id.remain_tip_tv)
    TextView mRemainTipTv;

    private int maxLength = 200;
    private int minLength = 10;
    private int charLength;

    @Override
    public int setLayout() {
        return R.layout.activity_advice;
    }

    @Override
    public void initView() {
        mBackIv.setVisibility(View.VISIBLE);
        mBackIv.setImageResource(R.drawable.back);
        mTitleTv.setText(R.string.advice);

        mRemainTipTv.setText("还差" + minLength + "个字可以发布");
    }

    @Override
    public void initData() {
        EditTextUtils.setEditTextInhibitInputSpace(mAdviceEt, maxLength);

        mAdviceEt.addTextChangedListener(new TextWatcher() {
            int sum = 0;
            int selectionEnd = 0;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                sum = before + count;
                mAdviceEt.setSelection(mAdviceEt.length());
                sum = mAdviceEt.length();
                charLength = charSequence.length();
                if (charLength > minLength) {
                    mRemainTipTv.setText("还差0个字可以发布");
//                    AbToastUtil.showToast(AdviceActivity.this, "字数超过" + maxLength + ",请检查输入");
                } else {
                    mRemainTipTv.setText("还差" + (minLength - charSequence.length()) + "个字可以发布");
                }
                if (charLength > maxLength) {
                    AbToastUtil.showToast(AdviceActivity.this, "字数超过" + maxLength + ",请检查输入");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                int index = mAdviceEt.getSelectionStart() - 1;
                if (index > 0) {
                    if (EditTextUtils.isEmojiCharacter(editable.charAt(index))) {
                        Editable edit = mAdviceEt.getText();
                        edit.delete(editable.length() - 2, editable.length());
                        return;
                    }
                }

                if (sum > maxLength) {
                    selectionEnd = mAdviceEt.getSelectionEnd();
                    editable.delete(maxLength, selectionEnd);
                }
            }
        });
    }

    @OnClick({R.id.back_iv, R.id.post_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.post_btn:
                if (charLength > minLength) {
                    // TODO: 2018/10/11 0011 提交意见

                }
                break;
        }
    }

}
