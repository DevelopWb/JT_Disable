package com.juntai.disabled.federation.home_page.business.handlerBusiness.trainRequest;

import android.os.Bundle;
import android.view.View;

import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.bean.business.detail.TrainRequestDetailBean;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BaseBusinessActivity;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述  培训申请详情
 * @date 2021/2/27 15:40
 */
public class TrainRequestDetailActivity extends BaseBusinessActivity {

    @Override
    public void initData() {

        mPresenter.getTrainInfo(businessId, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getTitleName() {
        return "残疾培训申请详情";
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
    protected void commit() {

    }

    @Override
    public void onSuccess(String tag, Object o) {
        TrainRequestDetailBean requestDetailBean = (TrainRequestDetailBean) o;
        if (requestDetailBean != null) {
            TrainRequestDetailBean.DataBean dataBean = requestDetailBean.getData();
            if (dataBean != null) {
                adapter.setNewData(mPresenter.getTrainingRequestAdapterData(dataBean));
            }
        }
    }

    @Override
    protected List<MultipleItem> getAdapterData() {
        return null;
    }
}
