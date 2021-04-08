package com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness;

import com.juntai.disabled.basecomponent.mvp.IPresenter;
import com.juntai.disabled.basecomponent.mvp.IView;

import java.util.List;

import okhttp3.RequestBody;

/**
 * @aouther tobato
 * @description 描述
 * @date 2020/3/12 16:00
 */
public interface BusinessContract {
    String CREAT_BUSINESS = "creat_business";//创建业务
    String TABLE_TITLE_NAME = "姓名";//表单的标题
    String TABLE_TITLE_NAME_FAMILY = "F姓名";//表单的标题
    String TABLE_TITLE_NAME_PERSIONAL = "P姓名";//表单的标题
    String TABLE_TITLE_CHILD_NAME = "儿童姓名";//表单的标题
    String TABLE_TITLE_PIC = "照片";//表单的标题
    String TABLE_TITLE_SEX = "性别";//表单的标题
    String TABLE_TITLE_SEX_FAMILY = "F性别";//表单的标题
    String TABLE_TITLE_SEX_PERSIONAL = "P性别";//表单的标题
    String TABLE_TITLE_DISABILITY_HEAR = "听力残疾";//表单的标题
    String TABLE_TITLE_DISABILITY_LIMB = "肢体残疾";//表单的标题
    String TABLE_TITLE_BIRTH = "出生年月";//表单的标题
    String TABLE_TITLE_AGE_FAMILY = "F年龄";//表单的标题
    String TABLE_TITLE_AGE_PERSIONAL = "P年龄";//表单的标题
    String TABLE_TITLE_RESUME = "本人简历";//表单的标题
    String TABLE_TITLE_RESUME_WORK = "工作简历";//表单的标题
    String TABLE_TITLE_RESUME_TRAIN = "培训简历";//表单的标题
    String TABLE_TITLE_DISABLE_CARD_ID = "残疾证号";//表单的标题
    String TABLE_TITLE_NATION = "民族";//民族
    String TABLE_TITLE_MARRIAGE = "婚姻状况";//婚姻
    String TABLE_TITLE_EDUCATION_LEVEL = "文化程度";//
    String TABLE_TITLE_HOMETOWN = "籍贯";//
    String TABLE_TITLE_HOME_ADDR = "户籍所在地";//
    String TABLE_TITLE_HUKOU_ADDR = "户口所在地";//
    String TABLE_TITLE_STREET = "街道";//
    String TABLE_TITLE_STREET_FAMILY = "F街道";//
    String TABLE_TITLE_STREET_PERSIONAL = "P街道";//
    String TABLE_TITLE_VILLAGE = "(村)社区";//
    String TABLE_TITLE_VILLAGE_FAMILY = "F(村)社区";//
    String TABLE_TITLE_VILLAGE_PERSIONAL = "P(村)社区";//
    String TABLE_TITLE_WANTED_POST = "意向岗位";//
    String TABLE_TITLE_WORK_AREA = "择业地区";//
    String TABLE_TITLE_IDCARD = "身份证号";//
    String TABLE_TITLE_CHILD_IDCARD = "儿童身份证号";//
    String TABLE_TITLE_ADDR = "现住址";//
    String TABLE_TITLE_ADDR_LIVE_NOW = "现居住地";//
    String TABLE_TITLE_HUKOU = "户口类别";//
    String TABLE_TITLE_GUARDIAN = "监护人";//
    String TABLE_TITLE_GUARDIAN_RELATION = "与监护人关系";//
    String TABLE_TITLE_PHONE = "联系电话";//
    String TABLE_TITLE_CONTACT_MODE = "联系方式";//
    String TABLE_TITLE_CONTACTER = "联系人";//
    String TABLE_TITLE_CURRENT_LIVE_ADDR = "目前居住地";//
    String TABLE_TITLE_WORKER = "工作单位";//
    String TABLE_TITLE_WORKER_TYPE = "职业工种";//
    String TABLE_TITLE_JOB = "职业";//
    String TABLE_TITLE_UNIT_NATURE = "单位性质";//
    String TABLE_TITLE_IS_WEEL_COMPANY = "是否福利企业";//
    String TABLE_TITLE_DISABLE_PIC = "申请人残疾证照片";//
    String TABLE_TITLE_DISABLED_PIC_IN_HEALTH_POSITION = "孩子在康复机构照片";//
    String TABLE_TITLE_DISABLE_PHOTO = "残疾证照片";//
    String TABLE_TITLE_GUARDIAN_ID_PIC = "监护人身份证拍照";//
    String TABLE_TITLE_GUARDIAN_HUJI_PIC = "监护人户籍证明照片";//
    String TABLE_TITLE_HUKOU_RELATION_PIC = "户口本能证明监护关系页拍照";//
    String TABLE_TITLE_STUDENT_CARD_PIC = "学生证照片";//
    String TABLE_TITLE_ADMISSION_NOTICE_PIC = "入学通知书照片";//
    String TABLE_TITLE_TUITION_PIC = "缴费凭证照片";//学费
    String TABLE_TITLE_MATERIAL_PIC = "病例材料照片";//
    String TABLE_TITLE_LIFE_PIC = "申请人生活照片";//
    String TABLE_TITLE_LIFE_PIC_MYSELF = "本人生活照照片";//
    String TABLE_TITLE_LIFE_PIC_HOUSE = "房屋外观照片";//
    String TABLE_TITLE_SPECIAL = "特长";//
    String TABLE_TITLE_ADMISSION_TIME = "入学时间";//
    String TABLE_TITLE_DISABILITY_KINDS = "残疾类别";//
    String TABLE_TITLE_DISABILITY_LEVEL = "残疾等级";//
    String TABLE_TITLE_ADMISSION_COLLEGE = "录取院校";//
    String TABLE_TITLE_ADMISSION_PERSIONAL = "录取专业";//
    String TABLE_TITLE_EDUCATION = "学历";//
    String TABLE_TITLE_SCHOOL_SYSTEM = "学制";//
    String TABLE_TITLE_FATHER_NAME = "父亲姓名";//
    String TABLE_TITLE_MATHER_NAME = "母亲姓名";//
    String TABLE_TITLE_HOME_ADDRESS = "家庭通讯地址";//
    String TABLE_TITLE_WCHAT_PHONE = "微信手机号";//
    String TABLE_TITLE_TOWN_STREET = "镇街";//
    String TABLE_TITLE_ACCOUNT_NAME = "户名";//
    String TABLE_TITLE_ACCOUNT_BANK = "开户行";//
    String TABLE_TITLE_CARD_NUM = "卡号";//
    String TABLE_TITLE_DISABILITY_PEOPLE_NAME = "重残人员姓名";//
    String TABLE_TITLE_DISABILITY_STUDENT_RELATION = "与该生关系";//
    String TABLE_TITLE_DISABILITY_PEOPLE_RELATION = "与残疾人关系";//
    String TABLE_TITLE_STUDENT_IDCARD = "学生身份证照片";//
    String TABLE_TITLE_PIC_IDCARD = "身份证照片";//
    String TABLE_TITLE_PRESENT_DISBILITY_IDCARD = "家长重度残疾证照片";//
    String TABLE_TITLE_GROUP_PHOTO = "学生与家长生活合影照片";//
    String TABLE_TITLE_ACCOUNT_BOOK = "户口本说明家庭关系照片或实际抚养证明";//
    String TABLE_TITLE_YEAR = "年";//
    String TABLE_TITLE_PROJECT_LEVEL = "项目级别";//
    String TABLE_TITLE_HOME_ADDR2 = "家庭住址";//
    String TABLE_TITLE_DIAGNOSIS_AGENCY = "诊断机构";//
    String TABLE_TITLE_DIAGNOSIS_RESULT = "诊断结果";//
    String TABLE_TITLE_GUARDIAN_NAME = "监护人姓名";//
    String TABLE_TITLE_GUARDIAN_ID_CARD = "监护人身份证号";//
    String TABLE_TITLE_PRESENT_NAME = "家长姓名";//
    String TABLE_TITLE_RELATION_TO_CHILD = "与儿童关系";//
    String TABLE_TITLE_RELATION_TO_CHILD_F = "F与儿童关系";//亲人
    String TABLE_TITLE_RELATION_TO_CHILD_C = "C与儿童关系";//护工
    String TABLE_TITLE_CHILD_IQ = "儿童发育商";//
    String TABLE_TITLE_BRAIN_PALSY_STYLE = "脑瘫类型";//
    String TABLE_TITLE_WITH_OTHER_DISABILITY = "是否伴有其他残疾";//
    String TABLE_TITLE_FAMILY_EMONIC_STATUS = "家庭经济状况";//
    String TABLE_TITLE_POOR_FAMILY = "贫困家庭";//
    String TABLE_TITLE_MEDICALSAFE = "享受医疗保险情况";//
    String TABLE_TITLE_IS_POOR_FAMILY = "是否为县区扶贫办确定的“精准扶贫建档立卡户”";//
    String TABLE_TITLE_HEALTH_AGENCY = "申请的定点康复机构名称";//
    String TABLE_TITLE_GUAIDIAN_REQUEST = "监护人申请";//
    String TABLE_TITLE_HOUSE_PHONE = "住宅电话";//
    String TABLE_TITLE_MOBILE_NUM = "手机号码";//
    String TABLE_TITLE_CHECK_CODE = "验证码";//
    String TABLE_TITLE_COMMUNICATION_ADDR = "通讯地址";//
    String TABLE_TITLE_DISCOVER_DISABILITY_YEAR = "发现耳聋月龄：";//
    String TABLE_TITLE_FAMILY_HAS_DISABILITY = "是否有家族耳聋史：";//
    String TABLE_TITLE_HEARING_LOSE_RECOVERY = "听力损失及康复情况";//
    String TABLE_TITLE_CURRENT_RECOVERY = "目前康复状态";//
    String TABLE_TITLE_HAS_CARE_WORKER = "接受救助后家庭中有无专人陪伴康复";//护工
    String TABLE_TITLE_REQUEST_RECOVERY = "康复需求项目";//护工
    String TABLE_TITLE_SELECT_ASSIST_TOOL = "辅具选择";//护工
    String TABLE_TITLE_DELIVERY_METHOD = "配送方式";//护工
    String TABLE_TITLE_ASSIST_TOOL_AMOUNT = "器具数量";//护工
    String TABLE_TITLE_SPECIALTY = "特长";//
    String TABLE_TITLE_JOB_STATUS = "就业状况";//
    String TABLE_TITLE_TRAIN_TYPE = "培训种类";//
    String TABLE_TITLE_TRAIN_INTENT = "培训意向";//
    String TABLE_TITLE_REMARK = "备注";//
    String TABLE_TITLE_CONTENT = "详细内容";//
    String TABLE_TITLE_CONTENT_TYPE = "内容分布";//
    String TABLE_TITLE_HOPE_TRAIN_TYPE = "希望参加何种培训";//


