package com.example.mobile_adproject.api_responses;

import com.google.gson.annotations.SerializedName;

public class LoginApiResponse {
    @SerializedName("accessToken")
    private String accessToken;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
