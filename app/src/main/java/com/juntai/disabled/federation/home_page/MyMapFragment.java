package com.juntai.disabled.federation.home_page;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.HeatMap;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.basecomponent.utils.LogUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.bdmap.service.LocateAndUpload;
import com.juntai.disabled.bdmap.utils.MapUtil;
import com.juntai.disabled.bdmap.utils.MyOrientationListener;
import com.juntai.disabled.bdmap.utils.SharedPreferencesUtil;
import com.juntai.disabled.bdmap.utils.clusterutil.clustering.Cluster;
import com.juntai.disabled.bdmap.utils.clusterutil.clustering.ClusterManager;
import com.juntai.disabled.federation.MyApp;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.MainActivity;
import com.juntai.disabled.federation.base.BaseAppFragment;
import com.juntai.disabled.federation.bean.BannerNewsBean;
import com.juntai.disabled.federation.bean.PoliceDetailBean;
import com.juntai.disabled.federation.bean.ServerPeopleBean;
import com.juntai.disabled.federation.bean.case_bean.CaseDesBean;
import com.juntai.disabled.federation.bean.MapClusterItem;
import com.juntai.disabled.federation.bean.MapMenuButton;
import com.juntai.disabled.federation.bean.PoliceCarBean;
import com.juntai.disabled.federation.bean.ResponseSiteBean;
import com.juntai.disabled.federation.bean.ResponseInspection;
import com.juntai.disabled.federation.bean.ResponseNews;
import com.juntai.disabled.federation.bean.history_track.LocationBean;
import com.juntai.disabled.federation.bean.stream.StreamCameraBean;
import com.juntai.disabled.federation.bean.UserBean;
import com.juntai.disabled.federation.home_page.call_to_police.CallToPoliceActivity;
import com.juntai.disabled.federation.home_page.call_to_police.VerifiedActivity;
import com.juntai.disabled.federation.home_page.camera.ijkplayer.PlayerLiveActivity;
import com.juntai.disabled.federation.home_page.healthOrgnize.HealthOrganizeActivity;
import com.juntai.disabled.federation.home_page.law_case.CaseInfoActivity;
import com.juntai.disabled.federation.home_page.map.DistanceUtilActivity;
import com.juntai.disabled.federation.home_page.map.HistoryTrack;
import com.juntai.disabled.federation.home_page.map.MapBottomListDialog;
import com.juntai.disabled.federation.home_page.map.MapContract;
import com.juntai.disabled.federation.home_page.map.MapMenuAdapter;
import com.juntai.disabled.federation.home_page.map.MapPresenter;
import com.juntai.disabled.federation.home_page.map.PanoramaActivity;
import com.juntai.disabled.federation.home_page.map.SelectTime;
import com.juntai.disabled.federation.home_page.search.SearchActivity;
import com.juntai.disabled.federation.home_page.weather.WeatherActivity;
import com.juntai.disabled.federation.home_page.inspection.InspectionInfoActivity;
import com.juntai.disabled.federation.home_page.site_manager.site_info.NewUnitDetailActivity;
import com.juntai.disabled.federation.utils.AppUtils;
import com.juntai.disabled.federation.utils.DateUtil;
import com.juntai.disabled.federation.utils.ImageUtil;
import com.juntai.disabled.federation.utils.StringTools;
import com.juntai.disabled.federation.utils.UserInfoManager;
import com.juntai.disabled.im.ModuleIm_Init;
import com.orhanobut.hawk.Hawk;
import com.sunfusheng.marqueeview.MarqueeView;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ????????????
 *
 * @aouther Ma
 * @date 2019/3/14
 */
