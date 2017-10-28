package com.pie.herethere;

public class AniWhere_ListData {
    String title;
    String img;
    String contentId;

    String getTitle() {
        return title;
    }
    String getImg() {
        return img;
    }
    String getContentId() {
        return contentId;
    }

    public AniWhere_ListData(String title, String img, String contentId) {
        this.title = title;
        this.img = img;
        this.contentId = contentId;
    }
}
