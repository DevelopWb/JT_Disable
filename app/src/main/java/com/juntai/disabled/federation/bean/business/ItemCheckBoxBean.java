package com.juntai.disabled.federation.bean.business;

/**
 * @Author: tobato
 * @Description: 作用描述  checkbox
 * @CreateDate: 2021/1/29 10:11
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/1/29 10:11
 */
public class ItemCheckBoxBean {

    private int index;//序号
    private String key;
    private boolean selecte;
    private  String des;//其他困难中的描述

    public ItemCheckBoxBean(int index, String key, boolean selecte) {
        this.index = index;
        this.key = key;
        this.selecte = selecte;
    }

    public int getIndex() {
        return index;
    }

    public String getDes() {
        return des == null ? "" : des;
    }

    public void setDes(String des) {
        this.des = des == null ? "" : des;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getKey() {
        return key == null ? "" : key;
    }

    public void setKey(String key) {
        this.key = key == null ? "" : key;
    }

    public boolean isSelecte() {
        return selecte;
    }

    public void setSelecte(boolean selecte) {
        this.selecte = selecte;
    }
}
