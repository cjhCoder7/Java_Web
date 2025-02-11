package com.library.mappers;

import com.library.entity.Student;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface StudentMappers {

    @Insert("insert into STUDENT (sid, name, age, gender) values (#{sid}, #{name}, #{age}, #{gender})")
    int insertStudent(Student student);

    @Select("select * from STUDENT")
    List<Student> selectAllStudent();

    @Select("select * from STUDENT where sid = #{sid}")
    Student selectStudentById(int sid);

    @Delete("delete from STUDENT where sid = #{sid}")
    int deleteStudentById(int sid);

    @Update("update STUDENT set name = #{name}, age = #{age}, gender = #{gender} where sid = #{sid}")
    int updateStudent(Student student);
}
