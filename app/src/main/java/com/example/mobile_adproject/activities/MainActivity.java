package com.example.mobile_adproject.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_adproject.HomePageModel.RecommendBookAdapter;
import com.example.mobile_adproject.R;
import com.example.mobile_adproject.models.Book;
import com.example.mobile_adproject.retrofit.BookApi;
import com.example.mobile_adproject.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recommendRecycler;
    RecommendBookAdapter recommendBookAdapter;
    ImageView user_profile;
    ImageView home;
    ImageView message;
    ImageView donate;
    ImageView profile;
    Button btnLogout;
    SharedPreferences sharedPreferences;
    EditText searchbook;

    String authorizationHeader;

    Long memberId;
    RetrofitService retrofitService = new RetrofitService();
    BookApi bookApi = retrofitService.getRetrofit().create(BookApi.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogout = findViewById(R.id.btn_logout);


        searchbook=findViewById(R.id.search_books);
        searchbook.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // When the user types, perform search
                performSearch(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Not needed for this example
            }




        });


        sharedPreferences = getSharedPreferences("Login Credentials", Context.MODE_PRIVATE);

        String jwtToken = sharedPreferences.getString("jwtToken","");
        memberId = sharedPreferences.getLong("memberId", 0);

        if(jwtToken.isEmpty()){
            btnLogout.setVisibility(View.GONE);

            bookApi.randomBook()
                    .enqueue(new Callback<List<Book>>() {
                        @Override
                        public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Get Random Successful!", Toast.LENGTH_SHORT).show();
                                setRecommendRecycler(response.body());
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Failed to Get Books: " + response.message(), Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<List<Book>> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Get Random Response Failed!", Toast.LENGTH_SHORT).show();
                            t.printStackTrace(); // Print the full stack trace to see the detailed error
                        }
                    });
        }

        authorizationHeader = "Bearer " + jwtToken;
        bookApi.getAllBooks(authorizationHeader)
                        .enqueue(new Callback<List<Book>>() {
                            @Override
                            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                                if(response.isSuccessful()){
                                    Toast.makeText(MainActivity.this, "Get All Books Successful!", Toast.LENGTH_SHORT).show();
                                    setRecommendRecycler(response.body());
                                }
                                else {
                                    Toast.makeText(MainActivity.this, "Failed to Get Books: " + response.message(), Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<List<Book>> call, Throwable t) {
                                Toast.makeText(MainActivity.this, "Get All Books Response Failed!", Toast.LENGTH_SHORT).show();
                                t.printStackTrace(); // Print the full stack trace to see the detailed error

                            }
                        });



        btnLogout.setOnClickListener(view -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            Toast.makeText(this, "Logged out!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        });


        user_profile=findViewById(R.id.your_account);
        user_profile.setEnabled(jwtToken.isEmpty()); // Disable if jwtToken is not empty
        user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(jwtToken.isEmpty()){
                    Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        home=findViewById(R.id.home_action_bottom);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });



        donate=findViewById(R.id.donate_action_bottom);
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(jwtToken.isEmpty()){
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent=new Intent(MainActivity.this,DonateBookActivity.class);
                    startActivity(intent);
                }
            }
        });

        profile=findViewById(R.id.profile_action_bottom);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(jwtToken.isEmpty()){
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void performSearch(String search) {
        bookApi.search(search,authorizationHeader)
                .enqueue(new Callback<List<Book>>() {
                    @Override
                    public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Get Search All Book Successful!", Toast.LENGTH_SHORT).show();
                            setRecommendRecycler(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Book>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Get Search Book Failed: " , Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private  void setRecommendRecycler(List<Book> recommendBookList){

        recommendRecycler = findViewById(R.id.recommend_book_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recommendRecycler.setLayoutManager(layoutManager);
        recommendBookAdapter = new RecommendBookAdapter(this, recommendBookList);
        recommendRecycler.setAdapter(recommendBookAdapter);

    }
}