package com.juntai.disabled.federation.bean.business.detail;

import android.text.TextUtils;

import com.juntai.disabled.basecomponent.base.BaseResult;

/**
 * @Author: tobato
 * @Description: 作用描述  居家托养详情
 * @CreateDate: 2021/2/28 8:34
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/28 8:34
 */
public class HomeCareDetailBean extends BaseResult {

    /**
     * error : null
     * data : {"id":3,"name":"王2","sex":1,"sexName":"女","age":22,"education":3,"educationName":"本科","category":3,
     * "categoryName":"智力残疾","level":4,"levelName":"四级","residentialAddress":"34,35","disabilityCertificate":"36",
     * "disabilityCertificatePicture":"http://www.juntaikeji
     * .com:17003//business_home_care/3755250221bb4e0e999fb1ccfe334110.jpeg","guardianIdPicture":"http://www
     * .juntaikeji.com:17003//business_home_care/8798f517fd434d48a3ebbc0227aeac49.jpeg",
     * "householdRegisterPicture":"http://www.juntaikeji
     * .com:17003//business_home_care/26f81fe911f540c8838d90bcd39d73a3.jpeg","lifePicture":"http://www.juntaikeji
     * .com:17003//business_home_care/390b66ecbd274caaae1f9ca7bb34189a.jpeg","housePicture":null,
     * "applicantSign":"http://www.juntaikeji.com:17003//business_home_care/c3a2dfde679549f6bd309975f2acab26.jpeg",
     * "guardianName":"王1","guardianSex":1,"guardianSexName":"女","guardianAge":11,"profession":"12",
     * "relationship":"13","telephone":"14","address":"15,16","familyEconomy":3,"familyEconomyName":"其他困难"}
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
         * id : 3
         * name : 王2
         * sex : 1
         * sexName : 女
         * age : 22
         * education : 3
         * educationName : 本科
         * category : 3
         * categoryName : 智力残疾
         * level : 4
         * levelName : 四级
         * residentialAddress : 34,35
         * disabilityCertificate : 36
         * disabilityCertificatePicture : http://www.juntaikeji
         * .com:17003//business_home_care/3755250221bb4e0e999fb1ccfe334110.jpeg
         * guardianIdPicture : http://www.juntaikeji.com:17003//business_home_care/8798f517fd434d48a3ebbc0227aeac49.jpeg
         * householdRegisterPicture : http://www.juntaikeji
         * .com:17003//business_home_care/26f81fe911f540c8838d90bcd39d73a3.jpeg
         * lifePicture : http://www.juntaikeji.com:17003//business_home_care/390b66ecbd274caaae1f9ca7bb34189a.jpeg
         * housePicture : null
         * applicantSign : http://www.juntaikeji.com:17003//business_home_care/c3a2dfde679549f6bd309975f2acab26.jpeg
         * guardianName : 王1
         * guardianSex : 1
         * guardianSexName : 女
         * guardianAge : 11
         * profession : 12
         * relationship : 13
         * telephone : 14
         * address : 15,16
         * familyEconomy : 3
         * familyEconomyName : 其他困难
         */

        private int id;
        private String name;
        private int sex;
        private String sexName;
        private int age;
        private int education;
        private String educationName;
        private int category;
        private String categoryName;
        private int level;
        private String levelName;
        private String residentialAddress;
        private String disabilityCertificate;
        private String disabilityCertificatePicture;
        private String guardianIdPicture;
        private String householdRegisterPicture;
        private String lifePicture;
        private String housePicture;
        private String applicantSign;
        private String guardianName;
        private int guardianSex;
        private String guardianSexName;
        private int guardianAge;
        private String profession;
        private String relationship;
        private String telephone;
        private String address;
        private int familyEconomy;
        private String familyEconomyName;
        private String idNumber;
        private String disabilityCertificateBackPicture;
        private int estatus;//评价状态（0已评价；1未评价）

        public int getEstatus() {
            return estatus;
        }

        public void setEstatus(int estatus) {
            this.estatus = estatus;
        }
        public String getDisabilityCertificateBackPicture() {
            return TextUtils.isEmpty(disabilityCertificateBackPicture) ? "暂无" : disabilityCertificateBackPicture;
        }

