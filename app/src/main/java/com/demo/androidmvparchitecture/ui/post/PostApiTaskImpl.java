package com.demo.androidmvparchitecture.ui.post;

import com.demo.androidmvparchitecture.api.repo.PostRepository;
import com.demo.androidmvparchitecture.api.response.ResponseCallback;
import com.demo.androidmvparchitecture.api.response.ResponseListener;
import com.demo.androidmvparchitecture.data.model.PostResponse;
import com.demo.androidmvparchitecture.room.AppDatabase;
import com.demo.androidmvparchitecture.room.AppExecutors;
import com.demo.androidmvparchitecture.room.dao.PostDAO;
import com.demo.androidmvparchitecture.room.model.PostEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class PostApiTaskImpl implements PostApiTask {

    private final PostContract.View mPostView;

    public PostApiTaskImpl(PostContract.View mPostView) {
        this.mPostView = mPostView;
    }

    @Override
    public void onPostApiTask() {
        PostRepository postRepository = PostRepository.getInstance();
        postRepository.setPostResponse();
        postRepository.setOnPostResponseListener(onPostCallbackListener);
    }

    private final ResponseListener<List<PostResponse>> onPostCallbackListener = new ResponseListener<List<PostResponse>>() {
        @Override
        public void onResponse(ResponseCallback<List<PostResponse>> response, String message) {
            List<PostResponse> postResponseList = response.getBody();
            onPostAPITaskComplete(postResponseList, "From Api", true);

            try {
                List<PostEntity> postEntityList = new ArrayList<>();
                if (!postResponseList.isEmpty()){
                    for (PostResponse item : postResponseList) {
                        PostEntity postEntity = new PostEntity();
                        postEntity.setId(item.getId());
                        postEntity.setUserId(item.getUserId());
                        postEntity.setTitle(item.getTitle());
                        postEntity.setBody(item.getBody());
                        postEntityList.add(postEntity);
                    }
                }

                AppExecutors.getInstance().diskIO().execute(() -> {
                    PostDAO postDAO = AppDatabase.getInstance(mPostView.getContext().getApplicationContext()).postDAO();
                    if (!postEntityList.isEmpty()) {
                        postDAO.insertAll(postEntityList);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(String message) {
            onPostAPITaskComplete(new ArrayList<>(), "From Api", false);
        }
    };

    public abstract void onPostAPITaskComplete(List<PostResponse> postResponseList, String message, boolean isFound);
}
