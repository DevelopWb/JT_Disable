package com.juntai.tyb.homePage.careTaker.addCareTaker;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.basecomponent.utils.PubUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.bdmap.act.LocateSelectionActivity;
import com.juntai.tyb.base.TextListAdapter;
import com.juntai.tyb.base.selectPics.BaseSelectPicActivity;
import com.juntai.tyb.base.selectPics.SelectPhotosFragment;
import com.juntai.tyb.bean.careTaker.CareTakerBaseInfoBean;
import com.juntai.tyb.bean.careTaker.CareTakerInfoBean;
import com.juntai.tyb.bean.careTaker.CareTakerInfoMoreBean;
import com.juntai.tyb.bean.careTaker.CareTakerPicBean;
import com.juntai.tyb.bean.TextListBean;
import com.juntai.tyb.hcb.R;
import com.juntai.tyb.homePage.careTaker.CareContract;
import com.juntai.tyb.homePage.careTaker.CarePresent;
import com.juntai.tyb.homePage.careTaker.careInfo.CareInfoMoreActivity;
import com.juntai.tyb.uitils.HawkProperty;
import com.juntai.tyb.uitils.StringTools;
import com.orhanobut.hawk.Hawk;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @aouther tobato
 * @description 描述  添加托养人
 * @date 2020/7/10 16:53
 */
