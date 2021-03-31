package com.juntai.disabled.federation.home_page.collectInfos.disabledInfoCollect;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.juntai.disabled.basecomponent.utils.DisplayUtil;
import com.juntai.disabled.basecomponent.utils.PickerManager;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.bdmap.act.SelectLocationActivity;
import com.juntai.disabled.federation.AppHttpPath;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.base.BaseSelectVideoActivity;
import com.juntai.disabled.federation.base.selectPics.SelectPhotosFragment;
import com.juntai.disabled.federation.home_page.PublishContract;
import com.juntai.disabled.federation.home_page.collectInfos.CollectInfoContract;
import com.juntai.disabled.federation.home_page.collectInfos.CollectInfoPresent;
import com.juntai.disabled.federation.utils.DateUtil;
import com.juntai.disabled.federation.utils.StringTools;
import com.juntai.disabled.federation.utils.UserInfoManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述  采集  视频采集和无障碍采集
 * @CreateDate: 2021/3/28 14:19
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/3/28 14:19
 */
public abstract class BaseCollectActivity extends BaseSelectVideoActivity<CollectInfoPresent> implements SelectPhotosFragment.OnPicCalculateed, CollectInfoContract.TakeInfoViewBase {
    /**
     * 请选择地点
     */
    private TextView mSelectAddrTv, mPicNoticeTv, mPicNameTv, mUploadYearTv;
    private ImageView mPlaceChoose;
    private TextView mUpdateTimeTv;

    /**
     * 输入文字描述
     */
    private EditText mRemarkEt;
    /**
     * 上传
     */
    private TextView mPushVideoCrashTv;
    private SelectPhotosFragment selectPhotosFragment;

    private boolean isVideoCollect = false;
    private String idNum;
    private String addr;

    @Override
    public int getLayoutView() {
        return R.layout.video_collect_activity;
    }


    @Override
    protected CollectInfoPresent createPresenter() {
        return new CollectInfoPresent();
    }


    @Override
    public void onLocationReceived(BDLocation bdLocation) {
        if (bdLocation == null) {
            return;
        }
        lat = bdLocation.getLatitude();
        lng = bdLocation.getLongitude();
        addr = bdLocation.getAddrStr();
        mSelectAddrTv.setText(addr);
    }

    @Override
    public boolean requestLocation() {
        return true;
    }

    @Override
    public void initView() {
        if ("视频采集".equals(getTitleName())) {
            isVideoCollect = true;
        } else {
            isVideoCollect = false;
        }
        setTitleName(getTitleName());
        mSelectAddrTv = (TextView) findViewById(R.id.select_addr_tv);
        mPicNoticeTv = (TextView) findViewById(R.id.pic_notice_tv);
        mPicNameTv = (TextView) findViewById(R.id.pic_name_tv);
        mUploadYearTv = (TextView) findViewById(R.id.upload_year_tv);
        mPicNameTv.setText(isVideoCollect ? "图片" : "选择图片(至少两张)");
        mPicNoticeTv.setVisibility(isVideoCollect ? View.VISIBLE : View.GONE);
        mUploadYearTv.setVisibility(!isVideoCollect ? View.VISIBLE : View.GONE);
        mSelectAddrTv.setOnClickListener(this);
        mUploadYearTv.setOnClickListener(this);
        mPlaceChoose = (ImageView) findViewById(R.id.place_choose);
        mPlaceChoose.setOnClickListener(this);
        mUpdateTimeTv = (TextView) findViewById(R.id.update_time_tv);
        mUpdateTimeTv.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        mRemarkEt = (EditText) findViewById(R.id.remark_et);
        mPushVideoCrashTv = (TextView) findViewById(R.id.push_video_crash_tv);
        mPushVideoCrashTv.setOnClickListener(this);
        super.initView();

    }

    protected abstract String getTitleName();

