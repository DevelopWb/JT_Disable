package com.juntai.disabled.federation.bean.business.detail;

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
        private String disabilityCertificatePicture;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getSexName() {
            return sexName;
        }

        public void setSexName(String sexName) {
            this.sexName = sexName;
        }

        public String getBirth() {
            return birth;
        }

        public void setBirth(String birth) {
            this.birth = birth;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public int getAidsId() {
            return aidsId;
        }

        public void setAidsId(int aidsId) {
            this.aidsId = aidsId;
        }

        public String getAidsName() {
            return aidsName;
        }

        public void setAidsName(String aidsName) {
            this.aidsName = aidsName;
        }

        public String getDisabilityCertificate() {
            return disabilityCertificate;
        }

        public void setDisabilityCertificate(String disabilityCertificate) {
            this.disabilityCertificate = disabilityCertificate;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getApplicantSign() {
            return applicantSign;
        }

        public void setApplicantSign(String applicantSign) {
            this.applicantSign = applicantSign;
        }

        public String getPhoto() {
            return photo == null ? "" : photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo == null ? "" : photo;
        }

        public String getDisabilityCertificatePicture() {
            return disabilityCertificatePicture;
        }

        public void setDisabilityCertificatePicture(String disabilityCertificatePicture) {
            this.disabilityCertificatePicture = disabilityCertificatePicture;
        }
    }
}
