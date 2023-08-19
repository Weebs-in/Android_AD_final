package com.example.mobile_adproject.retrofit;

import com.example.mobile_adproject.models.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BookApi {

    @POST("/api/book")
    Call<Book> createBook(@Body Book book, @Header("Authorization") String authorizationHeader);
     @POST("/api/book/Complete")
    Call<Void> sendCompleteTransactionStatus(@Query("recipientId")Long recipientId,@Query("BookId")Long bookId,@Header("Authorization") String authorizationHeader);

    @PUT("/api/book/like/{id}")
    Call<Void> updateLikeCount(@Path("id")Long id, @Header("Authorization") String authorizationHeader);

    @GET("/api/book")
    Call<List<Book>> getAllBooks(@Header("Authorization") String authorizationHeader);

    @GET("/api/book/donor/{id}")
    Call<List<Book>> getAllBooksByDonor(@Path("id") Long id, @Header("Authorization") String authorizationHeader);

    @GET("/api/book/{id}")
    Call<Book> getBookById(@Path("id") int id);

    @GET("/api/book/random")
    Call<List<Book>> randomBook();

    @GET("/api/book/recommend")
    Call<List<Book>> recommendBook(@Path("id")Long id,@Header("Authorization") String  authorizationHeader);
    @GET("/api/book/search")
    Call<List<Book>> search(@Query("searchString") String search,@Header("Authorization") String  authorizationHeader);

    @GET("/api/book/recipient/{id}")
    Call<List<Book>> approvedBookByRecipientId(@Path("id")Long id, @Header("Authorization") String authorizationHeader);

    @PUT("/api/book/{id}")
    Call<Book> updateBookById(@Path("id") Long id, @Body Book book, @Header("Authorization") String authorizationHeader);

    @DELETE("/api/book/{id}")
    Call<Void> deleteBookById(@Path("id") int id);

    @GET("/api/transactionHistory/{id}")
    Call<List<Book>> TransactionHistory(@Path("id")Long id, @Header("Authorization") String authorizationHeader);

}
