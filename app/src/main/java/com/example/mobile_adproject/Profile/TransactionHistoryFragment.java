package com.example.mobile_adproject.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_adproject.R;
import com.example.mobile_adproject.models.TransactionHistoryData;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryFragment extends Fragment  {
    RecyclerView transactionRecycler;
    TransactionAdapter transactionAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle save){
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.transaction_history,container,false);

        transactionRecycler = root.findViewById(R.id.transaction_recycleView);
        List<TransactionHistoryData> transactionHistoryDataList=new ArrayList<>();
        transactionHistoryDataList.add(new TransactionHistoryData());
        transactionHistoryDataList.add(new TransactionHistoryData());
        transactionHistoryDataList.add(new TransactionHistoryData());
        transactionHistoryDataList.add(new TransactionHistoryData());
        transactionHistoryDataList.add(new TransactionHistoryData());
        transactionHistoryDataList.add(new TransactionHistoryData());
        transactionHistoryDataList.add(new TransactionHistoryData());
        transactionHistoryDataList.add(new TransactionHistoryData());
        transactionHistoryDataList.add(new TransactionHistoryData());
        transactionHistoryDataList.add(new TransactionHistoryData());
        transactionHistoryDataList.add(new TransactionHistoryData());
        setTransactionRecycler(transactionHistoryDataList);

        return root;

    }
    private  void setTransactionRecycler(List<TransactionHistoryData> transactionHistoryDataList){

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        transactionRecycler.setLayoutManager(layoutManager);
        transactionAdapter = new TransactionAdapter(requireContext(), transactionHistoryDataList);
        transactionRecycler.setAdapter(transactionAdapter);

       /* transactionRecycler = findViewById(R.id.transaction_recycleView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        transactionRecycler.setLayoutManager(layoutManager);
        transactionAdapter = new RecommendBookAdapter(this, transactionHistoryDataList);
        transactionRecycler.setAdapter(transactionAdapter);
*/
    }
}
