package com.juntai.disabled.federation;


import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.bean.BaseStreamBean;
import com.juntai.disabled.federation.bean.BannerNewsBean;
import com.juntai.disabled.federation.bean.BaseDataBean;
import com.juntai.disabled.federation.bean.CityBean;
import com.juntai.disabled.federation.bean.CollectListBean;
import com.juntai.disabled.federation.bean.CommentListBean;
import com.juntai.disabled.federation.bean.HealthOrganizeDetailBean;
import com.juntai.disabled.federation.bean.IMUsersBean;
import com.juntai.disabled.federation.bean.MapMenuButton;
import com.juntai.disabled.federation.bean.PoliceBranchBean;
import com.juntai.disabled.federation.bean.PoliceCarBean;
import com.juntai.disabled.federation.bean.PoliceDetailBean;
import com.juntai.disabled.federation.bean.PolicePickerBean;
import com.juntai.disabled.federation.bean.PolicePositionBean;
import com.juntai.disabled.federation.bean.PublishListBean;
import com.juntai.disabled.federation.bean.ResponseCarHistory;
import com.juntai.disabled.federation.bean.ResponseInspection;
import com.juntai.disabled.federation.bean.ServerPeopleBean;
import com.juntai.disabled.federation.bean.ResponseNews;
import com.juntai.disabled.federation.bean.ResponsePeople;
import com.juntai.disabled.federation.bean.ResponseSiteBean;
import com.juntai.disabled.federation.bean.SearchBean;
import com.juntai.disabled.federation.bean.SearchResultBean;
import com.juntai.disabled.federation.bean.UserBean;
import com.juntai.disabled.federation.bean.UserScoreListBean;
import com.juntai.disabled.federation.bean.business.AllBusinessBean;
import com.juntai.disabled.federation.bean.business.BusinessListBean;
import com.juntai.disabled.federation.bean.business.BusinessNeedInfoBean;
import com.juntai.disabled.federation.bean.business.BusinessPropertyBean;
import com.juntai.disabled.federation.bean.business.ChildBusinessesBean;
import com.juntai.disabled.federation.bean.business.DisabledBaseInfoBean;
import com.juntai.disabled.federation.bean.business.ToolInfoBean;
import com.juntai.disabled.federation.bean.business.detail.AssistToolDetailBean;
import com.juntai.disabled.federation.bean.business.detail.BusinessChildDetailBean;
import com.juntai.disabled.federation.bean.business.detail.StudentBursaryDetailBean;
import com.juntai.disabled.federation.bean.business.detail.EmploymentRegDetailBean;
import com.juntai.disabled.federation.bean.business.detail.HandlerCardDetailBean;
import com.juntai.disabled.federation.bean.business.MyBusinessBean;
import com.juntai.disabled.federation.bean.business.MyBusinessDetailBean;
import com.juntai.disabled.federation.bean.business.detail.HomeCareDetailBean;
import com.juntai.disabled.federation.bean.business.detail.RecoveryDetailBean;
import com.juntai.disabled.federation.bean.business.detail.TrainRequestDetailBean;
import com.juntai.disabled.federation.bean.case_bean.CaseDesBean;
import com.juntai.disabled.federation.bean.case_bean.CaseInfoBean;
import com.juntai.disabled.federation.bean.case_bean.CaseTypeBean;
import com.juntai.disabled.federation.bean.conciliation.ConciliationInfoBean;
import com.juntai.disabled.federation.bean.conciliation.ConciliationListBean;
import com.juntai.disabled.federation.bean.conciliation.ConciliationTypesBean;
import com.juntai.disabled.federation.bean.conciliation.MediatorAllListBean;
import com.juntai.disabled.federation.bean.conciliation.UnitListBean;
import com.juntai.disabled.federation.bean.exchang_mall.GoodsInfoBean;
import com.juntai.disabled.federation.bean.exchang_mall.GoodsListBean;
import com.juntai.disabled.federation.bean.exchang_mall.HistoryGoodsListBean;
import com.juntai.disabled.federation.bean.history_track.HistoryTrackBean;
import com.juntai.disabled.federation.bean.inspection.InspectionDetailBean;
import com.juntai.disabled.federation.bean.inspection.InspectionForScanBean;
import com.juntai.disabled.federation.bean.inspection.InspectionPointInfoBean;
import com.juntai.disabled.federation.bean.inspection.InspectionRecordBean;
import com.juntai.disabled.federation.bean.key_personnel.InterviewDetailBean;
import com.juntai.disabled.federation.bean.key_personnel.InterviewListBean;
import com.juntai.disabled.federation.bean.key_personnel.KeyPersonnelInfoBean;
import com.juntai.disabled.federation.bean.message.InformDetailBean;
import com.juntai.disabled.federation.bean.message.LikeMsgListBean;
import com.juntai.disabled.federation.bean.message.UnReadCountBean;
import com.juntai.disabled.federation.bean.news.NewsDetailBean;
import com.juntai.disabled.federation.bean.news.NewsFansListBean;
import com.juntai.disabled.federation.bean.news.NewsListBean;
import com.juntai.disabled.federation.bean.news.NewsPersonalHomePageInfo;
import com.juntai.disabled.federation.bean.news.NewsUploadPhotoBean;
import com.juntai.disabled.federation.bean.site.CustomerSourceBean;
import com.juntai.disabled.federation.bean.site.EmployeeDetailBean;
import com.juntai.disabled.federation.bean.site.EmployeeListBean;
import com.juntai.disabled.federation.bean.site.SiteInspectionDetailBean;
import com.juntai.disabled.federation.bean.site.SiteTypeBean;
import com.juntai.disabled.federation.bean.site.UnitDetailBean;
import com.juntai.disabled.federation.bean.stream.CameraOnlineBean;
import com.juntai.disabled.federation.bean.stream.CaptureBean;
import com.juntai.disabled.federation.bean.stream.OpenLiveBean;
import com.juntai.disabled.federation.bean.stream.PlayUrlBean;
import com.juntai.disabled.federation.bean.stream.RecordInfoBean;
import com.juntai.disabled.federation.bean.stream.StreamCameraBean;
import com.juntai.disabled.federation.bean.stream.StreamCameraDetailBean;
import com.juntai.disabled.federation.bean.stream.VideoInfoBean;
import com.juntai.disabled.federation.bean.task.TaskDetailBean;
import com.juntai.disabled.federation.bean.task.TaskListBean;
import com.juntai.disabled.federation.bean.task.TaskSubmitedBean;
import com.juntai.disabled.federation.bean.user_equipment.EquipmentListBean;
import com.juntai.disabled.federation.bean.user_equipment.ReportTypeBean;
import com.juntai.disabled.federation.bean.weather.PoliceGriddingBean;
import com.juntai.disabled.federation.bean.weather.ResponseForcastWeather;
import com.juntai.disabled.federation.bean.weather.ResponseRealTimeWeather;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * responseBody????????????????????????(??????)?????????????????????????????????????????????????????????
 */
public interface AppServer {
    /**
     * ??????
     *
     * @param account
     * @param password
     * @param weChatId
     * @param qqId
     * @return
     */
    @POST(AppHttpPath.LOGIN)
    Observable<UserBean> login(@Query("account") String account, @Query("password") String password,
                               @Query("weChatId") String weChatId, @Query("qqId") String qqId);

    /************************************************************************????????????
     * *******************************************************************/

    /**
     * ??????????????????
     *
     * @return
     */
    @POST(AppHttpPath.SEARCH_POLICE_POSITION)
    Observable<PolicePositionBean> getPolicePosition();

    /**
     * ??????????????????
     *
     * @return
     */
    @POST(AppHttpPath.SEARCH_POLICE_BRANCH)
    Observable<PoliceBranchBean> getPoliceBranch();

    /**
     * ??????????????????
     *
     * @return
     */
    @POST(AppHttpPath.SEARCH_POLICE_GRIDDING)
    Observable<PoliceGriddingBean> getPoliceGridding(@Body RequestBody jsonBody);

    /**
     * ??????
     *
     * @return
     */
    @POST(AppHttpPath.REGIST)
    Observable<BaseResult> regist(@Body RequestBody route);

