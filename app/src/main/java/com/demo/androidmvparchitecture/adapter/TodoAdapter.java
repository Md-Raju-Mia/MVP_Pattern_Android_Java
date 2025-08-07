package com.demo.androidmvparchitecture.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.androidmvparchitecture.data.model.TodoResponse;
import com.demo.androidmvparchitecture.databinding.RowTodoBinding;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private final List<TodoResponse> todoResponseList;

    public TodoAdapter(List<TodoResponse> todoResponseList) {
        this.todoResponseList = todoResponseList;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowTodoBinding binding = RowTodoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TodoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        final TodoResponse item = todoResponseList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return todoResponseList.size();
    }

    static class TodoViewHolder extends RecyclerView.ViewHolder {
        private final RowTodoBinding binding;

        public TodoViewHolder(@NonNull RowTodoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(TodoResponse item) {
            binding.setTodoResponse(item);
        }
    }
}