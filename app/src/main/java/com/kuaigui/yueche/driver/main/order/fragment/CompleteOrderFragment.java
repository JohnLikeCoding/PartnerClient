package com.kuaigui.yueche.driver.main.order.fragment;

import android.os.Bundle;
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
import com.kuaigui.yueche.driver.enums.OrderStatus;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者: zengxc
 * 描述:
 * 时间: 2018/10/10 14:55
 */

public class CompleteOrderFragment extends BaseFragment implements IResultView {

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.order_rv)
    RecyclerView mOrderRv;

    private View view;
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

    private List<RootOrderListBean.DataBean> mCompleteOrderList = new ArrayList<>();
    private BaseRecycleAdapter<RootOrderListBean.DataBean> mCompleteOrderAdapter;

    private int pageNumber = 1;
    private final static String ARGS_LONGITUDE = "args_longitude";
    private final static String ARGS_LATITUDE = "args_latitude";

    public static CompleteOrderFragment newInstance(String longitude, String latitude) {
        Bundle args = new Bundle();
        args.putString(ARGS_LONGITUDE, longitude);
        args.putString(ARGS_LATITUDE, latitude);
        CompleteOrderFragment fragment = new CompleteOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private String curLongitude;
    private String curLatitude;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        curLongitude = getArguments().getString(ARGS_LONGITUDE);
        curLatitude = getArguments().getString(ARGS_LATITUDE);
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

    @Override
    protected void initData() {
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
        mCompleteOrderAdapter = new BaseRecycleAdapter<RootOrderListBean.DataBean>(getActivity(),
                R.layout.item_complete_order, mCompleteOrderList) {
            @Override
            public void convert(BaseRecycleHolder helper, RootOrderListBean.DataBean item, int position) {
                Space mSpaceTop = helper.getView(R.id.space_top);
                TextView mOrderTypeTv = helper.getView(R.id.order_type_tv);
                TextView mOrderStatusTv = helper.getView(R.id.order_status_tv);
                TextView mTimeTv = helper.getView(R.id.time_tv);
                TextView mDistanceTv = helper.getView(R.id.distance_tv);
                TextView mStartTv = helper.getView(R.id.start_tv);
                TextView mEndTv = helper.getView(R.id.end_tv);

                mSpaceTop.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
                mOrderTypeTv.setText("专车");
                OrderStatus status = OrderStatus.getOrderStatus(item.getState());
//                mOrderStatusTv.setText(status.mOrderDes);
                // TODO: 2018/10/12 0012 需不需要虽不同状态变换字体颜色
                mOrderStatusTv.setText(item.getStateStr());
                mOrderStatusTv.setTextColor(status.mTextColor);
//                mTimeTv.setText(AbDateUtil.getStringByFormat(item.getOrderTime(), dateFormatYMDHMS2));
                mTimeTv.setText(item.getOrderTimeStr());
                mDistanceTv.setText(item.getDistance()+"");//显示规则是什么
                mStartTv.setText(item.getDeparture());
                mEndTv.setText(item.getDestination());

            }
        };
        mOrderRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mOrderRv.setAdapter(mCompleteOrderAdapter);

    }

    private void getOrderList() {
        OkRequestParams params = new OkRequestParams();
        params.put("mobile", BaseUtils.getMobile());
        params.put("longitude", "113.880714");//这里不需要经纬度
        params.put("latitude", "22.560353");
        params.put("state", TypeConstant.COMPLETE_ORDER);
        params.put("page", pageNumber);
        params.put("pageSize", 10);
        mController.doPostRequest(Api.ORDER, "completeOrder", params);
    }

    /**
     * 获取最新列表
     */
    private void getLastData() {
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

                    if (!mRefreshLayout.isLoading()) {
                        mCompleteOrderList.clear();
                    }

                    mRefreshLayout.setNoMoreData(orderListBean.getData().size() == 0);
                    mCompleteOrderList.addAll(orderListBean.getData());
                    mCompleteOrderAdapter.updateView(mCompleteOrderList);
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
        super.onDestroyView();
        unbinder.unbind();
    }

}
