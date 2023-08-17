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
import com.example.mobile_adproject.models.TransactionHistoryData;
import com.example.mobile_adproject.retrofit.BookApi;
import com.example.mobile_adproject.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionHistoryFragment extends Fragment  {
    RecyclerView transactionRecycler;
    TransactionAdapter transactionAdapter;
    SharedPreferences sharedPreferences;
    Long recipientId;
    String authorizationHeader;
    String memberName;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle save){
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.transaction_history,container,false);

        RetrofitService retrofitService = new RetrofitService();
        BookApi bookApi = retrofitService.getRetrofit().create(BookApi.class);

        sharedPreferences = getActivity().getSharedPreferences("Login Credentials", Context.MODE_PRIVATE);

        String jwtToken = sharedPreferences.getString("jwtToken","");
        recipientId = sharedPreferences.getLong("memberId",0);

        memberName=sharedPreferences.getString("memberName","");


        authorizationHeader = "Bearer " + jwtToken;

        bookApi.TransactionHistory(recipientId,authorizationHeader)
                .enqueue(new Callback<List<Book>>() {
                    @Override
                    public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getContext(), "Get Transaction History Successful!", Toast.LENGTH_SHORT).show();
                            setTransactionRecycler(response.body());
                        }
                        else {
                            Toast.makeText(getContext(), "Failed Transaction History  Books: " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Book>> call, Throwable t) {
                        Toast.makeText(getContext(), "Get Transaction Data Response Failed!", Toast.LENGTH_SHORT).show();
                        t.printStackTrace(); // Print the full stack trace to see the detailed error

                    }
                });



        /*transactionRecycler = root.findViewById(R.id.transaction_recycleView);
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
*/
        return root;

    }
    private  void setTransactionRecycler(List<Book> transactionHistoryDataList){

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        transactionRecycler.setLayoutManager(layoutManager);
        transactionAdapter = new TransactionAdapter(requireContext(), transactionHistoryDataList,memberName);
        transactionRecycler.setAdapter(transactionAdapter);

       /* transactionRecycler = findViewById(R.id.transaction_recycleView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        transactionRecycler.setLayoutManager(layoutManager);
        transactionAdapter = new RecommendBookAdapter(this, transactionHistoryDataList);
        transactionRecycler.setAdapter(transactionAdapter);
*/
    }
}