public class MyMapFragment extends BaseAppFragment<MapPresenter> implements MapContract.View,
        View.OnClickListener,
        LocateAndUpload.Callback,
        ClusterManager.OnClusterClickListener<MapClusterItem>,
        ClusterManager.OnClusterItemClickListener<MapClusterItem>,
        MapBottomListDialog.MapBottomListItemClickListenner {

    private MapView mMapView = null;
    private LatLng myLocation = null;
    private BaiduMap mMap = null;
    private RecyclerView mapMenuButtonRv = null;
    //????????????=======================================
    private DrawerLayout drawerLayout = null;
    private RelativeLayout sideLayout = null;
    private LinearLayout mSearchLl;
    private RelativeLayout DistanceUtilBtn = null;
    private RelativeLayout sideViewNormal = null;
    private ConstraintLayout mMarqueeCl;
    private RelativeLayout twdBg, thdBg, weixingBg, jiejingBg;
    private TextView twdTv, thdTv, weixingTv, jiejingTv;
    private Switch roadStatus, distanceUtil;
    private List<LatLng> heatMapItems = new ArrayList<>();
    HeatMap mHeatMap = null;
    //==============================================
    private SharedPreferencesUtil mapSP = null;
    private MyOrientationListener myOrientationListener = null;
    private float direct = 0, locationRadius = 0;
    private String province = null, city = null, area = null;
    private Button backToMyLocation = null;
    private ImageView zoomPlus, zoomMinus;
    private ImageView satelliteMap = null, twdMap = null, thdMap = null, jiejing = null;
    private BaiduMap.OnMapClickListener normalClick;
    private BaiduMap.OnMapClickListener distanceUtilClick;
    private Boolean distanceUtilSwitch = false;
    List<LatLng> distancePoints = new ArrayList<>();
    private boolean isFisrt = true;
    private List<MapClusterItem> clusterItemList = new ArrayList<>();
    private ClusterManager<MapClusterItem> clusterManager;
    private double distance = 0;
    private int clickedButton = -1;
    private View infowindowPeople = null;
    private ImageView mScanIv;
    private ImageView mCallPoliceIv;
    private ImageView mDeleteNews;//????????????
    private MarqueeView marqueeView;
    private long currentTime;
    private List<BannerNewsBean.DataBean> noticeList;
    private boolean closeMarquee = false;//??????????????????

    //???????????????marker
    Marker nowMarker;
    int dateType;//0?????????1???????????????
    BitmapDescriptor bitmapDescriptor;
    String nowMarkerId = "";//??????markerid
    private MapStatus lastPosition;
    private int clickType = 2;//2??????marker?????????1??????????????????
    private MapBottomListDialog mapBottomListDialog;
    StreamCameraBean.DataBean currentStreamCamera;
    MapMenuAdapter mMenuAdapter;

    @Override
    protected MapPresenter createPresenter() {
        return new MapPresenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", String.valueOf(MyApp.getUid()));
        params.put("account", MyApp.getAccount());
        params.put("token", MyApp.getUserToken());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mContext.startForegroundService(new Intent(mContext, LocateAndUpload.class));
        } else {
            mContext.startService(new Intent(mContext, LocateAndUpload.class));
        }
        LocateAndUpload.setCallback(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LocateAndUpload.setCallback(null);
    }

    @Override
    protected void lazyLoad() {
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_mymap_latest;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        marqueeView = (MarqueeView) getView(R.id.marqueeView);
        marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                MyApp.gotoNewsInfo(noticeList.get(position).getTypeId(), noticeList.get(position).getId(), mContext);
            }
        });
        sideViewNormal = getView(R.id.normal_side);
        mMarqueeCl = getView(R.id.marqueeView_cl);
        mDeleteNews = getView(R.id.delete_icon);
        mDeleteNews.setOnClickListener(this);
        DistanceUtilBtn = getView(R.id.distance_util_btn);
        DistanceUtilBtn.setOnClickListener(this);
        myLocation = new LatLng(0, 0);
        //????????????====================================================
        initDrawerlayout();
        sideLayout = getView(R.id.cehuabuju);
        twdBg = getView(R.id.twd_bg);
        thdBg = getView(R.id.thd_bg);
        jiejingBg = getView(R.id.jiejing_bg);
        weixingBg = getView(R.id.weixing_bg);
        twdTv = getView(R.id.twd_tv);
        thdTv = getView(R.id.thd_tv);
        jiejingTv = getView(R.id.jiejing_tv);
        weixingTv = getView(R.id.weixing_tv);

        roadStatus = getView(R.id.lukuangtukaiguan);
        distanceUtil = getView(R.id.map_distanceutil);
        heatMapListener();
        //???????????????
        roadStatus.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mMap.setTrafficEnabled(true);
            } else {
                mMap.setTrafficEnabled(false);
            }
        });
        //????????????
        distanceUtil.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mMap.setOnMapClickListener(distanceUtilClick);
            } else {
                mMap.setOnMapClickListener(normalClick);
                distance = 0;
                clearTheMap(mMap);
                distancePoints.clear();
            }
        });
        //===========================================================
        backToMyLocation = getView(R.id.mylocation);
        zoomMinus = getView(R.id.zoomminus);
        zoomPlus = getView(R.id.zoomplus);
        zoomPlus.setOnClickListener(this);
        zoomMinus.setOnClickListener(this);
        backToMyLocation.setOnClickListener(this);
        getView(R.id.distance_switch).setOnClickListener(this);
        //??????????????????????????????
        satelliteMap = getView(R.id.weixingtu);
        twdMap = getView(R.id.erdpingmiantu);
        thdMap = getView(R.id.sandfushitu);
        jiejing = getView(R.id.jiejing);
        jiejing.setOnClickListener(this);
        satelliteMap.setOnClickListener(this);
        twdMap.setOnClickListener(this);
        thdMap.setOnClickListener(this);
        initMapConfig();
        setMapType();
        View child = mMapView.getChildAt(1);
        if ((child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }
        initMenuAdapter();

        mSearchLl = (LinearLayout) getView(R.id.search_ll);
        mSearchLl.setOnClickListener(this);
        mScanIv = (ImageView) getView(R.id.scan_iv);
        mScanIv.setOnClickListener(this);
        mCallPoliceIv = (ImageView) getView(R.id.call_police_iv);
        mCallPoliceIv.setOnClickListener(this);
    }

    /**
     * ?????????????????????
     */
    private void setMapType() {
        //    entityListRl = getView(R.id.entity_list_rl);
        mapSP = new SharedPreferencesUtil(mContext);
        switch (mapSP.getIntSP("mapType")) {
            case BaiduMap.MAP_TYPE_NORMAL:
                mMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                switchMapType(R.id.erdpingmiantu);
                break;
            case BaiduMap.MAP_TYPE_SATELLITE:
                mMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                switchMapType(R.id.weixingtu);
                break;
        }
    }

    /**
     * map?????????
     */
    private void initMapConfig() {
        //??????
        mMapView = getView(R.id.map_view_tmv);
        mMap = mMapView.getMap();
        mMapView.showZoomControls(false);
        //????????????
        myOrientationListener = new MyOrientationListener(mContext);
        myOrientationListener.setOnOrientationListener(x -> {
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(locationRadius)
                    .direction(direct)// ?????????????????????????????????????????????????????????0-360
                    .latitude(myLocation.latitude)
                    .longitude(myLocation.longitude).build();
            mMap.setMyLocationData(locData);
            direct = x;
        });
        myOrientationListener.start();
        //?????????UiSettings?????????
        UiSettings mUiSettings = mMap.getUiSettings();
        //????????????enable???true???false ???????????????????????????
        mUiSettings.setCompassEnabled(false);
        //??????????????????
        mMap.setMyLocationEnabled(true);
        MyLocationConfiguration config = new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL, true, null);
        mMap.setMyLocationConfiguration(config);
        clusterManager = new ClusterManager<>(mContext, mMap);
        clusterManager.setOnClusterItemClickListener(MyMapFragment.this);//?????????
        clusterManager.setOnClusterClickListener(MyMapFragment.this);//????????????
        mMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //?????????????????????
                if (!clusterManager.getClusterMarkerCollection().getMarkers().contains(marker)) {
                    if (nowMarker != null) {
                        if (bitmapDescriptor != null) {
                            nowMarker.setIcon(bitmapDescriptor);
                        }

                    }
                    nowMarker = marker;
                    clickType = 2;
                }
                //???nowMarker????????????
                clusterManager.onMarkerClick(marker);
                return false;
            }
        });
        //        map.setOnMapStatusChangeListener(clusterManager);
        mMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {
            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
                if (lastPosition != null && lastPosition.zoom != mMap.getMapStatus().zoom) {
                    mMapView.removeView(infowindowPeople);
                    clickType = 2;
                    if (nowMarker != null) {
                        nowMarker.setIcon(bitmapDescriptor);
                        nowMarker = null;
                    }
                    nowMarkerId = "";
                }
                lastPosition = mMap.getMapStatus();
                clusterManager.onMapStatusChange(mapStatus);
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
            }
        });
        normalClick = new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMapView.removeView(infowindowPeople);
            }

            @Override
            public void onMapPoiClick(MapPoi mapPoi) {
                mMapView.removeView(infowindowPeople);
            }
        };
        panoramaClick = new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                startActivity(new Intent(mContext, PanoramaActivity.class).putExtra("lat",
                        latLng.latitude)
                        .putExtra("lng", latLng.longitude));
            }

            @Override
            public void onMapPoiClick(MapPoi mapPoi) {
                startActivity(new Intent(mContext, PanoramaActivity.class).putExtra("lat",
                        mapPoi.getPosition().latitude)
                        .putExtra("lng", mapPoi.getPosition().longitude));
            }
        };
        mMap.setOnMapClickListener(normalClick);
    }

    //???????????????
    private void heatMapListener() {
        Switch heatMap = getView(R.id.relitukaiguan);
        heatMap.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                getBaseActivity().showLoadingDialog(mContext);
                if (clusterItemList.size() > 0) {
                    heatMapItems.clear();
                    for (MapClusterItem item : clusterItemList) {
                        heatMapItems.add(item.getLatLng());
                    }
                    mHeatMap = new HeatMap.Builder().data(heatMapItems).build();
                    mMap.addHeatMap(mHeatMap);
                    getBaseActivity().stopLoadingDialog();
                } else {
                    ToastUtils.toast(mContext, "???????????????????????????");
                    getBaseActivity().stopLoadingDialog();
                    heatMap.setChecked(false);
                }
            } else {
                if (mHeatMap != null) {
                    mHeatMap.removeHeatMap();
                    heatMap.setChecked(false);
                }
            }
        });
    }

    /**
     * ????????????????????????
     */
    private void initMenuAdapter() {
        mapMenuButtonRv = getView(R.id.menu_list);
        // entityListRv = getView(R.id.entity_list_rv);
        mPresenter.getMenus(MapContract.GET_MENUS);
        mapMenuButtonRv.setLayoutManager(new LinearLayoutManager(mContext));
        mMenuAdapter = new MapMenuAdapter(R.layout.map_menu_button, new ArrayList());
        mapMenuButtonRv.setAdapter(mMenuAdapter);
        mMenuAdapter.setOnItemClickListener((adapter1, view, position) -> {
            nowMarkerId = "";
            nowMarker = null;
            final MapMenuButton.DataBean item = (MapMenuButton.DataBean) adapter1.getData().get(position);
            if (item.isSelected()) {
                item.setSelected(false);
                clickedButton = -1;
                adapter1.notifyItemChanged(position);
                ((TextView) getView(R.id.dl_tv)).setText("????????????");
                sideViewNormal.setVisibility(View.VISIBLE);
                clearTheMap(mMap);
            } else {
                item.setSelected(true);
                if (clickedButton != -1) {
                    ((MapMenuButton.DataBean) adapter1.getData().get(clickedButton)).setSelected(false);
                    adapter1.notifyItemChanged(clickedButton);
                }
                clickedButton = position;
                adapter1.notifyItemChanged(position);
                LogUtil.d((item.getId() + ""));
                switch (item.getId()) {
                    case 1://??????????????????
                        //????????????
                        if (drawerLayout.isDrawerVisible(sideLayout)) {
                            drawerLayout.closeDrawer(sideLayout);
                        } else {
                            drawerLayout.openDrawer(sideLayout);
                        }
                        item.setSelected(false);
                        adapter1.notifyItemChanged(position);
                        break;
                    case 2://??????????????????
                        clearTheMap(mMap);
                        if (!StringTools.isStringValueOk(province)) {
                            ToastUtils.warning(mContext, "???????????????");
                        } else {
                            startActivity(new Intent(mContext, WeatherActivity.class)
                                    .putExtra("province", province)
                                    .putExtra("city", city)
                                    .putExtra("area", area == null ? "" : area));
                        }
                        item.setSelected(false);
                        adapter1.notifyItemChanged(position);
                        break;
                    case 3://??????????????????
                        clearTheMap(mMap);
                        mPresenter.getCameras(MapContract.GET_STREAM_CAMERAS);
                        break;
                    case 4://????????????
                        // TODO: 2021/3/3   ????????????
                        ToastUtils.toast(mContext,getString(R.string.not_open_notice));
//                        if (MyApp.isCompleteUserInfo()) {
//                            clearTheMap(mMap);
//                            mPresenter.getCases(MapContract.GET_CASES);
//                        } else {
//                            item.setSelected(false);
//                        }
                        break;
                    case 5://????????????
                        if (getBaseAppActivity().isCompleteUserInfo()) {
                            clearTheMap(mMap);
                            mPresenter.requestServer(MapContract.GET_SERVERS);
                        } else {
                            item.setSelected(false);
                        }
                        break;
                    case 6://????????????
                        if (getBaseAppActivity().isCompleteUserInfo()) {
                            clearTheMap(mMap);
                            try {
                                mPresenter.getPoliceCars(MapContract.GET_CARS);
                            } catch (Exception e) {
                                ToastUtils.toast(mContext, "??????????????????,?????????????????????");
                            }
                        } else {
                            item.setSelected(false);
                        }
                        break;
                    case 7://????????????
                        if (getBaseAppActivity().isCompleteUserInfo()) {
                            clearTheMap(mMap);
                            mPresenter.getSiteManagers(MapContract.GET_SITES);
                        } else {
                            item.setSelected(false);
                        }
                        break;
                    case 8://??????
                        clearTheMap(mMap);
                        // TODO: 2021/3/3   ????????????
                        ToastUtils.toast(mContext,getString(R.string.not_open_notice));
//                        mPresenter.getNews(MapContract.GET_NEWS);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * ?????????????????????
     */
    private void initDrawerlayout() {
        drawerLayout = getView(R.id.drawerlayout);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                ((MainActivity) getActivity()).drawerlayoutOpened(true);
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                ((MainActivity) getActivity()).drawerlayoutOpened(false);
            }

            @Override
            public void onDrawerStateChanged(int i) {
            }
        });
    }


    /**
     * ??????????????????
     *
     * @param viewId
     */
    private void switchMapType(int viewId) {
        switch (viewId) {
            case R.id.weixingtu:
                mMap.setOnMapClickListener(normalClick);
                twdBg.setBackgroundColor(getResources().getColor(R.color.transparent));
                thdBg.setBackgroundColor(getResources().getColor(R.color.transparent));
                weixingBg.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                weixingTv.setTextColor(getResources().getColor(R.color.colorAccent));
                twdTv.setTextColor(Color.parseColor("#8a000000"));
                thdTv.setTextColor(Color.parseColor("#8a000000"));
                jiejingBg.setBackgroundColor(getResources().getColor(R.color.transparent));
                jiejingTv.setTextColor(Color.parseColor("#8a000000"));
                break;
            case R.id.erdpingmiantu:
                mMap.setOnMapClickListener(normalClick);
                twdBg.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                thdBg.setBackgroundColor(getResources().getColor(R.color.transparent));
                weixingBg.setBackgroundColor(getResources().getColor(R.color.transparent));
                twdTv.setTextColor(getResources().getColor(R.color.colorAccent));
                thdTv.setTextColor(Color.parseColor("#8a000000"));
                weixingTv.setTextColor(Color.parseColor("#8a000000"));
                jiejingBg.setBackgroundColor(getResources().getColor(R.color.transparent));
                jiejingTv.setTextColor(Color.parseColor("#8a000000"));
                break;
            case R.id.sandfushitu:
                mMap.setOnMapClickListener(normalClick);
                twdBg.setBackgroundColor(getResources().getColor(R.color.transparent));
                thdBg.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                weixingBg.setBackgroundColor(getResources().getColor(R.color.transparent));
                thdTv.setTextColor(getResources().getColor(R.color.colorAccent));
                twdTv.setTextColor(Color.parseColor("#8a000000"));
                weixingTv.setTextColor(Color.parseColor("#8a000000"));
                jiejingBg.setBackgroundColor(getResources().getColor(R.color.transparent));
                jiejingTv.setTextColor(Color.parseColor("#8a000000"));
                break;
            case R.id.jiejing:
                mMap.setOnMapClickListener(panoramaClick);
                twdBg.setBackgroundColor(getResources().getColor(R.color.transparent));
                jiejingBg.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                thdBg.setBackgroundColor(getResources().getColor(R.color.transparent));
                weixingBg.setBackgroundColor(getResources().getColor(R.color.transparent));
                jiejingTv.setTextColor(getResources().getColor(R.color.colorAccent));
                twdTv.setTextColor(Color.parseColor("#8a000000"));
                thdTv.setTextColor(Color.parseColor("#8a000000"));
                weixingTv.setTextColor(Color.parseColor("#8a000000"));
                break;
        }
    }


    /**
     * ??????????????????
     *
     * @param map
     */
    private void clearTheMap(BaiduMap map) {
        map.clear();
        clusterItemList.clear();
        clusterManager.clearItems();
        mMapView.removeView(infowindowPeople);
    }

    //?????????????????????
    @Override
    protected void initData() {
        distanceUtilClick = new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //                ToastUtils.toast(mContext, "click");
                if (distancePoints.size() < 1) {
                    //                    distance = DistanceUtil.getDistance()
                    OverlayOptions mTextOptions = new TextOptions()
                            .text("??????") //????????????
                            .bgColor(0xffFFFFFF) //?????????
                            .fontSize(32) //??????
                            .fontColor(0xff5EA7FF) //????????????
                            //                        .rotate(-30) //????????????
                            .position(latLng);
                    //?????????????????????????????????
                    mMap.addOverlay(mTextOptions);
                    distancePoints.add(latLng);
                } else {
                    if (distancePoints.size() == 2) {
                        distancePoints.remove(0);
                    }
                    distancePoints.add(latLng);
                    distance =
                            distance + DistanceUtil.getDistance(distancePoints.get(distancePoints.size() - 1),
                                    distancePoints.get(distancePoints.size() - 2));
                    DecimalFormat df = new DecimalFormat("#.0");
                    String disStr = df.format(distance / 1000);
                    TextView child = new TextView(mContext);
                    child.setTextSize(15);
                    child.setBackgroundColor(Color.parseColor("#ffffff"));
                    child.setTextColor(Color.parseColor("#ff5ea7ff"));
                    child.setText(disStr + "??????");
                    // ?????????????????????addView??????
                    MapViewLayoutParams params2;
                    InfoWindow pinfo;
                    if (distancePoints.get(1).latitude > distancePoints.get(0).latitude) {
                        pinfo = new InfoWindow(child, latLng, -10);
                    } else {
                        pinfo = new InfoWindow(child, latLng, 70);
                    }
                    mMap.showInfoWindow(pinfo);
                }
                //????????????????????????
                //mPloyline ????????????
                if (distancePoints.size() > 1) {
                    //?????????????????????
                    OverlayOptions mOverlayOptions = new PolylineOptions()
                            .width(10)
                            .color(0xff5ea7ff)
                            .points(distancePoints);
                    Overlay mPolyline = mMap.addOverlay(mOverlayOptions);
                }
            }

            @Override
            public void onMapPoiClick(MapPoi mapPoi) {
                onMapClick(mapPoi.getPosition());
            }
        };

        // TODO: 2021/3/4   ??????????????? ???????????? 
//        if (!closeMarquee) {
//            mPresenter.getBannerNews(MapContract.BANNER_NEWS);
//        }
    }

    /**
     * ???????????????
     */
    private void clickCaseItem(MapClusterItem mmCase, BaiduMap map) {
        CaseDesBean.DataBean mCase = mmCase.mcase;
        LatLng caseLocation = new LatLng(mCase.getLatitude(), mCase.getLongitude());
        MapUtil.mapMoveTo(map, caseLocation);
        infowindowPeople = View.inflate(mContext, R.layout.infowindow_case, null);
        //????????????????????????????????????view??????
        TextView titleTv = infowindowPeople.findViewById(R.id.item_title);
        TextView contentTv = infowindowPeople.findViewById(R.id.item_content);
        TextView timeTv = infowindowPeople.findViewById(R.id.item_time);
        titleTv.setText(mContext.getString(R.string.case_addr) + mCase.getAddress());
        contentTv.setText(mContext.getString(R.string.case_decontent) + mCase.getCaseContent());
        timeTv.setText(mContext.getString(R.string.case_date) + mCase.getCreateDate());
        ImageLoadUtil.loadImageCache(getContext(), mCase.getPhotoOne(),
                (ImageView) infowindowPeople.findViewById(R.id.case_img));
        infowindowPeople.findViewById(R.id.case_navigation).setOnClickListener(v -> {
            getBaseAppActivity().navigationLogic(new LatLng(mCase.getLatitude(),
                    mCase.getLongitude()), mCase.getAddress());
        });
        infowindowPeople.findViewById(R.id.case_follow).setOnClickListener(v -> {
            startActivity(new Intent(mContext, CaseInfoActivity.class).putExtra("id",
                    mCase.getId()));
        });
        MapViewLayoutParams params2 = new MapViewLayoutParams.Builder()
                .layoutMode(MapViewLayoutParams.ELayoutMode.mapMode)
                .position(caseLocation)
                .width(MapViewLayoutParams.WRAP_CONTENT)
                .height(MapViewLayoutParams.WRAP_CONTENT)
                .yOffset(-mmCase.getBd().getBitmap().getHeight() * clickType)
                .build();
        mMapView.addView(infowindowPeople, params2);
    }

    /**
     * ???????????????
     */
    private void clickCarItem(MapClusterItem item, BaiduMap map) {
        PoliceCarBean.DataBean car = item.car;
        LatLng peopleLocation = new LatLng(car.getLat(), car.getLng());
        MapUtil.mapMoveTo(map, peopleLocation);
        InfoWindow pinfo;
        infowindowPeople = View.inflate(mContext, R.layout.infowindow_car, null);
        //????????????????????????????????????view??????
        String powerValue = "";
        if (car.getPowerValue() == null) {
            powerValue = "null";
        }
        StringBuffer strb = new StringBuffer(car.getDeviceName());
        StringBuffer strb2 = new StringBuffer(car.getImei());
        if (car.getDeviceName().length() > 12) {
            strb.insert(12, " ");
        }
        if (car.getImei().length() > 12) {
            strb2.insert(12, " ");
        }
        StringBuilder carInfo = new StringBuilder();
        carInfo.append(mContext.getString(R.string.car_name)).append(strb.toString()).append("\n");
        if (StringTools.isStringValueOk(car.getSpeed())) {
            carInfo.append(mContext.getString(R.string.car_speed)).append(car.getSpeed()).append(
                    "km/h").append("\n");
        }
        carInfo.append(mContext.getString(R.string.car_imei)).append(strb2.toString()).append("\n");
        if (StringTools.isStringValueOk(powerValue)) {
            carInfo.append(mContext.getString(R.string.car_power)).append(powerValue);
        }
        ((TextView) infowindowPeople.findViewById(R.id.car_infos)).setText(carInfo.toString());
        ImageLoadUtil.loadImageCache(mContext.getApplicationContext(), car.getImg(),
                infowindowPeople.findViewById(R.id.car_img));
        infowindowPeople.findViewById(R.id.car_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                startActivity(new Intent(mContext, CarLiveActivity.class).putExtra(CarLiveActivity
                //                .STREAM_CAMERA_NAME
                //                        , car.getDeviceName()).putExtra(CarLiveActivity.STREAM_CAMERA_URL,
                //                        UrlFormatUtil.getCarStream(car.getImei()))
                //                );
            }
        });

        infowindowPeople.findViewById(R.id.car_history).setOnClickListener(v -> {
            infowindowPeople.findViewById(R.id.his_ll).setVisibility(View.VISIBLE);
            infowindowPeople.findViewById(R.id.car_btn_rl).setVisibility(View.INVISIBLE);
            infowindowPeople.findViewById(R.id.today_history).setOnClickListener(v1 -> {
                startActivity(new Intent(mContext, HistoryTrack.class)
                        .putExtra("type", HistoryTrack.CAR)
                        .putExtra("time", HistoryTrack.TODAY)
                        .putExtra("carImei", car.getImei()));
            });
            infowindowPeople.findViewById(R.id.yesterday_history).setOnClickListener(v1 -> {
                startActivity(new Intent(mContext, HistoryTrack.class)
                        .putExtra("type", HistoryTrack.CAR)
                        .putExtra("time", HistoryTrack.YESTERDAY)
                        .putExtra("carImei", car.getImei()));
            });
            infowindowPeople.findViewById(R.id.zidingyi_history).setOnClickListener(v1 -> {
                startActivity(new Intent(mContext, SelectTime.class)
                        .putExtra("type", HistoryTrack.CAR)
                        .putExtra("carImei", car.getImei()));
            });
        });
        MapViewLayoutParams params2 = new MapViewLayoutParams.Builder()
                .layoutMode(MapViewLayoutParams.ELayoutMode.mapMode)
                .position(peopleLocation)
                .width(MapViewLayoutParams.WRAP_CONTENT)
                .height(MapViewLayoutParams.WRAP_CONTENT)
                .yOffset(-item.getBd().getBitmap().getHeight() * clickType)
                .build();
        mMapView.addView(infowindowPeople, params2);
    }

    /**
     * ??????????????????
     */
    private void clickPeopleItem(PoliceDetailBean server , BaiduMap map) {
        PoliceDetailBean.DataBean people = server.getData();
        LatLng peopleLocation = new LatLng(people.getLatitude(), people.getLongitude());
        MapUtil.mapMoveTo(map, peopleLocation);
        infowindowPeople = View.inflate(mContext, R.layout.infowindow_people, null);//????????????????????????????????????view??????
        String peopleInfo = "";
        peopleInfo = mContext.getString(R.string.people_name) + people.getNickname() + "\n"
                + mContext.getString(R.string.people_phone) + people.getAccount() + "\n"
                + mContext.getString(R.string.people_depaName) + people.getDepartmentName() + "\n"
                + mContext.getString(R.string.police_status) + (people.getState() == 1 ? "??????" : "??????");
        ((TextView) infowindowPeople.findViewById(R.id.people_infos)).setText(peopleInfo);
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(people.getId()));
        ImageLoadUtil.loadImageCache(mContext.getApplicationContext(),
                people.getHeadPortrait(),
                (ImageView) infowindowPeople.findViewById(R.id.head_image));
        infowindowPeople.findViewById(R.id.people_chat).setOnClickListener(v -> {
            try {
                ModuleIm_Init.chat(mContext, people.getAccount(), "????????????:" + people.getNickname());
            } catch (IllegalArgumentException e) {
                ToastUtils.toast(mContext, "??????????????????");
            }
        });
        infowindowPeople.findViewById(R.id.people_history).setOnClickListener(v -> {
            infowindowPeople.findViewById(R.id.his_ll).setVisibility(View.VISIBLE);
            infowindowPeople.findViewById(R.id.people_origin).setVisibility(View.GONE);

        });

        infowindowPeople.findViewById(R.id.today_history).setOnClickListener(v -> {
            startActivity(new Intent(mContext, HistoryTrack.class)
                    .putExtra("type", HistoryTrack.PEOPLE)
                    .putExtra("time", HistoryTrack.TODAY)
                    .putExtra("peopleId", String.valueOf(people.getId())));
        });
        infowindowPeople.findViewById(R.id.yesterday_history).setOnClickListener(v -> {
            startActivity(new Intent(mContext, HistoryTrack.class)
                    .putExtra("type", HistoryTrack.PEOPLE)
                    .putExtra("time", HistoryTrack.YESTERDAY)
                    .putExtra("peopleId", String.valueOf(people.getId())));
        });
        infowindowPeople.findViewById(R.id.zidingyi_history).setOnClickListener(v -> {
            startActivity(new Intent(mContext, SelectTime.class)
                    .putExtra("type", HistoryTrack.PEOPLE)
                    .putExtra("time", HistoryTrack.CUSTOM)
                    .putExtra("peopleId", String.valueOf(people.getId())));
        });
        MapViewLayoutParams params2 = new MapViewLayoutParams.Builder()
                .layoutMode(MapViewLayoutParams.ELayoutMode.mapMode)
                .position(peopleLocation)
                .width(MapViewLayoutParams.WRAP_CONTENT)
                .height(MapViewLayoutParams.WRAP_CONTENT)
                .yOffset(-people.getBd().getBitmap().getHeight() * clickType)
                .build();
        mMapView.addView(infowindowPeople, params2);
    }

    /**
     * ???????????????
     */
    private void clickInspectionItem(MapClusterItem item, BaiduMap map) {
        ResponseInspection.DataBean inspection = item.inspection;
        startActivity(new Intent(mContext, InspectionInfoActivity.class).putExtra("id",
                inspection.getId()));
    }

    /**
     * ???????????????
     */
    private void clickNewsItem(MapClusterItem item, BaiduMap map) {
        ResponseNews.News news = item.news;
        MyApp.gotoNewsInfo(news.getTypeId(), news.getId(), mContext);
    }

    /**
     * ????????????
     */
    private void clickSiteItem(MapClusterItem item) {
        ResponseSiteBean.DataBean dataBean = item.site;
        startActivity(new Intent(mContext, HealthOrganizeActivity.class).putExtra(HealthOrganizeActivity.ID,
                dataBean.getId()));
    }

    private boolean panoramaSwitch = false;
    private BaiduMap.OnMapClickListener panoramaClick;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.weixingtu:
                mMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);//??????????????????
                mapSP.putIntSP("mapType", BaiduMap.MAP_TYPE_SATELLITE);
                switchMapType(v.getId());
                break;
            case R.id.erdpingmiantu:
                mMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//??????????????????
                mapSP.putIntSP("mapType", BaiduMap.MAP_TYPE_NORMAL);
                switchMapType(v.getId());
                break;
            case R.id.sandfushitu:
                //3D?????????
                mMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                mapSP.putIntSP("mapType", BaiduMap.MAP_TYPE_NORMAL);
                switchMapType(v.getId());
                break;
            case R.id.jiejing:
                if (panoramaSwitch) {
                    mMap.setOnMapClickListener(normalClick);
                    panoramaSwitch = false;
                    switchMapType(R.id.erdpingmiantu);
                } else {
                    mMap.setOnMapClickListener(panoramaClick);
                    panoramaSwitch = true;
                    switchMapType(v.getId());
                }
                break;
            case R.id.mylocation:
                MapUtil.mapZoom(MapUtil.MAP_ZOOM_TO, mMap, 16);
                MapUtil.mapMoveTo(mMap, myLocation);
                break;
            case R.id.test1:

                break;
            case R.id.test2:
                startActivity(new Intent(mContext, HistoryTrack.class));
                break;
            case R.id.distance_switch:
                mMap.setOnMapClickListener(distanceUtilSwitch ? normalClick : distanceUtilClick);
                if (distanceUtilSwitch) {
                    mMap.setOnMapClickListener(normalClick);
                    distance = 0;
                    clearTheMap(mMap);
                    distancePoints.clear();
                    distanceUtilSwitch = false;
                } else {
                    mMap.setOnMapClickListener(distanceUtilClick);
                    distanceUtilSwitch = true;
                }
                break;
            case R.id.zoomminus:
                MapUtil.mapZoom(MapUtil.MAP_ZOOM_OUT1, mMap);
                break;
            case R.id.zoomplus:
                MapUtil.mapZoom(MapUtil.MAP_ZOOM_IN1, mMap);
                break;
            case R.id.distance_util_btn:
                startActivity(new Intent(mContext, DistanceUtilActivity.class));
                break;
            case R.id.search_ll:
                if (getBaseAppActivity().isCompleteUserInfo()) {
                    startActivity(new Intent(mContext, SearchActivity.class));
                }
                break;
            case R.id.scan_iv:
                if ((System.currentTimeMillis() - currentTime) < 800) {
                    return;
                }
                if (!getBaseAppActivity().isCompleteUserInfo()) {
                    return;
                }
                currentTime = System.currentTimeMillis();
                getActivity().startActivityForResult(new Intent(getActivity(),
                        QRScanActivity.class), AppUtils.QR_SCAN_NOMAL);
                break;
            //????????????
            case R.id.call_police_iv:
                if (!getBaseAppActivity().isLogin()) {
                    getBaseAppActivity().goLogin();
                    return;
                }
                //????????????????????????
                //?????????????????????0??????????????????1????????????????????????2?????????????????????3??????????????????

                UserBean userBean = Hawk.get(AppUtils.SP_KEY_USER);
                if (userBean != null) {
                    if (1 == userBean.getData().getBlacklist()) {
                        ToastUtils.toast(mContext, "???????????????????????????");
                        return;
                    }

                    int status = UserInfoManager.getRealNameStatus();
                    if (2 != status) {
                        new AlertDialog.Builder(mContext)
                                .setMessage(R.string.auth_msg)
                                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).setPositiveButton(R.string.to_auth, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mContext.startActivity(new Intent(mContext, VerifiedActivity.class).putExtra(VerifiedActivity.VERIFIED_STATUS, status));
                            }
                        }).show();
                        return;
                    }
                    startActivity(new Intent(mContext, CallToPoliceActivity.class));
                }
                break;
            case R.id.delete_icon:
                closeMarquee = true;
                marqueeView.stopFlipping();
                mMarqueeCl.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * ???????????????
     */
    @Override
    public boolean onClusterClick(Cluster<MapClusterItem> cluster) {
        List<MapClusterItem> items = new ArrayList<MapClusterItem>(cluster.getItems());
        //????????????--???????????????
        //if (items.get(0).getType().equals(MapClusterItem.YBSAS)) return false;
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", (Serializable) items);
        mapBottomListDialog = new MapBottomListDialog();
        mapBottomListDialog.setArguments(bundle);
        mapBottomListDialog.setCallback(this);
        mapBottomListDialog.show(getFragmentManager(), "list");
        LogUtil.d("mapLevel->" + String.valueOf(mMapView.getMapLevel()));
        return false;
    }

    /**
     * ?????????????????????
     */
    @Override
    public boolean onClusterItemClick(MapClusterItem item) {
        LogUtil.d("clicked item");
        if (infowindowPeople != null) {
            mMapView.removeView(infowindowPeople);
        }
        switch (item.getType()) {
            case MapClusterItem.STREAM_CAMERA:
                if (0 == item.streamCamera.getFlag()) {
                    bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.steam_cameras_tip);
                    //????????????????????????????????????
                    mPresenter.getAllStreamCamerasFromVCR(mPresenter.getPublishMultipartBody().addFormDataPart(
                            "dvrId", String.valueOf(item.streamCamera.getId())).build(),
                            MapContract.GET_STREAM_CAMERAS_FROM_VCR);
                } else {
                    bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.camera_tip);
                    updateMarkerIcon(item.streamCamera.getEzOpen());
                }
                if (clickType == 1 || nowMarkerId.equals(String.valueOf(item.streamCamera.getNumber()))) {
                    if (1 == item.streamCamera.getFlag()) {
                        currentStreamCamera = item.streamCamera;
                        startActivity(new Intent(mContext.getApplicationContext(), PlayerLiveActivity.class)
                                .putExtra(PlayerLiveActivity.STREAM_CAMERA_ID, currentStreamCamera.getId())
                                .putExtra(PlayerLiveActivity.STREAM_CAMERA_NUM, currentStreamCamera.getNumber())
                                .putExtra(PlayerLiveActivity.STREAM_CAMERA_THUM_URL, currentStreamCamera.getEzOpen()));
                    }
                }
                nowMarkerId = String.valueOf(item.streamCamera.getNumber());
                break;
            case MapClusterItem.CASE:
                updateMarkerIcon(item.mcase.getPhotoOne());
                if (clickType == 1 || nowMarkerId.equals(String.valueOf(item.mcase.getId()))) {
                    clickCaseItem(item, mMap);
                }
                nowMarkerId = String.valueOf(item.mcase.getId());
                break;
            case MapClusterItem.CAR:
                updateMarkerIcon(item.car.getImg());
                if (clickType == 1 || nowMarkerId.equals(String.valueOf(item.car.getImei()))) {
                    clickCarItem(item, mMap);
                }
                nowMarkerId = item.car.getImei();
                break;
            case MapClusterItem.INSPECTION:
                //??????
                updateMarkerIcon(item.inspection.getLogoUrl());
                if (clickType == 1 || nowMarkerId.equals(String.valueOf(item.inspection.getId()))) {
                    clickInspectionItem(item, mMap);
                }
                nowMarkerId = String.valueOf(item.inspection.getId());
                break;
            case MapClusterItem.NEWS:
                //??????
                updateMarkerIcon(item.news.getPicture());
                if (clickType == 1 || nowMarkerId.equals(String.valueOf(item.news.getId()))) {
                    clickNewsItem(item, mMap);
                }
                nowMarkerId = String.valueOf(item.news.getId());
                break;
            case MapClusterItem.HEALTH_OG:
                //????????????
                updateMarkerIcon(item.site.getPhoto());
                if (clickType == 1 || nowMarkerId.equals(String.valueOf(item.site.getId()))) {
                    clickSiteItem(item);
                }
                nowMarkerId = String.valueOf(item.site.getId());
                break;
            case MapClusterItem.SERVERS:
                //????????????
                updateMarkerIcon(item.server.getHeadPortrait());
                if (clickType == 1 || nowMarkerId.equals(String.valueOf(item.server.getId()))) {
                    //????????????????????????
                    mPresenter.getPolicePeopleDetail(item, MapContract.GET_PEOPLE_DETAIL);
                }
                nowMarkerId = String.valueOf(item.server.getId());
                break;
        }
        return false;
    }


    /**
     * ??????marker??????
     */
    public void updateMarkerIcon(String path) {
        if (nowMarker == null) {
            return;
        }
        ImageLoadUtil.getBitmap(getContext().getApplicationContext(), path, R.mipmap.ic_error,
                new ImageLoadUtil.BitmapCallBack() {
                    @Override
                    public void getBitmap(Bitmap bitmap) {
                        try {
                            nowMarker.setIcon(BitmapDescriptorFactory.fromBitmap(ImageUtil.combineBitmap(
                                    BitmapFactory.decodeStream(getResources().getAssets().open(
                                            "ic_map_shop_bg.png")),
                                    ImageUtil.getRoundedCornerBitmap(ImageUtil.zoomImg(bitmap), 200))));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        releaseBottomListDialog();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        //baiDuLocationUtils.stop();
        mMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        myOrientationListener.stop();
        super.onDestroy();
    }

    /**
     * ??????????????????
     *
     * @param bdLocation
     */
    @Override
    public void onLocationChange(BDLocation bdLocation) {
        if (bdLocation == null || mMapView == null) {
            return;
        }
        if (isFisrt) {
            MapUtil.mapMoveTo(mMap, new LatLng(bdLocation.getLatitude(),
                    bdLocation.getLongitude()));
            isFisrt = false;
        }
        LogUtil.d("??????????????????->" + "latitude=" + bdLocation.getLatitude()
                + "&longitude=" + bdLocation.getLongitude());
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(bdLocation.getRadius())
                // ?????????????????????????????????????????????????????????0-360
                .direction(direct).latitude(bdLocation.getLatitude())
                .longitude(bdLocation.getLongitude()).build();
        mMap.setMyLocationData(locData);
        province = bdLocation.getProvince();
        city = bdLocation.getCity();
        area = bdLocation.getDistrict();
        myLocation = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
        MyApp.app.setMyLocation(myLocation);
        MyApp.app.setBdLocation(bdLocation);
        locationRadius = bdLocation.getRadius();
        //?????????????????????
        if (MyApp.getUser() != null &&
                MyApp.getUser().getData() != null &&
                MyApp.getUser().getData().getSettleStatus() == 2) {
            LocationBean locationBean = new LocationBean(bdLocation.getAddrStr() + "",
                    LocateAndUpload.getLocType(bdLocation),
                    bdLocation.getLongitude() + "",
                    bdLocation.getLatitude() + "",
                    DateUtil.getCurrentTime() + "");
            MyApp.getDaoSession().getLocationBeanDao().save(locationBean);
        }
    }

    /**
     * ????????????????????????
     *
     * @param item
     */
    @Override
    public void onBottomListItemClick(MapClusterItem item) {
        if (infowindowPeople != null) {
            mMapView.removeView(infowindowPeople);
        }
        if (nowMarker != null) {
            nowMarker.setIcon(bitmapDescriptor);
        }
        nowMarker = null;
        mMap.hideInfoWindow();
        clickType = 1;
        onClusterItemClick(item);
        releaseBottomListDialog();
    }

    @Override
    public void onNavagition(LatLng latLng, String addr) {
        getBaseAppActivity().navigationLogic(latLng, addr);
    }

    /**
     * ??????dialog
     */
    private void releaseBottomListDialog() {
        if (mapBottomListDialog != null) {
            mapBottomListDialog.setCallback(null);
            mapBottomListDialog.dismiss();
            mapBottomListDialog = null;
        }
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case MapContract.GET_MENUS://????????????????????????
                MapMenuButton mapMenuButton = (MapMenuButton) o;
                mMenuAdapter.setNewData(mapMenuButton.getData());
                break;
            case MapContract.BANNER_NEWS:
                BannerNewsBean bannerNewsBean = (BannerNewsBean) o;
                if (bannerNewsBean != null) {
                    noticeList = bannerNewsBean.getData();
                    if (noticeList != null && noticeList.size() > 0) {
                        mMarqueeCl.setVisibility(View.VISIBLE);
                        marqueeView.startWithList(noticeList);
                    } else {
                        mMarqueeCl.setVisibility(View.GONE);
                    }
                }
                break;
            case MapContract.GET_STREAM_CAMERAS:
                StreamCameraBean streamCameraBean = (StreamCameraBean) o;
                if (streamCameraBean != null) {
                    List<StreamCameraBean.DataBean> datas = streamCameraBean.getData();
                    if (datas != null) {
                        for (StreamCameraBean.DataBean camera : datas) {
                            MapClusterItem mCItem = new MapClusterItem(
                                    new LatLng(camera.getLatitude(), camera.getLongitude()), camera);
                            clusterItemList.add(mCItem);
                        }
                    }
                    clusterManager.addItems(clusterItemList);
                    clusterManager.cluster();
                    dateType = 0;
                }
                break;
            case MapContract.GET_STREAM_CAMERAS_FROM_VCR:
                StreamCameraBean bean = (StreamCameraBean) o;
                if (bean != null) {
                    List<StreamCameraBean.DataBean> datas = bean.getData();
                    if (datas != null) {
                        List<MapClusterItem> items = new ArrayList<MapClusterItem>();
                        for (StreamCameraBean.DataBean camera : datas) {
                            camera.setFlag(1);
                            MapClusterItem mCItem = new MapClusterItem(
                                    new LatLng(camera.getLatitude(), camera.getLongitude()), camera);
                            items.add(mCItem);
                        }
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data", (Serializable) items);
                        MapBottomListDialog mapBottomListDialog = new MapBottomListDialog();
                        mapBottomListDialog.setArguments(bundle);
                        mapBottomListDialog.setCallback(this);
                        mapBottomListDialog.show(getFragmentManager(), "list");
                    }
                }
                break;
            case MapContract.GET_CASES://??????
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.case_tip);
                CaseDesBean caseDesBean = (CaseDesBean) o;
                if (caseDesBean != null) {
                    List<CaseDesBean.DataBean> cases = caseDesBean.getData();
                    for (CaseDesBean.DataBean aCase : cases) {
                        MapClusterItem mCItem = new MapClusterItem(
                                new LatLng(aCase.getLatitude(), aCase.getLongitude()), aCase);
                        clusterItemList.add(mCItem);
                    }
                    clusterManager.addItems(clusterItemList);
                    clusterManager.cluster();
                    dateType = 1;
                }
                break;
            case MapContract.GET_CARS://??????????????????
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.car_tip);
                PoliceCarBean responseCar = (PoliceCarBean) o;
                for (PoliceCarBean.DataBean item : responseCar.getData()) {
                    MapClusterItem mCItem = new MapClusterItem(
                            new LatLng(item.getLat(), item.getLng()), item);
                    clusterItemList.add(mCItem);
                }
                clusterManager.addItems(clusterItemList);
                clusterManager.cluster();
                break;
            case MapContract.GET_INSPECTION://??????
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.inspection_tip);
                ResponseInspection responseInspection = (ResponseInspection) o;
                if (responseInspection != null) {
                    List<ResponseInspection.DataBean> inspections = responseInspection.getData();
                    for (ResponseInspection.DataBean inspection : inspections) {
                        MapClusterItem mCItem = new MapClusterItem(
                                new LatLng(inspection.getLatitude(), inspection.getLongitude()),
                                inspection);
                        clusterItemList.add(mCItem);
                    }
                    clusterManager.addItems(clusterItemList);
                    clusterManager.cluster();
                }
                break;
            case MapContract.GET_NEWS://??????
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.news_tip);
                ResponseNews responseNews = (ResponseNews) o;
                if (responseNews != null) {
                    List<ResponseNews.News> news = responseNews.getData();
                    for (ResponseNews.News newsItem : news) {
                        MapClusterItem mCItem = new MapClusterItem(
                                new LatLng(newsItem.getLatitude(), newsItem.getLongitude()),
                                newsItem);
                        clusterItemList.add(mCItem);
                    }
                    clusterManager.addItems(clusterItemList);
                    clusterManager.cluster();
                }
                break;
            case MapContract.GET_SITES:
                //????????????
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.site_tip);
                ResponseSiteBean realLocBean = (ResponseSiteBean) o;
                int size = realLocBean.getData().size();
                for (int i = 0; i < size; i++) {
                    MapClusterItem mCItem = new MapClusterItem(
                            new LatLng(realLocBean.getData().get(i).getLatitude(),
                                    realLocBean.getData().get(i).getLongitude()),
                            realLocBean.getData().get(i), R.mipmap.site_tip);
                    clusterItemList.add(mCItem);
                }
                clusterManager.addItems(clusterItemList);
                clusterManager.cluster();
                break;
            case MapContract.GET_PEOPLE_DETAIL:
                //??????????????????
                PoliceDetailBean server = (PoliceDetailBean) o;
                if (server != null) {
                    clickPeopleItem(server, mMap);
                }
                break;
            case MapContract.GET_SERVERS://????????????
                //
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.key_personnel_tip);
                ServerPeopleBean responseKeyPersonnel = (ServerPeopleBean) o;
                if (responseKeyPersonnel != null) {
                    List<ServerPeopleBean.DataBean> keyPersonnels = responseKeyPersonnel.getData();
                    for (ServerPeopleBean.DataBean keyPersonnel : keyPersonnels) {
                        MapClusterItem mCItem = new MapClusterItem(
                                new LatLng(keyPersonnel.getLatitude(), keyPersonnel.getLongitude()),
                                keyPersonnel);
                        clusterItemList.add(mCItem);
                    }
                    clusterManager.addItems(clusterItemList);
                    clusterManager.cluster();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onError(String tag, Object o) {
        String msg = (String) o;
        ToastUtils.toast(mContext, msg);
        switch (tag) {
            case MapContract.GET_CARS:
                //                ToastUtils.toast(mContext, msg);
                break;
            case MapContract.GET_MENUS:
                //??????
                break;
            case MapContract.GET_SITES://????????????
                break;
            case MapContract.GET_CASES:
                break;
            case MapContract.GET_INSPECTION:
                break;
            case MapContract.GET_NEWS:
                break;
            case MapContract.GET_SERVERS:
                break;
            default:
                if (mMenuAdapter.getData().size() > 0 && clickedButton > -1) {
                    mMenuAdapter.getData().get(clickedButton).setSelected(false);
                    mMenuAdapter.notifyItemChanged(clickedButton);
                    clickedButton = -1;
                }
                break;
        }
    }
}

