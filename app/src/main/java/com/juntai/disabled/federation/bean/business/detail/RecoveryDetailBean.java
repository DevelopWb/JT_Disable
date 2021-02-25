package com.juntai.disabled.federation.bean.business.detail;

import com.juntai.disabled.basecomponent.base.BaseResult;

/**
 * @Author: tobato
 * @Description: 作用描述  精准康复详情
 * @CreateDate: 2021/2/25 15:33
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/25 15:33
 */
public class RecoveryDetailBean extends BaseResult {

    /**
     * error : null
     * data : {"id":2,"grand":1,"grandName":"国家","year":"2019","photo":"http://www.juntaikeji
     * .com:17003//business_children_intellectual/3f0a00e33e38433d909a76dea04e4a8f.jpeg","name":"小","sex":1,
     * "sexName":"女","nation":1,"nationName":"汉族","idNumber":"22","disabilityCertificate":"6","diagnoseAgency":"1",
     * "diagnoseResult":"2","guardian":"33","relationship":"4","address":"5","telephone":"6","iq":1,"iqName":"<=25",
     * "otherDisabled":"[视力, 精神]","familyEconomy":2,"familyEconomyName":"当地政府有关部门认定的低收入家庭","poorFamily":2,
     * "poorFamilyName":"一户多残","poorFamilyExplain":null,"alleviation":1,"alleviationName":"否","accountType":1,
     * "accountTypeName":"农业户口","medicalInsurance":4,"medicalInsuranceName":"无医疗保险","recoveryInstitution":"888",
     * "guardianApply":"9","applicantSign":"http://www.juntaikeji
     * .com:17003//business_children_intellectual/95f9745e20c54057b6b119b65c24ed27.jpeg"}
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
         * grand : 1
         * grandName : 国家
         * year : 2019
         * photo : http://www.juntaikeji.com:17003//business_children_intellectual/3f0a00e33e38433d909a76dea04e4a8f.jpeg
         * name : 小
         * sex : 1
         * sexName : 女
         * nation : 1
         * nationName : 汉族
         * idNumber : 22
         * disabilityCertificate : 6
         * diagnoseAgency : 1
         * diagnoseResult : 2
         * guardian : 33
         * relationship : 4
         * address : 5
         * telephone : 6
         * iq : 1
         * iqName : <=25
         * otherDisabled : [视力, 精神]
         * familyEconomy : 2
         * familyEconomyName : 当地政府有关部门认定的低收入家庭
         * poorFamily : 2
         * poorFamilyName : 一户多残
         * poorFamilyExplain : null
         * alleviation : 1
         * alleviationName : 否
         * accountType : 1
         * accountTypeName : 农业户口
         * medicalInsurance : 4
         * medicalInsuranceName : 无医疗保险
         * recoveryInstitution : 888
         * guardianApply : 9
         * applicantSign : http://www.juntaikeji
         * .com:17003//business_children_intellectual/95f9745e20c54057b6b119b65c24ed27.jpeg
         */

