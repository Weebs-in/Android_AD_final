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
