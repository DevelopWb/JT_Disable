package com.juntai.disabled.federation.bean.careTaker;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  托养记录  保存本地的
 * @CreateDate: 2020/7/15 11:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/15 11:47
 */
public class CareRecoredSavedBean {

    private int caregiversId;
    private String place;
    private  double longitude;
    private  double latitude;
    private String startTime;
    private String endTime;
    private String serviceLength;
    private String servicer;
    private String serviceIds;//服务人员的id
    private String describe ;
    private String uploadingYear;
    private ServiceTypeBean.DataBean typeBean;
    private List<CareTakerPicBean> picBeans;

    public ServiceTypeBean.DataBean getTypeBean() {
        return typeBean;
    }

    public void setTypeBean(ServiceTypeBean.DataBean typeBean) {
        this.typeBean = typeBean;
    }

    public String getServiceIds() {
        return serviceIds == null ? "" : serviceIds;
    }

    public void setServiceIds(String serviceIds) {
        this.serviceIds = serviceIds == null ? "" : serviceIds;
    }

    public int getCaregiversId() {
        return caregiversId;
    }

    public void setCaregiversId(int caregiversId) {
        this.caregiversId = caregiversId;
    }

    public String getPlace() {
        return place == null ? "" : place;
    }

    public void setPlace(String place) {
        this.place = place == null ? "" : place;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getStartTime() {
        return startTime == null ? "" : startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? "" : startTime;
    }

    public String getEndTime() {
        return endTime == null ? "" : endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? "" : endTime;
    }

    public String getServiceLength() {
        return serviceLength == null ? "" : serviceLength;
    }

    public void setServiceLength(String serviceLength) {
        this.serviceLength = serviceLength == null ? "" : serviceLength;
    }

    public String getServicer() {
        return servicer == null ? "" : servicer;
    }

    public void setServicer(String servicer) {
        this.servicer = servicer == null ? "" : servicer;
    }

    public String getDescribe() {
        return describe == null ? "" : describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? "" : describe;
    }

    public String getUploadingYear() {
        return uploadingYear == null ? "" : uploadingYear;
    }

    public void setUploadingYear(String uploadingYear) {
        this.uploadingYear = uploadingYear == null ? "" : uploadingYear;
    }

    public List<CareTakerPicBean> getPicBeans() {
        if (picBeans == null) {
            return new ArrayList<>();
        }
        return picBeans;
    }

    public void setPicBeans(List<CareTakerPicBean> picBeans) {
        this.picBeans = picBeans;
    }
}