        private int id;
        private int grand;
        private String grandName;
        private String year;
        private String photo;
        private String name;
        private int sex;
        private String sexName;
        private int nation;
        private String nationName;
        private String idNumber;
        private String disabilityCertificate;
        private String diagnoseAgency;
        private String diagnoseResult;
        private String guardian;
        private String relationship;
        private String address;
        private String telephone;
        private int iq;
        private String iqName;
        private String otherDisabled;
        private int familyEconomy;
        private String familyEconomyName;
        private int poorFamily;
        private String poorFamilyName;
        private Object poorFamilyExplain;
        private int alleviation;
        private String alleviationName;
        private int accountType;
        private String accountTypeName;
        private int medicalInsurance;
        private String medicalInsuranceName;
        private String recoveryInstitution;
        private String guardianApply;
        private String applicantSign;
        /**
         * 出生年月
         */
        private String birth;
        /**
         * 邮政编码
         */
        private String postCode;
        /**
         * 工作单位
         */
        private String workingUnit;
        /**
         * 监护人身份证号码
         */
        private String guardianId;
        /**
         * 手机号码
         */
        private String phone;
        /**
         * 通讯地址
         */
        private String postalAddress;
        /**
         * 发现耳聋月龄
         */
        private String findTime;
        /**
         * 是否有家族耳聋史（0有；1无）
         */
        private Integer geneticHistory;
        /**
         * 与儿童的关系（有家族耳聋史填写）
         */
        private String geneticHistoryRelationship;
        /**
         * 目前康复状态（1机构康复；2家庭康复；3未接受康复）
         */
        private Integer recovery;
        /**
         * 接受救助后家庭中有无专人陪伴康复（0有；1无）
         */
        private Integer accompany;
        /**
         * 与儿童的关系（有陪伴康复填写）
         */
        private String accompanyRelationship;
        /**
         * 康复需求项目（1聋儿人工耳蜗植入及术后首次语训救助项目；2聋儿（助听器或耳蜗）语训项目）
         */
        private Integer recoveryProject;

        public Integer getRecoveryProject() {
            return recoveryProject;
        }
        /**
         * 脑瘫类型（1痉挛型；2手足徐动型；3共济失调；4弛缓型；5混合型）
         */
        private String typeName;
        public String getTypeName() {
            return typeName == null ? "" : typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName == null ? "" : typeName;
        }

        public void setRecoveryProject(Integer recoveryProject) {
            this.recoveryProject = recoveryProject;
        }

        public String getAccompanyRelationship() {
            return accompanyRelationship == null ? "" : accompanyRelationship;
        }

        public void setAccompanyRelationship(String accompanyRelationship) {
            this.accompanyRelationship = accompanyRelationship == null ? "" : accompanyRelationship;
        }

        public Integer getAccompany() {
            return accompany;
        }

        public void setAccompany(Integer accompany) {
            this.accompany = accompany;
        }

        public Integer getRecovery() {
            return recovery;
        }

        public void setRecovery(Integer recovery) {
            this.recovery = recovery;
        }

        public String getGeneticHistoryRelationship() {
            return geneticHistoryRelationship == null ? "" : geneticHistoryRelationship;
        }

        public void setGeneticHistoryRelationship(String geneticHistoryRelationship) {
            this.geneticHistoryRelationship = geneticHistoryRelationship == null ? "" : geneticHistoryRelationship;
        }

        public Integer getGeneticHistory() {
            return geneticHistory;
        }

        public void setGeneticHistory(Integer geneticHistory) {
            this.geneticHistory = geneticHistory;
        }

        public String getFindTime() {
            return findTime == null ? "" : findTime;
        }

        public void setFindTime(String findTime) {
            this.findTime = findTime == null ? "" : findTime;
        }

        public String getPostalAddress() {
            return postalAddress == null ? "" : postalAddress;
        }

        public void setPostalAddress(String postalAddress) {
            this.postalAddress = postalAddress == null ? "" : postalAddress;
        }

        public String getPhone() {
            return phone == null ? "" : phone;
        }

        public void setPhone(String phone) {
            this.phone = phone == null ? "" : phone;
        }

        public String getGuardianId() {
            return guardianId == null ? "" : guardianId;
        }

        public void setGuardianId(String guardianId) {
            this.guardianId = guardianId == null ? "" : guardianId;
        }

        public String getWorkingUnit() {
            return workingUnit == null ? "" : workingUnit;
        }

        public void setWorkingUnit(String workingUnit) {
            this.workingUnit = workingUnit == null ? "" : workingUnit;
        }

        public String getPostCode() {
            return postCode == null ? "" : postCode;
        }

        public void setPostCode(String postCode) {
            this.postCode = postCode == null ? "" : postCode;
        }

        public String getBirth() {
            return birth == null ? "" : birth;
        }

