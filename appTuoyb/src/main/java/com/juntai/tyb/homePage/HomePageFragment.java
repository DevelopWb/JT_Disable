package com.juntai.tyb.homePage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.HeatMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.basecomponent.utils.DisplayUtil;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.bdmap.service.LocateAndUpload;
import com.juntai.disabled.bdmap.utils.MapUtil;
import com.juntai.disabled.bdmap.utils.clusterutil.clustering.Cluster;
import com.juntai.disabled.bdmap.utils.clusterutil.clustering.ClusterManager;
import com.juntai.tyb.AppHttpPath;
import com.juntai.tyb.base.BaseAppFragment;
import com.juntai.tyb.base.SingleTextAdapter;
import com.juntai.tyb.bean.TextListBean;
import com.juntai.tyb.bean.careTaker.YearsBean;
import com.juntai.tyb.bean.healthOrg.HealthOrgPositionBean;
import com.juntai.tyb.bean.HomePageMenuBean;
import com.juntai.tyb.bean.ServicePeoplePositionBean;
import com.juntai.tyb.bean.careTaker.CareRecordPositionBean;
import com.juntai.tyb.bean.stream.StreamCameraBean;
import com.juntai.tyb.hcb.R;
import com.juntai.tyb.homePage.camera.PlayContract;
import com.juntai.tyb.homePage.camera.ijkplayer.PlayerLiveActivity;
import com.juntai.tyb.homePage.careTaker.careInfo.CareTakerInfoActivity;
import com.juntai.tyb.homePage.healthOrganize.HealthOrganizeActivity;
import com.juntai.tyb.homePage.map.ClusterClickAdapter;
import com.juntai.tyb.homePage.map.MapClusterItem;
import com.juntai.tyb.homePage.olderCareData.CareInfoActivity;
import com.juntai.tyb.homePage.search.SearchActivity;
import com.juntai.tyb.homePage.search.SearchContract;
import com.juntai.tyb.homePage.weather.WeatherActivity;
import com.juntai.tyb.uitils.ImageUtil;
import com.juntai.tyb.uitils.StringTools;
import com.juntai.tyb.uitils.UrlFormatUtil;
import com.juntai.tyb.uitils.UserInfoManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @aouther tobato
 * @description 更新  主页fragment
 * @date 2020/4/21 11:06
 */
