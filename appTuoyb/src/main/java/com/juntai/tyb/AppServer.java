package com.juntai.tyb;


import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.bean.CareChildListNewestBean;
import com.juntai.disabled.basecomponent.bean.OpenLiveBean;
import com.juntai.tyb.bean.CityBean;
import com.juntai.tyb.bean.UserBaseMsgBean;
import com.juntai.tyb.bean.careTaker.ServiceTypeBean;
import com.juntai.tyb.bean.healthOrg.HealthOrgPositionBean;
import com.juntai.tyb.bean.LoginBean;
import com.juntai.tyb.bean.ServicePeoplePositionBean;
import com.juntai.tyb.bean.ServiceRecordBean;
import com.juntai.tyb.bean.SearchResultBean;
import com.juntai.tyb.bean.careTaker.CareRecordDetailBean;
import com.juntai.tyb.bean.careTaker.CareRecordPositionBean;
import com.juntai.tyb.bean.careTaker.CareTakerBaseInfoBean;
import com.juntai.tyb.bean.careTaker.CareTakerInfoBean;
import com.juntai.tyb.bean.careTaker.CareTakerInfoMoreBean;
import com.juntai.tyb.bean.careTaker.SearchedPeopleBean;
import com.juntai.tyb.bean.careTaker.ServicePeoplesBean;
import com.juntai.tyb.bean.careTaker.StreetBean;
import com.juntai.tyb.bean.careTaker.YearsBean;
import com.juntai.tyb.bean.healthOrg.HealthOrganizeDetailBean;
import com.juntai.tyb.bean.mine.MyMsgBean;
import com.juntai.tyb.bean.mine.UnReadMsgBean;
import com.juntai.tyb.bean.stream.StreamCameraBean;
import com.juntai.tyb.bean.stream.StreamCameraDetailBean;
import com.juntai.tyb.bean.weather.ResponseForcastWeather;
import com.juntai.tyb.bean.weather.ResponseRealTimeWeather;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * responseBody里的数据只能调用(取出)一次，第二次为空。可赋值给新的变量使用
 */
public interface AppServer {
    //    /**
    //     * 登录
    //     *
    //     * @param account
    //     * @param password
    //     * @return
    //     */
    //    @POST(AppHttpPath.LOGIN)
    //    Observable<UserBean> login(@Query("account") String account, @Query("password") String password);
    //    //车辆轨迹
    //    @POST(AppHttpPath.CAR_HISTORY)
    //    Observable<ResponseCarHistory> getPoliceCarTrack(@Body RequestBody body);
    //


    /**
     * 获取我的案件
     */
    @POST(AppHttpPath.GET_MYCASE)
    Observable<CareChildListNewestBean> getMyCase(@Body RequestBody body);

    /**
     * 获取我的案件
     */
    @POST(AppHttpPath.SEARCH)
    Observable<SearchResultBean> search(@Body RequestBody body);





    /*====================================================    描述信息
    ==============================================================*/

//    /**
//     * 获取托养子列表
//     */
//    @POST(AppHttpPath.CASE_INFO)
//    Observable<CareChildListNewestBean> getMyCare(@Body RequestBody requestBody);

    /**
     * 获取所有的区域 街道
     */
    @POST(AppHttpPath.ALL_STREETS)
    Observable<StreetBean> getStreets();

    /**
     * 获取所有的区域 街道
     */
    @POST(AppHttpPath.GET_YEARS)
    Observable<YearsBean> getAllYears();

    /*==================================================== 登录接口 ==============================================================*/
    /**
     * 登录
     */
    @POST(AppHttpPath.LOGIN)
    Observable<LoginBean> login(@Query("account") String account, @Query("password") String password);





