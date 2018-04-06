package com.shanshan.myapplication3.testDemo;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by shanshan on 2018/3/20.
 */

public interface TestService {

    @POST("test/test1.html")
    Call<ResponseBody> getResult3(@QueryMap Map<String, String> param);


    @POST("test/test2.html")
    Call<ResponseBody> getResult1(@QueryMap Map<String, String> param);

    @GET("test/test3.html")
    Call<ResponseBody> getResult2(@QueryMap Map<String, String> param);
}
