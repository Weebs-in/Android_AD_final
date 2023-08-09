package com.example.mobile_adproject.LoginAndRegisterFragment;
import android.os.Bundle;
import android.util.Log;
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

import com.example.mobile_adproject.R;
import com.example.mobile_adproject.api_responses.LoginApiResponse;
import com.example.mobile_adproject.decoder.JWTDecoder;
import com.example.mobile_adproject.models.Member;
import com.example.mobile_adproject.retrofit.MemberApi;
import com.example.mobile_adproject.retrofit.RetrofitService;

import org.json.JSONException;
import org.json.JSONObject;

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

            Member member = new Member();
            member.setUsername(username);
            member.setPassword(password);

            memberApi.login(member)
                    .enqueue(new Callback<LoginApiResponse>() {
                        @Override
                        public void onResponse(Call<LoginApiResponse> call, Response<LoginApiResponse> response) {
                            LoginApiResponse loginApiResponse = response.body();
                            String jwtToken = loginApiResponse.accessToken;
                            Toast.makeText(getContext(), "Login Successful!" + jwtToken, Toast.LENGTH_SHORT).show();

                            String[] tokenSegments = jwtToken.split("\\.");

                            String decoded = JWTDecoder.decodeBase64(tokenSegments[1]);

                            Log.d("DECODED", decoded);

                            try {
                                JSONObject jsonObject = new JSONObject(decoded);
                                String subValue = jsonObject.getString("sub");

                                System.out.println("Value of 'sub': " + subValue);

                                String authorizationHeader = "Bearer " + jwtToken;

                                memberApi.getMemberById(Integer.parseInt(subValue), authorizationHeader)
                                        .enqueue(new Callback<Member>() {
                                            @Override
                                            public void onResponse(Call<Member> call, Response<Member> response) {
                                                if(response.isSuccessful()){
                                                    Toast.makeText(getContext(), "Get Member Successful!", Toast.LENGTH_SHORT).show();
                                                    Member loggedInMember = response.body();

                                                    String username = loggedInMember.getUsername();
                                                    String email = loggedInMember.getEmail();

                                                    Log.d("USERNAME",username);
                                                    Log.d("EMAIL",email);
                                                }
                                                else {
                                                    Toast.makeText(getContext(), "Get Member Response not ok", Toast.LENGTH_SHORT).show();
                                                }

                                            }

                                            @Override
                                            public void onFailure(Call<Member> call, Throwable t) {
                                                Toast.makeText(getContext(), "Get Member Failed!", Toast.LENGTH_SHORT).show();

                                            }
                                        });
                            } catch (JSONException e) {
                                e.printStackTrace();
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
