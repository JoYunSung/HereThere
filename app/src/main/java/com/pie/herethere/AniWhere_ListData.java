package com.pie.herethere;

public class AniWhere_ListData {
    String title;
    String img;
    String distance;    //거리

    String getTitle() {
        return title;
    }
    String getImg() {
        return img;
    }
    String getDistance() { return distance; }

    public AniWhere_ListData(String title, String img, String distance) {
        this.title = title;
        this.img = img;
        this.distance = distance;
    }
}
