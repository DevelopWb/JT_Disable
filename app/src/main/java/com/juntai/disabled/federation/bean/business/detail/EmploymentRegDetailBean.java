package com.juntai.disabled.federation.bean.business.detail;

import android.text.TextUtils;

import com.juntai.disabled.basecomponent.base.BaseResult;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021/2/24 16:38
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/24 16:38
 */
public class EmploymentRegDetailBean extends BaseResult {


    /**
     * error : null
     * data : {"id":4,"photo":"http://www.juntaikeji
     * .com:17003//business_obtain_employment/0603df26b2684c549cf2190eab5fe4a8.jpeg","name":"1","sex":1,
     * "sexName":"女","birth":"2","nation":1,"nationName":"汉族","education":1,"educationName":"博士","specialty":"3",
     * "marriage":0,"marriageName":"未婚","way":0,"wayName":null,"residenceAddress":"4","residentialAddress":"5",
     * "category":null,"categoryName":null,"level":null,"levelName":null,"telephone":"6","disabilityCertificate":"7",
     * "hearingDisability":0,"hearingDisabilityName":"是","physicalDisability":1,"physicalDisabilityName":"上肢残疾",
     * "mineResume":"8","postIntention":"9","areaIntention":"10","monthlySalaryIntention":"11",
     * "disabilityCertificatePicture":"http://www.juntaikeji
     * .com:17003//business_obtain_employment/4fea786d216d4f298fc4eec1897dc87d.jpeg","trainingIntention":"12",
     * "remark":"13"}
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
         * photo : http://www.juntaikeji.com:17003//business_obtain_employment/0603df26b2684c549cf2190eab5fe4a8.jpeg
         * name : 1
         * sex : 1
         * sexName : 女
         * birth : 2
         * nation : 1
         * nationName : 汉族
         * education : 1
         * educationName : 博士
         * specialty : 3
         * marriage : 0
         * marriageName : 未婚
         * way : 0
         * wayName : null
         * residenceAddress : 4
         * residentialAddress : 5
         * category : null
         * categoryName : null
         * level : null
         * levelName : null
         * telephone : 6
         * disabilityCertificate : 7
         * hearingDisability : 0
         * hearingDisabilityName : 是
         * physicalDisability : 1
         * physicalDisabilityName : 上肢残疾
         * mineResume : 8
         * postIntention : 9
         * areaIntention : 10
         * monthlySalaryIntention : 11
         * disabilityCertificatePicture : http://www.juntaikeji
         * .com:17003//business_obtain_employment/4fea786d216d4f298fc4eec1897dc87d.jpeg
         * trainingIntention : 12
         * remark : 13
         */

        private int id;
        private String photo;
        private String name;
        private int sex;
        private String sexName;
        private String birth;
        private int nation;
        private String nationName;
        private int education;
        private String educationName;
        private String specialty;
        private int marriage;
        private String marriageName;
        private int way;
        private String wayName;
        private String residenceAddress;
        private String residentialAddress;
        private int category;
        private String categoryName;
        private int level;
        private String levelName;
        private String telephone;
        private String disabilityCertificate;
        private int hearingDisability;
        private String hearingDisabilityName;
        private int physicalDisability;
        private String physicalDisabilityName;
        private String mineResume;
        private String postIntention;
        private String areaIntention;
        private String monthlySalaryIntention;
        private String disabilityCertificatePicture;
        private String trainingIntention;
        private String remark;
        private String idNumber;
        //残疾证反面照片
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

        public String getSpecialty() {
            return TextUtils.isEmpty(specialty) ? "暂无" : specialty;
        }

        public void setSpecialty(String specialty) {
            this.specialty = specialty == null ? "" : specialty;
        }

        public int getMarriage() {
            return marriage;
        }

        public void setMarriage(int marriage) {
            this.marriage = marriage;
        }

        public String getMarriageName() {
            return TextUtils.isEmpty(marriageName) ? "暂无" : marriageName;
        }

        public void setMarriageName(String marriageName) {
            this.marriageName = marriageName == null ? "" : marriageName;
        }

        public int getWay() {
            return way;
        }

        public void setWay(int way) {
            this.way = way;
        }

        public String getWayName() {
            return TextUtils.isEmpty(wayName) ? "暂无" : wayName;
        }

        public void setWayName(String wayName) {
            this.wayName = wayName == null ? "" : wayName;
        }

        public String getResidenceAddress() {
            return TextUtils.isEmpty(residenceAddress) ? "暂无" : residenceAddress;
        }