        public void setBirth(String birth) {
            this.birth = birth == null ? "" : birth;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getGrand() {
            return grand;
        }

        public void setGrand(int grand) {
            this.grand = grand;
        }

        public String getGrandName() {
            return grandName;
        }

        public void setGrandName(String grandName) {
            this.grandName = grandName;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
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

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public String getDisabilityCertificate() {
            return disabilityCertificate;
        }

        public void setDisabilityCertificate(String disabilityCertificate) {
            this.disabilityCertificate = disabilityCertificate;
        }

        public String getDiagnoseAgency() {
            return diagnoseAgency;
        }

        public void setDiagnoseAgency(String diagnoseAgency) {
            this.diagnoseAgency = diagnoseAgency;
        }

        public String getDiagnoseResult() {
            return diagnoseResult;
        }

        public void setDiagnoseResult(String diagnoseResult) {
            this.diagnoseResult = diagnoseResult;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public int getIq() {
            return iq;
        }

        public void setIq(int iq) {
            this.iq = iq;
        }

        public String getIqName() {
            return iqName;
        }

        public void setIqName(String iqName) {
            this.iqName = iqName;
        }

        public String getOtherDisabled() {
            return otherDisabled;
        }

        public void setOtherDisabled(String otherDisabled) {
            this.otherDisabled = otherDisabled;
        }

        public int getFamilyEconomy() {
            return familyEconomy;
        }

        public void setFamilyEconomy(int familyEconomy) {
            this.familyEconomy = familyEconomy;
        }

        public String getFamilyEconomyName() {
            return familyEconomyName;
        }

        public void setFamilyEconomyName(String familyEconomyName) {
            this.familyEconomyName = familyEconomyName;
        }

        public int getPoorFamily() {
            return poorFamily;
        }

        public void setPoorFamily(int poorFamily) {
            this.poorFamily = poorFamily;
        }

        public String getPoorFamilyName() {
            return poorFamilyName;
        }

        public void setPoorFamilyName(String poorFamilyName) {
            this.poorFamilyName = poorFamilyName;
        }

        public Object getPoorFamilyExplain() {
            return poorFamilyExplain;
        }

        public void setPoorFamilyExplain(Object poorFamilyExplain) {
            this.poorFamilyExplain = poorFamilyExplain;
        }

        public int getAlleviation() {
            return alleviation;
        }

        public void setAlleviation(int alleviation) {
            this.alleviation = alleviation;
        }

        public String getAlleviationName() {
            return alleviationName;
        }

        public void setAlleviationName(String alleviationName) {
            this.alleviationName = alleviationName;
        }

        public int getAccountType() {
            return accountType;
        }

        public void setAccountType(int accountType) {
            this.accountType = accountType;
        }

        public String getAccountTypeName() {
            return accountTypeName;
        }

        public void setAccountTypeName(String accountTypeName) {
            this.accountTypeName = accountTypeName;
        }

        public int getMedicalInsurance() {
            return medicalInsurance;
        }

        public void setMedicalInsurance(int medicalInsurance) {
            this.medicalInsurance = medicalInsurance;
        }

        public String getMedicalInsuranceName() {
            return medicalInsuranceName;
        }

        public void setMedicalInsuranceName(String medicalInsuranceName) {
            this.medicalInsuranceName = medicalInsuranceName;
        }

        public String getRecoveryInstitution() {
            return recoveryInstitution;
        }

        public void setRecoveryInstitution(String recoveryInstitution) {
            this.recoveryInstitution = recoveryInstitution;
        }

        public String getGuardianApply() {
            return guardianApply;
        }

        public void setGuardianApply(String guardianApply) {
            this.guardianApply = guardianApply;
        }

        public String getApplicantSign() {
            return applicantSign;
        }

        public void setApplicantSign(String applicantSign) {
            this.applicantSign = applicantSign;
        }
    }
}
