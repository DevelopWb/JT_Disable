package com.juntai.disabled.federation.home_page.business.handlerBusiness.baseBusiness;

import com.juntai.disabled.basecomponent.mvp.IPresenter;
import com.juntai.disabled.basecomponent.mvp.IView;

import okhttp3.RequestBody;

/**
 * @aouther tobato
 * @description 描述
 * @date 2020/3/12 16:00
 */
public interface BusinessContract {
    String CREAT_BUSINESS = "creat_business";//创建业务
    String TABLE_TITLE_NAME = "姓名";//表单的标题
    String TABLE_TITLE_CHILD_NAME = "儿童姓名";//表单的标题
    String TABLE_TITLE_PIC = "照片";//表单的标题
    String TABLE_TITLE_SEX = "性别";//表单的标题
    String TABLE_TITLE_DISABILITY_HEAR= "听力残疾";//表单的标题
    String TABLE_TITLE_DISABILITY_LIMB= "肢体残疾";//表单的标题
    String TABLE_TITLE_BIRTH = "出生年月";//表单的标题
    String TABLE_TITLE_RESUME = "本人简历";//表单的标题
    String TABLE_TITLE_DISABLE_CARD_ID = "残疾证号";//表单的标题
    String TABLE_TITLE_NATION = "民族";//民族
    String TABLE_TITLE_MARRIAGE = "婚姻状况";//婚姻
    String TABLE_TITLE_EDUCATION_LEVEL = "文化程度";//
    String TABLE_TITLE_HOMETOWN = "籍贯";//
    String TABLE_TITLE_HOME_ADDR= "户籍所在地";//
    String TABLE_TITLE_WANTED_POST= "意向岗位";//
    String TABLE_TITLE_WORK_AREA= "择业地区";//
    String TABLE_TITLE_SALARY= "月薪要求";//薪资
    String TABLE_TITLE_IDCARD = "身份证号";//
    String TABLE_TITLE_ADDR = "现住址";//
    String TABLE_TITLE_ADDR_LIVE_NOW = "现居住地";//
    String TABLE_TITLE_ZIP_CODE = "邮政编码";//
    String TABLE_TITLE_HUKOU = "户口类别";//
    String TABLE_TITLE_GUARDIAN = "监护人";//
    String TABLE_TITLE_GUARDIAN_RELATION = "与监护人关系";//
    String TABLE_TITLE_PHONE = "联系电话";//
    String TABLE_TITLE_CONTACT_MODE = "联系方式";//
    String TABLE_TITLE_WORKER = "工作单位";//
    String TABLE_TITLE_WORKER_TYPE = "职业工种";//
    String TABLE_TITLE_UNIT_NATURE = "单位性质";//
    String TABLE_TITLE_IS_WEEL_COMPANY = "是否福利企业";//
    String TABLE_TITLE_CARD_TYPE = "证件申请类型";//
    String TABLE_TITLE_DISABLE_PIC = "申请人残疾证照片";//
    String TABLE_TITLE_STUDENT_CARD_PIC = "学生证照片";//
    String TABLE_TITLE_ADMISSION_NOTICE_PIC = "入学通知书照片";//
    String TABLE_TITLE_TUITION_PIC = "缴费凭证或缴费凭证照片";//学费
    String TABLE_TITLE_MATERIAL_PIC = "病例材料照片";//
    String TABLE_TITLE_LIFE_PIC = "申请人生活照片";//
    String TABLE_TITLE_SPECIAL = "特长";//
    String TABLE_TITLE_REG_MODE = "登记方式";//
    String TABLE_TITLE_DISABILITY_KINDS = "残疾类别";//
    String TABLE_TITLE_DISABILITY_LEVEL = "残疾等级";//
    String TABLE_TITLE_ADMISSION_COLLEGE = "录取院校";//
    String TABLE_TITLE_ADMISSION_PERSIONAL = "录取专业";//
    String TABLE_TITLE_EDUCATION = "学历";//
    String TABLE_TITLE_SCHOOL_SYSTEM = "学制";//
    String TABLE_TITLE_EMAIL= "E-mail";//
    String TABLE_TITLE_FATHER_NAME= "父亲姓名";//
    String TABLE_TITLE_MATHER_NAME= "母亲姓名";//
    String TABLE_TITLE_HOME_ADDRESS= "家庭通讯地址";//
    String TABLE_TITLE_ACCOUNT_NAME= "户名";//
    String TABLE_TITLE_ACCOUNT_BANK= "开户行";//
    String TABLE_TITLE_CARD_NUM= "卡号";//
    String TABLE_TITLE_DISABILITY_PEOPLE_NAME= "重残人员姓名";//
    String TABLE_TITLE_DISABILITY_PEOPLE_RELATION= "与该生关系";//
    String TABLE_TITLE_STUDENT_IDCARD= "学生身份证照片";//
    String TABLE_TITLE_PRESENT_DISBILITY_IDCARD= "家长重度残疾证照片";//
    String TABLE_TITLE_GROUP_PHOTO= "学生与家长生活合影照片";//
    String TABLE_TITLE_ACCOUNT_BOOK= "户口本拍照能说明家庭关系的照片";//
    String TABLE_TITLE_YEAR= "年度";//
    String TABLE_TITLE_PROJECT_LEVEL= "项目级别";//
    String TABLE_TITLE_HOME_ADDR2= "家庭住址";//
    String TABLE_TITLE_DIAGNOSIS_AGENCY= "诊断机构";//
    String TABLE_TITLE_DIAGNOSIS_RESULT= "诊断结果";//
    String TABLE_TITLE_GUARDIAN_NAME= "监护人姓名";//
    String TABLE_TITLE_RELATION_TO_CHILD= "与儿童关系";//
    String TABLE_TITLE_CHILD_IQ= "儿童发育商";//
    String TABLE_TITLE_WITH_OTHER_DISABILITY= "是否伴有其他残疾";//
    String TABLE_TITLE_FAMILY_EMONIC_STATUS= "家庭经济状况";//
    String TABLE_TITLE_POOR_FAMILY= "贫困家庭";//
    String TABLE_TITLE_IS_POOR_FAMILY= "是否为县区扶贫办确定的“精准扶贫建档立卡户”";//
    String TABLE_TITLE_HEALTH_AGENCY= "申请的定点康复机构名称";//
    String TABLE_TITLE_GUAIDIAN_REQUEST= "监护人申请";//


    interface IBusinessView extends IView {

    }

    interface IBusinessPresent extends IPresenter<IBusinessView> {
        void businessList(String account,String token, String keyWord, int pageSize, int currentPage, String tag);

        void businessDataNeeded(int declareId, String tag);

        void creatBusiness(RequestBody requestBody, String tag);

        /**
         * 业务详情
         * @param requestBody
         * @param tag
         */
        void businessDetail(RequestBody requestBody, String tag);

        void businessProgress(RequestBody requestBody, String tag);
    }

}
