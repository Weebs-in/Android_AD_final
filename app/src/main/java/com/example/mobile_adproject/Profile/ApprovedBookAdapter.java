package com.example.mobile_adproject.Profile;

import android.annotation.SuppressLint;
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
import com.example.mobile_adproject.models.CompleteDTO;
import com.example.mobile_adproject.models.Member;
import com.example.mobile_adproject.retrofit.ApplicationApi;
import com.example.mobile_adproject.retrofit.BookApi;
import com.example.mobile_adproject.retrofit.RetrofitService;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApprovedBookAdapter  extends RecyclerView.Adapter<ApprovedBookAdapter.ApprovedBookViewHolder>{
    Context context;
    List<Application> applicationList;

    List<Application> ex4ApplicationList;

    public ApprovedBookAdapter(Context context, List<Application> applicationList) {
        this.context = context;
        this.applicationList = applicationList;

        ex4ApplicationList = new ArrayList<>(); // Initialize the list

        for (Application application : applicationList) {
            if (application.getStatus() != 4&&application.getStatus()!=5) {
                ex4ApplicationList.add(application);
            }
        }
    }

    @NonNull
    @Override
    public ApprovedBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.approved_book_list_item,parent,false);

        return new ApprovedBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApprovedBookViewHolder holder, @SuppressLint("RecyclerView") int position) {


        // 设置占位图或者清空图片
        holder.bookCover.setImageBitmap(null);

        String coverImageUrl = ex4ApplicationList.get(position).getBook().getCover();
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
        holder.bookTitle.setText(ex4ApplicationList.get(position).getBook().getTitle());
        holder.bookAuthor.setText(ex4ApplicationList.get(position).getBook().getAuthor());
        switch (ex4ApplicationList.get(position).getStatus()){
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
            case 5:
                holder.applicationStatus.setText("Disabled");
                break;

            default:
                holder.applicationStatus.setText("NA");
                break;
        }


        if (ex4ApplicationList.get(position).getStatus() == 3) {
            holder.cancel_button.setOnClickListener(view -> {

                // Create and show the confirmation dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setView(R.layout.dialog_confirm_complete_take_over_book);
                AlertDialog dialog = builder.create();

                // Show the dialog
                dialog.show();

                // Find the buttons in the dialog layout
                Button confirmButton = dialog.findViewById(R.id.btn_confirm);
                Button cancelButton = dialog.findViewById(R.id.btn_cancel);

                RetrofitService retrofitService = new RetrofitService();
                ApplicationApi applicationApi = retrofitService.getRetrofit().create(ApplicationApi.class);

                SharedPreferences sharedPreferences = context.getSharedPreferences("Login Credentials", Context.MODE_PRIVATE);
                String jwtToken = sharedPreferences.getString("jwtToken", "");
                String authorizationHeader = "Bearer " + jwtToken;
                // Set click listener for the "Confirm" button
                confirmButton.setOnClickListener(v -> {
                    // Perform the request action here
                    // create application
                    Application application = ex4ApplicationList.get(position);
                    CompleteDTO completeDTO=new CompleteDTO();
                    completeDTO.setBookId(application.getBook().getId());
                    completeDTO.setRecipientId(application.getRecipient().getId());
                    applicationApi.cancelApplication( completeDTO ,authorizationHeader)
                            .enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.isSuccessful()) {
                                       // Toast.makeText(context, "Cancel Application Successful!", Toast.LENGTH_SHORT).show();
                                        System.out.println(application.getStatus());
                                        Intent intent = new Intent(context, ProfileActivity.class);
                                        context.startActivity(intent);
                                    } else {
                                        try {
                                            Toast.makeText(context, "Failed to cancel application: "
                                                    + response.message() + response.errorBody().string(), Toast.LENGTH_SHORT)
                                                    .show();
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Toast.makeText(context, "Cancel Application Response Failed!", Toast.LENGTH_SHORT)
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

                RetrofitService retrofitService = new RetrofitService();
                ApplicationApi applicationApi = retrofitService.getRetrofit().create(ApplicationApi.class);

                SharedPreferences sharedPreferences = context.getSharedPreferences("Login Credentials", Context.MODE_PRIVATE);
                String jwtToken = sharedPreferences.getString("jwtToken", "");
                String authorizationHeader = "Bearer " + jwtToken;
                // Set click listener for the "Confirm" button
                confirmButton.setOnClickListener(v -> {
                    // Perform the request action here
                    // create application
                    Application application = ex4ApplicationList.get(position);
                    application.setStatus(4);
                    CompleteDTO completeDTO=new CompleteDTO();
                    completeDTO.setBookId(application.getBook().getId());
                    completeDTO.setRecipientId(application.getRecipient().getId());
                    applicationApi.updateApplicationComplete( completeDTO ,authorizationHeader)
                            .enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.isSuccessful()) {
                                       // Toast.makeText(context, "Update Application Successful!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(context, ProfileActivity.class);
                                        context.startActivity(intent);
                                    } else {
                                        try {
                                            Toast.makeText(context, "Failed to update application: "
                                                    + response.message() + response.errorBody().string(), Toast.LENGTH_SHORT)
                                                    .show();
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
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



        } else {
            holder.button.setVisibility(View.GONE);
            holder.cancel_button.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return ex4ApplicationList.size();
    }

    public class ApprovedBookViewHolder  extends RecyclerView.ViewHolder {
        ImageView bookCover;
        TextView bookTitle, bookAuthor, applicationStatus;
        TextView button;
        TextView cancel_button;

        public ApprovedBookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookCover = itemView.findViewById(R.id.book_cover);
            bookTitle = itemView.findViewById(R.id.book_title);
            bookAuthor = itemView.findViewById(R.id.book_author_need_add_approved);
            applicationStatus = itemView.findViewById(R.id.book_status);
            button = itemView.findViewById(R.id.Status_button);
            cancel_button=itemView.findViewById(R.id.cancel_button);
        }


    }
}
