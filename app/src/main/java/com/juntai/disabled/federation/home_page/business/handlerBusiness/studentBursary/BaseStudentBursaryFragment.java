package com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness.BaseBusinessFragment;
import com.juntai.disabled.federation.utils.StringTools;

/**
 * @Author: tobato
 * @Description: 作用描述  残疾人大学生助学金申请
 * @CreateDate: 2021/1/26 10:17
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/1/26 10:17
 */
public abstract class BaseStudentBursaryFragment extends BaseBusinessFragment {
    /**
     * 获取声明内容
     * @return
     */
    protected abstract String getNotice();


    @Override
    protected View getFootView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.footview_disability_student_bursary, null);
        TextView mCommitBusinessTv = view.findViewById(R.id.commit_business_form_tv);
        TextView mBursaryNoticeTv = view.findViewById(R.id.bursary_notice_tv);
        mCommitBusinessTv.setOnClickListener(this);
        mBursaryNoticeTv.setOnClickListener(this);
        StringTools.setTextPartColor(mBursaryNoticeTv, getNotice(), getNotice().lastIndexOf("《"), getNotice().lastIndexOf("》")+1,
                "#FB7D06");
        return view;
    }



    @Override
    protected View getHeadView() {
        return null;
    }


    @Override
    protected ImageView getSignIv() {
        return null;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bursary_notice_tv:
                ToastUtils.toast(mContext,"跳转到申请表");
                break;
            case R.id.commit_business_form_tv:
                ToastUtils.toast(mContext,"提交");
                break;
            default:
                break;
        }
    }
}