    /**
     * ??????????????????
     *
     * @return
     */
    @POST(AppHttpPath.ADD_USER_INFO)
    Observable<BaseResult> addUserInfo(@Body RequestBody jsonBody);

    /*********************************************************************????????????
     * **********************************************************************/
    /**
     * ????????????
     *
     * @param account
     * @param token
     * @return
     */
    @POST(AppHttpPath.LOGIN_OUT)
    Observable<BaseResult> loginOut(@Query("account") String account, @Query("token") String token,
                                    @Query("userId") int userId,
                                    @Query("longitude") String longitude, @Query("latitude") String latitude, @Query(
            "source") int source);

    /**
     * ??????????????????
     * <p>
     * ??????????????????
     *
     * @param account
     * @param token
     * @param userId
     * @return
     */
    @POST(AppHttpPath.USERINFO)
    Observable<UserBean> getUserData(@Query("account") String account, @Query("token") String token,
                                     @Query("userId") int userId, @Query("source") int source);

    /**
     * ????????????
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.HEAD_UPDATE)
    Observable<BaseResult> modifyHeadPortrait(@Body RequestBody jsonBody);


    /**
     * ???????????????
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.BIND_PHONE)
    Observable<BaseResult> bindPhoneNum(@Body RequestBody jsonBody);

    /**
     * ????????????????????????
     *
     * @param account
     * @param token
     * @param userId
     * @param currentPage ??????
     * @param pageSize    ???????????????
     * @return
     */
    @POST(AppHttpPath.USER_COLLECT_CAMERA)
    Observable<CollectListBean> getUserCollectCamera(@Query("account") String account, @Query("token") String token,
                                                     @Query("userId") int userId,
                                                     @Query("currentPage") int currentPage,
                                                     @Query("pageSize") int pageSize);

    /**
     * ????????????????????????
     *
     * @param account
     * @param token
     * @param userId
     * @param currentPage ??????
     * @param pageSize    ???????????????
     * @return
     */
    @POST(AppHttpPath.USER_COLLECT_NEWS)
    Observable<CollectListBean> getUserCollectNews(@Query("account") String account, @Query("token") String token,
                                                   @Query("userId") int userId,
                                                   @Query("currentPage") int currentPage,
                                                   @Query("pageSize") int pageSize);

    /**
     * ??????????????????(??????)
     *
     * @param account
     * @param token
     * @param userId
     * @param currentPage ??????
     * @param pageSize    ???????????????
     * @return
     */
    @POST(AppHttpPath.USER_SHARE_CAMERA)
    Observable<CollectListBean> getUserShareCamera(@Query("account") String account, @Query("token") String token,
                                                   @Query("userId") int userId,
                                                   @Query("currentPage") int currentPage,
                                                   @Query("pageSize") int pageSize);

    /**
     * ??????????????????(??????)
     *
     * @param account
     * @param token
     * @param userId
     * @param currentPage ??????
     * @param pageSize    ???????????????
     * @return
     */
    @POST(AppHttpPath.USER_SHARE_NEWS)
    Observable<CollectListBean> getUserShareNews(@Query("account") String account, @Query("token") String token,
                                                 @Query("userId") int userId,
                                                 @Query("currentPage") int currentPage,
                                                 @Query("pageSize") int pageSize);

    /**
     * ??????????????????(??????)
     *
     * @param account
     * @param token
     * @param userId
     * @param currentPage ??????
     * @param pageSize    ???????????????
     * @return
     */
    @POST(AppHttpPath.USER_PUBLISH_CASE)
    Observable<PublishListBean> getUserPublishCase(@Query("account") String account, @Query("token") String token,
                                                   @Query("userId") int userId,
                                                   @Query("currentPage") int currentPage,
                                                   @Query("pageSize") int pageSize);

    /**
     * ??????????????????(??????)
     *
     * @param account
     * @param token
     * @param userId
     * @param currentPage ??????
     * @param pageSize    ???????????????
     * @return
     */
    @POST(AppHttpPath.USER_PUBLISH_INSPECTION)
    Observable<PublishListBean> getUserPublishInspection(@Query("account") String account,
                                                         @Query("token") String token, @Query("userId") int userId,
                                                         @Query("currentPage") int currentPage,
                                                         @Query("pageSize") int pageSize);

    /**
     * ??????????????????(??????)
     *
     * @param account
     * @param token
     * @param userId
     * @param currentPage ??????
     * @param pageSize    ???????????????
     * @return
     */
    @POST(AppHttpPath.USER_PUBLISH_SITE)
    Observable<PublishListBean> getUserPublishSite(@Query("account") String account, @Query("token") String token,
                                                   @Query("userId") int userId, @Query("currentPage") int currentPage
            , @Query("pageSize") int pageSize);

    /**
     * ??????????????????(??????)
     *
     * @param account
     * @param token
     * @param userId
     * @param currentPage ??????
     * @param pageSize    ???????????????
     * @return
     */
    @POST(AppHttpPath.USER_PUBLISH_NEWS)
    Observable<PublishListBean> getUserPublishNews(@Query("account") String account, @Query("token") String token,
                                                   @Query("userId") int userId,
                                                   @Query("currentPage") int currentPage,
                                                   @Query("pageSize") int pageSize);

    /**
     * ????????????????????????
     *
     * @param account
     * @param token
     * @param id      ??????id
     * @return
     */
    @POST(AppHttpPath.GET_INFORMATION_DETAIL)
    Observable<InformDetailBean> getSystemDetail(@Query("account") String account, @Query("token") String token,
                                                 @Query("id") int id, @Query("userId") int userId);

    /**
     * ??????????????????
     *
     * @param account
     * @param token
     * @param logId   ??????id
     * @return
     */
    @POST(AppHttpPath.DELETE_MY_SYSTEM_MESSAGE)
    Observable<BaseResult> deleteSystemMsg(@Query("account") String account, @Query("token") String token,
                                           @Query("userId") int userId, @Query("logId") int logId);

    /**
     * ??????????????????/??????????????????
     *
     * @param account
     * @param token
     * @param userId
     * @param typeId      ??????id???1:??????????????????2:??????????????????3:??????????????????4:???????????????
     * @param currentPage ??????
     * @param pageSize    ???????????????
     * @return
     */
    @POST(AppHttpPath.USER_MESSAGE)
    Observable<LikeMsgListBean> getUserLikeMsg(@Query("account") String account, @Query("token") String token,
                                               @Query("userId") int userId, @Query("typeId") int typeId,
                                               @Query("currentPage") int currentPage, @Query("pageSize") int pageSize);

    /**
     * ????????????????????????
     *
     * @param account
     * @param token
     * @param userId
     * @param typeId      ?????????1?????????2??????,null?????????
     * @param currentPage ??????
     * @param pageSize    ???????????????
     * @return
     */
    @POST(AppHttpPath.USER_SCORE)
    Observable<UserScoreListBean> getUserScore(@Query("account") String account, @Query("token") String token,
                                               @Query("userId") int userId, @Query("typeId") String typeId,
                                               @Query("currentPage") int currentPage, @Query("pageSize") int pageSize);

    /**
     * ??????????????????
     *
     * @param account
     * @param token
     * @param userId
     * @param typeId  ??????id???1:??????????????????2:??????????????????3:??????????????????4:???????????????
     * @param id      ??????0?????????????????????
     * @return
     */
    @POST(AppHttpPath.ALL_READ_MSG)
    Observable<BaseResult> allReadMsg(@Query("account") String account, @Query("token") String token,
                                      @Query("userId") int userId, @Query("typeId") int typeId, @Query("id") int id);

    /**
     * ????????????????????????(??????)
     *
     * @param account
     * @param token
     * @param userId  ??????id???????????????????????????
     * @param isType  ?????????0???????????????1????????????????????????
     * @param shareId ?????????????????????????????????
     * @param ids     ??????????????????????????????????????????????????????
     */
    @POST(AppHttpPath.ADD_OR_DELETE_SHARE)
    Observable<BaseResult> addOrDeleteShares(@Query("account") String account, @Query("token") String token, @Query(
            "userId") String userId,
                                             @Query("isType") int isType, @Query("typeId") String typeId,
                                             @Query("shareId") String shareId, @Query("ids") List<Integer> ids);

