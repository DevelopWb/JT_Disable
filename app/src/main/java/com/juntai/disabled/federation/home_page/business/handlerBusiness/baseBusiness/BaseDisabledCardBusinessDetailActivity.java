package com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.AppHttpPath;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.bean.business.detail.BusinessChildDetailBean;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @aouther tobato
 * @description 描述  期满换证 登记变更 遗失补办 迁入迁出 注销等业务
 * @date 2021/1/19 9:53
 */
public abstract class BaseDisabledCardBusinessDetailActivity extends BaseBusinessActivity {

    public static final String BUSINESS_NAME_RENEWAL = "残疾证期满换证详情";
    public static final String BUSINESS_NAME_LEVEL_CHANGE = "残疾证等级变更详情";
    public static final String BUSINESS_NAME_REISSUE = "残疾证遗失补办详情";
    public static final String BUSINESS_NAME_MOVE_IN = "残疾证迁入详情";
    public static final String BUSINESS_NAME_MOVE_OUT = "残疾证迁出详情";
    public static final String BUSINESS_NAME_LOGOUT = "残疾证注销详情";

    @Override
    public void initData() {
        switch (getTitleName()) {
            case BUSINESS_NAME_RENEWAL:
                mPresenter.getCertificatesExchangeInfo(businessId, BUSINESS_NAME_RENEWAL);
                break;
            case BUSINESS_NAME_LEVEL_CHANGE:
                mPresenter.getCertificatesChangeInfo(businessId, BUSINESS_NAME_LEVEL_CHANGE);
                break;
            case BUSINESS_NAME_REISSUE:
                mPresenter.getCertificatesReissueInfo(businessId, BUSINESS_NAME_REISSUE);
                break;
            case BUSINESS_NAME_MOVE_IN:
                mPresenter.getCertificatesMoveinInfo(businessId, BUSINESS_NAME_MOVE_IN);
                break;
            case BUSINESS_NAME_MOVE_OUT:
                mPresenter.getCertificatesMoveoutInfo(businessId, BUSINESS_NAME_MOVE_OUT);
                break;
            case BUSINESS_NAME_LOGOUT:
                mPresenter.getCertificatesCancelInfo(businessId, BUSINESS_NAME_LOGOUT);
                break;
            default:
                break;
        }
    }

    @Override
    protected View getHeadView() {
        return null;
    }

    @Override
    protected View getFootView() {
        return null;
    }

    @Override
    protected List<MultipleItem> getAdapterData() {
        return null ;
    }

    @Override
    public void onSuccess(String tag, Object o) {
        BusinessChildDetailBean detailBean = (BusinessChildDetailBean) o;
        if (detailBean != null) {
            BusinessChildDetailBean.DataBean dataBean =  detailBean.getData();
            if (dataBean != null) {
                adapter.setNewData(mPresenter.getBaseChildAdapterData(dataBean));
            }
        }
    }


    @Override
    protected ImageView getSignIv() {
        return null;
    }
}
