package com.hfad.lookafter;

/**
 * Created by Niki on 2016-10-24.
 */

public class Book {
    private String author;
    private String title;
    private int cover_resource_id;
    private int content_resource_id;

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    private boolean isFavourite;

    Book (String author, String title, int cover_resource_id, boolean isFavourite){
        this.author = author;
        this.title = title;
        this.cover_resource_id = cover_resource_id;
        this.isFavourite = isFavourite;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCover_resource_id() {
        return cover_resource_id;
    }

    public void setCover_resource_id(int cover_resource_id) {
        this.cover_resource_id = cover_resource_id;
    }

    public int getContent_resource_id() {
        return content_resource_id;
    }

    public void setContent_resource_id(int content_resource_id) {
        this.content_resource_id = content_resource_id;
    }
}

