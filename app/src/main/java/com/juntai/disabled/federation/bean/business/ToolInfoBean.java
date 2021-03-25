package com.juntai.disabled.federation.bean.business;

import com.juntai.disabled.basecomponent.base.BaseResult;

/**
 * @Author: tobato
 * @Description: 作用描述  辅具详情
 * @CreateDate: 2021/3/24 15:15
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/3/24 15:15
 */
public class ToolInfoBean extends BaseResult {

    /**
     * error : null
     * data : {"id":3,"name":"防滑垫","img":null,"content":null,"inventory":999}
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
         * name : 防滑垫
         * img : null
         * content : null
         * inventory : 999
         */

        private int id;
        private String name;
        private String img;
        private String content;
        private int inventory;

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

        public String getImg() {
            return img == null ? "" : img;
        }

        public void setImg(String img) {
            this.img = img == null ? "" : img;
        }

        public String getContent() {
            return content == null ? "" : content;
        }

        public void setContent(String content) {
            this.content = content == null ? "" : content;
        }

        public int getInventory() {
            return inventory;
        }

        public void setInventory(int inventory) {
            this.inventory = inventory;
        }
    }
}
