package com.example.mobile_adproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_adproject.R;

public class BookDetailActivity extends AppCompatActivity {

    Button btnRequest;

    ImageView imgBookDetail;
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

    }
}