package com.example.mobile_adproject.Profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_adproject.HomePageModel.RecommendBook;
import com.example.mobile_adproject.R;
import com.example.mobile_adproject.models.Book;

import java.util.List;

public class DonateBookAdapter extends RecyclerView.Adapter<DonateBookAdapter.DonateViewHolder>{
    Context context;
    List<Book> recommendBookList;//the donate book just the recommmend books, they are all books

    public DonateBookAdapter(Context context, List<Book> recommendBookList) {
        this.context = context;
        this.recommendBookList = recommendBookList;
    }

    @NonNull
    @Override
    public DonateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.donate_book_list_item,parent,false);

        return new DonateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonateViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return recommendBookList.size();
    }

    public class DonateViewHolder extends RecyclerView.ViewHolder{
        public DonateViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
