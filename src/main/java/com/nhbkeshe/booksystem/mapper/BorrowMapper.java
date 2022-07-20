package com.nhbkeshe.booksystem.mapper;

import com.nhbkeshe.booksystem.pojo.UserBorrow;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface BorrowMapper {
    @Insert("insert into user_borrow values (#{userBorrow.userID},#{userBorrow.bookID},#{userBorrow.borrowTime})")
    void insertOne(@Param("userBorrow") UserBorrow userBorrow);

    @Delete("delete from user_borrow where user_id=#{uid} and book_id=#{bid}")
    void deleteOne(@Param("uid") int uid, @Param("bid") int bid);
}
