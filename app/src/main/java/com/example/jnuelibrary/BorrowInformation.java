package com.example.jnuelibrary;

public class BorrowInformation {

    public String userName, bookID;
    public int status;

    public BorrowInformation() {
    }

    public BorrowInformation(String userName, String bookID, int status) {
        this.userName = userName;
        this.bookID = bookID;
        this.status = status;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
