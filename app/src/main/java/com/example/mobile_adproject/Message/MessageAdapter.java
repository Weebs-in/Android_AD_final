package com.example.mobile_adproject.Message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_adproject.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    Context context;
    List<ConfirmRequestMessage> mData;

    public MessageAdapter(android.content.Context context, List<ConfirmRequestMessage> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View layout;
        layout= LayoutInflater.from(context).inflate(R.layout.item_message,viewGroup,false);
        return new MessageViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, int position) {

        //bind data
        //true means need donor to confirm the request
        String book_title=mData.get(position).getTitle();
        String recipient=mData.get(position).getMemberUserName();

        if(mData.get(position).getIsConfirmRequest_or_noticeRequestStatus()){
            messageViewHolder.message_title.setText("Please Confirm Book Request");
            messageViewHolder.message_content.setText("Your book \""+book_title+"\" has been requested by user \""+recipient+"\". Please handle this request promptly.");
            messageViewHolder.message_image.setImageResource(R.drawable.ic_baseline_notification_important_24);
        }else { //false means notice recipient the status of the book he request for
            if(mData.get(position).getIsNoticeRequestStatus()){//ture means his request be accepted
                messageViewHolder.message_title.setText("Congratulation");
                messageViewHolder.message_image.setImageResource(R.drawable.ic_baseline_check_circle_24);
                messageViewHolder.message_content.setText("The book \""+book_title+"\" that you requested has been approved by the donor. Please check it out.");
            }else{//false means his request be rejected
                messageViewHolder.message_title.setText("Regret");
                messageViewHolder.message_content.setText("Sorry, the book \""+book_title+"\" that you requested has not been approved by the donor. ");
                messageViewHolder.message_image.setImageResource(R.drawable.ic_baseline_error_24);
            }

        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{
        TextView message_title,message_content;
        ImageView message_image;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            message_title=itemView.findViewById(R.id.message_title);
            message_content=itemView.findViewById(R.id.message_content);
            message_image=itemView.findViewById(R.id.message_image);
        }
    }
}
