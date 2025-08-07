package com.demo.androidmvparchitecture.api.response.callback;

import androidx.annotation.NonNull;

import com.demo.androidmvparchitecture.api.response.ResponseCallback;
import com.demo.androidmvparchitecture.api.response.ResponseListener;
import com.demo.androidmvparchitecture.api.rest.RetrofitClient;
import com.demo.androidmvparchitecture.constant.Config;
import com.demo.androidmvparchitecture.data.model.PostResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostCallback implements Callback<List<PostResponse>> {

    private static final String TAG = "PostCallback ";
    private ResponseListener<List<PostResponse>> onPostResponseListener;

    public PostCallback() {
        RetrofitClient.getInstance(Config.JSON_PLACEHOLDER_BASE_URL).getPost().enqueue(this);
    }

    public void setOnPostResponseListener(ResponseListener<List<PostResponse>> onPostResponseListener) {
        this.onPostResponseListener = onPostResponseListener;
    }

    @Override
    public void onResponse(Call<List<PostResponse>> call, Response<List<PostResponse>> response) {
        String message = response.raw().message();
        if (response.code() == 200 && response.isSuccessful()) {
            List<PostResponse> postResponseList = response.body();
            if (postResponseList != null && !postResponseList.isEmpty()) {
                onPostResponseListener.onResponse(new ResponseCallback<>(postResponseList), message);
            } else {
                onPostResponseListener.onFailure(message);
            }
        } else {
            onPostResponseListener.onFailure(message);
        }
    }

    @Override
    public void onFailure(Call<List<PostResponse>> call, @NonNull Throwable throwable) {
        String message = throwable.getMessage();
        onPostResponseListener.onFailure(TAG + message);
    }
}
