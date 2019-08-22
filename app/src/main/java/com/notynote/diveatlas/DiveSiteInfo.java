package com.notynote.diveatlas;

public class DiveSiteInfo {
    private String diveSiteName;
    private String diveSiteType;
    private String diveSiteDepth;
    private String diveSiteCoor;
    private String diveSiteBio;

    //constructor
    public DiveSiteInfo (String name, String type, String depth, String coor, String bio){
        this.diveSiteName = name;
        this.diveSiteType = type;
        this.diveSiteDepth = depth;
        this.diveSiteCoor = coor;
        this.diveSiteBio = bio;
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
}
