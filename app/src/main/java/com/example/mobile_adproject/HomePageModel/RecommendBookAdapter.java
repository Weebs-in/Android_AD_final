package com.example.mobile_adproject.HomePageModel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_adproject.BookDetailActivity;
import com.example.mobile_adproject.R;

import java.util.List;

public class RecommendBookAdapter extends RecyclerView.Adapter<RecommendBookAdapter.RecommendBookViewHolder> {
    Context context;
    List<RecommendBook> recommendBookList;

    public RecommendBookAdapter(Context context, List<RecommendBook> recommendBookList) {
        this.context = context;
        this.recommendBookList = recommendBookList;
    }

    @NonNull
    @Override
    public RecommendBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recommend_books_row_item,parent,false);

        return new RecommendBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendBookViewHolder holder, int position) {

        holder.bookTitle.setText(recommendBookList.get(position).getTitle());
        holder.bookAuthor.setText(recommendBookList.get(position).getAuthor());
        //String url = recommendBookList.get(position).getCover(); // get image URL
        //load the picture

        holder.bookCover.setImageResource(R.drawable.book);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, BookDetailActivity.class);
                context.startActivity(intent);
            }
        });





    }

    @Override
    public int getItemCount() {
        return recommendBookList.size();
    }

    public static  final class RecommendBookViewHolder extends RecyclerView.ViewHolder{

        ImageView bookCover;
        TextView bookTitle, bookAuthor;

        public RecommendBookViewHolder(@NonNull View itemView) {
            super(itemView);

            bookCover = itemView.findViewById(R.id.book_cover);
            bookTitle = itemView.findViewById(R.id.book_title);
            bookAuthor = itemView.findViewById(R.id.book_author);


        }
    }
}
