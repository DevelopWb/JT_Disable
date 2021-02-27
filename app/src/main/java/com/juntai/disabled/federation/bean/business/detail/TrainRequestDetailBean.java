package com.juntai.disabled.federation.bean.business.detail;

import com.juntai.disabled.basecomponent.base.BaseResult;

/**
 * @Author: tobato
 * @Description: 作用描述  培训申请详情
 * @CreateDate: 2021/2/27 16:13
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/27 16:13
 */
public class TrainRequestDetailBean extends BaseResult {

    /**
     * error : null
     * data : {"id":2,"photo":"http://www.juntaikeji.com:17003//business_train/9c5f3a5097ca48b9b8e5d5c7903082b4
     * .jpeg","name":"1","sex":1,"sexName":"女","birth":"2","nation":3,"nationName":"回族","residenceAddress":"3,4",
     * "education":2,"educationName":"硕士","marriage":1,"marriageName":"已婚(有配偶)","idNumber":"5","contacts":"7",
     * "telephone":"6","residentialAddress":"8","category":4,"categoryName":"精神残疾","level":1,"levelName":"一级",
     * "specialty":"9","jobSituation":"10","trains":"11","workingResume":"12","trainingResume":"13",
     * "disabilityCertificatePicture":"http://www.juntaikeji
     * .com:17003//business_train/2cbe020e613541ad8b5cf004e67d21b3.jpeg"}
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
         * id : 2
         * photo : http://www.juntaikeji.com:17003//business_train/9c5f3a5097ca48b9b8e5d5c7903082b4.jpeg
         * name : 1
         * sex : 1
         * sexName : 女
         * birth : 2
         * nation : 3
         * nationName : 回族
         * residenceAddress : 3,4
         * education : 2
         * educationName : 硕士
         * marriage : 1
         * marriageName : 已婚(有配偶)
         * idNumber : 5
         * contacts : 7
         * telephone : 6
         * residentialAddress : 8
         * category : 4
         * categoryName : 精神残疾
         * level : 1
         * levelName : 一级
         * specialty : 9
         * jobSituation : 10
         * trains : 11
         * workingResume : 12
         * trainingResume : 13
         * disabilityCertificatePicture : http://www.juntaikeji
         * .com:17003//business_train/2cbe020e613541ad8b5cf004e67d21b3.jpeg
         */

        private int id;
        private String photo;
        private String name;
        private int sex;
        private String sexName;
        private String birth;
        private int nation;
        private String nationName;
        private String residenceAddress;
        private int education;
        private String educationName;
        private int marriage;
        private String marriageName;
        private String idNumber;
        private String contacts;
        private String telephone;
        private String residentialAddress;
        private int category;
        private String categoryName;
        private int level;
        private String levelName;
        private String specialty;
        private String jobSituation;
        private String trains;
        private String workingResume;
        private String trainingResume;
        private String disabilityCertificatePicture;

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

        public String getResidenceAddress() {
            return residenceAddress;
        }

        public void setResidenceAddress(String residenceAddress) {
            this.residenceAddress = residenceAddress;
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

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public String getContacts() {
            return contacts;
        }

        public void setContacts(String contacts) {
            this.contacts = contacts;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getResidentialAddress() {
            return residentialAddress;
        }

        public void setResidentialAddress(String residentialAddress) {
            this.residentialAddress = residentialAddress;
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

        public String getSpecialty() {
            return specialty;
        }

        public void setSpecialty(String specialty) {
            this.specialty = specialty;
        }

        public String getJobSituation() {
            return jobSituation;
        }

        public void setJobSituation(String jobSituation) {
            this.jobSituation = jobSituation;
        }

        public String getTrains() {
            return trains;
        }

        public void setTrains(String trains) {
            this.trains = trains;
        }

        public String getWorkingResume() {
            return workingResume;
        }

        public void setWorkingResume(String workingResume) {
            this.workingResume = workingResume;
        }

        public String getTrainingResume() {
            return trainingResume;
        }

        public void setTrainingResume(String trainingResume) {
            this.trainingResume = trainingResume;
        }

        public String getDisabilityCertificatePicture() {
            return disabilityCertificatePicture;
        }

        public void setDisabilityCertificatePicture(String disabilityCertificatePicture) {
            this.disabilityCertificatePicture = disabilityCertificatePicture;
        }
    }
}
