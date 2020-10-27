package com.example.githubreposystem.ui.dashboard;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.view.menu.MenuBuilder;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.githubreposystem.Constants;
import com.example.githubreposystem.model.Repository;
import com.example.githubreposystem.retrofit.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<List<Repository>> mRepositories;
    private MutableLiveData<String> mErrorText;


    public DashboardViewModel() {
        mRepositories = new MutableLiveData<>();
        mErrorText = new MutableLiveData<>();
        //fetchRepositories();

    }

    public MutableLiveData<List<Repository>> getRepositories() {
        return mRepositories;
    }

    public MutableLiveData<String> getErrorText() {
        return mErrorText;
    }

    private void fetchRepositories() {
        RetrofitClient.getInstance().getMyApi().getRepositories().enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                mRepositories.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {

                mErrorText.setValue(t.getMessage());
            }
        });
    }

    public void fetchRepositoriesFromVolley(Context context) {
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest stringRequest = new JsonArrayRequest(Constants.ApiConstants.REPO_URL,
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(org.json.JSONArray response) {

                        ArrayList<Repository> repositories = new Gson().fromJson(String.valueOf(response), new TypeToken<List<Repository>>() {
                        }.getType());

                        mRepositories.setValue(repositories);
                        // Display the first 500 characters of the response string.
                        //textView.setText("Response is: "+ response.substring(0,500));
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mErrorText.setValue("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        mRequestQueue.add(stringRequest);

    }
}