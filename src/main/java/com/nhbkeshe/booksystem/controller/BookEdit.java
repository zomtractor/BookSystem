package com.nhbkeshe.booksystem.controller;
import com.nhbkeshe.booksystem.pojo.Book;
import com.nhbkeshe.booksystem.service.BookSystem;

import javax.swing.*;
import java.awt.event.*;

public class BookEdit extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField bookField;
    private JTextField authorNameField;
    private JTextField pressField;
    private JTextField priceField;
    private JTextArea memoArea;

    private Book book;

    public BookEdit(Book book) {
        this.book = book;
        if(book!=null) {
            setTitle("编辑书籍");
            buttonOK.setText("保存");
        }
        else {
            setTitle("新建书籍");
            buttonOK.setText("新建");
        }
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        putText();
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
        setBounds(600, 300, 500, 300);
        this.setResizable(false);
        setVisible(true);
    }

    public void putText() {
        if (book == null) return;
        bookField.setText(book.getBookName());
        authorNameField.setText(book.getAuthorName());
        pressField.setText(book.getPress());
        priceField.setText(String.valueOf(book.getPrice()));
        memoArea.setText(book.getDemo());
    }

    public void updateBook() {
        try {
            book.setBookName(bookField.getText())
                    .setAuthorName(authorNameField.getText())
                    .setPress(pressField.getText()).setPrice(Double.parseDouble(priceField.getText()))
                    .setDemo(memoArea.getText());
            BookSystem.getInstance().editBook(book);
            JOptionPane.showMessageDialog(null, "操作成功。");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "请输入正确的数据！");
        }
    }

    public void insertBook() {
        Book abook = new Book();
        try {
            abook.setBookName(bookField.getText())
                    .setAuthorName(authorNameField.getText())
                    .setPress(pressField.getText()).setPrice(Double.parseDouble(priceField.getText()))
                    .setDemo(memoArea.getText());
            BookSystem.getInstance().insertBook(abook);
            JOptionPane.showMessageDialog(null, "操作成功。");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "请输入正确的数据！");
        }

    }

    private void onOK() {
        if (book == null) insertBook();
        else updateBook();
    }

    private void onCancel() {
        // 必要时在此处添加您的代码
        dispose();
    }

}