    /**
     * ????????????????????????
     *
     * @param account
     * @param token
     * @param isType  ?????????0???????????????1????????????????????????
     * @param ids
     * @return
     */
    @POST(AppHttpPath.SHARE_TO_WCHAT)
    Observable<BaseResult> deleteCameraShare(@Query("account") String account, @Query("token") String token,
                                             @Query("isType") int isType, @Query("ids") List<Integer> ids);

    /**
     * ????????????
     *
     * @param account
     * @param token
     * @param ids
     * @return
     */
    @POST(AppHttpPath.DELETE_PUBLISH_CASE)
    Observable<BaseResult> deletePublishCase(@Query("account") String account, @Query("token") String token, @Query(
            "ids") List<Integer> ids);

    /**
     * ????????????
     *
     * @param account
     * @param token
     * @param ids
     * @return
     */
    @POST(AppHttpPath.DELETE_PUBLISH_INSPECTION)
    Observable<BaseResult> deletePublishInspection(@Query("account") String account, @Query("token") String token,
                                                   @Query("ids") List<Integer> ids);

    /**
     * ????????????
     *
     * @param account
     * @param token
     * @param ids
     * @return
     */
    @POST(AppHttpPath.DELETE_PUBLISH_SITE)
    Observable<BaseResult> deletePublishSite(@Query("account") String account, @Query("token") String token, @Query(
            "ids") List<Integer> ids);

    /**
     * ????????????
     *
     * @param account
     * @param token
     * @param ids
     * @return
     */
    @POST(AppHttpPath.DELETE_PUBLISH_NEWS)
    Observable<BaseResult> deletePublishNews(@Query("account") String account, @Query("token") String token, @Query(
            "ids") List<Integer> ids);

    /**
     * ????????????????????????
     *
     * @param account
     * @param token
     * @param userId
     * @param currentPage ??????
     * @param pageSize    ???????????????
     * @param typeId      0???????????????1???????????????2????????????,??????????????????????????????
     * @param keyWord     ????????? ????????????
     * @return
     */
    @POST(AppHttpPath.USER_TASK_LIST)
    Observable<TaskListBean> getUserTask(@Query("account") String account, @Query("token") String token, @Query(
            "userId") int userId,
                                         @Query("currentPage") int currentPage, @Query("pageSize") int pageSize,
                                         @Query("keyWord") String keyWord, @Query("typeId") String typeId);

    /**
     * ??????????????????
     *
     * @param account
     * @param token
     * @param userId
     * @param missionId    ??????id
     * @param taskPeopleId ??????????????????id
     * @return
     */
    @POST(AppHttpPath.USER_TASK_DETAIL)
    Observable<TaskDetailBean> getTaskInfo(@Query("account") String account, @Query("token") String token,
                                           @Query("userId") int userId, @Query("missionId") int missionId,
                                           @Query("taskPeopleId") int taskPeopleId);

    /**
     * ???????????????????????????
     *
     * @param account
     * @param token
     * @param reportId ??????id
     * @return
     */
    @POST(AppHttpPath.USER_TASK_SUBMITED_DETAIL)
    Observable<TaskSubmitedBean> getTaskSubmitDetail(@Query("account") String account, @Query("token") String token,
                                                     @Query("reportId") int reportId);

    /**
     * ???????????????QQ
     *
     * @param account    ?????????
     * @param source     ???????????????1????????????2????????????3????????????4???????????????
     * @param weChatId
     * @param weChatName
     * @param qqId
     * @param qqName
     * @return
     */
    @POST(AppHttpPath.BIND_QQ_WECHAT)
    Observable<BaseResult> bindQQAndWeChat(@Query("account") String account, @Query("token") String token, @Query(
            "source") int source,
                                           @Query("weChatId") String weChatId, @Query("weChatName") String weChatName,
                                           @Query("qqId") String qqId, @Query("qqName") String qqName);

    /**
     * ?????????????????????
     *
     * @return
     */
    @POST(AppHttpPath.UNREAD_COUNT)
    Observable<UnReadCountBean> getUnReadCount(@Body RequestBody requestBody);


    /**********************************************************************????????????
     * *********************************************************************/
    /**
     * ??????????????????????????????
     *
     * @return
     */
    @GET(AppHttpPath.MAP_MENU_BUTTON)
    Observable<MapMenuButton> getMapMenu();

    /**
     * ????????????????????????
     *
     * @return
     */
    @POST(AppHttpPath.MAP_CAMERA_LIST)
    Observable<StreamCameraBean> requestCamera(@Body RequestBody requestBody);

    /**
     * ????????????
     *
     * @return
     */
    @POST(AppHttpPath.MAP_CASE_LIST)
    Observable<CaseDesBean> requestCase(@Body RequestBody requestBody);

    /**
     * ????????????
     *
     * @return
     */
    @POST(AppHttpPath.MAP_CAR_LIST)
    Observable<PoliceCarBean> requestCar(@Body RequestBody requestBody);

    /**
     * ????????????
     *
     * @return
     */
    @POST(AppHttpPath.MAP_POLICE_LIST)
    Observable<ResponsePeople> requestPeople(@Body RequestBody requestBody);

    /**
     * ????????????
     *
     * @return
     */
    @POST(AppHttpPath.MAP_PATROL_LIST)
    Observable<ResponseInspection> requestInspection();

    /**
     * ????????????
     *
     * @return
     */
    @POST(AppHttpPath.MAP_SITE_LIST)
    Observable<ResponseSiteBean> requestSiteList(@Body RequestBody requestBody);

    /**
     * ??????????????????
     *
     * @return
     */
    @POST(AppHttpPath.HEATH_ORGANIZE_DETAIL)
    Observable<HealthOrganizeDetailBean> getHealthOrganizeDetail(@Body RequestBody requestBody);

    /**
     * ????????????
     *
     * @return
     */
    @POST(AppHttpPath.MAP_NEWS_LIST)
    Observable<ResponseNews> requestNews(@Body RequestBody requestBody);

    /**
     * ????????????
     *
     * @return
     */
    @POST(AppHttpPath.MAP_SERVER_PEOPLE_LIST)
    Observable<ServerPeopleBean> requestServer(@Body RequestBody requestBody);

    /**
     * ??????????????????
     *
     * @return
     */
    @POST(AppHttpPath.POLICE_DETAIL)
    Observable<PoliceDetailBean> requestPeopleDetail(@Body RequestBody body);

    /**
     * ????????????????????????
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.UPLOAD_CAMERA_COVER)
    Observable<BaseResult> uploadCameraCover(@Body RequestBody jsonBody);

    /**
     * ????????????
     */
    @POST(AppHttpPath.USER_AUTH)
    Observable<BaseResult> userAuth(@Body RequestBody jsonBody);

    /**
     * ???????????? ?????????
     */
    @POST(AppHttpPath.POLICE_PICKER)
    Observable<PolicePickerBean> getPolicePickerInfo(@Query("account") String account, @Query("token") String token,
                                                     @Query("departmentId") int departmentId);


    //??????
    @POST(AppHttpPath.SEARCH)
    Observable<SearchBean> search(@Body RequestBody requestBody);

    //??????
    @POST(AppHttpPath.SEARCH_GET_MORE)
    Observable<SearchResultBean> search_get_more(@Body RequestBody requestBody);

    /**
     * ????????????
     *
     * @return
     */
    @POST(AppHttpPath.BANNER_NEWS)
    Observable<BannerNewsBean> getBannerNews();

    /**
     * ??????im????????????
     *
     * @return
     */
    @POST(AppHttpPath.IM_USERS)
    Observable<IMUsersBean> getIMUsers(@Query("token") String token, @Query("account") String account);

    //????????????
    @POST(AppHttpPath.CAR_HISTORY)
    Observable<ResponseCarHistory> getPoliceCarTrack(@Body RequestBody body);

    //????????????
    @POST(AppHttpPath.PEOPLE_HISTORY)
    Observable<HistoryTrackBean> getPoliceTrack(@Body RequestBody body);

