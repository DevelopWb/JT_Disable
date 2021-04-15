package com.juntai.disabled.federation.bean.business.detail;

import android.text.TextUtils;

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
        private String poorFamilyExplain;
        private int alleviation;
        private String alleviationName;
        private int accountType;
        private String accountTypeName;
        private int medicalInsurance;
        private String medicalInsuranceName;
        private String recoveryInstitution;
        private String guardianApply;
        private String applicantSign;
        private String riPicture;
        private int estatus;//评价状态（0已评价；1未评价）

        public int getEstatus() {
            return estatus;
        }

        public void setEstatus(int estatus) {
            this.estatus = estatus;
        }
        public String getRiPicture() {
            return riPicture == null ? "" : riPicture;
        }

        public void setRiPicture(String riPicture) {
            this.riPicture = riPicture == null ? "" : riPicture;
        }

        /**
         * 左耳听力
         */
        private String leftEar;
        /**
         * 右耳听力
         */
        private String rightEar;

        /**
         * 是否佩戴助听器（0是；1否）
         */
        private Integer wear;
        /**
         * 佩戴时间（佩戴助听器填写）
         */
        private String wearTime;
        /**
         * 佩戴耳（0左；1右）（佩戴助听器填写）
         */
        private Integer wearEar;
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
            return grandName == null ? "" : grandName;
        }

        public void setGrandName(String grandName) {
            this.grandName = grandName == null ? "" : grandName;
        }

        public String getYear() {
            return year == null ? "" : year;
        }

        public void setYear(String year) {
            this.year = year == null ? "" : year;
        }

        public String getPhoto() {
            return photo == null ? "" : photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo == null ? "" : photo;
        }

        public String getName() {
            return name == null ? "" : name;
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
            return sexName == null ? "" : sexName;
        }

        public void setSexName(String sexName) {
            this.sexName = sexName == null ? "" : sexName;
        }

        public int getNation() {
            return nation;
        }

        public void setNation(int nation) {
            this.nation = nation;
        }

        public String getNationName() {
            return nationName == null ? "" : nationName;
        }

        public void setNationName(String nationName) {
            this.nationName = nationName == null ? "" : nationName;
        }

        public String getIdNumber() {
            return idNumber == null ? "" : idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber == null ? "" : idNumber;
        }

        public String getDisabilityCertificate() {
            return disabilityCertificate == null ? "" : disabilityCertificate;
        }

        public void setDisabilityCertificate(String disabilityCertificate) {
            this.disabilityCertificate = disabilityCertificate == null ? "" : disabilityCertificate;
        }

        public String getDiagnoseAgency() {
            return diagnoseAgency == null ? "" : diagnoseAgency;
        }

        public void setDiagnoseAgency(String diagnoseAgency) {
            this.diagnoseAgency = diagnoseAgency == null ? "" : diagnoseAgency;
        }

        public String getDiagnoseResult() {
            return diagnoseResult == null ? "" : diagnoseResult;
        }

        public void setDiagnoseResult(String diagnoseResult) {
            this.diagnoseResult = diagnoseResult == null ? "" : diagnoseResult;
        }

        public String getGuardian() {
            return guardian == null ? "" : guardian;
        }

        public void setGuardian(String guardian) {
            this.guardian = guardian == null ? "" : guardian;
        }

        public String getRelationship() {
            return relationship == null ? "" : relationship;
        }

        public void setRelationship(String relationship) {
            this.relationship = relationship == null ? "" : relationship;
        }

        public String getAddress() {
            return address == null ? "" : address;
        }

        public void setAddress(String address) {
            this.address = address == null ? "" : address;
        }

        public String getTelephone() {
            return telephone == null ? "" : telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone == null ? "" : telephone;
        }

        public int getIq() {
            return iq;
        }

        public void setIq(int iq) {
            this.iq = iq;
        }

        public String getIqName() {
            return iqName == null ? "" : iqName;
        }

        public void setIqName(String iqName) {
            this.iqName = iqName == null ? "" : iqName;
        }

        public String getOtherDisabled() {
            return otherDisabled == null ? "" : otherDisabled;
        }

        public void setOtherDisabled(String otherDisabled) {
            this.otherDisabled = otherDisabled == null ? "" : otherDisabled;
        }

        public int getFamilyEconomy() {
            return familyEconomy;
        }

        public void setFamilyEconomy(int familyEconomy) {
            this.familyEconomy = familyEconomy;
        }

        public String getFamilyEconomyName() {
            return familyEconomyName == null ? "" : familyEconomyName;
        }

        public void setFamilyEconomyName(String familyEconomyName) {
            this.familyEconomyName = familyEconomyName == null ? "" : familyEconomyName;
        }

        public int getPoorFamily() {
            return poorFamily;
        }

        public void setPoorFamily(int poorFamily) {
            this.poorFamily = poorFamily;
        }

        public String getPoorFamilyName() {
            return poorFamilyName == null ? "" : poorFamilyName;
        }

        public void setPoorFamilyName(String poorFamilyName) {
            this.poorFamilyName = poorFamilyName == null ? "" : poorFamilyName;
        }

        public String getPoorFamilyExplain() {
            return poorFamilyExplain == null ? "" : poorFamilyExplain;
        }

        public void setPoorFamilyExplain(String poorFamilyExplain) {
            this.poorFamilyExplain = poorFamilyExplain == null ? "" : poorFamilyExplain;
        }

        public int getAlleviation() {
            return alleviation;
        }

        public void setAlleviation(int alleviation) {
            this.alleviation = alleviation;
        }

        public String getAlleviationName() {
            return alleviationName == null ? "" : alleviationName;
        }

        public void setAlleviationName(String alleviationName) {
            this.alleviationName = alleviationName == null ? "" : alleviationName;
        }

        public int getAccountType() {
            return accountType;
        }

        public void setAccountType(int accountType) {
            this.accountType = accountType;
        }

        public String getAccountTypeName() {
            return accountTypeName == null ? "" : accountTypeName;
        }

        public void setAccountTypeName(String accountTypeName) {
            this.accountTypeName = accountTypeName == null ? "" : accountTypeName;
        }

        public int getMedicalInsurance() {
            return medicalInsurance;
        }

        public void setMedicalInsurance(int medicalInsurance) {
            this.medicalInsurance = medicalInsurance;
        }

        public String getMedicalInsuranceName() {
            return medicalInsuranceName == null ? "" : medicalInsuranceName;
        }

        public void setMedicalInsuranceName(String medicalInsuranceName) {
            this.medicalInsuranceName = medicalInsuranceName == null ? "" : medicalInsuranceName;
        }

        public String getRecoveryInstitution() {
            return recoveryInstitution == null ? "" : recoveryInstitution;
        }

        public void setRecoveryInstitution(String recoveryInstitution) {
            this.recoveryInstitution = recoveryInstitution == null ? "" : recoveryInstitution;
        }

        public String getGuardianApply() {
            return guardianApply == null ? "" : guardianApply;
        }

        public void setGuardianApply(String guardianApply) {
            this.guardianApply = guardianApply == null ? "" : guardianApply;
        }

        public String getApplicantSign() {
            return applicantSign == null ? "" : applicantSign;
        }

        public void setApplicantSign(String applicantSign) {
            this.applicantSign = applicantSign == null ? "" : applicantSign;
        }

        public String getLeftEar() {
            return leftEar == null ? "" : leftEar;
        }

        public void setLeftEar(String leftEar) {
            this.leftEar = leftEar == null ? "" : leftEar;
        }

        public String getRightEar() {
            return rightEar == null ? "" : rightEar;
        }

        public void setRightEar(String rightEar) {
            this.rightEar = rightEar == null ? "" : rightEar;
        }

        public Integer getWear() {
            return wear;
        }

        public void setWear(Integer wear) {
            this.wear = wear;
        }

        public String getWearTime() {
            return wearTime == null ? "" : wearTime;
        }

        public void setWearTime(String wearTime) {
            this.wearTime = wearTime == null ? "" : wearTime;
        }

        public Integer getWearEar() {
            return wearEar;
        }

        public void setWearEar(Integer wearEar) {
            this.wearEar = wearEar;
        }

        public String getBirth() {
            return birth == null ? "" : birth;
        }

        public void setBirth(String birth) {
            this.birth = birth == null ? "" : birth;
        }

        public String getPostCode() {
            return postCode == null ? "" : postCode;
        }

        public void setPostCode(String postCode) {
            this.postCode = postCode == null ? "" : postCode;
        }

        public String getWorkingUnit() {
            return workingUnit == null ? "" : workingUnit;
        }

        public void setWorkingUnit(String workingUnit) {
            this.workingUnit = workingUnit == null ? "" : workingUnit;
        }

        public String getGuardianId() {
            return guardianId == null ? "" : guardianId;
        }

        public void setGuardianId(String guardianId) {
            this.guardianId = guardianId == null ? "" : guardianId;
        }

        public String getPhone() {
            return phone == null ? "" : phone;
        }

        public void setPhone(String phone) {
            this.phone = phone == null ? "" : phone;
        }

        public String getPostalAddress() {
            return postalAddress == null ? "" : postalAddress;
        }

        public void setPostalAddress(String postalAddress) {
            this.postalAddress = postalAddress == null ? "" : postalAddress;
        }

        public String getFindTime() {
            return findTime == null ? "" : findTime;
        }

        public void setFindTime(String findTime) {
            this.findTime = findTime == null ? "" : findTime;
        }

        public Integer getGeneticHistory() {
            return geneticHistory;
        }

        public void setGeneticHistory(Integer geneticHistory) {
            this.geneticHistory = geneticHistory;
        }

        public String getGeneticHistoryRelationship() {
            return geneticHistoryRelationship == null ? "" : geneticHistoryRelationship;
        }

        public void setGeneticHistoryRelationship(String geneticHistoryRelationship) {
            this.geneticHistoryRelationship = geneticHistoryRelationship == null ? "" : geneticHistoryRelationship;
        }

        public Integer getRecovery() {
            return recovery;
        }

        public void setRecovery(Integer recovery) {
            this.recovery = recovery;
        }

        public Integer getAccompany() {
            return accompany;
        }

        public void setAccompany(Integer accompany) {
            this.accompany = accompany;
        }

        public String getAccompanyRelationship() {
            return accompanyRelationship == null ? "" : accompanyRelationship;
        }

        public void setAccompanyRelationship(String accompanyRelationship) {
            this.accompanyRelationship = accompanyRelationship == null ? "" : accompanyRelationship;
        }

        public void setRecoveryProject(Integer recoveryProject) {
            this.recoveryProject = recoveryProject;
        }

        public String getTypeName() {
            return typeName == null ? "" : typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName == null ? "" : typeName;
        }
    }
}
