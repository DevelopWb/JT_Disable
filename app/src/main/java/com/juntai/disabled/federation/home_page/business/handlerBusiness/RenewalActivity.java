package com.juntai.disabled.federation.home_page.business.handlerBusiness;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.juntai.disabled.basecomponent.utils.LogUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.home_page.business.BaseBusinessActivity;
import com.juntai.disabled.federation.home_page.business.BaseChildBusinessActivity;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述  期满换证
 * @date 2021/1/19 9:53
 */
public class RenewalActivity extends BaseChildBusinessActivity {

    @Override
    protected String getTitleName() {
        return BUSINESS_NAME_RENEWAL;
    }


}
