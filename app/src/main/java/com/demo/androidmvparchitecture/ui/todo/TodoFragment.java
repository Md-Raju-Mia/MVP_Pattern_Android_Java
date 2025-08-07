package com.demo.androidmvparchitecture.ui.todo;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.demo.androidmvparchitecture.R;
import com.demo.androidmvparchitecture.adapter.TodoAdapter;
import com.demo.androidmvparchitecture.base.BaseFragment;
import com.demo.androidmvparchitecture.data.model.TodoResponse;
import com.demo.androidmvparchitecture.databinding.FragmentTodoBinding;

import java.util.List;

public class TodoFragment extends BaseFragment<FragmentTodoBinding> implements TodoContract.View {

    private TodoPresenter mTodoPresenter;

    public TodoFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_todo;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTodoPresenter = new TodoPresenter(this);
        mTodoPresenter.onTodoTask();
    }

    @Override
    public void showLoader() {
        showLoading();
    }

    @Override
    public void hideLoader() {
        hideLoading();
    }

    @Override
    public void showMessage(String message) {
        showToast(message);
    }

    @Override
    public void onTodoTaskFinish(List<TodoResponse> todoResponseList) {
        TodoAdapter todoAdapter = new TodoAdapter(todoResponseList);
        binding.recyclerViewTodo.setAdapter(todoAdapter);
    }
}