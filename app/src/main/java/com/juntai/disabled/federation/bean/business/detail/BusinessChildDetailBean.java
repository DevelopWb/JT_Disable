package com.juntai.disabled.federation.bean.business.detail;

import com.juntai.disabled.basecomponent.base.BaseResult;

/**
 * @Author: tobato
 * @Description: 作用描述   期满换证 登记变更 遗失补办 迁入迁出 注销等业务 详情的实体类
 * @CreateDate: 2021/2/24 15:32
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/24 15:32
 */
public class BusinessChildDetailBean extends BaseResult {

    /**
     * error : null
     * data : {"id":4,"photo":"http://www.juntaikeji
     * .com:17003//business_certificates_exchange/43344aa108354d1fb7ef3b49096af1b2.jpeg","name":"1","idNumber":"2",
     * "disabilityCertificate":"3","address":"4","telephone":"5","disabilityCertificatePicture":"http://www
     * .juntaikeji.com:17003//business_certificates_exchange/1e1dd0064eef4392ab25986e2bc976dc.jpeg",
     * "casePicture":"http://www.juntaikeji
     * .com:17003//business_certificates_exchange/9b42648c6323427fb8a399600499475a.jpeg","lifePicture":"http://www
     * .juntaikeji.com:17003//business_certificates_exchange/58f1af25999b4e22b928b0851d116182.jpeg",
     * "applicantSign":"http://www.juntaikeji
     * .com:17003//business_certificates_exchange/bf756390e78542dbbd55bf985ba5d08b.jpeg"}
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
         * id : 4
         * photo : http://www.juntaikeji.com:17003//business_certificates_exchange/43344aa108354d1fb7ef3b49096af1b2.jpeg
         * name : 1
         * idNumber : 2
         * disabilityCertificate : 3
         * address : 4
         * telephone : 5
         * disabilityCertificatePicture : http://www.juntaikeji
         * .com:17003//business_certificates_exchange/1e1dd0064eef4392ab25986e2bc976dc.jpeg
         * casePicture : http://www.juntaikeji
         * .com:17003//business_certificates_exchange/9b42648c6323427fb8a399600499475a.jpeg
         * lifePicture : http://www.juntaikeji
         * .com:17003//business_certificates_exchange/58f1af25999b4e22b928b0851d116182.jpeg
         * applicantSign : http://www.juntaikeji
         * .com:17003//business_certificates_exchange/bf756390e78542dbbd55bf985ba5d08b.jpeg
         */

        private int id;
        private String photo;
        private String name;
        private String idNumber;
        private String disabilityCertificate;
        private String address;
        private String telephone;
        private String disabilityCertificatePicture;
        private String casePicture;
        private String lifePicture;
        private String applicantSign;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
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

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getDisabilityCertificatePicture() {
            return disabilityCertificatePicture;
        }

        public void setDisabilityCertificatePicture(String disabilityCertificatePicture) {
            this.disabilityCertificatePicture = disabilityCertificatePicture;
        }

        public String getCasePicture() {
            return casePicture;
        }

        public void setCasePicture(String casePicture) {
            this.casePicture = casePicture;
        }

        public String getLifePicture() {
            return lifePicture;
        }

        public void setLifePicture(String lifePicture) {
            this.lifePicture = lifePicture;
        }

        public String getApplicantSign() {
            return applicantSign;
        }

        public void setApplicantSign(String applicantSign) {
            this.applicantSign = applicantSign;
        }
    }
}
