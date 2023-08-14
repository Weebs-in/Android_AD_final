package com.example.mobile_adproject.api_responses;

import com.google.gson.annotations.SerializedName;

public class LoginApiResponse {
    @SerializedName("accessToken")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


}