        public void setDisabilityCertificateBackPicture(String disabilityCertificateBackPicture) {
            this.disabilityCertificateBackPicture = disabilityCertificateBackPicture == null ? "" : disabilityCertificateBackPicture;
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

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getEducation() {
            return education;
        }

        public void setEducation(int education) {
            this.education = education;
        }

        public String getEducationName() {
            return TextUtils.isEmpty(educationName) ? "暂无" : educationName;
        }

        public void setEducationName(String educationName) {
            this.educationName = educationName == null ? "" : educationName;
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

        public String getResidentialAddress() {
            return TextUtils.isEmpty(residentialAddress) ? "暂无" : residentialAddress;
        }

        public void setResidentialAddress(String residentialAddress) {
            this.residentialAddress = residentialAddress == null ? "" : residentialAddress;
        }

        public String getDisabilityCertificate() {
            return TextUtils.isEmpty(disabilityCertificate) ? "暂无" : disabilityCertificate;
        }

        public void setDisabilityCertificate(String disabilityCertificate) {
            this.disabilityCertificate = disabilityCertificate == null ? "" : disabilityCertificate;
        }

        public String getDisabilityCertificatePicture() {
            return TextUtils.isEmpty(disabilityCertificatePicture) ? "暂无" : disabilityCertificatePicture;
        }

        public void setDisabilityCertificatePicture(String disabilityCertificatePicture) {
            this.disabilityCertificatePicture = disabilityCertificatePicture == null ? "" :
                    disabilityCertificatePicture;
        }

        public String getGuardianIdPicture() {
            return TextUtils.isEmpty(guardianIdPicture) ? "暂无" : guardianIdPicture;
        }

        public void setGuardianIdPicture(String guardianIdPicture) {
            this.guardianIdPicture = guardianIdPicture == null ? "" : guardianIdPicture;
        }

        public String getHouseholdRegisterPicture() {
            return TextUtils.isEmpty(householdRegisterPicture) ? "暂无" : householdRegisterPicture;
        }

        public void setHouseholdRegisterPicture(String householdRegisterPicture) {
            this.householdRegisterPicture = householdRegisterPicture == null ? "" : householdRegisterPicture;
        }

        public String getLifePicture() {
            return TextUtils.isEmpty(lifePicture) ? "暂无" : lifePicture;
        }

        public void setLifePicture(String lifePicture) {
            this.lifePicture = lifePicture == null ? "" : lifePicture;
        }

        public String getHousePicture() {
            return TextUtils.isEmpty(housePicture) ? "暂无" : housePicture;
        }

        public void setHousePicture(String housePicture) {
            this.housePicture = housePicture == null ? "" : housePicture;
        }

        public String getApplicantSign() {
            return TextUtils.isEmpty(applicantSign) ? "暂无" : applicantSign;
        }

        public void setApplicantSign(String applicantSign) {
            this.applicantSign = applicantSign == null ? "" : applicantSign;
        }

        public String getGuardianName() {
            return TextUtils.isEmpty(guardianName) ? "暂无" : guardianName;
        }

        public void setGuardianName(String guardianName) {
            this.guardianName = guardianName == null ? "" : guardianName;
        }

        public int getGuardianSex() {
            return guardianSex;
        }

        public void setGuardianSex(int guardianSex) {
            this.guardianSex = guardianSex;
        }

        public String getGuardianSexName() {
            return TextUtils.isEmpty(guardianSexName) ? "暂无" : guardianSexName;
        }

        public void setGuardianSexName(String guardianSexName) {
            this.guardianSexName = guardianSexName == null ? "" : guardianSexName;
        }

        public int getGuardianAge() {
            return guardianAge;
        }

        public void setGuardianAge(int guardianAge) {
            this.guardianAge = guardianAge;
        }

        public String getProfession() {
            return TextUtils.isEmpty(profession) ? "暂无" : profession;
        }

        public void setProfession(String profession) {
            this.profession = profession == null ? "" : profession;
        }

        public String getRelationship() {
            return TextUtils.isEmpty(relationship) ? "暂无" : relationship;
        }

        public void setRelationship(String relationship) {
            this.relationship = relationship == null ? "" : relationship;
        }

        public String getTelephone() {
            return TextUtils.isEmpty(telephone) ? "暂无" : telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone == null ? "" : telephone;
        }

        public String getAddress() {
            return TextUtils.isEmpty(address) ? "暂无" : address;
        }

        public void setAddress(String address) {
            this.address = address == null ? "" : address;
        }

        public int getFamilyEconomy() {
            return familyEconomy;
        }

        public void setFamilyEconomy(int familyEconomy) {
            this.familyEconomy = familyEconomy;
        }

        public String getFamilyEconomyName() {
            return TextUtils.isEmpty(familyEconomyName) ? "暂无" : familyEconomyName;
        }

        public void setFamilyEconomyName(String familyEconomyName) {
            this.familyEconomyName = familyEconomyName == null ? "" : familyEconomyName;
        }

        public String getIdNumber() {
            return TextUtils.isEmpty(idNumber) ? "暂无" : idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber == null ? "" : idNumber;
        }
    }
}
