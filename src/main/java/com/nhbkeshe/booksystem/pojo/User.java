package com.nhbkeshe.booksystem.pojo;

import java.util.List;

public class User {
    private int id;
    private String username;
    private String password;
    private List<Book> bookedList;
    private int borrowingNum;
    private boolean isManager;

    public boolean isManager() {
        return isManager;
    }

    public User setManager(boolean manager) {
        isManager = manager;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", bookedList=" + bookedList +
                ", borrowingNum=" + borrowingNum +
                '}';
    }

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<Book> getBookedList() {
        return bookedList;
    }

    public User setBookedList(List<Book> bookedList) {
        this.bookedList = bookedList;
        return this;
    }

    public int getBorrowingNum() {
        return borrowingNum;
    }

    public User setBorrowingNum(int borrowingNum) {
        this.borrowingNum = borrowingNum;
        return this;
    }
}
