package com.juntai.tyb.bean;


import android.os.Parcel;
import android.os.Parcelable;

import com.juntai.disabled.basecomponent.base.BaseResult;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/7/25 17:16
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/25 17:16
 */
public class UserBaseMsgBean extends BaseResult {

    /**
     * error : null
     * returnValue : null
     * list : null
     * type : null
     * data : {"id":1,"account":"admin","nickname":"管理员","sex":"男","lastLoginTime":"2020-07-15 11:04:32",
     * "address":"临沂市","head":null,"category":4}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * id : 1
         * account : admin
         * nickname : 管理员
         * sex : 男
         * lastLoginTime : 2020-07-15 11:04:32
         * address : 临沂市
         * head : null
         * category : 4
         */

        private int id;
        private String account;
        private String nickname;
        private String sex;
        private String lastLoginTime;
        private String address;
        private String head;
        private int category;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getHead() {
            return head == null ? "" : head;
        }

        public void setHead(String head) {
            this.head = head == null ? "" : head;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.account);
            dest.writeString(this.nickname);
            dest.writeString(this.sex);
            dest.writeString(this.lastLoginTime);
            dest.writeString(this.address);
            dest.writeString(this.head);
            dest.writeInt(this.category);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.account = in.readString();
            this.nickname = in.readString();
            this.sex = in.readString();
            this.lastLoginTime = in.readString();
            this.address = in.readString();
            this.head = in.readString();
            this.category = in.readInt();
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }
}
