package com.library.util;

import lombok.extern.java.Log;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.function.Consumer;

@Log
public class SqlUtil {
    private static SqlSessionFactory sqlSessionFactory;
    private static SqlSession sqlSession;

    static {
        try {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
            executeCreateTableSql();
        } catch (IOException e) {
            log.warning("初始化SqlSessionFactory失败" + e.getMessage());
        }
    }

    public static SqlSession openSqlSession() {
        sqlSession = sqlSessionFactory.openSession(true);
        return sqlSession;
    }

    public static void closeSqlSession() {
        if (sqlSession != null) {
            sqlSession.close();
        }
    }

    // 执行创建表的 SQL 语句
    private static void executeCreateTableSql() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS BOOK (" +
                "bid INTEGER NOT NULL CONSTRAINT BOOK_pk PRIMARY KEY, " +
                "title TEXT NOT NULL, " +
                "author TEXT NOT NULL, " +
                "description TEXT, " +
                "press TEXT NOT NULL" +
                ");" +
                "CREATE TABLE IF NOT EXISTS STUDENT (" +
                "sid INTEGER NOT NULL CONSTRAINT STUDENT_pk PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "age INTEGER NOT NULL, " +
                "gender TEXT NOT NULL" +
                ");" +
                "CREATE TABLE IF NOT EXISTS BORROW (" +
                "sid INTEGER NOT NULL CONSTRAINT BORROW_STUDENT_sid_fk REFERENCES STUDENT, " +
                "bid INTEGER NOT NULL CONSTRAINT BORROW_BOOK_bid_fk REFERENCES BOOK, " +
                "borrow_date TEXT NOT NULL, " +
                "return_date TEXT NOT NULL" +
                ");" +
                "CREATE TABLE IF NOT EXISTS sqlite_master (" +
                "type TEXT, " +
                "name TEXT, " +
                "tbl_name TEXT, " +
                "rootpage INT, " +
                "sql TEXT" +
                ");" +
                "CREATE TABLE IF NOT EXISTS sqlite_sequence (" +
                "name, " +
                "seq" +
                ");";
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.getConnection().createStatement().executeUpdate(createTableSql);
            session.commit();
        } catch (SQLException e) {
            log.warning("执行创建表的 SQL 语句失败: " + e.getMessage());
        }
    }

//    public static <T> void doSqlWork(Class<T> mapperClass, Consumer<T> consumer) {
//        consumer.accept(sqlSession.getMapper(mapperClass));
//    }

    public static <T> void doSqlWork(Class<T> mapperClass, Consumer<T> consumer) {
        SqlSession session = null;
        try {
            session = sqlSessionFactory.openSession();
            T mapper = session.getMapper(mapperClass);
            consumer.accept(mapper);
            session.commit();
        } catch (Exception e) {
            if (session != null) {
                session.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
