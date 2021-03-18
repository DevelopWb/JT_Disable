package com.juntai.disabled.federation.bean.careTaker;


import com.juntai.disabled.basecomponent.base.BaseResult;

/**
 * @Author: tobato
 * @Description: 作用描述  托养人基本信息
 * @CreateDate: 2020/7/13 10:30
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/13 10:30
 */
public class CareTakerBaseInfoBean extends BaseResult {


    /**
     * error : null
     * returnValue : null
     * list : null
     * type : null
     * data : {"name":"孟祥臣","sex":"男","details":"智力三级;","certificateNo":"37280119730117485453",
     * "presentAddress":"九曲办事处孟家于埠村54号"}
     * success : true
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
         * name : 孟祥臣
         * sex : 男
         * details : 智力三级;
         * certificateNo : 37280119730117485453
         * presentAddress : 九曲办事处孟家于埠村54号
         */

        private String name;
        private String sex;
        private String details;
        private String certificateNo;
        private String presentAddress;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getCertificateNo() {
            return certificateNo;
        }

        public void setCertificateNo(String certificateNo) {
            this.certificateNo = certificateNo;
        }

        public String getPresentAddress() {
            return presentAddress;
        }

        public void setPresentAddress(String presentAddress) {
            this.presentAddress = presentAddress;
        }
    }
}
