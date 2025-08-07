package com.demo.androidmvparchitecture.ui.todo;

import androidx.annotation.NonNull;

import com.demo.androidmvparchitecture.api.rest.RetrofitClient;
import com.demo.androidmvparchitecture.constant.Config;
import com.demo.androidmvparchitecture.data.model.TodoResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class TodoApiTaskImpl implements TodoApiTask {

    private final TodoContract.View mTodoView;

    public TodoApiTaskImpl(TodoContract.View mTodoView) {
        this.mTodoView = mTodoView;
    }

    @Override
    public void onTodoApiTask() {
        RetrofitClient.getInstance(Config.JSON_PLACEHOLDER_BASE_URL).getTodo().enqueue(mTodoCallbackListener);
    }

    private final Callback<List<TodoResponse>> mTodoCallbackListener = new Callback<List<TodoResponse>>() {
        @Override
        public void onResponse(@NonNull Call<List<TodoResponse>> call, @NonNull Response<List<TodoResponse>> response) {
            mTodoView.hideLoader();
            try {
                if (response.code() == 200 && response.isSuccessful()) {
                    List<TodoResponse> todoResponseList = response.body();
                    if (todoResponseList != null && !todoResponseList.isEmpty()) {
                        mTodoView.onTodoTaskFinish(todoResponseList);
                    } else {
                        mTodoView.onTodoTaskFinish(new ArrayList<>());
                    }
                } else {
                    mTodoView.onTodoTaskFinish(new ArrayList<>());
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(@NonNull Call<List<TodoResponse>> call, @NonNull Throwable t) {
            mTodoView.hideLoader();
            mTodoView.onTodoTaskFinish(new ArrayList<>());
        }
    };
}
