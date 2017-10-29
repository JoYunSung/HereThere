package com.pie.herethere;

public class Book_ListData {
    String title;
    String img;
    String contentId;

    public String getTitle() {
        return title;
    }

    public String getImg() {
        return img;
    }

    public String getContentId() {
        return contentId;
    }

    Book_ListData(String title, String img, String contentId) {
        this.title = title;
        this.img = img;
        this.contentId = contentId;
    }
}