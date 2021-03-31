package com.juntai.disabled.federation.utils;

import com.juntai.disabled.federation.bean.careTaker.CareTakerInfoBean;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021/2/27 10:59
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/27 10:59
 */
public class HawkProperty {
    public static  String  USER_INFO = "userinfo";//用户信息的实体类
    public static  String  CARE_SERVICE_RECORD_KEY = "care_service_record";//托养服务记录
    public static  String  CARE_INFO_KEY = "care_info_key";//托养信息
    public static  String  CARE_INFO_MORE_KEY = "care_info_more_key";//托养信息 更多

    public static  String  getCareServiceRecordKey(CareTakerInfoBean.DataBean  careInfo){
        return CARE_SERVICE_RECORD_KEY+careInfo.getIdNo();
    }
}
