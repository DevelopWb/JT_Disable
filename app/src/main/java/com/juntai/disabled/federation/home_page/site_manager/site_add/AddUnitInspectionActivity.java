package com.juntai.disabled.federation.home_page.site_manager.site_add;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.basecomponent.base.BaseMvpActivity;
import com.juntai.disabled.basecomponent.utils.ActionConfig;
import com.juntai.disabled.basecomponent.utils.DisplayUtil;
import com.juntai.disabled.basecomponent.utils.EventManager;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.basecomponent.utils.LogUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.bdmap.act.LocationSeltionActivity;
import com.juntai.disabled.bdmap.service.LocateAndUpload;
import com.juntai.disabled.federation.MyApp;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.base.selectPics.SelectPhotosFragment;
import com.juntai.disabled.federation.home_page.PublishContract;
import com.juntai.disabled.federation.home_page.PublishPresent;
import com.juntai.disabled.federation.home_page.site_manager.SiteManagerContract;
import com.juntai.disabled.federation.home_page.site_manager.SiteManagerPresent;
import com.juntai.disabled.federation.utils.AppUtils;
import com.juntai.disabled.federation.utils.StringTools;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @description 添加单位检查记录
 * @aouther ZhangZhenlong
 * @date 2020-7-4
 */
public class AddUnitInspectionActivity extends BaseMvpActivity<SiteManagerPresent> implements SiteManagerContract.ISiteManagerView,
        View.OnClickListener,
        SelectPhotosFragment.OnPhotoItemClick, SelectPhotosFragment.OnPicCalculateed  {
    /**
     * 请输入标题
     */
    private LinearLayout mTitleLayout;
    private View mTitleLine;

    /**
     * 请输入描述信息
     */
    private EditText mDescriptionEt;
    /**
     * 已输入0/200
     */
    private TextView mInputNumTv;
    /**
     * 请选择地点
     */
    private TextView mAddressTv;
    private ImageView mAddressIv;
    private FrameLayout mPictureFragment;
    private ImageView mItemVideoPic, mDeleteVedio, mItemVideoTag;
    /**
     * 确认
     */
    private TextView mOkTv;
    private TextView imageTag, addressTag;

    //地址
    BDLocation bdLocation;
    private String locAddress = "";
    private Double locLat, locLon;
    //视频回调广播
    IntentFilter intentFilter = new IntentFilter();
    private VideoBroadcastReceiver videoBroadcastReceiver = null;
    //视频
    private String videoScreen;//视频封面
    private String videoPath;//视频地址

    private SelectPhotosFragment selectPhotosFragment;
    private int imgMaxCount = 9;//图片数量
    private int unitId;//单位id
    PublishPresent publishPresent;

    @Override
    protected SiteManagerPresent createPresenter() {
        return new SiteManagerPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_edit_inspection;
    }

    @Override
    public void initView() {
        unitId = getIntent().getIntExtra(AppUtils.ID_KEY,0);
        mTitleLayout = (LinearLayout) findViewById(R.id.title_layout);
        mTitleLine = (View) findViewById(R.id.title_line);
        mTitleLayout.setVisibility(View.GONE);
        mTitleLine.setVisibility(View.GONE);
        mDescriptionEt = (EditText) findViewById(R.id.description_et);
        mInputNumTv = (TextView) findViewById(R.id.input_num_tv);
        mAddressTv = (TextView) findViewById(R.id.address_tv);
        mAddressIv = findViewById(R.id.address_iv);
        mAddressTv.setOnClickListener(this);
        mAddressIv.setOnClickListener(this);
        mPictureFragment = (FrameLayout) findViewById(R.id.picture_fragment);
        mItemVideoPic = (ImageView) findViewById(R.id.item_video_pic);
        mItemVideoTag = (ImageView) findViewById(R.id.item_video_tag);
        mDeleteVedio = (ImageView) findViewById(R.id.push_case_delete_vedio_iv);
        mItemVideoPic.setOnClickListener(this);
        mDeleteVedio.setOnClickListener(this);
        mOkTv = (TextView) findViewById(R.id.ok_tv);
        mOkTv.setOnClickListener(this);
        imageTag = findViewById(R.id.image_tag);
        addressTag = findViewById(R.id.address_tag);

        publishPresent = new PublishPresent();
        publishPresent.setCallBack(this);

        mDescriptionEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) {
                    mInputNumTv.setText("已输入" + s.length() + "/500");
                }
            }
        });

        //注册广播
        videoBroadcastReceiver = new VideoBroadcastReceiver();
        intentFilter.addAction(ActionConfig.BROAD_VIDEO);
        registerReceiver(videoBroadcastReceiver, intentFilter);

        selectPhotosFragment = SelectPhotosFragment.newInstance().setPhotoTitle("")
                .setPhotoSpace(60).setType(1).setPicCalculateCallBack(this);
        mDescriptionEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(500)});
        mDescriptionEt.setHint("请输入检查描述");

        setTitleName("添加检查记录");
        mInputNumTv.setText("已输入0/500");
        imgMaxCount = 9;
        addressTag.setText("检查地点");
        imageTag.setText("照片上传（需上传至少3张，最多9张）");

        selectPhotosFragment.setMaxCount(imgMaxCount);
        FragmentManager fragmentManager = getSupportFragmentManager();
        //开启事务
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.replace(R.id.picture_fragment, selectPhotosFragment);
        //最后一步 记得commit
        beginTransaction.commit();
    }

    @Override
    public void initData() {
        setDefaultData();
    }

    /**
     * 设置默认数据
     */
    public void setDefaultData() {
        bdLocation = LocateAndUpload.bdLocation;
        if (bdLocation != null) {
            locAddress = bdLocation.getAddrStr() != null ? bdLocation.getCity() + bdLocation.getDistrict() + bdLocation.getStreet() : "";
            locLat = bdLocation.getLatitude();
            locLon = bdLocation.getLongitude();
        } else {
            locAddress = "";
            locLat = 0.0;
            locLon = 0.0;
        }
        mAddressTv.setText(locAddress);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.address_iv:
            case R.id.address_tv:
                startActivityForResult(new Intent(mContext, LocationSeltionActivity.class), PublishContract.REQUEST_CODE_CHOOSE_PLACE);
                break;
            case R.id.item_video_pic:
                //选择视频
                publishPresent.recordVideo(AddUnitInspectionActivity.this);
                break;
            case R.id.push_case_delete_vedio_iv:
                //删除录制得视频
                videoPath = null;
                mItemVideoTag.setVisibility(View.GONE);
                mDeleteVedio.setVisibility(View.GONE);
                ImageLoadUtil.loadImage(mContext.getApplicationContext(), R.mipmap.add_icons, mItemVideoPic);
                break;
            case R.id.ok_tv:
                if (MyApp.isFastClick()) {
                    ToastUtils.warning(mContext,"点击过于频繁");
                    return;
                }
                //发布
                if (!StringTools.isStringValueOk(getTextViewValue(mAddressTv))) {
                    ToastUtils.warning(mContext, "请选择地点");
                    return;
                }
                if (!StringTools.isStringValueOk(getTextViewValue(mDescriptionEt))) {
                    ToastUtils.warning(mContext, "请输入检查描述");
                    return;
                }
                MultipartBody.Builder builder = mPresenter.getPublishMultipartBody();
                builder.addFormDataPart("longitude", String.valueOf(locLon))
                        .addFormDataPart("latitude", String.valueOf(locLat))
                        .addFormDataPart("clientId",String.valueOf(unitId))
                        .addFormDataPart("address", locAddress)
                        .addFormDataPart("remake", getTextViewValue(mDescriptionEt))
                        .addFormDataPart("departmentId", MyApp.getUser().getData().getDepartmentId()+"");
                List<String> pics = selectPhotosFragment.getPhotosPath();
                if (pics.size() < 3) {
                    ToastUtils.warning(mContext, "请选择至少3张图片");
                    return;
                }
                if (!StringTools.isStringValueOk(videoPath)) {
                    ToastUtils.warning(mContext, "请选择视频");
                    return;
                }

                if (pics.size() > 0) {
                    for (int i = 0; i < pics.size(); i++) {
                        String path = pics.get(i);
                        builder.addFormDataPart("picture", "picture", RequestBody.create(MediaType.parse("file"), new File(path)));
                    }
                }
                if (StringTools.isStringValueOk(videoPath)) {
                    builder.addFormDataPart("video", "video", RequestBody.create(MediaType.parse("file"), new File(videoPath)));
                }
                mPresenter.addSiteInspection("", builder.build());
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess(String tag, Object o) {
        ToastUtils.success(mContext, "保存成功！");
//        setResult(AppUtils.PUBLISH_EMPLOYEE_BACK);
        EventManager.sendStringMsg(ActionConfig.REFRASH_SITE_EMPLOYEE_LIST);
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (videoBroadcastReceiver != null) {
            unregisterReceiver(videoBroadcastReceiver);
        }
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PublishContract.REQUEST_CODE_CHOOSE_PLACE && resultCode == RESULT_OK) {
            //地址选择
            locLat = data.getDoubleExtra("lat", 0.0);
            locLon = data.getDoubleExtra("lng", 0.0);
            locAddress = data.getStringExtra("address");
            mAddressTv.setText(locAddress);
            LogUtil.d("fffffffff" + locLat + "   " + locLon + "    " + locAddress);
        }
    }

    @Override
    public void onVedioPicClick(BaseQuickAdapter adapter, int position) {

    }

    @Override
    public void onPicClick(BaseQuickAdapter adapter, int position) {

    }

    /**
     * 动态获取到fragment中图片的宽高后 改变mItemVideoPic的宽高
     *
     * @param width
     */
    @Override
    public void picCalculateed(int width) {
        ViewGroup.LayoutParams params = mItemVideoPic.getLayoutParams();
        params.width = DisplayUtil.dp2px(mContext, width);
        params.height = DisplayUtil.dp2px(mContext, width);
        mItemVideoPic.setLayoutParams(params);
    }


    /**
     * 视频录制成功回调广播
     */
    public class VideoBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            videoPath = intent.getStringExtra("videoUri");
            videoScreen = intent.getStringExtra("videoScreenshotUri");
            ImageLoadUtil.loadImageCache(mContext.getApplicationContext(), videoScreen, mItemVideoPic);
            mDeleteVedio.setVisibility(View.VISIBLE);
            mItemVideoTag.setVisibility(View.VISIBLE);
        }
    }
}
