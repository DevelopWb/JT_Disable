package com.juntai.disabled.federation.bean.business.detail;

import android.text.TextUtils;

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
        private String jobSituationName;
        private String trainsName;
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

        public String getResidenceAddress() {
            return TextUtils.isEmpty(residenceAddress) ? "暂无" : residenceAddress;
        }

        public void setResidenceAddress(String residenceAddress) {
            this.residenceAddress = residenceAddress == null ? "" : residenceAddress;
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

        public String getIdNumber() {
            return TextUtils.isEmpty(idNumber) ? "暂无" : idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber == null ? "" : idNumber;
        }

        public String getContacts() {
            return TextUtils.isEmpty(contacts) ? "暂无" : contacts;
        }

        public void setContacts(String contacts) {
            this.contacts = contacts == null ? "" : contacts;
        }

        public String getTelephone() {
            return TextUtils.isEmpty(telephone) ? "暂无" : telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone == null ? "" : telephone;
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

        public String getSpecialty() {
            return TextUtils.isEmpty(specialty) ? "暂无" : specialty;
        }

        public void setSpecialty(String specialty) {
            this.specialty = specialty == null ? "" : specialty;
        }

        public String getJobSituation() {
            return TextUtils.isEmpty(jobSituation) ? "暂无" : jobSituation;
        }

        public void setJobSituation(String jobSituation) {
            this.jobSituation = jobSituation == null ? "" : jobSituation;
        }

        public String getTrains() {
            return TextUtils.isEmpty(trains) ? "暂无" : trains;
        }

        public void setTrains(String trains) {
            this.trains = trains == null ? "" : trains;
        }

        public String getWorkingResume() {
            return TextUtils.isEmpty(workingResume) ? "暂无" : workingResume;
        }

        public void setWorkingResume(String workingResume) {
            this.workingResume = workingResume == null ? "" : workingResume;
        }

        public String getTrainingResume() {
            return TextUtils.isEmpty(trainingResume) ? "暂无" : trainingResume;
        }

        public void setTrainingResume(String trainingResume) {
            this.trainingResume = trainingResume == null ? "" : trainingResume;
        }

        public String getDisabilityCertificatePicture() {
            return TextUtils.isEmpty(disabilityCertificatePicture) ? "暂无" : disabilityCertificatePicture;
        }

        public void setDisabilityCertificatePicture(String disabilityCertificatePicture) {
            this.disabilityCertificatePicture = disabilityCertificatePicture == null ? "" : disabilityCertificatePicture;
        }

        public String getJobSituationName() {
            return TextUtils.isEmpty(jobSituationName) ? "暂无" : jobSituationName;
        }

        public void setJobSituationName(String jobSituationName) {
            this.jobSituationName = jobSituationName == null ? "" : jobSituationName;
        }

        public String getTrainsName() {
            return TextUtils.isEmpty(trainsName) ? "暂无" : trainsName;
        }

        public void setTrainsName(String trainsName) {
            this.trainsName = trainsName == null ? "" : trainsName;
        }

        public String getDisabilityCertificateBackPicture() {
            return TextUtils.isEmpty(disabilityCertificateBackPicture) ? "暂无" : disabilityCertificateBackPicture;
        }

        public void setDisabilityCertificateBackPicture(String disabilityCertificateBackPicture) {
            this.disabilityCertificateBackPicture = disabilityCertificateBackPicture == null ? "" : disabilityCertificateBackPicture;
        }
    }
}
