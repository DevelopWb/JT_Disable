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

    private List<String> data;//recycleview的数据

    public RecycleBean(int layoutManagerType, int spanCount, List<String> data) {
        this.layoutManagerType = layoutManagerType;
        this.spanCount = spanCount;
        this.data = data;
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

    public List<String> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
