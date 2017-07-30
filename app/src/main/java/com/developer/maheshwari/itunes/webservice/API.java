package com.developer.maheshwari.itunes.webservice;

import com.developer.maheshwari.itunes.response.ResponseClass;
import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface API
{
    @FormUrlEncoded
    @POST("/search?term=Michael+jackson")
    Call<ResponseClass> getResponseData(@Field("") String name);
}