package com.juntai.disabled.federation.home_page.business.handlerBusiness.normalbusiness;

import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BaseDisabledCardBusinessActivity;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述  残疾证遗失补办
 * @date 2021/1/20 14:20
 */
public class CardReissueActivity extends BaseDisabledCardBusinessActivity {


    @Override
    protected String getTitleName() {
        return BUSINESS_NAME_REISSUE;
    }

    @Override
    protected List<MultipleItem> getAdapterData() {
        return mPresenter.getReissueAdapterData(null);
    }
}
