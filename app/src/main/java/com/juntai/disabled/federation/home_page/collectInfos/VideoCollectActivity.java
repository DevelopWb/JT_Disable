package com.juntai.disabled.federation.home_page.collectInfos;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.bdmap.act.SelectLocationActivity;
import com.juntai.disabled.federation.R;
import com.juntai.disabled.federation.base.BaseSelectVideoActivity;
import com.juntai.disabled.federation.base.selectPics.SelectPhotosFragment;
import com.juntai.disabled.federation.home_page.PublishContract;
import com.juntai.disabled.federation.utils.StringTools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.MultipartBody;

/**
 * @aouther tobato
 * @description 描述  影像采集
 * @date 2021/3/18 17:30
 */
public class VideoCollectActivity extends BaseCollectActivity  {


    @Override
    protected String getTitleName() {
        return "视频采集";
    }


}