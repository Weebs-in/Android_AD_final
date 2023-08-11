package com.example.mobile_adproject.activities;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import androidx.core.content.FileProvider;

import com.example.mobile_adproject.R;
import com.example.mobile_adproject.models.Book;
import com.example.mobile_adproject.models.Donor;
import com.example.mobile_adproject.retrofit.BookApi;
import com.example.mobile_adproject.retrofit.RetrofitService;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonateBookActivity extends AppCompatActivity {
    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HHmmss", Locale.CHINA);

    private int REQUEST_CAMERA=110;
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
                                Book responseBook = response.body();
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
                Glide.with(this).load(file).into(cover);
            }else if(requestCode==REQUEST_PICKER){
                try{
                    Uri data1 = data.getData();
                    ContentResolver contentResolver = getContentResolver();
                    String[] colum={MediaStore.Images.ImageColumns.DATA};
                    Cursor query = contentResolver.query(data1, colum, null, null, null);
                    query.moveToNext();
                    int columnIndex = query.getColumnIndex(colum[0]);
                    String string = query.getString(columnIndex);
                    Glide.with(this).load(string).into(cover);
                }catch (NullPointerException e){
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
                        // 处理Camera的点击事件
                        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        file = new File(getExternalCacheDir(), simpleDateFormat.format(new Date()) + ".png");
                        Uri uriForFile = FileProvider.getUriForFile(DonateBookActivity.this, "com.example.mobile_adproject.fileProvider", file);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,uriForFile);
                        startActivityForResult(intent,REQUEST_CAMERA);
                        break;
                    case 1:
                        // 处理Pick的点击事件
                        Intent intent1 = new Intent(Intent.ACTION_PICK);
                        intent1.setType("image/*");
                        startActivityForResult(intent1,REQUEST_PICKER);
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
