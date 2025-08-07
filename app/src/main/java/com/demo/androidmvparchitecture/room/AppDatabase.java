package com.demo.androidmvparchitecture.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.demo.androidmvparchitecture.room.dao.PostDAO;
import com.demo.androidmvparchitecture.room.model.PostEntity;

@Database(entities = {PostEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "post-todo";
    private static AppDatabase mInstance;

    public static AppDatabase getInstance(Context context) {
        if (mInstance == null) {
            synchronized (AppDatabase.class) {
                mInstance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class, DATABASE_NAME).build();
            }
        }
        return mInstance;
    }

    public abstract PostDAO postDAO();
}
