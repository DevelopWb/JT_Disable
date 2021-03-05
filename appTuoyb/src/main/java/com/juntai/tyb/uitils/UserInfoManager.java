package com.juntai.tyb.uitils;

import com.juntai.tyb.bean.LoginBean;
import com.orhanobut.hawk.Hawk;

/**
 * @Author: tobato
 * @Description: 作用描述   用户信息管理类
 * @CreateDate: 2020/7/9 10:05
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/9 10:05
 */
public class UserInfoManager {


    /**
     * 获取用户信息
     * @return
     */
    public static LoginBean.UserBean  getUserBean(){
        return Hawk.get(HawkProperty.USER_INFO);
    }
    /**
     * 获取用户信息
     * @return
     */
    public static int  getUserId(){
        return getUserBean().getUserId();
    }
    /**
     * 获取账号的性质 是否是测试号
     * @return
     */
    public static boolean  isTest(){
        return getUserBean().isTest();
    }


    /**
     * 获取用户信息 token
     * @return
     */
    public static String  getUserToken(){
        return getUserBean().getToken();
    }
    /**
     * 获取用户信息 pwd
     * @return
     */
    public static String  getUserPwd(){
        return getUserBean().getPassword();
    }
    /**
     * 获取用户信息 Account
     * @return
     */
    public static String  getUserAccount(){
        return getUserBean().getAccount();
    }
    /**
     * 获取用户信息 Phone
     * @return
     */
    public static String  getUserPhone(){
        return getUserBean()==null?"":getUserBean().getPhoneNumber();
    }


}
