package com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary.detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.juntai.disabled.federation.AppHttpPath;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.bean.business.detail.StudentBursaryDetailBean;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BaseBusinessActivity;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述  重残家庭学生助学金申请详情
 * @date 2021/2/28 14:52
 */
public class DisabledFamilyStudentBursaryDetailActivity extends BaseBusinessActivity {

    @Override
    public void initData() {
        mPresenter.getDisabledStudentFamilyGrantInfo(businessId, AppHttpPath.DISABLED_FAMILY_CHILD_BURSARY_DETAIL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getTitleName() {
        return "重残家庭学生助学金申请详情";
    }

    @Override
    protected View getFootView() {
        return null;
    }

    @Override
    protected View getHeadView() {
        return null;
    }

    @Override
    protected void commit() {

    }

    @Override
    protected List<MultipleItem> getAdapterData() {
        return null;
    }

    @Override
    public void onSuccess(String tag, Object o) {
        if (AppHttpPath.DISABLED_FAMILY_CHILD_BURSARY_DETAIL.equals(tag)) {
            StudentBursaryDetailBean bursaryDetailBean = (StudentBursaryDetailBean) o;
            if (bursaryDetailBean != null) {
                StudentBursaryDetailBean.DataBean dataBean = bursaryDetailBean.getData();
                if (1==dataBean.getEstatus()&&checkStatusId==1) {
                    //未评价
                    showScoreDialog(businessItemId);
                }
                int  whichYear = dataBean.getIsFirst();
                if (0==whichYear) {
                    //第一年
                    adapter.setNewData(mPresenter.getDisabilityFamilyStudentBursaryAdapterData(dataBean));
                }else {
                    adapter.setNewData(mPresenter.getDisabilityFamilyStudentBursaryNextYearAdapterData(dataBean));
                }

            }
        }else {
            super.onSuccess(tag,o);
        }

    }
}
