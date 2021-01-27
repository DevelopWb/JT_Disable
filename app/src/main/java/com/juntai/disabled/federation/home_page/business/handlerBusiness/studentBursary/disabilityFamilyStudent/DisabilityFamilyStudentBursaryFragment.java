package com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary.disabilityFamilyStudent;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary.disabilitystudentbursary.BaseDisabilityStudentBursaryFragment;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  重度残疾人家庭大学生助学金申请
 * @CreateDate: 2021/1/26 10:17
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/1/26 10:17
 */
public class DisabilityFamilyStudentBursaryFragment extends BaseDisabilityFamilyStudentBursaryFragment {

    @Override
    protected List<MultipleItem> getAdapterData() {
        return mPresenter.getDisabilityFamilyStudentBursaryAdapterData();
    }
}
