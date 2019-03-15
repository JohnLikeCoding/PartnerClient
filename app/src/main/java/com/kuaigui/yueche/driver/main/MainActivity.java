package com.kuaigui.yueche.driver.main;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.AoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.kuaigui.yueche.driver.MyApplication;
import com.kuaigui.yueche.driver.R;
import com.kuaigui.yueche.driver.base.view.BaseActivity;
import com.kuaigui.yueche.driver.bean.CheckOrderInfo;
import com.kuaigui.yueche.driver.bean.ProgressOrderInfo;
import com.kuaigui.yueche.driver.bean.RootCommonBean;
import com.kuaigui.yueche.driver.bean.RootOrderListBean;
import com.kuaigui.yueche.driver.constant.Api;
import com.kuaigui.yueche.driver.constant.Constant;
import com.kuaigui.yueche.driver.enums.OrderStatus;
import com.kuaigui.yueche.driver.main.mine.AdviceActivity;
import com.kuaigui.yueche.driver.main.mine.MyPerformanceActivity;
import com.kuaigui.yueche.driver.main.order.activity.OrderActivity;
import com.kuaigui.yueche.driver.main.order.activity.PickupCustomerActivity;
import com.kuaigui.yueche.driver.main.order.activity.SendCustomerActivity;
import com.kuaigui.yueche.driver.main.setting.SettingActivity;
import com.kuaigui.yueche.driver.mvc.BaseController;
import com.kuaigui.yueche.driver.mvc.IResultView;
import com.kuaigui.yueche.driver.okhttp.OkRequestParams;
import com.kuaigui.yueche.driver.util.AbJsonUtil;
import com.kuaigui.yueche.driver.util.AbToastUtil;
import com.kuaigui.yueche.driver.util.BaseUtils;
import com.kuaigui.yueche.driver.util.CustomProgress;
import com.kuaigui.yueche.driver.util.NumberUtil;
import com.kuaigui.yueche.driver.widget.CircleImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, IResultView,
        LocationSource, AMapLocationListener, GeocodeSearch.OnGeocodeSearchListener, PoiSearch.OnPoiSearchListener {

    @BindView(R.id.title_tv)
    TextView       mTitleTv;
    @BindView(R.id.toolbar)
    Toolbar        mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout   mDrawerLayout;
    @BindView(R.id.online_tip_ll)
    LinearLayout   mOnlineTipLl;
    @BindView(R.id.local_tv)
    TextView       mLocalTv;
    @BindView(R.id.current_time_tv)
    TextView       mCurrentTimeTv;

    @BindView(R.id.month_performance_tv)
    TextView mMonthPerformanceTv;
    @BindView(R.id.total_performance_tv)
    TextView mTotalPerformanceTv;
    @BindView(R.id.online_btn)
    Button   mOnlineBtn;
    @BindView(R.id.order_btn)
    Button   mOrderBtn;

    CircleImageView mHeadIv;
    TextView        mDriverNumTv;
    MapView         mMapView;
    private AMap                      aMap;
    private AMapLocationClient        mLocationClient;
    private OnLocationChangedListener mListener;

    private BaseController mController;

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        MainActivityPermissionsDispatcher.fetchLocationWithPermissionCheck(this);//获取定位权限
        initMap();

    }

    private void initMap() {
        if (aMap == null) {
            aMap = mMapView.getMap();
            setUpMap();
        }

        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {

            }
        });

        aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
            }
        });
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setLocationSource(this);// 设置定位监听
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.drawable.local1));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(Color.TRANSPARENT);
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(5f);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(Color.TRANSPARENT);

        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }

    @Override
    public void initView() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mTitleTv.setText(R.string.login_title);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();

        mNavView.setNavigationItemSelectedListener(this);

        mMonthPerformanceTv.setText("¥" + NumberUtil.getDoubleNoRoundNumberFormat(BaseUtils.getMonthValue()));
        mTotalPerformanceTv.setText("¥" + NumberUtil.getDoubleNoRoundNumberFormat(BaseUtils.getTotalValue()));

        View headView = mNavView.getHeaderView(0);
        mHeadIv = headView.findViewById(R.id.head_iv);
        mDriverNumTv = headView.findViewById(R.id.driver_num_tv);
        mDriverNumTv.setText(BaseUtils.getLicenseId());

        initAction(BaseUtils.isOnline());

        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        progressDialog = new ProgressDialog(this);

        String time;
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        time = format.format(new Date(System.currentTimeMillis()));
        mCurrentTimeTv.setText(time);


    }

    private void initAction(boolean isOnline) {
        mOnlineTipLl.setVisibility(isOnline ? View.GONE : View.VISIBLE);
        mOrderBtn.setEnabled(isOnline);
        mOrderBtn.setBackground(isOnline ? ContextCompat.getDrawable(this, R.drawable.conner_red_bg)
                : ContextCompat.getDrawable(this, R.drawable.conner_gray_bg));
        mOnlineBtn.setText(isOnline ? "下班" : "上班");
    }

    private List<String> mFilter = new ArrayList<>();

    @Override
    public void initData() {
        mDriverNumTv.setText(BaseUtils.getMobile());
        mHandler = new Handler(Looper.getMainLooper());
        mController = new BaseController(this);

        mFilter.add(MainActivity.class.getName());
        mFilter.add(OrderActivity.class.getName());
        fetchCurOrder();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(ACTION_BROADCAST_CHECK_ORDER));

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

