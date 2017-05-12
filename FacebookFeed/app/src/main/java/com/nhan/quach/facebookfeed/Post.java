package com.nhan.quach.facebookfeed;

/**
 * Created by nhan on 5/12/17.
 */

public class Post {
    String message, created_date, id;

    public Post() {
    }

    public Post(String message, String created_date, String id) {
        this.message = message;
        this.created_date = created_date;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
