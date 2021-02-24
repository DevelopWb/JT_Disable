package com.juntai.disabled.federation.bean.business.detail;

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

        public String getSpecialty() {
            return specialty;
        }

        public void setSpecialty(String specialty) {
            this.specialty = specialty;
        }

        public int getMarriage() {
            return marriage;
        }

        public void setMarriage(int marriage) {
            this.marriage = marriage;
        }

        public String getMarriageName() {
            return marriageName;
        }

        public void setMarriageName(String marriageName) {
            this.marriageName = marriageName;
        }

        public int getWay() {
            return way;
        }

        public void setWay(int way) {
            this.way = way;
        }

        public String getResidenceAddress() {
            return residenceAddress;
        }

        public void setResidenceAddress(String residenceAddress) {
            this.residenceAddress = residenceAddress;
        }

        public String getResidentialAddress() {
            return residentialAddress;
        }

        public void setResidentialAddress(String residentialAddress) {
            this.residentialAddress = residentialAddress;
        }

        public String getWayName() {
            return wayName == null ? "" : wayName;
        }

        public void setWayName(String wayName) {
            this.wayName = wayName == null ? "" : wayName;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public String getCategoryName() {
            return categoryName == null ? "" : categoryName;
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
            return levelName == null ? "" : levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName == null ? "" : levelName;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getDisabilityCertificate() {
            return disabilityCertificate;
        }

        public void setDisabilityCertificate(String disabilityCertificate) {
            this.disabilityCertificate = disabilityCertificate;
        }

        public int getHearingDisability() {
            return hearingDisability;
        }

        public void setHearingDisability(int hearingDisability) {
            this.hearingDisability = hearingDisability;
        }

        public String getHearingDisabilityName() {
            return hearingDisabilityName;
        }

        public void setHearingDisabilityName(String hearingDisabilityName) {
            this.hearingDisabilityName = hearingDisabilityName;
        }

        public int getPhysicalDisability() {
            return physicalDisability;
        }

        public void setPhysicalDisability(int physicalDisability) {
            this.physicalDisability = physicalDisability;
        }

        public String getPhysicalDisabilityName() {
            return physicalDisabilityName;
        }

        public void setPhysicalDisabilityName(String physicalDisabilityName) {
            this.physicalDisabilityName = physicalDisabilityName;
        }

        public String getMineResume() {
            return mineResume;
        }

        public void setMineResume(String mineResume) {
            this.mineResume = mineResume;
        }

        public String getPostIntention() {
            return postIntention;
        }

        public void setPostIntention(String postIntention) {
            this.postIntention = postIntention;
        }

        public String getAreaIntention() {
            return areaIntention;
        }

        public void setAreaIntention(String areaIntention) {
            this.areaIntention = areaIntention;
        }

        public String getMonthlySalaryIntention() {
            return monthlySalaryIntention;
        }

        public void setMonthlySalaryIntention(String monthlySalaryIntention) {
            this.monthlySalaryIntention = monthlySalaryIntention;
        }

        public String getDisabilityCertificatePicture() {
            return disabilityCertificatePicture;
        }

        public void setDisabilityCertificatePicture(String disabilityCertificatePicture) {
            this.disabilityCertificatePicture = disabilityCertificatePicture;
        }

        public String getTrainingIntention() {
            return trainingIntention;
        }

        public void setTrainingIntention(String trainingIntention) {
            this.trainingIntention = trainingIntention;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
