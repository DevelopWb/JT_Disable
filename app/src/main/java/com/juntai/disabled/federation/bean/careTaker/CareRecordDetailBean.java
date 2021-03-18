package com.juntai.disabled.federation.bean.careTaker;


import com.juntai.disabled.basecomponent.base.BaseResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  托养记录详情
 * @CreateDate: 2020/7/13 9:31
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/13 9:31
 */
public class CareRecordDetailBean extends BaseResult {


    /**
     * error : null
     * returnValue : null
     * list : null
     * type : null
     * data : {"id":24,"startTime":"2019-09-23 10:27:15","endTime":"2019-09-23 12:27:15","serviceLength":"2小时",
     * "describe":"彭善想 工业园 王家于埠","servicer":"陈庆香","gmtCreat":"2020-07-09 10:52:12","serviceFileVos":[{"id":24,
     * "path":"/case_image/31e9a6ac362f4a56aed06f755e367e25.jpeg","flag":0},{"id":55,"path":"/case_image
     * /199358de941c473daeb206116ba5d8d1.jpeg","flag":0},{"id":86,"path":"/case_image
     * /f3216e46cb384d4cbb43b5ac4c1c162f.jpeg","flag":0},{"id":117,"path":"/case_video
     * /5e1cd1de31c54525b284f64bc78027ac.mp4","flag":1}]}
     * success : true
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
         * id : 24
         * startTime : 2019-09-23 10:27:15
         * endTime : 2019-09-23 12:27:15
         * serviceLength : 2小时
         * describe : 彭善想 工业园 王家于埠
         * servicer : 陈庆香
         * gmtCreat : 2020-07-09 10:52:12
         * serviceFileVos : [{"id":24,"path":"/case_image/31e9a6ac362f4a56aed06f755e367e25.jpeg","flag":0},{"id":55,
         * "path":"/case_image/199358de941c473daeb206116ba5d8d1.jpeg","flag":0},{"id":86,"path":"/case_image
         * /f3216e46cb384d4cbb43b5ac4c1c162f.jpeg","flag":0},{"id":117,"path":"/case_video
         * /5e1cd1de31c54525b284f64bc78027ac.mp4","flag":1}]
         */

        private int id;
        private String startTime;
        private String endTime;
        private String serviceLength;
        private String describe;
        private String servicer;
        private String cateName;//服务类型
        private String gmtCreat;
        private List<ServiceFileVosBean> serviceFileVos;

        public String getCateName() {
            return cateName == null ? "暂无" : cateName;
        }

        public void setCateName(String cateName) {
            this.cateName = cateName == null ? "" : cateName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStartTime() {
            return startTime == null ? "暂无" : startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime == null ? "暂无" : startTime;
        }

        public String getEndTime() {
            return endTime == null ? "暂无" : endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime == null ? "暂无" : endTime;
        }

        public String getServiceLength() {
            return serviceLength == null ? "暂无" : serviceLength;
        }

        public void setServiceLength(String serviceLength) {
            this.serviceLength = serviceLength == null ? "暂无" : serviceLength;
        }

        public String getDescribe() {
            return describe == null ? "暂无" : describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe == null ? "暂无" : describe;
        }

        public String getServicer() {
            return servicer == null ? "暂无" : servicer;
        }

        public void setServicer(String servicer) {
            this.servicer = servicer == null ? "暂无" : servicer;
        }

        public String getGmtCreat() {
            return gmtCreat == null ? "暂无" : gmtCreat;
        }

        public void setGmtCreat(String gmtCreat) {
            this.gmtCreat = gmtCreat == null ? "暂无" : gmtCreat;
        }

        public List<ServiceFileVosBean> getServiceFileVos() {
            if (serviceFileVos == null) {
                return new ArrayList<>();
            }
            return serviceFileVos;
        }

        public void setServiceFileVos(List<ServiceFileVosBean> serviceFileVos) {
            this.serviceFileVos = serviceFileVos;
        }

        public static class ServiceFileVosBean {
            /**
             * id : 24
             * path : /case_image/31e9a6ac362f4a56aed06f755e367e25.jpeg
             * flag : 0
             */

            private int id;
            private String path;
            private int flag;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPath() {
                return path == null ? "" : path;
            }

            public void setPath(String path) {
                this.path = path == null ? "" : path;
            }

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }
        }
    }
}
