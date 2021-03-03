package com.juntai.disabled.federation.bean;

import com.juntai.disabled.basecomponent.base.BaseResult;

import java.util.List;

/**
 * Describe: 地图点重点人员
 * Create by zhangzhenlong
 * 2020-7-3
 * email:954101549@qq.com
 */
public class ServerPeopleBean extends BaseResult {

    /**
     * error : null
     * data : [{"id":87,"nickname":"苑新生","latitude":35.090634,"longitude":118.402076,"headPortrait":"https://www
     * .juntaikeji.com:17002/head_img/4acbbf83f3934de486fc0f179eaeeb3b.jpeg","postName":"单位领导",
     * "departmentName":"东关街派出所"},{"id":100,"nickname":"韩国杰","latitude":35.090634,"longitude":118.402076,
     * "headPortrait":"https://www.juntaikeji.com:17002/head_img/79abaa9f62c944618c2774e3876e63f3.jpeg",
     * "postName":"单位领导","departmentName":"东关街派出所"},{"id":101,"nickname":"铁人王进喜","latitude":35.090634,"longitude":118
     * .402076,"headPortrait":"https://www.juntaikeji.com:17002/head_img/ce7066cfd91b47128d20181fd151d97d.jpeg",
     * "postName":"单位领导","departmentName":"东关街派出所"}]
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 87
         * nickname : 苑新生
         * latitude : 35.090634
         * longitude : 118.402076
         * headPortrait : https://www.juntaikeji.com:17002/head_img/4acbbf83f3934de486fc0f179eaeeb3b.jpeg
         * postName : 单位领导
         * departmentName : 东关街派出所
         */

        private int id;
        private String nickname;
        private double latitude;
        private double longitude;
        private String headPortrait;
        private String postName;
        private String departmentName;
        private String account;
        private int state;

        public String getAccount() {
            return account == null ? "" : account;
        }

        public void setAccount(String account) {
            this.account = account == null ? "" : account;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getHeadPortrait() {
            return headPortrait;
        }

        public void setHeadPortrait(String headPortrait) {
            this.headPortrait = headPortrait;
        }

        public String getPostName() {
            return postName;
        }

        public void setPostName(String postName) {
            this.postName = postName;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }
    }
}
