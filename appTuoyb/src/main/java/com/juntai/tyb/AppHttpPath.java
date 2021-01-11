package com.juntai.tyb;

public class AppHttpPath {
    /**
     * base
     */
    public static final String BASE = "http://kb167.cn:25080/zhcl";
    /**
     * base
     */
    public static final String BASE_IMAGE = "http://image.kb167.cn/";

    /**
     * 摄像头缩略图
     */
    public static final String STREAM_CAMERA_THUMBNAI_IMAGE = "https://www.juntaikeji.com:17002/cameraImg/thumbnail/";
    /**
     * 测试接口
     */
//            public static final String BASE = "http://192.168.124.118:8080/zhcl";

    /**
     * 获取案件类型
     */
    public static final String GET_CASE_TYPE = BASE + "/u/appCallCate.shtml";
    /**
     * 获取我的案件
     */
    public static final String GET_MYCASE = BASE + "/u/infoAppCaseById.shtml";
    /**
     * 搜索
     */
    public static final String SEARCH = BASE + "/u/appCallUnion.shtml";

    /*====================================================    天气
    ==============================================================*/

    //实时天气
    public static final String REALTIME_WEATHER = BASE + "/u/appConnector/getRealTimeWeather.shtml";
    //天气预报
    public static final String FORCAST_WEATHER = BASE + "/u/appConnector/weatherForecast.shtml";
    //获取省份
    public static final String PROVINCE = BASE + "/u/appConnector/getProvince.shtml";
    //获取城市 u/apiAppAlarm/getProvince.shtml
    public static final String CITY = BASE + "/u/appConnector/getCity.shtml";
    //获取地区 u/apiAppAlarm/getProvince.shtml
    public static final String AREA = BASE + "/u/appConnector/getArea.shtml";
    //获取街道
    public static final String STREET = BASE + "/u/appConnector/getStreet.shtml";

    /*====================================================    流媒体
    ==============================================================*/
    //摄像头拉流地址
    public static final String BASE_CAMERA_URL = "http://www.juntaikeji.net:8060";
    //摄像头拉流地址
    public static final String BASE_CAMERA_DNS = "rtmp://www.juntaikeji.net:1935";



    //摄像头拉流地址
    public static final String BASE_CAMERA_CAPTURE_URL = "http://juntaikeji.net:8080/";


    /**
     * 硬盘录像机和OpenLiveBean独立摄像头列表
     */
    public static final String STREAM_CAMERAS = BASE + "/u/camera/selectDvrAndCameraListAPP.shtml";
    /**
     * 硬盘录像机下面的摄像头列表
     */
    public static final String STREAM_CAMERAS_FROM_VCR = BASE + "/u/camera/selectCameraByDvrIdAPP.shtml?";
    /**
     * 摄像头详情
     */
    public static final String STREAM_CAMERAS_DETAIL = BASE + "/u/camera/selectCameraByIdAPP.shtml";

    /**
     * 上传封面图
     */
    public static final String UPLOAD_STREAM_CAMERAS_THUMB = BASE + "/u/camera/uploadCameraImgAPP.shtml";


    /*====================================================    托养信息
    ==============================================================*/

    /**
     * 获取托养子列表/u/infoAppCase.shtml
     */
    //    public static final String CASE_INFO = BASE + "/u/infoAppCase.shtml";
    /**
     * 获取托养子列表/u/infoAppCase.shtml
     */
    public static final String ALL_STREETS = BASE + "/u/appConnector/selectStreet.shtml";
    /**
     * 查询年份
     */
    public static final String GET_YEARS = BASE + "/u/appConnector/selectYear.shtml";




    /*====================================================    登录接口
    ==============================================================*/

    /**
     * 登录
     */
    public static final String LOGIN = BASE + "/u/appConnector/appLogin.shtml";

    /**
     * 搜索托养人员
     */
    public static final String SEARCH_CARETAKER = BASE + "/u/appConnector/selectCaregiversList.shtml";

