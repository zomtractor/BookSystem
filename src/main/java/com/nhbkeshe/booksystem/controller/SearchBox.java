package com.nhbkeshe.booksystem.controller;
import com.nhbkeshe.booksystem.pojo.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SearchBox extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField bookNameField;
    private JTextField authorNameField;
    private JTextField publisherField;
    private JCheckBox bookNameCheckBox;
    private JCheckBox priceCheckBox;
    private JCheckBox descCheckBox;
    private JRadioButton unclearRatio;

    private Book book;

    public SearchBox(Frame owner) {
        super(owner);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // 单击 X 时调用 onCancel()
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // 遇到 ESCAPE 时调用 onCancel()
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setTitle("高级搜索");
        setBounds(600, 300, 300, 200);
        setVisible(true);
    }

    //点击搜索按钮时，获取输入的书籍信息
    private void onOK() {
        String bookName = bookNameField.getText().trim();
        String authorName = authorNameField.getText().trim();
        String press = publisherField.getText().trim();
        book = new Book().setBookName(bookName)
                .setAuthorName(authorName)
                .setPress(press);
        dispose();
    }

    public SearchConditions getResponse() {
        if (book == null) return null;
        return new SearchConditions(book,
                bookNameCheckBox.isSelected(),
                priceCheckBox.isSelected(),
                descCheckBox.isSelected(),
                unclearRatio.isSelected());
    }

    ;

    private void onCancel() {
        // 必要时在此处添加您的代码
        dispose();
    }

}

class SearchConditions{
    public Book book;
    public boolean isByBookName;
    public boolean isByPrice;
    public boolean isDescending;
    public boolean isUnclear;

    public SearchConditions(Book book, boolean isByPrice, boolean isByAuthorName, boolean isDescending, boolean isUnclear) {
        this.book = book;
        this.isByBookName = isByPrice;
        this.isByPrice = isByAuthorName;
        this.isDescending = isDescending;
        this.isUnclear = isUnclear;
    }
}

