package com.juntai.disabled.federation.bean.business;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021/1/28 10:57
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/1/28 10:57
 */
public class RecycleBean {

    private int layoutManagerType;//布局类型  0是 横向  1是竖向  2是 GridLayoutManager
    private int spanCount;//当是GridLayoutManager的时候 列数
    private List<ItemCheckBoxBean> data;//recycleview的数据
    private String titleKey;
    private boolean isSigleSelect;//是否是单选
    private boolean isImportant;//是否必填
    public RecycleBean(int layoutManagerType, int spanCount, List<ItemCheckBoxBean> data,String titleKey,
                       boolean isSigleSelect,boolean isImportant) {
        this.layoutManagerType = layoutManagerType;
        this.spanCount = spanCount;
        this.data = data;
        this.titleKey = titleKey;
        this.isSigleSelect = isSigleSelect;
        this.isImportant = isImportant;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }

    public boolean isSigleSelect() {
        return isSigleSelect;
    }

    public void setSigleSelect(boolean sigleSelect) {
        isSigleSelect = sigleSelect;
    }

    public String getTitleKey() {
        return titleKey == null ? "" : titleKey;
    }

    public void setTitleKey(String titleKey) {
        this.titleKey = titleKey == null ? "" : titleKey;
    }

    public int getSpanCount() {
        return spanCount;
    }

    public void setSpanCount(int spanCount) {
        this.spanCount = spanCount;
    }

    public int getLayoutManagerType() {
        return layoutManagerType;
    }

    public void setLayoutManagerType(int layoutManagerType) {
        this.layoutManagerType = layoutManagerType;
    }

    public List<ItemCheckBoxBean> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<ItemCheckBoxBean> data) {
        this.data = data;
    }
}
