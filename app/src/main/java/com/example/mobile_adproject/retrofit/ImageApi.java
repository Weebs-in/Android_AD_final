package com.example.mobile_adproject.retrofit;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ImageApi {

    @Multipart
    @POST("upload")
    Call<String> uploadImage(@Part MultipartBody.Part image);

}
