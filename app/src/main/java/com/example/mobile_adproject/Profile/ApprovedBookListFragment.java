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

import com.example.mobile_adproject.R;
import com.example.mobile_adproject.models.Book;
import com.example.mobile_adproject.retrofit.BookApi;
import com.example.mobile_adproject.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApprovedBookListFragment extends Fragment {
    RecyclerView approvedBookRecycler;
    ApprovedBookAdapter approvedBookAdapter;

    SharedPreferences sharedPreferences;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle save){
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.approved_book_list,container,false);
        approvedBookRecycler=root.findViewById(R.id.approved_recycleView);

        RetrofitService retrofitService = new RetrofitService();
        BookApi bookApi = retrofitService.getRetrofit().create(BookApi.class);

        sharedPreferences = getActivity().getSharedPreferences("Login Credentials", Context.MODE_PRIVATE);

        String jwtToken = sharedPreferences.getString("jwtToken","");
        Long recipientId = sharedPreferences.getLong("memberId",0);

        String authorizationHeader = "Bearer " + jwtToken;

        bookApi.approvedBookByRecipientId(recipientId,authorizationHeader)
                .enqueue(new Callback<List<Book>>() {
                    @Override
                    public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getContext(), "Get Approved BookList Successful!", Toast.LENGTH_SHORT).show();
                            setTransactionRecycler(response.body());
                        }
                        else {
                            Toast.makeText(getContext(), "Failed Approved BookList  Books: " + response.message(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Book>> call, Throwable t) {
                        Toast.makeText(getContext(), "Get All Donated Books Response Failed!", Toast.LENGTH_SHORT).show();
                        t.printStackTrace(); // Print the full stack trace to see the detailed error
                    }
                });




        /*List<TransactionHistoryData> transactionHistoryData=new ArrayList<>();
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
        setTransactionRecycler(transactionHistoryData);*/

        return root;

    }
    private  void setTransactionRecycler(List<Book> transactionHistoryData){

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        approvedBookRecycler.setLayoutManager(layoutManager);
        approvedBookAdapter = new ApprovedBookAdapter(requireContext(), transactionHistoryData);
        approvedBookRecycler.setAdapter(approvedBookAdapter);


    }
}