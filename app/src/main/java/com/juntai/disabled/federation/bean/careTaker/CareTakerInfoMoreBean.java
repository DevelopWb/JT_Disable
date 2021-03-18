package com.juntai.disabled.federation.bean.careTaker;


import com.juntai.disabled.basecomponent.base.BaseResult;

/**
 * @Author: tobato
 * @Description: 作用描述  托养信息 更多
 * @CreateDate: 2020/7/12 17:43
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/12 17:43
 */
public class CareTakerInfoMoreBean extends BaseResult {
    /**
     * error : null
     * returnValue : null
     * list : null
     * type : null
     * data : {"id":2,"description":"常在村南；测试","topContacts":"王村长","topRelation":"村长","topTel":"12345678901",
     * "contact":"王桂花","relation":"妻子","phone":"12345678907","remark":"常在村南池塘边钓鱼"}
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
         * id : 2
         * description : 常在村南；测试
         * topContacts : 王村长
         * topRelation : 村长
         * topTel : 12345678901
         * contact : 王桂花
         * relation : 妻子
         * phone : 12345678907
         * remark : 常在村南池塘边钓鱼
         */

        private int id;
        private String description;
        private String topContacts;
        private String topRelation;
        private String topTel;
        private String contact;
        private String relation;
        private String phone;
        private String remark;
        private int approvalStatus;//审批状态（0：待审核；1：通过；2：驳回;3：未申请修改）

        public int getApprovalStatus() {
            return approvalStatus;
        }

        public void setApprovalStatus(int approvalStatus) {
            this.approvalStatus = approvalStatus;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDescription() {
            return description == null ? "暂无" : description;
        }

        public void setDescription(String description) {
            this.description = description == null ? "暂无" : description;
        }

        public String getTopContacts() {
            return topContacts == null ? "暂无" : topContacts;
        }

        public void setTopContacts(String topContacts) {
            this.topContacts = topContacts == null ? "暂无" : topContacts;
        }

        public String getTopRelation() {
            return topRelation == null ? "暂无" : topRelation;
        }

        public void setTopRelation(String topRelation) {
            this.topRelation = topRelation == null ? "暂无" : topRelation;
        }

        public String getTopTel() {
            return topTel == null ? "暂无" : topTel;
        }

        public void setTopTel(String topTel) {
            this.topTel = topTel == null ? "暂无" : topTel;
        }

        public String getContact() {
            return contact == null||"".equals(contact) ? "暂无" : contact;
        }

        public void setContact(String contact) {
            this.contact = contact == null ? "暂无" : contact;
        }

        public String getRelation() {
            return relation == null||"".equals(relation) ? "暂无" : relation;
        }

        public void setRelation(String relation) {
            this.relation = relation == null ? "暂无" : relation;
        }

        public String getPhone() {
            return phone == null||"".equals(phone) ? "暂无" : phone;
        }

        public void setPhone(String phone) {
            this.phone = phone == null ? "暂无" : phone;
        }

        public String getRemark() {
            return remark == null ? "暂无" : remark;
        }

        public void setRemark(String remark) {
            this.remark = remark == null ? "暂无" : remark;
        }
    }
}
