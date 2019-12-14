package com.raisac.newapp;

class News {
    private final String title;
    private final String date;
    private final String url;
    private final String section;
    private final String Author;


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


    public String getUrl() {
        return url;
    }


    public String getTitle() {
        return title;
    }


    public String getDate() {
        return date;
    }


    public String getSection() {
        return section;
    }


}
