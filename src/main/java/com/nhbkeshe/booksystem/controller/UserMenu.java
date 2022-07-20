package com.nhbkeshe.booksystem.controller;

import com.nhbkeshe.booksystem.pojo.Book;
import com.nhbkeshe.booksystem.pojo.User;
import com.nhbkeshe.booksystem.service.BookSystem;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserMenu extends JFrame implements ActionListener {
    private JPanel rootPanel;
    private JTextField searchField;
    private JButton searchButton;
    private JButton moreSearchButton;
    private JButton myBorrowButton;
    private JTable bookTable;
    private JScrollPane bookSetPanel;
    private JButton borrowButton;
    private JButton readButton;
    private JLabel welcomelabel;

    private Object[][] content;

    private User user;

    private final String[] tableTitles = {"书名", "作者", "出版社", "定价", "余量"};

    protected void setFrameArgs() {
        this.add(rootPanel);
        this.setTitle("图书借阅管理系统");
        this.setBounds(255, 255, 600, 400);
        this.setResizable(false);
        //this.add(buttonsPanel);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        welcomelabel.setText("欢迎你，" + user.getUsername());
    }

    protected void addButtonsListener() {
        searchButton.addActionListener(this);
        moreSearchButton.addActionListener(this);
        myBorrowButton.addActionListener(this);
        borrowButton.addActionListener(this);
        readButton.addActionListener(this);
    }

    protected Object[][] getTableContent(List<Book> bookList) {
        Object[][] res = new Object[bookList.size()][5];
        int ptr = 0;
        for (Book book : bookList) {
            res[ptr][0] = book.getBookName();
            res[ptr][1] = book.getAuthorName();
            res[ptr][2] = book.getPress();
            res[ptr][3] = "￥" + book.getPrice();
            res[ptr][4] = book.getRemains();
            ptr++;
        }
        return this.content = res;
    }

    protected void updateTable(List<Book> bookList) {
        Object[][] content = getTableContent(bookList);
        bookTable.setModel(new MyTableModel(content, tableTitles, user.isManager()));
    }

    public UserMenu() {
    }

    public UserMenu(User user) throws HeadlessException {
        this.user = user;
        this.add(rootPanel);
        bookTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        addButtonsListener();
        List<Book> allBooks = BookSystem.getInstance().getAllBooks();
        updateTable(allBooks);
        setFrameArgs();
    }
    @Override
    public void dispose() {
        new Login();
        super.dispose();
    }


    protected void searchBookByName() {
        String bookName = searchField.getText();
        List<Book> bookList = BookSystem.getInstance().searchBooksByKeyword(bookName);
        updateTable(bookList);
    }

    protected void searchByCondition() {
        SearchBox dialog = new SearchBox(this);

        var response = dialog.getResponse();
        if (response == null) return;
        List<Book> bookList = BookSystem.getInstance().searchBookByConditions(response.book, response.isByBookName, response.isByPrice, response.isDescending, response.isUnclear);
        updateTable(bookList);
    }

    protected void borrowBook() {
        int idx = bookTable.getSelectedRow();
        if (idx < 0) {
            JOptionPane.showMessageDialog(null, "请选择一本书！");
            return;
        }
        String bookName = (String) content[idx][0];
        String authorName = (String) content[idx][1];
        int status = BookSystem.getInstance().borrowBook(user.getId(), bookName, authorName);
        if (status == 1) {
            user.setBorrowingNum(user.getBorrowingNum() + 1);
            JOptionPane.showMessageDialog(null, "借阅成功");
        } else if (status == 0) {
            JOptionPane.showMessageDialog(null, "您已借阅过该书！");
        } else {
            JOptionPane.showMessageDialog(null, "该书已被借完");
        }
    }

    protected void readBook() {
        int idx = bookTable.getSelectedRow();
        if (idx < 0) {
            JOptionPane.showMessageDialog(null, "请选择一本书！");
            return;
        }
        String bookName = (String) content[idx][0];
        String authorName = (String) content[idx][1];
        Book book = BookSystem.getInstance().searchBookByNames(bookName, authorName);
        new BookReader(false, book);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            searchBookByName();
        } else if (e.getSource() == moreSearchButton) {
            searchByCondition();
        } else if (e.getSource() == borrowButton) {
            borrowBook();
        } else if (e.getSource() == readButton) {
            readBook();
        } else if (e.getSource() == myBorrowButton) {
            new BorrowedList(user);
        }
    }


}

class MyTableModel extends AbstractTableModel{
    private Object[][] contents;
    private String[] tableTitles;
    private boolean isManager;

    public MyTableModel(Object[][] contents, String[] tableTitles, boolean isManager) {
        this.contents = contents;
        this.tableTitles = tableTitles;
        this.isManager=isManager;
    }
    public String getColumnName(int column) { return tableTitles[column].toString(); }
    public int getRowCount() { return contents.length; }
    public int getColumnCount() { return tableTitles.length; }
    public Object getValueAt(int row, int col) { return contents[row][col]; }
    public boolean isCellEditable(int row, int column) {return isManager;}
    public void setValueAt(Object value, int row, int col) {
        contents[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}
