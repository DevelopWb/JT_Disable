package com.juntai.disabled.federation.home_page.business.handlerBusiness.assistTool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.juntai.disabled.federation.AppHttpPath;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.bean.business.detail.AssistToolDetailBean;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BaseBusinessActivity;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述  辅助用品申请详情
 * @date 2021/2/27 15:05
 */
public class AssistToolDetailActivity extends BaseBusinessActivity {

    @Override
    public void initData() {

        mPresenter.getAIDSInfo(businessId, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onSuccess(String tag, Object o) {
        AssistToolDetailBean detailBean = (AssistToolDetailBean) o;
        if (detailBean != null) {
            AssistToolDetailBean.DataBean dataBean = detailBean.getData();
            adapter.setNewData(mPresenter.getAssistToolAdapterData(dataBean));
        }
    }

    @Override
    protected String getTitleName() {
        return "残疾辅助用品用具申请详情";
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
    protected List<MultipleItem> getAdapterData() {
        return null;
    }
}
