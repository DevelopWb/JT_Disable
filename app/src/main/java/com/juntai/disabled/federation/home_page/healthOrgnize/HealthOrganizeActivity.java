package com.juntai.disabled.federation.home_page.healthOrgnize;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.base.BaseAppActivity;
import com.juntai.disabled.federation.bean.HealthOrganizeDetailBean;
import com.juntai.disabled.federation.home_page.HomePageContract;
import com.juntai.disabled.federation.home_page.HomePagePresent;
import com.juntai.disabled.federation.utils.UrlFormatUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import okhttp3.FormBody;

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
       ImageView navigationIv = (ImageView) findViewById(R.id.navigation_iv);
        mHealthAddrValueTv = (TextView) findViewById(R.id.health_addr_value_tv);
        navigationIv.setOnClickListener(this);
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
        FormBody.Builder builder = new FormBody.Builder();
        mPresenter.getHealthOrganizeDetail(builder.add("id",String.valueOf(orgId)).build(),"");
    }


    @Override
    public void onSuccess(String tag, Object o) {
        HealthOrganizeDetailBean detailBean = (HealthOrganizeDetailBean) o;
        if (detailBean != null) {
            dataBean = detailBean.getData();
            if (dataBean != null) {
                Document content = Jsoup.parse(dataBean.getContent());
                mHealthOrgTitleTv.setText(dataBean.getName());
                mHealthOrgDesTv.setText(content.body().text());
                ImageLoadUtil.loadImage(mContext, UrlFormatUtil.formatPicUrl(dataBean.getPhoto()),mHealthOrgImgIv);
                mHealthAddrValueTv.setText(dataBean.getAddress());
                mHealthTelValueTv.setText(dataBean.getTelephone());
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.navigation_iv:
                if (dataBean != null) {
                    navigationLogic(new LatLng(dataBean.getLatitude(),dataBean.getLongitude()),dataBean.getAddress());
                }

                break;
            default:
                break;
        }
    }
}
