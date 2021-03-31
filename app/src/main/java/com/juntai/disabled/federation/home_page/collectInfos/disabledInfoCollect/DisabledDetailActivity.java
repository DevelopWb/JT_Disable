package com.juntai.disabled.federation.home_page.collectInfos.disabledInfoCollect;

import android.content.Intent;
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
import com.juntai.disabled.federation.bean.collect.CollectDisabledDetailBean;
import com.juntai.disabled.federation.home_page.collectInfos.CollectInfoContract;
import com.juntai.disabled.federation.home_page.collectInfos.CollectInfoPresent;
import com.juntai.disabled.federation.home_page.key_personnel.KPInfoAdapter;
import com.juntai.disabled.federation.utils.UrlFormatUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @aouther tobato
 * @description 描述 残疾人详情
 * @date 2021/3/18 17:42
 */
public class DisabledDetailActivity extends BaseAppActivity<CollectInfoPresent> implements
        CollectInfoContract.TakeInfoViewBase, View.OnClickListener {

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

    public static String DISABLED_ID = "disabledID";
    public static String DISABLED_ID_NUM = "disabledIDNU";//残疾证号
    public static String DISABLED_NAME = "disabledname";
    private String idNum;


    @Override
    protected CollectInfoPresent createPresenter() {
        return new CollectInfoPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_disabled_detail;
    }

    @Override
    public void initView() {
        textListAdapter = new KPInfoAdapter(R.layout.item_key_personnel_value, null);
        mDisabledInfoRv = (RecyclerView) findViewById(R.id.disabled_info_rv);
        mDisabledCollectTv = (TextView) findViewById(R.id.disabled_collect_tv);
        mDisabledCollectTv.setOnClickListener(this);
        mAccessableCollectTv = (TextView) findViewById(R.id.accessable_collect_tv);
        mAccessableCollectTv.setOnClickListener(this);
        initRecyclerview(mDisabledInfoRv, textListAdapter, LinearLayout.VERTICAL);
        addDivider(true, mDisabledInfoRv, false, true);
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
        if (getIntent() != null) {
            setTitleName(getIntent().getStringExtra(DISABLED_NAME));
            //获取详情
            mPresenter.getCollectDisabledDetail(getBaseBuilder().add("id",
                    String.valueOf(getIntent().getIntExtra(DISABLED_ID, 0))).build(), "");
        }


    }

    @Override
    public void onSuccess(String tag, Object o) {
        CollectDisabledDetailBean detailBean = (CollectDisabledDetailBean) o;
        if (detailBean != null) {
            CollectDisabledDetailBean.DataBean dataBean = detailBean.getData();
            if (dataBean != null) {
                textListAdapter.setNewData(getDisabledBaseInfo(dataBean));
                videoCollectfg.setIcons(getDisabledVideoInfos(dataBean));
            }
        }
    }

    /**
     * 影像采集的信息  图片和视频
     *
     * @param dataBean
     * @return
     */
    private List<String> getDisabledVideoInfos(CollectDisabledDetailBean.DataBean dataBean) {
        List<String> arrays = new ArrayList<>();
        if (dataBean.getImgoneid() > 0) {
            arrays.add(UrlFormatUtil.getCollectImagePath(dataBean.getImgoneid()));
        }
        if (dataBean.getImgtwoid() > 0) {
            arrays.add(UrlFormatUtil.getCollectImagePath(dataBean.getImgtwoid()));
        }
        if (dataBean.getImgthreeid() > 0) {
            arrays.add(UrlFormatUtil.getCollectImagePath(dataBean.getImgthreeid()));
        }
        if (dataBean.getVideoid() > 0) {
            arrays.add(UrlFormatUtil.getCollectImagePath(dataBean.getVideoid()));
        }
        return arrays;
    }

    /**
     * 残疾人的基本信息
     *
     * @param dataBean
     */
    private List<TextListBean> getDisabledBaseInfo(CollectDisabledDetailBean.DataBean dataBean) {
        List<TextListBean> textListBeans = new ArrayList<>();
        textListBeans.add(new TextListBean("姓名", dataBean.getName()));
        textListBeans.add(new TextListBean("残疾证号", dataBean.getCertificateno()));
        textListBeans.add(new TextListBean("发证日期", dataBean.getCertificatetime()));
        textListBeans.add(new TextListBean("持证状态", dataBean.getCertificatestatus()));
        textListBeans.add(new TextListBean("性别", dataBean.getSex()));
        textListBeans.add(new TextListBean("文化程度", dataBean.getEducation()));
        textListBeans.add(new TextListBean("婚姻状况：", dataBean.getMarriage()));
        textListBeans.add(new TextListBean("残疾类别：", dataBean.getCategory()));
        textListBeans.add(new TextListBean("残疾等级：", dataBean.getLevel()));
        textListBeans.add(new TextListBean("残疾详情：", dataBean.getDetails()));
        textListBeans.add(new TextListBean("户口类型：", dataBean.getPermanenttype()));
        textListBeans.add(new TextListBean("户籍地址：", dataBean.getPermanentaddress()));
        textListBeans.add(new TextListBean("现住址:", dataBean.getPresentaddress()));
        textListBeans.add(new TextListBean("联系方式:", dataBean.getPhone()));
        textListBeans.add(new TextListBean("贫困户:", dataBean.getAlleviation()));
        textListBeans.add(new TextListBean("低保户:", dataBean.getSecurity()));
        textListBeans.add(new TextListBean("无障碍:", dataBean.getRemould()));
        idNum = dataBean.getIdno();
        return textListBeans;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.disabled_collect_tv:
                //视频采集
                startActivity(new Intent(this, VideoCollectActivity.class).putExtra(DISABLED_ID_NUM,idNum));
                break;
            case R.id.accessable_collect_tv:
                //无障碍采集
                startActivity(new Intent(this, AccessibleCollectionActivity.class).putExtra(DISABLED_ID_NUM,idNum));
                break;
        }
    }
}
