package com.example.jnuelibrary;

public class BookInformation {

    public String bid, bname, bwritter, bdescription, bcatagory;


    public BookInformation() {
    }

    public BookInformation(String bid, String bname, String bwritter, String bdescription, String bcatagory) {
        this.bid = bid;
        this.bname = bname;
        this.bwritter = bwritter;
        this.bdescription = bdescription;
        this.bcatagory = bcatagory;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getBwritter() {
        return bwritter;
    }

    public void setBwritter(String bwritter) {
        this.bwritter = bwritter;
    }

    public String getBdescription() {
        return bdescription;
    }

    public void setBdescription(String bdescription) {
        this.bdescription = bdescription;
    }

    public String getBcatagory() {
        return bcatagory;
    }

    public void setBcatagory(String bcatagory) {
        this.bcatagory = bcatagory;
    }
}
