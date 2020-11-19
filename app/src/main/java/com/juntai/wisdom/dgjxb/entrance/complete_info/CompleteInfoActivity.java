package com.juntai.wisdom.dgjxb.entrance.complete_info;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.bigkoo.pickerview.view.OptionsPickerView;
import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.utils.PickerManager;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.dgjxb.MyApp;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.PoliceBranchBean;
import com.juntai.wisdom.dgjxb.bean.PolicePositionBean;
import com.juntai.wisdom.dgjxb.bean.UserBean;
import com.juntai.wisdom.dgjxb.bean.weather.PoliceGriddingBean;
import com.juntai.wisdom.dgjxb.entrance.regist.RegistContract;
import com.juntai.wisdom.dgjxb.entrance.regist.RegistPresent;
import com.juntai.wisdom.dgjxb.utils.StringTools;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;

/**
 * @description 完善用户信息
 * @aouther ZhangZhenlong
 * @date 2020-9-11
 */
public class CompleteInfoActivity extends BaseMvpActivity<RegistPresent> implements RegistContract.IRegistView,
        View.OnClickListener,
        AdapterView.OnItemClickListener {

    /**
     * 请确保填写信息真实有效，如若有误，则可能无法收到有效推送信息。
     */
    private TextView mVerifiedNoticeTv;
    /**
     * 请选择职务
     */
    private TextView mRegistWorkTv;
    /**
     * 请选择部门
     */
    private AutoCompleteTextView mRegistBranchTv;
    /**
     * 请选择二级部门
     */
    private TextView mRegistChildBranchTv;
    /**
     * 请选择责任网格
     */
    private TextView mRegistGriddingTv;
    /**
     * 提交
     */
    private TextView mOkTv;

    private OptionsPickerView optionsPickerViewPosition;
    private OptionsPickerView pickerViewChildDepart;
    private OptionsPickerView pickerViewGridding;

    private List<PoliceBranchBean.DataBean> departMents = new ArrayList<>();//部门列表

    private PoliceBranchBean.DataBean departMentBean;//选中的一级部门
    private PoliceBranchBean.DataBean.ChildDepartmentBean childDepartmentBean;//选中的二级部门
    private PoliceGriddingBean.DataBean policeGriddingBean;//选中的网格
    private PolicePositionBean.DataBean policePositionBean;//选中的职务

    private DataValueAdapter dataValueAdapter;

    @Override
    protected RegistPresent createPresenter() {
        return new RegistPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_complete_info;
    }

    @Override
    public void initView() {
        setTitleName("完善用户信息");
        mVerifiedNoticeTv = (TextView) findViewById(R.id.verified_notice_tv);
        mRegistWorkTv = (TextView) findViewById(R.id.regist_work_tv);
        mRegistWorkTv.setOnClickListener(this);
        mRegistBranchTv = (AutoCompleteTextView) findViewById(R.id.branch_complete_tv);
        mRegistChildBranchTv = (TextView) findViewById(R.id.regist_child_branch_tv);
        mRegistChildBranchTv.setOnClickListener(this);
        mRegistGriddingTv = (TextView) findViewById(R.id.regist_gridding_tv);
        mRegistGriddingTv.setOnClickListener(this);
        mOkTv = (TextView) findViewById(R.id.ok_tv);
        mOkTv.setOnClickListener(this);

        dataValueAdapter = new DataValueAdapter(mContext, departMents);
        mRegistBranchTv.setAdapter(dataValueAdapter);
        mRegistBranchTv.setOnItemClickListener(this);
    }

    @Override
    public void initData() {
        mPresenter.getPoliceBranch("", RegistContract.GET_POLICE_BRANCH);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            //选择职务
            case R.id.regist_work_tv:
                if (optionsPickerViewPosition != null && !optionsPickerViewPosition.isShowing()) {
                    optionsPickerViewPosition.show();
                } else {
                    mPresenter.getPolicePosition(RegistContract.GET_POLICE_POSITION);
                }
                break;
            case R.id.regist_child_branch_tv:
                //二级部门
                if (departMentBean == null){
                    ToastUtils.warning(mContext,"请先选择上级部门");
                    return;
                }
                if (pickerViewChildDepart != null && !pickerViewChildDepart.isShowing()) {
                    pickerViewChildDepart.show();
                }else {
                    ToastUtils.warning(mContext,"该部门下暂无二级部门");
                }
                break;
            case R.id.regist_gridding_tv:
                if (departMentBean == null){
                    ToastUtils.warning(mContext,"请先选择部门");
                    return;
                }
                //责任网格
                mPresenter.getPoliceGridding(departMentBean.getDepartmentId(), RegistContract.GET_POLICE_GRIDDING);
                break;
            case R.id.ok_tv:
                if (MyApp.isFastClick()) {
                    ToastUtils.warning(mContext,"点击过于频繁");
                    return;
                }
                if (departMentBean == null || !StringTools.isStringValueOk(getTextViewValue(mRegistBranchTv))) {
                    ToastUtils.warning(mContext, "请选择部门");
                    return;
                }

                if (policePositionBean == null || !StringTools.isStringValueOk(getTextViewValue(mRegistWorkTv))) {
                    ToastUtils.warning(mContext, "请选择职务");
                    return;
                }
                FormBody.Builder builder = mPresenter.getBaseFormBodyBuilder();
                builder.add("provinceCode", departMentBean.getProvinceCode())
                        .add("cityCode", departMentBean.getCityCode())
                        .add("areaCode", departMentBean.getAreaCode())
                        .add("streetCode", departMentBean.getStreetCode())
                        .add("departmentId", departMentBean.getDepartmentId()+"")
                        .add("departmentBranchId", childDepartmentBean == null? "" : childDepartmentBean.getId()+"")
                        .add("postId", policePositionBean.getId()+"")
                        .add("grid", policeGriddingBean == null? "" : policePositionBean.getId()+"");
                mPresenter.addUserInfo(RegistContract.ADD_USER_INFO, builder.build());
                break;
        }
    }

    @Override
    public void updateSendCheckCodeViewStatus(long second) {
    }

    @Override
    public void checkFormatError(String error) {
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case RegistContract.GET_POLICE_POSITION://职务
                PolicePositionBean positionBean = (PolicePositionBean) o;
                if (positionBean != null) {
                    List<PolicePositionBean.DataBean> arrays = positionBean.getData();
                    if (arrays != null) {
                        optionsPickerViewPosition = PickerManager.getInstance().getOptionPicker(this, "选择职务", arrays, new PickerManager.OnOptionPickerSelectedListener() {
                            @Override
                            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                                mRegistWorkTv.setText(arrays.get(options1).getName());
                                policePositionBean = arrays.get(options1);
                            }
                        });
                        if (optionsPickerViewPosition != null && !optionsPickerViewPosition.isShowing()) {
                            optionsPickerViewPosition.show();
                        }
                    }
                }
                break;
            case RegistContract.GET_POLICE_BRANCH://部门
                PoliceBranchBean branchBean = (PoliceBranchBean) o;
                if (branchBean != null) {
                    List<PoliceBranchBean.DataBean> arrays = branchBean.getData();
                    if (arrays != null && arrays.size() > 0) {
                        departMents.clear();
                        departMents.addAll(arrays);
//                        dataValueAdapter.setAllDatas(departMents);
                    }else {
                        ToastUtils.warning(mContext, "未获取部门数据");
                    }
                }
                break;
            case RegistContract.GET_POLICE_GRIDDING:
                PoliceGriddingBean griddingBean = (PoliceGriddingBean) o;
                if (griddingBean != null) {
                    List<PoliceGriddingBean.DataBean> arrays = griddingBean.getData();
                    if (arrays != null && arrays.size() > 0) {
                        pickerViewGridding = PickerManager.getInstance().getOptionPicker(this, "选择责任网格", arrays, new PickerManager.OnOptionPickerSelectedListener() {
                            @Override
                            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                                mRegistGriddingTv.setText(arrays.get(options1).getName());
                                policeGriddingBean = arrays.get(options1);
                            }
                        });
                    } else {
                        ToastUtils.warning(mContext, "该部门暂无网格信息");
                    }
                    if (pickerViewGridding != null && !pickerViewGridding.isShowing()) {
                        pickerViewGridding.show();
                    }
                }
                break;
            case RegistContract.ADD_USER_INFO:
                ToastUtils.success(mContext, "提交成功，信息审核中。。。");
                onBackPressed();
                break;
            default:
                break;
        }
    }

    /**
     * 二级部门
     * @param arrays
     */
    private void initPickerViewChildDepart(List<PoliceBranchBean.DataBean.ChildDepartmentBean> arrays){
        if (arrays != null && arrays.size() > 0) {
            pickerViewChildDepart = PickerManager.getInstance().getOptionPicker(this, "选择二级部门", arrays, new PickerManager.OnOptionPickerSelectedListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    mRegistChildBranchTv.setText(arrays.get(options1).getName());
                    childDepartmentBean = arrays.get(options1);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePickerView(optionsPickerViewPosition);
        releasePickerView(pickerViewChildDepart);
        releasePickerView(pickerViewGridding);
    }

    /**
     * 释放pickerview
     *
     * @param optionsPickerView
     */
    private void releasePickerView(OptionsPickerView optionsPickerView) {
        if (optionsPickerView != null) {
            if (optionsPickerView.isShowing()) {
                optionsPickerView.dismiss();
                optionsPickerView = null;
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        departMentBean = dataValueAdapter.getDatas().get(position);
        mRegistGriddingTv.setText("");
        policeGriddingBean = null;
        mRegistChildBranchTv.setText("");
        childDepartmentBean = null;
        pickerViewGridding = null;
        pickerViewChildDepart = null;
        initPickerViewChildDepart(departMentBean.getDepartmentBranch());
    }
}
