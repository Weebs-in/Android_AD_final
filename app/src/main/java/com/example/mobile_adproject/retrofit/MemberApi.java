package com.example.mobile_adproject.retrofit;

import com.example.mobile_adproject.api_responses.LoginApiResponse;
import com.example.mobile_adproject.api_responses.SignupApiResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MemberApi {
    @Headers("Content-Type: application/json")
    @POST("/api/member")
    Call<SignupApiResponse> create(@Body SignupApiResponse signupApiResponse);

    @GET("/api/member/{id}")
    Call<SignupApiResponse> getMemberById(@Path("id") int id);

    @POST("/auth/login")
    Call<LoginApiResponse> login(@Body LoginApiResponse loginApiResponse);
}
