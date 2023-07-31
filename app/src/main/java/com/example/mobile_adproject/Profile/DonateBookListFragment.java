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

import com.example.mobile_adproject.HomePageModel.RecommendBook;
import com.example.mobile_adproject.R;

import java.util.ArrayList;
import java.util.List;

public class DonateBookListFragment  extends Fragment {
    RecyclerView donateBookRecycler;
    DonateBookAdapter donateBookAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle save){
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.donate_book_list,container,false);

        donateBookRecycler = root.findViewById(R.id.donate_recycleView);
        List<RecommendBook> recommendBookList=new ArrayList<>();

        recommendBookList.add(new RecommendBook());
        recommendBookList.add(new RecommendBook());
        recommendBookList.add(new RecommendBook());
        recommendBookList.add(new RecommendBook());
        recommendBookList.add(new RecommendBook());
        recommendBookList.add(new RecommendBook());
        recommendBookList.add(new RecommendBook());
        recommendBookList.add(new RecommendBook());
        recommendBookList.add(new RecommendBook());
        recommendBookList.add(new RecommendBook());
        recommendBookList.add(new RecommendBook());
        recommendBookList.add(new RecommendBook());
        recommendBookList.add(new RecommendBook());

        setTransactionRecycler(recommendBookList);


        return root;

    }
    private  void setTransactionRecycler(List<RecommendBook> recommendBookList){

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        donateBookRecycler.setLayoutManager(layoutManager);
        donateBookAdapter = new DonateBookAdapter(requireContext(), recommendBookList);
        donateBookRecycler.setAdapter(donateBookAdapter);


    }
}