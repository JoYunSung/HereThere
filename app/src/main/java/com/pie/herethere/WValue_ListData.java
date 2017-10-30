package com.pie.herethere;

public class WValue_ListData {
    String lat;
    String lot;
    String sky;
    String time;

    String getLat() {
        return lat;
    }
    String getLot() {
        return lot;
    }
    String getSky() {
        return sky;
    }
    String getTime() {
        return time;
    }

    WValue_ListData(String lat, String lot, String sky, String time) {
        this.lat = lat;
        this.lot = lot;
        this.sky = sky;
        this.time = time;
    }
}