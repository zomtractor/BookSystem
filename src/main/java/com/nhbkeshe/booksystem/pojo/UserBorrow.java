package com.nhbkeshe.booksystem.pojo;

public class UserBorrow {
    private int userID;
    private int bookID;
    private long borrowTime;

    public UserBorrow(int userID, int bookID, long borrowTime) {
        this.userID = userID;
        this.bookID = bookID;
        this.borrowTime = borrowTime;
    }

    public int getUserID() {
        return userID;
    }

    public UserBorrow setUserID(int userID) {
        this.userID = userID;
        return this;
    }

    public int getBookID() {
        return bookID;
    }

    public UserBorrow setBookID(int bookID) {
        this.bookID = bookID;
        return this;
    }

    public long getBorrowTime() {
        return borrowTime;
    }

    public UserBorrow setBorrowTime(long borrowTime) {
        this.borrowTime = borrowTime;
        return this;
    }
}