    /**
     * ????????????
     */
    @POST(AppHttpPath.USER_HISTORY_UPLOAD)
    Observable<BaseResult> uploadHistory(@Query("account") String account, @Query("token") String token, @Query(
            "userId") int userId,
                                         @Query("source") int source, @Query("json") String json);

        /*====================================================    ??????
        ==============================================================*/


    //????????????
    @GET(AppHttpPath.REALTIME_WEATHER)
    Observable<ResponseRealTimeWeather> getWeatherRealtime(@Query("longitude") String longitude,
                                                           @Query("latitude") String latitude);

    //????????????
    @GET(AppHttpPath.FORCAST_WEATHER)
    Observable<ResponseForcastWeather> getForcast(@Query("longitude") String longitude,
                                                  @Query("latitude") String latitude);

    /**
     * ???????????????
     *
     * @return
     */
    @POST(AppHttpPath.PROVINCE)
    Observable<CityBean> getProvince();

    /**
     * ??????????????????
     *
     * @param cityNum
     * @return
     */
    @POST(AppHttpPath.CITY)
    Observable<CityBean> getCity(@Query("cityNum") String cityNum);

    /**
     * ??????????????????
     *
     * @param cityNum
     * @return
     */
    @POST(AppHttpPath.AREA)
    Observable<CityBean> getArea(@Query("cityNum") String cityNum);

    /**
     * ??????????????????
     *
     * @return
     */
    @POST(AppHttpPath.STREET)
    Observable<CityBean> getStreet(@Query("cityNum") String townNum);

    /*****************************************************************????????????
     * **************************************************************************/

    /**
     * ????????????
     *
     * @return
     */
    @POST(AppHttpPath.SET_PWD)
    Observable<BaseResult> setPwd(@Query("account") String account, @Query("password") String password);

    /**
     * ????????????
     *
     * @return
     */
    @POST(AppHttpPath.MODIFY_PWD)
    Observable<BaseResult> modifyPwd(@Body RequestBody requestBody);

    /**
     * ???????????????????????????
     *
     * @param account
     * @param token
     * @param userId
     * @param newAccount  ????????????
     * @param password    ?????????
     * @param oldPassword ?????????
     * @return
     */
    @POST(AppHttpPath.UPDATE_PHONE)
    Observable<BaseResult> updatePhone(@Query("account") String account, @Query("token") String token,
                                       @Query("phoneNumber") String phoneNumber, @Query("userId") int userId, @Query(
            "newAccount") String newAccount,
                                       @Query("password") String password, @Query("oldPassword") String oldPassword);


        /*====================================================    ????????????????????????
        ==============================================================*/

    /**
     * ????????????
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.PUBLISH_CASE)
    Observable<BaseResult> publishCase(@Body RequestBody jsonBody);

    /**
     * ???????????????????????????
     *
     * @return
     */
    @POST(AppHttpPath.CASE_TYPE)
    Observable<CaseTypeBean> getCaseType(@Query("account") String account, @Query("token") String token,
                                         @Query("id") int id);

    /**
     * ???????????????????????????--???????????????
     *
     * @param token
     * @param account
     * @param id      ??????id
     * @return
     */
    @POST(AppHttpPath.GET_CASE_INFO)
    Observable<CaseInfoBean> getCaseInfo(@Query("token") String token, @Query("account") String account,
                                         @Query("id") int id);

    /**
     * ????????????
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.PUBLISH_INSPECTION)
    Observable<BaseResult> publishInspection(@Body RequestBody jsonBody);

    /**
     * ????????????
     *
     * @param account
     * @param token
     * @param patrolId ??????id
     * @return
     */
    @POST(AppHttpPath.INSPECTION_DETAIL)
    Observable<InspectionDetailBean> getInspectionDetail(@Query("account") String account,
                                                         @Query("token") String token, @Query("id") int patrolId);

    /**
     * /**
     * ???????????????
     *
     * @param account
     * @param token
     * @param id      ??????id
     * @return
     */
    @POST(AppHttpPath.INSPECTION_POINT_DETAIL)
    Observable<InspectionPointInfoBean> getInspectionPointDetail(@Query("account") String account,
                                                                 @Query("token") String token, @Query("id") int id);

    /**
     * /**
     * ????????????
     *
     * @param account
     * @param token
     * @param patrolId ??????id
     * @return
     */
    @POST(AppHttpPath.INSPECTION_RECORD)
    Observable<InspectionRecordBean> getInspectionRecord(@Query("account") String account,
                                                         @Query("token") String token, @Query("patrolId") int patrolId
            , @Query("currentPage") int currentPage, @Query("pageSize") int pageSize);

    /**
     * ??????????????????
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.PUBLISH_TASK_REPORT)
    Observable<BaseResult> publishTaskReport(@Body RequestBody jsonBody);

    /**
     * ??????id?????????????????????????????????
     */
    @POST(AppHttpPath.GET_INSPECTION_INFO_FOR_SACN)
    Observable<InspectionForScanBean> getInspectionInfo(@Query("codeNumber") String codeNumber);

        /*====================================================    ????????????
        ==============================================================*/

    /**
     * ????????????
     *
     * @return
     */
    @POST(AppHttpPath.BUSINESS_LIST)
    Observable<BusinessListBean> businessList(@Query("account") String account, @Query("token") String token, @Query(
            "keyWord") String keyWord, @Query("pageSize") int pageSize, @Query("currentPage") int currentPage);


    /**
     * ?????????????????????
     *
     * @return
     */
    @POST(AppHttpPath.BUSINESS_DATA_NEEDED)
    Observable<BusinessNeedInfoBean> businessDataNeeded(@Query("account") String account,
                                                        @Query("token") String token,
                                                        @Query("declareId") int declareId);

    /**
     * ????????????
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.BUSINESS_ADD)
    Observable<BaseResult> creatBusiness(@Body RequestBody jsonBody);

    /**
     * ??????????????????
     *
     * @return
     */
    @POST(AppHttpPath.BUSINESS_PROGRESS)
    Observable<MyBusinessBean> businessProgress(@Body RequestBody jsonBody);

    /**
     * @return
     */
    @POST(AppHttpPath.DELETE_MY_BUSINESS)
    Observable<BaseResult> deleteUserBusiness(@Query("account") String account,
                                              @Query("token") String token, @Query("ids") List<Integer> ids);

    /**
     * ????????????????????????????????????
     *
     * @return
     */
    @POST(AppHttpPath.BUSINESS_DETAIL)
    Observable<MyBusinessDetailBean> businessDetail(@Body RequestBody jsonBody);


        /*====================================================    ????????????
        ==============================================================*/

    /**
     * ????????????
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.CONCILIATION_ADD)
    Observable<BaseResult> publishConciliation(@Body RequestBody jsonBody);

    /**
     * ??????????????????
     *
     * @param account
     * @param token
     * @param userId
     * @param typeId      ??????id ???1??????????????????2??????????????????3??????????????????
     * @param pageSize    ??????10
     * @param currentPage ??????1
     * @return
     */
    @POST(AppHttpPath.CONCILIATION_LIST)
    Observable<ConciliationListBean> getMyConciliationList(@Query("account") String account,
                                                           @Query("token") String token,
                                                           @Query("userId") int userId, @Query("typeId") int typeId,
                                                           @Query("pageSize") int pageSize,
                                                           @Query("currentPage") int currentPage);

    /**
     * ??????????????????
     *
     * @return
     */
    @POST(AppHttpPath.CONCILIATION_INFO)
    Observable<ConciliationInfoBean> getConciliationInfo(@Query("account") String account,
                                                         @Query("token") String token, @Query("caseId") int caseId);

    /**
     * ???????????????????????????
     *
     * @param account ?????????
     * @param token   ??????token
     * @param unitId  ??????id
     * @return
     */
    @POST(AppHttpPath.GET_ALL_MEDIATOR_LIST)
    Observable<MediatorAllListBean> getAllMediatorList(@Query("account") String account, @Query("token") String token
            , @Query("cityNumber") String cityNumber, @Query("unitId") int unitId);

    /**
     * ??????????????????
     *
     * @param account
     * @param token
     * @param cityNumber ??????id
     * @return
     */
    @POST(AppHttpPath.GET_UNIT_LIST)
    Observable<UnitListBean> getUnitList(@Query("account") String account, @Query("token") String token, @Query(
            "cityNumber") String cityNumber);

