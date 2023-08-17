package com.example.mobile_adproject.models;

public class Application {

    private Long id;
    private Book book;
    private Member recipient;
    private int status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Member getRecipient() {
        return recipient;
    }

    public void setRecipient(Member recipient) {
        this.recipient = recipient;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
