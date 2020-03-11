package com.example.jnuelibrary;

public class BorrowInformation {

    public String userName, bookID;

    public BorrowInformation() {
    }

    public BorrowInformation(String userName, String bookID) {
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
