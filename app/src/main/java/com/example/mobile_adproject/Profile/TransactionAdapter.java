package com.example.mobile_adproject.Profile;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.mobile_adproject.models.Application;
import com.example.mobile_adproject.models.Book;
import com.example.mobile_adproject.models.TransactionHistoryData;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>{

    Context context;
    List<Application> transactionHistoryData;

    public TransactionAdapter(Context context, List<Application> transactionHistoryData) {
        this.context = context;
        this.transactionHistoryData = transactionHistoryData;
    }



    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.transaction_history_item,parent,false);

        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        holder.bookCover.setImageBitmap(null);

        String coverImageUrl = transactionHistoryData.get(position).getBook().getCover();
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
        holder.bookTitle.setText(transactionHistoryData.get(position).getBook().getTitle());
        holder.bookauthor.setText(transactionHistoryData.get(position).getBook().getAuthor());
        holder.bookdonor.setText(transactionHistoryData.get(position).getBook().getDonor().getUsername());
        holder.bookRecipient.setText(transactionHistoryData.get(position).getRecipient().getUsername());

    }



    @Override
    public int getItemCount() {
        return transactionHistoryData.size();
    }




    public class TransactionViewHolder extends RecyclerView.ViewHolder{
        ImageView bookCover;
        TextView bookTitle,bookauthor,bookdonor,bookRecipient;
        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            bookCover=itemView.findViewById(R.id.book_cover);
            bookTitle=itemView.findViewById(R.id.book_title);
            bookauthor=itemView.findViewById(R.id.book_author_need_add_transaction);
            bookdonor=itemView.findViewById(R.id.book_donor_need_add_transaction);
            bookRecipient=itemView.findViewById(R.id.book_recipient_need_add_transaction);


        }

    }

}




