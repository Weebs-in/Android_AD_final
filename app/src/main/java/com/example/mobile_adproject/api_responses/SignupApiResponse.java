package com.example.mobile_adproject.api_responses;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class SignupApiResponse {
    @SerializedName("username")
    public String username;
    @SerializedName("displayName")
    public String displayName;
    @SerializedName("phoneNumber")
    public String phoneNumber;
    @SerializedName("email")
    public String email;
    @SerializedName("birthday")
    public LocalDate birthday;
    @SerializedName("gender")
    public int gender;
    @SerializedName("bio")
    public String bio;
    @SerializedName("avatar")
    public String avatar;

}
