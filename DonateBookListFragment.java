package com.example.mobile_adproject.Profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_adproject.HomePageModel.RecommendBook;
import com.example.mobile_adproject.R;
import com.example.mobile_adproject.activities.MainActivity;
import com.example.mobile_adproject.models.Book;
import com.example.mobile_adproject.models.TransactionHistoryData;
import com.example.mobile_adproject.retrofit.BookApi;
import com.example.mobile_adproject.retrofit.RetrofitService;
import com.example.mobile_adproject.retrofit.TransactionApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonateBookListFragment  extends Fragment {
    RecyclerView donateBookRecycler;
    DonateBookAdapter donateBookAdapter;
    List<Book> bookList = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle save){
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.donate_book_list,container,false);

        donateBookRecycler = root.findViewById(R.id.donate_recycleView);

        RetrofitService retrofitService = new RetrofitService();
        TransactionApi transactionApi = retrofitService.getRetrofit().create(TransactionApi.class);

     SharedPreferences sharedPreferences = getContext().getSharedPreferences("Login Credentials", Context.MODE_PRIVATE);

        String jwtToken = sharedPreferences.getString("jwtToken","");
        Long loggedInMemberId = sharedPreferences.getLong("memberId",0);
        if(jwtToken!=null) {
            transactionApi.DonatedBook(loggedInMemberId, jwtToken)
                    .enqueue(new Callback<List<TransactionHistoryData>>() {
                        @Override
                        public void onResponse(Call<List<TransactionHistoryData>> call, Response<List<TransactionHistoryData>> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Get Donated Book Successful!", Toast.LENGTH_SHORT).show();
                                List<TransactionHistoryData> transactionHistoryData = response.body();
                                for (TransactionHistoryData data : transactionHistoryData) {
                                    bookList.add(data.getBook());
                                }
                                setTransactionRecycler(bookList);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<TransactionHistoryData>> call, Throwable t) {
                            Toast.makeText(getContext(), "Get Donated Book Successful!", Toast.LENGTH_SHORT).show();

                        }
                    });
        }

       /* List<RecommendBook> recommendBookList=new ArrayList<>();

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
        recommendBookList.add(new RecommendBook());*/

       /* setTransactionRecycler(recommendBookList);*/


        return root;

    }
    private  void setTransactionRecycler(List<Book> recommendBookList){

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        donateBookRecycler.setLayoutManager(layoutManager);
        donateBookAdapter = new DonateBookAdapter(requireContext(), recommendBookList);
        donateBookRecycler.setAdapter(donateBookAdapter);


    }
}