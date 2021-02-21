package com.juntai.disabled.federation.home_page.business.handlerBusiness;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
 * @description 描述  居家托养
 * @date 2021/2/21 15:18
 */
public class HomeCareActivity extends BaseBusinessActivity {

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
        return "残疾人居家托养";
    }

    @Override
    protected View getFootView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.footview_commit, null);
        mCommitBusinessTv = view.findViewById(R.id.commit_business_form_tv);
        mCommitBusinessTv.setOnClickListener(this);
        return view;
    }

    @Override
    protected View getHeadView() {
        return null;
    }

    @Override
    protected List<MultipleItem> getAdapterData() {
        return mPresenter.getHomeCareAdapterData();
    }

    @Override
    protected ImageView getSignIv() {
        return null;
    }
}
