package com.example.mobile_adproject.retrofit;

import com.example.mobile_adproject.models.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BookApi {

    @POST("/api/book")
    Call<Book> createBook(@Body Book book, @Header("Authorization") String authorizationHeader);

    @GET("/api/book")
    Call<List<Book>> getAllBook(@Header("Authorization") String authorizationHeader);

    @GET("/api/book/randaom")
    Call<List<Book>> randomBook();

    @GET("/api/book/randaom")
    Call<List<Book>> recommandBook(@Header("Authorization") String  authorizationHeader);

    @GET("/api/book/{id}")
    Call<Book> getBookById(@Path("id") int id);

    @PUT("/api/book/{id}")
    Call<Book> editBookById(@Path("id") int id, @Body Book book);

    @DELETE("/api/book/{id}")
    Call<Void> deleteBookById(@Path("id") int id);


}
