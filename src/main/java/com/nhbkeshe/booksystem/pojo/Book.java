package com.nhbkeshe.booksystem.pojo;

public class Book {
    private int id;
    private String bookName;
    private String authorName;
    private String press;//出版社
    private double price;
    private int remains;
    private String demo;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", authorName='" + authorName + '\'' +
                ", press='" + press + '\'' +
                ", price=" + price +
                ", remains=" + remains +
                ", demo='" + demo + '\'' +
                "}\n";
    }

    public String getDemo() {
        return demo;
    }

    public Book setDemo(String demo) {
        this.demo = demo;
        return this;
    }

    public int getId() {
        return id;
    }

    public Book setId(int id) {
        this.id = id;
        return this;
    }

    public String getBookName() {
        return bookName;
    }

    public Book setBookName(String bookName) {
        this.bookName = bookName;
        return this;
    }

    public String getAuthorName() {
        return authorName;
    }

    public Book setAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public String getPress() {
        return press;
    }

    public Book setPress(String press) {
        this.press = press;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Book setPrice(double price) {
        this.price = price;
        return this;
    }

    public int getRemains() {
        return remains;
    }

    public Book setRemains(int remains) {
        this.remains = remains;
        return this;
    }
}

