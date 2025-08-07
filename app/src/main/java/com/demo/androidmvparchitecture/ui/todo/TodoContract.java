package com.demo.androidmvparchitecture.ui.todo;

import com.demo.androidmvparchitecture.base.BaseView;
import com.demo.androidmvparchitecture.data.model.TodoResponse;

import java.util.List;

public class TodoContract {

    interface View extends BaseView {
        void onTodoTaskFinish(List<TodoResponse> todoResponseList);
    }

    interface Presenter {
        void onTodoTask();
    }
}
