package com.kuaigui.yueche.driver.main.order.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Space;
import android.widget.TextView;

import com.kuaigui.yueche.driver.R;
import com.kuaigui.yueche.driver.base.adapter.BaseRecycleAdapter;
import com.kuaigui.yueche.driver.base.adapter.BaseRecycleHolder;
import com.kuaigui.yueche.driver.base.view.BaseFragment;
import com.kuaigui.yueche.driver.bean.RootOrderListBean;
import com.kuaigui.yueche.driver.constant.Api;
import com.kuaigui.yueche.driver.constant.TypeConstant;
import com.kuaigui.yueche.driver.main.order.activity.ConfirmOrderActivity;
import com.kuaigui.yueche.driver.mvc.BaseController;
import com.kuaigui.yueche.driver.mvc.IResultView;
import com.kuaigui.yueche.driver.okhttp.OkRequestParams;
import com.kuaigui.yueche.driver.util.AbJsonUtil;
import com.kuaigui.yueche.driver.util.BaseUtils;
import com.kuaigui.yueche.driver.util.CustomProgress;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者: zengxc
 * 描述:
 * 时间: 2018/10/10 14:55
 */

public class WaitOrderFragment extends BaseFragment implements IResultView {

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.order_rv)
    RecyclerView       mOrderRv;

    private View    view;
    /**
     * 标志位，标志已经初始化完成
     */
    private boolean isPrepared;
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    private boolean mHasLoadedOnce;

    private Unbinder unbinder;

    private BaseController mController;

    private List<RootOrderListBean.DataBean>               mWaitOrderList = new ArrayList<>();
    private BaseRecycleAdapter<RootOrderListBean.DataBean> mWaitOrderAdapter;

    private              int    pageNumber     = 1;
    private final static String ARGS_LONGITUDE = "args_longitude";
    private final static String ARGS_LATITUDE  = "args_latitude";

    public static WaitOrderFragment newInstance(String longitude, String latitude) {
        Bundle args = new Bundle();
        args.putString(ARGS_LONGITUDE, longitude);
        args.putString(ARGS_LATITUDE, latitude);
        WaitOrderFragment fragment = new WaitOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private String  curLongitude;
    private String  curLatitude;
    private Handler mHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        curLongitude = getArguments().getString(ARGS_LONGITUDE);
        curLatitude = getArguments().getString(ARGS_LATITUDE);
        mHandler = new Handler(Looper.getMainLooper());
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_order, container, false);
            unbinder = ButterKnife.bind(this, view);
            initViews();
            isPrepared = true;
            lazyLoad();
            setListener();
        }
        return view;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }
        initData();
    }

    @Override
    protected void initViews() {
        mController = new BaseController(this);
    }

    private Timer mOrderTimer;

    @Override
    protected void initData() {
        getLastData();
    }

    public void startTask() {
        if (mOrderTimer == null) {
            mOrderTimer = new Timer();
            MyOrderTask task = new MyOrderTask();
            //20s刷新一次，刚进来不刷新
            mOrderTimer.schedule(task, 20 * 1000, 20 * 1000);
        }
    }

    public void cancelTask() {
        if (mOrderTimer != null) {
            mOrderTimer.cancel();
            mOrderTimer = null;
        }
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
        mWaitOrderAdapter = new BaseRecycleAdapter<RootOrderListBean.DataBean>(getActivity(),
                R.layout.item_wait_order, mWaitOrderList) {
            @Override
            public void convert(BaseRecycleHolder helper, final RootOrderListBean.DataBean item, int position) {
                Space mSpaceTop = helper.getView(R.id.space_top);
                TextView mOrderTypeTv = helper.getView(R.id.order_type_tv);
                TextView mOrderConfirmTv = helper.getView(R.id.order_confirm_tv);
                TextView mTimeTv = helper.getView(R.id.time_tv);
                TextView mDistanceTv = helper.getView(R.id.distance_tv);
                TextView mStartTv = helper.getView(R.id.start_tv);
                TextView mEndTv = helper.getView(R.id.end_tv);

                mSpaceTop.setVisibility(position == 0 ? View.VISIBLE : View.GONE);

                mOrderTypeTv.setText("专车");
//                mTimeTv.setText(AbDateUtil.getStringByFormat(item.getOrderTime(), dateFormatYMDHMS2));
                mTimeTv.setText(item.getOrderTimeStr());
                mDistanceTv.setText(item.getDistance() + "");//显示规则是什么
                mStartTv.setText(item.getDeparture());
                mEndTv.setText(item.getDestination());

                mOrderConfirmTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ConfirmOrderActivity.class);
                        intent.putExtra("orderData", item);
                        startActivity(intent);
                    }
                });

            }
        };
        mOrderRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mOrderRv.setAdapter(mWaitOrderAdapter);
    }

    private void getOrderList() {
        OkRequestParams params = new OkRequestParams();
        params.put("token", BaseUtils.getToken());
        params.put("longitude", curLongitude);
        params.put("latitude", curLatitude);
        params.put("state", TypeConstant.WAIT_ORDER);
        params.put("page", pageNumber);
        params.put("pageSize", 10);
        mController.doPostRequest(Api.ORDER, "waitOrder", params);
    }

    /**
     * 获取最新列表
     */
    public void getLastData() {
        mRefreshLayout.setNoMoreData(false);
        pageNumber = 1;
        getOrderList();
    }

    /**
     * 加载更多
     */
    private void getMoreData() {
        pageNumber++;
        getOrderList();
    }

    @Override
    public void showLoadView(String url) {
        CustomProgress.disMiss();
    }

    @Override
    public void showResultView(String url, String type, String content) {
        if (url.equals(Api.ORDER)) {
            CustomProgress.disMiss();
            RootOrderListBean orderListBean = AbJsonUtil.fromJson(content, RootOrderListBean.class);
            if (orderListBean != null) {
                if (orderListBean.getCode() == Api.CODE_SUCCESS) {
                    mHasLoadedOnce = true;

                    if (mRefreshLayout == null || !mRefreshLayout.isLoading()) {
                        mWaitOrderList.clear();
                    }
                    if (mRefreshLayout != null)
                        mRefreshLayout.setNoMoreData(orderListBean.getData().size() == 0);
                    mWaitOrderList.addAll(orderListBean.getData());
                    mWaitOrderAdapter.updateView(mWaitOrderList);
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

    @Override
    public void onDestroyView() {
        OkHttpUtils.getInstance().cancelTag(this);
        cancelTask();
        super.onDestroyView();
        unbinder.unbind();
    }

    private Runnable refreshRunnable = new Runnable() {
        @Override
        public void run() {
            getLastData();
        }
    };

    /**
     * 实现定时刷新订单信息
     */
    private class MyOrderTask extends TimerTask {

        @Override
        public void run() {
            mHandler.post(refreshRunnable);
        }
    }

}
