package com.juntai.disabled.federation.home_page.business.handlerBusiness.disabilityChildRecovery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BaseBusinessActivity;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述  智力残疾儿童康复
 * @date 2021/1/27 17:35
 */
public class MoronRecoveryActivity extends BaseRecoveryActivity {


    @Override
    protected List<MultipleItem> getAdapterData() {
        return mPresenter.getMoronRecoveryData(null);
    }

    @Override
    public int getChildId() {
        return 0;
    }
}
