package com.nhbkeshe.booksystem.mapper;

import com.nhbkeshe.booksystem.pojo.Book;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookMapper {
    @ResultMap("bookResultMap")
    @Select("select * from book " +
            "order by book_name")
    List<Book> getAllBooks();
    @ResultMap("bookResultMap")
    @Select("select * from book " +
            "where book_name=#{bookName} and author_name=#{authorName }" )
    Book getBookByNames(@Param("bookName") String bookName, @Param("authorName") String authorName);

    @ResultMap("bookResultMap")
    @Select("select * from book " +
            "where book_name like concat('%',#{keyWord},'%')")
    List<Book> getBooksByKeyword(@Param("keyWord") String keyWord);

    List<Book> getBooksByClearConditions(@Param("book") Book book,
                                         @Param("byName") boolean byName,
                                         @Param("byPrice") boolean byPrice,
                                         @Param("isDesc") boolean isDesc);
    List<Book> getBooksByUnClearConditions(@Param("book") Book book,
                                         @Param("byName") boolean byName,
                                         @Param("byPrice") boolean byPrice,
                                         @Param("isDesc") boolean isDesc);
    List<Book> getBooksByBorrow(@Param("id") int id);

    @Update("update book set remains=remains-1 " +
            "where book.id=#{id}")
    void updateLessRemain(@Param("id") int id);
    @Update("update book set remains=remains+1 " +
            "where book.id=#{id}")
    void updateMoreRemain(@Param("id") int id);

    void updateBook(@Param("abook") Book abook);

    @Insert("insert into book(book_name, author_name, press, price, demo)\n" +
            "values (#{abook.bookName},#{abook.authorName},#{abook.press},#{abook.price},#{abook.demo})")
    void insertBook(@Param("abook") Book abook);

    @Delete("delete from book where id=#{id}")
    void deleteBookByID(@Param("id") int id);
}
