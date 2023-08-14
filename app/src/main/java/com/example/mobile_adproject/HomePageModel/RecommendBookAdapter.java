package com.example.mobile_adproject.HomePageModel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_adproject.R;
import com.example.mobile_adproject.activities.BookDetailActivity;
import com.example.mobile_adproject.models.Book;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class RecommendBookAdapter extends RecyclerView.Adapter<RecommendBookAdapter.RecommendBookViewHolder> {
    Context context;
    List<Book> recommendBookList;

    public RecommendBookAdapter(Context context, List<Book> recommendBookList) {
        this.context = context;
        this.recommendBookList = recommendBookList;
    }

    @NonNull
    @Override
    public RecommendBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recommend_books_row_item,parent,false);

        return new RecommendBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendBookViewHolder holder, int position) {

        holder.bookTitle.setText(recommendBookList.get(position).getTitle());
        holder.bookAuthor.setText(recommendBookList.get(position).getAuthor());
        String coverImageUrl=recommendBookList.get(position).getCover();
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

                        // 在 UI 线程中更新 ImageView
                        holder.itemView.post(new Runnable() {
                            @Override
                            public void run() {
                                holder.bookCover.setImageBitmap(bitmap);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, BookDetailActivity.class);
                context.startActivity(intent);
            }
        });





    }

    @Override
    public int getItemCount() {
        return recommendBookList.size();
    }

    public static  final class RecommendBookViewHolder extends RecyclerView.ViewHolder{

        ImageView bookCover;
        TextView bookTitle, bookAuthor;

        public RecommendBookViewHolder(@NonNull View itemView) {
            super(itemView);

            bookCover = itemView.findViewById(R.id.book_cover);
            bookTitle = itemView.findViewById(R.id.book_title);
            bookAuthor = itemView.findViewById(R.id.book_author);


        }
    }
    // 将 Base64 字符串转换为 Bitmap
    public Bitmap base64ToBitmap(String base64String) {
        try {
            byte[] decodedByteArray = Base64.decode(base64String, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null; // 或者返回一个默认的 Bitmap，以防解码失败
        }
    }

}
