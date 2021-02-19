package com.juntai.disabled.federation.bean.business;

import com.juntai.disabled.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021/2/19 15:26
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/19 15:26
 */
public class BusinessPropertyBean  extends BaseResult {


    /**
     * error : null
     * data : [{"id":1,"name":"汉族"},{"id":2,"name":"蒙古族"},{"id":3,"name":"回族"},{"id":4,"name":"藏族"},{"id":5,
     * "name":"维吾尔族"},"..."]
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
         * name : 汉族
         */

        private int id;
        private String name;

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
    }
}
