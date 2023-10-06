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
    // 포워더업체정보 조회 (업체명 또는 주소
    @Headers("Accept: application/json")
    @GET("/Forward/list/{forwarder}/{addr}")
    Call<ResponseBody> getForwarder(@Path("forwarder") String forwarder, @Path("addr") String addr);

    // 신규업체등록
    @Headers("Accept: application/json")
    @POST("/Forward/save/")
    Call<ResponseBody> saveForwarder( @Body RequestBody forwarder);


}
