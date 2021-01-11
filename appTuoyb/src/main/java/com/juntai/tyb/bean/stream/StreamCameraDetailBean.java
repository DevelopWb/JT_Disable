package com.juntai.tyb.bean.stream;


import com.juntai.disabled.basecomponent.base.BaseResult;

/**
 * @Author: tobato
 * @Description: 作用描述   摄像头详情
 * @CreateDate: 2020/6/3 8:34
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/6/3 8:34
 */
public class StreamCameraDetailBean extends BaseResult {

    /**
     * error : null
     * returnValue : null
     * msg : null
     * list : null
     * type : null
     * data : {"id":3,"number":"37130201561327011001","place":"永恒华府","ezopen":"https://www.juntaikeji
     * .com:17002/cameraImg/thumbnail/37130201561327011001.jpeg","isOnLine":1,"frozenstatus":0,"remark":"永恒华府 能看到河的三岔口"}
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
         * id : 3
         * number : 37130201561327011001
         * place : 永恒华府
         * ezopen : https://www.juntaikeji.com:17002/cameraImg/thumbnail/37130201561327011001.jpeg
         * isOnLine : 1
         * frozenstatus : 0
         * remark : 永恒华府 能看到河的三岔口
         */

        private int id;
        private String number;
        private String place;
        private String ezopen;
        private int isOnLine;
        private int frozenstatus;
        private String remark;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getEzopen() {
            return ezopen;
        }

        public void setEzopen(String ezopen) {
            this.ezopen = ezopen;
        }

        public int getIsOnLine() {
            return isOnLine;
        }

        public void setIsOnLine(int isOnLine) {
            this.isOnLine = isOnLine;
        }

        public int getFrozenstatus() {
            return frozenstatus;
        }

        public void setFrozenstatus(int frozenstatus) {
            this.frozenstatus = frozenstatus;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
