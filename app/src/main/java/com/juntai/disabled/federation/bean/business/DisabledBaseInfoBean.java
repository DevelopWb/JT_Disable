package com.juntai.disabled.federation.bean.business;

import com.juntai.disabled.basecomponent.base.BaseResult;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021/4/9 14:23
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/4/9 14:23
 */
public class DisabledBaseInfoBean extends BaseResult {


    /**
     * error : null
     * data : {"idNo":"372801196310085736","certificateNo":"37280119631008573641","sexName":"男","sex":0,
     * "categoryName":"肢体","category":1,"levelName":"一级","level":1,"permanentAddress":"山东省河东区相公办事处洪岭埠村324号"}
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
         * idNo : 372801196310085736
         * certificateNo : 37280119631008573641
         * sexName : 男
         * sex : 0
         * categoryName : 肢体
         * category : 1
         * levelName : 一级
         * level : 1
         * permanentAddress : 山东省河东区相公办事处洪岭埠村324号
         */

        private String idNo;
        private String certificateNo;
        private String sexName;
        private int sex;
        private String categoryName;
        private int category;
        private String levelName;
        private int level;
        private String permanentAddress;

        public String getIdNo() {
            return idNo;
        }

        public void setIdNo(String idNo) {
            this.idNo = idNo;
        }

        public String getCertificateNo() {
            return certificateNo;
        }

        public void setCertificateNo(String certificateNo) {
            this.certificateNo = certificateNo;
        }

        public String getSexName() {
            return sexName;
        }

        public void setSexName(String sexName) {
            this.sexName = sexName;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getPermanentAddress() {
            return permanentAddress;
        }

        public void setPermanentAddress(String permanentAddress) {
            this.permanentAddress = permanentAddress;
        }
    }
}
