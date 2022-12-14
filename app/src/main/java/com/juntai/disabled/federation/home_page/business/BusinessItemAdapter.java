package com.juntai.disabled.federation.home_page.business;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.MyApp;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.bean.MultipleItem;
import com.juntai.disabled.federation.bean.business.AllBusinessBean;
import com.juntai.disabled.federation.entrance.LoginActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.SuggestionActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.assistTool.AssistToolsRequestActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.normalbusiness.CardLevelChangeActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.normalbusiness.CardLogoutActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.normalbusiness.CardMoveInActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.normalbusiness.CardMoveOutActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.normalbusiness.CardReissueActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.employmentRegist.EmploymentRegistActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.handlercard.HandlerCardActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.homecare.HomeCareActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.normalbusiness.RenewalActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.trainRequest.TrainingRequestActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.disabilityChildRecovery.DisabilityChildRecoveryActivity;
import com.juntai.disabled.federation.home_page.business.handlerBusiness.studentBursary.StudentBursaryActivity;
import com.juntai.disabled.federation.home_page.call_to_police.CallToPoliceActivity;
import com.juntai.disabled.federation.home_page.call_to_police.VerifiedActivity;
import com.juntai.disabled.federation.utils.UserInfoManager;

import java.util.List;

/**
 * @Author: tobato
 * @Description: ????????????  ???????????????????????????
 * @CreateDate: 2020/5/20 9:47
 * @UpdateUser: ?????????
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

                        if (!MyApp.isLogin()) {
                            MyApp.goLogin();
                            return;
                        }
                        //????????????????????????
                        //?????????????????????0??????????????????1????????????????????????2?????????????????????3??????????????????
                        int status = UserInfoManager.getRealNameStatus();
                        if (2 != status) {
                            new AlertDialog.Builder(mContext)
                                    .setMessage(R.string.auth_msg)
                                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).setPositiveButton(R.string.to_auth, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mContext.startActivity(new Intent(mContext, VerifiedActivity.class).putExtra(VerifiedActivity.VERIFIED_STATUS, status));
                                }
                            }).show();
                            return;
                        }
                        AllBusinessBean.DataBean.WorkMatterListBean bean =
                                (AllBusinessBean.DataBean.WorkMatterListBean) adapter.getData().get(position);
                        switch (bean.getId()) {
                            case 1:
                                mContext.startActivity(new Intent(mContext, HandlerCardActivity.class));
                                break;
                            case 2:
                                //????????????
                                mContext.startActivity(new Intent(mContext, RenewalActivity.class));
                                break;
                            case 3:
                                //????????????
                                mContext.startActivity(new Intent(mContext, CardLevelChangeActivity.class));
                                break;
                            case 4:
                                //????????????
                                mContext.startActivity(new Intent(mContext, CardReissueActivity.class));
                                break;
                            case 5:
                                //????????????
                                mContext.startActivity(new Intent(mContext, CardMoveInActivity.class));
                                break;
                            case 6:
                                //????????????
                                mContext.startActivity(new Intent(mContext, CardMoveOutActivity.class));
                                break;
                            case 7:
                                //????????????
                                mContext.startActivity(new Intent(mContext, CardLogoutActivity.class));
                                break;
                            case 8:
                                //????????????
                                mContext.startActivity(new Intent(mContext, EmploymentRegistActivity.class));
                                break;
                            case 9:
                                //?????????????????????????????????
                                mContext.startActivity(new Intent(mContext, DisabilityChildRecoveryActivity.class)
                                        .putExtra(DisabilityChildRecoveryActivity.BUSINESS_ID, bean.getId()));
                                break;
                            case 10:
                                //?????????????????????
                                mContext.startActivity(new Intent(mContext, StudentBursaryActivity.class)
                                        .putExtra(DisabilityChildRecoveryActivity.BUSINESS_ID, bean.getId()));
                                break;
                            case 12:
                                //??????????????????
                                mContext.startActivity(new Intent(mContext, AssistToolsRequestActivity.class));
                                break;
                            case 13:
                                //?????????????????????
                                mContext.startActivity(new Intent(mContext, TrainingRequestActivity.class));
                                break;
                            case 14:
                                //????????????
                                mContext.startActivity(new Intent(mContext, HomeCareActivity.class));
                                break;
                            case 15:
                                //????????????
                                mContext.startActivity(new Intent(mContext, SuggestionActivity.class));
                                break;
                            default:
                                ToastUtils.toast(mContext,R.string.not_open_notice);
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
