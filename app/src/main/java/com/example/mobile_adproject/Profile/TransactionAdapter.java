package com.example.mobile_adproject.Profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_adproject.R;
import com.example.mobile_adproject.models.TransactionHistoryData;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>{

    Context context;
    List<TransactionHistoryData> transactionHistoryData;

    public TransactionAdapter(Context context, List<TransactionHistoryData> transactionHistoryData) {
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


    }

    @Override
    public int getItemCount() {
        return transactionHistoryData.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder{

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}




