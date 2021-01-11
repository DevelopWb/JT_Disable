package com.juntai.tyb.bean.careTaker;

import android.os.Parcel;
import android.os.Parcelable;


import com.juntai.disabled.basecomponent.base.BaseResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  托养信息实体类
 * @CreateDate: 2020/7/12 9:59
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/12 9:59
 */
public class CareTakerInfoBean extends BaseResult {

    /**
     * error : null
     * returnValue : null
     * list : null
     * type : null
     * data : {"id":2,"name":"彭善想","sex":"男","details":"智力三级;","certificateNo":"37280119720512483053",
     * "idNo":"372801197205124830","year":"2020","place":"山东省河东区工业园王家于埠村184号","longitude":118.4019901,"latitude":35
     * .0905211,"houseImg":"/case_image/753e7f82d08c4d30ab8f8c18f9d78ba5.jpeg",
     * "indoorImg":"/case_image/dd3479c5341e45a99f3388df217f8147.jpeg",
     * "personImg":"/case_image/cef7ea86b339419caabb92c5a3be3e33.jpeg",
     * "gateImg":"/case_image/fc62dd3d649449eb82cad792193d8cc4.jpeg",
     * "streetImg":"/case_image/5d15a754fb0848dd90ea5ee02330deb0.jpeg",
     * "otherImg":"/case_image/e0869ee8df9843a0b376c0ab711b1a95.jpeg","serviceListVos":[{"id":26,"servicer":"彭自营 陈庆香
     * 尤欣欣","startTime":"2020-04-21 10:03:48","endTime":"2020-04-21 12:12:36","serviceLength":"2小时",
     * "serviceFileVos":[{"id":88,"path":"/case_image/cef7ea86b339419caabb92c5a3be3e33.jpeg","flag":0},{"id":57,
     * "path":"/case_image/dd3479c5341e45a99f3388df217f8147.jpeg","flag":0},{"id":26,"path":"/case_image
     * /753e7f82d08c4d30ab8f8c18f9d78ba5.jpeg","flag":0},{"id":119,"path":"/case_video
     * /a1f08db936b24464a872c5426f121e43.mp4","flag":1}]},{"id":25,"servicer":"尤欣欣 彭自营","startTime":"2020-04-21
     * 09:21:37","endTime":"2020-04-21 11:30:37","serviceLength":"2小时","serviceFileVos":[{"id":87,"path":"/case_image
     * /41684338fce248e8a6d9de6f2bac42b8.jpeg","flag":0},{"id":56,"path":"/case_image
     * /5792f5b93472449f89a4628259a0ee68.jpeg","flag":0},{"id":25,"path":"/case_image
     * /94a286ac829f4f9fb7f8ee554c848fb8.jpeg","flag":0},{"id":118,"path":"/case_video
     * /a6728e7391994ceb88dc0da0e13590cb.mp4","flag":1}]}]}
     * success : true
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
         * id : 2
         * name : 彭善想
         * sex : 男
         * details : 智力三级;
         * certificateNo : 37280119720512483053
         * idNo : 372801197205124830
         * year : 2020
         * place : 山东省河东区工业园王家于埠村184号
         * longitude : 118.4019901
         * latitude : 35.0905211
         * houseImg : /case_image/753e7f82d08c4d30ab8f8c18f9d78ba5.jpeg
         * indoorImg : /case_image/dd3479c5341e45a99f3388df217f8147.jpeg
         * personImg : /case_image/cef7ea86b339419caabb92c5a3be3e33.jpeg
         * gateImg : /case_image/fc62dd3d649449eb82cad792193d8cc4.jpeg
         * streetImg : /case_image/5d15a754fb0848dd90ea5ee02330deb0.jpeg
         * otherImg : /case_image/e0869ee8df9843a0b376c0ab711b1a95.jpeg
         * serviceListVos : [{"id":26,"servicer":"彭自营 陈庆香 尤欣欣","startTime":"2020-04-21 10:03:48",
         * "endTime":"2020-04-21 12:12:36","serviceLength":"2小时","serviceFileVos":[{"id":88,"path":"/case_image
         * /cef7ea86b339419caabb92c5a3be3e33.jpeg","flag":0},{"id":57,"path":"/case_image
         * /dd3479c5341e45a99f3388df217f8147.jpeg","flag":0},{"id":26,"path":"/case_image
         * /753e7f82d08c4d30ab8f8c18f9d78ba5.jpeg","flag":0},{"id":119,"path":"/case_video
         * /a1f08db936b24464a872c5426f121e43.mp4","flag":1}]},{"id":25,"servicer":"尤欣欣 彭自营","startTime":"2020-04-21
         * 09:21:37","endTime":"2020-04-21 11:30:37","serviceLength":"2小时","serviceFileVos":[{"id":87,
         * "path":"/case_image/41684338fce248e8a6d9de6f2bac42b8.jpeg","flag":0},{"id":56,"path":"/case_image
         * /5792f5b93472449f89a4628259a0ee68.jpeg","flag":0},{"id":25,"path":"/case_image
         * /94a286ac829f4f9fb7f8ee554c848fb8.jpeg","flag":0},{"id":118,"path":"/case_video
         * /a6728e7391994ceb88dc0da0e13590cb.mp4","flag":1}]}]
         */

