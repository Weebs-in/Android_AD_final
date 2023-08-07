package com.example.mobile_adproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_adproject.HomePageModel.RecommendBook;
import com.example.mobile_adproject.HomePageModel.RecommendBookAdapter;
import com.example.mobile_adproject.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recommendRecycler;
    RecommendBookAdapter recommendBookAdapter;


    ImageView user_profile;
    ImageView home;
    ImageView message;
    ImageView donate;
    ImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //add some data just for testing
       /* List<RecommendBook> recommendBookList=new ArrayList<>();
        recommendBookList.add(new RecommendBook(1L,1,"Harry Potter and the Order of the Phoenix","J. K. Rowling",
                "http",
                1,"1","1","1",1,1,1L,time1,time2));
        recommendBookList.add(new RecommendBook(2L,1,"Harry Potter and the Order of the Phoenix","J. K. Rowling",
                "http",
                1,"1","1","1",1,1,1L,time1,time2));
        recommendBookList.add(new RecommendBook(3L,1,"Harry Potter and the Order of the Phoenix","J. K. Rowling",
                "http",
                1,"1","1","1",1,1,1L,time1,time2));
        recommendBookList.add(new RecommendBook(3L,1,"Harry Potter and the Order of the Phoenix","J. K. Rowling",
                "http",
                1,"1","1","1",1,1,1L,time1,time2));
        recommendBookList.add(new RecommendBook(3L,1,"Harry Potter and the Order of the Phoenix","J. K. Rowling",
                "http",
                1,"1","1","1",1,1,1L,time1,time2));
        setRecommendRecycler(recommendBookList);*/

        List<RecommendBook> recommendBookList=new ArrayList<>();
        recommendBookList.add(new RecommendBook());
        recommendBookList.add(new RecommendBook());
        recommendBookList.add(new RecommendBook());
        recommendBookList.add(new RecommendBook());
        recommendBookList.add(new RecommendBook());
        recommendBookList.add(new RecommendBook());
        recommendBookList.add(new RecommendBook());
        recommendBookList.add(new RecommendBook());
        recommendBookList.add(new RecommendBook());
        recommendBookList.add(new RecommendBook());
        recommendBookList.add(new RecommendBook());
        recommendBookList.add(new RecommendBook());
        recommendBookList.add(new RecommendBook());
        recommendBookList.add(new RecommendBook());

        setRecommendRecycler(recommendBookList);

        user_profile=findViewById(R.id.your_account);
        user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
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

        message=findViewById(R.id.message_action_bottom);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,MessageActivity.class);
                startActivity(intent);
            }
        });

        donate=findViewById(R.id.donate_action_bottom);
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,DonateBookActivity.class);
                startActivity(intent);
            }
        });

        profile=findViewById(R.id.profile_action_bottom);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

    }
    private  void setRecommendRecycler(List<RecommendBook> recommendBookList){

        recommendRecycler = findViewById(R.id.recommend_book_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recommendRecycler.setLayoutManager(layoutManager);
        recommendBookAdapter = new RecommendBookAdapter(this, recommendBookList);
        recommendRecycler.setAdapter(recommendBookAdapter);

    }
}