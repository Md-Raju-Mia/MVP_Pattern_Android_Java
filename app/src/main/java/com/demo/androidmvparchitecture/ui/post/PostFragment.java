package com.demo.androidmvparchitecture.ui.post;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.View;

import com.demo.androidmvparchitecture.R;
import com.demo.androidmvparchitecture.adapter.PostAdapter;
import com.demo.androidmvparchitecture.base.BaseFragment;
import com.demo.androidmvparchitecture.data.model.PostResponse;
import com.demo.androidmvparchitecture.databinding.FragmentPostBinding;

import java.util.List;

public class PostFragment extends BaseFragment<FragmentPostBinding> implements PostContract.View {

    private PostPresenter mPostPresenter;

    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_post;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPostPresenter = new PostPresenter(this);
        mPostPresenter.onPostTask();
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
    public Context getContext() {
        return requireActivity();
    }

    @Override
    public void showPostList(@NonNull List<PostResponse> postResponseList, String message, boolean isFound) {
        hideLoader();
        requireActivity().runOnUiThread(() -> {
            PostAdapter postAdapter = new PostAdapter(postResponseList);
            binding.recyclerViewPost.setAdapter(postAdapter);
        });
    }
}
