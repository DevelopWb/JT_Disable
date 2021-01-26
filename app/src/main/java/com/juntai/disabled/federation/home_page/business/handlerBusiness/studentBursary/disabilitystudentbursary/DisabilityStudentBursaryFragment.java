package com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary.disabilitystudentbursary;
import com.juntai.disabled.federation.bean.MultipleItem;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  残疾人大学生助学金申请
 * @CreateDate: 2021/1/26 10:17
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/1/26 10:17
 */
public class DisabilityStudentBursaryFragment extends BaseDisabilityStudentBursaryFragment {

    @Override
    protected List<MultipleItem> getAdapterData() {
        return mPresenter.getDisabilityStudentBursaryAdapterData();
    }
}
