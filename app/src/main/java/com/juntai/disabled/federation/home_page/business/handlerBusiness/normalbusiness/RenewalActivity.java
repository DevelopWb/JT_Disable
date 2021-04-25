package com.juntai.disabled.federation.home_page.business.handlerBusiness.normalbusiness;

import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BaseDisabledCardBusinessActivity;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述  期满换证
 * @date 2021/1/19 9:53
 */
public class RenewalActivity extends BaseDisabledCardBusinessActivity {

    @Override
    protected String getTitleName() {
        return BUSINESS_NAME_RENEWAL;
    }

    @Override
    protected List<MultipleItem> getAdapterData() {
        return mPresenter.getRenewalAdapterData(null);
    }


}
