package com.juntai.disabled.federation.bean.business.detail;

import android.text.TextUtils;

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
        private String disabilityCertificateBackPicture;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhoto() {
            return TextUtils.isEmpty(photo) ? "暂无" : photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo == null ? "" : photo;
        }

        public String getName() {
            return TextUtils.isEmpty(name) ? "暂无" : name;
        }

        public void setName(String name) {
            this.name = name == null ? "" : name;
        }

        public String getIdNumber() {
            return TextUtils.isEmpty(idNumber) ? "暂无" : idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber == null ? "" : idNumber;
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

        public String getTelephone() {
            return TextUtils.isEmpty(telephone) ? "暂无" : telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone == null ? "" : telephone;
        }

        public String getDisabilityCertificatePicture() {
            return TextUtils.isEmpty(disabilityCertificatePicture) ? "暂无" : disabilityCertificatePicture;
        }

        public void setDisabilityCertificatePicture(String disabilityCertificatePicture) {
            this.disabilityCertificatePicture = disabilityCertificatePicture == null ? "" :
                    disabilityCertificatePicture;
        }

        public String getCasePicture() {
            return TextUtils.isEmpty(casePicture) ? "暂无" : casePicture;
        }

        public void setCasePicture(String casePicture) {
            this.casePicture = casePicture == null ? "" : casePicture;
        }

        public String getLifePicture() {
            return TextUtils.isEmpty(lifePicture) ? "暂无" : lifePicture;
        }

        public void setLifePicture(String lifePicture) {
            this.lifePicture = lifePicture == null ? "" : lifePicture;
        }

        public String getApplicantSign() {
            return TextUtils.isEmpty(applicantSign) ? "暂无" : applicantSign;
        }

        public void setApplicantSign(String applicantSign) {
            this.applicantSign = applicantSign == null ? "" : applicantSign;
        }

        public String getDisabilityCertificateBackPicture() {
            return TextUtils.isEmpty(disabilityCertificateBackPicture) ? "暂无" : disabilityCertificateBackPicture;
        }

        public void setDisabilityCertificateBackPicture(String disabilityCertificateBackPicture) {
            this.disabilityCertificateBackPicture = disabilityCertificateBackPicture == null ? "" : disabilityCertificateBackPicture;
        }
    }
}
