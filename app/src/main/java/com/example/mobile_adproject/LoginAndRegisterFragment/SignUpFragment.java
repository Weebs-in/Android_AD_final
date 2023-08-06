package com.example.mobile_adproject.LoginAndRegisterFragment;
import com.example.mobile_adproject.R;
import com.example.mobile_adproject.api_responses.SignupApiResponse;
import com.example.mobile_adproject.models.Member;
import com.example.mobile_adproject.retrofit.MemberApi;
import com.example.mobile_adproject.retrofit.RetrofitService;

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

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpFragment extends Fragment {
    EditText inputUsername;
    EditText inputEmail;
    EditText inputPassword;
    EditText inputPhone;
    Button buttonRegister;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle save){
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.signup,container,false);

        RetrofitService retrofitService = new RetrofitService();
        MemberApi memberApi = retrofitService.getRetrofit().create(MemberApi.class);

        inputUsername=root.findViewById(R.id.username_signup);
        inputPassword=root.findViewById(R.id.password_signup);
        inputEmail=root.findViewById(R.id.email_signup);
        inputPhone=root.findViewById(R.id.phone_signup);

        buttonRegister=root.findViewById(R.id.register_button);
        buttonRegister.setOnClickListener(view -> {
            String username = String.valueOf(inputUsername.getText());
            String password = String.valueOf(inputPassword.getText());
            String email = String.valueOf(inputEmail.getText());
            String phone = String.valueOf(inputPhone.getText());

            Member member = new Member();
            member.setUsername(username);
            member.setPassword(password);
            member.setEmail(email);
            member.setPhoneNumber(phone);
            member.setBirthday(LocalDate.now());
            member.setBio("Bio");
            member.setDisplayName("DisplayName");
            member.setGender(0);
            member.setAvatar("Avatar");

            memberApi.create(member)
                    .enqueue(new Callback<SignupApiResponse>() {
                        @Override
                        public void onResponse(Call<SignupApiResponse> call, Response<SignupApiResponse> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(getContext(), "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getContext(), "Response not ok", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<SignupApiResponse> call, Throwable t) {
                            Toast.makeText(getContext(), "Account Failed to Create: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            t.printStackTrace(); // Print the full stack trace to see the detailed error
                        }
                    });
        }
   );
        return root;
    }


}