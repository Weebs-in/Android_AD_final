package com.example.mobile_adproject.retrofit;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private Retrofit retrofit;

    public RetrofitService(){
        initializeRetrofit();
    }

    private void initializeRetrofit() {
    /*    OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor()) // 添加拦截器
                .build();*/
        retrofit = new Retrofit.Builder()
                .baseUrl("https://adt8api.azurewebsites.net")
               // .client(httpClient) // 使用带有拦截器的OkHttpClient
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public Retrofit getRetrofit(){
        return retrofit;
    }
}
