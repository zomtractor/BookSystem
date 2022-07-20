package com.nhbkeshe.booksystem.controller;

import com.nhbkeshe.booksystem.service.BookSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener {
    private JTextField userNameField;
    private JTextField passwordField;
    private JButton userLoginButton;
    private JButton managerLoginButton;
    private JButton clearButton;
    private JPanel rootPanel;

    public Login() throws HeadlessException {
        setFrameArgs();
        addButtonListener();
    }

    private void setFrameArgs() {
        this.add(rootPanel);
        this.setTitle("图书管理系统登录界面");
        this.setBounds(255, 255, 600, 180);
        this.setResizable(false);
        //this.add(buttonsPanel);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void addButtonListener() {
        managerLoginButton.addActionListener(this);
        userLoginButton.addActionListener(this);
        clearButton.addActionListener(this);
    }

    private void loginFunction(boolean isManager) {
        String username = userNameField.getText().trim();
        String password = passwordField.getText().trim();
        if ("".equals(username) || "".equals(password)) {
            JOptionPane.showMessageDialog(null, "请输入用户名和密码。");
            return;
        }
        int status = BookSystem.getInstance().login(username, password, isManager);
        if (status == 1) {
            JOptionPane.showMessageDialog(null, "登录成功！");
            if (!isManager)
                new UserMenu(BookSystem.getInstance().getUser());
            else
                new ManagerMenu(BookSystem.getInstance().getUser());
            this.dispose();
        } else if (status == 0) {
            JOptionPane.showMessageDialog(null, "密码错误！");
        } else {
            if (status == -1 && !isManager) {
                int res = JOptionPane.showConfirmDialog(null, "用户不存在，是否现在注册？", null, JOptionPane.YES_NO_OPTION);
                if (res == 0) {
                    BookSystem.getInstance().register(username, password);
                }
            } else {
                JOptionPane.showMessageDialog(null, "用户不存在！");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == userLoginButton) {
            loginFunction(false);
        } else if (e.getSource() == managerLoginButton) {
            loginFunction(true);
        } else if (e.getSource() == clearButton) {
            userNameField.setText("");
            passwordField.setText("");
        }
    }

}
