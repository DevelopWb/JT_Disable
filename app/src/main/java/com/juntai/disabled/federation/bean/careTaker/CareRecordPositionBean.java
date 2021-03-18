package com.juntai.disabled.federation.bean.careTaker;

import android.os.Parcel;
import android.os.Parcelable;


import com.juntai.disabled.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  托养分布
 * @CreateDate: 2020/7/19 16:18
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/19 16:18
 */
public class CareRecordPositionBean extends BaseResult {


    /**
     * error : null
     * returnValue : null
     * list : null
     * type : null
     * data : {"datas":[{"id":1,"name":"孟祥臣","idNo":"372801197301174854","streetAddress":"九曲街道办事处",
     * "communityAddress":"孟于埠社区居委会","personImg":"/case_image/d6f765cf21c94b75bfc5077e3be915d9.jpeg","year":"2020",
     * "longitude":118.4019901,"latitude":35.0905211},{"id":3,"name":"孟祥臣","idNo":"372801197301174854",
     * "streetAddress":"九曲街道办事处","communityAddress":"孟于埠社区居委会",
     * "personImg":"/case_image/d6f765cf21c94b75bfc5077e3be915d9.jpeg","year":"2019","longitude":118.4019901,
     * "latitude":35.0905211}],"total":0,"listSize":2,"pageCount":0}
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
         * datas : [{"id":1,"name":"孟祥臣","idNo":"372801197301174854","streetAddress":"九曲街道办事处",
         * "communityAddress":"孟于埠社区居委会","personImg":"/case_image/d6f765cf21c94b75bfc5077e3be915d9.jpeg",
         * "year":"2020","longitude":118.4019901,"latitude":35.0905211},{"id":3,"name":"孟祥臣",
         * "idNo":"372801197301174854","streetAddress":"九曲街道办事处","communityAddress":"孟于埠社区居委会",
         * "personImg":"/case_image/d6f765cf21c94b75bfc5077e3be915d9.jpeg","year":"2019","longitude":118.4019901,
         * "latitude":35.0905211}]
         * total : 0
         * listSize : 2
         * pageCount : 0
         */

        private int total;
        private int listSize;
        private int pageCount;
        private List<DatasBean> datas;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getListSize() {
            return listSize;
        }

        public void setListSize(int listSize) {
            this.listSize = listSize;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean implements Parcelable {
            /**
             * id : 1
             * name : 孟祥臣
             * idNo : 372801197301174854
             * streetAddress : 九曲街道办事处
             * communityAddress : 孟于埠社区居委会
             * personImg : /case_image/d6f765cf21c94b75bfc5077e3be915d9.jpeg
             * year : 2020
             * longitude : 118.4019901
             * latitude : 35.0905211
             */

            private int id;
            private String name;
            private String idNo;
            private String streetAddress;
            private String communityAddress;
            private String personImg;
            private String year;
            private String place;
            private String caseDate;
            private double longitude;
            private double latitude;

            public String getCaseDate() {
                return caseDate == null ? "" : caseDate;
            }

            public void setCaseDate(String caseDate) {
                this.caseDate = caseDate == null ? "" : caseDate;
            }

            public String getPlace() {
                return place == null ? "" : place;
            }

            public void setPlace(String place) {
                this.place = place == null ? "" : place;
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

            public String getIdNo() {
                return idNo;
            }

            public void setIdNo(String idNo) {
                this.idNo = idNo;
            }

            public String getStreetAddress() {
                return streetAddress;
            }

            public void setStreetAddress(String streetAddress) {
                this.streetAddress = streetAddress;
            }

            public String getCommunityAddress() {
                return communityAddress;
            }

            public void setCommunityAddress(String communityAddress) {
                this.communityAddress = communityAddress;
            }

            public String getPersonImg() {
                return personImg;
            }

            public void setPersonImg(String personImg) {
                this.personImg = personImg;
            }

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
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


            public DatasBean() {
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeString(this.name);
                dest.writeString(this.idNo);
                dest.writeString(this.streetAddress);
                dest.writeString(this.communityAddress);
                dest.writeString(this.personImg);
                dest.writeString(this.year);
                dest.writeString(this.place);
                dest.writeString(this.caseDate);
                dest.writeDouble(this.longitude);
                dest.writeDouble(this.latitude);
            }

            protected DatasBean(Parcel in) {
                this.id = in.readInt();
                this.name = in.readString();
                this.idNo = in.readString();
                this.streetAddress = in.readString();
                this.communityAddress = in.readString();
                this.personImg = in.readString();
                this.year = in.readString();
                this.place = in.readString();
                this.caseDate = in.readString();
                this.longitude = in.readDouble();
                this.latitude = in.readDouble();
            }

            public static final Creator<DatasBean> CREATOR = new Creator<DatasBean>() {
                @Override
                public DatasBean createFromParcel(Parcel source) {
                    return new DatasBean(source);
                }

                @Override
                public DatasBean[] newArray(int size) {
                    return new DatasBean[size];
                }
            };
        }
    }
}
