package com.juntai.disabled.federation.home_page.business.handlerBusiness.disabilityChildRecovery.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.AppHttpPath;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.bean.business.detail.RecoveryDetailBean;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BaseBusinessActivity;

import java.util.List;

import okhttp3.MultipartBody;

/**
 * @aouther tobato
 * @description 描述  智力残疾儿童康复
 * @date 2021/1/27 17:35
 */
public abstract class BaseRecoveryDetailActivity extends BaseBusinessActivity {
    public static String RECOVERY_NAME = "recoveryname";
    public abstract   int getChildId();

    @Override
    public void initData() {

    }
    @Override
    protected List<MultipleItem> getAdapterData() {
        return null;
    }

    @Override
    protected String getTitleName() {
        if (getIntent() != null) {
            return getIntent().getStringExtra(RECOVERY_NAME)+"详情";
        }
        return "";
    }

    @Override
    protected void commit() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View getFootView() {
      return null;
    }

    @Override
    protected View getHeadView() {
        return null;
    }


    @Override
    public void onSuccess(String tag, Object o) {
        RecoveryDetailBean recoveryDetailBean = (RecoveryDetailBean) o;
        if (recoveryDetailBean != null) {
            RecoveryDetailBean.DataBean dataBean = recoveryDetailBean.getData();
            switch (getChildId()) {
                case 0:
                    adapter.setNewData(mPresenter.getMoronRecoveryData(dataBean));
                    break;
                case 1:
                    adapter.setNewData(mPresenter.getLonelyChildRecoveryData(dataBean));
                    break;
                case 2:
                    adapter.setNewData(mPresenter.getDeafDumbChildRecoveryData(dataBean));
                    break;
                case 3:
                    adapter.setNewData(mPresenter.getBrainPalsyRecoveryData(dataBean));
                    break;
                default:
                    break;
            }

        }
    }
}