    /**
     * ????????????????????????
     *
     * @param account
     * @param token
     * @param typeId  ??????id???0???????????????????????????
     * @return
     */
    @POST(AppHttpPath.GET_CONCILIATION_TYPE_LIST)
    Observable<ConciliationTypesBean> getConciliationTypes(@Query("account") String account,
                                                           @Query("token") String token, @Query("typeId") int typeId);

    /**
     * ??????????????????????????????
     *
     * @param account
     * @param token
     * @param caseNumber
     * @return
     */
    @POST(AppHttpPath.SELECT_CASE_NUMBER_IS_CORRECT)
    Observable<BaseResult> selectCaseNumberIsCorrect(@Query("account") String account, @Query("token") String token,
                                                     @Query("caseNumber") String caseNumber);

        /*====================================================    ?????????
        ==============================================================*/

    /**
     * ???????????????????????????
     *
     * @return
     */
    @POST(AppHttpPath.SHARE_TO_WCHAT)
    Observable<BaseResult> shareToWechat(@Body RequestBody requestBody);

    /**
     * ????????????????????????????????????????????????
     *
     * @return
     */
    @POST(AppHttpPath.STREAM_CAMERAS_FROM_VCR)
    Observable<StreamCameraBean> getAllStreamCamerasFromVCR(@Body RequestBody requestBody);

    /**
     * ???????????????
     *
     * @return
     */
    @POST(AppHttpPath.STREAM_CAMERAS_DETAIL)
    Observable<StreamCameraDetailBean> getStreamCameraDetail(@Body RequestBody requestBody);

    /**
     * ???????????????
     *
     * @return
     */
    @POST(AppHttpPath.UPLOAD_STREAM_CAMERAS_THUMB)
    Observable<BaseResult> uploadStreamCameraThumbPic(@Body RequestBody requestBody);

    /**
     * ???????????????
     *
     * @return
     */
    @POST(AppHttpPath.STREAM_OPE_ADDR)
    Observable<PlayUrlBean> openStream(@Body RequestBody requestBody);

    /**
     * ??????id   ???????????????
     *
     * @param sessionid
     * @return
     */
    @GET(AppHttpPath.BASE_CAMERA_URL + "/vss/video_keepalive/{sessionid}")
    Observable<OpenLiveBean> keepAlive(@Path("sessionid") String sessionid);

    /**
     * ??????
     *
     * @param channelid
     * @return
     */
    @GET(AppHttpPath.BASE_CAMERA_URL + "/vss/get_image/{channelid}/{type}")
    Observable<CaptureBean> capturePic(@Path("channelid") String channelid, @Path("type") String type);


    /**
     * ????????????
     *
     * @param channelid
     * @return
     */
    @GET(AppHttpPath.BASE_CAMERA_URL + "/vss/history_search/{begintime}/{endtime}/{channelid}")
    Observable<VideoInfoBean> searchVideos(@Path("begintime") String begintime, @Path("endtime") String endtime,
                                           @Path("channelid") String channelid);


    /**
     * ???????????? ??????rtmp???
     *
     * @return
     */
    @GET(AppHttpPath.BASE_CAMERA_URL + "/vss/playback/start?")
    Observable<RecordInfoBean> getVideosUrl(@QueryMap Map<String, String> options);


    /**
     * ????????????
     *
     * @param ptztype
     * @param ptzparam
     * @param channelid
     * @return
     */
    @GET(AppHttpPath.BASE_CAMERA_URL + "/vss/ptz/{ptztype}/{ptzparam}/{channelid}")
    Observable<BaseStreamBean> operateYunTai(@Path("ptztype") String ptztype, @Path("ptzparam") int ptzparam,
                                             @Path("channelid") String channelid);


    /**
     * ????????????
     * "sessionid":    (?????????) ???????????????sessionid??????
     * "vodctrltype":  (?????????) "play","pause","stop","jump"
     * "vodctrlparam": (?????????)  0(pause,stop) / 0.125,0.25,0.5,1,2,4,8,16(play) (??????:0-32)/ ??????????????????????????????(jump)
     *
     * @return
     */
    @GET(AppHttpPath.BASE_CAMERA_URL + "/vss/his_stream_ctrl/{sessionid}/{vodctrltype}/{vodctrlparam}")
    Observable<BaseStreamBean> operateRecordVideo(@Path("sessionid") String sessionid,
                                                  @Path("vodctrltype") String vodctrltype,
                                                  @Path("vodctrlparam") String vodctrlparam);

    /**
     * ???????????????
     */
    @GET(AppHttpPath.GET_ONLINE_AMOUNT)
    Observable<CameraOnlineBean> getOnlineAmount(@Query("q") String q);

    /**
     * ?????????
     */
    @GET(AppHttpPath.STOP_STREAM)
    Observable<BaseStreamBean> stopStream(@Query("sessionid") String q);


    /**
     * ????????????id??????????????????
     *
     * @param commentedId ??????id
     * @param pageSize
     * @param currentPage
     * @return
     */
    @POST(AppHttpPath.GET_ALL_COMMENT_CAMERA)
    Observable<CommentListBean> getAllCommentCamera(@Query("account") String account, @Query("token") String token,
                                                    @Query("userId") int userId,
                                                    @Query("commentedId") int commentedId,
                                                    @Query("pageSize") int pageSize,
                                                    @Query("currentPage") int currentPage);

    /**
     * ???????????????id?????????????????????
     *
     * @param commentedId
     * @return
     */
    @POST(AppHttpPath.GET_CHILD_COMMENT_CAMERA)
    Observable<CommentListBean> getChildCommentCamera(@Query("account") String account, @Query("token") String token,
                                                      @Query("commentedId") int commentedId,
                                                      @Query("unreadId") int unreadId,
                                                      @Query("pageSize") int pageSize,
                                                      @Query("currentPage") int currentPage);

    /**
     * ????????????
     *
     * @param body
     * @return
     */
    @POST(AppHttpPath.ADD_COMMENT_CAMERA)
    Observable<BaseResult> addCommentCamera(@Body RequestBody body);

    /**
     * ?????????????????????(?????????)
     *
     * @param id      ????????????????????????likeid
     * @param account
     * @param token
     * @param userId  ??????id???????????????????????????
     * @param isType  ?????????0???????????????1????????????????????????
     * @param typeId  ??????id ???????????????????????????
     * @param likeId  ?????????????????????????????????????????????????????????????????????
     * @return
     */
    @POST(AppHttpPath.ADD_OR_CANCLE_LIKE_CAMERA)
    Observable<BaseDataBean> addOrCancleLikeCamera(@Query("id") int id, @Query("account") String account, @Query(
            "token") String token,
                                                   @Query("userId") int userId, @Query("isType") int isType,
                                                   @Query("typeId") int typeId, @Query("likeId") int likeId);

    /**
     * ???????????????????????? (?????????)
     *
     * @param id        ????????????????????????collectId
     * @param account
     * @param token
     * @param userId    ??????id???????????????????????????
     * @param isType    ?????????0???????????????1????????????????????????
     * @param typeId    ??????id???1???????????????2???????????????
     * @param collectId ??????????????????????????????
     * @param ids       ??????????????????????????????????????????????????????
     * @return
     */
    @POST(AppHttpPath.ADD_OR_DELETE_COLLECT_CAMERA)
    Observable<BaseDataBean> addOrDeleteCollectsCamera(@Query("id") int id, @Query("account") String account, @Query(
            "token") String token,
                                                       @Query("userId") int userId, @Query("isType") int isType,
                                                       @Query("typeId") int typeId,
                                                       @Query("collectId") int collectId,
                                                       @Query("ids") List<Integer> ids);

    /*==============================================  ????????????????????????  =============================================*/


