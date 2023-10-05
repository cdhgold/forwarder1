package com.kmetabus.forwarder.service;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    // 공인중개사 목록 gu: 서울 구 명 , office는 공인중개사소 명
    @Headers("Accept: application/json")
    @GET("/budongsan/list/{gu}/{office}")
    Call<ResponseBody> getBudongsan(@Path("gu") String gu, @Path("office") String office);

    // 급매정보 등록
    @Headers("Accept: application/json")
    @POST("/budongsan/qsale/")
    Call<ResponseBody> saveQsale( @Body RequestBody qsale);

    // 급매리스트, param : 공인중개소명
    @Headers("Accept: application/json")
    @GET("/budongsan/getQsale/{search}/")
    Call<ResponseBody> getQsale( @Path("search") String search);

    //  개인급매리스트, param : 공인중개소명
    @Headers("Accept: application/json")
    @GET("/budongsan/getPsale/{addr}/")
    Call<ResponseBody> getPsale(   @Path("addr") String addr );
    // 개인 급매정보 등록
    @Headers("Accept: application/json")
    @POST("/budongsan/psale/")
    Call<ResponseBody> savePsale( @Body RequestBody psale);

}
