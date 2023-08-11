package com.example.mobile_adproject.Profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_adproject.HomePageModel.RecommendBook;
import com.example.mobile_adproject.R;
import com.example.mobile_adproject.models.Book;

import java.util.List;

public class DonateBookAdapter extends RecyclerView.Adapter<DonateBookAdapter.DonateViewHolder>{
    Context context;
    List<Book> donatedBookList;//the donate book just the recommmend books, they are all books

    public DonateBookAdapter(Context context, List<Book> donatedBookList) {
        this.context = context;
        this.donatedBookList = donatedBookList;
    }

    @NonNull
    @Override
    public DonateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.donate_book_list_item,parent,false);

        return new DonateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonateViewHolder holder, int position) {

        holder.bookTitle.setText(donatedBookList.get(position).getTitle());
        holder.bookAuthor.setText(donatedBookList.get(position).getAuthor());
        switch (donatedBookList.get(position).getStatus()){
            case 0:
                holder.bookStatus.setText("Created");
                break;
            case 1:
                holder.bookStatus.setText("Deposited");
                break;
            case 2:
                holder.bookStatus.setText("Available");
                break;
            case 3:
                holder.bookStatus.setText("Ineligible");
                break;
            case 4:
                holder.bookStatus.setText("Reserved");
                break;
            case 5:
                holder.bookStatus.setText("Unavailable");
                break;
            default:
                holder.bookStatus.setText("NA");
                break;
        }
//        holder.bookStatus.setText(String.valueOf(donatedBookList.get(position).getStatus()));
        //String url = donatedBookList.get(position).getCover(); // get image URL
        //load the picture

        holder.bookCover.setImageResource(R.drawable.book);

    }

    @Override
    public int getItemCount() {
        return donatedBookList.size();
    }

    public class DonateViewHolder extends RecyclerView.ViewHolder{

        ImageView bookCover;
        TextView bookTitle, bookAuthor, bookStatus;
        public DonateViewHolder(@NonNull View itemView) {
            super(itemView);

            bookCover = itemView.findViewById(R.id.book_cover);
            bookTitle = itemView.findViewById(R.id.book_title_profile);
            bookAuthor = itemView.findViewById(R.id.book_author_need_add_donate);
            bookStatus = itemView.findViewById(R.id.status_donate_book_need_add);
        }
    }
}