    /**
     * 搜索所有的残疾人
     */
    public static final String SEARCH_ALL_DISABLED_PEOPLE = BASE + "/u/appConnector/selectDisabledList.shtml";
    /**
     * 托养信息
     */
    public static final String CARE_INFO = BASE + "/u/appConnector/selectCaregiversById.shtml";
    /**
     * 托养信息
     */
    public static final String CARE_RECORD = BASE + "/u/appConnector/selectServiceInfoById.shtml";
    /**
     * 托养信息  更多
     */
    public static final String CARE_INFO_MORE = BASE + "/u/appConnector/selectCaregiversMoreById.shtml";
    /**
     * 托养人基本信息
     */
    public static final String CARE_TAKER_BASE_INFO = BASE + "/u/appConnector/selectDisabledInfoByIdNo.shtml";


    /**
     * 添加托养人
     */
    public static final String ADD_CARE_TAKER = BASE + "/u/appConnector/insertCaregivers.shtml";
    /**
     * 修改托养人
     */
    public static final String MODIFY_CARE_TAKER = BASE + "/u/appConnector/insertCaregiversUpdate.shtml";
    /**
     * 按街道分类服务人员(选择服务人员)接口
     */
    public static final String GET_SERVICE_PEOPLE = BASE + "/u/appConnector/selectStreetServicerList.shtml";
    /**
     * 提交托养记录
     */
    public static final String COMMIT_CARE_RECORD = BASE + "/u/appConnector/insertService.shtml";
    /**
     * 获取服务类型
     */
    public static final String GET_SERVICE_TYPE = BASE + "/u/appConnector/getServiceCate.shtml";

    /**
     * 服务记录
     */
    public static final String SERVICE_RECORD = BASE + "/u/appConnector/getMyCaregiversList.shtml";
    /**
     * 上传位置信息
     */
    public static final String UPLOAD_LOCATION = BASE + "/u/appConnector/insertUserHistory.shtml";


    /**
     * 托养记录分布
     */
    public static final String CARE_RECORD_POSITIONS = BASE + "/u/appConnector/selectCaregiversListWhere.shtml";


    /**
     * 服务人员
     */
    public static final String SERVICE_PEOPLES_POSITIONS = BASE + "/u/appConnector/selectServicerList.shtml";
    /**
     * 康复机构
     */
    public static final String HEATH_ORGANIZE_POSITIONS = BASE + "/u/appConnector/selectKeyunitList.shtml";
    /**
     * 康复机构详情
     */
    public static final String HEATH_ORGANIZE_DETAIL = BASE + "/u/appConnector/selectKeyunitById.shtml";




    /*====================================================
    我的=============================================================*/


    /**
     * 退出登录
     */
    public static final String LOG_OUT = BASE + "/u/appConnector/appLogout.shtml";

    /**
     * 个人基本信息
     */
    public static final String USER_INFO = BASE + "/u/appConnector/getUserInfoById.shtml";


    /**
     * 检查更新
     */
    public static final String APP_UPDATE = BASE + "/u/appConnector/detectionAppVersions.shtml";
    /**
     * 修改用户密码
     */
    public static final String MODIFY_PWD = BASE + "/u/appConnector/updateUserPassWord.shtml";
    /**
     * 修改用户头像
     */
    public static final String MODIFY_HEAD_ICON = BASE + "/u/appConnector/updateUserHead.shtml";
    /**
     * 我的消息
     */
    public static final String MY_NOTICE = BASE + "/u/appConnector/getMsgList.shtml";
    /**
     * 全部已读
     */
    public static final String IS_READ = BASE + "/u/appConnector/isReaded.shtml";
    /**
     * 未读数据
     */
    public static final String UNREAD_MSG = BASE + "/u/appConnector/getUnReadMsgCount.shtml";

    /**
     * 获取托养子列表/u/infoAppCase.shtml
     */
    public static final String OLD_CASE_INFO = BASE + "/u/appConnector/selectCaregiversInfoOld.shtml";
}
