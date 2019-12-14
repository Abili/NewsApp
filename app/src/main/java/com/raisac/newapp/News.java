package com.raisac.newapp;

public class News {
    private String title;
    private String date;
    private String url;
    private String section;
    private String Author;




    public News(String title, String date, String url, String section, String Author) {
        this.title = title;
        this.date = date;
        this.url = url;
        this.section = section;
        this.Author = Author;

    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String place) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }


    public void setDate(String date) {
        this.date = date;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

}
