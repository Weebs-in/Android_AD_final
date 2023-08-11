package com.example.mobile_adproject.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionHistoryData {
    private Long transaction_id;
    private Book book;
    private Donor donor;
    private Recipient recipient;
    private CollectionPoint collectionPoint;
    private int status;
    private BigDecimal rating_donor;
    private BigDecimal rating_recipient;
    private LocalDateTime gmt_create;
    private LocalDateTime gmt_modified;

    public Long getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(Long transaction_id) {
        this.transaction_id = transaction_id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Donor getDonor() {
        return donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public CollectionPoint getCollectionPoint() {
        return collectionPoint;
    }

    public void setCollectionPoint(CollectionPoint collectionPoint) {
        this.collectionPoint = collectionPoint;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BigDecimal getRating_donor() {
        return rating_donor;
    }

    public void setRating_donor(BigDecimal rating_donor) {
        this.rating_donor = rating_donor;
    }

    public BigDecimal getRating_recipient() {
        return rating_recipient;
    }

    public void setRating_recipient(BigDecimal rating_recipient) {
        this.rating_recipient = rating_recipient;
    }
}
