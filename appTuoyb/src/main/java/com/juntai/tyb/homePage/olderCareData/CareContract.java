package com.juntai.tyb.homePage.olderCareData;


import com.juntai.disabled.basecomponent.mvp.BaseIView;

import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/4/21 16:05
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/21 16:05
 */
public interface CareContract {
    interface ICareView extends BaseIView {}

    interface ICarePresent {
        /**
         * 获取托养子列表
         * @param tag
         */
        void getCareChildList(RequestBody requestBody, String tag);
    }

}
