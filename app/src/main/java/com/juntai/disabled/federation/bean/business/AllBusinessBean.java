package com.juntai.disabled.federation.bean.business;

import com.juntai.disabled.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  所有的业务
 * @CreateDate: 2021/2/18 9:44
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/18 9:44
 */
public class AllBusinessBean extends BaseResult {


    /**
     * error : null
     * data : [{"typeId":1,"typeName":"常用业务办理","workMatterList":[{"id":1,"name":"残疾人证办理","icon":"http://192.168.124
     * .115:8093/work_type_matter_img/残疾人证办理.png"},{"id":2,"name":"残疾证换领","icon":"http://192.168.124
     * .115:8093/work_type_matter_img/残疾证换领.png"},{"id":3,"name":"残疾证等级变更","icon":"http://192.168.124
     * .115:8093/work_type_matter_img/残疾证等级变更.png"},{"id":4,"name":"残疾证补办","icon":"http://192.168.124
     * .115:8093/work_type_matter_img/残疾证补办.png"},{"id":5,"name":"残疾证迁入","icon":"http://192.168.124
     * .115:8093/work_type_matter_img/残疾证迁入.png"},{"id":6,"name":"残疾证迁出","icon":"http://192.168.124
     * .115:8093/work_type_matter_img/残疾证迁出.png"},{"id":7,"name":"残疾证注销","icon":"http://192.168.124
     * .115:8093/work_type_matter_img/残疾证注销.png"},{"id":8,"name":"残疾人就业登记","icon":"http://192.168.124
     * .115:8093/work_type_matter_img/残疾人就业登记.png"},{"id":9,"name":"残疾儿童抢救性康复项目","icon":"http://192.168.124
     * .115:8093/work_type_matter_img/残疾人儿童抢救性康复项目.png"},{"id":10,"name":"残疾学生助学金","icon":"http://192.168.124
     * .115:8093/work_type_matter_img/残疾学生助学金.png"}]},{"typeId":2,"typeName":"公共服务事项","workMatterList":[{"id":11,
     * "name":"家庭无障碍改造项目","icon":"http://192.168.124.115:8093/work_type_matter_img/家庭无障碍改造项目.png"},{"id":12,
     * "name":"残疾人辅助用品用具申请","icon":"http://192.168.124.115:8093/work_type_matter_img/残疾人辅助用品用具申请.png"},{"id":13,
     * "name":"残疾人培训申请","icon":"http://192.168.124.115:8093/work_type_matter_img/残疾人培训申请.png"},{"id":14,
     * "name":"残疾人居家托养","icon":"http://192.168.124.115:8093/work_type_matter_img/残疾人居家托养.png"}]},{"typeId":3,
     * "typeName":"意见建议","workMatterList":[{"id":15,"name":"意见建议","icon":"http://192.168.124
     * .115:8093/work_type_matter_img/意见建议.png"}]}]
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
         * typeId : 1
         * typeName : 常用业务办理
         * workMatterList : [{"id":1,"name":"残疾人证办理","icon":"http://192.168.124.115:8093/work_type_matter_img/残疾人证办理
         * .png"},{"id":2,"name":"残疾证换领","icon":"http://192.168.124.115:8093/work_type_matter_img/残疾证换领.png"},{"id
         * ":3,"name":"残疾证等级变更","icon":"http://192.168.124.115:8093/work_type_matter_img/残疾证等级变更.png"},{"id":4,
         * "name":"残疾证补办","icon":"http://192.168.124.115:8093/work_type_matter_img/残疾证补办.png"},{"id":5,
         * "name":"残疾证迁入","icon":"http://192.168.124.115:8093/work_type_matter_img/残疾证迁入.png"},{"id":6,
         * "name":"残疾证迁出","icon":"http://192.168.124.115:8093/work_type_matter_img/残疾证迁出.png"},{"id":7,
         * "name":"残疾证注销","icon":"http://192.168.124.115:8093/work_type_matter_img/残疾证注销.png"},{"id":8,
         * "name":"残疾人就业登记","icon":"http://192.168.124.115:8093/work_type_matter_img/残疾人就业登记.png"},{"id":9,
         * "name":"残疾儿童抢救性康复项目","icon":"http://192.168.124.115:8093/work_type_matter_img/残疾人儿童抢救性康复项目.png"},{"id":10,
         * "name":"残疾学生助学金","icon":"http://192.168.124.115:8093/work_type_matter_img/残疾学生助学金.png"}]
         */

        private int typeId;
        private String typeName;
        private List<WorkMatterListBean> workMatterList;

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public List<WorkMatterListBean> getWorkMatterList() {
            return workMatterList;
        }

        public void setWorkMatterList(List<WorkMatterListBean> workMatterList) {
            this.workMatterList = workMatterList;
        }

        public static class WorkMatterListBean {
            /**
             * id : 1
             * name : 残疾人证办理
             * icon : http://192.168.124.115:8093/work_type_matter_img/残疾人证办理.png
             */

            private int id;
            private String name;
            private String icon;

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

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }
        }
    }
}
