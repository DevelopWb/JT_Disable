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
    private int  defaultSelectedIndex ;
    private String[] values;

    public BusinessRadioBean(String key, int defaultSelectedIndex, String[] values) {
        this.key = key;
        this.defaultSelectedIndex = defaultSelectedIndex;
        this.values = values;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
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
