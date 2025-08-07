package com.demo.androidmvparchitecture.api.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.demo.androidmvparchitecture.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private  static OkHttpClient okHttpClient = null;
    private static final long TIMEOUT = 30;


    private static HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    public static RetrofitAPI getInstance(String baseURL) {
        try{
            if(okHttpClient == null) {
                if(BuildConfig.DEBUG){
                    okHttpClient =  new OkHttpClient.Builder()
                            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                            .addInterceptor(getLoggingInterceptor())
                            .build();

                }else {
                    okHttpClient =  new OkHttpClient.Builder()
                            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                            .build();
                }
            }

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(baseURL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
            }else {
                if (!retrofit.baseUrl().equals(baseURL)) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(baseURL)
                            .client(okHttpClient)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();
                }
            }
            return retrofit.create(RetrofitAPI.class);
        } catch (Exception e){
            return null;
        }
    }
}
