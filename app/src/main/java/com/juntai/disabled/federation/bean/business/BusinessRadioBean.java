package com.juntai.disabled.federation.bean.business;

/**
 * @Author: tobato
 * @Description: 作用描述 radiobutton
 * @CreateDate: 2021/1/16 15:22
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/1/16 15:22
 */
public class BusinessRadioBean {

    private String key;
    private int  defaultSelectedIndex;

    public BusinessRadioBean(String key, int defaultSelectedIndex) {
        this.key = key;
        this.defaultSelectedIndex = defaultSelectedIndex;
    }

    public String getKey() {
        return key == null ? "" : key;
    }

    public void setKey(String key) {
        this.key = key == null ? "" : key;
    }

    public int getDefaultSelectedIndex() {
        return defaultSelectedIndex;
    }

    public void setDefaultSelectedIndex(int defaultSelectedIndex) {
        this.defaultSelectedIndex = defaultSelectedIndex;
    }
}
