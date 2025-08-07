package com.demo.androidmvparchitecture.ui.post;

import com.demo.androidmvparchitecture.data.model.PostResponse;
import com.demo.androidmvparchitecture.room.AppDatabase;
import com.demo.androidmvparchitecture.room.AppExecutors;
import com.demo.androidmvparchitecture.room.model.PostEntity;
import com.demo.androidmvparchitecture.utils.ConnectivityUtils;

import java.util.ArrayList;
import java.util.List;

public class PostPresenter extends PostApiTaskImpl implements PostContract.Presenter {

    private final PostContract.View mPostView;
    private final ConnectivityUtils connectivityUtils;
    private List<PostEntity> postEntityList;
    private final AppDatabase appDB;

    public PostPresenter(PostContract.View mPostView) {
        super(mPostView);
        this.mPostView = mPostView;
        this.connectivityUtils = new ConnectivityUtils(mPostView.getContext());
        appDB = AppDatabase.getInstance(mPostView.getContext());
    }

    @Override
    public void onPostTask() {
        mPostView.showLoader();
        if (connectivityUtils.isOnline()) {
            onPostApiTask();
        } else {
            onPostDBTask();
        }
    }

    @Override
    public void onPostDBTask() {
        try {
            List<PostResponse> postResponseList = new ArrayList<>();
            AppExecutors.getInstance().diskIO().execute(() -> {
                postEntityList = appDB.postDAO().getPost();
                if (postEntityList != null && !postEntityList.isEmpty()) {
                    for (PostEntity entity : postEntityList) {
                        PostResponse postResponse = new PostResponse();
                        postResponse.setId(entity.getId());
                        postResponse.setUserId(entity.getUserId());
                        postResponse.setTitle(entity.getTitle());
                        postResponse.setBody(entity.getBody());
                        postResponseList.add(postResponse);
                    }
                }

                if (!postResponseList.isEmpty()) {
                    mPostView.showPostList(postResponseList, "Data From Local DB", true);
                } else {
                    mPostView.showPostList(new ArrayList<>(), "Data From Local DB", false);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPostAPITaskComplete(List<PostResponse> postResponseList, String message, boolean isFound) {
        mPostView.showPostList(postResponseList, message, isFound);
    }
}
