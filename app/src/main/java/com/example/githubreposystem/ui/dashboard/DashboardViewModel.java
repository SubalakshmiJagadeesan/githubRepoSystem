package com.example.githubreposystem.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.githubreposystem.model.Repository;
import com.example.githubreposystem.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<List<Repository>> mRepositories;
    private MutableLiveData<String> mErrorText;

    public DashboardViewModel() {
        mRepositories = new MutableLiveData<>();
        mErrorText = new MutableLiveData<>();
        fetchRepositories();
    }

    public MutableLiveData<List<Repository>> getRepositories(){
        return mRepositories;
    }

    public MutableLiveData<String> getErrorText(){
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
}