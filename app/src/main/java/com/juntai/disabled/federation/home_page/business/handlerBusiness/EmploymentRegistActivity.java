package com.juntai.disabled.federation.home_page.business.handlerBusiness;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.home_page.business.BaseBusinessActivity;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述 就业登记
 * @date 2021/1/21 13:47
 */
public class EmploymentRegistActivity extends BaseBusinessActivity {

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getTitleName() {
        return "残疾人就业登记";
    }

    @Override
    protected View getFootView() {
        return null;
    }

    @Override
    protected List<MultipleItem> getAdapterData() {
        return mPresenter.getEmploymentRegistAdapterData();
    }

    @Override
    protected ImageView getSignIv() {
        return null;
    }

    @Override
    public void onSuccess(String tag, Object o) {

    }
}
