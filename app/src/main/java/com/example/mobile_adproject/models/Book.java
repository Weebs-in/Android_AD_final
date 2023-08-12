package com.example.mobile_adproject.models;

import java.time.LocalDateTime;

public class Book {
    //Attributes
    private Long id;
    private int isbn;
    private String title;
    private String author;
    private String cover;
    private int bookCondition;
    private String description;
    private String genre;
    private String press;
    private int language;
    private int status;
    private int likeCount;
    private Donor donor;

    // Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getBookCondition() {
        return bookCondition;
    }

    public void setBookCondition(int bookCondition) {
        this.bookCondition = bookCondition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public Donor getDonor() {
        return donor;
    }
    public void setDonor(Donor donor) {
        this.donor = donor;
    }

}
