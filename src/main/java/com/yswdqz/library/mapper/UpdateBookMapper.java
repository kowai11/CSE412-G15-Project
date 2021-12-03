package com.yswdqz.library.mapper;

import lombok.Data;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UpdateBookMapper {
    @Insert("insert into updatebook (id,isbn) values(#{id},#{isbn})")
    Integer insert(Integer id,String isbn);
}
