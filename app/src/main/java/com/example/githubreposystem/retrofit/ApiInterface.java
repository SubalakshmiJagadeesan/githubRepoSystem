package com.example.githubreposystem.retrofit;

import com.example.githubreposystem.model.Repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

public interface ApiInterface {

    String BASE_URL = "https://api.github.com/";

    @GET("repositories")
    Call<List<Repository>> getRepositories();
}
