package com.juntai.disabled.federation.entrance.regist;


import com.juntai.disabled.basecomponent.mvp.IView;

import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述  APP入口
 * @CreateDate: 2020/3/5 15:53
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/3/5 15:53
 */
public interface RegistContract {
    String GET_POLICE_POSITION = "get_position";//职务
    String GET_POLICE_BRANCH = "get_branch";//部门
    String GET_POLICE_AREA = "get_area";//辖区
    String GET_POLICE_GRIDDING = "get_gridding";//辖区
    String SET_PWD = "set_pwd";//设置密码
    String MODIFY_PWD = "modify_pwd";//修改密码
    String SET_PHONE = "set_phone";//设置手机号
    String REGIST = "regist";//注册
    String ADD_USER_INFO = "add_user_info";//补充用户信息

    interface IRegistView extends IView {
        /**
         * 接收到验证码后更改view得状态
         *
         * @param second
         */
        void updateSendCheckCodeViewStatus(long second);

        /**
         * 校验手机号错误
         *
         * @param error
         */
        void checkFormatError(String error);


    }

    interface IRegistPresent {

        /**
         * 发送验证码
         *
         * @param mobile
         * @param tempCode 短信模板
         */
        void sendCheckCode(String mobile,String tempCode);

        /**
         * 接收到验证码了 将observerble dispose
         */
        void receivedCheckCodeAndDispose();

        /**
         * 获取职务列表
         */
        void getPolicePosition(String tag);

        /**
         * 获取部门列表
         */
        void getPoliceBranch( String tag);

        /**
         * 获取网格列表
         */
        void getPoliceGridding(int departmentId, String tag);

        /**
         * 注册
         */
        void regist(String tag, RequestBody body);

        /**
         * 找回密码
         */
        void retrievePwd(String tag, String account, String password);
        /**
         * 修改密码
         */
        void modifyPwd(RequestBody requestBody,String tag);

        /**
         * 修改账号（手机号）
         * @param tag
         * @param newAccount
         * @param password
         */
        void updateAccount(String tag, String phoneNumber,String newAccount, String password, String oldPassword);

        /**
         * 补充用户信息
         * @param tag
         * @param body
         */
        void addUserInfo(String tag, RequestBody body);


        /**
         * @param tag
         */
        void addOpinionsAndSuggestions(RequestBody requestBody, String tag);

    }
}
