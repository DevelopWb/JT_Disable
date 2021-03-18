package com.juntai.disabled.federation.bean.takeinfo;

import com.juntai.disabled.basecomponent.base.BaseResult;

import java.util.List;

/**
 * 搜索结果  待采集残疾人信息
 * Created by Ma
 * on 2019/6/1
 */
public class SearchedDisabledBean extends BaseResult {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : 周纯友
         * idno : 372801193907055755
         * certificateno : 37280119390705575541
         * sex : 男
         * presentaddress : 山东省河东区相公办事处周家庄村95号
         * alleviation : 否
         * security : 否
         */

        private int id;
        private String name;
        private String idno;
        private String certificateno;
        private String sex;
        private String presentaddress;
        private String alleviation;
        private String security;
        private String remould;//是否无障碍

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

        public String getIdno() {
            return idno;
        }

        public void setIdno(String idno) {
            this.idno = idno;
        }

        public String getCertificateno() {
            return certificateno;
        }

        public void setCertificateno(String certificateno) {
            this.certificateno = certificateno;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getPresentaddress() {
            return presentaddress;
        }

        public void setPresentaddress(String presentaddress) {
            this.presentaddress = presentaddress;
        }

        public String getAlleviation() {
            return alleviation;
        }

        public void setAlleviation(String alleviation) {
            this.alleviation = alleviation;
        }

        public String getSecurity() {
            return security;
        }

        public void setSecurity(String security) {
            this.security = security;
        }

        public String getRemould() {
            return remould;
        }

        public void setRemould(String remould) {
            this.remould = remould;
        }
    }
}
