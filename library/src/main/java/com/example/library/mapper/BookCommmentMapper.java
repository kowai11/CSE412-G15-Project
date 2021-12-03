package com.example.library.mapper;

import com.example.library.model.Bookcomment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface BookCommmentMapper {
    @Insert("insert into bookcomment (commentid,rate,content,posttime)" +
            "values(#{commentid},#{rate},#{content},#{posttime})")
    Integer insert(Integer commentid, Integer rate, String content, Date posttime);

    @Select("select * from bookcomment where commentid = #{commentid}")
    Bookcomment getById(Integer commentid);
}
