package com.juntai.tyb.bean;

import com.google.gson.annotations.SerializedName;
import com.juntai.disabled.basecomponent.base.BaseResult;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/7/9 10:06
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/9 10:06
 */
public class LoginBean extends BaseResult {


    /**
     * error : null
     * returnValue : null
     * msg : null
     * list : null
     * type : null
     * data : {"id":1,"account":"admin","nickname":"管理员","password":"57eb72e6b78a87a12d46a7f5e9315138","sex":"男",
     * "status":1,"address":"临沂市","street":1,"streetName":"河东区","createTime":"2020-04-18 18:06:05",
     * "lastLoginTime":"2020-07-07 16:52:21","token":"B2AGYCW992-JEDHP8FB3821U7N57O7Q3-VUGDPCCK-0"}
     * success : true
     */

    private UserBean data;

    public UserBean getData() {
        return data;
    }

    public void setData(UserBean data) {
        this.data = data;
    }

    public static class UserBean {
        /**
         * id : 1
         * account : admin
         * nickname : 管理员
         * password : 57eb72e6b78a87a12d46a7f5e9315138
         * sex : 男
         * status : 1
         * address : 临沂市
         * street : 1
         * streetName : 河东区
         * createTime : 2020-04-18 18:06:05
         * lastLoginTime : 2020-07-07 16:52:21
         * token : B2AGYCW992-JEDHP8FB3821U7N57O7Q3-VUGDPCCK-0
         */

        private Integer userId;//用户id
        private String account;//账号
        private String password;//密码
        private String nickname;//昵称
        private String headPortrait;//头像
        private String phoneNumber;//手机号
        private Integer realNameStatus;//实名认证状态（0未提交；1提交审核中；2审核通过；3审核失败）
        private Integer settleStatus;//申请入驻状态（0未提交；1提交审核中；2审核通过；3审核失败）
        private String rOngYunToken;//融云token
        private Integer blacklist;//黑名单状态
        private Integer frozenStatus;//冻结状态
        private String gmtCreate;//注册时间
        private String token;//验证token
        private boolean test;//测试权限（false：关闭；true：开通）

        public boolean isTest() {
            return test;
        }

        public void setTest(boolean test) {
            this.test = test;
        }


        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getAccount() {
            return account == null ? "" : account;
        }

        public void setAccount(String account) {
            this.account = account == null ? "" : account;
        }

        public String getPassword() {
            return password == null ? "" : password;
        }

        public void setPassword(String password) {
            this.password = password == null ? "" : password;
        }

        public String getNickname() {
            return nickname == null ? "" : nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname == null ? "" : nickname;
        }

        public String getHeadPortrait() {
            return headPortrait == null ? "" : headPortrait;
        }

        public void setHeadPortrait(String headPortrait) {
            this.headPortrait = headPortrait == null ? "" : headPortrait;
        }

        public String getPhoneNumber() {
            return phoneNumber == null ? "" : phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber == null ? "" : phoneNumber;
        }

        public Integer getRealNameStatus() {
            return realNameStatus;
        }

        public void setRealNameStatus(Integer realNameStatus) {
            this.realNameStatus = realNameStatus;
        }

        public Integer getSettleStatus() {
            return settleStatus;
        }

        public void setSettleStatus(Integer settleStatus) {
            this.settleStatus = settleStatus;
        }

        public String getrOngYunToken() {
            return rOngYunToken == null ? "" : rOngYunToken;
        }

        public void setrOngYunToken(String rOngYunToken) {
            this.rOngYunToken = rOngYunToken == null ? "" : rOngYunToken;
        }

        public Integer getBlacklist() {
            return blacklist;
        }

        public void setBlacklist(Integer blacklist) {
            this.blacklist = blacklist;
        }

        public Integer getFrozenStatus() {
            return frozenStatus;
        }

        public void setFrozenStatus(Integer frozenStatus) {
            this.frozenStatus = frozenStatus;
        }

        public String getGmtCreate() {
            return gmtCreate == null ? "" : gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate == null ? "" : gmtCreate;
        }

        public String getToken() {
            return token == null ? "" : token;
        }

        public void setToken(String token) {
            this.token = token == null ? "" : token;
        }
    }
}
