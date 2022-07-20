package com.nhbkeshe.booksystem.mapper;

import com.nhbkeshe.booksystem.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {
    @ResultMap("userResultMap")
    @Select("select * from user where username = #{username}")
    User selectUserByUsername(String username);

    void addUser(User user);

    @ResultMap("userResultMap")
    @Select("select * from user")
    List<User> selectAllUser();

    @Update("update user set borrowing_num=borrowing_num+1 " +
            "where id=#{id}")
    void updateMoreBollowingNnum(@Param("id") int id);
    @Update("update user set borrowing_num=borrowing_num-1 " +
            "where id=#{id}")
    void updateLessBollowingNnum(@Param("id") int id);

}
