package com.juntai.disabled.federation.home_page.business.handlerBusiness;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.home_page.business.BaseBusinessActivity;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述 就业登记
 * @date 2021/1/21 13:47
 */
public class EmploymentRegistActivity extends BaseBusinessActivity {

    private TextView mCommitBusinessTv;

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
        View view = LayoutInflater.from(mContext).inflate(R.layout.footview_commit, null);
        mCommitBusinessTv = view.findViewById(R.id.commit_business_form_tv);
        mCommitBusinessTv.setOnClickListener(this);
        return view;
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


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.commit_business_form_tv:
                break;
            default:
                break;
        }
    }
}
