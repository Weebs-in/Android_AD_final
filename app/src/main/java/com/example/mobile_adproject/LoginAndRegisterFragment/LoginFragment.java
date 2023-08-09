package com.example.mobile_adproject.LoginAndRegisterFragment;
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

import com.example.mobile_adproject.R;
import com.example.mobile_adproject.api_responses.LoginApiResponse;
import com.example.mobile_adproject.retrofit.MemberApi;
import com.example.mobile_adproject.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {
    EditText inputUsername;
    EditText inputPassword;
    Button login;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle save){
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.login,container,false);

        RetrofitService retrofitService = new RetrofitService();
        MemberApi memberApi = retrofitService.getRetrofit().create(MemberApi.class);

        inputUsername=root.findViewById(R.id.username_login);
        inputPassword=root.findViewById(R.id.password_login);
        login=root.findViewById(R.id.login_button);
        login.setOnClickListener(view -> {
            String username = String.valueOf(inputUsername.getText());
            String password = String.valueOf(inputPassword.getText());

            LoginApiResponse loginApiResponse = new LoginApiResponse();
            loginApiResponse.setUsername(username);
            loginApiResponse.setPassword(password);
            memberApi.login(loginApiResponse)
                    .enqueue(new Callback<LoginApiResponse>() {
                        @Override
                        public void onResponse(Call<LoginApiResponse> call, Response<LoginApiResponse> response) {
                            if (response.isSuccessful()) {
                                LoginApiResponse responseBody = response.body();
                                if (responseBody != null) {
                                    String token = responseBody.getAccessToken();
                                    Toast.makeText(getContext(), "Login Successful!" + token, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Login Failed: Empty Response Body", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), "Login Failed：" + response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }


                        @Override
                        public void onFailure(Call<LoginApiResponse> call, Throwable t) {
                            Toast.makeText(getContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
                            t.printStackTrace(); // Print the full stack trace to see the detailed error
                           // Log.d("TAG", "This is a debug log message.");
                        }
                    });
        });

        return root;

    }
}





