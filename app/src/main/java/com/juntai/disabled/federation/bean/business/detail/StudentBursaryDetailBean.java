package com.juntai.disabled.federation.bean.business.detail;

import com.juntai.disabled.basecomponent.base.BaseResult;

/**
 * @Author: tobato
 * @Description: 作用描述  残疾学生助学金详情
 * @CreateDate: 2021/2/28 14:26
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/28 14:26
 */
public class StudentBursaryDetailBean extends BaseResult {

    /**
     * error : null
     * data : {"id":3,"photo":"http://www.juntaikeji
     * .com:17003//business_student_family_grant/dcaa0b99510d4e9e960cf1d80103b4f3.jpeg","name":"第1年以上","sex":1,
     * "sexName":"女","birth":"90","nation":4,"nationName":"藏族","nativePlace":"看看","idNumber":"88",
     * "severelyDisabledName":"888","relationship":"你现在","disabilityCertificate":"555","category":3,
     * "categoryName":"智力残疾","level":3,"levelName":"三级","college":"我","major":"专业","education":2,
     * "educationName":"硕士研究生","system":null,"telephone":"999","email":"666","fatherName":"111","motherName":"222",
     * "address":"333","postCode":"444","isFirst":0,"isFirstName":"是","studentIdPicture":"http://www.juntaikeji
     * .com:17003//business_student_family_grant/5b678fc9cdf349d7bba6527cff4ea73e.jpeg",
     * "disabilityCertificatePicture":"http://www.juntaikeji
     * .com:17003//business_student_family_grant/2a824b9c0fc648479931ca4ef58ab726.jpeg","lifePicture":"http://www
     * .juntaikeji.com:17003//business_student_family_grant/333558c32d674522ba211284dde0bcf3.jpeg",
     * "householdRegisterPicture":"http://www.juntaikeji
     * .com:17003//business_student_family_grant/f38401fcc034483b9458ddd3fb603f02.jpeg","noticePicture":"http://www
     * .juntaikeji.com:17003//business_student_family_grant/c0c225d799fb48bcb81809a2d6239652.jpeg",
     * "payPicture":"http://www.juntaikeji.com:17003//business_student_family_grant/c8863d5f10e04bc39f386eca81ce83bd
     * .jpeg","studentCertificatePicture":null}
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
         * photo : http://www.juntaikeji.com:17003//business_student_family_grant/dcaa0b99510d4e9e960cf1d80103b4f3.jpeg
         * name : 第1年以上
         * sex : 1
         * sexName : 女
         * birth : 90
         * nation : 4
         * nationName : 藏族
         * nativePlace : 看看
         * idNumber : 88
         * severelyDisabledName : 888
         * relationship : 你现在
         * disabilityCertificate : 555
         * category : 3
         * categoryName : 智力残疾
         * level : 3
         * levelName : 三级
         * college : 我
         * major : 专业
         * education : 2
         * educationName : 硕士研究生
         * system : null
         * telephone : 999
         * email : 666
         * fatherName : 111
         * motherName : 222
         * address : 333
         * postCode : 444
         * isFirst : 0
         * isFirstName : 是
         * studentIdPicture : http://www.juntaikeji
         * .com:17003//business_student_family_grant/5b678fc9cdf349d7bba6527cff4ea73e.jpeg
         * disabilityCertificatePicture : http://www.juntaikeji
         * .com:17003//business_student_family_grant/2a824b9c0fc648479931ca4ef58ab726.jpeg
         * lifePicture : http://www.juntaikeji
         * .com:17003//business_student_family_grant/333558c32d674522ba211284dde0bcf3.jpeg
         * householdRegisterPicture : http://www.juntaikeji
         * .com:17003//business_student_family_grant/f38401fcc034483b9458ddd3fb603f02.jpeg
         * noticePicture : http://www.juntaikeji
         * .com:17003//business_student_family_grant/c0c225d799fb48bcb81809a2d6239652.jpeg
         * payPicture : http://www.juntaikeji
         * .com:17003//business_student_family_grant/c8863d5f10e04bc39f386eca81ce83bd.jpeg
         * studentCertificatePicture : null
         */

