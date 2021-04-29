package com.juntai.disabled.federation.home_page.business.handlerBusiness.employmentRegist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.juntai.disabled.federation.AppHttpPath;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.bean.business.detail.EmploymentRegDetailBean;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BaseBusinessActivity;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述  就业登记业务详情
 * @date 2021/2/24 16:25
 */
public class EmploymentRegistDetailActivity extends BaseBusinessActivity {

    @Override
    public void initData() {

        mPresenter.getDisabledObtainEmploymentInfo(businessId, AppHttpPath.DISABLED_CARD_EMPLOYMENT_REGIST_DETAIL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getTitleName() {
        return "残疾人就业登记详情";
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
    protected List<MultipleItem> getAdapterData() {
        return null;
    }

    @Override
    public void onSuccess(String tag, Object o) {
        if (AppHttpPath.DISABLED_CARD_EMPLOYMENT_REGIST_DETAIL.equals(tag)) {
            EmploymentRegDetailBean regDetailBean = (EmploymentRegDetailBean) o;
            if (regDetailBean != null) {
                EmploymentRegDetailBean.DataBean dataBean = regDetailBean.getData();
                if (1==dataBean.getEstatus()&&checkStatusId==1) {
                    //未评价
                    showScoreDialog(businessItemId);
                }
                if (dataBean != null) {
                    adapter.setNewData(mPresenter.getEmploymentRegistAdapterData(dataBean));
                }
            }
        }else {
            super.onSuccess(tag,o);
        }

    }
}
