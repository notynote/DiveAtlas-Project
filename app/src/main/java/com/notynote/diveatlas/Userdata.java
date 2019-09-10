package com.notynote.diveatlas;

public class Userdata {

    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String certLevel;
    private String certNo;
    private String certAgent;
    private String imageUrl;

    public Userdata() {
    }

    public Userdata(String email, String firstName, String lastName, String phone, String certLevel, String certNo, String certAgent, String image) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.certLevel = certLevel;
        this.certNo = certNo;
        this.certAgent = certAgent;
        this.imageUrl = image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCertLevel() {
        return certLevel;
    }

    public void setCertLevel(String certLevel) {
        this.certLevel = certLevel;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getCertAgent() {
        return certAgent;
    }

    public void setCertAgent(String certAgent) {
        this.certAgent = certAgent;
    }
}
