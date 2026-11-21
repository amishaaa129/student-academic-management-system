package com.college.model;

public class Notice {

    private int noticeId;
    private String title;
    private String content;
    private String postedBy;
    private String createdAt;

    public Notice(int noticeId, String title, String content,
                 String postedBy, String createdAt) {
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
        this.postedBy = postedBy;
        this.createdAt = createdAt;
    }

    public int getNoticeId() {
        return noticeId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "\n------------------------" +
                "\nNotice ID: " + noticeId +
                "\nTitle: " + title +
                "\nContent: " + content +
                "\nPosted By: " + postedBy +
                "\nDate: " + createdAt +
                "\n------------------------";
    }
}