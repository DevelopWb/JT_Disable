package com.juntai.tyb.bean.healthOrg;


import com.juntai.disabled.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/7/22 17:43
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/22 17:43
 */
public class HealthOrgPositionBean extends BaseResult {
    /**
     * error : null
     * returnValue : null
     * list : null
     * type : null
     * data : [{"id":1,"name":"天使国际特教学校","img":"/case_image/2cace878efa64bddb4002e9f7e662cc6.jpeg","longitude":118
     * .415557,"latitude":35.089238},{"id":2,"name":"尚善残疾人托养院","img":"/case_image/94825862b5934d27bb5bdfe54a0e300b
     * .jpeg","longitude":118.49503,"latitude":35.269302},{"id":3,"name":"临沂河东康复医院",
     * "img":"/case_image/dbbee2616cf74143b2f285cf7e9c14f7.jpeg","longitude":118.401166,"latitude":35.087481}]
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
         * id : 1
         * name : 天使国际特教学校
         * img : /case_image/2cace878efa64bddb4002e9f7e662cc6.jpeg
         * longitude : 118.415557
         * latitude : 35.089238
         */

        private int id;
        private String name;
        private String img;
        private String phone;//联系电话
        private String address;//地址
        private double longitude;
        private double latitude;

        public String getPhone() {
            return phone == null ? "" : phone;
        }

        public void setPhone(String phone) {
            this.phone = phone == null ? "" : phone;
        }

        public String getAddress() {
            return address == null ? "" : address;
        }

        public void setAddress(String address) {
            this.address = address == null ? "" : address;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }
    }
}
