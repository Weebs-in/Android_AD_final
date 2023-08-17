package com.example.mobile_adproject.Profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_adproject.R;
import com.example.mobile_adproject.models.Book;
import com.example.mobile_adproject.retrofit.BookApi;
import com.example.mobile_adproject.retrofit.RetrofitService;

import java.util.List;

public class ApprovedBookAdapter  extends RecyclerView.Adapter<ApprovedBookAdapter.ApprovedBookViewHolder>{
    Context context;
    List<Book> transactionHistoryData;

    SharedPreferences sharedPreferences;
    BookApi bookApi;

    public ApprovedBookAdapter(Context context, List<Book> transactionHistoryData) {
        this.context = context;
        this.transactionHistoryData = transactionHistoryData;
    }

    @NonNull
    @Override
    public ApprovedBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.approved_book_list_item,parent,false);
        return new ApprovedBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApprovedBookViewHolder holder, int position) {
        /*holder.bookTitle.setText(donatedBookList.get(position).getTitle());
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
                break;*/

//        holder.bookStatus.setText(String.valueOf(donatedBookList.get(position).getStatus()));
        //String url = donatedBookList.get(position).getCover(); // get image URL
        //load the picture

        /*holder.bookCover.setImageResource(R.drawable.book);*/
        Integer status=transactionHistoryData.get(position).getStatus();
        Long bookId=transactionHistoryData.get(position).getId();
        if(status==1){
            holder.button.setVisibility(View.VISIBLE);
        }else{
            holder.button.setVisibility(View.GONE);
        }

        holder.bookCover.setImageResource(R.drawable.book);
        holder.bookTitle.setText(transactionHistoryData.get(position).getTitle());
        holder.bookAuthor.setText(transactionHistoryData.get(position).getTitle());
        switch (transactionHistoryData.get(position).getStatus()){
            case 0:
                holder.bookStatus.setText("Pending");
                break;
            case 1:
                holder.bookStatus.setText("Approved");
                break;
            case 2:
                holder.bookStatus.setText("Rejected");
                break;
            case 3:
                holder.bookStatus.setText("Ready for Collection");
                break;
            case 4:
                holder.bookStatus.setText("Completed");
                break;
            default:
                holder.bookStatus.setText("NA");
                break;
        }

        holder.button.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 holder.bookStatus.setText("Complete");
                                                 holder.button.setText("Thank You ");
                                                 ApprovedBookListFragment approvedBookListFragment=new ApprovedBookListFragment();
                                                approvedBookListFragment.sendTransactionCompletetoServer(bookId);
                                             }
                                         }

        );


    }

    @Override
    public int getItemCount() {
        return transactionHistoryData.size();
    }

    public class ApprovedBookViewHolder  extends RecyclerView.ViewHolder {
        ImageView bookCover;
        TextView bookTitle, bookAuthor, bookStatus;
        Button button;

        public ApprovedBookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookCover = itemView.findViewById(R.id.book_cover);
            bookTitle = itemView.findViewById(R.id.book_title);
            bookAuthor = itemView.findViewById(R.id.book_author_need_add_donate);
            bookStatus = itemView.findViewById(R.id.book_status);
            button = itemView.findViewById(R.id.Status_button);
        }


    }
}
