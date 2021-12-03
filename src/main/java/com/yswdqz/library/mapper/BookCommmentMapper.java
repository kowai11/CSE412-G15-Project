package com.yswdqz.library.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Mapper
public interface BookCommmentMapper {
    @Insert("insert into bookcomment (commentid,rate,content,posttime)" +
            "values(#{commentid},#{rate},#{content},#{posttime})")
    Integer insert(Integer commentid, Integer rate, String content, Date posttime);
}
