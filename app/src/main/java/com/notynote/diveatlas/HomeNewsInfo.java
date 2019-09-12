package com.notynote.diveatlas;

public class HomeNewsInfo {

    String homeNewsHeading, homeNewsDate, homeNewsDesc, homeNewsImage;

    public HomeNewsInfo (String heading, String date, String homeNewsDesc, String image){
        this.homeNewsHeading = heading;
        this.homeNewsDate = date;
        this.homeNewsDesc = homeNewsDesc;
        this.homeNewsImage = image;
    }

    public String getHomeNewsHeading() {
        return homeNewsHeading;
    }

    public void setHomeNewsHeading(String homeNewsHeading) {
        this.homeNewsHeading = homeNewsHeading;
    }

    public String getHomeNewsDate() {
        return homeNewsDate;
    }

    public void setHomeNewsDate(String homeNewsDate) {
        this.homeNewsDate = homeNewsDate;
    }

    public String getHomeNewsDesc() {
        return homeNewsDesc;
    }

    public void setHomeNewsDesc(String homeNewsDesc) {
        this.homeNewsDesc = homeNewsDesc;
    }

    public String getHomeNewsImage() {
        return homeNewsImage;
    }

    public void setHomeNewsImage(String homeNewsImage) {
        this.homeNewsImage = homeNewsImage;
    }
}
