package com.example.githubreposystem.retrofit;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private ApiInterface myApi;

    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create( new GsonBuilder()
                        .create()))
                .build();
        myApi = retrofit.create(ApiInterface.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public ApiInterface getMyApi() {
        return myApi;
    }
}