//        mDrawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId()) {
            case R.id.nav_performance:
                startActivity(new Intent(this, MyPerformanceActivity.class));
                break;
            case R.id.nav_advice:
                startActivity(new Intent(this, AdviceActivity.class));
                break;
            case R.id.nav_call:
                MainActivityPermissionsDispatcher.showCallPhoneWithPermissionCheck(this);
                break;
            case R.id.nav_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }

        return true;
    }

    @OnClick({R.id.mine_iv, R.id.online_btn, R.id.order_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_iv:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.online_btn:
                if (BaseUtils.isOnline()) {
                    offline();
                } else {
                    online();
                }
                break;
            case R.id.order_btn:
                if (curLatLng == null) {
                    AbToastUtil.showToast(this, "定位失败，请重试!");
                    return;
                }
                startActivity(OrderActivity.getCallIntent(this, curLatLng.longitude + "", curLatLng.latitude + ""));
                break;
        }
    }

    /**
     * 获取正在进行的订单
     */
    private void fetchCurOrder() {
        OkRequestParams params = new OkRequestParams();
        params.put("driverMobile", BaseUtils.getMobile());
        mController.doPostRequest(Api.PROGRESSINFO, "progressinfo", params);
    }

    /**
     * 上报位置信息
     */
    private void uploadPosition(String longitude, String latitude) {
        OkRequestParams params = new OkRequestParams();
        params.put("token", BaseUtils.getToken());
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        mController.doPostRequest(Api.LOCATE, "locate", params);
    }


    private void online() {
        if (curLatLng == null) {
            AbToastUtil.showToast(this, "定位失败，请重试!");
            return;
        }
        OkRequestParams params = new OkRequestParams();
        params.put("token", BaseUtils.getToken());
        params.put("longitude", curLatLng.longitude + "");
        params.put("latitude", curLatLng.latitude + "");
        mController.doPostRequest(Api.ONLINE, "online", params);

    }

    private void offline() {
        if (curLatLng == null) {
            AbToastUtil.showToast(this, "定位失败，请重试!");
            return;
        }
        OkRequestParams params = new OkRequestParams();
        params.put("token", BaseUtils.getToken());
        params.put("longitude", curLatLng.longitude + "");
        params.put("latitude", curLatLng.latitude + "");
        mController.doPostRequest(Api.OFFLINE, "online", params);
    }

    private             BroadcastReceiver receiver                     = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_BROADCAST_CHECK_ORDER)) {
                int state = intent.getIntExtra(EXTRA_BROADCAST_ORDER_STATE, BROADCAST_ORDER_STATE_CANCEL);
                if (state == BROADCAST_ORDER_STATE_DONE) {
                    String orderNo = intent.getStringExtra(EXTRA_BROADCAST_ORDER_NO);
                    startTask(orderNo);
                }
            }
        }
    };
    /**
     * 监听是否需要取消定时获取已接订单的状态
     */
    public static final String            ACTION_BROADCAST_CHECK_ORDER = "action_broadcast_check_order";
    public static final String            EXTRA_BROADCAST_ORDER_STATE  = "extra_broadcast_order_state";
    public static final String            EXTRA_BROADCAST_ORDER_NO     = "extra_broadcast_order_no";
    public static final int               BROADCAST_ORDER_STATE_DONE   = 0;//确认接单
    public static final int               BROADCAST_ORDER_STATE_CANCEL = 1;//没有接单

    private Timer mOrderTimer;

    public void startTask(String orderNo) {
        if (mOrderTimer == null) {
            mOrderTimer = new Timer();
            CheckOrderTask task = new CheckOrderTask(orderNo);
            //2s刷新一次，刚进来不刷新
            mOrderTimer.schedule(task, 1000, 2 * 1000);
        }
    }

    public void cancelTask() {
        if (mOrderTimer != null) {
            mOrderTimer.cancel();
            mOrderTimer = null;
        }
    }

    /**
     * 实现定时更新订单状态
     */
    private class CheckOrderTask extends TimerTask {
        private String orderNo;

        public CheckOrderTask(String orderNo) {
            this.orderNo = orderNo;
        }

        @Override
        public void run() {
            checkOrder(orderNo);
        }
    }


    private void checkOrder(String orderNo) {
        OkRequestParams params = new OkRequestParams();
        params.put("orderNo", orderNo);
        mController.doPostRequest(Api.CHECK_ORDER, "check_order", params);
    }

    @Override
    public void showLoadView(String url) {
//        CustomProgress.show(this);
    }

    @Override
    public void showResultView(String url, String type, String content) {
        CustomProgress.disMiss();
        switch (url) {
            case Api.CHECK_ORDER: {
                CustomProgress.disMiss();
                CheckOrderInfo orderInfo = AbJsonUtil.fromJson(content, CheckOrderInfo.class);
                if (orderInfo != null) {
                    if (orderInfo.getCode() == Api.CODE_SUCCESS) {
                        if (orderInfo.getData().getState() == OrderStatus.ORDER_CREATE.mOrderStatus) {
//                            AbToastUtil.showToast(this, "当前订单已被司机取消!");
                            MyApplication.getApp().filterAndFinish(mFilter);
//                            waitOrderFragment.getLastData();//刷新页面
                            cancelTask();
                        }
                        if (orderInfo.getData().getState() == OrderStatus.ORDER_CUSTOMER_CANCEL.mOrderStatus) {
                            AbToastUtil.showToast(this, "当前订单已被乘客取消!");
                            MyApplication.getApp().filterAndFinish(mFilter);
//                            waitOrderFragment.getLastData();//刷新页面
                            cancelTask();
                        } else if (orderInfo.getData().getState() == OrderStatus.ORDER_CUSTOMER_EVALUATE.mOrderStatus
                                || orderInfo.getData().getState() == OrderStatus.ORDER_COMPLETE.mOrderStatus) {
                            cancelTask();
                        }
                    }
                }
                break;
            }
            case Api.PROGRESSINFO:
                ProgressOrderInfo orderInfo = AbJsonUtil.fromJson(content, ProgressOrderInfo.class);
                if (orderInfo != null) {
                    if (orderInfo.getCode() != Api.CODE_SUCCESS) {
                        AbToastUtil.showToast(this, orderInfo.getMessage());
                    } else {//根据订单状态跳到不同的界面中
                        ProgressOrderInfo.DataBean data = orderInfo.getData();
                        if (data == null) {
                            return;
                        }
                        int state = Integer.parseInt(data.getState());
                        RootOrderListBean.DataBean mOrderData = new RootOrderListBean.DataBean();
                        mOrderData.setOrderNo(data.getOrderNo());
//                        mOrderData.setCommericalType(Integer.parseInt(data.getVehicleType()));
//                        mOrderData.setOrderTime(Long.parseLong(data.getOr()));
//                        mOrderData.setOrderTimeStr(data.get());
//                        mOrderData.setDistance(data.getDeparture());
                        mOrderData.setState(state);
//                        mOrderData.setStateStr(data.getDeparture());
                        mOrderData.setDeparture(data.getDeparture());
                        mOrderData.setDestination(data.getDestination());
                        mOrderData.setDepLatitude(data.getDepLatitude());
                        mOrderData.setDepLongitude(data.getDepLongitude());
                        mOrderData.setDestLatitude(data.getDestLatitude());
                        mOrderData.setDestLongitude(data.getDestLongitude());
                        mOrderData.setPassengerName(data.getPassengerName());
                        mOrderData.setPassengerMobile(data.getPassengerMobile());

                        //启动订单状态查询
                        Intent broadIntent = new Intent(MainActivity.ACTION_BROADCAST_CHECK_ORDER);
                        broadIntent.putExtra(MainActivity.EXTRA_BROADCAST_ORDER_STATE, MainActivity.BROADCAST_ORDER_STATE_DONE);
                        broadIntent.putExtra(MainActivity.EXTRA_BROADCAST_ORDER_STATE, MainActivity.BROADCAST_ORDER_STATE_DONE);
                        broadIntent.putExtra(MainActivity.EXTRA_BROADCAST_ORDER_NO, mOrderData.getOrderNo() + "");
                        LocalBroadcastManager.getInstance(this).sendBroadcast(broadIntent);

                        if (state == OrderStatus.ORDER_DRIVER_TAKE.mOrderStatus) {//司机接单
                            Intent intent = new Intent(this, PickupCustomerActivity.class);
                            intent.putExtra("orderData", mOrderData);
                            startActivity(intent);
                        } else if (state == OrderStatus.ORDER_CUSTOMER_BOARDING.mOrderStatus) {//乘客上车
                            Intent intent = new Intent(this, SendCustomerActivity.class);
                            intent.putExtra("orderData", mOrderData);
                            startActivity(intent);
                        }
                    }
                }
                break;
            case Api.LOCATE://上报位置信息
                RootCommonBean bean = AbJsonUtil.fromJson(content, RootCommonBean.class);
                if (bean != null) {
                    if (bean.getCode() != Api.CODE_SUCCESS) {
                        Log.d("MainActivity", "LOCATE:" + bean.getMessage());
                    }
                }
                break;
            case Api.ONLINE:
                RootCommonBean onlineBean = AbJsonUtil.fromJson(content, RootCommonBean.class);
                if (onlineBean != null) {
                    if (onlineBean.getCode() == Api.CODE_SUCCESS) {
                        BaseUtils.saveOnline();
                        initAction(true);
                    } else {
                        AbToastUtil.showToast(this, onlineBean.getMessage());
                    }
                }
                break;
            case Api.OFFLINE:
                RootCommonBean offlineBean = AbJsonUtil.fromJson(content, RootCommonBean.class);
                if (offlineBean != null) {
                    if (offlineBean.getCode() == Api.CODE_SUCCESS) {
                        BaseUtils.offline();
                        initAction(false);
                    } else {
                        AbToastUtil.showToast(this, offlineBean.getMessage());
                    }
                }
                break;
        }
    }

    @Override
    public void showErrorView(String url, Exception e, String msg) {
        CustomProgress.disMiss();
    }

    @Override
    public void showProgressView(String url, int progress) {

    }

    @NeedsPermission(Manifest.permission.CALL_PHONE)
    void showCallPhone() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Constant.TEL));
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale(Manifest.permission.CALL_PHONE)
    void showRationaleForCallPhone(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(R.string.permission_call_phone_des)
                .show();
    }

    @OnPermissionDenied(Manifest.permission.CALL_PHONE)
    void onCallPhoneDenied() {
        AbToastUtil.showToast(MyApplication.getApp(), R.string.open_authority_in_call);
    }

    @OnNeverAskAgain(Manifest.permission.CALL_PHONE)
    void onCallPhoneNeverAskAgain() {
        AbToastUtil.showToast(MyApplication.getApp(), R.string.open_authority_in_call);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
//        if (!hasLocation) {
//            location();
//        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        cancelTask();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onDestroy();
        mMapView.onDestroy();
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
    }

    private boolean hasLocation;
    private String  curAddress = "定位失败";
    private LatLng  curLatLng;
    private Handler mHandler;

    private void refreshLocationTv() {
        if (curAddress.equals("定位失败")) {
            mLocalTv.setText(curAddress);
        } else {
            mLocalTv.setText("当前位置：" + curAddress);
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);
                curLatLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                uploadPosition(curLatLng.longitude + "", curLatLng.latitude + "");

//                QNLogUtils.log("ReserveActivity", "onLocationChanged:$curLatLng")
                curAddress = aMapLocation.getAddress();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        refreshLocationTv();
                    }
                });
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLatLng, 18f));//后一个参数表示缩放倍数，4-20
                hasLocation = true;
                //获取定位位置周边的地址
                mSearchLatLonPoint = new LatLonPoint(curLatLng.latitude, curLatLng.longitude);
                geoAddress();
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                AbToastUtil.showToast(this, errText);
//                QNLogUtils.error("AmapErr", errText);
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        location();
    }

    /**
     * 定位
     */
    private void location() {
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(this);
            //设置定位监听
            mLocationClient.setLocationListener(this);

            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
            mLocationOption.setOnceLocation(false);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mLocationOption.setInterval(20 * 1000L);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.startLocation();
        } else {
            mLocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    private LatLonPoint   mSearchLatLonPoint;
    private GeocodeSearch geocoderSearch;

    /**
     * 响应逆地理编码
     */
    public void geoAddress() {
        showDialog();
        if (mSearchLatLonPoint != null) {
            RegeocodeQuery query = new RegeocodeQuery(mSearchLatLonPoint, 200, GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
            geocoderSearch.getFromLocationAsyn(query);
        }
    }

    private ProgressDialog progressDialog;

    private void showDialog() {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("正在加载...");
        progressDialog.show();
    }

    private void dismissDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int rCode) {
        dismissDialog();
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (regeocodeResult != null && regeocodeResult.getRegeocodeAddress() != null
                    && regeocodeResult.getRegeocodeAddress().getFormatAddress() != null) {
                String address = regeocodeResult.getRegeocodeAddress().getProvince() +
                        regeocodeResult.getRegeocodeAddress().getCity() +
                        regeocodeResult.getRegeocodeAddress().getDistrict() +
                        regeocodeResult.getRegeocodeAddress().getTownship();
//                firstItem = new PoiItem("regeo", mSearchLatLonPoint, address, address);
//                doSearchQuery();
                curAddress = regeocodeResult.getRegeocodeAddress().getFormatAddress();
                List<AoiItem> aois = regeocodeResult.getRegeocodeAddress().getAois();
                if (aois != null && aois.size() > 0) {
                    AoiItem pos = aois.get(0);
                    curAddress = pos.getAoiName();

                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        refreshLocationTv();
                    }
                });

            }
        } else {
            Toast.makeText(MainActivity.this, "error code is " + rCode, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void fetchLocation() {
    }

    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void showRemind() {//获取定位权限失败，弹框提示
        AbToastUtil.showToast(MyApplication.getApp(), R.string.open_authority_in_location);
    }
}
