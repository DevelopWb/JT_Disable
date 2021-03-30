package com.juntai.disabled.federation.bean.collect;

import android.text.TextUtils;

import com.juntai.disabled.basecomponent.base.BaseResult;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021/3/26 14:19
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/3/26 14:19
 */
public class CollectDisabledDetailBean extends BaseResult {


    /**
     * error : null
     * data : {"id":13,"name":"张宗堂","idno":"372801196006176035","certificateno":"37280119600617603514","sex":"男",
     * "education":"文盲","marriage":"已婚","category":"视力","level":"四级","details":"视力四级;","permanenttype":"农业",
     * "certificatetime":"2009-04-28","permanentaddress":"山东省河东区汤河镇东洽沟村237号","presentaddress":"山东省河东区汤河镇东洽沟村237号",
     * "phone":"15053978487","certificatestatus":"注销","alleviation":"否","security":"否","remould":"否","imgoneid":null,
     * "imgtwoid":null,"imgthreeid":null,"videoid":null,"imgvideoid":null,"eventId":null,"latitude":null,
     * "longitude":null,"remouldPhoto":null,"remouldVideo":null,"remouldVideoPhoto":null}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int id;//残疾人ID
        private String name;//姓名
        private String idno;//身份证号
        private String certificateno;//残疾证号
        private String sex;//性别
        private String education;//文化程度
        private String marriage;//婚姻状况
        private String category;//残疾类别
        private String level;//残疾等级
        private String details;//残疾详情
        private String permanenttype;//户口类型
        private String certificatetime;//发证日期
        private String permanentaddress;//户籍地址
        private String presentaddress;//现住址
        private String phone;//联系方式
        private String certificatestatus;//持证状态
        private String alleviation;//贫困户
        private String security;//低保户
        private String remould;//是否无障碍改造
        private int imgoneid;//图片id
        private int imgtwoid;//图片id
        private int imgthreeid;//图片id
        private int videoid;//视频id
        private int imgvideoid;//视频图片id
        private int eventId;//采集信息id
        private double latitude;//纬度
        private double longitude;//经度
        private String remouldPhoto;//无障碍改造图片
        private Long remouldVideo;//视频
        private Long remouldVideoPhoto;//视频封面

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name == null ? "暂无" : name;
        }

        public void setName(String name) {
            this.name = name == null ? "" : name;
        }

        public String getIdno() {
            return idno == null ? "暂无" : idno;
        }

        public void setIdno(String idno) {
            this.idno = idno == null ? "" : idno;
        }

        public String getCertificateno() {
            return certificateno == null ? "暂无" : certificateno;
        }

        public void setCertificateno(String certificateno) {
            this.certificateno = certificateno == null ? "" : certificateno;
        }

        public String getSex() {
            return sex == null ? "暂无" : sex;
        }

        public String getPhone() {
            return TextUtils.isEmpty(phone) ? "暂无" : phone;
        }

        public void setPhone(String phone) {
            this.phone = phone == null ? "" : phone;
        }

        public void setSex(String sex) {
            this.sex = sex == null ? "" : sex;
        }

        public String getEducation() {
            return education == null ? "暂无" : education;
        }

        public void setEducation(String education) {
            this.education = education == null ? "" : education;
        }

        public String getMarriage() {
            return marriage == null ? "暂无" : marriage;
        }

        public void setMarriage(String marriage) {
            this.marriage = marriage == null ? "" : marriage;
        }

        public String getCategory() {
            return category == null ? "暂无" : category;
        }

        public void setCategory(String category) {
            this.category = category == null ? "" : category;
        }

        public String getLevel() {
            return level == null ? "暂无" : level;
        }

        public void setLevel(String level) {
            this.level = level == null ? "" : level;
        }

        public String getDetails() {
            return details == null ? "暂无" : details;
        }

        public void setDetails(String details) {
            this.details = details == null ? "" : details;
        }

        public String getPermanenttype() {
            return permanenttype == null ? "暂无" : permanenttype;
        }

        public void setPermanenttype(String permanenttype) {
            this.permanenttype = permanenttype == null ? "" : permanenttype;
        }

        public String getCertificatetime() {
            return certificatetime == null ? "暂无" : certificatetime;
        }

        public void setCertificatetime(String certificatetime) {
            this.certificatetime = certificatetime == null ? "" : certificatetime;
        }

        public String getPermanentaddress() {
            return permanentaddress == null ? "暂无" : permanentaddress;
        }

        public void setPermanentaddress(String permanentaddress) {
            this.permanentaddress = permanentaddress == null ? "" : permanentaddress;
        }

        public String getPresentaddress() {
            return presentaddress == null ? "暂无" : presentaddress;
        }

        public void setPresentaddress(String presentaddress) {
            this.presentaddress = presentaddress == null ? "" : presentaddress;
        }


        public String getCertificatestatus() {
            return certificatestatus == null ? "暂无" : certificatestatus;
        }

        public void setCertificatestatus(String certificatestatus) {
            this.certificatestatus = certificatestatus == null ? "" : certificatestatus;
        }

        public String getAlleviation() {
            return alleviation == null ? "暂无" : alleviation;
        }

        public void setAlleviation(String alleviation) {
            this.alleviation = alleviation == null ? "" : alleviation;
        }

        public String getSecurity() {
            return security == null ? "暂无" : security;
        }

        public void setSecurity(String security) {
            this.security = security == null ? "" : security;
        }

        public String getRemould() {
            return remould == null ? "暂无" : remould;
        }

        public void setRemould(String remould) {
            this.remould = remould == null ? "" : remould;
        }

        public int getImgoneid() {
            return imgoneid;
        }

        public void setImgoneid(int imgoneid) {
            this.imgoneid = imgoneid;
        }

        public int getImgtwoid() {
            return imgtwoid;
        }

        public void setImgtwoid(int imgtwoid) {
            this.imgtwoid = imgtwoid;
        }

        public int getImgthreeid() {
            return imgthreeid;
        }

        public void setImgthreeid(int imgthreeid) {
            this.imgthreeid = imgthreeid;
        }

        public int getVideoid() {
            return videoid;
        }

        public void setVideoid(int videoid) {
            this.videoid = videoid;
        }

        public int getImgvideoid() {
            return imgvideoid;
        }

        public void setImgvideoid(int imgvideoid) {
            this.imgvideoid = imgvideoid;
        }

        public int getEventId() {
            return eventId;
        }

        public void setEventId(int eventId) {
            this.eventId = eventId;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getRemouldPhoto() {
            return remouldPhoto == null ? "暂无" : remouldPhoto;
        }

        public void setRemouldPhoto(String remouldPhoto) {
            this.remouldPhoto = remouldPhoto == null ? "" : remouldPhoto;
        }

        public Long getRemouldVideo() {
            return remouldVideo;
        }

        public void setRemouldVideo(Long remouldVideo) {
            this.remouldVideo = remouldVideo;
        }

        public Long getRemouldVideoPhoto() {
            return remouldVideoPhoto;
        }

        public void setRemouldVideoPhoto(Long remouldVideoPhoto) {
            this.remouldVideoPhoto = remouldVideoPhoto;
        }
    }
}
