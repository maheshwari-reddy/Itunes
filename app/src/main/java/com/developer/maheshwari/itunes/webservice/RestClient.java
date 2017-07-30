package com.developer.maheshwari.itunes.webservice;

import retrofit.Retrofit;

public class RestClient
{
    public static final String baseUrl = "https://itunes.apple.com/";

    public static Retrofit client = new Retrofit.Builder().baseUrl(baseUrl).build();
}
