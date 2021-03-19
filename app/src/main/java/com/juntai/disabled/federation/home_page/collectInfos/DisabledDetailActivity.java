package com.juntai.disabled.federation.home_page.collectInfos;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.base.BaseAppActivity;
import com.juntai.disabled.federation.base.selectPics.SelectPhotosFragment;
import com.juntai.disabled.federation.bean.TextListBean;
import com.juntai.disabled.federation.home_page.key_personnel.KPInfoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @aouther tobato
 * @description 描述 残疾人详情
 * @date 2021/3/18 17:42
 */
public class DisabledDetailActivity extends BaseAppActivity<CollectInfoPresent> implements View.OnClickListener {

    private SelectPhotosFragment videoCollectfg;
    private SelectPhotosFragment accessableCollectfg;
    private KPInfoAdapter textListAdapter;
    private RecyclerView mDisabledInfoRv;
    /**
     * 影像采集
     */
    private TextView mDisabledCollectTv;
    /**
     * 无障碍采集
     */
    private TextView mAccessableCollectTv;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected CollectInfoPresent createPresenter() {
        return null;
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_disabled_detail;
    }

    @Override
    public void initView() {
        setTitleName("姓名");
        List<TextListBean> textListBeans = new ArrayList<>();
        textListBeans.add(new TextListBean("姓名", "测试"));
        textListBeans.add(new TextListBean("性别", 1 == 0 ? "男" : "女"));
        textListBeans.add(new TextListBean("年龄", "20"));
        textListBeans.add(new TextListBean("联系电话", "12322221222"));
        textListBeans.add(new TextListBean("身份证号", "12322221222"));
        textListBeans.add(new TextListBean("住址", "12322221222"));
        textListBeans.add(new TextListBean("单位", "12322221222"));
        textListBeans.add(new TextListBean("所属网格", "12322221222"));
        textListAdapter = new KPInfoAdapter(R.layout.item_key_personnel_value, textListBeans);
        mDisabledInfoRv = (RecyclerView) findViewById(R.id.disabled_info_rv);
        mDisabledCollectTv = (TextView) findViewById(R.id.disabled_collect_tv);
        mDisabledCollectTv.setOnClickListener(this);
        mAccessableCollectTv = (TextView) findViewById(R.id.accessable_collect_tv);
        mAccessableCollectTv.setOnClickListener(this);
        initRecyclerview(mDisabledInfoRv, textListAdapter, LinearLayout.VERTICAL);
       addDivider(true,mDisabledInfoRv,false,true);
        initFragment();
    }

    private void initFragment() {
        videoCollectfg = SelectPhotosFragment.newInstance();
        accessableCollectfg = SelectPhotosFragment.newInstance();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.video_collect_fl, videoCollectfg);
        transaction.add(R.id.accessble_collect_gl, accessableCollectfg);
        transaction.commit();

        videoCollectfg.setSpanCount(3)
                .setMaxCount(3)
                .setPhotoDelateable(false);
        accessableCollectfg.setSpanCount(3)
                .setMaxCount(9)
                .setPhotoDelateable(false);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onSuccess(String tag, Object o) {

    }

}
