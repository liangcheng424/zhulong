package com.lmc.frame;

import com.google.gson.JsonObject;
import com.lmc.data.BaseInfo;
import com.lmc.data.LoginInfo;
import com.lmc.data.MainAdEntity;
import com.lmc.data.PersonHeader;
import com.lmc.data.SpecialtyChooseEntity;
import com.lmc.data.TestInfo;
import com.lmc.data.course.CourseBean;
import com.lmc.data.data.DataGroupBean;
import com.lmc.data.data.SmartBean;
import com.lmc.data.mainpage.BannerLiveInfo;
import com.lmc.data.mainpage.IndexCommondEntity;
import com.lmc.data.vip.VipBannerBean;
import com.lmc.data.vip.VipListBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface IService {
    @GET(".")
    Observable<TestInfo> getTestData(@QueryMap Map<String,Object> params, @Query("page_id") int pageId);

    @GET
    Observable<BaseInfo<MainAdEntity>> getAdvert(@Url String url, @QueryMap Map<String,Object> pMap);

    @GET
    Observable<BaseInfo<List<SpecialtyChooseEntity>>> getSubjectList(@Url String url);

    @GET("loginByMobileCode")
    Observable<BaseInfo<String>> getLoginVerify(@Query("mobile") String mobile);

    @GET("loginByMobileCode")
    Observable<BaseInfo<LoginInfo>> loginByVerify(@QueryMap Map<String, Object> params);

    @POST
    @FormUrlEncoded
    Observable<BaseInfo<PersonHeader>> getHeaderInfo(@FieldMap Map<String,Object> params);

    @GET
    Observable<BaseInfo<List<IndexCommondEntity>>> getMainPageList(@Url String url, @QueryMap Map<String,Object> params);

    @GET
    Observable<JsonObject> getBannerLive(@Url String url, @QueryMap Map<String,Object> params);

    String url = "https://edu.zhulong.com/openapi/lesson/";
    @GET("getCarouselphoto?pro=21&more_live=1&is_new=1&new_banner=1&uid=15063681&time=1591367557&devices=oppoR11&system=android,5.1.1&version=2.1.4&unique_id=355757265852349&client_id=205")
    Observable<BannerLiveInfo> getBanner();

    @POST("lesson/getIndexCommend")
    @FormUrlEncoded
    Observable<BaseInfo<List<IndexCommondEntity>>> getMainPageList(@FieldMap Map<String,Object> params);

    @GET("lesson/getLessonListForApi?page=1&course_type=3&limit=15&specialty_id=6&uid=15063681&time=1591366329&devices=oppoR11&system=android,5.1.1&version=2.1.4&unique_id=355757265852349&client_id=205")
    Observable<CourseBean> getCourse();

    @GET("lesson/getLessonListForApi?page=1&course_type=1&limit=15&specialty_id=6&uid=15063681&time=1591366330&devices=oppoR11&system=android,5.1.1&version=2.1.4&unique_id=355757265852349&client_id=205")
    Observable<CourseBean> getSmartCourse();

    @GET("lesson/getLessonListForApi?page=1&course_type=2&limit=15&specialty_id=6&uid=15063681&time=1591366543&devices=oppoR11&system=android,5.1.1&version=2.1.4&unique_id=355757265852349&client_id=205")
    Observable<CourseBean> getPublicCourse();

    @GET("get_new_vip?uid=15063681&pro=21&uid=15063681&time=1591368296&devices=oppoR11&system=android,5.1.1&version=2.1.4&unique_id=355757265852349&client_id=205")
    Observable<VipBannerBean> getVipBanner();

    @GET("lesson/getVipSmallLessonList?<specialty_id=21&sort=2&page=2&uid=15063681&appid=301&time=1591368435&token=a0cccbcac418f78103c754a870494b85&devices=oppoR11&system=android,5.1.1&version=2.1.4&unique_id=355757265852349&client_id=205")
    Observable<VipListBean> getVipList();

    @GET("group/getGroupList?page=1&type=1&fid=29&uid=15063681&time=1591368576&devices=oppoR11&system=android,5.1.1&version=2.1.4&unique_id=355757265852349&client_id=205")
    Observable<DataGroupBean> getDataList();

    @GET("group/getThreadEssence?page=1&fid=29&uid=15063681&time=1591368576&devices=oppoR11&system=android,5.1.1&version=2.1.4&unique_id=355757265852349&client_id=205")
    Observable<SmartBean> getSmart();
}
