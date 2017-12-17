package com.example.cldla.finalproject;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by Owner on 2017-12-16.
 */

public interface ApiService_now {
    //베이스 Url
    static final String BASEURL = "http://apis.skplanetx.com/";
    static final String APPKEY ="7478135a-39b8-3ec9-80d8-02881e2a61a6";
    //get 메소드를 통한 http rest api 통신
    @GET("weather/current/hourly")
    Call<JsonObject> get3days(@Header("appKey") String appKey, @Query("version") int version,
                              @Query("lat") double lat, @Query("lon") double lon);

}

