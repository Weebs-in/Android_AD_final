package com.example.mobile_adproject.retrofit;

import com.example.mobile_adproject.models.CollectionPoint;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface CollectionPointApi {
    @GET("/api/collectionPoint")
    Call<List<CollectionPoint>> getAllCollectionPoints(@Header("Authorization") String authorizationHeader);

}
