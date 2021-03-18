package com.juntai.disabled.federation.home_page.takeInfos;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.base.BaseAppFragment;
import com.juntai.disabled.federation.base.search.BaseSearchFragment;
import com.juntai.disabled.federation.base.search.SearchPresent;

/**
 * @Author: tobato
 * @Description: 作用描述  无障碍采集
 * @CreateDate: 2021/3/17 16:46
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/3/17 16:46
 */
public class DisabledPeopleInfoTakeFragment extends BaseSearchFragment implements TakeInfoContract.TakeInfoView {


    @Override
    protected BaseQuickAdapter getSearchResultAdapter() {
        DisabledPeoplesAdapter  adapter = new DisabledPeoplesAdapter(R.layout.disabled_peoples_item);
        adapter.setNewData(getBaseActivity().getTestData());
        return adapter;
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onSuccess(String tag, Object o) {

    }

    @Override
    protected void initData() {
        mFilterIv.setVisibility(View.GONE);
    }
}
