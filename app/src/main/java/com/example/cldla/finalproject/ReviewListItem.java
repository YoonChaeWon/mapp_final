package com.example.cldla.finalproject;

/**
 * Created by hansol on 2017-12-13.
 */

public class ReviewListItem {
    private int id;
    private String title;
    private String author;
    private String writeDate;

    //걍 다 겟셋

    public void setId(int id) {this.id = id;}

    public void setTitle(String title){
        this.title = title;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public void setWriteDate(String writeDate){
        this.writeDate = writeDate;
    }

    public int getId(){return id;}

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getWriteDate() {
        return writeDate;
    }
}
