package com.juntai.disabled.federation.bean.business;

/**
 * @Author: tobato
 * @Description: 作用描述  checkbox
 * @CreateDate: 2021/1/29 10:11
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/1/29 10:11
 */
public class ItemCheckBoxBean {

    private String key;
    private boolean selecte;

    public ItemCheckBoxBean(String key, boolean selecte) {
        this.key = key;
        this.selecte = selecte;
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