        private int id;
        private String name;
        private String sex;
        private String details;
        private String certificateNo;
        private String idNo;
        private String year;
        private String place;
        private double longitude;
        private double latitude;
        private String houseImg;
        private String indoorImg;
        private String personImg;
        private String gateImg;
        private String streetAddress;
        private String communityAddress;
        private String streetImg;
        private String maxEnd;//最大结束时间  开始时间不能比这个小
        private String otherImg;
        private List<ServiceListVosBean> serviceListVos;

        public String getMaxEnd() {
            return maxEnd == null ? "" : maxEnd;
        }

        public void setMaxEnd(String maxEnd) {
            this.maxEnd = maxEnd == null ? "" : maxEnd;
        }

        public String getStreetAddress() {
            return streetAddress == null ? "" : streetAddress;
        }

        public void setStreetAddress(String streetAddress) {
            this.streetAddress = streetAddress == null ? "" : streetAddress;
        }

        public String getCommunityAddress() {
            return communityAddress == null ? "" : communityAddress;
        }

        public void setCommunityAddress(String communityAddress) {
            this.communityAddress = communityAddress == null ? "" : communityAddress;
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

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getCertificateNo() {
            return certificateNo;
        }

        public void setCertificateNo(String certificateNo) {
            this.certificateNo = certificateNo;
        }

        public String getIdNo() {
            return idNo;
        }

        public void setIdNo(String idNo) {
            this.idNo = idNo;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
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

        public String getHouseImg() {
            return houseImg;
        }

        public void setHouseImg(String houseImg) {
            this.houseImg = houseImg;
        }

        public String getIndoorImg() {
            return indoorImg;
        }

        public void setIndoorImg(String indoorImg) {
            this.indoorImg = indoorImg;
        }

        public String getPersonImg() {
            return personImg;
        }

        public void setPersonImg(String personImg) {
            this.personImg = personImg;
        }

        public String getGateImg() {
            return gateImg;
        }

        public void setGateImg(String gateImg) {
            this.gateImg = gateImg;
        }

        public String getStreetImg() {
            return streetImg;
        }

        public void setStreetImg(String streetImg) {
            this.streetImg = streetImg;
        }

        public String getOtherImg() {
            return otherImg;
        }

        public void setOtherImg(String otherImg) {
            this.otherImg = otherImg;
        }

        public List<ServiceListVosBean> getServiceListVos() {
            return serviceListVos;
        }

        public void setServiceListVos(List<ServiceListVosBean> serviceListVos) {
            this.serviceListVos = serviceListVos;
        }

        public static class ServiceListVosBean implements Parcelable {
            /**
             * id : 26
             * servicer : 彭自营 陈庆香 尤欣欣
             * startTime : 2020-04-21 10:03:48
             * endTime : 2020-04-21 12:12:36
             * serviceLength : 2小时
             * serviceFileVos : [{"id":88,"path":"/case_image/cef7ea86b339419caabb92c5a3be3e33.jpeg","flag":0},{"id
             * ":57,"path":"/case_image/dd3479c5341e45a99f3388df217f8147.jpeg","flag":0},{"id":26,"path":"/case_image
             * /753e7f82d08c4d30ab8f8c18f9d78ba5.jpeg","flag":0},{"id":119,"path":"/case_video/a1f08db936b24464a872c5426f121e43.mp4","flag":1}]
             */

            private int serviceId;
            private String servicer;
            private String startTime;
            private String endTime;
            private String serviceLength;
            private List<ServiceFileVosBean> serviceFileVos;

            public int getServiceId() {
                return serviceId;
            }

            public void setServiceId(int serviceId) {
                this.serviceId = serviceId;
            }

            public String getServicer() {
                return servicer;
            }

            public void setServicer(String servicer) {
                this.servicer = servicer;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getServiceLength() {
                return serviceLength;
            }

            public void setServiceLength(String serviceLength) {
                this.serviceLength = serviceLength;
            }

            public List<ServiceFileVosBean> getServiceFileVos() {
                return serviceFileVos;
            }

            public void setServiceFileVos(List<ServiceFileVosBean> serviceFileVos) {
                this.serviceFileVos = serviceFileVos;
            }

            public static class ServiceFileVosBean implements Parcelable {
                /**
                 * id : 88
                 * path : /case_image/cef7ea86b339419caabb92c5a3be3e33.jpeg
                 * flag : 0
                 */

                private int fileId;
                private String path;
                private int flag;

                public int getFileId() {
                    return fileId;
                }

                public void setFileId(int fileId) {
                    this.fileId = fileId;
                }

                public String getPath() {
                    return path;
                }

                public void setPath(String path) {
                    this.path = path;
                }

                public int getFlag() {
                    return flag;
                }

                public void setFlag(int flag) {
                    this.flag = flag;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(this.fileId);
                    dest.writeString(this.path);
                    dest.writeInt(this.flag);
                }

                public ServiceFileVosBean() {
                }

                protected ServiceFileVosBean(Parcel in) {
                    this.fileId = in.readInt();
                    this.path = in.readString();
                    this.flag = in.readInt();
                }

                public static final Creator<ServiceFileVosBean> CREATOR = new Creator<ServiceFileVosBean>() {
                    @Override
                    public ServiceFileVosBean createFromParcel(Parcel source) {
                        return new ServiceFileVosBean(source);
                    }

                    @Override
                    public ServiceFileVosBean[] newArray(int size) {
                        return new ServiceFileVosBean[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.serviceId);
                dest.writeString(this.servicer);
                dest.writeString(this.startTime);
                dest.writeString(this.endTime);
                dest.writeString(this.serviceLength);
                dest.writeList(this.serviceFileVos);
            }

            public ServiceListVosBean() {
            }

            protected ServiceListVosBean(Parcel in) {
                this.serviceId = in.readInt();
                this.servicer = in.readString();
                this.startTime = in.readString();
                this.endTime = in.readString();
                this.serviceLength = in.readString();
                this.serviceFileVos = new ArrayList<ServiceFileVosBean>();
                in.readList(this.serviceFileVos, ServiceFileVosBean.class.getClassLoader());
            }

            public static final Creator<ServiceListVosBean> CREATOR = new Creator<ServiceListVosBean>() {
                @Override
                public ServiceListVosBean createFromParcel(Parcel source) {
                    return new ServiceListVosBean(source);
                }

                @Override
                public ServiceListVosBean[] newArray(int size) {
                    return new ServiceListVosBean[size];
                }
            };
        }

        public DataBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeString(this.sex);
            dest.writeString(this.details);
            dest.writeString(this.certificateNo);
            dest.writeString(this.idNo);
            dest.writeString(this.year);
            dest.writeString(this.place);
            dest.writeDouble(this.longitude);
            dest.writeDouble(this.latitude);
            dest.writeString(this.houseImg);
            dest.writeString(this.indoorImg);
            dest.writeString(this.personImg);
            dest.writeString(this.gateImg);
            dest.writeString(this.streetAddress);
            dest.writeString(this.communityAddress);
            dest.writeString(this.streetImg);
            dest.writeString(this.maxEnd);
            dest.writeString(this.otherImg);
            dest.writeTypedList(this.serviceListVos);
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
            this.sex = in.readString();
            this.details = in.readString();
            this.certificateNo = in.readString();
            this.idNo = in.readString();
            this.year = in.readString();
            this.place = in.readString();
            this.longitude = in.readDouble();
            this.latitude = in.readDouble();
            this.houseImg = in.readString();
            this.indoorImg = in.readString();
            this.personImg = in.readString();
            this.gateImg = in.readString();
            this.streetAddress = in.readString();
            this.communityAddress = in.readString();
            this.streetImg = in.readString();
            this.maxEnd = in.readString();
            this.otherImg = in.readString();
            this.serviceListVos = in.createTypedArrayList(ServiceListVosBean.CREATOR);
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
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
