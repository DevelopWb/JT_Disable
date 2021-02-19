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
 * @description 描述  残疾培训申请
 * @date 2021/2/18 15:18
 */
public class TrainingRequestActivity extends BaseBusinessActivity {

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getTitleName() {
        return "残疾培训申请";
    }


    @Override
    protected View getFootView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.footview_commit, null);
        TextView mCommitBusinessTv = view.findViewById(R.id.commit_business_form_tv);
        mCommitBusinessTv.setOnClickListener(this);
        return view;
    }

    @Override
    protected View getHeadView() {
        return null;
    }

    @Override
    protected List<MultipleItem> getAdapterData() {
        return mPresenter.getTrainingRequestAdapterData();
    }

    @Override
    protected ImageView getSignIv() {
        return null;
    }

    @Override
    public void onSuccess(String tag, Object o) {

    }
}