    interface IBusinessView extends IView {

    }

    interface IBusinessPresent extends IPresenter<IBusinessView> {
        void businessList(String account, String token, String keyWord, int pageSize, int currentPage, String tag);

        void businessDataNeeded(int declareId, String tag);

        void creatBusiness(RequestBody requestBody, String tag);

        /**
         * 业务详情
         *
         * @param requestBody
         * @param tag
         */
        void businessDetail(RequestBody requestBody, String tag);

        void businessProgress(RequestBody requestBody, String tag);

        void deleteUserBusiness(List<Integer> ids, String tag);




        /*==============================================  描述信息  =============================================*/


        /**
         * 获取所有的业务
         *
         * @param tag
         */
        void getAllBusinesses(String tag);

        /**
         * @param tag
         */
        void getChildBusinesses(int matterId, String tag);

        /**
         * @param tag
         */
        void getDisabledCategory(String tag);

        /**
         * @param tag
         */
        void getDisabledLevel(String tag);

        /**
         * @param tag
         */
        void getDisabledEducation(String tag);

        /**
         * @param tag
         */
        void getDisabledNation(String tag);

        /**
         * @param tag
         */
        void getDisabledAIDS(int categoryId,String tag);

        /**
         * @param tag
         */
        void getDisabledBarrier(String tag);
        /**
         * 培训意向
         * @param tag
         */
        void getTrainingIntention(String tag);

