package com.notynote.diveatlas;

public class DiveSiteInfo {
    String diveSiteName;
    String diveSiteType;
    String diveSiteDepth;
    String diveSiteCoor;
    String diveSiteBio;
    String image;

    //constructor
    public DiveSiteInfo (String name, String type, String depth, String coor, String bio, String imgurl){
        this.diveSiteName = name;
        this.diveSiteType = type;
        this.diveSiteDepth = depth;
        this.diveSiteCoor = coor;
        this.diveSiteBio = bio;
        this.image = imgurl;
    }

    public String getDiveSiteName() {
        return diveSiteName;
    }

    public void setDiveSiteName(String diveSiteName) {
        this.diveSiteName = diveSiteName;
    }

    public String getDiveSiteType() {
        return diveSiteType;
    }

    public void setDiveSiteType(String diveSiteType) {
        this.diveSiteType = diveSiteType;
    }

    public String getDiveSiteDepth() {
        return diveSiteDepth;
    }

    public void setDiveSiteDepth(String diveSiteDepth) {
        this.diveSiteDepth = diveSiteDepth;
    }

    public String getDiveSiteCoor() {
        return diveSiteCoor;
    }

    public void setDiveSiteCoor(String diveSiteCoor) {
        this.diveSiteCoor = diveSiteCoor;
    }

    public String getDiveSiteBio() {
        return diveSiteBio;
    }

    public void setDiveSiteBio(String diveSiteBio) {
        this.diveSiteBio = diveSiteBio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
