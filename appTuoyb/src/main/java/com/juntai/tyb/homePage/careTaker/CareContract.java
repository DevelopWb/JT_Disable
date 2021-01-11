package com.juntai.tyb.homePage.careTaker;


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

    String CARE_INFO = "CARE_INFO";
    String CARE_INFO_MORE = "CARE_INFO_MORE";
    String CARE_TAKER_BASE_INFO = "CARE_INFO_base";
    String CARE_RECORD = "CARE_RECORD";
    String ADD_CARE_TAKER = "ADD_CARE_TAKER";
    String MODIFY_CARE_TAKER = "MODIFY_CARE_TAKER";
    String SERVICE_PEOPLE = "SERVICE_PEOPLE";
    String START_TIME = "START_TIME";//开始计时
    String COMMIT_RECORED = "COMMIT_RECORED";//提交记录
    String GET_SERVICE_TYPE = "getservicetype";//获取服务类型

    interface ICareView extends BaseIView {
    }

    interface ICarePresent {

        /**
         * 开始计时
         */
        void startTime(String tag);

        /**
         * 结束计时
         */
        void endTime();


        /**
         * 获取托养信息
         *
         * @param tag
         */
        void getCareInfo(RequestBody requestBody, String tag);

        /**
         * 获取托养信息 更多
         *
         * @param tag
         */
        void getCareInfoMore(RequestBody requestBody, String tag);

        /**
         * 获取托养记录
         *
         * @param tag
         */
        void getCareRecord(RequestBody requestBody, String tag);
        /**
         * 获取托养人基本信息
         *
         * @param tag
         */
        void careTakerBaseInfo(RequestBody requestBody, String tag);
        /**
         * 添加托养人
         *
         * @param tag
         */
        void addCareTaker(RequestBody requestBody, String tag);
        /**
         * 修改托养人
         *
         * @param tag
         */
        void modifyCareTaker(RequestBody requestBody, String tag);
        /**
         * 按街道分类服务人员
         *
         * @param tag
         */
        void getServicePeople(RequestBody requestBody, String tag);
        /**
         * 提交托养记录
         *
         * @param tag
         */
        void commitCareRecord(RequestBody requestBody, String tag);
        /**
         * 选择服务类型
         *
         * @param tag
         */
        void getServiceType(RequestBody requestBody, String tag);


    }

}
