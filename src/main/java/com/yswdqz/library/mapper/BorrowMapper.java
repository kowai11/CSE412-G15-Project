package com.yswdqz.library.mapper;

import com.yswdqz.library.model.Borrow;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.awt.*;
import java.util.Date;

@Mapper
public interface BorrowMapper {
    @Insert("insert into borrow (id,borrowtime,isbn,returntime)" +
            "values(#{id},#{borrowtime},#{isbn},#{returntime})")
    Integer insert(Integer id, Date borrowtime,Date returntime,String isbn);

    @Select("select * from borrow where id = #{id} and isbn = #{isbn}")
    Borrow searchByIdAndIsbn(Integer id,String isbn);

    @Delete("delete from borrow where id = #{id} and isbn = #{isbn}")
    Integer deleteByIdAndIsbn(Integer id,String isbn);
}