    //    @POST(AppHttpPath.ADD_PRE_POSITION)
    //    Observable<BaseResult> addPrePosition(@Body RequestBody body);
    //
    //    /**
    //     * ???????????????
    //     *
    //     * @param body
    //     * @return
    //     */
    //    @POST(AppHttpPath.DEL_PRE_POSITION)
    //    Observable<BaseResult> delPrePosition(@Body RequestBody body);
    //
    //    /**
    //     * ???????????????
    //     *
    //     * @param body
    //     * @return
    //     */
    //    @POST(AppHttpPath.GET_PRE_POSITIONS)
    //    Observable<PreSetBean> getPrePositions(@Body RequestBody body);

        /*====================================================    ??????????????????
        ==============================================================*/

    /**
     * ??????????????????
     *
     * @param account
     * @param token
     * @return
     */
    @POST(AppHttpPath.MALL_ALL_GOODS_LIST)
    Observable<GoodsListBean> getGoodsList(@Query("account") String account, @Query("token") String token);

    /**
     * ??????????????????
     *
     * @param account
     * @param token
     * @param commodityId
     * @return
     */
    @POST(AppHttpPath.MALL_GOODS_DEATIL)
    Observable<GoodsInfoBean> getGoodsDetail(@Query("account") String account, @Query("token") String token, @Query(
            "commodityId") int commodityId);

    /**
     * ??????????????????
     *
     * @param account
     * @param token
     * @param userId
     * @return
     */
    @POST(AppHttpPath.MALL_HISTORY_LIST)
    Observable<HistoryGoodsListBean> getHistoryGoodsList(@Query("account") String account,
                                                         @Query("token") String token, @Query("userId") int userId);

    /**
     * ????????????
     *
     * @param account
     * @param token
     * @param userId
     * @param price         ??????????????????
     * @param commodityId   ??????id
     * @param commodityName ????????????
     * @return
     */
    @POST(AppHttpPath.MALL_EXCHANGE_GOODS)
    Observable<BaseResult> exchangeGoods(@Query("account") String account, @Query("token") String token, @Query(
            "userId") int userId,
                                         @Query("price") int price, @Query("commodityId") int commodityId, @Query(
            "commodityName") String commodityName);

        /*====================================================    ??????????????????
        ==============================================================*/

    /**
     * ??????????????????
     *
     * @return
     */
    @POST(AppHttpPath.KEY_PERSONNEL_DETAIL)
    Observable<KeyPersonnelInfoBean> getKeyPersonnelInfo(@Query("account") String account,
                                                         @Query("token") String token, @Query("id") int id);

    /**
     * ????????????
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.INTERVIEW_ADD)
    Observable<BaseResult> addInterview(@Body RequestBody jsonBody);

    /**
     * ??????????????????
     *
     * @param account
     * @param token
     * @param id      ????????????id
     * @param page    ??????
     * @param rows    ??????
     * @return
     */
    @POST(AppHttpPath.INTERVIEW_LIST)
    Observable<InterviewListBean> getInterviewList(@Query("account") String account, @Query("token") String token,
                                                   @Query("id") int id,
                                                   @Query("currentPage") int page, @Query("pageSize") int rows);

    /**
     * ??????????????????
     *
     * @return
     */
    @POST(AppHttpPath.INTERVIEW_DETAIL)
    Observable<InterviewDetailBean> getInterviewDetail(@Query("account") String account, @Query("token") String token
            , @Query("id") int id);

        /*====================================================    ??????????????????
        ==============================================================*/

    /**
     * ????????????
     *
     * @return
     */
    @POST(AppHttpPath.UNIT_DETAIL)
    Observable<UnitDetailBean> getUnitDetail(@Body RequestBody requestBody);

    /**
     * ??????????????????
     *
     * @param account
     * @param token
     * @param unitId      ??????id
     * @param pageSize    ??????
     * @param currentPage ??????
     * @return
     */
    @POST(AppHttpPath.GET_EMPLOYEE_LIST)
    Observable<EmployeeListBean> getEmployeeList(@Query("account") String account, @Query("token") String token,
                                                 @Query("unitId") int unitId,
                                                 @Query("pageSize") int pageSize,
                                                 @Query("currentPage") int currentPage);

    /**
     * ??????????????????
     *
     * @return
     */
    @POST(AppHttpPath.GET_EMPLOYEE_DETAIL)
    Observable<EmployeeDetailBean> getEmployeeDetail(@Query("account") String account, @Query("token") String token,
                                                     @Query("personnelId") int personnelId);

    /**
     * ??????????????????
     *
     * @param account
     * @param token
     * @param unitId      ??????id
     * @param pageSize    ??????
     * @param currentPage ??????
     * @return
     */
    @POST(AppHttpPath.GET_SITE_INSPECTION_LIST)
    Observable<EmployeeListBean> getSiteInspectionList(@Query("account") String account, @Query("token") String token
            , @Query("unitId") int unitId,
                                                       @Query("pageSize") int pageSize,
                                                       @Query("currentPage") int currentPage);

    /**
     * ??????????????????
     *
     * @return
     */
    @POST(AppHttpPath.GET_SITE_INSPECTION_DETAIL)
    Observable<SiteInspectionDetailBean> getSiteInspectionDetail(@Query("account") String account,
                                                                 @Query("token") String token,
                                                                 @Query("recordId") int recordId);

    /**
     * ??????????????????
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.ADD_EMPLOYEE)
    Observable<BaseResult> addEmployee(@Body RequestBody jsonBody);

    /**
     * ??????????????????
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.ADD_SITE_INSPECTION)
    Observable<BaseResult> addSiteInspection(@Body RequestBody jsonBody);

    /**
     * ????????????????????????
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.GET_SITE_TYPE_LIST)
    Observable<SiteTypeBean> getSiteTypes(@Body RequestBody jsonBody);

    /**
     * ????????????????????????
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.GET_CUSTOMER_SOURCE_LIST)
    Observable<CustomerSourceBean> getCustomerSources(@Body RequestBody jsonBody);

    /**
     * ????????????
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.ADD_SITE_MANAGER)
    Observable<BaseResult> addSiteManager(@Body RequestBody jsonBody);

    /**
     * ????????????
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.UPDATE_SITE_MANAGER)
    Observable<BaseResult> updateSiteManager(@Body RequestBody jsonBody);

        /*====================================================    ????????????
        ==============================================================*/

    /**
     * ??????????????????
     *
     * @param pageSize
     * @param currentPage
     * @return
     */
    @POST(AppHttpPath.GET_NEWS_LIST)
    Observable<NewsListBean> getNewsList(@Query("pageSize") int pageSize, @Query("currentPage") int currentPage);

    /**
     * ????????????
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.NEWS_PUBLISH)
    Observable<BaseResult> publishNews(@Body RequestBody jsonBody);

    /**
     * ????????????
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.UPDATE_NEWS)
    Observable<BaseResult> updateNews(@Body RequestBody jsonBody);


    /**
     * ??????????????????
     *
     * @return
     */
    @POST(AppHttpPath.NEWS_UPLOAD_PHOTO)
    Observable<NewsUploadPhotoBean> uploadNewsPhoto(@Body RequestBody jsonBody);

    /**
     * ??????????????????
     *
     * @return
     */
    @POST(AppHttpPath.NEWS_DELETE_PHOTO)
    Observable<BaseResult> deleteNewsPhoto(@Query("account") String account, @Query("token") String token, @Query(
            "informationId") String informationId);

    /**
     * ????????????
     *
     * @param keyWord
     * @param pageSize
     * @param currentPage
     * @return
     */
    @POST(AppHttpPath.NEWS_SEARCH_LIST)
    Observable<NewsListBean> searchNewsList(@Query("keyWord") String keyWord, @Query("pageSize") int pageSize,
                                            @Query("currentPage") int currentPage);

    /**
     * ??????????????????
     *
     * @param userId
     * @param infoId
     * @return
     */
    @POST(AppHttpPath.GET_NEWS_INFO)
    Observable<NewsDetailBean> getNewsInfo(@Query("userId") int userId, @Query("infoId") int infoId);

    /**
     * ????????????????????????
     *
     * @param userId ???????????????id
     * @param fId    ????????????id
     * @return
     */
    @POST(AppHttpPath.NEWS_PERSONAL_HOMEPAGE_INFO)
    Observable<NewsPersonalHomePageInfo> getNewsAuthorInfo(@Query("userId") int userId, @Query("fId") int fId);

