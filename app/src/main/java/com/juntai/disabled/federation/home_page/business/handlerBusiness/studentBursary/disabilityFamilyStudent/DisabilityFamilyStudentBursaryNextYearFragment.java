package com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary.disabilityFamilyStudent;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary.disabilitystudentbursary.BaseDisabilityStudentBursaryFragment;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  重度残疾人家庭大学生非首次申请助学金
 * @CreateDate: 2021/1/26 10:17
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/1/26 10:17
 */
public class DisabilityFamilyStudentBursaryNextYearFragment extends BaseDisabilityFamilyStudentBursaryFragment {

    @Override
    protected List<MultipleItem> getAdapterData() {
        return mPresenter.getDisabilityFamilyStudentBursaryNextYearAdapterData(null);
    }

    @Override
    protected String getFragmentTag() {
        return FAMILY_STUDENT_BURSARY_SECOND;
    }
}