    @Override
    public void initData() {
        if (getIntent() != null) {
            idNum = getIntent().getStringExtra(DisabledDetailActivity.DISABLED_ID_NUM);
        }
        selectPhotosFragment = SelectPhotosFragment.newInstance()
                .setPhotoSpace(45)
                .setMaxCount(isVideoCollect ? 3 : 9)
                .setPicCalculateCallBack(this)
                .setShowTag(isVideoCollect ? true : false);
        FragmentManager fragmentManager = getSupportFragmentManager();
        //开启事务
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.replace(R.id.imageselect_layout, selectPhotosFragment);
        //最后一步 记得commit
        beginTransaction.commit();
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.select_addr_tv:
                startActivityForResult(new Intent(mContext, SelectLocationActivity.class),
                        PublishContract.REQUEST_CODE_CHOOSE_PLACE);
                break;
            case R.id.place_choose:
                mSelectAddrTv.performClick();
                break;
            case R.id.upload_year_tv:
                //选择上传年份
                PickerManager.getInstance().showTimePickerView(this, new boolean[]{true, false, false, false, false,
                        false}, "选择年份", new PickerManager.OnTimePickerTimeSelectedListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        mUploadYearTv.setText(DateUtil.getDateString(date, "yyyy"));
                    }
                });
                break;
            case R.id.push_video_crash_tv:
                MultipartBody.Builder builder = getPublishMultipartBody();
                //上传
                List<String> pics = selectPhotosFragment.getPhotosPath();
                if (isVideoCollect) {
                    if (pics.size() < 3) {
                        ToastUtils.warning(mContext, "请选择3张图片");
                        return;
                    }
                } else {
                    if (pics.size() < 2) {
                        ToastUtils.warning(mContext, "最少选择两张照片");
                        return;
                    }
                }
                if (!StringTools.isStringValueOk(videoPath)) {
                    ToastUtils.warning(mContext, "请选择视频");
                    return;
                }
                if (TextUtils.isEmpty(getTextViewValue(mRemarkEt))) {
                    ToastUtils.warning(mContext, "请输入描述信息");
                    return;
                }
                if (isVideoCollect) {
                    for (int i = 0; i < pics.size(); i++) {
                        String path = pics.get(i);
                        if (0 == i) {
                            builder.addFormDataPart("fileOne", "fileOne", RequestBody.create(MediaType.parse("file"),
                                    new File(path)));
                        } else if (1 == i) {
                            builder.addFormDataPart("fileTwo", "fileTwo", RequestBody.create(MediaType.parse("file"),
                                    new File(path)));
                        } else {
                            builder.addFormDataPart("fileThree", "fileThree", RequestBody.create(MediaType.parse(
                                    "file"), new File(path)));
                        }
                    }
                    builder.addFormDataPart("fileFour", "fileFour", RequestBody.create(MediaType.parse("file"),
                            new File(videoPath)));
                    builder.addFormDataPart("reporter", String.valueOf(UserInfoManager.getUserId()));
                    builder.addFormDataPart("idno", idNum);
                    builder.addFormDataPart("latitude", String.valueOf(lat));
                    builder.addFormDataPart("longitude", String.valueOf(lng));
                    builder.addFormDataPart("remark", getTextViewValue(mRemarkEt));

                } else {
                    if (TextUtils.isEmpty(getTextViewValue(mUploadYearTv))) {
                        ToastUtils.warning(mContext, "请选择上报年份");
                        return;
                    }
                    for (int i = 0; i < pics.size(); i++) {
                        String path = pics.get(i);
                        //这里上传的是多图
                        builder.addFormDataPart("file", "file", RequestBody.create(MediaType.parse("file"),
                                new File(path)));
                    }
                    builder.addFormDataPart("video", "video", RequestBody.create(MediaType.parse("file"),
                            new File(videoPath)));
                    builder.addFormDataPart("videoPhoto", "videoPhoto", RequestBody.create(MediaType.parse("file"),
                            new File(videoScreenPath)));

                    builder.addFormDataPart("casePlace", addr)
                            .addFormDataPart("idNumber", idNum)
                            .addFormDataPart("caseLongitude", String.valueOf(lng))
                            .addFormDataPart("caseLatitude", String.valueOf(lat))
                            .addFormDataPart("caseReporter", String.valueOf(UserInfoManager.getUserId()))
                            .addFormDataPart("caseReportDate", getTextViewValue(mUpdateTimeTv))
                            .addFormDataPart("caseDeContent", getTextViewValue(mRemarkEt))
                            .addFormDataPart("uploadingYear", getTextViewValue(mUploadYearTv));//上传年份

                }
                mPresenter.insertEvent(builder.build(), isVideoCollect ? AppHttpPath.COLLECT_DISABLED_VIDEO :
                        AppHttpPath.COLLECT_DISABLED_ACCESSBLE);
                break;
            default:
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PublishContract.REQUEST_CODE_CHOOSE_PLACE && resultCode == RESULT_OK) {
            //地址选择
            lat = data.getDoubleExtra("lat", 0.0);
            lng = data.getDoubleExtra("lng", 0.0);
            addr = data.getStringExtra("address");
            mSelectAddrTv.setText(addr);
        }
    }


    @Override
    public void onSuccess(String tag, Object o) {
       ToastUtils.toast(mContext,"上传成功");
       finish();
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
}
