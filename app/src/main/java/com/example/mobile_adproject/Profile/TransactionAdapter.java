package com.example.mobile_adproject.Profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_adproject.R;
import com.example.mobile_adproject.models.Book;
import com.example.mobile_adproject.models.TransactionHistoryData;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>{

    Context context;
    List<Book> transactionHistoryData;
    String memberName;

    public TransactionAdapter(Context context, List<Book> transactionHistoryData,String memberName) {
        this.context = context;
        this.transactionHistoryData = transactionHistoryData;
        this.memberName=memberName;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.transaction_history_item,parent,false);

        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        holder.bookCover.setImageResource(R.drawable.ic_baseline_link_off_24);
        holder.bookTitle.setText(transactionHistoryData.get(position).getTitle());
        holder.bookauthor.setText(transactionHistoryData.get(position).getAuthor());
        holder.bookdonor.setText(transactionHistoryData.get(position).getDonor().getUsername());
        holder.bookRecipient.setText(memberName);

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
            bookauthor=itemView.findViewById(R.id.book_author_need_add_donate);
            bookdonor=itemView.findViewById(R.id.book_donor_need_add_transaction);
            bookRecipient=itemView.findViewById(R.id.book_recipient_need_add_transaction);


        }

    }

}




