package com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.juntai.disabled.basecomponent.utils.LogUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.MultipleItem;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述  期满换证 登记变更 遗失补办 迁入迁出 注销等业务
 * @date 2021/1/19 9:53
 */
public abstract class BaseChildBusinessActivity extends BaseBusinessActivity {

    private ImageView mGuardianNameSignIv;
    public static final  String BUSINESS_NAME_RENEWAL = "残疾证期满换证";
    public static final  String BUSINESS_NAME_LEVEL_CHANGE = "残疾证等级变更";
    public static final  String BUSINESS_NAME_REISSUE = "残疾证遗失补办";
    public static final  String BUSINESS_NAME_MOVE_IN = "残疾证迁入";
    public static final  String BUSINESS_NAME_MOVE_OUT= "残疾证迁出";
    public static final  String BUSINESS_NAME_LOGOUT = "残疾证注销";

    @Override
    public void initData() {

    }

    @Override
    protected View getHeadView() {
        return null;
    }

    @Override
    protected View getFootView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.footview_renewal, null);
        TextView mCommitBusinessTv = view.findViewById(R.id.commit_business_form_tv);
        mGuardianNameSignIv = view.findViewById(R.id.guardian__name_sign_iv);
        mCommitBusinessTv.setOnClickListener(this);
        mGuardianNameSignIv.setOnClickListener(this);
        return view;
    }

    @Override
    protected List<MultipleItem> getAdapterData() {
        return mPresenter.getBaseChildAdapterData();
    }

    @Override
    public void onSuccess(String tag, Object o) {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.commit_business_form_tv:
                StringBuilder sb = getStringBuilderOfAdapterData();
                if (sb == null) {
                    return;
                }
                if (TextUtils.isEmpty(getSignPath())) {
                    ToastUtils.toast(mContext,"请签名");
                    return;
                }

                LogUtil.e(sb.toString());
                ToastUtils.toast(mContext, sb.toString());
                switch (getTitleName()) {
                    case BUSINESS_NAME_RENEWAL:
                        break;
                    case BUSINESS_NAME_LEVEL_CHANGE:
                        break;
                    case BUSINESS_NAME_REISSUE:
                        break;
                    case BUSINESS_NAME_MOVE_IN:
                        break;
                    case BUSINESS_NAME_MOVE_OUT:
                        break;
                    case BUSINESS_NAME_LOGOUT:
                        break;
                    default:
                        break;
                }
                break;
            case R.id.guardian__name_sign_iv:
                showSignatureView();
                break;
            default:
                break;
        }
    }



    @Override
    protected ImageView getSignIv() {
        return mGuardianNameSignIv;
    }
}
