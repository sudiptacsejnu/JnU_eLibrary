package com.example.jnuelibrary;

public class BookInformation {

    public String bid, bname, bwritter, bdescription, bcategory, bquantity;


    public BookInformation() {
    }

    public BookInformation(String bid, String bname, String bwritter, String bdescription, String bcategory, String bquantity) {
        this.bid = bid;
        this.bname = bname;
        this.bwritter = bwritter;
        this.bdescription = bdescription;
        this.bcategory = bcategory;
        this.bquantity = bquantity;
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

    public String getBcategory() {
        return bcategory;
    }

    public void setBcategory(String bcategory) {
        this.bcategory = bcategory;
    }

    public String getBquantity() {
        return bquantity;
    }

    public void setBquantity(String bquantity) {
        this.bquantity = bquantity;
    }
}
