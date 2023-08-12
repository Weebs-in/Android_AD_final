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
import com.example.mobile_adproject.retrofit.BookApi;
import com.example.mobile_adproject.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonateBookListFragment  extends Fragment {
    RecyclerView donateBookRecycler;
    DonateBookAdapter donateBookAdapter;
    SharedPreferences sharedPreferences;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle save){
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.donate_book_list,container,false);

        donateBookRecycler = root.findViewById(R.id.donate_recycleView);

        RetrofitService retrofitService = new RetrofitService();
        BookApi bookApi = retrofitService.getRetrofit().create(BookApi.class);

        sharedPreferences = getActivity().getSharedPreferences("Login Credentials", Context.MODE_PRIVATE);

        String jwtToken = sharedPreferences.getString("jwtToken","");
        Long donorId = sharedPreferences.getLong("memberId",0);

        String authorizationHeader = "Bearer " + jwtToken;

        bookApi.getAllBooksByDonor(donorId, authorizationHeader)
                .enqueue(new Callback<List<Book>>() {
                    @Override
                    public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getContext(), "Get All Donated Books Successful!", Toast.LENGTH_SHORT).show();
                            setTransactionRecycler(response.body());
                        }
                        else {
                            Toast.makeText(getContext(), "Failed to Get All Donated Books: " + response.message(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Book>> call, Throwable t) {
                        Toast.makeText(getContext(), "Get All Donated Books Response Failed!", Toast.LENGTH_SHORT).show();
                        t.printStackTrace(); // Print the full stack trace to see the detailed error
                    }
                });

//        recommendBookList.add(new RecommendBook());
//        recommendBookList.add(new RecommendBook());
//        recommendBookList.add(new RecommendBook());
//        recommendBookList.add(new RecommendBook());
//        recommendBookList.add(new RecommendBook());
//        recommendBookList.add(new RecommendBook());
//        recommendBookList.add(new RecommendBook());
//        recommendBookList.add(new RecommendBook());
//        recommendBookList.add(new RecommendBook());
//        recommendBookList.add(new RecommendBook());
//        recommendBookList.add(new RecommendBook());
//        recommendBookList.add(new RecommendBook());
//        recommendBookList.add(new RecommendBook());




        return root;

    }
    private  void setTransactionRecycler(List<Book> donatedBookList){

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        donateBookRecycler.setLayoutManager(layoutManager);
        donateBookAdapter = new DonateBookAdapter(requireContext(), donatedBookList);
        donateBookRecycler.setAdapter(donateBookAdapter);


    }
}