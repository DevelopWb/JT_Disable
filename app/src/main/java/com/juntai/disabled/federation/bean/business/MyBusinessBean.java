package com.juntai.disabled.federation.bean.business;

import com.google.gson.annotations.SerializedName;
import com.juntai.disabled.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/5/22 10:43
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/22 10:43
 */
public class MyBusinessBean extends BaseResult {

    /**
     * error : null
     * data : {"datas":[{"id":1,"matterId":14,"matterName":"残疾人居家托养","businessId":1,"gmtCreate":"2020-11-20 16:38:52
     * .0","status":0,"auditOpinion":"正在审核中"}],"total":1,"listSize":1,"pageCount":1}
     * type : null
     * list : null
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
         * datas : [{"id":1,"matterId":14,"matterName":"残疾人居家托养","businessId":1,"gmtCreate":"2020-11-20 16:38:52.0",
         * "status":0,"auditOpinion":"正在审核中"}]
         * total : 1
         * listSize : 1
         * pageCount : 1
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

        public static class DatasBean {
            /**
             * id : 1
             * matterId : 14
             * matterName : 残疾人居家托养
             * businessId : 1
             * gmtCreate : 2020-11-20 16:38:52.0
             * status : 0
             * auditOpinion : 正在审核中
             */

            private int id;
            private int matterId;
            private String matterName;
            private int businessId;
            private boolean checked;
            private String gmtCreate;
            @SerializedName("status")
            private int statusX;
            private String auditOpinion;

            public int getId() {
                return id;
            }

            public boolean isChecked() {
                return checked;
            }

            public void setChecked(boolean checked) {
                this.checked = checked;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getMatterId() {
                return matterId;
            }

            public void setMatterId(int matterId) {
                this.matterId = matterId;
            }

            public String getMatterName() {
                return matterName;
            }

            public void setMatterName(String matterName) {
                this.matterName = matterName;
            }

            public int getBusinessId() {
                return businessId;
            }

            public void setBusinessId(int businessId) {
                this.businessId = businessId;
            }

            public String getGmtCreate() {
                return gmtCreate;
            }

            public void setGmtCreate(String gmtCreate) {
                this.gmtCreate = gmtCreate;
            }

            public int getStatusX() {
                return statusX;
            }

            public void setStatusX(int statusX) {
                this.statusX = statusX;
            }

            public String getAuditOpinion() {
                return auditOpinion;
            }

            public void setAuditOpinion(String auditOpinion) {
                this.auditOpinion = auditOpinion;
            }
        }
    }
}
