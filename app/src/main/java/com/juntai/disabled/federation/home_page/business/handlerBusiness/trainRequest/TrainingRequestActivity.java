package com.juntai.disabled.federation.home_page.business.handlerBusiness.trainRequest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.juntai.disabled.federation.AppHttpPath;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BaseBusinessActivity;

import java.util.List;

import okhttp3.MultipartBody;

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
    protected void commit() {
        MultipartBody.Builder builder = getBuilderOfAdapterData();
        if (builder == null) {
            return;
        }
        String hukouInfo = getHukouInfoOrFamilyAddrAdapterData();
        builder.addFormDataPart("residenceAddress",hukouInfo);
        mPresenter.addTrain(builder.build(), AppHttpPath.REQUEST_TRAIN);
    }

    @Override
    protected List<MultipleItem> getAdapterData() {
        return mPresenter.getTrainingRequestAdapterData(null);
    }

    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);
    }
}
