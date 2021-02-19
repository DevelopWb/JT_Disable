package com.juntai.disabled.federation.bean.business;

import com.juntai.disabled.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021/2/19 15:09
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/19 15:09
 */
public class ChildBusinessesBean extends BaseResult {


    /**
     * error : null
     * data : [{"id":16,"name":"精准康复智力残疾儿童康复救助","icon":null},{"id":17,"name":"精准康复孤独儿童康复救助","icon":null},{"id":18,
     * "name":"精准康复聋儿童康复救助","icon":null},{"id":19,"name":"精准康复脑瘫儿童康复救助","icon":null}]
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
         * id : 16
         * name : 精准康复智力残疾儿童康复救助
         * icon : null
         */

        private int id;
        private String name;
        private Object icon;

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

        public Object getIcon() {
            return icon;
        }

        public void setIcon(Object icon) {
            this.icon = icon;
        }
    }
}
