package com.example.mobile_adproject.Profile;

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
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.mobile_adproject.R;
import com.example.mobile_adproject.activities.BookDetailActivity;
import com.example.mobile_adproject.activities.DonateBookDetailActivity;
import com.example.mobile_adproject.activities.ProfileActivity;
import com.example.mobile_adproject.models.Application;
import com.example.mobile_adproject.models.Book;
import com.example.mobile_adproject.models.Member;
import com.example.mobile_adproject.retrofit.ApplicationApi;
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

public class ApprovedBookAdapter  extends RecyclerView.Adapter<ApprovedBookAdapter.ApprovedBookViewHolder>{
    Context context;
    List<Application> applicationList;


    public ApprovedBookAdapter(Context context, List<Application> applicationList) {
        this.context = context;
        this.applicationList = applicationList;
    }

    @NonNull
    @Override
    public ApprovedBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.approved_book_list_item,parent,false);

        return new ApprovedBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApprovedBookViewHolder holder, int position) {
        if(applicationList.get(position).getStatus() == 4 ){
            // Hide the item
            holder.itemView.setVisibility(View.GONE);

        }
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
       /* Integer status=applicationList.get(position).getStatus();
        Long bookId=applicationList.get(position).getId();
        if(status==1){
            holder.button.setVisibility(View.VISIBLE);
        }else{
            holder.button.setVisibility(View.GONE);
        }*/

        // 设置占位图或者清空图片
        holder.bookCover.setImageBitmap(null);

        String coverImageUrl = applicationList.get(position).getBook().getCover();
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
        holder.bookTitle.setText(applicationList.get(position).getBook().getTitle());
        holder.bookAuthor.setText(applicationList.get(position).getBook().getAuthor());
        switch (applicationList.get(position).getStatus()){
            case 0:
                holder.applicationStatus.setText("Pending");
                break;
            case 1:
                holder.applicationStatus.setText("Approved");
                break;
            case 2:
                holder.applicationStatus.setText("Rejected");
                break;
            case 3:
                holder.applicationStatus.setText("Ready for Collection");
                break;
            case 4:
                holder.applicationStatus.setText("Completed");
                break;
            default:
                holder.applicationStatus.setText("NA");
                break;
        }

        holder.button.setOnClickListener(view -> {

            // Create and show the confirmation dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setView(R.layout.dialog_confirm_complete_take_over_book);
            AlertDialog dialog = builder.create();

            // Show the dialog
            dialog.show();

            // Find the buttons in the dialog layout
            Button confirmButton = dialog.findViewById(R.id.btn_confirm);
            Button cancelButton = dialog.findViewById(R.id.btn_cancel);

            RetrofitService retrofitService=new RetrofitService();
            ApplicationApi applicationApi = retrofitService.getRetrofit().create(ApplicationApi.class);

            SharedPreferences sharedPreferences = context.getSharedPreferences("Login Credentials", Context.MODE_PRIVATE);
            String jwtToken = sharedPreferences.getString("jwtToken", "");
            String authorizationHeader = "Bearer " + jwtToken;
            // Set click listener for the "Confirm" button
            confirmButton.setOnClickListener(v -> {
                // Perform the request action here
                // create application

                holder.itemView.setVisibility(View.GONE);
                Application application=applicationList.get(position);
                application.setStatus(4);
                applicationApi.updateApplicationById(applicationList.get(position).getId(), application,authorizationHeader)
                        .enqueue(new Callback<Application>() {
                            @Override
                            public void onResponse(Call<Application> call, Response<Application> response) {
                                if(response.isSuccessful()){
                                    Toast.makeText(context, "Update Application Successful!", Toast.LENGTH_SHORT).show();

                                }
                                else {
                                    try {
                                        Toast.makeText(context, "Failed to update applicayion: "
                                                + response.message() + response.errorBody().string(), Toast.LENGTH_SHORT)
                                                .show();
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Application> call, Throwable t) {
                                Toast.makeText(context, "Update Application Response Failed!", Toast.LENGTH_SHORT)
                                        .show();
                                t.printStackTrace(); // Print the full stack trace to see the detailed error
                            }
                        });

                // Dismiss the dialog
                dialog.dismiss();
            });

            // Set click listener for the "Cancel" button
            cancelButton.setOnClickListener(v -> {
                // Dismiss the dialog
                dialog.dismiss();
            });

        });


    }

    @Override
    public int getItemCount() {
        return applicationList.size();
    }

    public class ApprovedBookViewHolder  extends RecyclerView.ViewHolder {
        ImageView bookCover;
        TextView bookTitle, bookAuthor, applicationStatus;
        Button button;

        public ApprovedBookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookCover = itemView.findViewById(R.id.book_cover);
            bookTitle = itemView.findViewById(R.id.book_title);
            bookAuthor = itemView.findViewById(R.id.book_author_need_add_approved);
            applicationStatus = itemView.findViewById(R.id.book_status);
            button = itemView.findViewById(R.id.Status_button);
        }


    }
}
