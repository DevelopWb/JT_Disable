package com.juntai.tyb.homePage.careTaker.careInfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.tyb.base.BaseAppActivity;
import com.juntai.tyb.bean.careTaker.CareTakerInfoMoreBean;
import com.juntai.tyb.hcb.R;
import com.juntai.tyb.homePage.careTaker.CareContract;
import com.juntai.tyb.homePage.careTaker.CarePresent;
import com.juntai.tyb.homePage.careTaker.addCareTaker.AddCareTakerActivity;
import com.juntai.tyb.uitils.HawkProperty;
import com.orhanobut.hawk.Hawk;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * @aouther tobato
 * @description 描述  托养详情 更多
 * @date 2020/7/12 11:17
 */
public class CareInfoMoreActivity extends BaseAppActivity<CarePresent> implements CareContract.ICareView,
        View.OnClickListener {


    /**
     * 比如：村南头倒数第二排
     */
    private EditText mOtherLocationDesEt;
    private EditText mConnecterNameEt;
    private EditText mConnecterNoteEt;
    private TextView mConnecterTelValueTv;
    private EditText mSpareNameEt;
    private EditText mSpareNoteEt;
    private TextView mSpareTelValueTv;
    /**
     * 如：常年在村前花棚看花，来之前电话联系他哥哥。
     */
    private EditText mAddTakerRemarkEt;
    /**
     * 提交
     */
    private TextView mAddTakerCommitTv;

    public static int EDIT_CARE_TAKER_INFO = 10010;//申请修改信息
    private CareTakerInfoMoreBean.DataBean infoBean;
    private SmartRefreshLayout mCareInfoMoreSf;
    private EditText mRejectReasonEt;
    private LinearLayout mRejectReasonLl;

    @Override
    protected CarePresent createPresenter() {
        return new CarePresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_care_info_more;
    }

    @Override
    public void initView() {
        setTitleName("托养信息-更多");
        mOtherLocationDesEt = (EditText) findViewById(R.id.other_location_des_et);
        mConnecterNameEt = (EditText) findViewById(R.id.connecter_name_et);
        mConnecterNoteEt = (EditText) findViewById(R.id.connecter_note_et);
        mConnecterTelValueTv = (TextView) findViewById(R.id.connecter_tel_value_tv);
        mSpareNameEt = (EditText) findViewById(R.id.spare_name_et);
        mSpareNoteEt = (EditText) findViewById(R.id.spare_note_et);
        mSpareTelValueTv = (TextView) findViewById(R.id.spare_tel_value_tv);
        mConnecterTelValueTv.setOnClickListener(this);
        mSpareTelValueTv.setOnClickListener(this);
        mAddTakerRemarkEt = (EditText) findViewById(R.id.add_taker_remark_et);
        mAddTakerCommitTv = (TextView) findViewById(R.id.add_taker_commit_tv);
        mAddTakerCommitTv.setOnClickListener(this);
        mConnecterTelValueTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                copyTelephoneNum(getTextViewValue(mConnecterTelValueTv));
                ToastUtils.toast(mContext, String.format("%s%s%s", "已将", getTextViewValue(mConnecterTelValueTv), "复制"));
                return true;
            }
        });
        mSpareTelValueTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                copyTelephoneNum(getTextViewValue(mSpareTelValueTv));
                ToastUtils.toast(mContext, String.format("%s%s%s", "已将", getTextViewValue(mSpareTelValueTv), "复制"));
                return true;
            }
        });
        mCareInfoMoreSf = (SmartRefreshLayout) findViewById(R.id.care_info_more_sf);
        mCareInfoMoreSf.setEnableLoadMore(false);
        mCareInfoMoreSf.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initData();
            }
        });
        mRejectReasonEt = (EditText) findViewById(R.id.reject_reason_et);
        mRejectReasonLl = (LinearLayout) findViewById(R.id.reject_reason_ll);
    }


    @Override
    public void initData() {
        //获取托养人员的id
        if (getIntent() != null) {
            int id = getIntent().getIntExtra(CareTakerInfoActivity.CARE_TAKER_ID, 0);
            mPresenter.getCareInfoMore(getBaseBuilder().add("id", String.valueOf(id)).build(),
                    CareContract.CARE_INFO_MORE);
        }


    }


    @Override
    public void onSuccess(String tag, Object o) {
        mCareInfoMoreSf.finishRefresh();
        CareTakerInfoMoreBean careTakerInfoMoreBean = (CareTakerInfoMoreBean) o;
        if (careTakerInfoMoreBean != null) {
            infoBean = careTakerInfoMoreBean.getData();
            Hawk.put(HawkProperty.CARE_INFO_MORE_KEY, infoBean);
            if (infoBean != null) {
                mOtherLocationDesEt.setText(infoBean.getDescription());
                mConnecterNameEt.setText(infoBean.getTopContacts());
                mConnecterNoteEt.setText(infoBean.getTopRelation());
                mConnecterTelValueTv.setText(infoBean.getTopTel());
                mSpareNameEt.setText(infoBean.getContact());
                mSpareNoteEt.setText(infoBean.getRelation());
                if ("暂无".equals(infoBean.getPhone())) {
                    mSpareTelValueTv.setBackgroundResource(0);
                    mSpareTelValueTv.setTextColor(ContextCompat.getColor(mContext, R.color.text_default_color));
                } else {
                    mSpareTelValueTv.setBackgroundResource(R.drawable.bg_white_only_bottom_blue_shape_1px);
                    mSpareTelValueTv.setTextColor(ContextCompat.getColor(mContext, R.color.blue));
                }
                mSpareTelValueTv.setText(infoBean.getPhone());
                mAddTakerRemarkEt.setText(infoBean.getRemark());
                int commitable = infoBean.getApprovalStatus();
                initCommitBtStatus(commitable);

            }
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.add_taker_commit_tv) {//申请修改托养信息
            new AlertDialog.Builder(mContext).setTitle("提示")
                    .setMessage(R.string.modify_info_notice)
                    .setPositiveButton("申请", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //跳转到添加托养人的界面
                            startActivityForResult(new Intent(mContext, AddCareTakerActivity.class).putExtra(AddCareTakerActivity.CARE_TAKER_TYPE, 1), EDIT_CARE_TAKER_INFO);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).create().show();
        } else if (id == R.id.spare_tel_value_tv) {
            makeAPhoneCall(getTextViewValue(mSpareTelValueTv));
        } else if (id == R.id.connecter_tel_value_tv) {
            if (!"暂无".equals(getTextViewValue(mConnecterTelValueTv))) {
                makeAPhoneCall(getTextViewValue(mConnecterTelValueTv));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (EDIT_CARE_TAKER_INFO == resultCode) {
            initCommitBtStatus(0);
        }
    }

    /**
     * 初始化提交按钮
     *
     * @param status //审批状态（0：待审核；1：通过；2：驳回;3：未申请修改）
     */
    private void initCommitBtStatus(int status) {
        mRejectReasonLl.setVisibility(View.GONE);
        switch (status) {
            case 0:
                mAddTakerCommitTv.setText("待审核");
                mAddTakerCommitTv.setBackgroundResource(R.drawable.sp_gray_square_button);
                mAddTakerCommitTv.setTextColor(ContextCompat.getColor(mContext, R.color.text_default_color));
                mAddTakerCommitTv.setClickable(false);
                mAddTakerCommitTv.setFocusable(false);
                break;
            case 1:
                mAddTakerCommitTv.setText("申请修改");
                mAddTakerCommitTv.setBackgroundResource(R.drawable.sp_blue_square_button);
                mAddTakerCommitTv.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                mAddTakerCommitTv.setClickable(true);
                mAddTakerCommitTv.setFocusable(true);
                break;
            case 2:
//                mRejectReasonLl.setVisibility(View.VISIBLE);
//                mRejectReasonEt.setText(infoBean.);
                mAddTakerCommitTv.setText("重新申请");
                mAddTakerCommitTv.setBackgroundResource(R.drawable.sp_blue_square_button);
                mAddTakerCommitTv.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                mAddTakerCommitTv.setClickable(true);
                mAddTakerCommitTv.setFocusable(true);
                break;
            case 3:
                mAddTakerCommitTv.setText("申请修改");
                mAddTakerCommitTv.setBackgroundResource(R.drawable.sp_blue_square_button);
                mAddTakerCommitTv.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                mAddTakerCommitTv.setClickable(true);
                mAddTakerCommitTv.setFocusable(true);
                break;
            default:
                break;
        }


    }

}
