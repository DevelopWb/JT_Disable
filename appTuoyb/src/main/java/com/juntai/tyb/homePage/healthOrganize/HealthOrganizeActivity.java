package com.juntai.tyb.homePage.healthOrganize;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.tyb.base.BaseAppActivity;
import com.juntai.tyb.bean.healthOrg.HealthOrganizeDetailBean;
import com.juntai.tyb.hcb.R;
import com.juntai.tyb.homePage.HomePageContract;
import com.juntai.tyb.homePage.HomePagePresent;
import com.juntai.tyb.uitils.UrlFormatUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @aouther tobato
 * @description 描述  康复机构
 * @date 2020/7/23 16:35
 */
public class HealthOrganizeActivity extends BaseAppActivity<HomePagePresent> implements HomePageContract.IHomePageView,View.OnClickListener {
    public static String ID = "id";
    /**
     * 机构名称
     */
    private TextView mHealthOrgTitleTv;
    private TextView mHealthOrgDesTv;
    private ImageView mHealthOrgImgIv;
    private TextView mHealthAddrValueTv;
    private TextView mHealthTelValueTv;
    private TextView mNavigationTv;
    private HealthOrganizeDetailBean.DataBean dataBean;

    @Override
    protected HomePagePresent createPresenter() {
        return new HomePagePresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_health_organize;
    }

    @Override
    public void initView() {
        setTitleName("");
        mHealthOrgTitleTv = (TextView) findViewById(R.id.health_org_title_tv);
        mHealthOrgDesTv = (TextView) findViewById(R.id.health_org_des_tv);
        mHealthOrgImgIv = (ImageView) findViewById(R.id.health_org_img_iv);
        mHealthAddrValueTv = (TextView) findViewById(R.id.health_addr_value_tv);
        mNavigationTv = (TextView) findViewById(R.id.health_navigation_tv);
        mNavigationTv.setOnClickListener(this);
        mHealthTelValueTv = (TextView) findViewById(R.id.health_tel_value_tv);
        mHealthTelValueTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                copyTelephoneNum(getTextViewValue(mHealthTelValueTv));
                ToastUtils.toast(mContext, String.format("%s%s%s", "已将", getTextViewValue(mHealthTelValueTv), "复制"));
                return true;
            }
        });
        mHealthTelValueTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!"暂无".equals(getTextViewValue(mHealthTelValueTv))) {
                    makeAPhoneCall(getTextViewValue(mHealthTelValueTv));
                }

            }
        });
    }

    @Override
    public void initData() {
        int orgId = getIntent().getIntExtra(ID,0);
        mPresenter.getHealthOrganizeDetail(getBaseBuilder().add("id",String.valueOf(orgId)).build(),"");
    }


    @Override
    public void onSuccess(String tag, Object o) {
        HealthOrganizeDetailBean detailBean = (HealthOrganizeDetailBean) o;
        if (detailBean != null) {
            dataBean = detailBean.getData();
            if (dataBean != null) {
                Document content = Jsoup.parse(dataBean.getRemark());
                mHealthOrgTitleTv.setText(dataBean.getName());
                mHealthOrgDesTv.setText(content.body().text());
                ImageLoadUtil.loadImage(mContext, UrlFormatUtil.formatPicUrl(dataBean.getImg()),mHealthOrgImgIv);
                mHealthAddrValueTv.setText(dataBean.getAddress());
                mHealthTelValueTv.setText(dataBean.getPhone());
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.health_navigation_tv) {
            if (dataBean != null) {
                navigationLogic(new LatLng(dataBean.getLatitude(), dataBean.getLongitude()), dataBean.getAddress());
            }
        }
    }
}
