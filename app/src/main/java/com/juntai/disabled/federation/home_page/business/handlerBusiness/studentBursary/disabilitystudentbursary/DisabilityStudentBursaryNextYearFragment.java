package com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary.disabilitystudentbursary;
import com.juntai.disabled.federation.bean.MultipleItem;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  残疾人大学生非首次申请助学金
 * @CreateDate: 2021/1/26 10:17
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/1/26 10:17
 */
public class DisabilityStudentBursaryNextYearFragment extends BaseDisabilityStudentBursaryFragment {

    @Override
    protected List<MultipleItem> getAdapterData() {
        return mPresenter.getDisabilityStudentBursaryNextYearAdapterData(null);
    }
    @Override
    protected String getFragmentTag() {
        return STUDENT_BURSARY_SECOND;
    }
}
