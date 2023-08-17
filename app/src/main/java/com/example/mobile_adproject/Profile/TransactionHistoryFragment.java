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
import com.example.mobile_adproject.models.Application;
import com.example.mobile_adproject.models.Book;
import com.example.mobile_adproject.models.Member;
import com.example.mobile_adproject.models.TransactionHistoryData;
import com.example.mobile_adproject.retrofit.ApplicationApi;
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
    String authorizationHeader;
    Long recipientId;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle save){
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.transaction_history,container,false);
        transactionRecycler=root.findViewById(R.id.transaction_recycleView);

        RetrofitService retrofitService = new RetrofitService();

        ApplicationApi applicationApi = retrofitService.getRetrofit().create(ApplicationApi.class);

        sharedPreferences = getActivity().getSharedPreferences("Login Credentials", Context.MODE_PRIVATE);

        String jwtToken = sharedPreferences.getString("jwtToken","");
        recipientId = sharedPreferences.getLong("memberId",0);

        authorizationHeader = "Bearer " + jwtToken;
        applicationApi.getAllApplicationsByMember(recipientId, authorizationHeader)
                .enqueue(new Callback<List<Application>>() {
                    @Override
                    public void onResponse(Call<List<Application>> call, Response<List<Application>> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getContext(), "Get All Transaction History Successful!", Toast.LENGTH_SHORT).show();
                            List<Application> body =response.body();
                            List<Application>completed = new ArrayList<>();
                            for(int i=0;i<body.size();i++){
                                if(body.get(i).getStatus()==4){
                                    completed.add(body.get(i));
                                }
                            }

                            setTransactionRecycler(completed);
                        }
                        else {
                            Toast.makeText(getContext(), "Failed to Get All Transaction History: " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Application>> call, Throwable t) {
                        Toast.makeText(getContext(), "Get All Transaction History Response Failed!", Toast.LENGTH_SHORT).show();
                        t.printStackTrace(); // Print the full stack trace to see the detailed error
                    }
                });


        return root;

    }
    private  void setTransactionRecycler(List<Application> transactionHistoryDataList){

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
