package com.juntai.disabled.federation.bean.business;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021/1/19 11:33
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/1/19 11:33
 */
public class BusinessPicBean {
    private String picName;
    private int picNameIndex;//图片的索引  -1 代表没有序号
    private String picPath;

    public BusinessPicBean(String picName, int picNameIndex, String picPath) {
        this.picName = picName;
        this.picNameIndex = picNameIndex;
        this.picPath = picPath;
    }

    public int getPicNameIndex() {
        return picNameIndex;
    }

    public void setPicNameIndex(int picNameIndex) {
        this.picNameIndex = picNameIndex;
    }

    public String getPicName() {
        return picName == null ? "" : picName;
    }

    public void setPicName(String picName) {
        this.picName = picName == null ? "" : picName;
    }

    public String getPicPath() {
        return picPath == null ? "" : picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath == null ? "" : picPath;
    }
}
