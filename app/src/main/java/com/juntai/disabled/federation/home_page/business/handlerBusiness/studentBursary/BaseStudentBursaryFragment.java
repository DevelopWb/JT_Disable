package com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.AppHttpPath;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BaseBusinessFragment;
import com.juntai.disabled.federation.utils.StringTools;

import java.util.List;

import okhttp3.MultipartBody;

/**
 * @Author: tobato
 * @Description: 作用描述  残疾人大学生助学金申请
 * @CreateDate: 2021/1/26 10:17
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/1/26 10:17
 */
public abstract class BaseStudentBursaryFragment extends BaseBusinessFragment {

    private CheckBox checkBox;

    /**
     * 获取声明内容
     * @return
     */
    protected abstract String getNotice();
    protected abstract String getFragmentTag();


    @Override
    protected View getFootView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.footview_disability_student_bursary, null);
        TextView mCommitBusinessTv = view.findViewById(R.id.commit_business_form_tv);
        TextView mBursaryNoticeTv = view.findViewById(R.id.bursary_notice_tv);
        checkBox = view.findViewById(R.id.agree_cb);

        mCommitBusinessTv.setOnClickListener(this);
        mBursaryNoticeTv.setText(getNotice());
        return view;
    }



    @Override
    protected View getHeadView() {
        return null;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit_business_form_tv:
                //提交
                MultipartBody.Builder builder = getBuilderOfAdapterData();
                if (builder == null) {
                    return;
                }
                if (!checkBox.isChecked()) {
                    ToastUtils.toast(mContext,"请接受诚信声明后再提交");
                    return;
                }
                switch (getFragmentTag()) {
                    case STUDENT_BURSARY_FIRST:
                        builder.addFormDataPart("isFirst",String.valueOf(0));
                        mPresenter.addDisabledStudentGrant(builder.build(), AppHttpPath.DISABLED_CHILD_BURSARY);
                        break;
                    case STUDENT_BURSARY_SECOND:
                        builder.addFormDataPart("isFirst",String.valueOf(1));
                        mPresenter.addDisabledStudentGrant(builder.build(), AppHttpPath.DISABLED_CHILD_BURSARY);
                        break;
                    case FAMILY_STUDENT_BURSARY_FIRST:
                        builder.addFormDataPart("isFirst",String.valueOf(0));
                        mPresenter.addDisabledStudentFamilyGrant(builder.build(), AppHttpPath.DISABLED_FAMILY_CHILD_BURSARY);
                        break;
                    case FAMILY_STUDENT_BURSARY_SECOND:
                        builder.addFormDataPart("isFirst",String.valueOf(1));
                        mPresenter.addDisabledStudentFamilyGrant(builder.build(), AppHttpPath.DISABLED_FAMILY_CHILD_BURSARY);
                        break;
                    default:
                        break;
                }


                break;
            default:
                break;
        }
    }


}
