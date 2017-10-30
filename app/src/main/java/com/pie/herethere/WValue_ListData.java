package com.pie.herethere;

public class WValue_ListData {
    String lat;
    String lon;
    String sky;
    String time;

    String getLat() {
        return lat;
    }
    String getLon() {
        return lon;
    }
    String getSky() {
        return sky;
    }
    String getTime() {
        return time;
    }

    WValue_ListData(String lat, String lon, String sky, String time) {
        this.lat = lat;
        this.lon = lon;
        this.sky = sky;
        this.time = time;
    }
}