package com.example.mobile_adproject.Profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_adproject.R;
import com.example.mobile_adproject.activities.DonateBookDetailActivity;
import com.example.mobile_adproject.models.Book;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DonateBookAdapter extends RecyclerView.Adapter<DonateBookAdapter.DonateViewHolder>{
    Context context;
    private List<Book> donatedBookList;

    public DonateBookAdapter(Context context, List<Book> donatedBookList) {
        this.context = context;
        this.donatedBookList = donatedBookList;
    }

    @NonNull
    @Override
    public DonateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.donate_book_list_item,parent,false);

        return new DonateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonateViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.bookTitle.setText(donatedBookList.get(position).getTitle());
        holder.bookAuthor.setText(donatedBookList.get(position).getAuthor());
        // 设置占位图或者清空图片
        holder.bookCover.setImageBitmap(null);

        String coverImageUrl = donatedBookList.get(position).getCover();
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
        switch (donatedBookList.get(position).getStatus()){
            case 0:
                holder.bookStatus.setText("Pending");
                break;
            case 1:
                holder.bookStatus.setText("Deposited");
                break;
            case 2:
                holder.bookStatus.setText("Available");
                break;
            case 3:
                holder.bookStatus.setText("Reserved");
                break;
//            case 4:
//                holder.bookStatus.setText("Unavailable");
//                break;
            case 5:
                holder.bookStatus.setText("Rejected");
                break;
            default:
                holder.bookStatus.setText("NA");
                break;
        }

        holder.bookCover.setImageResource(R.drawable.ic_baseline_link_off_24);

        int status = donatedBookList.get(position).getStatus();

        if(status == 4 || status == 6){
            // Hide the item
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
        else {
            holder.itemView.setVisibility(View.VISIBLE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            // Check if the status is not Pending
            if (donatedBookList.get(position).getStatus() != 0) {
                // Disable click action for items except Pending status
                holder.itemView.setOnClickListener(null);
                holder.txtContinue.setVisibility(View.GONE);
            } else {
                // Enable click action for other items
                holder.itemView.setOnClickListener(view -> {
                    Book selectedBook = donatedBookList.get(position); // 获取选定的书籍对象

                    Intent intent = new Intent(context, DonateBookDetailActivity.class);
                    intent.putExtra("selectedBook",  selectedBook); // 将选定的书籍对象放入Intent中

                    context.startActivity(intent);
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return donatedBookList.size();
    }

    public class DonateViewHolder extends RecyclerView.ViewHolder{

        ImageView bookCover;
        TextView bookTitle, bookAuthor, bookStatus, txtContinue;
        public DonateViewHolder(@NonNull View itemView) {
            super(itemView);

            bookCover = itemView.findViewById(R.id.book_cover);
            bookTitle = itemView.findViewById(R.id.book_title_profile);
            bookAuthor = itemView.findViewById(R.id.book_author_need_add_donate);
            bookStatus = itemView.findViewById(R.id.status_donate_book_need_add);
            txtContinue = itemView.findViewById(R.id.contiguous_donate_book);

        }
    }
}
