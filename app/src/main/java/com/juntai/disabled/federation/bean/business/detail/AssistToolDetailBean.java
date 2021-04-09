package com.juntai.disabled.federation.bean.business.detail;

import android.text.TextUtils;

import com.juntai.disabled.basecomponent.base.BaseResult;

/**
 * @Author: tobato
 * @Description: 作用描述  辅助用品申请详情
 * @CreateDate: 2021/2/27 15:14
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/27 15:14
 */
public class AssistToolDetailBean extends BaseResult {

    /**
     * error : null
     * data : {"id":2,"name":"王","sex":1,"sexName":"女","birth":"88","telephone":"45","category":3,
     * "categoryName":"智力残疾","level":2,"levelName":"二级","aidsId":28,"aidsName":"急救医药包","disabilityCertificate":"88",
     * "address":"77","quantity":99,"applicantSign":"http://www.juntaikeji
     * .com:17003//business_aids/46f20af72b6a4400ac1bffc9b1494b36.jpeg","photo":null,
     * "disabilityCertificatePicture":"http://www.juntaikeji
     * .com:17003//business_aids/a58f42d4f9cd4ddd8dc64ac8ad073b84.jpeg"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2
         * name : 王
         * sex : 1
         * sexName : 女
         * birth : 88
         * telephone : 45
         * category : 3
         * categoryName : 智力残疾
         * level : 2
         * levelName : 二级
         * aidsId : 28
         * aidsName : 急救医药包
         * disabilityCertificate : 88
         * address : 77
         * quantity : 99
         * applicantSign : http://www.juntaikeji.com:17003//business_aids/46f20af72b6a4400ac1bffc9b1494b36.jpeg
         * photo : null
         * disabilityCertificatePicture : http://www.juntaikeji
         * .com:17003//business_aids/a58f42d4f9cd4ddd8dc64ac8ad073b84.jpeg
         */

        private int id;
        private String name;
        private int sex;
        private String sexName;
        private String birth;
        private String telephone;
        private int category;
        private String categoryName;
        private int level;
        private String levelName;
        private int aidsId;
        private String aidsName;
        private String disabilityCertificate;
        private String address;
        private int quantity;
        private String applicantSign;
        private String photo;
        private String deliveryWay;
        private String deliveryWayName;
        private String disabilityCertificatePicture;
        private String disabilityCertificateBackPicture;
        /**
         * 生活照片
         */
        private String lifePicture;
        private String idNumber;

        public String getIdNumber() {
            return TextUtils.isEmpty(idNumber) ? "暂无" : idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber == null ? "" : idNumber;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return TextUtils.isEmpty(name) ? "暂无" : name;
        }

        public void setName(String name) {
            this.name = name == null ? "" : name;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getSexName() {
            return TextUtils.isEmpty(sexName) ? "暂无" : sexName;
        }

        public void setSexName(String sexName) {
            this.sexName = sexName == null ? "" : sexName;
        }

        public String getBirth() {
            return TextUtils.isEmpty(birth) ? "暂无" : birth;
        }

        public void setBirth(String birth) {
            this.birth = birth == null ? "" : birth;
        }

        public String getTelephone() {
            return TextUtils.isEmpty(telephone) ? "暂无" : telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone == null ? "" : telephone;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public String getCategoryName() {
            return TextUtils.isEmpty(categoryName) ? "暂无" : categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName == null ? "" : categoryName;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getLevelName() {
            return TextUtils.isEmpty(levelName) ? "暂无" : levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName == null ? "" : levelName;
        }

        public int getAidsId() {
            return aidsId;
        }

        public void setAidsId(int aidsId) {
            this.aidsId = aidsId;
        }

        public String getAidsName() {
            return TextUtils.isEmpty(aidsName) ? "暂无" : aidsName;
        }

        public void setAidsName(String aidsName) {
            this.aidsName = aidsName == null ? "" : aidsName;
        }

        public String getDisabilityCertificate() {
            return TextUtils.isEmpty(disabilityCertificate) ? "暂无" : disabilityCertificate;
        }

        public void setDisabilityCertificate(String disabilityCertificate) {
            this.disabilityCertificate = disabilityCertificate == null ? "" : disabilityCertificate;
        }

        public String getAddress() {
            return TextUtils.isEmpty(address) ? "暂无" : address;
        }

        public void setAddress(String address) {
            this.address = address == null ? "" : address;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getApplicantSign() {
            return TextUtils.isEmpty(applicantSign) ? "暂无" : applicantSign;
        }

        public void setApplicantSign(String applicantSign) {
            this.applicantSign = applicantSign == null ? "" : applicantSign;
        }

        public String getPhoto() {
            return TextUtils.isEmpty(photo) ? "暂无" : photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo == null ? "" : photo;
        }

        public String getDeliveryWay() {
            return TextUtils.isEmpty(deliveryWay) ? "暂无" : deliveryWay;
        }

        public void setDeliveryWay(String deliveryWay) {
            this.deliveryWay = deliveryWay == null ? "" : deliveryWay;
        }

        public String getDeliveryWayName() {
            return TextUtils.isEmpty(deliveryWayName) ? "暂无" : deliveryWayName;
        }

        public void setDeliveryWayName(String deliveryWayName) {
            this.deliveryWayName = deliveryWayName == null ? "" : deliveryWayName;
        }

        public String getDisabilityCertificatePicture() {
            return TextUtils.isEmpty(disabilityCertificatePicture) ? "暂无" : disabilityCertificatePicture;
        }

        public void setDisabilityCertificatePicture(String disabilityCertificatePicture) {
            this.disabilityCertificatePicture = disabilityCertificatePicture == null ? "" : disabilityCertificatePicture;
        }

        public String getDisabilityCertificateBackPicture() {
            return TextUtils.isEmpty(disabilityCertificateBackPicture) ? "暂无" : disabilityCertificateBackPicture;
        }

        public void setDisabilityCertificateBackPicture(String disabilityCertificateBackPicture) {
            this.disabilityCertificateBackPicture = disabilityCertificateBackPicture == null ? "" : disabilityCertificateBackPicture;
        }

        public String getLifePicture() {
            return TextUtils.isEmpty(lifePicture) ? "暂无" : lifePicture;
        }

        public void setLifePicture(String lifePicture) {
            this.lifePicture = lifePicture == null ? "" : lifePicture;
        }
    }
}
