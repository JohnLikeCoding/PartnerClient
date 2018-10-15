package com.kuaigui.yueche.driver.base.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

import com.autolayout.AutoFrameLayout;
import com.autolayout.AutoLinearLayout;
import com.autolayout.AutoRelativeLayout;
import com.autolayout.expand.AutoActionMenuItemView;
import com.autolayout.expand.AutoAppBarLayout;
import com.autolayout.expand.AutoCardView;
import com.autolayout.expand.AutoCollapsingToolbarLayout;
import com.autolayout.expand.AutoNestedScrollView;
import com.autolayout.expand.AutoRadioGroup;
import com.autolayout.expand.AutoScrollView;
import com.autolayout.expand.AutoTabLayout;
import com.autolayout.expand.AutoTableLayout;
import com.autolayout.expand.AutoTableRow;
import com.autolayout.expand.AutoToolbar;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {

    private static final String LAYOUT_LINEARLAYOUT = "LinearLayout";
    private static final String LAYOUT_FRAMELAYOUT = "FrameLayout";
    private static final String LAYOUT_RELATIVELAYOUT = "RelativeLayout";
    private static final String ACTION_MENU_ITEM_VIEW = "android.support.v7.view.menu.ActionMenuItemView";
    private static final String LAYOUT_APPBARLAYOUT = "android.support.design.widget.AppBarLayout";
    private static final String LAYOUT_CARDVIEW = "android.support.v7.widget.CardView";
    private static final String LAYOUT_COLLAPSINGTOOLBARLAYOUT = "android.support.design.widget.CollapsingToolbarLayout";
    private static final String LAYOUT_RADIOGROUP = "RadioGroup";
    private static final String LAYOUT_SCROLLVIEW = "ScrollView";
    private static final String LAYOUT_NESTEDSCROLLVIEW = "android.support.v4.widget.NestedScrollView";
    private static final String LAYOUT_TABLAYOUT = "android.support.design.widget.TabLayout";
    private static final String LAYOUT_TABLELAYOUT = "TableLayout";
    private static final String LAYOUT_TABLEROW = "TableRow";
    private static final String LAYOUT_TOOLBAR = "android.support.v7.widget.Toolbar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (setLayout() != 0) {
            setContentView(setLayout());
        } else {
            finish();
        }
        ButterKnife.bind(this);
        initView();
        initData();
    }

    public abstract int setLayout();

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;

        if (name.equals(LAYOUT_FRAMELAYOUT)) {
            view = new AutoFrameLayout(context, attrs);
        }

        if (name.equals(LAYOUT_LINEARLAYOUT)) {
            view = new AutoLinearLayout(context, attrs);
        }

        if (name.equals(LAYOUT_RELATIVELAYOUT)) {
            view = new AutoRelativeLayout(context, attrs);
        }

        if (name.equals(ACTION_MENU_ITEM_VIEW)) {
            view = new AutoActionMenuItemView(context, attrs);
        }

        if (name.equals(LAYOUT_APPBARLAYOUT)) {
            view = new AutoAppBarLayout(context, attrs);
        }

        if (name.equals(LAYOUT_CARDVIEW)) {
            view = new AutoCardView(context, attrs);
        }

        if (name.equals(LAYOUT_COLLAPSINGTOOLBARLAYOUT)) {
            view = new AutoCollapsingToolbarLayout(context, attrs);
        }

        if (name.equals(LAYOUT_RADIOGROUP)) {
            view = new AutoRadioGroup(context, attrs);
        }

        if (name.equals(LAYOUT_SCROLLVIEW)) {
            view = new AutoScrollView(context, attrs);
        }

        if (name.equals(LAYOUT_NESTEDSCROLLVIEW)) {
            view = new AutoNestedScrollView(context, attrs);
        }

        if (name.equals(LAYOUT_TABLAYOUT)) {
            view = new AutoTabLayout(context, attrs);
        }

        if (name.equals(LAYOUT_TABLELAYOUT)) {
            view = new AutoTableLayout(context, attrs);
        }

        if (name.equals(LAYOUT_TABLEROW)) {
            view = new AutoTableRow(context, attrs);
        }

        if (name.equals(LAYOUT_TOOLBAR)) {
            view = new AutoToolbar(context, attrs);
        }

        if (view != null) return view;

        return super.onCreateView(name, context, attrs);
    }

    /**
     * 初始化控件
     * 一般用于:控件id的初始化
     * 上一个界面传过来的值
     * AbAdapter的初始化
     */
    public abstract void initView();

    /**
     * 初始化数据
     * 一般用于:请求数据
     * 数据的交互
     */
    public abstract void initData();

    @Override
    protected void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(this);
        super.onDestroy();
    }
}
