package com.example.mobile_adproject.retrofit;

import com.example.mobile_adproject.models.Application;
import com.example.mobile_adproject.models.Book;
import com.example.mobile_adproject.models.CompleteDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApplicationApi {




    @POST("/api/application")
    Call<Application> createApplication(@Body Application application, @Header("Authorization") String authorizationHeader);

    @GET("/api/application/member/{id}")
    Call<List<Application>> getAllApplicationsByMember(@Path("id") Long id, @Header("Authorization") String authorizationHeader);

    @PUT("/api/application/complete")
    Call<Void> updateApplicationComplete(@Body CompleteDTO completeDTO, @Header("Authorization") String authorizationHeader);

    @PUT("/api/application/{id}")
    Call<Application> updateApplicationById(@Path("id") Long id,@Body Application application, @Header("Authorization") String authorizationHeader);
}
