package com.kuaigui.yueche.driver.main.order.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
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
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
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
import com.kuaigui.yueche.driver.bean.RootCommonBean;
import com.kuaigui.yueche.driver.bean.RootOrderListBean;
import com.kuaigui.yueche.driver.constant.Api;
import com.kuaigui.yueche.driver.constant.Constant;
import com.kuaigui.yueche.driver.constant.TypeConstant;
import com.kuaigui.yueche.driver.mvc.BaseController;
import com.kuaigui.yueche.driver.mvc.IResultView;
import com.kuaigui.yueche.driver.okhttp.OkRequestParams;
import com.kuaigui.yueche.driver.util.AbJsonUtil;
import com.kuaigui.yueche.driver.util.AbToastUtil;
import com.kuaigui.yueche.driver.util.CustomProgress;
import com.kuaigui.yueche.driver.widget.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 作者: zengxc
 * 描述:
 * 时间: 2018/10/12 10:53
 */
@RuntimePermissions
public class PickupCustomerActivity extends BaseActivity implements IResultView,
        LocationSource, AMapLocationListener, GeocodeSearch.OnGeocodeSearchListener, PoiSearch.OnPoiSearchListener {

    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.place_tv)
    TextView mPlaceTv;
    @BindView(R.id.place_tip_tv)
    TextView mPlaceTipTv;
    @BindView(R.id.local_tv)
    TextView mLocalTv;
    @BindView(R.id.time_tv)
    TextView mTimeTv;
    @BindView(R.id.map)
    View mMap;
    @BindView(R.id.head_iv)
    CircleImageView mHeadIv;
    @BindView(R.id.name_tv)
    TextView mNameTv;
    @BindView(R.id.start_tv)
    TextView mStartTv;
    @BindView(R.id.end_tv)
    TextView mEndTv;

    private RootOrderListBean.DataBean mOrderData;

    private BaseController mController;
    MapView mMapView;
    private AMap aMap;
    private AMapLocationClient mLocationClient;
    private LocationSource.OnLocationChangedListener mListener;

    @Override
    public int setLayout() {
        return R.layout.activity_pickup_customer;
    }

    @Override
    public void initView() {
        mTitleTv.setText("接乘客");
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
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
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
    public void initData() {
        mController = new BaseController(this);

        mOrderData = getIntent().getParcelableExtra("orderData");
        SpannableString placeString = new SpannableString("去" + mOrderData.getDeparture());
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_1bb671));
        if (!TextUtils.isEmpty(mOrderData.getDeparture())) {
            placeString.setSpan(colorSpan, 1, placeString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        mPlaceTv.setText(placeString);

        SpannableString placeTipString = new SpannableString("请在" + "" + "前到达预定地点");
//        placeTipString.setSpan(colorSpan,);
        mPlaceTipTv.setText(placeTipString);

        mLocalTv.setText("当前位置");
        mTimeTv.setText("6分钟");

        mStartTv.setText(mOrderData.getDeparture());
        mEndTv.setText(mOrderData.getDestination());
    }

    @OnClick({R.id.nav_tv, R.id.cancel_order_tv, R.id.confirm_order_tv, R.id.call_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.nav_tv:
                // TODO: 2018/10/12 0012 导航去预定地点
                break;
            case R.id.cancel_order_tv:
                Intent intent = new Intent(this, ReasonActivity.class);
                intent.putExtra("orderNo", mOrderData.getOrderNo());
                intent.putExtra("cancelType", TypeConstant.CANCEL_TYPE_DRIVER_VIOLATE);
                startActivity(intent);
                break;
            case R.id.confirm_order_tv:
                pickupCustomer();
                break;
            case R.id.call_iv:
                PickupCustomerActivityPermissionsDispatcher.showCallPhoneWithPermissionCheck(this);
                break;
        }
    }

    private void pickupCustomer() {
        OkRequestParams params = new OkRequestParams();
        params.put("orderNo", mOrderData.getOrderNo());
        params.put("longitude", "113.880714");
        params.put("latitude", "22.560353");
        mController.doPostRequest(Api.PICKUP_CUSTOMER, "pickupCustomer", params);
    }

    @Override
    public void showLoadView(String url) {
        CustomProgress.show(this);
    }

    @Override
    public void showResultView(String url, String type, String content) {
        if (url.equals(Api.PICKUP_CUSTOMER)) {
            CustomProgress.disMiss();
            RootCommonBean pickupCustomerBean = AbJsonUtil.fromJson(content, RootCommonBean.class);
            if (pickupCustomerBean != null) {
                if (pickupCustomerBean.getCode() == Api.CODE_SUCCESS) {
                    Intent intent = new Intent(this, SendCustomerActivity.class);
                    intent.putExtra("orderData", mOrderData);
                    startActivity(intent);
                    finish();
                }
            }
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
        PickupCustomerActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
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

    private LatLng curLatLng;
    private Marker mLocMarker;
    private boolean hasLocation;

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);
                curLatLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
//                QNLogUtils.log("ReserveActivity", "onLocationChanged:$curLatLng")
                if (mLocMarker == null) {
                    mLocMarker = addMarker(curLatLng, R.drawable.local1);
                } else {
                    mLocMarker.setPosition(curLatLng);
                }
                curAddress = aMapLocation.getAddress();
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

    /**
     * 设置地图上的标示
     */
    private Marker addMarker(LatLng curLatLng, int resIcon) {
        MarkerOptions options = new MarkerOptions();
        options.icon(BitmapDescriptorFactory.fromResource(resIcon));
        options.anchor(0.5f, 0.5f);
        options.position(curLatLng);
        return aMap.addMarker(options);
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
            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mLocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setOnceLocation(true);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
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

    private LatLonPoint mSearchLatLonPoint;
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

    private String curAddress;

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

            }
        } else {
            Toast.makeText(PickupCustomerActivity.this, "error code is " + rCode, Toast.LENGTH_SHORT).show();
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
        super.onDestroy();
        mMapView.onDestroy();
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
    }
}
