package com.example.mobile_adproject.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_adproject.R;
import com.example.mobile_adproject.models.Book;
import com.example.mobile_adproject.retrofit.BookApi;
import com.example.mobile_adproject.retrofit.RetrofitService;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonateBookDetailActivity extends AppCompatActivity {
    private ImageView bookCover;
    private TextView titleTextView;
    private TextView authorTextView;
    private TextView isbnTextView;
    private TextView conditionTextView;
    private TextView genreTextView;
    private TextView pressTextView;
    private TextView languageTextView;
    private TextView collectionPointTextView;
    private TextView descriptionTextView;
    private Button btnDepositBook;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_book_detail);

        // Retrofit
        RetrofitService retrofitService = new RetrofitService();
        BookApi bookApi = retrofitService.getRetrofit().create(BookApi.class);

        sharedPreferences = getSharedPreferences("Login Credentials", Context.MODE_PRIVATE);

        String jwtToken = sharedPreferences.getString("jwtToken", "");
        String authorizationHeader = "Bearer " + jwtToken;



        titleTextView = findViewById(R.id.book_title_donate_book_detail);
        authorTextView = findViewById(R.id.book_author_need_to_add_donate_book_detail);
        isbnTextView = findViewById(R.id.isbn_need_to_add_book_detail);
        conditionTextView = findViewById(R.id.book_condition_need_to_add_donate_book_detail);
        genreTextView = findViewById(R.id.book_genre_need_to_add_donate_book_detail);
        pressTextView = findViewById(R.id.book_press_need_to_add_donate_book_detail);
        languageTextView = findViewById(R.id.book_language_need_to_add_donate_book_detail);
        collectionPointTextView = findViewById(R.id.book_collection_point_need_to_add_donate_book_detail);
        descriptionTextView = findViewById(R.id.book_description_need_to_add_donate_book_detail);

        Intent intent = getIntent();
        Book selectedBook = (Book) intent.getSerializableExtra("selectedBook");
        if (selectedBook != null) {

            titleTextView.setText(selectedBook.getTitle());
            authorTextView.setText(selectedBook.getAuthor());
            isbnTextView.setText(selectedBook.getIsbn());

            switch (selectedBook.getBookCondition()){
                case 0:
                    conditionTextView.setText("Brand New");
                    break;
                case 1:
                    conditionTextView.setText("Like New");
                    break;
                case 2:
                    conditionTextView.setText("Lightly Used");
                    break;
                case 3:
                    conditionTextView.setText("Well Used");
                    break;
                default:
                    conditionTextView.setText("NA");
                    break;
            }

            genreTextView.setText(selectedBook.getGenre());
            pressTextView.setText(selectedBook.getPress());

            switch (selectedBook.getLanguage()){
                case 0:
                    languageTextView.setText("English");
                    break;
                case 1:
                    languageTextView.setText("Chinese");
                    break;
                case 2:
                    languageTextView.setText("Malay");
                    break;
                case 3:
                    languageTextView.setText("Tamil");
                    break;
                case 4:
                    languageTextView.setText("Japanese");
                    break;
                case 5:
                    languageTextView.setText("French");
                    break;
                case 6:
                    languageTextView.setText("German");
                    break;
                default:
                    languageTextView.setText("NA");
                    break;
            }

            collectionPointTextView.setText(selectedBook.getCollectionPoint().getName());
            descriptionTextView.setText(selectedBook.getDescription());

        } else {
            // Handle the case where no book object is found
            Toast.makeText(this, "No book information available.", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity
        }

        bookCover = findViewById(R.id.book_cover_background_donate_book_detail);
        bookCover.setImageBitmap(null);
        String coverImageUrl = selectedBook.getCover();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(coverImageUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("GET");
                    if (conn.getResponseCode() == 200) {
                        InputStream inputStream = conn.getInputStream();
                        final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 在主线程上更新UI元素
                                bookCover.setImageBitmap(bitmap);
                            }
                        });

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


        btnDepositBook = findViewById(R.id.button_deposit_donate_book);
        btnDepositBook.setOnClickListener(view -> {

            Long selectedBookId = selectedBook.getId();

            selectedBook.setStatus(1);


            bookApi.updateBookById(selectedBookId, selectedBook, authorizationHeader)
                    .enqueue(new Callback<Book>() {
                        @Override
                        public void onResponse(Call<Book> call, Response<Book> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(DonateBookDetailActivity.this, "Update Book Successful!", Toast.LENGTH_SHORT).show();

                                Intent intent1 = new Intent(DonateBookDetailActivity.this, ProfileActivity.class);
                                startActivity(intent1);
                                finish();
                            }
                            else {
                                try {
                                    Toast.makeText(DonateBookDetailActivity.this, "Failed to Update Book: "
                                                    + response.message() + response.errorBody().string(), Toast.LENGTH_SHORT)
                                            .show();
                                    System.out.println(response.body());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Book> call, Throwable t) {
                            Toast.makeText(DonateBookDetailActivity.this, "Update Book Response Failed!", Toast.LENGTH_SHORT)
                                    .show();
                            t.printStackTrace(); // Print the full stack trace to see the detailed error
                        }
                    });
        });
    }
}