    /**
     * 搜索托养人员
     */
    @POST(AppHttpPath.SEARCH_CARETAKER)
    Observable<SearchedPeopleBean> searchCareTaker(@Body RequestBody requestBody);
    /**
     * 搜索所有的残疾人
     */
    @POST(AppHttpPath.SEARCH_ALL_DISABLED_PEOPLE)
    Observable<SearchedPeopleBean> searchDisabledPeoples(@Body RequestBody requestBody);
    /**
     * 托养信息
     */
    @POST(AppHttpPath.CARE_INFO)
    Observable<CareTakerInfoBean> careInfo(@Body RequestBody requestBody);
    /**
     * 托养信息  更多
     */
    @POST(AppHttpPath.CARE_INFO_MORE)
    Observable<CareTakerInfoMoreBean> careInfoMore(@Body RequestBody requestBody);
    /**
     * 托养信息
     */
    @POST(AppHttpPath.CARE_RECORD)
    Observable<CareRecordDetailBean> careRecord(@Body RequestBody requestBody);
    /**
     * 托养信息
     */
    @POST(AppHttpPath.CARE_TAKER_BASE_INFO)
    Observable<CareTakerBaseInfoBean> careTakerBaseInfo(@Body RequestBody requestBody);
    /**
     * 添加托养人
     */
    @POST(AppHttpPath.ADD_CARE_TAKER)
    Observable<BaseResult> addCareTaker(@Body RequestBody requestBody);
    /**
     * 修改托养人
     */
    @POST(AppHttpPath.MODIFY_CARE_TAKER)
    Observable<BaseResult> modifyCareTaker(@Body RequestBody requestBody);
    /**
     * 托养分布
     */
    @POST(AppHttpPath.CARE_RECORD_POSITIONS)
    Observable<CareRecordPositionBean> careRecordPosition(@Body RequestBody requestBody);

    /**
     * 按街道分类服务人员
     */
    @POST(AppHttpPath.GET_SERVICE_PEOPLE)
    Observable<ServicePeoplesBean> getServicePeople(@Body RequestBody requestBody);



    /**
     * 提交托养记录
     */
    @POST(AppHttpPath.COMMIT_CARE_RECORD)
    Observable<BaseResult> commitCareRecord(@Body RequestBody requestBody);
    /**
     * 获取服务类型
     */
    @POST(AppHttpPath.GET_SERVICE_TYPE)
    Observable<ServiceTypeBean> getServiceType(@Body RequestBody requestBody);

    /*====================================================    天气   ==============================================================*/


    //实时天气
    @POST(AppHttpPath.REALTIME_WEATHER)
    Observable<ResponseRealTimeWeather> getWeatherRealtime(@Query("longitude") String longitude, @Query("latitude") String latitude);
    //天气预报
    @POST(AppHttpPath.FORCAST_WEATHER)
    Observable<ResponseForcastWeather> getForcast(@Query("longitude") String longitude, @Query("latitude") String latitude);

    @POST(AppHttpPath.PROVINCE)
    Observable<CityBean> getProvince();

    @POST(AppHttpPath.CITY)
    Observable<CityBean> getCity(@Query("cityNum") int cityNum);

    @POST(AppHttpPath.AREA)
    Observable<CityBean> getArea(@Query("cityNum") int cityNum);
    @POST(AppHttpPath.STREET)
    Observable<CityBean> getStreet(@Query("cityName") int townNum);


    /*====================================================    流媒体   ==============================================================*/




    /**
     * 获取所有的流摄像头
     *
     * @return
     */
    @POST(AppHttpPath.STREAM_CAMERAS)
    Observable<StreamCameraBean> getAllStreamCameras(@Body RequestBody requestBody);


    /**
     * 获取硬盘录像机下的所有的流摄像头
     *
     * @return
     */
    @POST(AppHttpPath.STREAM_CAMERAS_FROM_VCR)
    Observable<StreamCameraBean> getAllStreamCamerasFromVCR(@Body RequestBody requestBody);

    /**
     * 摄像头详情
     *
     * @return
     */
    @POST(AppHttpPath.STREAM_CAMERAS_DETAIL)
    Observable<StreamCameraDetailBean> getStreamCameraDetail(@Body RequestBody requestBody);

