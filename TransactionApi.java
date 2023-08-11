package com.example.mobile_adproject.retrofit;

import com.example.mobile_adproject.models.Book;
import com.example.mobile_adproject.models.TransactionHistoryData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface TransactionApi {

    @GET("/api/book/{id}")
    Call<List<TransactionHistoryData>> DonatedBook(@Path("id") Long donarId, @Header("Authorization") String authorizationHeader);


}
