package com.lmc.frame;

import com.lmc.data.BaseInfo;
import com.lmc.data.MainAdEntity;
import com.lmc.data.SpecialtyChooseEntity;
import com.lmc.data.TestInfo;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface IService {
    @GET(".")
    Observable<TestInfo> getTestData(@QueryMap Map<String,Object> params, @Query("page_id") int pageId);

    @GET("ad/getAd")
    Observable<BaseInfo<MainAdEntity>> getAdvert(@QueryMap Map<String,Object> pMap);

    @GET("lesson/getAllspecialty")
    Observable<BaseInfo<List<SpecialtyChooseEntity>>> getSubjectList();
}