public class AddCareTakerActivity extends BaseSelectPicActivity<CarePresent> implements CareContract.ICareView,
        View.OnClickListener, SelectPhotosFragment.OnPhotoItemClick {

    private TextListAdapter textListAdapter;
    private CareTakerPicsAdapter mTakerPicsAdapter;
    private RecyclerView mTakerInfoRv;
    /**
     * 定位:
     */
    private TextView mAddTakerAddrTv;
    private RecyclerView mCareTakerPicsRv;
    /**
     * 比如：村南头倒数第二排
     */
    private EditText mOtherLocationDesEt;
    private EditText mConnecterNameEt;
    private EditText mConnecterNoteEt;
    private EditText mConnecterTelEt;
    private EditText mSpareNameEt;
    private EditText mSpareNoteEt;
    private EditText mSpareTelEt;
    /**
     * 如：常年在村前花棚看花，来之前电话联系他哥哥。
     */
    private EditText mAddTakerRemarkEt;
    /**
     * 提交
     */
    private TextView mAddTakerCommitTv;
    private int currentPosition;
    private String addr;

    public static String CARE_TAKER_ID_NO = "care_id";//残疾人身份证号
    public static String CARE_TAKER_YEAR = "care_year";//年度
    public static String CARE_TAKER_TYPE = "care_type";//编辑类型
    private String idNO;
    private String year;
    private int editType = 0;//0是添加托养人  1 是更改托养人
    private CareTakerInfoBean.DataBean careTakerInfoBean;

    public static int ADD_CARE_TAKER_TAG = 10085;//添加托养人标识

    @Override
    protected CarePresent createPresenter() {
        return new CarePresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_add_care_taker;
    }

    @Override
    public void initView() {
        setTitleName("添加本年度托养人员");
        initToolbarAndStatusBar(true);
        textListAdapter = new TextListAdapter(R.layout.item_text_layout);
        textListAdapter.setHidePresentBg(true);
        mTakerPicsAdapter = new CareTakerPicsAdapter(R.layout.care_item_pic);
        mTakerInfoRv = (RecyclerView) findViewById(R.id.taker_info_rv);
        mAddTakerAddrTv = (TextView) findViewById(R.id.add_taker_addr_tv);
        mAddTakerAddrTv.setOnClickListener(this);
        mAddTakerAddrTv.setText("   请稍等正在获取位置信息");
        mAddTakerAddrTv.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        if (addr != null) {
            mAddTakerAddrTv.setText(addr);
            mAddTakerAddrTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray_deeper));
        }
        mCareTakerPicsRv = (RecyclerView) findViewById(R.id.care_taker_pics_rv);
        initRecyclerview(mTakerInfoRv, textListAdapter, LinearLayoutManager.VERTICAL);
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        mCareTakerPicsRv.setAdapter(mTakerPicsAdapter);
        mCareTakerPicsRv.setLayoutManager(manager);

        mOtherLocationDesEt = (EditText) findViewById(R.id.other_location_des_et);
        mConnecterNameEt = (EditText) findViewById(R.id.connecter_name_et);
        mConnecterNoteEt = (EditText) findViewById(R.id.connecter_note_et);
        mConnecterTelEt = (EditText) findViewById(R.id.connecter_tel_et);
        mSpareNameEt = (EditText) findViewById(R.id.spare_name_et);
        mSpareNoteEt = (EditText) findViewById(R.id.spare_note_et);
        mSpareTelEt = (EditText) findViewById(R.id.spare_tel_et);
        mAddTakerRemarkEt = (EditText) findViewById(R.id.add_taker_remark_et);
        mAddTakerCommitTv = (TextView) findViewById(R.id.add_taker_commit_tv);
        mAddTakerCommitTv.setOnClickListener(this);

        mTakerPicsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                CareTakerPicBean dataBean =
                        (CareTakerPicBean) adapter.getData().get(position);
                currentPosition = position;
                int id = view.getId();
                if (id == R.id.select_pic_icon_iv) {//选择图片
                    if (isInnerAccount()) {
                        choseImage(0, AddCareTakerActivity.this, 1);
                    } else {
                        choseImage(1, AddCareTakerActivity.this, 1);
                    }
                } else if (id == R.id.delete_pushed_news_iv) {//删除图片
                    dataBean.setPicPath("");
                    adapter.notifyItemChanged(position);
                }
            }
        });
    }

    @Override
    public void initData() {

        mTakerPicsAdapter.setNewData(mPresenter.getPics());
        if (getIntent() != null) {
            editType = getIntent().getIntExtra(CARE_TAKER_TYPE, 0);
            if (0 == editType) {
                idNO = getIntent().getStringExtra(CARE_TAKER_ID_NO);
                year = getIntent().getStringExtra(CARE_TAKER_YEAR);
            } else {
                careTakerInfoBean = Hawk.get(HawkProperty.CARE_INFO_KEY);
                CareTakerInfoMoreBean.DataBean careTakerMoreBean = Hawk.get(HawkProperty.CARE_INFO_MORE_KEY);
                idNO = careTakerInfoBean.getIdNo();
                year = careTakerInfoBean.getYear();
                List<CareTakerPicBean> pics = mTakerPicsAdapter.getData();
                for (CareTakerPicBean pic : pics) {
                    switch (pic.getTitle()) {
                        case "房屋":
                            pic.setPicPath(careTakerInfoBean.getHouseImg());
                            break;
                        case "室内":
                            pic.setPicPath(careTakerInfoBean.getIndoorImg());
                            break;
                        case "人员":
                            pic.setPicPath(careTakerInfoBean.getPersonImg());
                            break;
                        case "大门":
                            pic.setPicPath(careTakerInfoBean.getGateImg());
                            break;
                        case "街道":
                            pic.setPicPath(careTakerInfoBean.getStreetImg());
                            break;
                        case "其他标志":
                            pic.setPicPath(careTakerInfoBean.getOtherImg());
                            break;
                        default:
                            break;
                    }
                }
                mTakerPicsAdapter.notifyDataSetChanged();
                mOtherLocationDesEt.setText(careTakerMoreBean.getDescription());
                mConnecterNameEt.setText(careTakerMoreBean.getTopContacts());
                mConnecterNoteEt.setText(careTakerMoreBean.getTopRelation());
                mConnecterTelEt.setText(careTakerMoreBean.getTopTel());
                updateTextValue(mSpareNameEt,careTakerMoreBean.getContact());
                updateTextValue(mSpareNoteEt,careTakerMoreBean.getRelation());
                updateTextValue(mSpareTelEt,careTakerMoreBean.getPhone());
                updateTextValue(mAddTakerRemarkEt,careTakerMoreBean.getRemark());

            }
            mPresenter.careTakerBaseInfo(getBaseBuilder().add("idNo", idNO).build(), CareContract.CARE_TAKER_BASE_INFO);
        }

    }

    /**
     * 更新view
     * @param textView
     * @param content
     */
   private void  updateTextValue(TextView textView,String content){
       if (StringTools.isStringValueOk(content)&&!"暂无".equals(content)) {
           textView.setText(content);
       }
   }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case CareContract.CARE_TAKER_BASE_INFO:
                //残疾人基本信息
                CareTakerBaseInfoBean careTakerBaseInfoBean = (CareTakerBaseInfoBean) o;
                if (careTakerBaseInfoBean != null) {
                    CareTakerBaseInfoBean.DataBean dataBean = careTakerBaseInfoBean.getData();
                    if (dataBean != null) {
                        textListAdapter.addData(new TextListBean("姓名", dataBean.getName()));
                        textListAdapter.addData(new TextListBean("性别", dataBean.getSex()));
                        textListAdapter.addData(new TextListBean("残疾详情", dataBean.getDetails()));
                        textListAdapter.addData(new TextListBean("残疾证号", dataBean.getCertificateNo()));
                        textListAdapter.addData(new TextListBean("现在住址", dataBean.getPresentAddress()));
                    }
                }
                break;
            case CareContract.ADD_CARE_TAKER:
                ToastUtils.toast(mContext, "添加成功");
                setResult(ADD_CARE_TAKER_TAG);
                finish();
                break;
            case CareContract.MODIFY_CARE_TAKER:
                ToastUtils.toast(mContext, "已提交修改申请");
                setResult(CareInfoMoreActivity.EDIT_CARE_TAKER_INFO);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean requestLocation() {
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.add_taker_addr_tv) {//选择地点
            Intent intent = new Intent(mContext, LocateSelectionActivity.class);
            startActivityForResult(intent, LocateSelectionActivity.SELECT_ADDR);
        } else if (id == R.id.add_taker_commit_tv) {
            if (!checkPicSelectedStatus()) {
                return;
            }

            MultipartBody.Builder builder = getPublishMultipartBody();
            builder.addFormDataPart("idNo", idNO);
            builder.addFormDataPart("year", year);
            String addr = getTextViewValue(mAddTakerAddrTv);
            if (addr.contains("请稍等")) {
                ToastUtils.toast(mContext, "无法获取定位数据，请手动选择当前位置");
                return;
            }
            builder.addFormDataPart("place", addr);
            builder.addFormDataPart("longitude", String.valueOf(lng));
            builder.addFormDataPart("latitude", String.valueOf(lat));
            List<CareTakerPicBean> arrays = mTakerPicsAdapter.getData();
            for (int i = 0; i < arrays.size(); i++) {
                CareTakerPicBean bean = arrays.get(i);
                String path = bean.getPicPath();
                if (StringTools.isStringValueOk(path)) {
                    if (path.contains("托养宝")) {
                        switch (i) {
                            case 0:
                                builder.addFormDataPart("houseImgFile", "houseImgFile",
                                        RequestBody.create(MediaType.parse("file"), new File(path)));
                                break;
                            case 1:
                                builder.addFormDataPart("indoorImgFile", "indoorImgFile",
                                        RequestBody.create(MediaType.parse("file"), new File(path)));
                                break;
                            case 2:
                                builder.addFormDataPart("personImgFile", "personImgFile",
                                        RequestBody.create(MediaType.parse("file"), new File(path)));
                                break;
                            case 3:
                                builder.addFormDataPart("gateImgFile", "gateImgFile",
                                        RequestBody.create(MediaType.parse(
                                                "file"), new File(path)));
                                break;
                            case 4:
                                builder.addFormDataPart("streetImgFile", "streetImgFile",
                                        RequestBody.create(MediaType.parse("file"), new File(path)));
                                break;
                            case 5:
                                builder.addFormDataPart("otherImgFile", "otherImgFile   ",
                                        RequestBody.create(MediaType.parse("file"), new File(path)));
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
            String des = getTextViewValue(mOtherLocationDesEt);
            if (!TextUtils.isEmpty(des)) {
                builder.addFormDataPart("description", des);
            }

            String connecterName = getTextViewValue(mConnecterNameEt);
            String connecterRelation = getTextViewValue(mConnecterNoteEt);
            String connecterTel = getTextViewValue(mConnecterTelEt);

            if (TextUtils.isEmpty(connecterName)) {
                ToastUtils.toast(mContext, "请输入常用联系人");
                return;
            }
            if (TextUtils.isEmpty(connecterRelation)) {
                ToastUtils.toast(mContext, "请输入和常用联系人的关系");
                return;
            }
            if (TextUtils.isEmpty(connecterTel)) {
                ToastUtils.toast(mContext, "请输入常用联系人的电话");
                return;
            }
            builder.addFormDataPart("topcontacts", connecterName);
            builder.addFormDataPart("toprelation", connecterRelation);
            if (!PubUtil.isMobileNO(connecterTel)) {
                ToastUtils.toast(mContext, "常用联系人手机格式不正确");
                return;
            }
            builder.addFormDataPart("toptel", connecterTel);

            String sparePersion = getTextViewValue(mSpareNameEt);
            if (!TextUtils.isEmpty(sparePersion)) {
                builder.addFormDataPart("contact", sparePersion);
            }
            String spareRelat = getTextViewValue(mSpareNoteEt);
            if (!TextUtils.isEmpty(spareRelat)) {
                builder.addFormDataPart("relation", spareRelat);
            }
            String sparePhone = getTextViewValue(mSpareTelEt);
            if (!TextUtils.isEmpty(sparePhone)) {
                if (!PubUtil.isMobileNO(sparePhone)) {
                    ToastUtils.toast(mContext, "备用联系人手机格式不正确");
                    return;
                }
                builder.addFormDataPart("phone", sparePhone);
            }
            String remark = getTextViewValue(mAddTakerRemarkEt);
            if (!TextUtils.isEmpty(remark)) {
                builder.addFormDataPart("remark", remark);
            }

            if (1 == editType) {
                //修改信息
                if (careTakerInfoBean != null) {
                    builder.addFormDataPart("id", String.valueOf(careTakerInfoBean.getId()));
                }
                mPresenter.modifyCareTaker(builder.build(), CareContract.MODIFY_CARE_TAKER);
            } else {
                mPresenter.addCareTaker(builder.build(), CareContract.ADD_CARE_TAKER);
            }
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
            CareTakerPicBean bean = arrays.get(i);
            String name = bean.getTitle();
            String path = bean.getPicPath();
            if ("".equals(path)) {
                ToastUtils.toast(mContext, String.format("%s%s%s", "请选择", name, "的图片"));
                isOk = false;
                break;
            }
        }
        return isOk;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LocateSelectionActivity.SELECT_ADDR && resultCode == RESULT_OK) {
            //地址选择
            lat = data.getDoubleExtra("lat", 0.0);
            lng = data.getDoubleExtra("lng", 0.0);
            String locAddress = data.getStringExtra("address");
            mAddTakerAddrTv.setText(locAddress);
            mAddTakerAddrTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray_deeper));
        }

    }

    @Override
    public void onVedioPicClick(BaseQuickAdapter adapter, int position) {

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
    public void onPicClick(BaseQuickAdapter adapter, int position) {

    }

    @Override
    public void onLocationReceived(BDLocation bdLocation) {

        addr = String.format("%s%s%s%s%s%s", bdLocation.getProvince(), bdLocation.getCity(),
                bdLocation.getDistrict(), bdLocation.getTown(), bdLocation.getStreet(),
                bdLocation.getStreetNumber());
        if (mAddTakerAddrTv != null) {
            mAddTakerAddrTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray_deeper));
            mAddTakerAddrTv.setText(addr);
        }

    }
}
