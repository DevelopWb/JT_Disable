package com.juntai.disabled.federation.home_page.business.handlerBusiness.assistTool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.AppHttpPath;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BaseBusinessActivity;

import java.util.List;

import okhttp3.MultipartBody;

/**
 * @aouther tobato
 * @description 描述  辅助用品申领
 * @date 2021/2/9 9:31
 */
public class AssistToolsRequestActivity extends BaseBusinessActivity {
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
        return "残疾辅助用品用具申请";
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
    protected void commit() {
        MultipartBody.Builder builder = getBuilderOfAdapterData();
        if (builder == null) {
            return;
        }
        mPresenter.addAIDS(builder.build(),AppHttpPath.REQUEST_AIDS);
    }

    @Override
    protected List<MultipleItem> getAdapterData() {
        return mPresenter.getAssistToolAdapterData(null);
    }


    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);

    }

}
