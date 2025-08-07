package com.demo.androidmvparchitecture.api.repo;

import com.demo.androidmvparchitecture.api.response.ResponseCallback;
import com.demo.androidmvparchitecture.api.response.ResponseListener;
import com.demo.androidmvparchitecture.api.response.callback.PostCallback;
import com.demo.androidmvparchitecture.data.model.PostResponse;

import java.util.List;

public class PostRepository {

    private static PostRepository instance;
    private ResponseListener<List<PostResponse>> onPostResponseListener;

    public static PostRepository getInstance() {
        if (instance == null) {
            instance = new PostRepository();
        }
        return instance;
    }

    public void setPostResponse() {
        PostCallback postCallback = new PostCallback();
        postCallback.setOnPostResponseListener(onPostCallbackListener);
    }

    public void setOnPostResponseListener(ResponseListener<List<PostResponse>> onPostResponseListener) {
        this.onPostResponseListener = onPostResponseListener;
    }

    private final ResponseListener<List<PostResponse>> onPostCallbackListener = new ResponseListener<List<PostResponse>>() {
        @Override
        public void onResponse(ResponseCallback<List<PostResponse>> response, String message) {
            onPostResponseListener.onResponse(response, message);
        }

        @Override
        public void onFailure(String message) {
            onPostResponseListener.onFailure(message);
        }
    };
}
