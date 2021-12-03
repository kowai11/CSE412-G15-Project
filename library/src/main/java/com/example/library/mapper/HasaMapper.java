package com.example.library.mapper;

import com.example.library.model.Hasa;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HasaMapper {
    @Insert("insert into hasa (commentid,isbn) values(#{commentid},#{isbn})")
    Integer insert(Integer commentid,String isbn);
    @Select("select * from hasa where isbn = #{isbn}")
    List<Hasa> getHasaByIsbn(String isbn);
}
