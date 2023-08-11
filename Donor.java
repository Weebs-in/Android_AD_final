package com.example.mobile_adproject.models;

import java.time.LocalDate;
import java.util.Date;

public class Donor {
    private int id;
    private LocalDate gmtCreated;
    private LocalDate gmtModified;
    private String username;
    private String displayName;
    private String phoneNumber;
    private String email;
    private String password;
    private Date birthday;
    private int gender;
    private String bio;
    private String avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(LocalDate gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public LocalDate getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDate gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
