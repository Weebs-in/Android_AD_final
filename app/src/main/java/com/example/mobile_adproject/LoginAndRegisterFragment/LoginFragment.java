package com.example.mobile_adproject.LoginAndRegisterFragment;
import com.example.mobile_adproject.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



public class LoginFragment extends Fragment {
    EditText username;
    EditText password;
    Button login;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle save){
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.login,container,false);
        username=root.findViewById(R.id.username_login);
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
        });

        return root;

    }
}