        /**
         * @param tag
         */
        void addDisabilityCertificate(RequestBody requestBody, String tag);

        /**
         * @param tag
         */
        void addCertificatesExchange(RequestBody requestBody, String tag);

        /**
         * @param tag
         */
        void addCertificatesChange(RequestBody requestBody, String tag);

        /**
         * @param tag
         */
        void addCertificatesReissue(RequestBody requestBody, String tag);

        /**
         * @param tag
         */
        void addCertificatesMovein(RequestBody requestBody, String tag);

        /**
         * @param tag
         */
        void addCertificatesMoveout(RequestBody requestBody, String tag);

        /**
         * @param tag
         */
        void addCertificatesCancel(RequestBody requestBody, String tag);

        /**
         * @param tag
         */
        void addDisabledObtainEmployment(RequestBody requestBody, String tag);

        /**
         * @param tag
         */
        void addDisabledChildrenCerebralPalsy(RequestBody requestBody, String tag);

        /**
         * @param tag
         */
        void addDisabledChildrenDeaf(RequestBody requestBody, String tag);

        /**
         * @param tag
         */
        void addDisabledChildrenAutism(RequestBody requestBody, String tag);

        /**
         * @param tag
         */
        void addDisabledChildrenIntellectual(RequestBody requestBody, String tag);

