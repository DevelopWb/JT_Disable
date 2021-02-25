package com.juntai.disabled.federation.home_page.business.handlerBusiness.disabilityChildRecovery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.AppHttpPath;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BaseBusinessActivity;

import okhttp3.MultipartBody;

/**
 * @aouther tobato
 * @description 描述  智力残疾儿童康复
 * @date 2021/1/27 17:35
 */
public abstract class BaseRecoveryActivity extends BaseBusinessActivity {
    private TextView mCommitBusinessTv;
    public static String RECOVERY_NAME = "recoveryname";

    public abstract   int getChildId();

    @Override
    public void initData() {

    }

    @Override
    protected String getTitleName() {
        if (getIntent() != null) {
            return getIntent().getStringExtra(RECOVERY_NAME);
        }
        return "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View getFootView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.footview_commit, null);
        mCommitBusinessTv = view.findViewById(R.id.commit_business_form_tv);
        mCommitBusinessTv.setOnClickListener(this);
        return view;
    }

    @Override
    protected View getHeadView() {
        return null;
    }


    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);
        if (AppHttpPath.IQ_CHILD_RECOVERY.equals(tag)||AppHttpPath.LONELY_CHILD_RECOVERY.equals(tag)
                ||AppHttpPath.DEAF_CHILD_RECOVERY.equals(tag)||AppHttpPath.CEREBRAL_PALSY_RECOVERY.equals(tag)) {
            BaseResult baseResult = (BaseResult) o;
            ToastUtils.toast(mContext,baseResult.message);
            finish();
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.commit_business_form_tv:
                MultipartBody.Builder builder = getBuilderOfAdapterData();
                if (builder == null) {
                    return;
                }
                switch (getChildId()) {
                    case 0:
                        //智力残疾
                        mPresenter.addDisabledChildrenIntellectual(builder.build(), AppHttpPath.IQ_CHILD_RECOVERY);
                        break;
                    case 1:
                        //孤独
                        mPresenter.addDisabledChildrenAutism(builder.build(), AppHttpPath.LONELY_CHILD_RECOVERY);
                        break;
                    case 2:
                        //聋
                        mPresenter.addDisabledChildrenDeaf(builder.build(), AppHttpPath.DEAF_CHILD_RECOVERY);
                        break;
                    case 3:
                        //脑瘫
                        mPresenter.addDisabledChildrenCerebralPalsy(builder.build(), AppHttpPath.CEREBRAL_PALSY_RECOVERY);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }
}
