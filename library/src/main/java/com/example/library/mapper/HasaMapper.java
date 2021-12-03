package com.example.library.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HasaMapper {
    @Insert("insert into hasa (commentid,isbn) values(#{commentid},#{isbn})")
    Integer insert(Integer commentid,String isbn);
}