        /**
         * @param tag
         */
        void addDisabledStudentGrant(RequestBody requestBody, String tag);

        /**
         * @param tag
         */
        void addDisabledStudentFamilyGrant(RequestBody requestBody, String tag);

        /**
         * @param tag
         */
        void addAIDS(RequestBody requestBody, String tag);
        /**
         * @param tag
         */
        void getDisabledAIDSInfo(int aidsId , String tag);

        /**
         * @param tag
         */
        void addTrain(RequestBody requestBody, String tag);

        /**
         * @param tag
         */
        void addHomCare(RequestBody requestBody, String tag);






        /*==============================================  详情接口  =============================================*/

        /**
         * @param businessId
         * @param tag
         */
        void getDisabilityCertificateInfo(int businessId, String tag);

        /**
         * @param businessId
         * @param tag
         */
        void getCertificatesExchangeInfo(int businessId, String tag);

        /**
         * @param businessId
         * @param tag
         */
        void getCertificatesChangeInfo(int businessId, String tag);

        /**
         * @param businessId
         * @param tag
         */
        void getCertificatesReissueInfo(int businessId, String tag);

        /**
         * @param businessId
         * @param tag
         */
        void getCertificatesMoveinInfo(int businessId, String tag);

        /**
         * @param businessId
         * @param tag
         */
        void getCertificatesMoveoutInfo(int businessId, String tag);
        /**
         * @param businessId
         * @param tag
         */
        void getCertificatesCancelInfo(int businessId, String tag);
        /**
         * @param businessId
         * @param tag
         */
        void getDisabledObtainEmploymentInfo(int businessId, String tag);
        /**
         * @param businessId
         * @param tag
         */
        void getDisabledChildrenCerebralPalsyInfo(int businessId, String tag);
        /**
         * @param businessId
         * @param tag
         */
        void getDisabledChildrenDeafInfo(int businessId, String tag);
        /**
         * @param businessId
         * @param tag
         */
        void getDisabledChildrenAutismInfo(int businessId, String tag);
        /**
         * @param businessId
         * @param tag
         */
        void getDisabledChildrenIntellectualInfo(int businessId, String tag);
        /**
         * @param businessId
         * @param tag
         */
        void getDisabledStudentFamilyGrantInfo(int businessId, String tag);
        /**
         * @param businessId
         * @param tag
         */
        void getDisabledStudentGrantInfo(int businessId, String tag);
        /**
         * @param businessId
         * @param tag
         */
        void getAIDSInfo(int businessId, String tag);
        /**
         * @param businessId
         * @param tag
         */
        void getTrainInfo(int businessId, String tag);
        /**
         * @param businessId
         * @param tag
         */
        void getHomCareInfo(int businessId, String tag);

    }
}
