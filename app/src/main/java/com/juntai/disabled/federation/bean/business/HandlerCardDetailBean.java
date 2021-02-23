package com.juntai.disabled.federation.bean.business;

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
     * data : {"id":13,"type":null,"photo":"http://www.juntaikeji
     * .com:17003//business_disability_certificate/f304b7c459cf4b54a64c43bd9d4a8567.jpeg","name":"1","sex":1,
     * "birth":"2","nation":1,"marriage":0,"education":1,"nativePlace":"3","idNumber":"4","address":"5",
     * "postCode":"6","accountType":1,"guardian":"7","relationship":"8","telephone":"9","workingUnit":"10",
     * "profession":"11","unitNature":"12","unitWelfare":1,"commitment":"我是残疾人13,残疾人监护人14,因(视力)残
     * 疾，申办残疾人证，我承诺同意将姓名、所在村居（社区）、残疾评定类别及等级，在河东区残联网站进行公示，公示期一年。并承诺配合区残联做好因公示可能产生的异议事项落实，自愿承担可能因异议事项落实产生的评定结论调整等后果。",
     * "applicantSign":"http://www.juntaikeji
     * .com:17003//business_disability_certificate/a8e737d79d7f4f7597e258c19cd06132.jpeg"}
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
         * id : 13
         * type : null
         * photo : http://www.juntaikeji.com:17003//business_disability_certificate/f304b7c459cf4b54a64c43bd9d4a8567
         * .jpeg
         * name : 1
         * sex : 1
         * birth : 2
         * nation : 1
         * marriage : 0
         * education : 1
         * nativePlace : 3
         * idNumber : 4
         * address : 5
         * postCode : 6
         * accountType : 1
         * guardian : 7
         * relationship : 8
         * telephone : 9
         * workingUnit : 10
         * profession : 11
         * unitNature : 12
         * unitWelfare : 1
         * commitment : 我是残疾人13,残疾人监护人14,因(视力)残
         * 疾，申办残疾人证，我承诺同意将姓名、所在村居（社区）、残疾评定类别及等级，在河东区残联网站进行公示，公示期一年。并承诺配合区残联做好因公示可能产生的异议事项落实，自愿承担可能因异议事项落实产生的评定结论调整等后果。
         * applicantSign : http://www.juntaikeji
         * .com:17003//business_disability_certificate/a8e737d79d7f4f7597e258c19cd06132.jpeg
         */

        private int id;
        @SerializedName("type")
        private Object typeX;
        private String photo;
        private String name;
        private int sex;
        private String birth;
        private int nation;
        private int marriage;
        private int education;
        private String nativePlace;
        private String idNumber;
        private String address;
        private String postCode;
        private int accountType;
        private String guardian;
        private String relationship;
        private String telephone;
        private String workingUnit;
        private String profession;
        private String unitNature;
        private int unitWelfare;
        private String commitment;
        private String applicantSign;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getTypeX() {
            return typeX;
        }

        public void setTypeX(Object typeX) {
            this.typeX = typeX;
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

        public int getMarriage() {
            return marriage;
        }

        public void setMarriage(int marriage) {
            this.marriage = marriage;
        }

        public int getEducation() {
            return education;
        }

        public void setEducation(int education) {
            this.education = education;
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

        public int getAccountType() {
            return accountType;
        }

        public void setAccountType(int accountType) {
            this.accountType = accountType;
        }

        public String getGuardian() {
            return guardian;
        }

        public void setGuardian(String guardian) {
            this.guardian = guardian;
        }

        public String getRelationship() {
            return relationship;
        }

        public void setRelationship(String relationship) {
            this.relationship = relationship;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getWorkingUnit() {
            return workingUnit;
        }

        public void setWorkingUnit(String workingUnit) {
            this.workingUnit = workingUnit;
        }

        public String getProfession() {
            return profession;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public String getUnitNature() {
            return unitNature;
        }

        public void setUnitNature(String unitNature) {
            this.unitNature = unitNature;
        }

        public int getUnitWelfare() {
            return unitWelfare;
        }

        public void setUnitWelfare(int unitWelfare) {
            this.unitWelfare = unitWelfare;
        }

        public String getCommitment() {
            return commitment;
        }

        public void setCommitment(String commitment) {
            this.commitment = commitment;
        }

        public String getApplicantSign() {
            return applicantSign;
        }

        public void setApplicantSign(String applicantSign) {
            this.applicantSign = applicantSign;
        }
    }
}
