package com.example.mobile_adproject.LoginAndRegisterFragment;
import com.example.mobile_adproject.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class SignUpFragment extends Fragment {


    EditText username;
    EditText email;
    EditText password;
    EditText phone;
    Button register;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle save){
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.signup,container,false);
        username=root.findViewById(R.id.username_signup);
        password=root.findViewById(R.id.password_signup);
        email=root.findViewById(R.id.email_signup);
        phone=root.findViewById(R.id.phone_signup);
        register=root.findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().equals("abc")&&password.getText().toString().equals("123456")) {
                    Toast.makeText(getContext(), "Sign Up Success", Toast.LENGTH_LONG).show();
                    
                } else {
                    Toast.makeText(getContext(), "Sign Up Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
        return root;
    }


}