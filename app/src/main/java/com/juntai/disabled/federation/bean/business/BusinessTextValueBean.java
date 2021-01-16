package com.juntai.disabled.federation.bean.business;

/**
 * @Author: tobato
 * @Description: 作用描述  办理业务中 text的值
 * @CreateDate: 2021/1/16 11:48
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/1/16 11:48
 */
public class BusinessTextValueBean {

    private String key;
    private String value;
    private String hint;

    public BusinessTextValueBean(String key, String value, String hint) {
        this.key = key;
        this.value = value;
        this.hint = hint;
    }

    public String getKey() {
        return key == null ? "" : key;
    }

    public void setKey(String key) {
        this.key = key == null ? "" : key;
    }

    public String getValue() {
        return value == null ? "" : value;
    }

    public void setValue(String value) {
        this.value = value == null ? "" : value;
    }

    public String getHint() {
        return hint == null ? "" : hint;
    }

    public void setHint(String hint) {
        this.hint = hint == null ? "" : hint;
    }
}
