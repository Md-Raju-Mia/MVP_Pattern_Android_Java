package com.demo.androidmvparchitecture.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.demo.androidmvparchitecture.room.model.PostEntity;

import java.util.List;

@Dao
public interface PostDAO {

    @Query("SELECT * FROM tbl_post")
    List<PostEntity> getPost();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PostEntity> postEntityList);
}
