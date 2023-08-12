package com.example.mobile_adproject.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_adproject.R;
import com.example.mobile_adproject.api_responses.LoginApiResponse;
import com.example.mobile_adproject.models.Book;
import com.example.mobile_adproject.retrofit.BookApi;
import com.example.mobile_adproject.retrofit.RetrofitService;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetailActivity extends AppCompatActivity {

    BookApi bookApi;
    Long bookId;

    ImageView bookCoverImageView;
    TextView bookTitleTextView;
    TextView bookAuthorTextView;
    TextView bookAuthorAddTextView;
    TextView bookAddIsbn;

    TextView bookConditionTextview;

    TextView bookGenreTextView;

    TextView bookPressTextView;

    TextView bookLanguageTextView;

    TextView bookDescriptionTextView;

    TextView bookFavouriteTextView;

    Button btnRequest;

    ImageView imgBookDetail;

    String jwtToken = LoginApiResponse.getAccessToken();

    String authHeader = "Bearer " + jwtToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        btnRequest = findViewById(R.id.button_request);
        btnRequest.setOnClickListener(view -> {
            Intent intent = new Intent(BookDetailActivity.this, BookRequestActivity.class);
            startActivity(intent);
        });

        imgBookDetail = findViewById(R.id.back_img_book_detail);
        imgBookDetail.setOnClickListener(view -> {
            Intent intent = new Intent(BookDetailActivity.this, MainActivity.class);
            startActivity(intent);
        });

        bookCoverImageView = findViewById(R.id.book_cover_background_book_detail);
        bookTitleTextView = findViewById(R.id.book_title_book_detail);
        bookAuthorAddTextView = findViewById(R.id.book_author_need_to_add_book_detail);
        bookAddIsbn = findViewById(R.id.isbn_need_to_add_book_detail);
        bookConditionTextview = findViewById(R.id.book_condition_need_to_add_book_detail);
        bookGenreTextView = findViewById(R.id.book_genre_need_to_add_book_detail);
        bookPressTextView = findViewById(R.id.book_press_need_to_add_book_detail);
        bookLanguageTextView = findViewById(R.id.book_language_need_to_add_book_detail);
        bookDescriptionTextView = findViewById(R.id.book_description_need_to_add_book_detail);
        bookFavouriteTextView = findViewById(R.id.book_detail_favourite_number);

        RetrofitService retrofitService = new RetrofitService();
        bookApi = retrofitService.getRetrofit().create(BookApi.class);


        fetchBookDetails(authHeader, bookId);
    }

    private void fetchBookDetails(String authHeader, Long bookId) {
        Call<Book> call = bookApi.getBookByIds(authHeader, bookId);

        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful()) {
                    Book book = response.body();

                    Picasso.get()
                            .load(book.getCover())
                            .placeholder(R.drawable.baseline_view_compact_24)
                            .into(bookCoverImageView);

                    // Update UI components with the fetched book details
                    updateUIWithBookDetails(book);
                } else {
                    // Handle error for this specific book ID
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Toast.makeText(BookDetailActivity.this, "Failed to fetch book details!", Toast.LENGTH_SHORT).show();
                t.printStackTrace(); // Print the full stack trace to see the detailed error
            }
        });
    }

    private void updateUIWithBookDetails(Book book) {

        bookTitleTextView.setText(book.getTitle());
        bookAuthorAddTextView.setText(book.getAuthor());
        bookAddIsbn.setText(book.getIsbn());
        bookConditionTextview.setText(book.getBookCondition());
        bookGenreTextView.setText(book.getGenre());
        bookPressTextView.setText(book.getPress());
        bookLanguageTextView.setText(book.getLanguage());
        bookDescriptionTextView.setText(book.getDescription());
        bookFavouriteTextView.setText(book.getLikeCount());

    }

}