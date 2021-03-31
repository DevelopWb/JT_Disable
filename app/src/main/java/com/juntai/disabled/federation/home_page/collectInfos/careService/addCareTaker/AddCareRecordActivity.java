package com.juntai.disabled.federation.home_page.collectInfos.careService.addCareTaker;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.basecomponent.utils.PickerManager;
import com.juntai.disabled.basecomponent.utils.TimeUtils;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.bdmap.act.LocateSelectionActivity;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.base.BaseAppActivity;
import com.juntai.disabled.federation.base.selectPics.BaseSelectPicsActivity;
import com.juntai.disabled.federation.base.selectPics.SelectPhotosFragment;
import com.juntai.disabled.federation.bean.careTaker.CareRecoredSavedBean;
import com.juntai.disabled.federation.bean.careTaker.CareTakerInfoBean;
import com.juntai.disabled.federation.bean.careTaker.CareTakerPicBean;
import com.juntai.disabled.federation.bean.careTaker.ServicePeoplesBean;
import com.juntai.disabled.federation.bean.careTaker.ServiceTypeBean;
import com.juntai.disabled.federation.home_page.collectInfos.careService.CareContract;
import com.juntai.disabled.federation.home_page.collectInfos.careService.CarePresent;
import com.juntai.disabled.federation.home_page.collectInfos.careService.careInfo.CareTakerInfoActivity;
import com.juntai.disabled.federation.utils.HawkProperty;
import com.juntai.disabled.federation.utils.StringTools;
import com.orhanobut.hawk.Hawk;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @aouther tobato
 * @description 描述 添加托养记录
 * @date 2020/7/13 17:17
 */
