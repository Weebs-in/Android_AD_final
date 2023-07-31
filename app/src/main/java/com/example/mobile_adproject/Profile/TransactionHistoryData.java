package com.example.mobile_adproject.Profile;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionHistoryData {
    private Long transaction_id;
    private Long book_id;
    private Long donor_id;
    private String donor_name;
    private Long recipient_id;
    private String recipient_name;


    private LocalDateTime create_time;
    private LocalDateTime complete_time;
    private int status;
    private Long collection_point_id;
    private BigDecimal rating_donor;
    private BigDecimal rating_recipient;
    private LocalDateTime gmt_create;
    private LocalDateTime gmt_modified;


}
