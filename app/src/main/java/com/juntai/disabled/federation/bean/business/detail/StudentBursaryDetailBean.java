package com.juntai.disabled.federation.bean.business.detail;

import android.text.TextUtils;

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
        private String wechatPhone;
        private String townStreet;
        private String idPictureFile;
        private String lifePictureFile;
        private String accountName;
        private String bankName;
        private String cardNumber;
        private String startSchoolTimeName;
        private String idPicture;
        private String disabilityCertificateBackPicture;
        private int startSchoolTime;

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

        public int getNation() {
            return nation;
        }

        public void setNation(int nation) {
            this.nation = nation;
        }

        public String getNationName() {
            return TextUtils.isEmpty(nationName) ? "暂无" : nationName;
        }

        public void setNationName(String nationName) {
            this.nationName = nationName == null ? "" : nationName;
        }

        public String getNativePlace() {
            return TextUtils.isEmpty(nativePlace) ? "暂无" : nativePlace;
        }

        public void setNativePlace(String nativePlace) {
            this.nativePlace = nativePlace == null ? "" : nativePlace;
        }

        public String getIdNumber() {
            return TextUtils.isEmpty(idNumber) ? "暂无" : idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber == null ? "" : idNumber;
        }

        public String getSeverelyDisabledName() {
            return TextUtils.isEmpty(severelyDisabledName) ? "暂无" : severelyDisabledName;
        }

        public void setSeverelyDisabledName(String severelyDisabledName) {
            this.severelyDisabledName = severelyDisabledName == null ? "" : severelyDisabledName;
        }

        public String getRelationship() {
            return TextUtils.isEmpty(relationship) ? "暂无" : relationship;
        }

        public void setRelationship(String relationship) {
            this.relationship = relationship == null ? "" : relationship;
        }

        public String getDisabilityCertificate() {
            return TextUtils.isEmpty(disabilityCertificate) ? "暂无" : disabilityCertificate;
        }

        public void setDisabilityCertificate(String disabilityCertificate) {
            this.disabilityCertificate = disabilityCertificate == null ? "" : disabilityCertificate;
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

        public String getCollege() {
            return TextUtils.isEmpty(college) ? "暂无" : college;
        }

        public void setCollege(String college) {
            this.college = college == null ? "" : college;
        }

        public String getMajor() {
            return TextUtils.isEmpty(major) ? "暂无" : major;
        }

        public void setMajor(String major) {
            this.major = major == null ? "" : major;
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

        public String getSystem() {
            return TextUtils.isEmpty(system) ? "暂无" : system;
        }

        public void setSystem(String system) {
            this.system = system == null ? "" : system;
        }

        public String getTelephone() {
            return TextUtils.isEmpty(telephone) ? "暂无" : telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone == null ? "" : telephone;
        }

        public String getEmail() {
            return TextUtils.isEmpty(email) ? "暂无" : email;
        }

        public void setEmail(String email) {
            this.email = email == null ? "" : email;
        }

        public String getFatherName() {
            return TextUtils.isEmpty(fatherName) ? "暂无" : fatherName;
        }

        public void setFatherName(String fatherName) {
            this.fatherName = fatherName == null ? "" : fatherName;
        }

        public String getMotherName() {
            return TextUtils.isEmpty(motherName) ? "暂无" : motherName;
        }

        public void setMotherName(String motherName) {
            this.motherName = motherName == null ? "" : motherName;
        }

        public String getAddress() {
            return TextUtils.isEmpty(address) ? "暂无" : address;
        }

        public void setAddress(String address) {
            this.address = address == null ? "" : address;
        }

        public String getPostCode() {
            return TextUtils.isEmpty(postCode) ? "暂无" : postCode;
        }

        public void setPostCode(String postCode) {
            this.postCode = postCode == null ? "" : postCode;
        }

        public int getIsFirst() {
            return isFirst;
        }

        public void setIsFirst(int isFirst) {
            this.isFirst = isFirst;
        }

        public String getIsFirstName() {
            return TextUtils.isEmpty(isFirstName) ? "暂无" : isFirstName;
        }

        public void setIsFirstName(String isFirstName) {
            this.isFirstName = isFirstName == null ? "" : isFirstName;
        }

        public String getStudentIdPicture() {
            return TextUtils.isEmpty(studentIdPicture) ? "暂无" : studentIdPicture;
        }

        public void setStudentIdPicture(String studentIdPicture) {
            this.studentIdPicture = studentIdPicture == null ? "" : studentIdPicture;
        }

        public String getDisabilityCertificatePicture() {
            return TextUtils.isEmpty(disabilityCertificatePicture) ? "暂无" : disabilityCertificatePicture;
        }

        public void setDisabilityCertificatePicture(String disabilityCertificatePicture) {
            this.disabilityCertificatePicture = disabilityCertificatePicture == null ? "" :
                    disabilityCertificatePicture;
        }

        public String getLifePicture() {
            return TextUtils.isEmpty(lifePicture) ? "暂无" : lifePicture;
        }

        public void setLifePicture(String lifePicture) {
            this.lifePicture = lifePicture == null ? "" : lifePicture;
        }

        public String getHouseholdRegisterPicture() {
            return TextUtils.isEmpty(householdRegisterPicture) ? "暂无" : householdRegisterPicture;
        }

        public void setHouseholdRegisterPicture(String householdRegisterPicture) {
            this.householdRegisterPicture = householdRegisterPicture == null ? "" : householdRegisterPicture;
        }

        public String getNoticePicture() {
            return TextUtils.isEmpty(noticePicture) ? "暂无" : noticePicture;
        }

        public void setNoticePicture(String noticePicture) {
            this.noticePicture = noticePicture == null ? "" : noticePicture;
        }

        public String getPayPicture() {
            return TextUtils.isEmpty(payPicture) ? "暂无" : payPicture;
        }

        public void setPayPicture(String payPicture) {
            this.payPicture = payPicture == null ? "" : payPicture;
        }

        public String getStudentCertificatePicture() {
            return TextUtils.isEmpty(studentCertificatePicture) ? "暂无" : studentCertificatePicture;
        }

        public void setStudentCertificatePicture(String studentCertificatePicture) {
            this.studentCertificatePicture = studentCertificatePicture == null ? "" : studentCertificatePicture;
        }

        public String getWechatPhone() {
            return TextUtils.isEmpty(wechatPhone) ? "暂无" : wechatPhone;
        }

        public void setWechatPhone(String wechatPhone) {
            this.wechatPhone = wechatPhone == null ? "" : wechatPhone;
        }

        public String getTownStreet() {
            return TextUtils.isEmpty(townStreet) ? "暂无" : townStreet;
        }

        public void setTownStreet(String townStreet) {
            this.townStreet = townStreet == null ? "" : townStreet;
        }

        public String getIdPictureFile() {
            return TextUtils.isEmpty(idPictureFile) ? "暂无" : idPictureFile;
        }

        public void setIdPictureFile(String idPictureFile) {
            this.idPictureFile = idPictureFile == null ? "" : idPictureFile;
        }

        public String getLifePictureFile() {
            return TextUtils.isEmpty(lifePictureFile) ? "暂无" : lifePictureFile;
        }

        public void setLifePictureFile(String lifePictureFile) {
            this.lifePictureFile = lifePictureFile == null ? "" : lifePictureFile;
        }

        public String getAccountName() {
            return TextUtils.isEmpty(accountName) ? "暂无" : accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName == null ? "" : accountName;
        }

        public String getBankName() {
            return TextUtils.isEmpty(bankName) ? "暂无" : bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName == null ? "" : bankName;
        }

        public String getCardNumber() {
            return TextUtils.isEmpty(cardNumber) ? "暂无" : cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber == null ? "" : cardNumber;
        }

        public String getStartSchoolTimeName() {
            return TextUtils.isEmpty(startSchoolTimeName) ? "暂无" : startSchoolTimeName;
        }

        public void setStartSchoolTimeName(String startSchoolTimeName) {
            this.startSchoolTimeName = startSchoolTimeName == null ? "" : startSchoolTimeName;
        }

        public String getIdPicture() {
            return TextUtils.isEmpty(idPicture) ? "暂无" : idPicture;
        }

        public void setIdPicture(String idPicture) {
            this.idPicture = idPicture == null ? "" : idPicture;
        }

        public String getDisabilityCertificateBackPicture() {
            return TextUtils.isEmpty(disabilityCertificateBackPicture) ? "暂无" : disabilityCertificateBackPicture;
        }

        public void setDisabilityCertificateBackPicture(String disabilityCertificateBackPicture) {
            this.disabilityCertificateBackPicture = disabilityCertificateBackPicture == null ? "" : disabilityCertificateBackPicture;
        }

        public int getStartSchoolTime() {
            return startSchoolTime;
        }

        public void setStartSchoolTime(int startSchoolTime) {
            this.startSchoolTime = startSchoolTime;
        }
    }
}