    /**
     * ??????????????????????????????
     */
    @POST(AppHttpPath.NEWS_PERSONAL_HOMEPAGE_NEWS_LIST)
    Observable<NewsListBean> getNewsListByAuthorId(@Query("userId") int userId, @Query("typeId") int typeId,
                                                   @Query("currentPage") int currentPage,
                                                   @Query("pageSize") int pageSize);

    /**
     * ????????????????????????
     *
     * @param account
     * @param token
     * @param userId      ????????????id
     * @param followId    ?????????id
     * @param currentPage
     * @param pageSize
     * @return
     */
    @POST(AppHttpPath.NEWS_FANS_LIST)
    Observable<NewsFansListBean> getFansList(@Query("account") String account, @Query("token") String token,
                                             @Query("userId") int userId, @Query("followId") int followId,
                                             @Query("currentPage") int currentPage, @Query("pageSize") int pageSize);

    /**
     * ????????????????????????
     *
     * @param account
     * @param token
     * @param userId      ????????????id
     * @param fansId      ?????????id
     * @param currentPage
     * @param pageSize
     * @return
     */
    @POST(AppHttpPath.NEWS_FOLLOW_LIST)
    Observable<NewsFansListBean> getFollowList(@Query("account") String account, @Query("token") String token,
                                               @Query("userId") int userId, @Query("fansId") int fansId,
                                               @Query("currentPage") int currentPage, @Query("pageSize") int pageSize);

    /**
     * ?????????????????????
     *
     * @param account
     * @param token
     * @param fansId
     * @param typeId   1 :?????????2:????????????
     * @param followId
     * @return
     */
    @POST(AppHttpPath.NEWS_ADD_FOLLOW_OR_DELETE)
    Observable<BaseResult> addFollowOrDelete(@Query("account") String account, @Query("token") String token,
                                             @Query("fansId") int fansId, @Query("typeId") int typeId, @Query(
            "followId") int followId);

    /**
     * ????????????id??????????????????
     *
     * @param commentedId ????????????id
     * @param pageSize
     * @param currentPage
     * @return
     */
    @POST(AppHttpPath.GET_ALL_COMMENT_NEWS)
    Observable<CommentListBean> getAllCommentNews(@Query("account") String account, @Query("token") String token,
                                                  @Query("userId") int userId, @Query("commentedId") int commentedId,
                                                  @Query("pageSize") int pageSize,
                                                  @Query("currentPage") int currentPage);

    /**
     * ???????????????id?????????????????????
     *
     * @param commentedId
     * @return
     */
    @POST(AppHttpPath.GET_CHILD_COMMENT_NEWS)
    Observable<CommentListBean> getChildCommentNews(@Query("account") String account, @Query("token") String token,
                                                    @Query("commentedId") int commentedId,
                                                    @Query("unreadId") int unreadId,
                                                    @Query("pageSize") int pageSize,
                                                    @Query("currentPage") int currentPage);

    /**
     * ????????????
     *
     * @param body
     * @return
     */
    @POST(AppHttpPath.ADD_COMMENT_NEWS)
    Observable<BaseResult> addCommentNews(@Body RequestBody body);

    /**
     * ?????????????????????(??????)
     *
     * @param id      ????????????????????????likeid
     * @param account
     * @param token
     * @param userId  ??????id???????????????????????????
     * @param isType  ?????????0???????????????1????????????????????????
     * @param typeId  ??????id ???????????????????????????
     * @param likeId  ?????????????????????????????????????????????????????????????????????
     * @return
     */
    @POST(AppHttpPath.ADD_OR_CANCLE_LIKE_NEWS)
    Observable<BaseDataBean> addOrCancleLikeNews(@Query("id") int id, @Query("account") String account, @Query("token"
    ) String token,
                                                 @Query("userId") int userId, @Query("isType") int isType,
                                                 @Query("typeId") int typeId, @Query("likeId") int likeId);

    /**
     * ???????????????????????? (??????)
     *
     * @param id        ????????????????????????collectId
     * @param account
     * @param token
     * @param userId    ??????id???????????????????????????
     * @param isType    ?????????0???????????????1????????????????????????
     * @param typeId    ??????id ???????????????????????????
     * @param collectId ??????????????????????????????
     * @param ids       ??????????????????????????????????????????????????????
     * @return
     */
    @POST(AppHttpPath.ADD_OR_DELETE_COLLECT_NEWS)
    Observable<BaseDataBean> addOrDeleteCollectsNews(@Query("id") int id, @Query("account") String account, @Query(
            "token") String token,
                                                     @Query("userId") int userId, @Query("isType") int isType,
                                                     @Query("typeId") int typeId,
                                                     @Query("collectId") int collectId,
                                                     @Query("ids") List<Integer> ids);

        /*====================================================    ????????????
        ==============================================================*/

    /**
     * ????????????????????????
     *
     * @return
     */
    @POST(AppHttpPath.GET_USER_EQUIPMENT_LIST)
    Observable<EquipmentListBean> getUserEquipmentList(@Query("account") String account, @Query("token") String token
            , @Query("unreadId") int unreadId,
                                                       @Query("currentPage") int currentPage,
                                                       @Query("pageSize") int pageSize);


    /**
     * ??????????????????
     *
     * @return
     */
    @POST(AppHttpPath.GET_REPORT_TYPES)
    Observable<ReportTypeBean> getReportTypes(@Body RequestBody jsonBody);

    /**
     * ??????????????????
     *
     * @return
     */
    @POST(AppHttpPath.REPORT)
    Observable<BaseResult> report(@Body RequestBody jsonBody);




    /*==============================================  ?????????????????? =============================================*/

    /**
     * ???????????????????????????
     *
     * @return
     */
    @GET(AppHttpPath.GET_ALL_BUSINESSES)
    Observable<AllBusinessBean> getAllBusinesses();

    /**
     * @return
     */
    @GET(AppHttpPath.GET_CHILD_BUSINESSES)
    Observable<ChildBusinessesBean> getChildBusinesses(@Query("matterId") int matterId);

    /**
     * @return
     */
    @GET(AppHttpPath.GET_DISABLED_TYPE)
    Observable<BusinessPropertyBean> getDisabledCategory();

    /**
     * @return
     */
    @GET(AppHttpPath.GET_DISABLED_LEVEL)
    Observable<BusinessPropertyBean> getDisabledLevel();

    /**
     * @return
     */
    @GET(AppHttpPath.GET_DISABLED_EDUCATION)
    Observable<BusinessPropertyBean> getDisabledEducation();

    /**
     * @return
     */
    @GET(AppHttpPath.GET_DISABLED_NATION)
    Observable<BusinessPropertyBean> getDisabledNation();

    /**
     * @return
     */
    @GET(AppHttpPath.GET_DISABLED_AIDS)
    Observable<BusinessPropertyBean> getDisabledAIDS(@Query("categoryId") int categoryId);

    /**
     * @return
     */
    @GET(AppHttpPath.GET_DISABLED_BARRIER)
    Observable<BusinessPropertyBean> getDisabledBarrier();

    /**
     * @return
     */
    @POST(AppHttpPath.HANDLER_DISABLED_CARD)
    Observable<BaseResult> addDisabilityCertificate(@Body RequestBody requestBody);

    /**
     * @return
     */
    @POST(AppHttpPath.DISABLED_CARD_RENEWAL)
    Observable<BaseResult> addCertificatesExchange(@Body RequestBody requestBody);

    /**
     * @return
     */
    @POST(AppHttpPath.DISABLED_CARD_CHANGE_LEVEL)
    Observable<BaseResult> addCertificatesChange(@Body RequestBody requestBody);

    /**
     * @return
     */
    @POST(AppHttpPath.DISABLED_CARD_REISSUE)
    Observable<BaseResult> addCertificatesReissue(@Body RequestBody requestBody);

    /**
     * @return
     */
    @POST(AppHttpPath.DISABLED_CARD_MOVE_IN)
    Observable<BaseResult> addCertificatesMovein(@Body RequestBody requestBody);

    /**
     * @return
     */
    @POST(AppHttpPath.DISABLED_CARD_MOVE_OUT)
    Observable<BaseResult> addCertificatesMoveout(@Body RequestBody requestBody);

