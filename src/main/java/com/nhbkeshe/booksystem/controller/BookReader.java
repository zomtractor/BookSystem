package com.nhbkeshe.booksystem.controller;

import com.nhbkeshe.booksystem.pojo.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BookReader extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel nameLabel;
    private JTextArea bookArea;

    private boolean isManager;

    public BookReader(boolean isManager, Book book) {
        if (!isManager) {
            buttonOK.setVisible(false);
            buttonOK.setEnabled(false);
        }
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
        setTitle("《" + book.getBookName() + "》");
        bookArea.setText(book.getDemo());
        bookArea.setFont(new Font(null, 0, 20));
        setBounds(600, 300, 440, 300);
        this.setResizable(false);
        setVisible(true);
    }

    private void onOK() {
        // 在此处添加您的代码
        dispose();
    }

    private void onCancel() {
        dispose();
    }

}
