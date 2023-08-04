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

import java.util.ArrayList;
import java.util.List;

public class ApprovedBookListFragment extends Fragment {
    RecyclerView approvedBookRecycler;
    ApprovedBookAdapter approvedBookAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle save){
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.approved_book_list,container,false);
        approvedBookRecycler=root.findViewById(R.id.approved_recycleView);
        List<TransactionHistoryData> transactionHistoryData=new ArrayList<>();
        transactionHistoryData.add(new TransactionHistoryData());
        transactionHistoryData.add(new TransactionHistoryData());
        transactionHistoryData.add(new TransactionHistoryData());
        transactionHistoryData.add(new TransactionHistoryData());
        transactionHistoryData.add(new TransactionHistoryData());
        transactionHistoryData.add(new TransactionHistoryData());
        transactionHistoryData.add(new TransactionHistoryData());
        transactionHistoryData.add(new TransactionHistoryData());
        transactionHistoryData.add(new TransactionHistoryData());
        transactionHistoryData.add(new TransactionHistoryData());
        transactionHistoryData.add(new TransactionHistoryData());
        transactionHistoryData.add(new TransactionHistoryData());
        transactionHistoryData.add(new TransactionHistoryData());
        transactionHistoryData.add(new TransactionHistoryData());
        setTransactionRecycler(transactionHistoryData);

        return root;

    }
    private  void setTransactionRecycler(List<TransactionHistoryData> transactionHistoryData){

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        approvedBookRecycler.setLayoutManager(layoutManager);
        approvedBookAdapter = new ApprovedBookAdapter(requireContext(), transactionHistoryData);
        approvedBookRecycler.setAdapter(approvedBookAdapter);


    }
}