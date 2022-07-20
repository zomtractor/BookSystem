package com.nhbkeshe.booksystem.util;

import com.nhbkeshe.booksystem.mapper.BookMapper;
import com.nhbkeshe.booksystem.mapper.BorrowMapper;
import com.nhbkeshe.booksystem.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class Sqls {

    private static SqlSessionFactory sqlSessionFactory;
    public static UserMapper userMapper;
    public static BookMapper bookMapper;
    public static BorrowMapper borrowMapper;
    public static SqlSession sqlSession;
    static {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
            userMapper=sqlSession.getMapper(UserMapper.class);
            bookMapper = sqlSession.getMapper(BookMapper.class);
            borrowMapper=sqlSession.getMapper(BorrowMapper.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
