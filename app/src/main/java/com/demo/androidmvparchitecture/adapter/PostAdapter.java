package com.demo.androidmvparchitecture.adapter;

import android.view.LayoutInflater;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.demo.androidmvparchitecture.data.model.PostResponse;
import com.demo.androidmvparchitecture.databinding.RowPostBinding;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private final List<PostResponse> postResponseList;

    public PostAdapter(List<PostResponse> postResponseList) {
        this.postResponseList = postResponseList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowPostBinding binding = RowPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        final PostResponse item = postResponseList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return postResponseList.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        private final RowPostBinding binding;

        public PostViewHolder(@NonNull RowPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(PostResponse item) {
            binding.setPostResponse(item);
        }
    }
}