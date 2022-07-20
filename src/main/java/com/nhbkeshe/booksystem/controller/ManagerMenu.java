package com.nhbkeshe.booksystem.controller;

import com.nhbkeshe.booksystem.pojo.Book;
import com.nhbkeshe.booksystem.pojo.User;
import com.nhbkeshe.booksystem.service.BookSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ManagerMenu extends JFrame implements ActionListener {
    private JPanel rootPanel;
    private JTextField searchField;
    private JButton searchButton;
    private JTable bookTable;
    private JButton modifyButton;
    private JButton deleteButton;
    private JButton newBookButton;
    private JLabel welcomelabel;
    private User manager;
    private Object[][] content;

    public ManagerMenu(User user) throws HeadlessException {
        super();
        this.manager = user;
        bookTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        setFrameArgs();
        addButtonsListener();
        searchBookByName();
    }

    protected void setFrameArgs() {
        this.add(rootPanel);
        this.setTitle("图书借阅管理系统");
        this.setBounds(255, 255, 600, 400);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        welcomelabel.setText("欢迎你，" + manager.getUsername());
    }

    protected void addButtonsListener() {
        searchButton.addActionListener(this);
        modifyButton.addActionListener(this);
        deleteButton.addActionListener(this);
        newBookButton.addActionListener(this);
    }

    protected void searchBookByName() {
        String bookName = searchField.getText();
        List<Book> bookList = BookSystem.getInstance().searchBooksByKeyword(bookName);
        updateTable(bookList);
    }

    protected void updateTable(List<Book> bookList) {
        Object[][] content = getTableContent(bookList);
        bookTable.setModel(new MyTableModel(content, tableTitles, manager.isManager()));
    }

    private final String[] tableTitles = {"书名", "作者", "出版社", "定价", "已借"};

    protected Object[][] getTableContent(List<Book> bookList) {
        Object[][] res = new Object[bookList.size()][5];
        int ptr = 0;
        for (Book book : bookList) {
            res[ptr][0] = book.getBookName();
            res[ptr][1] = book.getAuthorName();
            res[ptr][2] = book.getPress();
            res[ptr][3] = "￥" + book.getPrice();
            res[ptr][4] = 200 - book.getRemains();
            ptr++;
        }
        return this.content = res;
    }

    private Book getSelectedBook() {
        int idx = bookTable.getSelectedRow();
        if (idx < 0) {
            JOptionPane.showMessageDialog(null, "请选择一本书！");
            return null;
        }
        String bookName = (String) content[idx][0];
        String authorName = (String) content[idx][1];
        Book book = BookSystem.getInstance().searchBookByNames(bookName, authorName);
        return book;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            searchBookByName();
        } else if (e.getSource() == modifyButton) {
            modifyBook();
        } else if (e.getSource() == newBookButton) {
            addNewBook();
        } else if (e.getSource() == deleteButton) {
            deleteBook();
        }
    }
    @Override
    public void dispose() {
        new Login();
        super.dispose();
    }

    private void deleteBook() {
        Book book = getSelectedBook();
        if (book == null) return;
        int res = JOptionPane.showConfirmDialog(null, "是否确认删除？", null, JOptionPane.YES_NO_OPTION);
        if (res == 0) {
            BookSystem.getInstance().deleteBook(book.getId());
            JOptionPane.showMessageDialog(null, "删除成功。");
        } else {
            return;
        }
    }

    private void addNewBook() {
        new BookEdit(null);
    }

    private void modifyBook() {
        Book book = getSelectedBook();
        if (book == null) return;
        new BookEdit(book);
    }

}