public class AddCareRecordActivity extends BaseAppActivity<CarePresent> implements CareContract.ICareView,
        View.OnClickListener, SelectPhotosFragment.OnPhotoItemClick {

    /**
     * 地址：
     */
    private TextView mCareTakerAddrTv;
    private TextView mAddLocationAddrTv;
    private RecyclerView mCareTakerRecordPicsRv;
    /**
     * 如：常年在村前花棚看花，来之前电话联系他哥哥。
     */
    private EditText mAddTakerRecordRemarkEt;
    /**
     * -  -  -
     */
    private TextView mServiceStartTimeValueTv;
    /**
     * -  -  -
     */
    private TextView mServiceEndTimeValueTv;
    private String addr;
    private CareTakerPicsAdapter mTakerPicsAdapter;
    private int currentPosition;
    private LinearLayout mServiceControlerLl;
    /**
     * 至少选择两位
     */
    private TextView mServicePeopleValueTv;
    private ImageView mFilterIv;
    private RecyclerView mServicePeopleRv;
    /**
     * 保存草稿
     */
    private TextView mSaveRecordTv;
    /**
     * 提交
     */
    private TextView mCommitRecordTv;
    List<ServicePeoplesBean.DataBean> serviceAreas = new ArrayList<>();
    List<ServicePeoplesBean.DataBean.ServicerVOsVosBean> allServices = new ArrayList<>();
    private List<ServicePeoplesBean.DataBean> peopleBeans;
    private CareServicePeopleAdapter servicePeopleAdapter;
    private TextView mServiceAreaTv;
    List<String> selectedServicePeoples = new ArrayList<>();//已选择的服务人员
    List<String> selectedServicePeopleIds = new ArrayList<>();//已选择的服务人员id
    private CareTakerInfoBean.DataBean careTakerInfoBean;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static String MIN_START_HOUR_MINUTE = "08:00";//最早时间 8点
    private static String MAX_START_HOUR_MINUTE = "16:30";//最晚时间 16点
    private String serviceStartTime;
    /**
     * 开始
     */
    private TextView mStartStopServiceTv;
    /**
     * 服务时长\n2小时2分钟
     */
    private TextView mServiedTimeTv;
    private CareRecoredSavedBean careRecoredSavedBean;

    private static String SERVICE_START_TAG = "开始";//服务开始
    private static String SERVICE_STOP_TAG = "结束";
    private static String SERVICE_STOPED_TAG = "已结束";
    private TextView mServiceTypeTv;


    @Override
    protected CarePresent createPresenter() {
        return new CarePresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_add_care_record;
    }

    @Override
    public void initView() {
        setTitleName("添加服务记录");
        mCareTakerAddrTv = (TextView) findViewById(R.id.care_taker_addr_tv);
        mAddLocationAddrTv = (TextView) findViewById(R.id.add_location_addr_tv);
        mAddLocationAddrTv.setOnClickListener(this);
        mAddLocationAddrTv.setText("   请稍等...正在获取位置信息");
        mAddLocationAddrTv.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        if (addr != null) {
            mAddLocationAddrTv.setText(addr);
            mAddLocationAddrTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray_deeper));
        }
        mCareTakerRecordPicsRv = (RecyclerView) findViewById(R.id.care_taker_record_pics_rv);
        mTakerPicsAdapter = new CareTakerPicsAdapter(R.layout.care_item_pic);
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        mCareTakerRecordPicsRv.setAdapter(mTakerPicsAdapter);
        mCareTakerRecordPicsRv.setLayoutManager(manager);
        mAddTakerRecordRemarkEt = (EditText) findViewById(R.id.add_taker_record_remark_et);
        mServiceStartTimeValueTv = (TextView) findViewById(R.id.service_start_time_value_tv);
        mServiceStartTimeValueTv.setOnClickListener(this);
        mServiceEndTimeValueTv = (TextView) findViewById(R.id.service_end_time_value_tv);
        mServiceEndTimeValueTv.setOnClickListener(this);
        mServiceTypeTv = (TextView) findViewById(R.id.service_type_value_tv);
        mServiceTypeTv.setOnClickListener(this);
        mTakerPicsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                CareTakerPicBean dataBean =
                        (CareTakerPicBean) adapter.getData().get(position);
                currentPosition = position;
                switch (view.getId()) {
                    case R.id.select_pic_icon_iv:
                        //选择图片
                        if (isInnerAccount()) {
                            choseImage(0, AddCareRecordActivity.this, 1);
                        } else {
                            choseImage(1, AddCareRecordActivity.this, 1);
                        }

                        break;
                    case R.id.delete_pushed_news_iv:
                        //删除图片
                        dataBean.setPicPath("");
                        adapter.notifyItemChanged(position);
                        break;
                    default:
                        break;
                }
            }
        });
        mServiceControlerLl = (LinearLayout) findViewById(R.id.service_controler_ll);
        mServiceControlerLl.setOnClickListener(this);
        mServicePeopleValueTv = (TextView) findViewById(R.id.service_people_value_tv);
        mFilterIv = (ImageView) findViewById(R.id.filter_iv);
        mFilterIv.setOnClickListener(this);
        mServicePeopleRv = (RecyclerView) findViewById(R.id.service_people_rv);
        servicePeopleAdapter = new CareServicePeopleAdapter(R.layout.service_people_item);
        GridLayoutManager servicePeopleManager = new GridLayoutManager(mContext, 4);
        mServicePeopleRv.setAdapter(servicePeopleAdapter);
        mServicePeopleRv.setLayoutManager(servicePeopleManager);
        mSaveRecordTv = (TextView) findViewById(R.id.save_record_tv);
        mServiceAreaTv = (TextView) findViewById(R.id.service_people_area_tv);
        mServiceAreaTv.setOnClickListener(this);
        mSaveRecordTv.setOnClickListener(this);
        mCommitRecordTv = (TextView) findViewById(R.id.commit_record_tv);
        mCommitRecordTv.setOnClickListener(this);
        servicePeopleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ServicePeoplesBean.DataBean.ServicerVOsVosBean dataBean =
                        (ServicePeoplesBean.DataBean.ServicerVOsVosBean) adapter.getData().get(position);
                if (dataBean.isSelected()) {
                    dataBean.setSelected(false);
                    if (selectedServicePeoples.contains(dataBean.getName())) {
                        selectedServicePeoples.remove(dataBean.getName());
                        selectedServicePeopleIds.remove(String.valueOf(dataBean.getId()));
                    }
                } else {
                    dataBean.setSelected(true);
                    if (!selectedServicePeoples.contains(dataBean.getName())) {
                        selectedServicePeoples.add(dataBean.getName());
                        selectedServicePeopleIds.add(String.valueOf(dataBean.getId()));
                    }
                }
                notifyServiceNum();
                adapter.notifyItemChanged(position);
            }
        });
        mStartStopServiceTv = (TextView) findViewById(R.id.start_stop_service_tv);
        mServiedTimeTv = (TextView) findViewById(R.id.servied_time_tv);
    }

    /**
     * 更新服务人员数据
     */
    private void notifyServiceNum() {
        String people = getListToString(selectedServicePeoples);
        mServicePeopleValueTv.setText(people);
        careRecoredSavedBean.setServicer(people);
        careRecoredSavedBean.setServiceIds(getListToString(selectedServicePeopleIds));


    }


    @Override
    public void initData() {

        careTakerInfoBean = getIntent().getParcelableExtra(CareTakerInfoActivity.CARE_TAKER_INFO);
        initCareTakerAddr();
        careRecoredSavedBean = Hawk.get(HawkProperty.getCareServiceRecordKey(careTakerInfoBean));
        if (careRecoredSavedBean == null) {
            careRecoredSavedBean = new CareRecoredSavedBean();
            mTakerPicsAdapter.setNewData(mPresenter.getRecordPics());
            careRecoredSavedBean.setPicBeans(mPresenter.getRecordPics());
        } else {
            if (StringTools.isStringValueOk(careRecoredSavedBean.getStartTime())) {
                serviceStartTime = careRecoredSavedBean.getStartTime();
                mServiceStartTimeValueTv.setText(serviceStartTime);
                if (StringTools.isStringValueOk(careRecoredSavedBean.getEndTime())) {
                    mStartStopServiceTv.setText(SERVICE_STOPED_TAG);
                    mServiceEndTimeValueTv.setText(careRecoredSavedBean.getEndTime());
                    mStartStopServiceTv.setBackgroundResource(R.drawable.sp_half_circle_green);
                } else {
                    mPresenter.startTime(CareContract.START_TIME);
                    mStartStopServiceTv.setText(SERVICE_STOP_TAG);
                    mStartStopServiceTv.setBackgroundResource(R.drawable.sp_half_circle_red);
                }
            }
            if (StringTools.isStringValueOk(careRecoredSavedBean.getServiceLength())) {
                mServiedTimeTv.setText("服务时长\n" + careRecoredSavedBean.getServiceLength());
            } else {
                mServiedTimeTv.setText("服务时长\n- - -");
            }
            if (careRecoredSavedBean.getTypeBean() != null) {
                mServiceTypeTv.setText(careRecoredSavedBean.getTypeBean().getName());
            }

            //填充图片适配器
            List<CareTakerPicBean> picBeans = careRecoredSavedBean.getPicBeans();
            mTakerPicsAdapter.setNewData(picBeans);
            //填充服务人员适配器
            String servers = careRecoredSavedBean.getServicer();
            if (StringTools.isStringValueOk(servers)) {
                selectedServicePeoples.clear();
                mServicePeopleValueTv.setText(servers);
                if (servers.contains(",")) {
                    List<String> arrays = Arrays.asList(servers.split(","));
                    selectedServicePeoples.addAll(arrays);
                } else {
                    selectedServicePeoples.add(servers);
                }
            }
            //填充服务人员id
            String serverIds = careRecoredSavedBean.getServiceIds();
            if (StringTools.isStringValueOk(serverIds)) {
                selectedServicePeopleIds.clear();
                if (servers.contains(",")) {
                    List<String> arrays = Arrays.asList(serverIds.split(","));
                    selectedServicePeopleIds.addAll(arrays);
                } else {
                    selectedServicePeopleIds.add(servers);
                }
            }
            //填写备注信息
            if (StringTools.isStringValueOk(careRecoredSavedBean.getDescribe())) {
                mAddTakerRecordRemarkEt.setText(careRecoredSavedBean.getDescribe());
            }
        }
        mPresenter.getServicePeople(getBaseBuilder().build(), CareContract.SERVICE_PEOPLE);
    }

    /**
     * 托养人地址简介
     */
    private void initCareTakerAddr() {
        String street = careTakerInfoBean.getStreetAddress();
        String village = careTakerInfoBean.getCommunityAddress();
        if (StringTools.isStringValueOk(street)) {
            if (street.length() > 5) {
                street = street.substring(0, 5);
            }
        }
        if (StringTools.isStringValueOk(village)) {
            if (village.length() > 5) {
                village = village.substring(0, 5);
            }
        }

        String title = String.format("%s%s%s%s%s", street, " ", village, " ",
                careTakerInfoBean.getName());
        mCareTakerAddrTv.setText(title);
    }

    @Override
    public void onLocationReceived(BDLocation bdLocation) {
        addr = String.format("%s%s%s%s%s%s", bdLocation.getProvince(), bdLocation.getCity(),
                bdLocation.getDistrict(), bdLocation.getTown(), bdLocation.getStreet(),
                bdLocation.getStreetNumber());
        if (mAddLocationAddrTv != null) {
            mAddLocationAddrTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray_deeper));
            mAddLocationAddrTv.setText(addr);
        }
    }

    @Override
    public boolean requestLocation() {
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LocateSelectionActivity.SELECT_ADDR && resultCode == RESULT_OK) {
            //地址选择
            lat = data.getDoubleExtra("lat", 0.0);
            lng = data.getDoubleExtra("lng", 0.0);
            addr = data.getStringExtra("address");
            mAddLocationAddrTv.setText(addr);
            mAddLocationAddrTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray_deeper));
        }

    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case CareContract.SERVICE_PEOPLE:
                serviceAreas.clear();
                allServices.clear();
                if (peopleBeans != null) {
                    peopleBeans.clear();
                }
                ServicePeoplesBean servicePeoplesBean = (ServicePeoplesBean) o;
                if (servicePeoplesBean != null) {
                    peopleBeans = servicePeoplesBean.getData();
                    if (peopleBeans != null) {
                        for (int i = 0; i < peopleBeans.size(); i++) {
                            ServicePeoplesBean.DataBean array = peopleBeans.get(i);
                            serviceAreas.add(array);
                            if (0 == i) {
//                                mServiceAreaTv.setText(array.getStreet());
                                List<ServicePeoplesBean.DataBean.ServicerVOsVosBean> peoples =
                                        array.getServicerVOsVos();
                                for (ServicePeoplesBean.DataBean.ServicerVOsVosBean people : peoples) {
                                    if (selectedServicePeoples.contains(people.getName())) {
                                        people.setSelected(true);
                                    }
                                }
                                allServices.addAll(peoples);
                            }
                        }
//                        servicePeopleAdapter.setNewData(allServices);

                    }

                }

                break;
            case CareContract.START_TIME:
                //计时中
                String currentTime = sdf.format(new Date());
                String servicedTime = TimeUtils.getTimeExpend(sdf, serviceStartTime, currentTime);
                if (StringTools.isStringValueOk(servicedTime)) {
                    mServiedTimeTv.setText("服务时长\n" + servicedTime);

                }

                break;

            case CareContract.COMMIT_RECORED:
                //提交记录
                ToastUtils.toast(mContext, "已成功提交托养记录");
                Hawk.delete(HawkProperty.getCareServiceRecordKey(careTakerInfoBean));
                setResult(CareTakerInfoActivity.CARE_RECORE);
                finish();
                break;
            case CareContract.GET_SERVICE_TYPE:
                ServiceTypeBean serviceTypeBean = (ServiceTypeBean) o;

                if (serviceTypeBean != null) {
                    List<ServiceTypeBean.DataBean> dataBeans = serviceTypeBean.getData();
                    if (dataBeans != null) {
                        //获取服务类型
                        PickerManager.getInstance().showOptionPicker(mContext, dataBeans, new PickerManager.OnOptionPickerSelectedListener() {
                            @Override
                            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                                ServiceTypeBean.DataBean serviceType = dataBeans.get(options1);
                                careRecoredSavedBean.setTypeBean(serviceType);
                                mServiceTypeTv.setText(serviceType.getName());
                            }
                        });
                    }
                }

                break;
            default:
                break;


        }
    }

    /**
     * 检测照片选择的状态
     *
     * @return
     */
    private boolean checkPicSelectedStatus() {
        List<CareTakerPicBean> arrays = mTakerPicsAdapter.getData();
        boolean isOk = true;
        for (int i = 0; i < arrays.size(); i++) {
            if (i < 3) {
                CareTakerPicBean bean = arrays.get(i);
                String name = bean.getTitle();
                String path = bean.getPicPath();
                if ("".equals(path)) {
                    ToastUtils.toast(mContext, String.format("%s%s%s", "请选择", name, "的图片"));
                    isOk = false;
                    break;
                }
            } else {
                isOk = true;
                break;
            }

        }
        return isOk;
    }

    @Override
    protected void selectedPicsAndEmpressed(List<String> icons) {
        if (icons.size() > 0) {
            String path = icons.get(0);
            List<CareTakerPicBean> arrays = mTakerPicsAdapter.getData();
            for (int i = 0; i < arrays.size(); i++) {
                CareTakerPicBean bean = arrays.get(i);
                if (currentPosition == i) {
                    bean.setPicPath(path);
                    mTakerPicsAdapter.notifyItemChanged(currentPosition);
                    break;
                }
            }
        }
    }

    @Override
    public void onVedioPicClick(BaseQuickAdapter adapter, int position) {

    }

    @Override
    public void onPicClick(BaseQuickAdapter adapter, int position) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_location_addr_tv:
                //选择地点
                if (isInnerAccount()) {
                    Intent intent = new Intent(mContext, LocateSelectionActivity.class);
                    startActivityForResult(intent, LocateSelectionActivity.SELECT_ADDR);
                }
                break;
            default:
                break;
            case R.id.service_controler_ll:
                //开始结束服务

                //首先判断是否是测试账号，如果是测试账号 直接开始服务
                //其次 判断登记的位置和当前位置相差距离 大于100米无法开始服务
                if (isInnerAccount()) {
                    startOrEndTimeOfService();
                } else {
                    if (distanceIsOk()) {
                        startOrEndTimeOfService();
                    } else {
                        ToastUtils.toast(mContext, "不在服务区域，不能开始服务");
                    }
                }

                break;
            case R.id.filter_iv:
                //过滤服务人员
                PickerManager.getInstance().showOptionPicker(mContext, serviceAreas,
                        new PickerManager.OnOptionPickerSelectedListener() {
                            @Override
                            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                                ServicePeoplesBean.DataBean dataBean = serviceAreas.get(options1);
                                List<ServicePeoplesBean.DataBean.ServicerVOsVosBean> peoples =
                                        dataBean.getServicerVOsVos();
                                for (ServicePeoplesBean.DataBean.ServicerVOsVosBean people : peoples) {
                                    if (selectedServicePeoples.contains(people.getName())) {
                                        people.setSelected(true);
                                    } else {
                                        people.setSelected(false);
                                    }
                                }
                                servicePeopleAdapter.setNewData(peoples);
                                mServiceAreaTv.setText(dataBean.getStreet());
                                notifyServiceNum();


                            }
                        });
                break;

            case R.id.service_people_area_tv:
                mFilterIv.performClick();
                break;
            case R.id.save_record_tv:
                //保存开始时间到本地
                careRecoredSavedBean.setPicBeans(mTakerPicsAdapter.getData());
                if (StringTools.isStringValueOk(getTextViewValue(mAddTakerRecordRemarkEt))) {
                    careRecoredSavedBean.setDescribe(getTextViewValue(mAddTakerRecordRemarkEt));
                }
                Hawk.put(HawkProperty.getCareServiceRecordKey(careTakerInfoBean), careRecoredSavedBean);
                ToastUtils.toast(mContext, "已保存");
                break;
            case R.id.commit_record_tv:
                if (selectedServicePeoples.size() < 2) {
                    ToastUtils.toast(mContext, "服务人员最少2位，请选择相应服务人员");
                    return;
                }
                //提交记录
                if (!distanceIsOk()) {
                    return;
                }
                if (!checkPicSelectedStatus()) {
                    return;
                }
                if (!SERVICE_STOPED_TAG.equals(getTextViewValue(mStartStopServiceTv))) {
                    ToastUtils.toast(mContext, "请先结束服务再提交");
                    return;
                }
                if (careRecoredSavedBean.getTypeBean() == null) {
                    ToastUtils.toast(mContext, "请选择服务类型");
                    return;
                }
                MultipartBody.Builder builder = getPublishMultipartBody();
                String serverPeopleIds = getListToString(selectedServicePeopleIds);
                builder.addFormDataPart("caregiversId", String.valueOf(careTakerInfoBean.getId()))
                        .addFormDataPart("place", addr)
                        .addFormDataPart("longitude", String.valueOf(lng))
                        .addFormDataPart("latitude", String.valueOf(lat))
                        .addFormDataPart("startTime", careRecoredSavedBean.getStartTime())
                        .addFormDataPart("endTime", careRecoredSavedBean.getEndTime())
                        .addFormDataPart("serviceLength", careRecoredSavedBean.getServiceLength())
                        .addFormDataPart("servicer", getTextViewValue(mServicePeopleValueTv))
                        .addFormDataPart("cate", String.valueOf(careRecoredSavedBean.getTypeBean().getId()))
                        .addFormDataPart("servicerIds", serverPeopleIds)
                        .addFormDataPart("uploadingYear", careTakerInfoBean.getYear());
                if (StringTools.isStringValueOk(getTextViewValue(mAddTakerRecordRemarkEt))) {
                    builder.addFormDataPart("describe", getTextViewValue(mAddTakerRecordRemarkEt));
                }
                List<CareTakerPicBean> arrays = mTakerPicsAdapter.getData();
                for (int i = 0; i < arrays.size(); i++) {
                    CareTakerPicBean bean = arrays.get(i);
                    String path = bean.getPicPath();
                    if (StringTools.isStringValueOk(path)) {
                        builder.addFormDataPart("file", new File(path).getName(), RequestBody.create(MediaType.parse(
                                "file")
                                , new File(path)));
                    }

                }

                mPresenter.commitCareRecord(builder.build(), CareContract.COMMIT_RECORED);
                break;
            case R.id.service_start_time_value_tv:
                //首先看是否选中服务人员，因为需要根据人员信息获取对应的所有托养记录里面的服务时间段   解决网段重叠的问题

                //开始服务的时间
                if (isInnerAccount()) {
                    PickerManager.getInstance().showTimePickerView(mContext, PickerManager.getInstance().getTimeType(
                            "minute"), "", new PickerManager.OnTimePickerTimeSelectedListener() {
                        @Override
                        public void onTimeSelect(Date date, View v) {
                            String startTime = sdf.format(date);
                            //其次看开始时间是否是在8点之后 16点半之前
                            if (!TimeUtils.isOKTimeToStartService(sdf, startTime, MIN_START_HOUR_MINUTE,
                                    MAX_START_HOUR_MINUTE)) {
                                ToastUtils.toast(mContext, "服务时间段：早晨8点至下午16点半");
                                return;
                            }
                            serviceStartTime = startTime;
                            mServiceStartTimeValueTv.setText(serviceStartTime);
                            mStartStopServiceTv.setText(SERVICE_STOP_TAG);
                            mStartStopServiceTv.setBackgroundResource(R.drawable.sp_half_circle_red);
                            mPresenter.startTime(CareContract.START_TIME);
                            //将开始时间赋值到bean中
                            careRecoredSavedBean.setStartTime(serviceStartTime);
                            Hawk.put(HawkProperty.getCareServiceRecordKey(careTakerInfoBean), careRecoredSavedBean);
                        }
                    });
                }
                break;
            case R.id.service_end_time_value_tv:
                //结束服务的时间
                if (isInnerAccount()) {
                    PickerManager.getInstance().showTimePickerView(mContext, PickerManager.getInstance().getTimeType(
                            "minute"), "", new PickerManager.OnTimePickerTimeSelectedListener() {
                        @Override
                        public void onTimeSelect(Date date, View v) {
                            String serviceEndTime = sdf.format(date);
                            if (StringTools.isStringValueOk(serviceStartTime)) {
                                if (TimeUtils.getTimeHourExpend(sdf, serviceStartTime, serviceEndTime) > 1) {
                                    mPresenter.endTime();
                                    mServiceEndTimeValueTv.setText(serviceEndTime);
                                    mStartStopServiceTv.setText(SERVICE_STOPED_TAG);
                                    String serviceLength = TimeUtils.getTimeExpend(sdf, serviceStartTime,
                                            serviceEndTime);
                                    mServiedTimeTv.setText("服务时长\n" + serviceLength);
                                    //将开始时间赋值到bean中
                                    careRecoredSavedBean.setEndTime(serviceEndTime);
                                    careRecoredSavedBean.setServiceLength(serviceLength);
                                    Hawk.put(HawkProperty.getCareServiceRecordKey(careTakerInfoBean),
                                            careRecoredSavedBean);
                                } else {
                                    ToastUtils.toast(mContext, "服务时间不到2小时，不能结束");
                                }
                            } else {
                                ToastUtils.toast(mContext, "服务还未开始，不能结束");
                            }
                        }
                    });
                }
                break;

            case R.id.service_type_value_tv:
                //选择定位类型
                mPresenter.getServiceType(getBaseBuilder().build(), CareContract.GET_SERVICE_TYPE);
                break;
        }
    }

    /**
     * 判断当前位置和登记位置是否在范围内
     *
     * @return
     */
    private boolean distanceIsOk() {
        if (0.0 == lat || 0.0 == lng) {
            ToastUtils.toast(mContext, "无法获取当前位置");
            return false;
        }
        long distance = Math.round(DistanceUtil.getDistance(
                new LatLng(careTakerInfoBean.getLatitude(), careTakerInfoBean.getLongitude()),
                new LatLng(lat, lng)));

        if (distance > 100) {
            ToastUtils.toast(mContext, "不在服务范围，请确保当前位置");
            return false;
        } else {
            return true;
        }
    }


    /**
     * 开始或者停止服务计时
     */
    private void startOrEndTimeOfService() {
        if (SERVICE_START_TAG.equals(getTextViewValue(mStartStopServiceTv))) {

            String startTime = sdf.format(new Date());
            //其次看开始时间是否是在8点之后 16点半之前

            if (!TimeUtils.isOKTimeToStartService(sdf, startTime, MIN_START_HOUR_MINUTE, MAX_START_HOUR_MINUTE)) {
                ToastUtils.toast(mContext, "服务时间段：早晨8点至下午16点半");
                return;
            }
            new AlertDialog.Builder(this)
                    .setMessage("请注意残疾人信息是否正确，确认开始服务吗？开始后将无法撤销！")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            serviceStartTime = startTime;
                            mServiceStartTimeValueTv.setText(serviceStartTime);
                            mStartStopServiceTv.setText(SERVICE_STOP_TAG);
                            mStartStopServiceTv.setBackgroundResource(R.drawable.sp_half_circle_red);
                            mPresenter.startTime(CareContract.START_TIME);
                            //将开始时间赋值到bean中
                            careRecoredSavedBean.setStartTime(serviceStartTime);
                            Hawk.put(HawkProperty.getCareServiceRecordKey(careTakerInfoBean), careRecoredSavedBean);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();

        } else if (SERVICE_STOP_TAG.equals(getTextViewValue(mStartStopServiceTv))) {
            //服务时间是否大于2小时
            String endTime = sdf.format(new Date());
            if (TimeUtils.getTimeHourExpend(sdf, serviceStartTime, endTime) > 1) {
                mStartStopServiceTv.setText(SERVICE_STOPED_TAG);
                mStartStopServiceTv.setBackgroundResource(R.drawable.sp_half_circle_green);
                mPresenter.endTime();
                mServiceEndTimeValueTv.setText(endTime);
                careRecoredSavedBean.setEndTime(endTime);
                careRecoredSavedBean.setServiceLength(TimeUtils.getTimeExpend(sdf, serviceStartTime, endTime));
            } else {
                ToastUtils.toast(mContext, "服务时间不到2小时，不能结束");
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.endTime();
        if (serviceAreas != null) {
            serviceAreas.clear();
            serviceAreas = null;
        }
        if (allServices != null) {
            allServices.clear();
            allServices = null;
        }
        if (peopleBeans != null) {
            peopleBeans.clear();
            peopleBeans = null;
        }
        if (selectedServicePeoples != null) {
            selectedServicePeoples.clear();
            selectedServicePeoples = null;
        }
    }


}
