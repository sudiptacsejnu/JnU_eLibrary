package com.example.jnuelibrary;

public class ReturnInformation {

    public String userName, bookID;

    public ReturnInformation() {
    }

    public ReturnInformation(String userName, String bookID) {
        this.userName = userName;
        this.bookID = bookID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

}
