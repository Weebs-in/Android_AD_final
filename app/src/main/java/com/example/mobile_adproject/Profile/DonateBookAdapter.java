package com.example.mobile_adproject.Profile;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_adproject.R;
import com.example.mobile_adproject.activities.DonateBookDetailActivity;
import com.example.mobile_adproject.activities.ProfileActivity;
import com.example.mobile_adproject.models.Book;
import com.example.mobile_adproject.retrofit.BookApi;
import com.example.mobile_adproject.retrofit.RetrofitService;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DonateBookAdapter extends RecyclerView.Adapter<DonateBookAdapter.DonateViewHolder>{
    Context context;
    private List<Book> donatedBookList;
    private RetrofitService retrofitService;
    private BookApi bookApi;
    private SharedPreferences sharedPreferences;

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
    public void onBindViewHolder(@NonNull DonateViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.bookTitle.setText(donatedBookList.get(position).getTitle());
        holder.bookAuthor.setText(donatedBookList.get(position).getAuthor());
        // 设置占位图或者清空图片
        holder.bookCover.setImageBitmap(null);

        String coverImageUrl = donatedBookList.get(position).getCover();
        System.out.println(coverImageUrl);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(coverImageUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("GET");
                    if (conn.getResponseCode() == 200) {
                        InputStream inputStream = conn.getInputStream();
                        final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                        // 在 UI 线程中更新 ImageView
                        holder.itemView.post(new Runnable() {
                            @Override
                            public void run() {
                                holder.bookCover.setImageBitmap(bitmap);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        switch (donatedBookList.get(position).getStatus()){
            case 0:
                holder.bookStatus.setText("Pending");
                break;
            case 1:
                holder.bookStatus.setText("Deposited");
                break;
            case 2:
                holder.bookStatus.setText("Available");
                break;
            case 3:
                holder.bookStatus.setText("Reserved");
                break;
//            case 4:
//                holder.bookStatus.setText("Unavailable");
//                break;
            case 5:
                holder.bookStatus.setText("Rejected");
                break;
            default:
                holder.bookStatus.setText("NA");
                break;
        }

        holder.bookCover.setImageResource(R.drawable.ic_baseline_link_off_24);

        int status = donatedBookList.get(position).getStatus();

        /*if(status == 4 || status == 6){
            // Hide the item
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }*/
       /* else {*/
           /* holder.itemView.setVisibility(View.VISIBLE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));*/
            // Check if the status is not Pending
            if (donatedBookList.get(position).getStatus() != 0) {
                // Disable click action for items except Pending status
                holder.itemView.setOnClickListener(null);
                holder.btnDeposit.setVisibility(View.GONE);
                holder.btnCancel.setVisibility(View.GONE);
            } else {
                // Enable click action for other items
                holder.itemView.setOnClickListener(view -> {
                    Book selectedBook = donatedBookList.get(position); // 获取选定的书籍对象

                    Intent intent = new Intent(context, DonateBookDetailActivity.class);
                    intent.putExtra("selectedBook",  selectedBook); // 将选定的书籍对象放入Intent中

                    context.startActivity(intent);
                });

                holder.btnDeposit.setOnClickListener(view -> {


                    // Create and show the confirmation dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setView(R.layout.dialog_confirm_deposit);
                    AlertDialog dialog = builder.create();

                    // Show the dialog
                    dialog.show();

                    // Find the buttons in the dialog layout
                    Button confirmButton = dialog.findViewById(R.id.btn_confirm);
                    Button cancelButton = dialog.findViewById(R.id.btn_cancel);

                    // Set click listener for the "Confirm" button
                    confirmButton.setOnClickListener(v -> {
                        // Perform the deposit action here
                        // Update book status and make API call
                        Book selectedBook =donatedBookList.get(position);

                        Long selectedBookId = selectedBook.getId();

                        selectedBook.setStatus(1);

                        retrofitService = new RetrofitService();
                        bookApi = retrofitService.getRetrofit().create(BookApi.class);

                        sharedPreferences = context.getSharedPreferences("Login Credentials", Context.MODE_PRIVATE);

                        String jwtToken = sharedPreferences.getString("jwtToken", "");
                        String authorizationHeader = "Bearer " + jwtToken;

                        bookApi.updateBookById(selectedBookId, selectedBook, authorizationHeader)
                                .enqueue(new Callback<Book>() {
                                    @Override
                                    public void onResponse(Call<Book> call, Response<Book> response) {
                                        if(response.isSuccessful()){
                                            Toast.makeText(context, "Update Book Successful!", Toast.LENGTH_SHORT).show();

                                            Intent intent1 = new Intent(context, ProfileActivity.class);
                                            context.startActivity(intent1);
                                        }
                                        else {
                                            try {
                                                Toast.makeText(context, "Failed to Update Book: "
                                                                + response.message() + response.errorBody().string(), Toast.LENGTH_SHORT)
                                                        .show();
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Book> call, Throwable t) {
                                        Toast.makeText(context, "Update Book Response Failed!", Toast.LENGTH_SHORT)
                                                .show();
                                        t.printStackTrace(); // Print the full stack trace to see the detailed error
                                    }
                                });

                    });

                    // Set click listener for the "Cancel" button
                    cancelButton.setOnClickListener(v -> {
                        // Dismiss the dialog
                        dialog.dismiss();
                    });
                });

                holder.btnCancel.setOnClickListener(view -> {

                    // Create and show the confirmation dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setView(R.layout.dialog_cancel_donate);
                    AlertDialog dialog = builder.create();

                    // Show the dialog
                    dialog.show();

                    // Find the buttons in the dialog layout
                    Button confirmButton = dialog.findViewById(R.id.btn_confirm);
                    Button cancelButton = dialog.findViewById(R.id.btn_cancel);

                    // Set click listener for the "Confirm" button
                    confirmButton.setOnClickListener(v -> {

                        Book selectedBook =donatedBookList.get(position);

                        Long selectedBookId = selectedBook.getId();

                        String jwtToken = sharedPreferences.getString("jwtToken", "");
                        String authorizationHeader = "Bearer " + jwtToken;

                        bookApi.deleteBookById(selectedBookId, authorizationHeader)
                                .enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Toast.makeText(context, "Delete Book Success!", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(context, "Delete Book Failed!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    });

                    // Set click listener for the "Cancel" button
                    cancelButton.setOnClickListener(v -> {
                        // Dismiss the dialog
                        dialog.dismiss();
                    });
                });
            }
        }

    @Override
    public int getItemCount() {
        return donatedBookList.size();
    }

    public class DonateViewHolder extends RecyclerView.ViewHolder{

        ImageView bookCover;
        TextView bookTitle, bookAuthor, bookStatus, btnDeposit, btnCancel;
        public DonateViewHolder(@NonNull View itemView) {
            super(itemView);

            bookCover = itemView.findViewById(R.id.book_cover);
            bookTitle = itemView.findViewById(R.id.book_title_profile);
            bookAuthor = itemView.findViewById(R.id.book_author_need_add_donate);
            bookStatus = itemView.findViewById(R.id.status_donate_book_need_add);
            btnDeposit = itemView.findViewById(R.id.deposit_button);
            btnCancel = itemView.findViewById(R.id.cancel_donate_button);

        }
    }
}
