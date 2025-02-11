package com.library.mappers;

import com.library.entity.Book;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface BookMappers {

    @Insert("insert into BOOK (bid, title, author, press, description) values (#{bid}, #{title}, #{author}, #{press}, #{description})")
    int insertBook(Book book);

    @Select("select * from BOOK")
    List<Book> selectAllBook();

    @Select("select * from BOOK where bid = #{bid}")
    Book selectBookById(int bid);

    @Delete("delete from BOOK where bid = #{bid}")
    int deleteBookById(int bid);

    @Update("update BOOK set title = #{title}, author = #{author}, press = #{press}, description = #{description} where bid = #{bid}")
    int updateBook(Book book);
}
