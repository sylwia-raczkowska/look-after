package com.hfad.lookafter;


import com.google.gson.annotations.SerializedName;

public class Book {
    @SerializedName("author")
    private String author;
    @SerializedName("title")
    private String title;
    @SerializedName("coverResourceId")
    private String coverResourcePath;
    @SerializedName("contentResourceId")
    private String contentResourcePath;

    public Book(String author, String title, String coverResourceId, String content, boolean isFavourite) {
        this.author = author;
        this.title = title;
        this.coverResourcePath = coverResourceId;
        this.contentResourcePath = content;
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

    public String getCoverResourcePath() {
        return coverResourcePath;
    }

    public String getContentResourcePath() {
        return contentResourcePath;
    }

    private int privateMethod() {
        return 5;
    }
}

