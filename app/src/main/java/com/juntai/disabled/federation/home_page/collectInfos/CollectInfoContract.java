package com.juntai.disabled.federation.home_page.collectInfos;

import com.juntai.disabled.basecomponent.mvp.BaseIView;

import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021/3/17 16:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/3/17 16:47
 */
public interface CollectInfoContract {

    interface TakeInfoViewBase extends BaseIView {

    }


    interface  ITakeInfoPresent {
        /**
         * 获取残疾人信息
         */
        void getCollectDisabledDetail(RequestBody body, String tag);

        /**
         * 视频采集
         * @param body
         * @param tag
         */
        void insertEvent(RequestBody body, String tag);
        /**
         * 无障碍采集
         * @param body
         * @param tag
         */
        void insertCase2(RequestBody body, String tag);
    }
}
