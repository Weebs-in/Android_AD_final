package com.example.mobile_adproject.Profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_adproject.R;

import java.util.List;

public class ApprovedBookAdapter  extends RecyclerView.Adapter<ApprovedBookAdapter.ApprovedBookViewHolder>{
    Context context;
    List<TransactionHistoryData> transactionHistoryData;


    public ApprovedBookAdapter(Context context, List<TransactionHistoryData> transactionHistoryData) {
        this.context = context;
        this.transactionHistoryData = transactionHistoryData;
    }

    @NonNull
    @Override
    public ApprovedBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.approved_book_list_item,parent,false);

        return new ApprovedBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApprovedBookViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return transactionHistoryData.size();
    }

    public class ApprovedBookViewHolder  extends RecyclerView.ViewHolder{
        public ApprovedBookViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
