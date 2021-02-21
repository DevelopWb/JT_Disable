package com.juntai.disabled.federation.home_page.business.my_business;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.business.MyBusinessBean;

/**
 * Describe:任务上报列表
 * Create by zhangzhenlong
 * 2020-5-16
 * email:954101549@qq.com
 */
public class MyBusinessAdapter extends BaseQuickAdapter<MyBusinessBean.DataBean.DatasBean, BaseViewHolder> {


    private boolean  isEdit;

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
        notifyDataSetChanged();
    }

    public MyBusinessAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyBusinessBean.DataBean.DatasBean item) {
        if (isEdit){
            //编辑状态
            helper.setChecked(R.id.item_check,item.isChecked());
            helper.getView(R.id.item_check).setVisibility(View.VISIBLE);
            helper.getView(R.id.item_check).setOnClickListener(v -> {
                item.setChecked(((CheckBox)v).isChecked());
            });
        }else {
            helper.getView(R.id.item_check).setVisibility(View.GONE);
            item.setChecked(false);
        }
        helper.setText(R.id.item_name,item.getMatterName());
        helper.setText(R.id.item_content,item.getGmtCreate());
        helper.setVisible(R.id.item_status,true);
        setStatus(helper.getView(R.id.item_status),item.getStatusX());
    }

    //审批状态（0：审核中）（1：审核通过）（2：审核失败）
    public void setStatus(TextView textView, int status){
        switch (status){
            case 1:
                textView.setTextColor(mContext.getResources().getColor(R.color.success_color));
                textView.setText("审核通过");
                break;
            case 2:
                textView.setTextColor(mContext.getResources().getColor(R.color.fail_color));
                textView.setText("审核拒绝");
                break;
            default:
                textView.setTextColor(mContext.getResources().getColor(R.color.orange));
                textView.setText("审核中");
                break;
        }
    }
}