        public void setResidenceAddress(String residenceAddress) {
            this.residenceAddress = residenceAddress == null ? "" : residenceAddress;
        }

        public String getResidentialAddress() {
            return TextUtils.isEmpty(residentialAddress) ? "暂无" : residentialAddress;
        }

        public void setResidentialAddress(String residentialAddress) {
            this.residentialAddress = residentialAddress == null ? "" : residentialAddress;
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

        public String getTelephone() {
            return TextUtils.isEmpty(telephone) ? "暂无" : telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone == null ? "" : telephone;
        }

        public String getDisabilityCertificate() {
            return TextUtils.isEmpty(disabilityCertificate) ? "暂无" : disabilityCertificate;
        }

        public void setDisabilityCertificate(String disabilityCertificate) {
            this.disabilityCertificate = disabilityCertificate == null ? "" : disabilityCertificate;
        }

        public int getHearingDisability() {
            return hearingDisability;
        }

        public void setHearingDisability(int hearingDisability) {
            this.hearingDisability = hearingDisability;
        }

        public String getHearingDisabilityName() {
            return TextUtils.isEmpty(hearingDisabilityName) ? "暂无" : hearingDisabilityName;
        }

        public void setHearingDisabilityName(String hearingDisabilityName) {
            this.hearingDisabilityName = hearingDisabilityName == null ? "" : hearingDisabilityName;
        }

        public int getPhysicalDisability() {
            return physicalDisability;
        }

        public void setPhysicalDisability(int physicalDisability) {
            this.physicalDisability = physicalDisability;
        }

        public String getPhysicalDisabilityName() {
            return TextUtils.isEmpty(physicalDisabilityName) ? "暂无" : physicalDisabilityName;
        }

        public void setPhysicalDisabilityName(String physicalDisabilityName) {
            this.physicalDisabilityName = physicalDisabilityName == null ? "" : physicalDisabilityName;
        }

        public String getMineResume() {
            return TextUtils.isEmpty(mineResume) ? "暂无" : mineResume;
        }

        public void setMineResume(String mineResume) {
            this.mineResume = mineResume == null ? "" : mineResume;
        }

        public String getPostIntention() {
            return TextUtils.isEmpty(postIntention) ? "暂无" : postIntention;
        }

        public void setPostIntention(String postIntention) {
            this.postIntention = postIntention == null ? "" : postIntention;
        }

        public String getAreaIntention() {
            return TextUtils.isEmpty(areaIntention) ? "暂无" : areaIntention;
        }

        public void setAreaIntention(String areaIntention) {
            this.areaIntention = areaIntention == null ? "" : areaIntention;
        }

        public String getMonthlySalaryIntention() {
            return TextUtils.isEmpty(monthlySalaryIntention) ? "暂无" : monthlySalaryIntention;
        }

        public void setMonthlySalaryIntention(String monthlySalaryIntention) {
            this.monthlySalaryIntention = monthlySalaryIntention == null ? "" : monthlySalaryIntention;
        }

        public String getDisabilityCertificatePicture() {
            return TextUtils.isEmpty(disabilityCertificatePicture) ? "暂无" : disabilityCertificatePicture;
        }

        public void setDisabilityCertificatePicture(String disabilityCertificatePicture) {
            this.disabilityCertificatePicture = disabilityCertificatePicture == null ? "" : disabilityCertificatePicture;
        }

        public String getTrainingIntention() {
            return TextUtils.isEmpty(trainingIntention) ? "暂无" : trainingIntention;
        }

        public void setTrainingIntention(String trainingIntention) {
            this.trainingIntention = trainingIntention == null ? "" : trainingIntention;
        }

        public String getRemark() {
            return TextUtils.isEmpty(remark) ? "暂无" : remark;
        }

        public void setRemark(String remark) {
            this.remark = remark == null ? "" : remark;
        }

        public String getIdNumber() {
            return TextUtils.isEmpty(idNumber) ? "暂无" : idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber == null ? "" : idNumber;
        }

        public String getDisabilityCertificateBackPicture() {
            return TextUtils.isEmpty(disabilityCertificateBackPicture) ? "暂无" : disabilityCertificateBackPicture;
        }

        public void setDisabilityCertificateBackPicture(String disabilityCertificateBackPicture) {
            this.disabilityCertificateBackPicture = disabilityCertificateBackPicture == null ? "" : disabilityCertificateBackPicture;
        }
    }
}
