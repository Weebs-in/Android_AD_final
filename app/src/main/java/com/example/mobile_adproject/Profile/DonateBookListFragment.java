package com.example.mobile_adproject.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobile_adproject.R;

public class DonateBookListFragment  extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle save){
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.donate_book_list,container,false);
/*        username=root.findViewById(R.id.username_login);
        password=root.findViewById(R.id.password_login);
        login=root.findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().equals("abc")&&password.getText().toString().equals("123456")) {
                    Toast.makeText(getContext(), "Login Success", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_LONG).show();
                }
            }
        });*/

        return root;

    }
}