package com.kuaigui.yueche.driver.main.mine;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuaigui.yueche.driver.R;
import com.kuaigui.yueche.driver.base.adapter.BaseRecycleAdapter;
import com.kuaigui.yueche.driver.base.adapter.BaseRecycleHolder;
import com.kuaigui.yueche.driver.base.view.BaseActivity;
import com.kuaigui.yueche.driver.bean.RootMyPerformanceBean;
import com.kuaigui.yueche.driver.constant.Api;
import com.kuaigui.yueche.driver.enums.OrderStatus;
import com.kuaigui.yueche.driver.mvc.BaseController;
import com.kuaigui.yueche.driver.mvc.IResultView;
import com.kuaigui.yueche.driver.okhttp.OkRequestParams;
import com.kuaigui.yueche.driver.util.AbJsonUtil;
import com.kuaigui.yueche.driver.util.BaseUtils;
import com.kuaigui.yueche.driver.util.CustomProgress;
import com.kuaigui.yueche.driver.util.NumberUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: zengxc
 * 描述:
 * 时间: 2018/10/11 15:27
 */

public class MyPerformanceActivity extends BaseActivity implements IResultView {

    @BindView(R.id.back_iv)
    ImageView          mBackIv;
    @BindView(R.id.title_tv)
    TextView           mTitleTv;
    @BindView(R.id.performance_count_tv)
    TextView           mPerformanceCountTv;
    @BindView(R.id.order_count_tv)
    TextView           mOrderCountTv;
    @BindView(R.id.order_failure_count_tv)
    TextView           mOrderFailureCountTv;
    @BindView(R.id.withhold_tv)
    TextView           mWithholdTv;
    @BindView(R.id.order_rv)
    RecyclerView       mOrderRv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    private BaseController mController;

    private int pageNumber = 1;

    private List<RootMyPerformanceBean.DataBean.OrdersBean>               mOrderList = new ArrayList<>();
    private BaseRecycleAdapter<RootMyPerformanceBean.DataBean.OrdersBean> mOrderAdapter;

    @Override
    public int setLayout() {
        return R.layout.activity_my_performance;
    }

    @Override
    public void initView() {
        mBackIv.setVisibility(View.VISIBLE);
        mBackIv.setImageResource(R.drawable.back);
        mTitleTv.setText(R.string.my_performance);
        mOrderRv.setNestedScrollingEnabled(false);
    }

    @Override
    public void initData() {
        mController = new BaseController(this);
        setListener();
        getLastData();
    }

    private void setListener() {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getMoreData();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getLastData();
            }
        });
        mOrderAdapter = new BaseRecycleAdapter<RootMyPerformanceBean.DataBean.OrdersBean>(this,
                R.layout.item_performance_normal, mOrderList) {
            @Override
            public void convert(BaseRecycleHolder helper, RootMyPerformanceBean.DataBean.OrdersBean item, int position) {
                TextView mOrderTypeTv = helper.getView(R.id.order_type_tv);
                TextView mOrderStatusTv = helper.getView(R.id.order_status_tv);
                TextView mTimeTv = helper.getView(R.id.time_tv);
                TextView mStartTv = helper.getView(R.id.start_tv);
                TextView mEndTv = helper.getView(R.id.end_tv);
                TextView mOrderPriceTv = helper.getView(R.id.order_price_tv);
                TextView mOrderTipTv = helper.getView(R.id.order_tip_tv);

                mOrderTypeTv.setText("专车");
                OrderStatus status = OrderStatus.getOrderStatus(item.getState());
//                mOrderStatusTv.setText(status.mOrderDes);
                // TODO: 2018/10/12 0012 订单状态到底是以哪个为准
                mOrderStatusTv.setText(item.getStateStr());
                mOrderStatusTv.setTextColor(status.mTextColor);

//                mTimeTv.setText(AbDateUtil.getStringByFormat(item.getOrderTime(), dateFormatYMDHMS2));
                mTimeTv.setText(item.getOrderTimeStr());
                mStartTv.setText(item.getDeparture());
                mEndTv.setText(item.getDestination());
                // TODO: 2018/10/11 0011 缺少订单金额 &说明
//                mOrderPriceTv.setText("¥"+NumberUtil.getDoubleNoRoundNumberFormat());
//                mOrderTipTv.setText();
            }
        };
        mOrderRv.setLayoutManager(new LinearLayoutManager(this));
        mOrderRv.setAdapter(mOrderAdapter);
    }

    @OnClick({R.id.back_iv, R.id.agreement_performance_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.agreement_performance_tv:
                // TODO: 2018/10/11 0011 跳转绩效奖励规则
                break;
        }
    }

    private void getMyPerformance() {
        OkRequestParams params = new OkRequestParams();
        params.put("token", BaseUtils.getToken());
        params.put("page", pageNumber);
        params.put("pageSize", 10);
        mController.doPostRequest(Api.MY_PERFORMANCE, "waitOrder", params);
    }

    /**
     * 获取最新列表
     */
    private void getLastData() {
        mRefreshLayout.setNoMoreData(false);
        pageNumber = 1;
        getMyPerformance();
    }

    /**
     * 加载更多
     */
    private void getMoreData() {
        pageNumber++;
        getMyPerformance();
    }

    @Override
    public void showLoadView(String url) {
        CustomProgress.show(this);
    }

    @Override
    public void showResultView(String url, String type, String content) {
        if (url.equals(Api.MY_PERFORMANCE)) {
            CustomProgress.disMiss();
            RootMyPerformanceBean performanceBean = AbJsonUtil.fromJson(content, RootMyPerformanceBean.class);
            if (performanceBean != null) {
                if (performanceBean.getCode() == Api.CODE_SUCCESS) {
                    if (performanceBean.getData() != null) {
                        mPerformanceCountTv.setText(NumberUtil.getDoubleNoRoundNumberFormat(performanceBean.getData().getRewardTotal()));
                        mOrderCountTv.setText(performanceBean.getData().getOrderNums() + "次\n接单次数");
                        mOrderFailureCountTv.setText(performanceBean.getData().getUncompletedOrderNums() + "次\n未完成次数");
                        mWithholdTv.setText("- " + NumberUtil.getDoubleNoRoundNumberFormat(performanceBean.getData()
                                .getDeductionTotal()) + "\n未完成扣款");

                        if (!mRefreshLayout.isLoading()) {
                            mOrderList.clear();
                        }

                        mRefreshLayout.setNoMoreData(performanceBean.getData().getOrders().size() == 0);

                        mOrderList.addAll(performanceBean.getData().getOrders());
                        mOrderAdapter.updateView(mOrderList);
                    }
                } else {

                }
            }

            if (mRefreshLayout != null && mRefreshLayout.isRefreshing()) {
                mRefreshLayout.finishRefresh();
            }
            if (mRefreshLayout != null && mRefreshLayout.isLoading()) {
                mRefreshLayout.finishLoadMore();
            }
        }
    }

    @Override
    public void showErrorView(String url, Exception e, String msg) {
        CustomProgress.disMiss();
        if (mRefreshLayout != null && mRefreshLayout.isRefreshing()) {
            mRefreshLayout.finishRefresh();
        }
        if (mRefreshLayout != null && mRefreshLayout.isLoading()) {
            mRefreshLayout.finishLoadMore();
        }
    }

    @Override
    public void showProgressView(String url, int progress) {

    }

}
