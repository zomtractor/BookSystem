package com.nhbkeshe.booksystem.controller;

import com.nhbkeshe.booksystem.pojo.Book;
import com.nhbkeshe.booksystem.pojo.User;
import com.nhbkeshe.booksystem.service.BookSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class BorrowedList extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable bookTable;

    private User user;
    private Object[][] content;
    private String[] titles = {"书名", "作者", "借阅日期"};

    public BorrowedList(User user) {
        this.user = user;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // 单击 X 时调用 onCancel()
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // 遇到 ESCAPE 时调用 onCancel()
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        loadData();
        setBounds(600, 300, 400, 300);
        this.setResizable(false);
        setVisible(true);
    }

    private void loadData() {
        List<Book> bookList = BookSystem.getInstance().searchBookByBorrow();
        Object[][] res = new Object[bookList.size()][3];
        int ptr = 0;
        for (Book book : bookList) {
            res[ptr][0] = book.getBookName();
            res[ptr][1] = book.getAuthorName();
            res[ptr][2] = book.getPress();
            ptr++;
        }
        content = res;
        bookTable.setModel(new MyTableModel(res, titles, false));
    }


    private void onOK() {
        int idx = bookTable.getSelectedRow();
        if (idx < 0) {
            JOptionPane.showMessageDialog(null, "请选择一本书！");
            return;
        }
        String bookName = (String) content[idx][0];
        String authorName = (String) content[idx][1];
        if (BookSystem.getInstance().returnBook(user.getId(), bookName, authorName)) {
            JOptionPane.showMessageDialog(null, "归还成功。");
            loadData();
        } else {
            JOptionPane.showMessageDialog(null, "该书已经归还。");
        }

    }

    private void onCancel() {
        // 必要时在此处添加您的代码
        dispose();
    }

}
