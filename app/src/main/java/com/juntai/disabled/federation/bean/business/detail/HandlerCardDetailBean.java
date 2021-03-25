package com.juntai.disabled.federation.bean.business.detail;

import com.google.gson.annotations.SerializedName;
import com.juntai.disabled.basecomponent.base.BaseResult;

/**
 * @Author: tobato
 * @Description: 作用描述  办理残疾证详情
 * @CreateDate: 2021/2/23 16:11
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/23 16:11
 */
public class HandlerCardDetailBean extends BaseResult {


    /**
     * error : null
     * data : {"id":14,"type":1,"typeName":"新申请","photo":"http://www.juntaikeji
     * .com:17003//business_disability_certificate/8657fbc302d445a8842d1fd735d78e72.jpeg","name":"1","sex":1,
     * "sexName":"女","birth":"2","nation":1,"nationName":"汉族","marriage":0,"marriageName":"未婚","education":1,
     * "nativePlace":"3","idNumber":"4","address":"5","postCode":"6","accountType":1,"accountTypeName":"农业户口",
     * "guardian":"7","relationship":"8","telephone":"9","workingUnit":"10","profession":"11","unitNature":"12",
     * "unitWelfare":0,"unitWelfareName":"是","commitment":"我是残疾人13,残疾人监护人14,因(视力)残
     * 疾，申办残疾人证，我承诺同意将姓名、所在村居（社区）、残疾评定类别及等级，在河东区残联网站进行公示，公示期一年。并承诺配合区残联做好因公示可能产生的异议事项落实，自愿承担可能因异议事项落实产生的评定结论调整等后果。",
     * "applicantSign":"http://www.juntaikeji
     * .com:17003//business_disability_certificate/af89878da9494c13befc7ff1145f1ddb.jpeg"}
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
         * id : 14
         * type : 1
         * typeName : 新申请
         * photo : http://www.juntaikeji.com:17003//business_disability_certificate/8657fbc302d445a8842d1fd735d78e72
         * .jpeg
         * name : 1
         * sex : 1
         * sexName : 女
         * birth : 2
         * nation : 1
         * nationName : 汉族
         * marriage : 0
         * marriageName : 未婚
         * education : 1
         * nativePlace : 3
         * idNumber : 4
         * address : 5
         * postCode : 6
         * accountType : 1
         * accountTypeName : 农业户口
         * guardian : 7
         * relationship : 8
         * telephone : 9
         * workingUnit : 10
         * profession : 11
         * unitNature : 12
         * unitWelfare : 0
         * unitWelfareName : 是
         * commitment : 我是残疾人13,残疾人监护人14,因(视力)残
         * 疾，申办残疾人证，我承诺同意将姓名、所在村居（社区）、残疾评定类别及等级，在河东区残联网站进行公示，公示期一年。并承诺配合区残联做好因公示可能产生的异议事项落实，自愿承担可能因异议事项落实产生的评定结论调整等后果。
         * applicantSign : http://www.juntaikeji
         * .com:17003//business_disability_certificate/af89878da9494c13befc7ff1145f1ddb.jpeg
         */

        private int id;
        @SerializedName("type")
        private int typeX;
        private String typeName;
        private String photo;
        private String name;
        private int sex;
        private String sexName;
        private String birth;
        private int nation;
        private String nationName;
        private int marriage;
        private String marriageName;
        private int education;
        private String nativePlace;
        private String idNumber;
        /**
         * 文化程度
         */
        private String educationName;
        private String address;
        private String postCode;
        private int accountType;
        private String accountTypeName;
        private String guardian;
        private String relationship;
        private String telephone;
        private String workingUnit;
        private String profession;
        private String unitNature;
        private int unitWelfare;
        private String unitWelfareName;
        private String commitment;
        private String applicantSign;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTypeX() {
            return typeX;
        }

        public void setTypeX(int typeX) {
            this.typeX = typeX;
        }

        public String getTypeName() {
            return typeName == null ? "暂无" : typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName == null ? "" : typeName;
        }

        public String getPhoto() {
            return photo == null ? "暂无" : photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo == null ? "" : photo;
        }

