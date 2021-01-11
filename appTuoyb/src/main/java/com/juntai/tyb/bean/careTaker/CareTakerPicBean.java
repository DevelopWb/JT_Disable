package com.juntai.tyb.bean.careTaker;

/**
 * @Author: tobato
 * @Description: 作用描述  托养人员相关图片类
 * @CreateDate: 2020/7/8 11:21
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/8 11:21
 */
public class CareTakerPicBean {

    private String title;//标题
    private String picPath;//选择图片的路径

    private  boolean isImportant;//是否重要

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }

    public CareTakerPicBean(String title, String picPath, boolean isImportant) {
        this.title = title;
        this.picPath = picPath;
        this.isImportant = isImportant;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title == null ? "" : title;
    }

    public String getPicPath() {
        return picPath == null ? "" : picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath == null ? "" : picPath;
    }
}