    /**
     * @return
     */
    @POST(AppHttpPath.DISABLED_CARD_LOGOUT)
    Observable<BaseResult> addCertificatesCancel(@Body RequestBody requestBody);

    /**
     * @return
     */
    @POST(AppHttpPath.DISABLED_EMPLOYMENT_REGIST)
    Observable<BaseResult> addDisabledObtainEmployment(@Body RequestBody requestBody);

    /**
     * @return
     */
    @POST(AppHttpPath.CEREBRAL_PALSY_RECOVERY)
    Observable<BaseResult> addDisabledChildrenCerebralPalsy(@Body RequestBody requestBody);

    /**
     * @return
     */
    @POST(AppHttpPath.DEAF_CHILD_RECOVERY)
    Observable<BaseResult> addDisabledChildrenDeaf(@Body RequestBody requestBody);

    /**
     * @return
     */
    @POST(AppHttpPath.LONELY_CHILD_RECOVERY)
    Observable<BaseResult> addDisabledChildrenAutism(@Body RequestBody requestBody);

    /**
     * @return
     */
    @POST(AppHttpPath.IQ_CHILD_RECOVERY)
    Observable<BaseResult> addDisabledChildrenIntellectual(@Body RequestBody requestBody);

    /**
     * @return
     */
    @POST(AppHttpPath.DISABLED_CHILD_BURSARY)
    Observable<BaseResult> addDisabledStudentGrant(@Body RequestBody requestBody);

    /**
     * @return
     */
    @POST(AppHttpPath.DISABLED_FAMILY_CHILD_BURSARY)
    Observable<BaseResult> addDisabledStudentFamilyGrant(@Body RequestBody requestBody);

    /**
     * @return
     */
    @POST(AppHttpPath.REQUEST_AIDS)
    Observable<BaseResult> addAIDS(@Body RequestBody requestBody);

    /**
     * @return
     */
    @GET(AppHttpPath.REQUEST_TOOL_INFO)
    Observable<ToolInfoBean> getDisabledAIDSInfo(@Query("aidsId") int aidsId);


    /**
     * @return
     */
    @POST(AppHttpPath.REQUEST_TRAIN)
    Observable<BaseResult> addTrain(@Body RequestBody requestBody);

    /**
     * ????????????
     *
     * @return
     */
    @GET(AppHttpPath.GET_TRAIN_INTENT_TYPES)
    Observable<BusinessPropertyBean> getTrainingIntention();

    /**
     * @return
     */
    @POST(AppHttpPath.HOME_CARE)
    Observable<BaseResult> addHomCare(@Body RequestBody requestBody);

    /**
     * @return
     */
    @POST(AppHttpPath.REQUEST_SUGGESTION)
    Observable<BaseResult> addOpinionsAndSuggestions(@Body RequestBody requestBody);




    /*==============================================  ????????????  =============================================*/

    /**
     * @return
     */
    @GET(AppHttpPath.DISABLED_ID_CARD_DETAIL)
    Observable<HandlerCardDetailBean> getDisabilityCertificateInfo(@Query("account") String account,
                                                                   @Query("token") String token,
                                                                   @Query("businessId") String businessId);

    /**
     * @return
     */
    @GET(AppHttpPath.DISABLED_RENEWAL_DETAIL)
    Observable<BusinessChildDetailBean> getCertificatesExchangeInfo(@Query("account") String account,
                                                                    @Query("token") String token,
                                                                    @Query("businessId") String businessId);

    /**
     * @return
     */
    @GET(AppHttpPath.DISABLED_LEVEL_CHANGE_DETAIL)
    Observable<BusinessChildDetailBean> getCertificatesChangeInfo(@Query("account") String account,
                                                                  @Query("token") String token,
                                                                  @Query("businessId") String businessId);

    /**
     * @return
     */
    @GET(AppHttpPath.DISABLED_REISSUE_DETAIL)
    Observable<BusinessChildDetailBean> getCertificatesReissueInfo(@Query("account") String account,
                                                                   @Query("token") String token,
                                                                   @Query("businessId") String businessId);

    /**
     * @return
     */
    @GET(AppHttpPath.DISABLED_CARD_MOVE_IN_DETAIL)
    Observable<BusinessChildDetailBean> getCertificatesMoveinInfo(@Query("account") String account,
                                                                  @Query("token") String token,
                                                                  @Query("businessId") String businessId);

    /**
     * @return
     */
    @GET(AppHttpPath.DISABLED_CARD_MOVE_OUT_DETAIL)
    Observable<BusinessChildDetailBean> getCertificatesMoveoutInfo(@Query("account") String account,
                                                                   @Query("token") String token,
                                                                   @Query("businessId") String businessId);

    /**
     * @return
     */
    @GET(AppHttpPath.DISABLED_CARD_LOGOUT_DETAIL)
    Observable<BusinessChildDetailBean> getCertificatesCancelInfo(@Query("account") String account,
                                                                  @Query("token") String token,
                                                                  @Query("businessId") String businessId);

    /**
     * @return
     */
    @GET(AppHttpPath.DISABLED_CARD_EMPLOYMENT_REGIST_DETAIL)
    Observable<EmploymentRegDetailBean> getDisabledObtainEmploymentInfo(@Query("account") String account, @Query(
            "token") String token, @Query("businessId") String businessId);

    /**
     * @return
     */
    @GET(AppHttpPath.CEREBRAL_PALSY_RECOVERY_DETAIL)
    Observable<RecoveryDetailBean> getDisabledChildrenCerebralPalsyInfo(@Query("account") String account, @Query(
            "token") String token, @Query("businessId") String businessId);

    /**
     * @return
     */
    @GET(AppHttpPath.DEAF_CHILD_RECOVERY_DETAIL)
    Observable<RecoveryDetailBean> getDisabledChildrenDeafInfo(@Query("account") String account,
                                                               @Query("token") String token,
                                                               @Query("businessId") String businessId);

    /**
     * @return
     */
    @GET(AppHttpPath.LONELY_CHILD_RECOVERY_DETAIL)
    Observable<RecoveryDetailBean> getDisabledChildrenAutismInfo(@Query("account") String account,
                                                                 @Query("token") String token,
                                                                 @Query("businessId") String businessId);

    /**
     * @return
     */
    @GET(AppHttpPath.IQ_CHILD_RECOVERY_DETAIL)
    Observable<RecoveryDetailBean> getDisabledChildrenIntellectualInfo(@Query("account") String account, @Query(
            "token") String token, @Query("businessId") String businessId);

    /**
     * @return
     */
    @GET(AppHttpPath.DISABLED_FAMILY_CHILD_BURSARY_DETAIL)
    Observable<StudentBursaryDetailBean> getDisabledStudentFamilyGrantInfo(@Query("account") String account, @Query(
            "token") String token, @Query("businessId") String businessId);

    /**
     * @return
     */
    @GET(AppHttpPath.DISABLED_CHILD_BURSARY_DETAIL)
    Observable<StudentBursaryDetailBean> getDisabledStudentGrantInfo(@Query("account") String account,
                                                                     @Query("token") String token, @Query("businessId"
    ) String businessId);

    /**
     * @return
     */
    @GET(AppHttpPath.REQUEST_AIDS_DETAIL)
    Observable<AssistToolDetailBean> getAIDSInfo(@Query("account") String account, @Query("token") String token,
                                                 @Query("businessId") String businessId);

    /**
     * @return
     */
    @GET(AppHttpPath.REQUEST_TRAIN_DETAIL)
    Observable<TrainRequestDetailBean> getTrainInfo(@Query("account") String account, @Query("token") String token,
                                                    @Query("businessId") String businessId);

    /**
     * @return
     */
    @GET(AppHttpPath.HOME_CARE_DETAIL)
    Observable<HomeCareDetailBean> getHomCareInfo(@Query("account") String account, @Query("token") String token,
                                                  @Query("businessId") String businessId);
    /**
     * @return
     */
    @GET(AppHttpPath.GET_DISABLED_BASE_INFO)
    Observable<DisabledBaseInfoBean> getDisabledBaseInfo(@Query("idNo") String idNo);

}
