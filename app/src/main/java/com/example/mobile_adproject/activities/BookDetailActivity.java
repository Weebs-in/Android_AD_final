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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_adproject.R;
import com.example.mobile_adproject.models.Application;
import com.example.mobile_adproject.models.Book;
import com.example.mobile_adproject.models.Donor;
import com.example.mobile_adproject.models.Member;
import com.example.mobile_adproject.retrofit.ApplicationApi;
import com.example.mobile_adproject.retrofit.BookApi;
import com.example.mobile_adproject.retrofit.RetrofitService;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetailActivity extends AppCompatActivity {

    boolean favouriteOrNot=false;
    Button btnRequest;

    ImageView bookCover;
    TextView bookTitle;
    TextView author;
    TextView ISBN;
    TextView condition;
    TextView genre;
    TextView press;
    TextView language;
    TextView status;
    TextView description;
    ImageView favourite;
    TextView favourite_number;
    SharedPreferences sharedPreferences;
    String jwtToken;
    String authorizationHeader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        bookCover=findViewById(R.id.book_cover_background_book_detail);
        bookTitle=findViewById(R.id.book_title_book_detail);
        author=findViewById(R.id.book_author_need_to_add_book_detail);
        ISBN=findViewById(R.id.isbn_need_to_add_book_detail);
        condition=findViewById(R.id.book_condition_need_to_add_book_detail);
        genre=findViewById(R.id.book_genre_need_to_add_book_detail);
        press=findViewById(R.id.book_press_need_to_add_book_detail);
        language=findViewById(R.id.book_language_need_to_add_book_detail);
        status=findViewById(R.id.book_status_need_to_add_book_detail);
        description=findViewById(R.id.book_description_need_to_add_book_detail);
        favourite_number=findViewById(R.id.book_detail_favourite_number);
        favourite=findViewById(R.id.book_detail_favourite);

        // 获取传递的书籍信息
        Intent intent = getIntent();
        if (intent != null) {
            Book selectedBook = (Book) intent.getSerializableExtra("selectedBook");

            String coverImageUrl=selectedBook.getCover();
            System.out.println(coverImageUrl);
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

           bookTitle.setText(selectedBook.getTitle());
           author.setText(selectedBook.getAuthor());
           ISBN.setText(selectedBook.getIsbn());
           switch (selectedBook.getBookCondition()){
                case 0:
                    condition.setText("Brand new");
                    break;
                case 1:
                    condition.setText("Like new");
                    break;
                case 2:
                    condition.setText("Lightly used");
                    break;
                case 3:
                    condition.setText("Well used");
                    break;
                case 4:
                   condition.setText("Heavily used");
                    break;

                default:
                    condition.setText("NA");
                    break;
            }
           genre.setText(selectedBook.getGenre());
           press.setText(selectedBook.getPress());
           switch (selectedBook.getLanguage()){
                case 0:
                    language.setText("English");
                    break;
                case 1:
                    language.setText("Chinese");
                    break;
                case 2:
                    language.setText("Malay");
                    break;
                case 3:
                    language.setText("Tamil");
                    break;
                case 4:
                    language.setText("Japanese");
                    break;
                case 5:
                    language.setText("French");
                    break;
               case 6:
                   language.setText("German");
                   break;
                default:
                    language.setText("NA");
                    break;
           }
           switch (selectedBook.getStatus()){
                case 0:
                    status.setText("Pending");
                    break;
                case 1:
                    status.setText("Deposited");
                    break;
                case 2:
                    status.setText("Available");
                    break;
                case 3:
                    status.setText("Reserved");
                    break;
                case 4:
                    status.setText("Unavailable");
                    break;
                case 5:
                    status.setText("Rejected");
                    break;
                default:
                    status.setText("NA");
                    break;
           }
           description.setText(selectedBook.getDescription());
            favourite_number.setText(String.valueOf(selectedBook.getLikeCount()));
            RetrofitService retrofitService = new RetrofitService();
            BookApi bookApi = retrofitService.getRetrofit().create(BookApi.class);
            sharedPreferences = getSharedPreferences("Login Credentials", Context.MODE_PRIVATE);
            jwtToken = sharedPreferences.getString("jwtToken", "");
            authorizationHeader = "Bearer " + jwtToken;
            favourite.setOnClickListener(view -> {
                if(favouriteOrNot==false){
                    favouriteOrNot=true;
                    favourite.setImageResource(R.drawable.ic_baseline_favorite_24);

                    try {
                        int favouriteNumber = selectedBook.getLikeCount();
                        favouriteNumber++;
                        favourite_number.setText(String.valueOf(favouriteNumber));
                        selectedBook.setLikeCount(favouriteNumber);



                        bookApi.updateLikeCount(selectedBook.getId(), authorizationHeader)
                                    .enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            if (response.isSuccessful()) {
                                                //Toast.makeText(BookDetailActivity.this, "save like count successful!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                try {
                                                    Toast.makeText(BookDetailActivity.this, "Failed to save like count: "
                                                            + response.message() + response.errorBody().string(), Toast.LENGTH_SHORT)
                                                            .show();
                                                } catch (IOException e) {
                                                    throw new RuntimeException(e);
                                                }
                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {
                                            Toast.makeText(BookDetailActivity.this, "Update Like Count Failed!", Toast.LENGTH_SHORT)
                                                    .show();
                                            t.printStackTrace(); // Print the full stack trace to see the detailed error
                                        }
                                    });
                    } catch (NumberFormatException e) {
                        // 处理转换失败的情况，例如文本不是一个有效的整数
                        e.printStackTrace();
                    }
                }else{
                    favouriteOrNot=false;
                    favourite.setImageResource(R.drawable.ic_baseline_favorite_border_24);

                    try {
                        int favouriteNumber = selectedBook.getLikeCount();
                        favouriteNumber--;
                        favourite_number.setText(String.valueOf(favouriteNumber));
                        selectedBook.setLikeCount(favouriteNumber);

                        System.out.println(selectedBook.getLikeCount());
                        bookApi.updateLikeCount(selectedBook.getId(), authorizationHeader)
                                .enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (response.isSuccessful()) {
                                            //Toast.makeText(BookDetailActivity.this, "save like count successful!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            try {
                                                Toast.makeText(BookDetailActivity.this, "Failed to save like count: "
                                                        + response.message() + response.errorBody().string(), Toast.LENGTH_SHORT)
                                                        .show();
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(BookDetailActivity.this, "Update Like Count Failed!", Toast.LENGTH_SHORT)
                                                .show();
                                        t.printStackTrace(); // Print the full stack trace to see the detailed error
                                    }
                                });
                    } catch (NumberFormatException e) {
                        // 处理转换失败的情况，例如文本不是一个有效的整数
                        e.printStackTrace();
                    }
                }
            });

            if(selectedBook.getStatus()==2){
                btnRequest = findViewById(R.id.button_request);
                btnRequest.setOnClickListener(view -> {
                    // Create and show the confirmation dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(BookDetailActivity.this);

                    builder.setView(R.layout.dialog_confirm_request);
                    AlertDialog dialog = builder.create();

                    // Show the dialog
                    dialog.show();

                    // Find the buttons in the dialog layout
                    Button confirmButton = dialog.findViewById(R.id.btn_confirm);
                    Button cancelButton = dialog.findViewById(R.id.btn_cancel);

                    ApplicationApi applicationApi = retrofitService.getRetrofit().create(ApplicationApi.class);
                    sharedPreferences = getSharedPreferences("Login Credentials", Context.MODE_PRIVATE);
                    jwtToken = sharedPreferences.getString("jwtToken", "");
                    authorizationHeader = "Bearer " + jwtToken;
                    // Set click listener for the "Confirm" button
                    confirmButton.setOnClickListener(v -> {
                        // Perform the request action here
                        // create application

                        Application application=new Application();
                        application.setBook(selectedBook);
                        Long loggedInMemberId = sharedPreferences.getLong("memberId", 0);
                        Member member=new Member();
                        member.setId(loggedInMemberId);
                        application.setRecipient(member);
                        application.setStatus(0);
                        applicationApi.createApplication(application, authorizationHeader)
                                .enqueue(new Callback<Application>() {
                                    @Override
                                    public void onResponse(Call<Application> call, Response<Application> response) {
                                        if(response.isSuccessful()){
                                           // Toast.makeText(BookDetailActivity.this, "Create Application Successful!", Toast.LENGTH_SHORT).show();

                                            Intent intent1 = new Intent(BookDetailActivity.this, ProfileActivity.class);
                                            startActivity(intent1);
                                            finish();
                                        }
                                        else {
                                            try {
                                                Toast.makeText(BookDetailActivity.this, "Failed to create applicayion: "
                                                        + response.message() + response.errorBody().string(), Toast.LENGTH_SHORT)
                                                        .show();
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Application> call, Throwable t) {
                                        Toast.makeText(BookDetailActivity.this, "Create Application Response Failed!", Toast.LENGTH_SHORT)
                                                .show();
                                        t.printStackTrace(); // Print the full stack trace to see the detailed error
                                    }
                                });

                        // Dismiss the dialog
                        dialog.dismiss();
                    });

                    // Set click listener for the "Cancel" button
                    cancelButton.setOnClickListener(v -> {
                        // Dismiss the dialog
                        dialog.dismiss();
                    });
                });
            }


        }



    }
}