package com.library.mappers;

import com.library.entity.Borrow;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BorrowMappers {
    @Insert("insert into BORROW (bid, sid, borrow_date, return_date) values (#{bid}, #{sid}, #{borrowDate}, #{returnDate})")
    @Results({
            @Result(property = "borrowDate", column = "borrow_date"),
            @Result(property = "returnDate", column = "return_date")
    })
    int insertBorrow(Borrow borrow);

    @Select("select * from BORROW")
    List<Borrow> selectAllBorrow();

    @Select("select * from BORROW where sid = #{sid}")
    List<Borrow> selectBorrowBySid(int sid);

    @Select("select * from BORROW where bid = #{bid}")
    List<Borrow> selectBorrowByBid(int bid);

    @Delete("delete from BORROW where sid = #{sid} and bid = #{bid}")
    // 需要加@Param注解，因为编译后变量名会被抹除
    int deleteBorrow(@Param("sid") int sid, @Param("bid") int bid);

    @Update("update BORROW set borrow_date = #{borrowDate}, return_date = #{returnDate} where sid = #{sid} and bid = #{bid}")
    @Results({
            @Result(property = "borrowDate", column = "borrow_date"),
            @Result(property = "returnDate", column = "return_date")
    })
    int updateBorrow(Borrow borrow);
}
