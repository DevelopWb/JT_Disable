package com.juntai.disabled.federation.home_page.business;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.bean.business.AllBusinessBean;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.assistTool.AssistToolsRequestActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.CardLevelChangeActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.CardLogoutActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.CardMoveInActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.CardMoveOutActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.CardReissueActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.EmploymentRegistActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.HandlerCardActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.HomeCareActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.RenewalActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.train.TrainingRequestActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.disabilityChildRecovery.DisabilityChildRecoveryActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary.StudentBursaryActivity;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  业务办理条目适配器
 * @CreateDate: 2020/5/20 9:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/20 9:47
 */
public class BusinessItemAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public BusinessItemAdapter(List<MultipleItem> data) {
        super(data);
        addItemType(MultipleItem.ITEM_BUSINESS_TITILE_BIG, R.layout.item_layout_type_title_big);
        addItemType(MultipleItem.ITEM_BUSINESS_LIST, R.layout.item_layout_type_recyclerview);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {


        switch (item.getItemType()) {
            case MultipleItem.ITEM_BUSINESS_TITILE_BIG:
                helper.setText(R.id.item_business_big_title_tv, (String) item.getObject());
                break;
            case MultipleItem.ITEM_BUSINESS_LIST:
                RecyclerView recyclerView = helper.getView(R.id.item_normal_rv);
                BusinessItemChildAdapter adapter = new BusinessItemChildAdapter(R.layout.business_item);
                LinearLayoutManager manager = new GridLayoutManager(mContext, 4);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(manager);
                adapter.setNewData((List<AllBusinessBean.DataBean.WorkMatterListBean>) item.getObject());
                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        AllBusinessBean.DataBean.WorkMatterListBean bean =
                                (AllBusinessBean.DataBean.WorkMatterListBean) adapter.getData().get(position);
                        switch (bean.getId()) {
                            case 1:
                                mContext.startActivity(new Intent(mContext, HandlerCardActivity.class));
                                break;
                            case 2:
                                //期满换证
                                mContext.startActivity(new Intent(mContext, RenewalActivity.class));
                                break;
                            case 3:
                                //期满换证
                                mContext.startActivity(new Intent(mContext, CardLevelChangeActivity.class));
                                break;
                            case 4:
                                //期满换证
                                mContext.startActivity(new Intent(mContext, CardReissueActivity.class));
                                break;
                            case 5:
                                //期满换证
                                mContext.startActivity(new Intent(mContext, CardMoveInActivity.class));
                                break;
                            case 6:
                                //期满换证
                                mContext.startActivity(new Intent(mContext, CardMoveOutActivity.class));
                                break;
                            case 7:
                                //期满换证
                                mContext.startActivity(new Intent(mContext, CardLogoutActivity.class));
                                break;
                            case 8:
                                //就业登记
                                mContext.startActivity(new Intent(mContext, EmploymentRegistActivity.class));
                                break;
                            case 9:
                                //残疾儿童抢救性康复项目
                                mContext.startActivity(new Intent(mContext, DisabilityChildRecoveryActivity.class)
                                        .putExtra(DisabilityChildRecoveryActivity.BUSINESS_ID, bean.getId()));
                                break;
                            case 10:
                                //残疾学生助学金
                                mContext.startActivity(new Intent(mContext, StudentBursaryActivity.class)
                                        .putExtra(DisabilityChildRecoveryActivity.BUSINESS_ID, bean.getId()));
                                break;
                            case 12:
                                //辅助用品申请
                                mContext.startActivity(new Intent(mContext, AssistToolsRequestActivity.class));
                                break;
                            case 13:
                                //残疾人培训申请
                                mContext.startActivity(new Intent(mContext, TrainingRequestActivity.class));
                                break;
                            case 14:
                                //居家托养
                                mContext.startActivity(new Intent(mContext, HomeCareActivity.class));
                                break;
                            case 15:
                                //意见建议
                                mContext.startActivity(new Intent(mContext, AssistToolsRequestActivity.class));
                                break;
                            default:
                                break;
                        }
                    }
                });
                break;
            default:
                break;
        }
    }
}
