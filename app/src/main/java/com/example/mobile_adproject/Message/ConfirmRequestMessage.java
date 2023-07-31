package com.example.mobile_adproject.Message;

public class ConfirmRequestMessage {
    private Long book_id;
    private String title;
    private Long donor_id;
    private String donor_username;
    private Long member_id;
    private String member_userName;
    private boolean confirmRequest_or_noticeRequestStatus;
    private boolean noticeRequestStatus;

    public ConfirmRequestMessage(String title, String member_userName, boolean confirmRequest_or_noticeRequestStatus, boolean noticeRequestStatus) {
        this.title = title;
        this.member_userName = member_userName;
        this.confirmRequest_or_noticeRequestStatus = confirmRequest_or_noticeRequestStatus;
        this.noticeRequestStatus = noticeRequestStatus;
    }

    public Long getBook_id() {
        return book_id;
    }

    public String getTitle() {
        return title;
    }

    public Long getDonor_id() {
        return donor_id;
    }

    public String getDonor_username() {
        return donor_username;
    }

    public Long getMember_id() {
        return member_id;
    }

    public String getMemberUserName() {
        return member_userName;
    }

    public boolean getIsConfirmRequest_or_noticeRequestStatus() {
        return confirmRequest_or_noticeRequestStatus;
    }

    public boolean getIsNoticeRequestStatus() {
        return noticeRequestStatus;
    }
}

