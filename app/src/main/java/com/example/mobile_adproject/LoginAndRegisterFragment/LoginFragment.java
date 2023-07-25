package com.example.mobile_adproject.LoginAndRegisterFragment;
import com.example.mobile_adproject.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



public class LoginFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle save){
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.login,container,false);
        return root;
    }
}
