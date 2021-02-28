package com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary.disabilityFamilyStudent;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BaseBusinessFragment;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary.BaseStudentBursaryFragment;
import com.juntai.disabled.federation.utils.StringTools;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  残疾人大学生助学金申请
 * @CreateDate: 2021/1/26 10:17
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/1/26 10:17
 */
public  class BaseDisabilityFamilyStudentBursaryFragment extends BaseStudentBursaryFragment {

    @Override
    protected String getNotice() {
        return getString(R.string.family_student_bursary_notice);
    }

    @Override
    protected String getFragmentTag() {
        return null;
    }

    @Override
    protected List<MultipleItem> getAdapterData() {
        return null;
    }



}
