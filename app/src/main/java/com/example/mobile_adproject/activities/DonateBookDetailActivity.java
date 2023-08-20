package com.example.mobile_adproject.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mobile_adproject.R;
import com.example.mobile_adproject.models.Book;
import com.example.mobile_adproject.models.CollectionPoint;
import com.example.mobile_adproject.models.Donor;
import com.example.mobile_adproject.retrofit.BookApi;
import com.example.mobile_adproject.retrofit.CollectionPointApi;
import com.example.mobile_adproject.retrofit.ImageApi;
import com.example.mobile_adproject.retrofit.ImageServer;
import com.example.mobile_adproject.retrofit.RetrofitService;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonateBookDetailActivity extends AppCompatActivity {

    private int REQUEST_CAMERA = 110;
    private static final int CAMERA_PERMISSION_CODE = 1001;
    private static final int STORAGE_PERMISSION_CODE = 1002;
    private int REQUEST_PICKER = 111;
    private File file = null;
    String coverString;
    Bitmap coverImageBitmap;
    private ImageView bookCover;
    private EditText titleEditText;
    private EditText authorEditText;
    private EditText isbnEditText;
    private Spinner conditionSpinner;
    private int bookCondition;
    private EditText genreEditText;
    private EditText pressEditText;
    private Spinner languageSpinner;
    private int bookLanguage;
    private Spinner collectionPointSpinner;
    private List<CollectionPoint> collectionPoints;
    private CollectionPoint selectedCollectionPoint;
    private EditText descriptionEditText;
    private Button btnUpdateBook;
    List<String> collectionPointNames;
    SharedPreferences sharedPreferences;
    BookApi bookApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_book_detail);
        init();

        // Retrofit
        RetrofitService retrofitService = new RetrofitService();
        bookApi = retrofitService.getRetrofit().create(BookApi.class);

        sharedPreferences = getSharedPreferences("Login Credentials", Context.MODE_PRIVATE);

        String jwtToken = sharedPreferences.getString("jwtToken", "");
        String authorizationHeader = "Bearer " + jwtToken;



        titleEditText = findViewById(R.id.book_title_donate_book_detail);
        authorEditText = findViewById(R.id.book_author_need_to_add_donate_book_detail);
        isbnEditText = findViewById(R.id.isbn_need_to_add_book_detail);
        conditionSpinner = findViewById(R.id.book_condition_need_to_add_donate_book_detail);
        genreEditText = findViewById(R.id.book_genre_need_to_add_donate_book_detail);
        pressEditText = findViewById(R.id.book_press_need_to_add_donate_book_detail);
        languageSpinner = findViewById(R.id.book_language_need_to_add_donate_book_detail);
        collectionPointSpinner = findViewById(R.id.book_collection_point_need_to_add_donate_book_detail);
        descriptionEditText = findViewById(R.id.book_description_need_to_add_donate_book_detail);

        Intent intent = getIntent();
        Book selectedBook = (Book) intent.getSerializableExtra("selectedBook");
        if (selectedBook != null) {

            titleEditText.setText(selectedBook.getTitle());
            authorEditText.setText(selectedBook.getAuthor());
            isbnEditText.setText(selectedBook.getIsbn());

            /*** Spinner for Book Conditions ***/
            ArrayAdapter<CharSequence> adapterBookConditions = ArrayAdapter.createFromResource(this,
                    R.array.book_conditions, android.R.layout.simple_spinner_item);
            adapterBookConditions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            conditionSpinner.setAdapter(adapterBookConditions);
            conditionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // Handle the selected item
                    String selectedBookCondition = parent.getItemAtPosition(position).toString();
                    bookCondition = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Handle case where nothing is selected
                    Toast.makeText(DonateBookDetailActivity.this, "Please select a Condition", Toast.LENGTH_SHORT).show();
                }
            });
            // 在获取到已有书籍信息后，设置 Spinner 的选中项
            conditionSpinner.setSelection(selectedBook.getBookCondition());

            /*** Spinner for Book Conditions ***/

            genreEditText.setText(selectedBook.getGenre());
            pressEditText.setText(selectedBook.getPress());

            /*** Spinner for Book Languages ***/
            ArrayAdapter<CharSequence> adapterBookLanguages = ArrayAdapter.createFromResource(this, R.array.book_languages,
                    android.R.layout.simple_spinner_item);
            adapterBookLanguages.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            languageSpinner.setAdapter(adapterBookLanguages);
            languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // Handle the selected item
                    String selectedBookLanguage = parent.getItemAtPosition(position).toString();
                    bookLanguage = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Handle case where nothing is selected
                    Toast.makeText(DonateBookDetailActivity.this, "Please select a Language", Toast.LENGTH_SHORT).show();
                }
            });
            languageSpinner.setSelection(selectedBook.getLanguage());
            /*** Spinner for Book Languages ***/



            CollectionPointApi collectionPointApi = retrofitService.getRetrofit().create(CollectionPointApi.class);
            collectionPointApi.getAllCollectionPoints(authorizationHeader)
                    .enqueue(new Callback<List<CollectionPoint>>() {
                        @Override
                        public void onResponse(Call<List<CollectionPoint>> call, Response<List<CollectionPoint>> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(DonateBookDetailActivity.this, "Get All Collection Points Successful!", Toast.LENGTH_SHORT)
                                        .show();
                                collectionPoints = response.body();
                                collectionPointNames = new ArrayList<>();
                                for (CollectionPoint collectionPoint : collectionPoints){
                                    String name = collectionPoint.getName();
                                    collectionPointNames.add(name);
                                }
                             /*   System.out.println(selectedBook.getCollectionPoint().getName());
                                System.out.println(collectionPointNames.get(1));
                                System.out.println(Objects.equals(selectedBook.getCollectionPoint().getName(),collectionPointNames.get(1)));*/


                                // Create an ArrayAdapter using custom spinner item layout and the list of collection points
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(DonateBookDetailActivity.this, R.layout.spinner_collection_point_item, collectionPointNames);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                // Find the Spinner view and set the adapter

                                collectionPointSpinner.setAdapter(adapter);

                                for(int i=0;i<collectionPointNames.size();i++){
                                    if(selectedBook.getCollectionPoint().getName().equals(collectionPointNames.get(i))){
                                        collectionPointSpinner.setSelection(i);

                                       /* System.out.println(collectionPointNames.get(i));
                                        System.out.println("1");*/
                                        break;
                                    }
                                }

                                // Handle spinner item selection
                                collectionPointSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                                        // Get the selected collection point name
                                        String selectedName = collectionPointNames.get(position);

                                        // Find the corresponding CollectionPoint object
                                        for (CollectionPoint collectionPoint : collectionPoints) {
                                            if (collectionPoint.getName().equals(selectedName)) {
                                                selectedCollectionPoint = collectionPoint;
                                                break;
                                            }
                                        }
                                    }


                                    @Override
                                    public void onNothingSelected(AdapterView<?> parentView) {
                                        // Handle case where no item is selected
                                        Toast.makeText(DonateBookDetailActivity.this, "Please select a Collection Point", Toast.LENGTH_SHORT).show();
                                    }
                                });


                            }
                            else {
                                try {
                                    Toast.makeText(DonateBookDetailActivity.this, "Failed to Get Collection Points: "
                                                    + response.message() + response.errorBody().string(), Toast.LENGTH_SHORT)
                                            .show();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }


                        @Override
                        public void onFailure(Call<List<CollectionPoint>> call, Throwable t) {

                        }
                    });

            descriptionEditText.setText(selectedBook.getDescription());

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

        } else {
            // Handle the case where no book object is found
            Toast.makeText(this, "No book information available.", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity
        }


        btnUpdateBook = findViewById(R.id.button_update_donate_book);
        btnUpdateBook.setOnClickListener(view -> {
            Long loggedInMemberId = sharedPreferences.getLong("memberId", 0);

            System.out.println("Token " + jwtToken);

            String isbn = String.valueOf(isbnEditText.getText());
            String title = String.valueOf(titleEditText.getText());
            String author = String.valueOf(authorEditText.getText());
            String genre = String.valueOf(genreEditText.getText());
            String press = String.valueOf(pressEditText.getText());
            String description = String.valueOf(descriptionEditText.getText());

            Donor donor = new Donor();
            donor.setId(loggedInMemberId);

            selectedBook.setIsbn(isbn);
            selectedBook.setTitle(title);
            selectedBook.setAuthor(author);
            if(coverString==null){
                selectedBook.setCover(selectedBook.getCover());
            }else{
                selectedBook.setCover(coverString);
            }

            selectedBook.setBookCondition(bookCondition);
            selectedBook.setGenre(genre);
            selectedBook.setPress(press);
            selectedBook.setLanguage(bookLanguage);
            selectedBook.setStatus(0);
            selectedBook.setDescription(description);
            selectedBook.setDonor(donor);
            selectedBook.setCollectionPoint(selectedCollectionPoint);

            bookApi.updateBookById(selectedBook.getId(), selectedBook, authorizationHeader)
                    .enqueue(new Callback<Book>() {
                        @Override
                        public void onResponse(Call<Book> call, Response<Book> response) {
                            Toast.makeText(DonateBookDetailActivity.this, "Update Success!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DonateBookDetailActivity.this, ProfileActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<Book> call, Throwable t) {
                            Toast.makeText(DonateBookDetailActivity.this, "Update Failed!", Toast.LENGTH_SHORT).show();

                        }
                    });
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                Bundle extras = data.getExtras();
                coverImageBitmap = (Bitmap) extras.get("data");
                bookCover.setImageBitmap(coverImageBitmap);

            } else if (requestCode == REQUEST_PICKER) {
                Uri selectedImageUri = data.getData();
                try {
                    // 将选中的图片 Uri 转换为 Bitmap
                    coverImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    // 将 Bitmap 设置给 ImageView
                    bookCover.setImageBitmap(coverImageBitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            coverImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            RequestBody imageBody = RequestBody.create(MediaType.parse("image/jpeg"),
                    byteArrayOutputStream.toByteArray());
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", "image.jpg", imageBody);

            ImageServer imageServer = new ImageServer();
            ImageApi imageApi = imageServer.getImageServerRetrofit().create(ImageApi.class);
            imageApi.uploadImage(imagePart)
                    .enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(DonateBookDetailActivity.this, "Upload Book Cover Successful!",
                                        Toast.LENGTH_SHORT).show();
                                coverString = response.body();
                            } else {
                                try {
                                    Toast.makeText(DonateBookDetailActivity.this, "Upload Book Cover Failed: "
                                                    + response.message() + response.errorBody().string(), Toast.LENGTH_SHORT)
                                            .show();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(DonateBookDetailActivity.this, "Upload Cover Response Failed!", Toast.LENGTH_SHORT)
                                    .show();
                            t.printStackTrace(); // Print the full stack trace to see the detailed error
                        }
                    });

        }
    }

    protected void init() {
        String[] mode = { "Camera", "Pick", "Cancel" };
        ListView listView = new ListView(this);
        listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mode));
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(listView);
        bookCover = findViewById(R.id.book_cover_background_donate_book_detail);
        bookCover.setOnClickListener(new View.OnClickListener() {
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

                        if (ContextCompat.checkSelfPermission(DonateBookDetailActivity.this,
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(DonateBookDetailActivity.this,
                                    new String[] { Manifest.permission.CAMERA }, CAMERA_PERMISSION_CODE);
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
                        dialog.dismiss();
                        break;
                    case 1:
                        if (ContextCompat.checkSelfPermission(DonateBookDetailActivity.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(DonateBookDetailActivity.this,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                    STORAGE_PERMISSION_CODE);
                        }
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        galleryIntent.setType("image/*");
                        if (galleryIntent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(galleryIntent, REQUEST_PICKER);
                        }
                        dialog.dismiss();
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