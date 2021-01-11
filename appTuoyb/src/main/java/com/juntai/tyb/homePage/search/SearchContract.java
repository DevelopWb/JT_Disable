package com.juntai.tyb.homePage.search;


import com.juntai.disabled.basecomponent.mvp.BaseIView;

import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/7/9 15:00
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/9 15:00
 */
public interface SearchContract {
    String  STREETS = "streets";//所有的街道
    String  YEARS = "years";//所有的街道
    String  ALL_DISABLED_PEOPLE = "DISABLED_PEOPLE";//所有残疾人
    String  ALL_CARE_TAKER = "CARE_TAKER";//所有的托养人
    interface ISearchView extends BaseIView {}


    interface ISearchPresent {

        /**
         * 获取所有的街道
         */
        void getStreets(String tag);
        /**
         * 获取所有的年份
         */
        void getAllYears(String tag);

        /**
         * 获取托养人
         * @param tag
         */
        void searchAllCareTakers(RequestBody body,String tag);
        /**
         * 所有的残疾人
         * @param tag
         */
        void searchAllDisabledPeoples(RequestBody body,String tag);

    }
}
