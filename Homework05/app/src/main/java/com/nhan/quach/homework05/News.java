package com.nhan.quach.homework05;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.IOException;

/**
 * Created by nhan on 4/26/17.
 */

public class News implements Parcelable {
    String title, description, link, comments, pubDate, creator;
    String thumbnail;

    public News() {
    }

    public News(String title, String description, String link, String comments, String pubDate, String creator, String thumbnail) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.comments = comments;
        this.pubDate = pubDate;
        this.creator = creator;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public News(Parcel in) throws IOException {
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) throws IOException {
        this.title = in.readString();
        this.description = in.readString();
        this.link = in.readString();
        this.comments = in.readString();
        this.pubDate = in.readString();
        this.creator = in.readString();
        this.thumbnail = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public News createFromParcel(Parcel in) {
            try {
                return new News(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

        @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(link);
        dest.writeString(comments);
        dest.writeString(pubDate);
        dest.writeString(creator);
        dest.writeString(thumbnail);
    }
}
