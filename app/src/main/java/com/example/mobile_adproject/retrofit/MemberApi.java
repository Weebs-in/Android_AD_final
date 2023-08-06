package com.example.mobile_adproject.retrofit;

import com.example.mobile_adproject.api_responses.LoginApiResponse;
import com.example.mobile_adproject.api_responses.SignupApiResponse;
import com.example.mobile_adproject.models.Member;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MemberApi {
    @POST("/api/member")
    Call<SignupApiResponse> create(@Body Member member);

    @GET("/api/member/{id}")
    Call<Member> getMemberById(@Path("id") int id);

    @POST("/auth/login")
    Call<LoginApiResponse> login(@Body Member member);
}
