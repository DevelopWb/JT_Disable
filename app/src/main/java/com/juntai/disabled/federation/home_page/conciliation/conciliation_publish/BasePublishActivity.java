package com.juntai.disabled.federation.home_page.conciliation.conciliation_publish;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.basecomponent.base.BaseMvpActivity;
import com.juntai.disabled.basecomponent.utils.ActionConfig;
import com.juntai.disabled.basecomponent.utils.DisplayUtil;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.basecomponent.utils.LogUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.basecomponent.widght.BaseBottomDialog;
import com.juntai.disabled.federation.MyApp;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.base.customview.MediatorInfoDialog;
import com.juntai.disabled.federation.base.selectPics.SelectPhotosFragment;
import com.juntai.disabled.federation.bean.CityBean;
import com.juntai.disabled.federation.bean.conciliation.ConciliationTypesBean;
import com.juntai.disabled.federation.bean.conciliation.MediatorAllListBean;
import com.juntai.disabled.federation.bean.conciliation.UnitListBean;
import com.juntai.disabled.federation.home_page.conciliation.conciliation.ConciliationContract;
import com.juntai.disabled.federation.home_page.conciliation.conciliation.ConciliationPresent;
import com.juntai.disabled.federation.home_page.weather.SelectLocation;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Describe:????????????
 * Create by zhangzhenlong
 * 2020-7-22
 * email:954101549@qq.com
 */
