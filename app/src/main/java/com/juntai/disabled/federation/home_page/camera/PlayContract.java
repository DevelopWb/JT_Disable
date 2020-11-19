package com.juntai.disabled.federation.home_page.camera;

import com.juntai.disabled.basecomponent.mvp.IView;

import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/5/30 9:50
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/30 9:50
 */
public interface PlayContract{


    String GET_URL_PATH = "get_url_path";//获取流地址
    String GET_STREAM_CAMERA_DETAIL = "get_url_path_detail";//获取摄像头详情
    String GET_STREAM_CAMERA_THUMBNAIL = "get_url_path_capture";//缩略图
    String GET_STREAM_CAMERA_CAPTURE = "get_url_path_capture_save";//截图  保存本地
    String UPLOAD_CAMERA_CAPTURE = "upload_path_capture";//上传截图
    interface IPlayView extends IView {

    }

    interface IPlayPresent{
        /**
         * 打开视频流
         * @param channelid
         * @param type
         * @param videourltype
         * @param tag
         */
        void openStream(String channelid, String type, String videourltype, String tag);

        /**
         * 截图
         * @param channelid
         * @param type
         * @param tag
         */
        void  capturePic(String channelid,String type,String tag);

        /**
         * 摄像头详情
         * @param tag
         */
        void  getStreamCameraDetail( RequestBody requestBody, String tag);
        /**
         * 上传封面图
         * @param tag
         */
        void  uploadStreamCameraCapturePic( RequestBody requestBody, String tag);
        /**
         * 上传封面图
         * @param tag
         */
        void  uploadStreamCameraThumbPic(RequestBody requestBody, String tag);

        /**
         * 获取评论
         * @param
         */
        void getCommentList(int commentedId, int pageSize, int currentPage, String tag);
        /**
         * 获取子评论
         * @param
         */
        void getCommentChildList(int commentedId, int unreadId, int pageSize, int currentPage, String tag);

        /**
         * 点赞
         * @param id 重复添加点赞是传likeid
         * @param isType 状态（0：点赞）（1：取消点赞）必传
         * @param typeId 类型id （取消点赞时不传）
         * @param likeId 点赞时为被点赞的主键；取消点赞时为点赞表的主键
         */
        void like(int id, int userId, int isType, int typeId, int likeId,String tag);
    }
}
