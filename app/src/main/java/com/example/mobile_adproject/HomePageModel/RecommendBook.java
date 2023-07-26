package com.example.mobile_adproject.HomePageModel;

import java.time.LocalDateTime;

public class RecommendBook {
  private Long id;
    private int isbn;

    private String title;
    private String author;
    private String Cover;//image url
  private int book_condition;
    private String description;
    private String genre;
    private String press;
    private int language;
    private int status;
    private Long donor_id;
    private LocalDateTime gmt_created;
    private LocalDateTime gmt_modified;


    public RecommendBook(String title, String author, String cover) {
      this.id = id;
        this.isbn = isbn;

        this.title = title;
        this.author = author;
        Cover = cover;
        this.book_condition = book_condition;
        this.description = description;
        this.genre = genre;
        this.press = press;
        this.language = language;
        this.status = status;
        this.donor_id = donor_id;
        this.gmt_created = gmt_created;
        this.gmt_modified = gmt_modified;

    }


    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getCover() {
        return Cover;
    }

}