        private int id;
        private String photo;
        private String name;
        private int sex;
        private String sexName;
        private String birth;
        private int nation;
        private String nationName;
        private String nativePlace;
        private String idNumber;
        private String severelyDisabledName;
        private String relationship;
        private String disabilityCertificate;
        private int category;
        private String categoryName;
        private int level;
        private String levelName;
        private String college;
        private String major;
        private int education;
        private String educationName;
        private String system;
        private String telephone;
        private String email;
        private String fatherName;
        private String motherName;
        private String address;
        private String postCode;
        private int isFirst;
        private String isFirstName;
        private String studentIdPicture;
        private String disabilityCertificatePicture;
        private String lifePicture;
        private String householdRegisterPicture;
        private String noticePicture;
        private String payPicture;
        private String studentCertificatePicture;
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

        public int getNation() {
            return nation;
        }

        public void setNation(int nation) {
            this.nation = nation;
        }

        public String getNationName() {
            return nationName;
        }

        public void setNationName(String nationName) {
            this.nationName = nationName;
        }

        public String getNativePlace() {
            return nativePlace;
        }

        public void setNativePlace(String nativePlace) {
            this.nativePlace = nativePlace;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public String getSeverelyDisabledName() {
            return severelyDisabledName;
        }

        public void setSeverelyDisabledName(String severelyDisabledName) {
            this.severelyDisabledName = severelyDisabledName;
        }

        public String getRelationship() {
            return relationship;
        }

        public void setRelationship(String relationship) {
            this.relationship = relationship;
        }

        public String getDisabilityCertificate() {
            return disabilityCertificate;
        }

        public void setDisabilityCertificate(String disabilityCertificate) {
            this.disabilityCertificate = disabilityCertificate;
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

        public String getCollege() {
            return college;
        }

        public void setCollege(String college) {
            this.college = college;
        }

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
        }

        public int getEducation() {
            return education;
        }

        public void setEducation(int education) {
            this.education = education;
        }

        public String getEducationName() {
            return educationName;
        }

        public void setEducationName(String educationName) {
            this.educationName = educationName;
        }


        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFatherName() {
            return fatherName;
        }

        public void setFatherName(String fatherName) {
            this.fatherName = fatherName;
        }

        public String getMotherName() {
            return motherName;
        }

        public void setMotherName(String motherName) {
            this.motherName = motherName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPostCode() {
            return postCode;
        }

        public void setPostCode(String postCode) {
            this.postCode = postCode;
        }

        public int getIsFirst() {
            return isFirst;
        }

        public void setIsFirst(int isFirst) {
            this.isFirst = isFirst;
        }

        public String getIsFirstName() {
            return isFirstName;
        }

        public void setIsFirstName(String isFirstName) {
            this.isFirstName = isFirstName;
        }

        public String getStudentIdPicture() {
            return studentIdPicture;
        }

        public void setStudentIdPicture(String studentIdPicture) {
            this.studentIdPicture = studentIdPicture;
        }

        public String getDisabilityCertificatePicture() {
            return disabilityCertificatePicture;
        }

        public void setDisabilityCertificatePicture(String disabilityCertificatePicture) {
            this.disabilityCertificatePicture = disabilityCertificatePicture;
        }

        public String getLifePicture() {
            return lifePicture;
        }

        public void setLifePicture(String lifePicture) {
            this.lifePicture = lifePicture;
        }

        public String getHouseholdRegisterPicture() {
            return householdRegisterPicture;
        }

        public void setHouseholdRegisterPicture(String householdRegisterPicture) {
            this.householdRegisterPicture = householdRegisterPicture;
        }

        public String getNoticePicture() {
            return noticePicture;
        }

        public void setNoticePicture(String noticePicture) {
            this.noticePicture = noticePicture;
        }

        public String getPayPicture() {
            return payPicture;
        }

        public void setPayPicture(String payPicture) {
            this.payPicture = payPicture;
        }

        public String getSystem() {
            return system == null ? "" : system;
        }

        public void setSystem(String system) {
            this.system = system == null ? "" : system;
        }

        public String getStudentCertificatePicture() {
            return studentCertificatePicture == null ? "" : studentCertificatePicture;
        }

        public void setStudentCertificatePicture(String studentCertificatePicture) {
            this.studentCertificatePicture = studentCertificatePicture == null ? "" : studentCertificatePicture;
        }
    }
}
