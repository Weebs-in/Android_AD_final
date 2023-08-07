package com.example.mobile_adproject;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.bumptech.glide.Glide;

public class DonateBookActivity extends AppCompatActivity {
    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HHmmss", Locale.CHINA);

    private int REQUEST_CAMERA=110;
    private int REQUEST_PICKER=111;
    private File file=null;
    ImageView cover;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_book);
        init();
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
