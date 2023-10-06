package com.kmetabus.forwarder.service;

import com.kmetabus.forwarder.vo.ListItem;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// 서버에서 갱신날짜를 가져온다.
public class ServerResponse {
    private String message;
    private ApiService apiService;
    private String BASE_URL = "http://kmetabus.com";
    public String getMessage() {
        return message;
    }

    private Call<ResponseBody> call = null;
    public void setMessage(String message) {
        this.message = message;
    }
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(UnsafeOkHttpClient.getUnsafeOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    // forwarder 목록을 가져온다.
    public  void getForwarder(String forwarder, String addr, Callback<ResponseBody> callback){
        List<ListItem> list= null;
        apiService = retrofit.create(ApiService.class);
        call = apiService.getForwarder(forwarder, addr );
        call.enqueue(callback);
    }
    public  void saveForwarder(String json , Callback<ResponseBody> callback){
        List<ListItem> list= null;
        apiService = retrofit.create(ApiService.class);
        RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
        call = apiService.saveForwarder(requestBody);
        call.enqueue(callback);
    }


}
