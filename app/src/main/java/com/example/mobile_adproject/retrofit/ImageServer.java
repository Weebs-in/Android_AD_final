package com.example.mobile_adproject.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImageServer {
    private Retrofit retrofit;
    public ImageServer(){init();}

    private void init() {
         retrofit = new Retrofit.Builder()
            .baseUrl("http://20.187.123.52:80/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    }
    public Retrofit getImageServerRetrofit() {
        return retrofit;
    }
}

