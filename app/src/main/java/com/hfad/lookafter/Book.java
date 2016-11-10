package com.hfad.lookafter;


public class Book {
    private String author;
    private String title;
    private int cover_resource_id;
    private int content_resource_id;

    Book (String author, String title, int cover_resource_id, int content, boolean isFavourite){
        this.author = author;
        this.title = title;
        this.cover_resource_id = cover_resource_id;
        this.content_resource_id = content;
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

    public int getCover_resource_id() {
        return cover_resource_id;
    }

    public int getContent_resource_id() {
        return content_resource_id;
    }
}

