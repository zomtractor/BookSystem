package com.nhbkeshe.booksystem.service;

import com.nhbkeshe.booksystem.pojo.Book;
import com.nhbkeshe.booksystem.pojo.User;
import com.nhbkeshe.booksystem.pojo.UserBorrow;
import com.nhbkeshe.booksystem.util.Sqls;

import java.util.List;

public class BookSystem {
    private User user;
    private BookSystem(){user=null;}
    private static BookSystem bookSystem;

    public User getUser() {
        return user;
    }
    public static BookSystem getInstance(){
        if(bookSystem==null){
            bookSystem = new BookSystem();
        }
        return bookSystem;
    }
    public int login(String username,String password,boolean isManager){
        User user = Sqls.userMapper.selectUserByUsername(username);
        if(user==null || user.getId()==0 ){//用户不存在
            return -1;
        } else if(user.isManager()!=isManager) {//用户身份不匹配
            return -2;
        } else if(!password.equals(user.getPassword())){//密码不正确
            return 0;
        } else {//登录成功
            this.user = user;
            return 1;
        }
    }
    public void register(String username,String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        Sqls.userMapper.addUser(user);
        Sqls.sqlSession.commit();
    }

    public List<Book> getAllBooks() {
        return Sqls.bookMapper.getAllBooks();
    }
    public List<Book> searchBookByBorrow(){
        return Sqls.bookMapper.getBooksByBorrow(user.getId());
    }

    public List<Book> searchBooksByKeyword(String bookName) {
        return Sqls.bookMapper.getBooksByKeyword(bookName);
    }
    public Book searchBookByNames(String bookName,String authorName){
        return Sqls.bookMapper.getBookByNames(bookName,authorName);
    }


    public List<Book> searchBookByConditions(Book book, boolean isByBookName, boolean isByPrice, boolean isDescending, boolean isUnclear) {
        List<Book> res=null;
        if(!isUnclear){
            res=Sqls.bookMapper.getBooksByClearConditions(book,isByBookName,isByPrice,isDescending);
        } else {
            res=Sqls.bookMapper.getBooksByUnClearConditions(book,isByBookName,isByPrice,isDescending);
        }
        return res;
    }

    //借阅书籍
    public int borrowBook(int id, String bookName, String authorName) {
        Book book = searchBookByNames(bookName,authorName);
        if(book.getRemains()<=0)    //处理不合理清空
            return -1;
        UserBorrow borrow = new UserBorrow(id,book.getId(),System.currentTimeMillis());
        try{
            Sqls.borrowMapper.insertOne(borrow);//添加借阅信息
            Sqls.bookMapper.updateLessRemain(book.getId());//更新书本余量信息
            Sqls.userMapper.updateMoreBollowingNnum(id);//更新用户借阅数量
            Sqls.sqlSession.commit();//提交事务
        } catch (Exception e){
            e.printStackTrace();
            Sqls.sqlSession.rollback();//回滚事务
            return 0;
        }
        return 1;
    }
    public boolean returnBook(int id, String bookName, String authorName) {
        Book book = searchBookByNames(bookName,authorName);
        try{
            Sqls.borrowMapper.deleteOne(id,book.getId());
            Sqls.bookMapper.updateMoreRemain(book.getId());
            Sqls.userMapper.updateLessBollowingNnum(id);
            Sqls.sqlSession.commit();
        } catch (Exception e){
            Sqls.sqlSession.rollback();
            return false;
        }
        return true;
    }

    public void editBook(Book abook) {
        Sqls.bookMapper.updateBook(abook);
        Sqls.sqlSession.commit();
    }
    public void insertBook(Book abook) {
        Sqls.bookMapper.insertBook(abook);
        Sqls.sqlSession.commit();
    }

    public void deleteBook(int id) {
        Sqls.bookMapper.deleteBookByID(id);
        Sqls.sqlSession.commit();
    }
}
