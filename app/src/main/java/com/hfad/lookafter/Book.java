package com.hfad.lookafter;


import com.google.gson.annotations.SerializedName;

public class Book {
    @SerializedName("author")
    private String author;
    @SerializedName("title")
    private String title;
    @SerializedName("coverResourceId")
    private int coverResourceId;
    @SerializedName("contentResourceId")
    private int contentResourceId;

    public Book(String author, String title, int coverResourceId, int content, boolean isFavourite) {
        this.author = author;
        this.title = title;
        this.coverResourceId = coverResourceId;
        this.contentResourceId = content;
        this.isFavourite = isFavourite;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    private boolean isFavourite;

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCoverResourceId() {
        return coverResourceId;
    }

    public int getContentResourceId() {
        return contentResourceId;
    }
}

