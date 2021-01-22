package com.juntai.disabled.federation.home_page.business;

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
    String TABLE_TITLE_PIC = "照片";//表单的标题
    String TABLE_TITLE_SEX = "性别";//表单的标题
    String TABLE_TITLE_DISABILITY_HEAR= "听力残疾";//表单的标题
    String TABLE_TITLE_DISABILITY_LIMB= "肢体残疾";//表单的标题
    String TABLE_TITLE_BIRTH = "出生年月";//表单的标题
    String TABLE_TITLE_RESUME = "本人简历";//表单的标题
    String TABLE_TITLE_DISABLE_CARD_ID = "残疾证号";//表单的标题
    String TABLE_TITLE_NATION = "民族";//民族
    String TABLE_TITLE_MARRIAGE = "婚姻状况";//婚姻
    String TABLE_TITLE_EDUCATION = "文化程度";//
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
    String TABLE_TITLE_DISABLE_PIC = "1.申请人残疾证照片";//
    String TABLE_TITLE_DISABLE_PIC2 = "申请人残疾证照片";//
    String TABLE_TITLE_MATERIAL_PIC = "2.病例材料照片";//
    String TABLE_TITLE_LIFE_PIC = "3.申请人生活照片";//
    String TABLE_TITLE_SPECIAL = "特长";//
    String TABLE_TITLE_REG_MODE = "登记方式";//
    String TABLE_TITLE_DISABILITY_KINDS = "残疾类别";//
    String TABLE_TITLE_DISABILITY_LEVEL = "残疾等级";//


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
