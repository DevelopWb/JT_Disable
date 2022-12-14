package com.juntai.disabled.federation.utils;

import com.juntai.disabled.federation.AppHttpPath;
import com.juntai.disabled.federation.MyApp;

import java.util.Map;

/**
 * author:wong
 * Date: 2019/3/27
 * Description:
 */
public class UrlFormatUtil {

    public static String formatUrl(String path, Map<String, String> params) {
        String url = path + "?" + "account=" + MyApp.getAccount() + "&token=" + MyApp.getUserToken();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            url += "&" + entry.getKey() + "=" + entry.getValue();
        }
        return url;
    }
    /**
     * 内容图片url拼接
     *
     * @param path
     * @return
     */
    public static String formatPicUrl(String path) {
//        return AppHttpPath.BASE_IMAGE + path;
        return  path;
    }

    /**
     * 车辆直播流
     *
     * @param path
     * @return
     */
    public static String getCarStream(String path) {
        String url = AppHttpPath.CAR_STREAM +  path;
        return url;
    }

    /**
     * 缩略图url拼接（巡检）
     *
     * @param pathId
     * @return
     */
    public static String getInspectionThumImg(int pathId) {
        return String.format("%s%s%s", AppHttpPath.INSPECTION_THUMBNAI_IMAGE, "?id=", pathId);
    }

    /**
     * 原图url拼接（巡检）
     *
     * @param pathId
     * @return
     */
    public static String getInspectionOriginalImg(int pathId) {
        return String.format("%s%s%s", AppHttpPath.INSPECTION_ORIGINAL_IMAGE, "?id=", pathId);
    }
}
