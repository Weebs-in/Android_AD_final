package com.example.mobile_adproject.activities;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.mobile_adproject.R;
import com.example.mobile_adproject.models.Book;
import com.example.mobile_adproject.models.Donor;
import com.example.mobile_adproject.retrofit.BookApi;
import com.example.mobile_adproject.retrofit.RetrofitService;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonateBookActivity extends AppCompatActivity {
    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HHmmss", Locale.CHINA);

    private int REQUEST_CAMERA=110;
    private static final int CAMERA_PERMISSION_CODE = 1001;
    private static final int STORAGE_PERMISSION_CODE = 1002;
    private int REQUEST_PICKER=111;
    private File file=null;
    ImageView cover;
    Button btnDonate;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_book);
        init();

        RetrofitService retrofitService = new RetrofitService();
        BookApi bookApi = retrofitService.getRetrofit().create(BookApi.class);

        btnDonate = findViewById(R.id.btnDonateBook);
        btnDonate.setOnClickListener(view -> {

            sharedPreferences = getSharedPreferences("Login Credentials", Context.MODE_PRIVATE);

            String jwtToken = sharedPreferences.getString("jwtToken","");
            Long loggedInMemberId = sharedPreferences.getLong("memberId",0);

            System.out.println("Token " + jwtToken );

            Donor donor = new Donor();
            donor.setId(loggedInMemberId);

            Book book = new Book();
            book.setIsbn(123456);
            book.setTitle("Title");
            book.setAuthor("Author");
            book.setCover("Cover");
            book.setBookCondition(0);
            book.setDescription("Description");
            book.setGenre("Genre");
            book.setPress("Press");
            book.setLanguage(0);
            book.setStatus(0);
            book.setLikeCount(1);
            book.setDonor(donor);

            String authorizationHeader = "Bearer " + jwtToken;

            bookApi.createBook(book, authorizationHeader)
                    .enqueue(new Callback<Book>() {
                        @Override
                        public void onResponse(Call<Book> call, Response<Book> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(DonateBookActivity.this, "Create Book Successful!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(DonateBookActivity.this, ProfileActivity.class);
                                startActivity(intent);
                            }
                            else {
                                try {
                                    Toast.makeText(DonateBookActivity.this, "Failed to Create Book: " + response.message() + response.errorBody().string(), Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                        }
                        @Override
                        public void onFailure(Call<Book> call, Throwable t) {
                            Toast.makeText(DonateBookActivity.this, "Create Book Response Failed!", Toast.LENGTH_SHORT).show();
                            t.printStackTrace(); // Print the full stack trace to see the detailed error
                        }
                    });
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==REQUEST_CAMERA){
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                cover.setImageBitmap(imageBitmap);
            }else if(requestCode==REQUEST_PICKER){
                Uri selectedImageUri = data.getData();
                try {
                    // 将选中的图片 Uri 转换为 Bitmap
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    // 将 Bitmap 设置给 ImageView
                    cover.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    protected void init(){
        String[] mode={"Camera","Pick","Cancel"};
        ListView listView=new ListView(this);
        listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,mode));
        BottomSheetDialog dialog=new BottomSheetDialog(this);
        dialog.setContentView(listView);
        cover=findViewById(R.id.book_cover_background_book_donate);
        cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:

                        if (ContextCompat.checkSelfPermission(DonateBookActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(DonateBookActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                        }
                            // 启动相机应用
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (intent.resolveActivity(getPackageManager()) != null) {
                                try {
                                    startActivityForResult(intent, REQUEST_CAMERA);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        break;
                    case 1:
                        if (ContextCompat.checkSelfPermission(DonateBookActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(DonateBookActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        galleryIntent.setType("image/*");
                        if (galleryIntent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(galleryIntent, REQUEST_PICKER);
                        }
                        break;
                    case 2:
                        // 处理Cancel的点击事件
                        dialog.dismiss(); // 关闭BottomSheetDialog
                        break;
                    default:
                        break;
                }
            }
        });

    }
}
