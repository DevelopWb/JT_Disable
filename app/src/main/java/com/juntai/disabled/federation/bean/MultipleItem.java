package com.juntai.disabled.federation.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/4/15 10:08
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/15 10:08
 */
public class MultipleItem implements MultiItemEntity {
    public static final int ITEM_TITLE = 1;//类型1
    public static final int ITEM_CONTENT = 2;//类型2
    public static final int ITEM_LOAD_MORE = 3;//类型3
    public static final int ITEM_BUSINESS_HEAD_PIC = 4;//类型3
    public static final int ITEM_BUSINESS_TITILE_BIG = 5;//类型3
    public static final int ITEM_BUSINESS_TITILE_SMALL = 6;//类型3
    public static final int ITEM_BUSINESS_EDIT = 7;//类型3
    public static final int ITEM_BUSINESS_SELECT = 8;//类型3
    public static final int ITEM_BUSINESS_RADIO = 9;//类型3
    public static final int ITEM_BUSINESS_PIC = 10;//类型3
    public static final int ITEM_BUSINESS_SIGN = 11;//签字
    public static final int ITEM_BUSINESS_NOTICE = 12;//提示
    public static final int ITEM_BUSINESS_YEAR = 13;//年度
    public static final int ITEM_BUSINESS_NORMAL_RECYCLEVIEW = 14;//年度
    public static final int ITEM_BUSINESS_EDIT2 = 15;//
    public static final int ITEM_BUSINESS_DEAF_TABLE = 16;//聋儿表单中的部分布局

    private int itemType;
    private Object object;

    public MultipleItem(int itemType, Object object) {
        this.itemType = itemType;
        this.object = object;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
