package com.example.sustainabyte;

public class Post {
    private String userIcon;
    private String username;
    private String title;
    private String content;

    private String comment;
    private int like;
    private long timestamp;

    public Post(String userIcon, String username, String title, String content, int like, long timestamp, String comment) {
        this.userIcon = userIcon;
        this.username = username;
        this.title = title;
        this.content = content;
        this.like = like;
        this.timestamp = timestamp;
        this.comment = comment;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
