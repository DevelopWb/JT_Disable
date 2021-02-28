package com.juntai.disabled.federation.home_page.business.handlerBusiness.homecare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.bean.business.detail.HomeCareDetailBean;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BaseBusinessActivity;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述  居家托养详情
 * @date 2021/2/27 16:32
 */
public class HomeCareDetailActivity extends BaseBusinessActivity {

    @Override
    public void initData() {
        mPresenter.getHomCareInfo(businessId,null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getTitleName() {
        return "残疾人居家托养详情";
    }

    @Override
    protected View getFootView() {
        return null;
    }

    @Override
    public void onSuccess(String tag, Object o) {
        HomeCareDetailBean homeCareDetailBean = (HomeCareDetailBean) o;
        if (homeCareDetailBean != null) {
            HomeCareDetailBean.DataBean dataBean =   homeCareDetailBean.getData();
            adapter.setNewData(mPresenter.getHomeCareAdapterData(dataBean));
        }

    }

    @Override
    protected View getHeadView() {
        return null;
    }

    @Override
    protected void commit() {

    }

    @Override
    protected List<MultipleItem> getAdapterData() {
        return null;
    }
}
