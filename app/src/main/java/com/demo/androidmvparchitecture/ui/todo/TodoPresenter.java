package com.demo.androidmvparchitecture.ui.todo;

public class TodoPresenter extends TodoApiTaskImpl implements TodoContract.Presenter {

    private TodoContract.View mTodoView;

    public TodoPresenter(TodoContract.View mTodoView) {
        super(mTodoView);
        this.mTodoView = mTodoView;
    }

    @Override
    public void onTodoTask() {
        mTodoView.showLoader();
        onTodoApiTask();
    }
}