        public String getName() {
            return name == null ? "暂无" : name;
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
            return sexName == null ? "暂无" : sexName;
        }

        public void setSexName(String sexName) {
            this.sexName = sexName == null ? "" : sexName;
        }

        public String getBirth() {
            return birth == null ? "暂无" : birth;
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
            return nationName == null ? "暂无" : nationName;
        }

        public void setNationName(String nationName) {
            this.nationName = nationName == null ? "" : nationName;
        }

        public int getMarriage() {
            return marriage;
        }

        public void setMarriage(int marriage) {
            this.marriage = marriage;
        }

        public String getMarriageName() {
            return marriageName == null ? "暂无" : marriageName;
        }

        public void setMarriageName(String marriageName) {
            this.marriageName = marriageName == null ? "" : marriageName;
        }

        public int getEducation() {
            return education;
        }

        public void setEducation(int education) {
            this.education = education;
        }

        public String getNativePlace() {
            return nativePlace == null ? "暂无" : nativePlace;
        }

        public void setNativePlace(String nativePlace) {
            this.nativePlace = nativePlace == null ? "" : nativePlace;
        }

        public String getIdNumber() {
            return idNumber == null ? "暂无" : idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber == null ? "" : idNumber;
        }

        public String getEducationName() {
            return educationName == null ? "暂无" : educationName;
        }

        public void setEducationName(String educationName) {
            this.educationName = educationName == null ? "" : educationName;
        }

        public String getAddress() {
            return address == null ? "暂无" : address;
        }

        public void setAddress(String address) {
            this.address = address == null ? "" : address;
        }

        public String getPostCode() {
            return postCode == null ? "暂无" : postCode;
        }

        public void setPostCode(String postCode) {
            this.postCode = postCode == null ? "" : postCode;
        }

        public int getAccountType() {
            return accountType;
        }

        public void setAccountType(int accountType) {
            this.accountType = accountType;
        }

        public String getAccountTypeName() {
            return accountTypeName == null ? "暂无" : accountTypeName;
        }

        public void setAccountTypeName(String accountTypeName) {
            this.accountTypeName = accountTypeName == null ? "" : accountTypeName;
        }

        public String getGuardian() {
            return guardian == null ? "暂无" : guardian;
        }

        public void setGuardian(String guardian) {
            this.guardian = guardian == null ? "" : guardian;
        }

        public String getRelationship() {
            return relationship == null ? "暂无" : relationship;
        }

        public void setRelationship(String relationship) {
            this.relationship = relationship == null ? "" : relationship;
        }

        public String getTelephone() {
            return telephone == null ? "暂无" : telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone == null ? "" : telephone;
        }

        public String getWorkingUnit() {
            return workingUnit == null ? "暂无" : workingUnit;
        }

        public void setWorkingUnit(String workingUnit) {
            this.workingUnit = workingUnit == null ? "" : workingUnit;
        }

        public String getProfession() {
            return profession == null ? "暂无" : profession;
        }

        public void setProfession(String profession) {
            this.profession = profession == null ? "" : profession;
        }

        public String getUnitNature() {
            return unitNature == null ? "暂无" : unitNature;
        }

        public void setUnitNature(String unitNature) {
            this.unitNature = unitNature == null ? "" : unitNature;
        }

        public int getUnitWelfare() {
            return unitWelfare;
        }

        public void setUnitWelfare(int unitWelfare) {
            this.unitWelfare = unitWelfare;
        }

        public String getUnitWelfareName() {
            return unitWelfareName == null ? "暂无" : unitWelfareName;
        }

        public void setUnitWelfareName(String unitWelfareName) {
            this.unitWelfareName = unitWelfareName == null ? "" : unitWelfareName;
        }

        public String getCommitment() {
            return commitment == null ? "暂无" : commitment;
        }

        public void setCommitment(String commitment) {
            this.commitment = commitment == null ? "" : commitment;
        }

        public String getApplicantSign() {
            return applicantSign == null ? "暂无" : applicantSign;
        }

        public void setApplicantSign(String applicantSign) {
            this.applicantSign = applicantSign == null ? "" : applicantSign;
        }
    }
}