    /**
     * 上传封面图
     *
     * @return
     */
    @POST(AppHttpPath.UPLOAD_STREAM_CAMERAS_THUMB)
    Observable<BaseResult> uploadStreamCameraThumbPic(@Body RequestBody requestBody);

    /**
     * 打开视频流
     *
     * 字段说明：
     * 	"channelid":  (字符串)   通道20位编号
     * 	"type":       (数字)   	 国标请求视频类型：1：udp 2：tcp主动 3：tcp被动
     * 	"videourltype":  (字符串)   视频类型：rtsp：返回rtsp地址  rtmp：返回rtmp地址 hls：返回hls地址
     * @param channelid
     * @param type
     * @param videourltype
     * @return
     */
    @GET(AppHttpPath.BASE_CAMERA_URL+"/vss/open_stream/{channelid}/{type}/{videourltype}")
    Observable<OpenLiveBean> openStream(@Path("channelid") String channelid, @Path("type")String type,
                                        @Path("videourltype")String videourltype);

    /**
     * 会话id   保活的接口
     * @param sessionid
     * @return
     */
    @GET(AppHttpPath.BASE_CAMERA_URL+"/vss/video_keepalive/{sessionid}")
    Observable<OpenLiveBean> keepAlive(@Path("sessionid") String sessionid);
    /**
     * 截图
     * @param channelid
     * @return
     */
    @GET(AppHttpPath.BASE_CAMERA_URL+"/vss/get_image/{channelid}/{type}")
    Observable<OpenLiveBean> capturePic(@Path("channelid") String channelid,@Path("type") String type);




    /**
     * 服务人员
     * @return
     */
    @POST(AppHttpPath.SERVICE_PEOPLES_POSITIONS)
    Observable<ServicePeoplePositionBean> getServicePeoplesPosition(@Body RequestBody requestBody);

    /**
     * 康复机构
     * @return
     */
    @POST(AppHttpPath.HEATH_ORGANIZE_POSITIONS)
    Observable<HealthOrgPositionBean> getHealthOrganizePosition(@Body RequestBody requestBody);
    /**
     * 康复机构详情
     * @return
     */
    @POST(AppHttpPath.HEATH_ORGANIZE_DETAIL)
    Observable<HealthOrganizeDetailBean> getHealthOrganizeDetail(@Body RequestBody requestBody);





        /*====================================================    描述信息   ==============================================================*/

    /**
     * 服务记录
     */
    @POST(AppHttpPath.SERVICE_RECORD)
    Observable<ServiceRecordBean> serviceRecord(@Body RequestBody requestBody);
    /**
     * 退出登录
     */
    @POST(AppHttpPath.LOG_OUT)
    Observable<BaseResult> logout(@Body RequestBody requestBody);
    /**
     * 个人资料
     */
    @POST(AppHttpPath.USER_INFO)
    Observable<UserBaseMsgBean> persionalInfo(@Body RequestBody requestBody);
    /**
     * 修改头像
     */
    @POST(AppHttpPath.MODIFY_HEAD_ICON)
    Observable<BaseResult> modifyHeadIcon(@Body RequestBody requestBody);
    /**
     * 修改密码
     */
    @POST(AppHttpPath.MODIFY_PWD)
    Observable<BaseResult> modifyPwd(@Body RequestBody requestBody);
    /**
     * 我的消息
     */
    @POST(AppHttpPath.MY_NOTICE)
    Observable<MyMsgBean> myNotice(@Body RequestBody requestBody);
    /**
     * 消息已读
     */
    @POST(AppHttpPath.IS_READ)
    Observable<BaseResult> msgIsRead(@Body RequestBody requestBody);
    /**
     * 未读消息
     */
    @POST(AppHttpPath.UNREAD_MSG)
    Observable<UnReadMsgBean> unReadMsgAmount(@Body RequestBody requestBody);





    /**
     * 获取托养子列表
     */
    @POST(AppHttpPath.OLD_CASE_INFO)
    Observable<CareChildListNewestBean> getMyCare(@Body RequestBody requestBody);

}
