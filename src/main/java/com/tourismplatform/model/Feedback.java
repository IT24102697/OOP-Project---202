package com.tourismplatform.model;

public class Feedback {
    private int id;
    private String username;
    private String comment;

    public Feedback(int id, String username, String comment) {
        this.id = id;
        this.username = username;
        this.comment = comment;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getComment() { return comment; }

    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setComment(String comment) { this.comment = comment; }
}
