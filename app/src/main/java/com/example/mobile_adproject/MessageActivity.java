package com.example.mobile_adproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_adproject.Message.ConfirmRequestMessage;
import com.example.mobile_adproject.Message.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    RecyclerView messageRecycleView;
    MessageAdapter messageAdapter;
    List<ConfirmRequestMessage> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        messageRecycleView=findViewById(R.id.message_recycler_view);
        mData=new ArrayList<>();
        mData.add(new ConfirmRequestMessage("Harry Potter and the Order of the Phoenix","mnbvcxz",true,false));
        mData.add(new ConfirmRequestMessage("Harry Potter and the Order of the Phoenix","mnbvcxz",false,false));
        mData.add(new ConfirmRequestMessage("Harry Potter and the Order of the Phoenix","mnbvcxz",false,true));
        mData.add(new ConfirmRequestMessage("Harry Potter and the Order of the Phoenix","mnbvcxz",true,false));
        mData.add(new ConfirmRequestMessage("Harry Potter and the Order of the Phoenix","mnbvcxz",false,false));
        mData.add(new ConfirmRequestMessage("Harry Potter and the Order of the Phoenix","mnbvcxz",false,true));
        mData.add(new ConfirmRequestMessage("Harry Potter and the Order of the Phoenix","mnbvcxz",true,false));
        mData.add(new ConfirmRequestMessage("Harry Potter and the Order of the Phoenix","mnbvcxz",false,false));
        mData.add(new ConfirmRequestMessage("Harry Potter and the Order of the Phoenix","mnbvcxz",false,true));
        mData.add(new ConfirmRequestMessage("Harry Potter and the Order of the Phoenix","mnbvcxz",true,false));
        mData.add(new ConfirmRequestMessage("Harry Potter and the Order of the Phoenix","mnbvcxz",false,false));
        mData.add(new ConfirmRequestMessage("Harry Potter and the Order of the Phoenix","mnbvcxz",false,true));

        messageAdapter=new MessageAdapter(this,mData);
        messageRecycleView.setAdapter(messageAdapter);
        messageRecycleView.setLayoutManager(new LinearLayoutManager(this));
    }
}