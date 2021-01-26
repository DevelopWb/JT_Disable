package com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary.disabilityFamilyStudent;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;

import com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary.BaseStudentBursaryActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary.disabilitystudentbursary.DisabilityStudentBursaryFragment;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary.disabilitystudentbursary.DisabilityStudentBursaryNextYearFragment;

/**
 * @aouther tobato
 * @description 描述  残疾人家庭的大学生助学金
 * @date 2021/1/26 16:55
 */
public class DisabilityFamilyStudentActivity extends BaseStudentBursaryActivity {


    @Override
    protected SparseArray<Fragment> getFragments() {
        SparseArray<Fragment> fragments = new SparseArray<>();
        fragments.append(0, new DisabilityFamilyStudentBursaryFragment());
        fragments.append(1, new DisabilityFamilyStudentBursaryNextYearFragment());
        return fragments;
    }

    @Override
    protected String getTitleName() {
        return "重度残疾人家庭大学生";
    }
}
