package com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary.disabilitystudentbursary;

import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.View;

import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BusinessContract;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary.BaseStudentBursaryActivity;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述  残疾人大学生助学金
 * @date 2021/1/26 9:45
 */
public class DisabilityStudentBursaryActivity extends BaseStudentBursaryActivity implements BusinessContract.BaseIBusinessView {


    @Override
    protected SparseArray<Fragment> getFragments() {
        SparseArray<Fragment> fragments = new SparseArray<>();
        fragments.append(0, new DisabilityStudentBursaryFragment());
        fragments.append(1, new DisabilityStudentBursaryNextYearFragment());
        return fragments;
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
    protected List<MultipleItem> getAdapterData() {
        return null;
    }
}