public abstract class BasePublishActivity extends BaseMvpActivity<ConciliationPresent> implements ConciliationContract.IConciliationView,
        View.OnClickListener,
        SelectPhotosFragment.OnPhotoItemClick, SelectPhotosFragment.OnPicCalculateed {
    /**
     * ????????????????????????
     */
    protected EditText mNameEt;
    /**
     * ????????????????????????
     */
    protected EditText mTelEt;
    protected EditText mAddressEt;
    /**
     * ???????????????????????????
     */
    protected EditText mNameEt2;
    /**
     * ???????????????????????????
     */
    protected EditText mTelEt2;
    /**
     * ?????????????????????????????????????????????????????????????????????????????????
     */
    protected EditText mDescriptionEt;
    /**
     * ?????????0/200
     */
    protected TextView mInputNumTv;
    /**
     * ??????
     */
    protected TextView mOkTv;
    protected ImageView mItemVideoPic;
    protected ImageView mItemVideoTag;
    protected ImageView mDeleteVedio;
    //??????????????????
    IntentFilter intentFilter = new IntentFilter();
    protected VideoBroadcastReceiver videoBroadcastReceiver = null;
    //??????
    protected String videoScreen;//????????????
    protected String videoPath;//????????????

    protected BaseBottomDialog baseBottomDialog;
    protected BaseBottomDialog.OnItemClick onItemClick;
    protected SelectPhotosFragment selectPhotosFragment;

    protected MediatorAllListBean.MediatorBean policeBean;//??????
    protected MediatorAllListBean.MediatorBean lawyerBean;//??????
    protected MediatorAllListBean.MediatorBean mediatorBean;//?????????
    /**
     * ???????????????
     */
    protected TextView mAreaTv;
    /**
     * ???????????????
     */
    protected TextView mUnitTv;
    protected RecyclerView mPoliceRecyclerview;
    protected RecyclerView mLawyerRecyclerview;
    protected RecyclerView mMediatorRecyclerview;
    ChooseMediatorsAdapter chooseMediatorsAdapterPolices;
    ChooseMediatorsAdapter chooseMediatorsAdapterLawyers;
    ChooseMediatorsAdapter chooseMediatorsAdapterMediators;
    List<MediatorAllListBean.MediatorBean> polices = new ArrayList<>();
    List<MediatorAllListBean.MediatorBean> lawyers = new ArrayList<>();
    List<MediatorAllListBean.MediatorBean> mediators = new ArrayList<>();

    protected OptionsPickerView optionsPickerViewUnits;
    protected CityBean.DataBean areaBean;
    protected UnitListBean.DataBean unitBean;
    /**
     * ???????????????
     */
    protected TextView mTypeTv;
    /**
     * ??????????????????
     */
    protected TextView mChildTypeTv;
    protected OptionsPickerView optionsPickerViewTypes;
    protected OptionsPickerView optionsPickerViewChildTypes;
    protected ConciliationTypesBean.DataBean typeBean;
    protected ConciliationTypesBean.DataBean childTypeBean;

    @Override
    protected ConciliationPresent createPresenter() {
        return new ConciliationPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_publish_conciliation;
    }

    @Override
    public void initView() {
        setTitleName("????????????");
        mAddressEt = findViewById(R.id.address_et);
        mNameEt = (EditText) findViewById(R.id.name_et);
        mTelEt = (EditText) findViewById(R.id.tel_et);
        mNameEt2 = (EditText) findViewById(R.id.name_et_2);
        mTelEt2 = (EditText) findViewById(R.id.tel_et_2);
        mDescriptionEt = (EditText) findViewById(R.id.description_et);
        mInputNumTv = (TextView) findViewById(R.id.input_num_tv);
        mOkTv = (TextView) findViewById(R.id.ok_tv);
        mOkTv.setOnClickListener(this);
        mItemVideoPic = (ImageView) findViewById(R.id.item_video_pic);
        mItemVideoPic.setOnClickListener(this);
        mItemVideoTag = (ImageView) findViewById(R.id.item_video_tag);
        mDeleteVedio = (ImageView) findViewById(R.id.item_delete_vedio_iv);
        mDeleteVedio.setOnClickListener(this);
        mAreaTv = (TextView) findViewById(R.id.area_tv);
        mAreaTv.setOnClickListener(this);
        mUnitTv = (TextView) findViewById(R.id.unit_tv);
        mUnitTv.setOnClickListener(this);
        mTypeTv = (TextView) findViewById(R.id.type_tv);
        mTypeTv.setOnClickListener(this);
        mChildTypeTv = (TextView) findViewById(R.id.child_type_tv);
        mChildTypeTv.setOnClickListener(this);
        mPoliceRecyclerview = (RecyclerView) findViewById(R.id.police_recyclerview);
        mLawyerRecyclerview = (RecyclerView) findViewById(R.id.lawyer_recyclerview);
        mMediatorRecyclerview = (RecyclerView) findViewById(R.id.mediator_recyclerview);

        mPoliceRecyclerview.setLayoutManager(new GridLayoutManager(mContext, 5));
        mLawyerRecyclerview.setLayoutManager(new GridLayoutManager(mContext, 5));
        mMediatorRecyclerview.setLayoutManager(new GridLayoutManager(mContext, 5));
        chooseMediatorsAdapterPolices = new ChooseMediatorsAdapter(R.layout.item_choose_mediator, polices);
        chooseMediatorsAdapterLawyers = new ChooseMediatorsAdapter(R.layout.item_choose_mediator, lawyers);
        chooseMediatorsAdapterMediators = new ChooseMediatorsAdapter(R.layout.item_choose_mediator, mediators);
        mPoliceRecyclerview.setAdapter(chooseMediatorsAdapterPolices);
        mLawyerRecyclerview.setAdapter(chooseMediatorsAdapterLawyers);
        mMediatorRecyclerview.setAdapter(chooseMediatorsAdapterMediators);

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
                    mInputNumTv.setText("?????????" + s.length() + "/200");
                }
            }
        });

        //????????????
        videoBroadcastReceiver = new VideoBroadcastReceiver();
        intentFilter.addAction(ActionConfig.BROAD_VIDEO);
        registerReceiver(videoBroadcastReceiver, intentFilter);

        selectPhotosFragment = SelectPhotosFragment.newInstance().setPhotoTitle("")
                .setPhotoSpace(60).setMaxCount(10).setType(0).setPicCalculateCallBack(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        //????????????
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.replace(R.id.picture_fragment, selectPhotosFragment);
        //???????????? ??????commit
        beginTransaction.commit();

        chooseMediatorsAdapterPolices.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.item_iv) {
                    //????????????
                    showMediatorInfoDialog(chooseMediatorsAdapterPolices.getData().get(position));
                } else {
                    if (position == chooseMediatorsAdapterPolices.getChoosePosition()) {
                        policeBean = null;
                        chooseMediatorsAdapterPolices.setChoosePosition(-1);
                    } else {
                        policeBean = chooseMediatorsAdapterPolices.getData().get(position);
                        chooseMediatorsAdapterPolices.setChoosePosition(position);
                    }
                    chooseMediatorsAdapterPolices.notifyDataSetChanged();
                }
            }
        });
        chooseMediatorsAdapterMediators.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.item_iv) {
                    //????????????
                    showMediatorInfoDialog(chooseMediatorsAdapterMediators.getData().get(position));
                } else {
                    if (position == chooseMediatorsAdapterMediators.getChoosePosition()) {
                        mediatorBean = null;
                        chooseMediatorsAdapterMediators.setChoosePosition(-1);
                    } else {
                        mediatorBean = chooseMediatorsAdapterMediators.getData().get(position);
                        chooseMediatorsAdapterMediators.setChoosePosition(position);
                    }
                    chooseMediatorsAdapterMediators.notifyDataSetChanged();
                }
            }
        });
        chooseMediatorsAdapterLawyers.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.item_iv) {
                    //????????????
                    showMediatorInfoDialog(chooseMediatorsAdapterLawyers.getData().get(position));
                } else {
                    if (position == chooseMediatorsAdapterLawyers.getChoosePosition()) {
                        lawyerBean = null;
                        chooseMediatorsAdapterLawyers.setChoosePosition(-1);
                    } else {
                        lawyerBean = chooseMediatorsAdapterLawyers.getData().get(position);
                        chooseMediatorsAdapterLawyers.setChoosePosition(position);
                    }
                    chooseMediatorsAdapterLawyers.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void initData() {
        mNameEt.setEnabled(false);
        mNameEt.setFocusable(false);
        mTelEt.setEnabled(false);
        mTelEt.setFocusable(false);
        mTelEt.setText(MyApp.getUser().getData().getAccount());
        mNameEt.setText(MyApp.getUser().getData().getRealName());
        initViewRightDrawable(mAreaTv, R.mipmap.arrow_right, 23, 23);
        initViewRightDrawable(mUnitTv, R.mipmap.arrow_right, 23, 23);
        initViewRightDrawable(mTypeTv, R.mipmap.arrow_right, 23, 23);
        initViewRightDrawable(mChildTypeTv, R.mipmap.arrow_right, 23, 23);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case ConciliationContract.SELECT_VIDEO_RESULT:
                List<String> paths = Matisse.obtainPathResult(data);
                if (paths != null && paths.size() > 0) {
                    if (new File(paths.get(0)).length() / 1024 < 10000) {
                        videoPath = paths.get(0);
                        videoScreen = paths.get(0);
                        LogUtil.e("size-->" + new File(videoPath));
                        ImageLoadUtil.loadImageCache(mContext.getApplicationContext(), videoScreen, mItemVideoPic);
                        mDeleteVedio.setVisibility(View.VISIBLE);
                        mItemVideoTag.setVisibility(View.VISIBLE);
                    } else {
                        ToastUtils.warning(this, "???????????????10m?????????");
                    }
                }
                break;
            case SelectLocation.SELECT_LOCATION:
                CityBean.DataBean dataBeanPrivince = (CityBean.DataBean) data.getSerializableExtra("privince");
                CityBean.DataBean dataBeanCity = (CityBean.DataBean) data.getSerializableExtra("city");
                CityBean.DataBean dataBeanTown = (CityBean.DataBean) data.getSerializableExtra("town");
                if (dataBeanTown != null){
                    areaBean = dataBeanTown;
                    mAreaTv.setText(dataBeanPrivince.getName()+dataBeanCity.getName()+dataBeanTown.getName());
                }else{
                    areaBean = dataBeanCity;
                    mAreaTv.setText(dataBeanPrivince.getName()+dataBeanCity.getName());
                }
                unitBean = null;
                mUnitTv.setText("");
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (videoBroadcastReceiver != null) {
            unregisterReceiver(videoBroadcastReceiver);
        }
        releaseDialog();
        MyApp.delCache();
        releasePickerView(optionsPickerViewUnits);
        releasePickerView(optionsPickerViewTypes);
        releasePickerView(optionsPickerViewChildTypes);
        super.onDestroy();
    }

    /**
     * ??????pickerview
     *
     * @param optionsPickerView
     */
    protected void releasePickerView(OptionsPickerView optionsPickerView) {
        if (optionsPickerView != null) {
            if (optionsPickerView.isShowing()) {
                optionsPickerView.dismiss();
                optionsPickerView = null;
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
    public void picCalculateed(int width) {
        ViewGroup.LayoutParams params = mItemVideoPic.getLayoutParams();
        params.width = DisplayUtil.dp2px(mContext, width);
        params.height = DisplayUtil.dp2px(mContext, width);
        mItemVideoPic.setLayoutParams(params);
    }

    /**
     * ??????????????????????????????
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

    /**
     * ?????????dialog
     */
    public void initBottomDialog(List<String> arrays) {
        if (baseBottomDialog == null) {
            onItemClick = new BaseBottomDialog.OnItemClick() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    switch (position) {
                        case 0:
                            //??????
                            mPresenter.recordVideo(BasePublishActivity.this);
                            break;
                        case 1:
                            //??????
                            mPresenter.videoChoose(BasePublishActivity.this);
                            break;
                    }
                    baseBottomDialog.dismiss();
                }
            };
            baseBottomDialog = new BaseBottomDialog();
            baseBottomDialog.setOnBottomDialogCallBack(onItemClick);
        }
        baseBottomDialog.setData(arrays);
        baseBottomDialog.show(getSupportFragmentManager(), "arrays");
    }

    /**
     * ????????????
     *
     * @param dataBean
     */
    protected void showMediatorInfoDialog(MediatorAllListBean.MediatorBean dataBean) {
        MediatorInfoDialog infoDialog = new MediatorInfoDialog(mContext).builder();
        infoDialog.setCanceledOnTouchOutside(true).setMediatorBean(dataBean).show();
    }

    /**
     * ??????dialog
     */
    protected void releaseDialog() {
        if (baseBottomDialog != null) {
            if (baseBottomDialog.isAdded()) {
                onItemClick = null;
                baseBottomDialog.setOnBottomDialogCallBack(null);
                baseBottomDialog.dismiss();
            }
        }
        baseBottomDialog = null;
    }
}