public class HomePageFragment extends BaseAppFragment<HomePagePresent> implements BaiduMap.OnMapLoadedCallback,
        View.OnClickListener, LocateAndUpload.Callback, ClusterManager.OnClusterClickListener<MapClusterItem>,
        ClusterManager.OnClusterItemClickListener<MapClusterItem>, HomePageContract.IHomePageView {
    private MapView mBmapView;
    private LinearLayout mSearchLl;
    private RecyclerView mHomePageRightMenuRv;
    private ImageView mZoomplus;
    private ImageView mZoomminus;
    private View infowindow = null;
    private Button mMylocation;
    private ImageView mSatalliteMapIv;
    private ImageView mTwoDMapIv;
    private ImageView mThreeDMapIv;
    private SwitchCompat mHeatSw;
    private SwitchCompat mTrafficSv;
    private DrawerLayout mDrawerlayout;
    private HomePageMenuAdapter menuAdapter;
    private BaiduMap mBaiduMap;
    private RelativeLayout mDrawerRightLaytoutRl;
    public static String province = null;
    public static String city = null;
    public static String area = null;
    private double lat;
    private double lng;
    private boolean isFisrt = true;
    private PopupWindow popupWindow;
    private List<MapClusterItem> clusterItemList = new ArrayList<>();
    private ClusterManager clusterManager;
    private BitmapDescriptor bitmapDescriptor;
    //当前点击的marker
    Marker nowMarker;
    private int clickItemType = 2;//2单个marker点击，1聚合列表点击
    private MapStatus lastPosition;
    String nowMarkerId = "";//当前markerid
    StreamCameraBean.DataBean currentStreamCamera;
    HeatMap mHeatMap = null;
    private List<LatLng> heatMapItems = new ArrayList<>();
    private ImageView menuImageIv;
    private BottomSheetDialog mapBottomDialog;
    private ClusterClickAdapter clusterClickAdapter;


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.satallite_map_iv) {
            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);//打开卫星地图
            mapType(v.getId());
        } else if (id == R.id.two_d_map_iv) {
            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//
            mapType(v.getId());
        } else if (id == R.id.three_d_map_iv) {
            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//
            mapType(v.getId());
        } else if (id == R.id.search_ll) {//搜索
            startActivity(new Intent(getContext(), SearchActivity.class));
        } else if (id == R.id.zoomminus) {
            MapUtil.mapZoom(MapUtil.MAP_ZOOM_OUT1, mBaiduMap);
        } else if (id == R.id.zoomplus) {
            MapUtil.mapZoom(MapUtil.MAP_ZOOM_IN1, mBaiduMap);
        } else if (id == R.id.mylocation) {//当前位置
            MapUtil.mapZoom(MapUtil.MAP_ZOOM_TO, mBaiduMap, 16);
            MapUtil.mapMoveTo(mBaiduMap, new LatLng(lat, lng));
        }
    }

    /**
     * 地图模式切换
     *
     * @param viewId
     */
    private void mapType(int viewId) {
        if (viewId == R.id.satallite_map_iv) {
            mTwoDMapIv.setBackgroundColor(getResources().getColor(R.color.transparent));
            mThreeDMapIv.setBackgroundColor(getResources().getColor(R.color.transparent));
            mSatalliteMapIv.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            //                weixingTv.setTextColor(getResources().getColor(R.color.colorAccent));
            //                twdTv.setTextColor(Color.parseColor("#8a000000"));
            //                thdTv.setTextColor(Color.parseColor("#8a000000"));
        } else if (viewId == R.id.two_d_map_iv) {
            mTwoDMapIv.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            mThreeDMapIv.setBackgroundColor(getResources().getColor(R.color.transparent));
            mSatalliteMapIv.setBackgroundColor(getResources().getColor(R.color.transparent));
            //                twdTv.setTextColorx(getResources().getColor(R.color.colorAccent));
            //                thdTv.setTextColor(Color.parseColor("#8a000000"));
            //                weixingTv.setTextColor(Color.parseColor("#8a000000"));
        } else if (viewId == R.id.three_d_map_iv) {
            mTwoDMapIv.setBackgroundColor(getResources().getColor(R.color.transparent));
            mThreeDMapIv.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            mSatalliteMapIv.setBackgroundColor(getResources().getColor(R.color.transparent));
            //                thdTv.setTextColor(getResources().getColor(R.color.colorAccent));
            //                twdTv.setTextColor(Color.parseColor("#8a000000"));
            //                weixingTv.setTextColor(Color.parseColor("#8a000000"));
        }
    }

    @Override
    public void onMapLoaded() {
    }

    @Override
    protected HomePagePresent createPresenter() {
        return new HomePagePresent();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.homefragmentnew;
    }

    @Override
    protected void initView() {

        mBmapView = (MapView) getView(R.id.bmapView);
        mBmapView.showZoomControls(false);
        mBaiduMap = mBmapView.getMap();
        mSearchLl = (LinearLayout) getView(R.id.search_ll);
        mSearchLl.setOnClickListener(this);
        mHomePageRightMenuRv = (RecyclerView) getView(R.id.home_page_right_menu_rv);
        mZoomplus = (ImageView) getView(R.id.zoomplus);
        mZoomplus.setOnClickListener(this);
        mZoomminus = (ImageView) getView(R.id.zoomminus);
        mZoomminus.setOnClickListener(this);
        mMylocation = (Button) getView(R.id.mylocation);
        mMylocation.setOnClickListener(this);
        initUISetting();
        initMenuAdapter();
        initDrawerLayout();
        initClusterManagerAndMap();
    }

    /**
     * 配置地图相关控件
     */
    private void initUISetting() {
        //实例化UiSettings类对象
        UiSettings mUiSettings = mBaiduMap.getUiSettings();
        //通过设置enable为true或false 选择是否显示指南针
        mUiSettings.setCompassEnabled(false);
        //开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        MyLocationConfiguration config = new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL, true, null);
        mBaiduMap.setMyLocationConfiguration(config);
    }

    /**
     * 初始化ClusterManager和map
     */
    private void initClusterManagerAndMap() {
        clusterManager = new ClusterManager<>(mContext, mBaiduMap);
        clusterManager.setOnClusterItemClickListener(HomePageFragment.this);//点点击
        clusterManager.setOnClusterClickListener(HomePageFragment.this);//聚合展开
        //地图图标点击事件的监听
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //不是大的聚合点
                if (!clusterManager.getClusterMarkerCollection().getMarkers().contains(marker)) {
                    if (nowMarker != null) {
                        if (bitmapDescriptor != null) {
                            nowMarker.setIcon(bitmapDescriptor);
                        }

                    }
                    //marker.setIcon(BitmapDescriptorFactory.fromBitmap(compoundBitmap
                    // (BitmapFactory.decodeResource(getResources(),R.mipmap
                    // .ic_client_location_pre), BitmapFactory.decodeResource(getResources(),R
                    // .mipmap.ic_my_default_head))));
                    nowMarker = marker;
                    clickItemType = 2;
                }
                //聚合管理类响应点击事件
                clusterManager.onMarkerClick(marker);
                return false;
            }
        });
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mBmapView.removeView(infowindow);
            }

            @Override
            public void onMapPoiClick(MapPoi mapPoi) {
                mBmapView.removeView(infowindow);
            }
        });
        //
        //手动缩放地图的时候 聚合图标
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {
            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
                if (lastPosition != null && lastPosition.zoom != mBaiduMap.getMapStatus().zoom) {
                    mBmapView.removeView(infowindow);
                    clickItemType = 2;
                    if (nowMarker != null) {
                        nowMarker.setIcon(bitmapDescriptor);
                        nowMarker = null;
                    }
                    nowMarkerId = "";
                }
                lastPosition = mBaiduMap.getMapStatus();
                clusterManager.onMapStatusChange(mapStatus);
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
            }
        });
    }

    /**
     * 初始化抽屉式布局
     */
    private void initDrawerLayout() {
        mSatalliteMapIv = (ImageView) getView(R.id.satallite_map_iv);
        mSatalliteMapIv.setOnClickListener(this);
        mTwoDMapIv = (ImageView) getView(R.id.two_d_map_iv);
        mTwoDMapIv.setOnClickListener(this);
        mThreeDMapIv = (ImageView) getView(R.id.three_d_map_iv);
        mThreeDMapIv.setOnClickListener(this);
        mHeatSw = (SwitchCompat) getView(R.id.heat_sw);
        mTrafficSv = (SwitchCompat) getView(R.id.traffic_sv);
        mDrawerlayout = (DrawerLayout) getView(R.id.drawerlayout);
        mDrawerRightLaytoutRl = getView(R.id.drawer_right_layout_rl);
        //关闭侧滑功能
        mDrawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //热力图监听
        mHeatSw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (clusterItemList.size() > 0) {
                    heatMapItems.clear();
                    for (MapClusterItem item : clusterItemList) {
                        heatMapItems.add(item.getLatLng());
                    }
                    mHeatMap = new HeatMap.Builder().data(heatMapItems).build();
                    mBaiduMap.addHeatMap(mHeatMap);
                } else {
                    ToastUtils.toast(mContext, "没有选择查看的内容");
                    mHeatSw.setChecked(false);
                }
            } else {
                if (mHeatMap != null) {
                    mHeatMap.removeHeatMap();
                    mHeatSw.setChecked(false);
                }

            }
        });
        //路况图监听
        mTrafficSv.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mBaiduMap.setTrafficEnabled(true);
            } else {
                mBaiduMap.setTrafficEnabled(false);
            }

        });
    }

    /**
     * 初始化菜单适配器
     */
    private void initMenuAdapter() {
        menuAdapter = new HomePageMenuAdapter(R.layout.home_page_menu_item);
        getBaseActivity().initRecyclerview(mHomePageRightMenuRv, menuAdapter, LinearLayoutManager.VERTICAL);
        menuAdapter.setNewData(mPresenter.getMenus());
        menuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                menuImageIv = (ImageView) adapter.getViewByPosition(mHomePageRightMenuRv, position,
                        R.id.home_page_menu_iv);
                nowMarkerId = "";
                nowMarker = null;
                HomePageMenuBean menuBean = (HomePageMenuBean) adapter.getData().get(position);
                switch (menuBean.getMenuId()) {
                    case HomePageContract.MENUE_MAP_TYPE:
                        if (mDrawerlayout.isDrawerVisible(mDrawerRightLaytoutRl)) {
                            mDrawerlayout.closeDrawers();
                        } else {
                            mDrawerlayout.openDrawer(mDrawerRightLaytoutRl);

                        }
                        break;
                    case HomePageContract.MENUE_WEATHER:
                        if (!StringTools.isStringValueOk(province)) {
                            ToastUtils.warning(mContext, "定位未获取");
                        } else {
                            startActivity(new Intent(mContext, WeatherActivity.class)
                                    .putExtra("province", province)
                                    .putExtra("city", city)
                                    .putExtra("area", area == null ? "" : area));
                        }
                        break;
                    case HomePageContract.MENUE_CAMERA:
                        clearTheMap();
                        //                        mPresenter.getCameras(MapContract.GET_CAMERAS);
                        mPresenter.getAllStreamCameras(mPresenter.getPublishMultipartBody().build(),
                                HomePageContract.GET_STREAM_CAMERAS);
                        break;
                    case HomePageContract.MENUE_CARE_TAKER:

                        //托养分布
                        mPresenter.getAllYears(SearchContract.YEARS);
                        break;
                    case HomePageContract.MENUE_SERVICE_PEOPLE:
                        clearTheMap();
                        //服务人员
                        mPresenter.getServicePeoplesPosition(mPresenter.getPublishMultipartBody().build(),
                                HomePageContract.SERVICE_PEOPLES_POSITIONS);
                        break;
                    case HomePageContract.MENUE_HEALTH_ORGNAIZE:
                        clearTheMap();

                        //康复机构
                        mPresenter.getHealthOrganizePosition(mPresenter.getPublishMultipartBody().build(),
                                HomePageContract.HEATH_ORGANIZE_POSITIONS);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onResume() {
        mBmapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        releaseBottomListDialog();
        mBmapView.onPause();
        super.onPause();
    }


    @Override
    public void onDestroy() {
        if (popupWindow != null) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
        }
        mBmapView.onDestroy();
        mBmapView = null;
        super.onDestroy();
    }

    @Override
    protected void initData() {
        startUploadLocationService();
    }

    /**
     * 开启上传位置的服务
     */
    private void startUploadLocationService() {
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", String.valueOf(UserInfoManager.getUserId()));
        params.put("account", UserInfoManager.getUserAccount());
        params.put("token", UserInfoManager.getUserToken());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mContext.startForegroundService(new Intent(mContext, LocateAndUpload.class)
                    .putExtra("historyApiUrl", AppHttpPath.UPLOAD_LOCATION)
                    .putExtra("params", params));
        } else {
            mContext.startService(new Intent(mContext, LocateAndUpload.class)
                    .putExtra("historyApiUrl", AppHttpPath.UPLOAD_LOCATION)
                    .putExtra("params", params));
        }
        LocateAndUpload.setCallback(this);
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case HomePageContract.CARE_RECORD_POSITIONS:
                CareRecordPositionBean careRecordPositionBean = (CareRecordPositionBean) o;
                if (careRecordPositionBean != null) {
                    CareRecordPositionBean.DataBean dataBean = careRecordPositionBean.getData();
                    List<CareRecordPositionBean.DataBean.DatasBean> careRecords = dataBean.getDatas();
                    if (careRecords != null) {
                        if (careRecords.size() > 0) {
                            for (CareRecordPositionBean.DataBean.DatasBean careRecord : careRecords) {
                                String year = StringTools.isStringValueOk(careRecord.getYear()) ?
                                        careRecord.getYear() : "2019";
                                careRecord.setYear(year);
                                MapClusterItem mCItem = new MapClusterItem(
                                        new LatLng(careRecord.getLatitude(), careRecord.getLongitude()), careRecord);
                                clusterItemList.add(mCItem);
                            }
                            clusterManager.addItems(clusterItemList);
                            clusterManager.cluster();
                        } else {
                            ToastUtils.toast(mContext, "暂无数据");
                        }

                    }

                }
                break;
            case HomePageContract.GET_STREAM_CAMERAS:

                StreamCameraBean streamCameraBean = (StreamCameraBean) o;
                if (streamCameraBean != null) {
                    List<StreamCameraBean.DataBean> datas = streamCameraBean.getData();
                    if (datas != null) {
                        if (datas.size() > 0) {
                            for (StreamCameraBean.DataBean camera : datas) {
                                MapClusterItem mCItem = new MapClusterItem(
                                        new LatLng(camera.getLatitude(), camera.getLongitude()), camera);
                                clusterItemList.add(mCItem);
                            }
                            clusterManager.addItems(clusterItemList);
                            clusterManager.cluster();
                        } else {
                            ToastUtils.toast(mContext, "暂无数据");
                        }

                    }

                    //                    dateType = 0;
                }
                break;
            case PlayContract.GET_URL_PATH:
                OpenLiveBean openLiveBean = (OpenLiveBean) o;
                int errorCode = openLiveBean.getErrcode();
                if (errorCode < 0) {
                    ToastUtils.toast(mContext, "设备离线，无数据");
                    return;
                }
                String playUrl = openLiveBean.getVideourl();
                if (StringTools.isStringValueOk(playUrl)) {
                    if (playUrl.contains("//")) {
                        playUrl = playUrl.substring(playUrl.indexOf("//")+2);
                        playUrl = playUrl.substring(playUrl.indexOf("/"));
                        playUrl =AppHttpPath.BASE_CAMERA_DNS+playUrl;
                    }
                }
                String strsessionid = openLiveBean.getStrsessionid();
                startActivity(new Intent(mContext.getApplicationContext(), PlayerLiveActivity.class)
                        .putExtra(PlayerLiveActivity.STREAM_CAMERA_ID, currentStreamCamera.getId())
                        .putExtra(PlayerLiveActivity.STREAM_CAMERA_URL, playUrl)
                        .putExtra(PlayerLiveActivity.STREAM_CAMERA_THUM_URL, currentStreamCamera.getEzopen())
                        .putExtra(PlayerLiveActivity.STREAM_CAMERA_SESSION_ID, strsessionid)
                        .putExtra(PlayerLiveActivity.STREAM_CAMERA_NUM, currentStreamCamera.getNumber()));
                break;
            case HomePageContract.GET_STREAM_CAMERAS_FROM_VCR:

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
                    }


                }

                break;
            case HomePageContract.SERVICE_PEOPLES_POSITIONS:

                ServicePeoplePositionBean peoplePositionBean = (ServicePeoplePositionBean) o;
                if (peoplePositionBean != null) {
                    List<ServicePeoplePositionBean.DataBean> peoples = peoplePositionBean.getData();
                    if (peoples != null) {
                        if (peoples.size() > 0) {
                            for (ServicePeoplePositionBean.DataBean people : peoples) {
                                MapClusterItem mCItem = new MapClusterItem(
                                        new LatLng(people.getLatitude(), people.getLongitude()), people);
                                clusterItemList.add(mCItem);
                            }
                            clusterManager.addItems(clusterItemList);
                            clusterManager.cluster();
                        } else {
                            ToastUtils.toast(mContext, "暂无数据");
                        }

                    }

                }
                //服务人员
                break;
            case HomePageContract.HEATH_ORGANIZE_POSITIONS:
                //康复机构
                HealthOrgPositionBean healthOrgPositionBean = (HealthOrgPositionBean) o;
                if (healthOrgPositionBean != null) {
                    List<HealthOrgPositionBean.DataBean> healthPositions = healthOrgPositionBean.getData();
                    if (healthPositions != null) {
                        if (healthPositions.size() > 0) {
                            for (HealthOrgPositionBean.DataBean healthPosition : healthPositions) {
                                MapClusterItem mCItem = new MapClusterItem(
                                        new LatLng(healthPosition.getLatitude(), healthPosition.getLongitude()),
                                        healthPosition);
                                clusterItemList.add(mCItem);
                            }
                            clusterManager.addItems(clusterItemList);
                            clusterManager.cluster();
                        } else {
                            ToastUtils.toast(mContext, "暂无数据");
                        }

                    }

                }


                break;
            case SearchContract.YEARS:

                YearsBean yearsBean = (YearsBean) o;
                if (yearsBean != null) {
                    List<YearsBean.DataBean> years = yearsBean.getData();
                    List<String> yearArrays = new ArrayList<>();
                    if (years != null && years.size() > 0) {
                        for (YearsBean.DataBean year : years) {
                            if (!"全部".equals(year.getYear())) {
                                yearArrays.add(year.getYear());
                            }
                        }
                        showPopCarePositions(menuImageIv, yearArrays);
                    } else {
                        ToastUtils.toast(mContext, "一条都没有");
                    }
                }

                break;
            default:
                break;
        }
    }

    @Override
    public void onError(String tag, Object o) {
        ToastUtils.error(mContext, (String) o);
    }

    @Override
    public void onLocationChange(BDLocation bdLocation) {
        //  161是网络定位的结果   61是GPS定位成功的结果
        if (bdLocation == null) {
            return;
        }
        lat = bdLocation.getLatitude();
        lng = bdLocation.getLongitude();
        city = bdLocation.getCity();
        area = bdLocation.getDistrict();
        province = bdLocation.getProvince();
        if (isFisrt) {
            MapUtil.mapMoveTo(mBaiduMap, new LatLng(lat,
                    lng));
            isFisrt = false;
        }
        MyLocationData data = new MyLocationData.Builder()//构建定位信息包
                .latitude(lat)//经度
                .longitude(lng)//纬度
                .build();//创建
        mBaiduMap.setMyLocationData(data);

    }

    /**
     * 托养分布
     */
    public void showPopCarePositions(View view, List<String> years) {

        View viewPop = LayoutInflater.from(getActivity()).inflate(R.layout.pop_select_date, null);
        popupWindow = new PopupWindow(viewPop, DisplayUtil.dp2px(mContext, 70), DisplayUtil.dp2px(mContext, 80),
                false);
        popupWindow.setOutsideTouchable(true);
        RecyclerView selectYearRv = viewPop.findViewById(R.id.select_year_rv);
        SingleTextAdapter singleTextAdapter = new SingleTextAdapter(R.layout.single_text_layout);
        getBaseActivity().initRecyclerview(selectYearRv, singleTextAdapter, LinearLayoutManager.VERTICAL);
        singleTextAdapter.setNewData(years);
        getBaseActivity().addDivider(true, selectYearRv, false, false);
        singleTextAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String text = (String) adapter.getData().get(position);
                clearTheMap();
                if ("全部".equals(text)) {
                    mPresenter.getCareRecordPosition(getBaseAppActivity().getBaseBuilder().build(),
                            HomePageContract.CARE_RECORD_POSITIONS);
                    popupWindow.dismiss();
                } else {
                    popupWindow.dismiss();
                    mPresenter.getCareRecordPosition(getBaseAppActivity().getBaseBuilder().add("year",
                            String.valueOf(text)).build(),
                            HomePageContract.CARE_RECORD_POSITIONS);
                }
            }
        });
        popupWindow.showAtLocation(view, Gravity.RIGHT, view.getWidth() + DisplayUtil.dp2px(mContext, 15),
                -view.getHeight() * 2);
    }

    /**
     * 清理地图标点
     */
    private void clearTheMap() {
        mBaiduMap.clear();
        clusterItemList.clear();
        clusterManager.clearItems();
        mBmapView.removeView(infowindow);
    }

    /**
     * 点聚合展开
     *
     * @param cluster
     * @return
     */
    @Override
    public boolean onClusterClick(Cluster<MapClusterItem> cluster) {
        List<MapClusterItem> items = new ArrayList<MapClusterItem>(cluster.getItems());
        if (mapBottomDialog == null) {
            mapBottomDialog = new BottomSheetDialog(mContext);
            View view = LayoutInflater.from(mContext).inflate(R.layout.bottom_list_layout, null);
            mapBottomDialog.setContentView(view);
            RecyclerView bottomRv = view.findViewById(R.id.map_bottom_list_rv);
            clusterClickAdapter = new ClusterClickAdapter(R.layout.care_item_layout);
            getBaseActivity().initRecyclerview(bottomRv, clusterClickAdapter, LinearLayoutManager.VERTICAL);
            clusterClickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    MapClusterItem item = (MapClusterItem) adapter.getData().get(position);
                    if (infowindow != null) {
                        mBmapView.removeView(infowindow);
                    }
                    if (nowMarker != null) {
                        nowMarker.setIcon(bitmapDescriptor);
                    }
                    nowMarker = null;
                    mBaiduMap.hideInfoWindow();
                    clickItemType = 1;
                    onClusterItemClick(item);
                    releaseBottomListDialog();
                }
            });
            clusterClickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    MapClusterItem item = (MapClusterItem) adapter.getData().get(position);
                    switch (item.getType()) {
                        case MapClusterItem.CARE_POSITION:
                            getBaseAppActivity().navigationLogic(new LatLng(item.carePosition.getLatitude(),
                                    item.carePosition.getLongitude()), item.carePosition.getPlace());
                            break;
                        case MapClusterItem.HEALTH_POSITION:
                            getBaseAppActivity().navigationLogic(new LatLng(item.healthPosition.getLatitude(),
                                    item.healthPosition.getLongitude()), item.healthPosition.getAddress());
                            break;
                        default:
                            break;
                    }
                }
            });
        }
        clusterClickAdapter.setNewData(items);
        mapBottomDialog.show();
        return false;
    }


    /**
     * 地图点点击监听
     *
     * @param item
     * @return
     */
    @Override
    public boolean onClusterItemClick(MapClusterItem item) {
        if (infowindow != null) {
            mBmapView.removeView(infowindow);
        }
        switch (item.getType()) {
            case MapClusterItem.STREAM_CAMERA:
                if (0 == item.streamCamera.getFlag()) {
                    bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap
                            .steam_cameras_tip);
                    //请求硬盘录像机下的摄像头
                    mPresenter.getAllStreamCamerasFromVCR(mPresenter.getPublishMultipartBody()
                                    .addFormDataPart(
                                            "dvrId", String.valueOf(item.streamCamera.getId())).build(),
                            HomePageContract.GET_STREAM_CAMERAS_FROM_VCR);
                } else {
                    bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.camera_tip);
                    updateMarkerIcon(item.streamCamera.getEzopen());
                }
                if (clickItemType == 1 || nowMarkerId.equals(String.valueOf(item.streamCamera.getNumber
                        ()))) {
                    if (1 == item.streamCamera.getFlag()) {
                        currentStreamCamera = item.streamCamera;
                        //打开流数据
                        mPresenter.openStream(item.streamCamera.getNumber(), "1", "rtmp",
                                PlayContract.GET_URL_PATH);

                    }
                }
                nowMarkerId = String.valueOf(item.streamCamera.getNumber());
                break;
            case MapClusterItem.CARE_POSITION:
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.care_taker_icon);
                //托养分布
                CareRecordPositionBean.DataBean.DatasBean carePosition = item.carePosition;
                String year = carePosition.getYear();
                if ("2019".equals(year)) {
                    Intent mintent = new Intent(mContext, CareInfoActivity.class);
                    mintent.putExtra(CareInfoActivity.CARE_ID, carePosition.getId());
                    startActivity(mintent);
                } else {
                    startActivity(new Intent(mContext, CareTakerInfoActivity.class).putExtra(CareTakerInfoActivity.CARE_TAKER_ID, carePosition.getId()));
                }
                break;

            case MapClusterItem.PEOPLE:
                //服务人员
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.people_tip);
                updateMarkerIcon(UrlFormatUtil.formatPicUrl(item.peoplePosition.getHead()));
                if (clickItemType == 1 || nowMarkerId.equals(String.valueOf(item.peoplePosition.getId()))) {
                    onServicePeopleItemClick(item, mBaiduMap);
                }
                nowMarkerId = String.valueOf(item.peoplePosition.getId());
                break;
            case MapClusterItem.HEALTH_POSITION:
                //康复机构
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.health_organize_icon);
                updateMarkerIcon(UrlFormatUtil.formatPicUrl(item.healthPosition.getImg()));
                HealthOrgPositionBean.DataBean healthPosition = item.healthPosition;
                startActivity(new Intent(mContext, HealthOrganizeActivity.class).putExtra(HealthOrganizeActivity.ID,
                        healthPosition.getId()));
                break;
        }
        return false;
    }

    /**
     * 更新marker图标
     *
     * @param path
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


    /**
     * 释放dialog
     */
    private void releaseBottomListDialog() {
        if (mapBottomDialog != null) {
            mapBottomDialog.dismiss();
            mapBottomDialog = null;
        }
    }

    /**
     * 服务人员item点击后 弹出infowidow
     *
     * @param map
     */
    private void onServicePeopleItemClick(MapClusterItem item, BaiduMap map) {
        ServicePeoplePositionBean.DataBean people = item.peoplePosition;
        LatLng peopleLocation = new LatLng(people.getLatitude(), people.getLongitude());
        MapUtil.mapMoveTo(map, peopleLocation);
        infowindow = View.inflate(mContext, R.layout.infowindow_people, null);//将一个布局文件转换成一个view对象
        RecyclerView peopleRv = infowindow.findViewById(R.id.infowindow_people_rv);
        ServicePeopleAdapter adapter = new ServicePeopleAdapter(R.layout.infowindow_people_item);
        getBaseActivity().initRecyclerview(peopleRv, adapter, LinearLayoutManager.VERTICAL);
        adapter.setNewData(getInfoWindowAdapterData(item));
        ImageLoadUtil.loadCirImgNoCrash(mContext.getApplicationContext(),
                UrlFormatUtil.formatPicUrl(people.getHead()),
                (ImageView) infowindow.findViewById(R.id.infowindow_people_head_icon_iv),
                R.mipmap.default_head_icon, R.mipmap.default_head_icon
        );
        MapViewLayoutParams params2 = new MapViewLayoutParams.Builder()
                .layoutMode(MapViewLayoutParams.ELayoutMode.mapMode)
                .position(peopleLocation)
                .width(MapViewLayoutParams.WRAP_CONTENT)
                .height(MapViewLayoutParams.WRAP_CONTENT)
                .yOffset(-item.getBd().getBitmap().getHeight() * clickItemType)
                .build();
        mBmapView.addView(infowindow, params2);
    }

    /**
     * 获取数据
     *
     * @return
     */
    private List<TextListBean> getInfoWindowAdapterData(MapClusterItem item) {
        ServicePeoplePositionBean.DataBean people = item.peoplePosition;
        List<TextListBean> beans = new ArrayList<>();
        beans.add(new TextListBean("姓名：", people.getName()));
        beans.add(new TextListBean("街道：", people.getStreet()));
        beans.add(new TextListBean("电话：", people.getPhone()));

        return beans;
    }

}
