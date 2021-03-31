package com.juntai.disabled.federation.base;

import com.juntai.disabled.basecomponent.mvp.BaseIView;
import com.juntai.disabled.basecomponent.mvp.IPresenter;

/**
 * Describe: 首页
 * Create by zhangzhenlong
 * 2020-8-8
 * email:954101549@qq.com
 */
public interface MainPageContract {
    String DELETE_NEWS_DRAFTS = "delete_news_drafts";
    String UPLOAD_HISTORY = "upload_history";


    interface BaseIMainPageView extends BaseIView {
    }

    interface IMainPagePresent extends IPresenter<BaseIMainPageView> {
        /**
         * 删除资讯图片
         * @param informationId 资讯id
         * @param tag
         */
        void deleteNewsDrafts(String informationId, String tag);

        /**
         * 轨迹长传
         * @param data
         */
        void uploadHistory(String data, String tag);
    }
}
